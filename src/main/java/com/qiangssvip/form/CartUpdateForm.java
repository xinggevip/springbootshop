package com.qiangssvip.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateForm {

    private Integer quantity;

    private Boolean selected;
}
