package com.qiangssvip.controller;

import com.qiangssvip.consts.MallConst;
import com.qiangssvip.form.CartAddForm;
import com.qiangssvip.form.CartUpdateForm;
import com.qiangssvip.pojo.User;
import com.qiangssvip.service.ICartService;
import com.qiangssvip.service.vo.CartVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class CartController {

    @Autowired
    private ICartService iCartService;

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session) {
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return iCartService.list(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session) {
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return iCartService.add(user.getId(), cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session) {
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return iCartService.update(user.getId(),productId, form);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     HttpSession session) {
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return iCartService.delete(user.getId(),productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return iCartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return iCartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return iCartService.sum(user.getId());
    }


}
