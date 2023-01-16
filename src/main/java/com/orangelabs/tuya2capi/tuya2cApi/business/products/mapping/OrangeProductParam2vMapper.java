package com.orangelabs.tuya2capi.tuya2cApi.business.products.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProductParam2v;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.DistinctParamKeyResp;

@Mapper
public interface OrangeProductParam2vMapper {
    int deleteByPrimaryKey(Long orangeProductParamId);

    int insert(OrangeProductParam2v record);

    int insertSelective(OrangeProductParam2v record);

    OrangeProductParam2v selectByPrimaryKey(Long orangeProductParamId);

    int updateByPrimaryKeySelective(OrangeProductParam2v record);

    int updateByPrimaryKey(OrangeProductParam2v record);
    
    List<OrangeProductParam2v> getParamsv2ByConditions(Map<String,Object> map);
    
    List<OrangeProductParam2v> getValsByConditions(Map<String,Object> map);
    
    List<DistinctParamKeyResp> getDistinctKeysByConditions(Map<String,Object> map);
    
    int deleteByProductId(Long productId);
}