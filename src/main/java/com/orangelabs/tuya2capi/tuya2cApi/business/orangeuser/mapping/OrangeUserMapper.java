package com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.model.OrangeUser;

@Mapper
public interface OrangeUserMapper {
    int deleteByPrimaryKey(Long orangeUserId);

    int insert(OrangeUser record);

    int insertSelective(OrangeUser record);

    OrangeUser selectByPrimaryKey(Long orangeUserId);

    int updateByPrimaryKeySelective(OrangeUser record);

    int updateByPrimaryKey(OrangeUser record);
    
    List<OrangeUser> selectByCondition(Map<String,Object> map);
}