package com.orangelabs.tuya2capi.tuya2cApi.business.comments.mapping;

import org.apache.ibatis.annotations.Mapper;

import com.orangelabs.tuya2capi.tuya2cApi.business.comments.model.OrangeCommentFile;

@Mapper
public interface OrangeCommentFileMapper {
    int deleteByPrimaryKey(Long commentFileId);

    int insert(OrangeCommentFile record);

    int insertSelective(OrangeCommentFile record);

    OrangeCommentFile selectByPrimaryKey(Long commentFileId);

    int updateByPrimaryKeySelective(OrangeCommentFile record);

    int updateByPrimaryKeyWithBLOBs(OrangeCommentFile record);

    int updateByPrimaryKey(OrangeCommentFile record);
    
    OrangeCommentFile selectByCommentId(Long commentId);
    
    int deleteByCommentId(Long commentId);
}