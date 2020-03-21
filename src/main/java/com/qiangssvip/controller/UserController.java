package com.qiangssvip.controller;

import com.qiangssvip.form.UserForm;
import com.qiangssvip.pojo.User;
import com.qiangssvip.service.IUservice;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.qiangssvip.enums.ResponseEnum.PASSWORD_ERROR;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUservice iUservice;

    @RequestMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm,
                               BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            String msg = "";
//            log.info(bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                msg += fieldError.getField() + " " + fieldError.getDefaultMessage();
                log.error(fieldError.getField() + " " + fieldError.getDefaultMessage());
            }

            return ResponseVo.error(PASSWORD_ERROR,msg);

        }

        User user = new User();
        BeanUtils.copyProperties(userForm,user);  // 拷贝对象的属性

        log.info("username={}",userForm);
//        return ResponseVo.success();
//        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        return iUservice.register(user);
    }

}
