package com.orangelabs.tuya2capi.tuya2cApi.business.comments.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orangelabs.reservation.base.responsebody.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.req.CommentReqt;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.service.CommentsService;

@BaseResponse
@RestController
public class CommentsController {
	
	private Logger log = Logger.getLogger(CommentsController.class);
	
	@Autowired
	private CommentsService commentsService;
	
	@RequestMapping(value = "/comments/{productId}", method = { RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getCommentsList(@PathVariable String productId) throws Exception {
		log.info("get comments list  " + productId);
		
		Map<String, Object> list = commentsService.getCommentsList(productId);
		
		return list;
	}
	
	@RequestMapping(value = "/comments", method = { RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> createComments(@RequestBody CommentReqt request) throws Exception {
		log.info("create.....comments  ");
		
		Map<String, Object> map = commentsService.createOrUpdateComment(request, false, false);
		
		return map;
	}
	
	@RequestMapping(value = "/comments/{commentId}", method = { RequestMethod.PUT})
	@ResponseBody
	public Map<String, Object> updateComments(@PathVariable String commentId, @RequestBody CommentReqt request) throws Exception {
		log.info("update.....comments  ");
		request.setCommentId(commentId);
		Map<String, Object> map = commentsService.createOrUpdateComment(request, true, false);
		return map;
	}
	
	@RequestMapping(value = "/comments/{commentId}/{productId}/{userId}", method = { RequestMethod.DELETE})
	@ResponseBody
	public Map<String, Object> deleteComments(@PathVariable String commentId, @PathVariable String productId, @PathVariable String userId) throws Exception {
		log.info("delete.....comments  ");
		CommentReqt request = new CommentReqt();
		request.setCommentId(commentId);
		
		Map<String, Object> map = commentsService.createOrUpdateComment(request, false, true);
		return map;
	}

}
