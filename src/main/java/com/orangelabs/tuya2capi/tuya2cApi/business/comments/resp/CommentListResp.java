package com.orangelabs.tuya2capi.tuya2cApi.business.comments.resp;

import java.util.HashMap;
import java.util.Map;

public class CommentListResp {
	
	private String _id;
	
	private String country;
	
    private String content;
    
    private Integer rating;
    
    private String productId;
    
    private String role;
    
    private String label;
    
    private Map<String, Object> user_id = new HashMap<>();
    
    private CommentFileListResp commentFiles;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Map<String, Object> getUser_id() {
		return user_id;
	}

	public void setUser_id(Map<String, Object> user_id) {
		this.user_id = user_id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public CommentFileListResp getCommentFiles() {
		return commentFiles;
	}

	public void setCommentFiles(CommentFileListResp commentFiles) {
		this.commentFiles = commentFiles;
	}
}
