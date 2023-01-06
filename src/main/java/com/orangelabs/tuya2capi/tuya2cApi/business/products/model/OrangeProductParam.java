package com.orangelabs.tuya2capi.tuya2cApi.business.products.model;

import java.util.Date;

public class OrangeProductParam {
    private Long productParamId;

    private Long productId;

    private Date createTime;

    private Date updateTime;

    private String productParamContent;

    public Long getProductParamId() {
        return productParamId;
    }

    public void setProductParamId(Long productParamId) {
        this.productParamId = productParamId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getProductParamContent() {
        return productParamContent;
    }

    public void setProductParamContent(String productParamContent) {
        this.productParamContent = productParamContent == null ? null : productParamContent.trim();
    }
}