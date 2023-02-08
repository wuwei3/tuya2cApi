package com.orangelabs.tuya2capi.tuya2cApi.business.news.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.news.model.OrangeNews;
import com.orangelabs.tuya2capi.tuya2cApi.business.news.model.OrangeNewsWithBLOBs;

@Mapper
public interface OrangeNewsMapper {
    int deleteByPrimaryKey(Long newsId);

    int insert(OrangeNewsWithBLOBs record);

    int insertSelective(OrangeNewsWithBLOBs record);

    OrangeNewsWithBLOBs selectByPrimaryKey(Long newsId);

    int updateByPrimaryKeySelective(OrangeNewsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(OrangeNewsWithBLOBs record);

    int updateByPrimaryKey(OrangeNews record);
    
    List<OrangeNewsWithBLOBs> selectAllList();
    
    List<OrangeNewsWithBLOBs> selectTop3List();
}