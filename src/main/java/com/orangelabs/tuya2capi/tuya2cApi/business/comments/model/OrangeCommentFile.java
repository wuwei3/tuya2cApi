package com.orangelabs.tuya2capi.tuya2cApi.business.comments.model;

import java.util.Date;

public class OrangeCommentFile {
    private Long commentFileId;

    private Long commentId;

    private String commentFileName;

    private Date createTime;

    private Date updateTime;

    private byte[] commentFile;

    public Long getCommentFileId() {
        return commentFileId;
    }

    public void setCommentFileId(Long commentFileId) {
        this.commentFileId = commentFileId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentFileName() {
        return commentFileName;
    }

    public void setCommentFileName(String commentFileName) {
        this.commentFileName = commentFileName == null ? null : commentFileName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public byte[] getCommentFile() {
        return commentFile;
    }

    public void setCommentFile(byte[] commentFile) {
        this.commentFile = commentFile;
    }
}