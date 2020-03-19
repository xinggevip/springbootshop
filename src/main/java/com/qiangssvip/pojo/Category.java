package com.qiangssvip.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Category {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;
}
