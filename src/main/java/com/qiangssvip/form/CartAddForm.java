package com.qiangssvip.form;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 添加商品
 */

@Data
@Valid
public class CartAddForm {

    @NotNull(message = "不能为空")
    private Integer productId;

    private Boolean selected = true;

}
