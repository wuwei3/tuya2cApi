package com.orangelabs.tuya2capi.tuya2cApi.business.news.resp;

public class NewsListResp {
	
	private Long newsId;
	
	private Long key;
	
    private String newsTitle;
	
	private String newsImage;
	
	private String newsContent;
	
	private String shortDesc;
	
	private String publishTime;
	
	private String newsUpdateTime;
	
	private Integer status;
	
	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsImage() {
		return newsImage;
	}

	public void setNewsImage(String newsImage) {
		this.newsImage = newsImage;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getNewsUpdateTime() {
		return newsUpdateTime;
	}

	public void setNewsUpdateTime(String newsUpdateTime) {
		this.newsUpdateTime = newsUpdateTime;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
