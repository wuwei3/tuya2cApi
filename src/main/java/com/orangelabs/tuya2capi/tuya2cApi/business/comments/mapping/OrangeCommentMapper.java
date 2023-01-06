package com.orangelabs.tuya2capi.tuya2cApi.business.comments.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.comments.model.OrangeComment;

@Mapper
public interface OrangeCommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(OrangeComment record);

    int insertSelective(OrangeComment record);

    OrangeComment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(OrangeComment record);

    int updateByPrimaryKeyWithBLOBs(OrangeComment record);

    int updateByPrimaryKey(OrangeComment record);
    
    List<OrangeComment> selectCommentByCondition(Map<String,Object> map);
}