package com.orangelabs.tuya2capi.tuya2cApi.business.comments.model;

import java.util.Date;

public class OrangeComment {
    private Long commentId;

    private Long orangeUserId;

    private Long productId;

    private String country;

    private String userRole;

    private Integer commentOrder;

    private String commentLabel;

    private Integer commentRating;

    private Date createTime;

    private Date updateTime;

    private String commentContent;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getOrangeUserId() {
        return orangeUserId;
    }

    public void setOrangeUserId(Long orangeUserId) {
        this.orangeUserId = orangeUserId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public Integer getCommentOrder() {
        return commentOrder;
    }

    public void setCommentOrder(Integer commentOrder) {
        this.commentOrder = commentOrder;
    }

    public String getCommentLabel() {
        return commentLabel;
    }

    public void setCommentLabel(String commentLabel) {
        this.commentLabel = commentLabel == null ? null : commentLabel.trim();
    }

    public Integer getCommentRating() {
        return commentRating;
    }

    public void setCommentRating(Integer commentRating) {
        this.commentRating = commentRating;
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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }
}