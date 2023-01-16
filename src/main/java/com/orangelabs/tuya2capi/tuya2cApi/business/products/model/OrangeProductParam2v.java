package com.orangelabs.tuya2capi.tuya2cApi.business.products.model;

import java.util.Date;

public class OrangeProductParam2v {
    private Long orangeProductParamId;

    private Long orangeProductId;

    private String orangeProductParamKey;

    private String orangeProductParamVals;

    private Date createTime;

    private Date updateTime;

    public Long getOrangeProductParamId() {
        return orangeProductParamId;
    }

    public void setOrangeProductParamId(Long orangeProductParamId) {
        this.orangeProductParamId = orangeProductParamId;
    }

    public Long getOrangeProductId() {
        return orangeProductId;
    }

    public void setOrangeProductId(Long orangeProductId) {
        this.orangeProductId = orangeProductId;
    }

    public String getOrangeProductParamKey() {
        return orangeProductParamKey;
    }

    public void setOrangeProductParamKey(String orangeProductParamKey) {
        this.orangeProductParamKey = orangeProductParamKey == null ? null : orangeProductParamKey.trim();
    }

    public String getOrangeProductParamVals() {
        return orangeProductParamVals;
    }

    public void setOrangeProductParamVals(String orangeProductParamVals) {
        this.orangeProductParamVals = orangeProductParamVals == null ? null : orangeProductParamVals.trim();
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