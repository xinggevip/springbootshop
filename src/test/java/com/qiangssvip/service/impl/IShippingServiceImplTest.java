package com.qiangssvip.service.impl;

import com.github.pagehelper.PageInfo;
import com.qiangssvip.form.ShippingForm;
import com.qiangssvip.service.IShippingService;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Slf4j
//@Transactional
class IShippingServiceImplTest {

    @Autowired
    private IShippingService shippingService;

    private Integer uid = 12;

    @Test
    void add() {
        ShippingForm form = new ShippingForm("星哥", "15912345678", "15912345678", "河南", "商丘", "梁园区", "高星电子商务经营部", "476340");
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        log.info("responseVo = {}",responseVo.getData());

    }

    @Test
    void delete() {
        ResponseVo responseVo = shippingService.delete(10, 8);
        log.info("responseVo = {}",responseVo);
    }

    @Test
    void update() {
        ResponseVo responseVo = shippingService.update(12, 11,new ShippingForm("星哥vip", "15912345678", "15912345678", "河南", "商丘", "梁园区", "高星电子商务经营部", "476340"));
        log.info("responseVo = {}",responseVo);
    }

    @Test
    void list() {
        ResponseVo<PageInfo> responseVo = shippingService.list(12,1,10);
        log.info("responseVo = {}",responseVo.getData());
    }
}