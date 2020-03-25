package com.qiangssvip.service;

import com.qiangssvip.form.CartAddForm;
import com.qiangssvip.form.CartUpdateForm;
import com.qiangssvip.pojo.Cart;
import com.qiangssvip.service.vo.CartVo;
import com.qiangssvip.service.vo.ResponseVo;

import java.util.List;

public interface ICartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm form);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);

    ResponseVo<CartVo> delete(Integer uid,Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);

    List<Cart> listForCart(Integer uid);


}
