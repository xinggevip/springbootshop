package com.qiangssvip.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    ERROR(-1,"服务器错误"),

    SUCCESS(0,"成功"),

    PASSWORD_ERROR(1,"密码错误"),

    USER_EXIST(2,"用户名已存在"),

    EMAILE_EXIST(4,"邮箱已存在"),

    REGISTER_FAILED(5,"注册失败"),

    NEED_LOGIN(10,"用户未登录，请先登录"),

    PARAMTER_ERROR(3,"参数错误");

    ;

    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
