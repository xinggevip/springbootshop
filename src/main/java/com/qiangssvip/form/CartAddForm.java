package com.qiangssvip.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 添加商品
 */

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class CartAddForm {

    @NotNull(message = "不能为空")
    private Integer productId;

    private Boolean selected = true;

}
