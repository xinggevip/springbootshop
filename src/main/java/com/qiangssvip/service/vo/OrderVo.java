package com.qiangssvip.service.vo;

import com.qiangssvip.pojo.Shipping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private  Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private Integer shippingId;

    private Shipping shippingVo;

    private List orderItemVoList;

}
