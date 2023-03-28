package com.orangelabs.tuya2capi.tuya2cApi.business.news.reqt;

public class NewsEditRequest {
	
	private Long newsId;
	
	private String newsTitle;
	
	private String newsImage;
	
	private String newsContent;
	
	private Integer status;

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
