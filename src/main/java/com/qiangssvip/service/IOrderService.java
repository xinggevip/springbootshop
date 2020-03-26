package com.qiangssvip.service;

import com.github.pagehelper.PageInfo;
import com.qiangssvip.service.vo.OrderVo;
import com.qiangssvip.service.vo.ResponseVo;

public interface IOrderService {
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

}
