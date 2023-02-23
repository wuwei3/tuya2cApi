package com.orangelabs.tuya2capi.tuya2cApi.business.products.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProduct;

@Mapper
public interface OrangeProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(OrangeProduct record);

    int insertSelective(OrangeProduct record);

    OrangeProduct selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(OrangeProduct record);

    int updateByPrimaryKeyWithBLOBs(OrangeProduct record);

    int updateByPrimaryKey(OrangeProduct record);
    
    List<OrangeProduct> selectProductByCondition(Map<String,Object> map);
}