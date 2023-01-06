package com.orangelabs.tuya2capi.tuya2cApi.business.products.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProductParam;

@Mapper
public interface OrangeProductParamMapper {
    int deleteByPrimaryKey(Long productParamId);

    int insert(OrangeProductParam record);

    int insertSelective(OrangeProductParam record);

    OrangeProductParam selectByPrimaryKey(Long productParamId);

    int updateByPrimaryKeySelective(OrangeProductParam record);

    int updateByPrimaryKeyWithBLOBs(OrangeProductParam record);

    int updateByPrimaryKey(OrangeProductParam record);
    
    List<OrangeProductParam> getParamsByProductId(Long productId);
    
    int deleteByProductId(Long productId);
}