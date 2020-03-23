package com.qiangssvip.service.impl;

import com.google.gson.Gson;
import com.qiangssvip.dao.ProductMapper;
import com.qiangssvip.enums.ProductStatusEnum;
import com.qiangssvip.enums.ResponseEnum;
import com.qiangssvip.form.CartAddForm;
import com.qiangssvip.pojo.Cart;
import com.qiangssvip.pojo.Product;
import com.qiangssvip.service.ICartService;
import com.qiangssvip.service.vo.CartProductVo;
import com.qiangssvip.service.vo.CartVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ICartServiceImpl implements ICartService {

    Integer quantity = 1;

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm form) {
        // 判断商品是否存在
        // 商品是否为在售状态
        // 判断库存是否充足

        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        if (product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode()) || product.getStatus().equals(ProductStatusEnum.DELETE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        // 写入到redis
        // key: cart_1
//        redisTemplate.opsForValue().set(String.format(CART_REDIS_KEY_TEMPLATE, uid),
//                gson.toJson(new Cart(product.getId(), quantity, form.getSelected())));


//        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
//        opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE,uid),
//                String.valueOf(product.getId()),
//                gson.toJson(new Cart(product.getId(), quantity, form.getSelected()))
//        );


        /**
         * 先读取redis判断该用户有没有加购此商品
         * 如果没有 则加购
         * 如果有   则商品数量 + 1
         */
        Cart cart;

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid); // redisKey
        String value = opsForHash.get(redisKey, String.valueOf(product.getId())); // 根据redisKey和productId取 商品对象（已经序列化字符串了）
        if (StringUtils.isEmpty(value)) {
            cart = new Cart(product.getId(), quantity, true);
        } else {
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey,String.valueOf(product.getId()),gson.toJson(cart));

        ResponseVo<CartVo> list = list(uid);
        return list;
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid); // redisKey
        Boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;

        Map<String, String> entries = opsForHash.entries(redisKey);
        ArrayList<CartProductVo> cartProductVoArrayList = new ArrayList<>();
        CartVo cartVo = new CartVo();
        for (Map.Entry<String,String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);

            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(
                        productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartProductVoArrayList.add(cartProductVo);
                if (!cart.getProductSelected()) {
                    selectAll = false;
                }
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }

            }

            cartTotalQuantity += cart.getQuantity();
        }
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoArrayList);
        return ResponseVo.successs(cartVo);
    }
}
