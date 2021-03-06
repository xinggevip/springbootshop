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

    LOGIN_ERROR(6,"用户名或密码错误"),

    USER_NOFIND(7, "用户未注册"),

    NEED_LOGIN(10,"用户未登录，请先登录"),

    PARAMTER_ERROR(3,"参数错误"),

    PRODUCT_OFF_SALE_OR_DELETE(12,"商品下架或删除"),

    PRODUCT_NOT_EXIST(13,"商品不存在"),

    PRODUCT_STOCK_ERROR(14,"库存不足"),

    CART_PRODUCT_NOT_EXIST(15,"购物车里无此商品"),

    DELETE_SHIPPING_FAIL(16,"删除商品失败"),

    UPDATE_SHIPPING_FAIL(99,"更新商品失败"),

    SHIPPING_NOT_EXIST(17,"地址不存在"),

    CART__SELECTED_IS_EMPTY(18,"请选择商品后下单"),

    ORDER_NOT_EXIST(19,"订单不存在"),

    ORDER_STATUS_ERROR(20,"订单状态错误"),

    ;


    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
