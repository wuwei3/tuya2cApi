package com.orangelabs.tuya2capi.tuya2cApi.business.fav.model;

import java.util.Date;

public class OrangeUserFav {
    private Long favId;

    private Long productId;

    private Long orangeUserId;

    private Date createTime;

    private Date updateTime;

    public Long getFavId() {
        return favId;
    }

    public void setFavId(Long favId) {
        this.favId = favId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrangeUserId() {
        return orangeUserId;
    }

    public void setOrangeUserId(Long orangeUserId) {
        this.orangeUserId = orangeUserId;
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
}