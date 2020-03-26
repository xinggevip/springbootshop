package com.qiangssvip.dao;

import com.qiangssvip.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByIdAndUid(@Param("shippingId") Integer shippingId, @Param("uid") Integer uid);

    List<Shipping> selectByUid(Integer uid);

    Shipping selectByUidAndShippingId(@Param("uid") Integer uid, @Param("shippingId") Integer shipping);

    List<Shipping> selectByIdSet(@Param("idSet") Set<Integer> idSet);
}