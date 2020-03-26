package com.qiangssvip.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiangssvip.dao.OrderItemMapper;
import com.qiangssvip.dao.OrderMapper;
import com.qiangssvip.dao.ProductMapper;
import com.qiangssvip.dao.ShippingMapper;
import com.qiangssvip.enums.OrderStatusEnum;
import com.qiangssvip.enums.PaymentTypeEnum;
import com.qiangssvip.enums.ResponseEnum;
import com.qiangssvip.pojo.*;
import com.qiangssvip.service.ICartService;
import com.qiangssvip.service.IOrderService;
import com.qiangssvip.service.vo.OrderItemVo;
import com.qiangssvip.service.vo.OrderVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        // 收获地址校验 查出来
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping == null) {
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        // 获取购物车，校验 是否有商品 库存
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(ResponseEnum.CART__SELECTED_IS_EMPTY);
        }

        List<Integer> productIds = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        List<Product> productList = productMapper.selectByProductIdSet(productIds);
        Map<Integer, Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();
        for (Cart cart : cartList) {
            // 根据productId查数据库
            Product product = map.get(cart.getProductId());
            if (product == null) {
                // 是否有商品
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST ,
                        "商品不存在 productId = " + cart.getProductId());
            }
            // 商品上下架状态
            if (product.getStatus().equals(2) || product.getStatus().equals(3)) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
                        "商品不是在售状态");
            }
            // 库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,
                        "库存不足" + product.getName());
            }

            OrderItem orderItem = buildOrderItem(uid, orderNo, product, cart.getQuantity());
            orderItemList.add(orderItem);

            // 减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }

        Order order = builderOrder(uid, orderNo, shippingId, orderItemList);
        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        // 计算价格  只计算选中的商品
        // 生成订单，入库 order 和 order——item 事务
        // 更新购物车
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }
        // 构造orderVo
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);

        return ResponseVo.successs(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVoList);

        return ResponseVo.successs(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.successs(orderVo);
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> OrderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(OrderItemVoList);

        if (shipping != null) {
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }

        return orderVo;
    }

    private Order  builderOrder(Integer uid,
                                Long orderNo,
                                Integer shippingId,
                                List<OrderItem> orderItemList) {

        BigDecimal payment = orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());

        return order;


    }

    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid,Long orderNO,Product product,Integer quantity) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNO);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        return item;
    }
}
