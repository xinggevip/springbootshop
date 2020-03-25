package com.qiangssvip.service;

import com.qiangssvip.service.vo.OrderVo;
import com.qiangssvip.service.vo.ResponseVo;

public interface IOrderService {
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

}
