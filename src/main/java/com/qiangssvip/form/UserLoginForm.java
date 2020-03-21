package com.qiangssvip.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginForm {

    @NotBlank(message = "用户名不能为空")  // 判断字符串是否为空
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
