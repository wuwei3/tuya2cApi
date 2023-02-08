package com.orangelabs.tuya2capi.tuya2cApi.business.news.model;

public class OrangeNewsWithBLOBs extends OrangeNews {
    private byte[] newsImage;

    private byte[] newsContent;

    public byte[] getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(byte[] newsImage) {
        this.newsImage = newsImage;
    }

    public byte[] getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(byte[] newsContent) {
        this.newsContent = newsContent;
    }
}