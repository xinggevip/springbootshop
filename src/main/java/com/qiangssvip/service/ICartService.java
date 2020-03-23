package com.qiangssvip.service;

import com.qiangssvip.form.CartAddForm;
import com.qiangssvip.service.vo.CartVo;
import com.qiangssvip.service.vo.ResponseVo;

public interface ICartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm form);

}
