package com.qiangssvip.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserForm {

    //@NotEmpty  判断集合是否为空
    //@NotNull   // 判断字符串是否为null
    @NotBlank(message = "用户名不能为空")  // 判断字符串是否为空
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank
    @Email
    private String email;
}
