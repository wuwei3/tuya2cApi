package com.orangelabs.tuya2capi.tuya2cApi.business.fav.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.fav.model.OrangeUserFav;

@Mapper
public interface OrangeUserFavMapper {
    int deleteByPrimaryKey(Long favId);

    int insert(OrangeUserFav record);

    int insertSelective(OrangeUserFav record);

    OrangeUserFav selectByPrimaryKey(Long favId);

    int updateByPrimaryKeySelective(OrangeUserFav record);

    int updateByPrimaryKey(OrangeUserFav record);
    
    List<OrangeUserFav> selectFavByCondition(Map<String,Object> map);
}