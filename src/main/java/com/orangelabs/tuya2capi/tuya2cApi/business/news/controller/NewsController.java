package com.orangelabs.tuya2capi.tuya2cApi.business.news.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.business.news.reqt.NewsEditRequest;
import com.orangelabs.tuya2capi.tuya2cApi.business.news.resp.NewsListResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.news.service.NewsService;

@BaseResponse
@RestController
@RequestMapping("/news")
public class NewsController {
	
    private Logger log = Logger.getLogger(NewsController.class);
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value = "/createNewOne", method = { RequestMethod.POST})
	@ResponseBody
	public void createOrUpdate(@RequestBody NewsEditRequest request) throws Exception {
		log.info("create or update");
		newsService.createOrUpdateNews(request);
	}
	
	
	@RequestMapping(value = "/getAllNews", method = { RequestMethod.GET})
	@ResponseBody
	public List<NewsListResp> getAllNews() throws Exception {
		log.info("to get all list");
		return newsService.getAllNewsList();
	}
	
	@RequestMapping(value = "/get3News", method = { RequestMethod.GET})
	@ResponseBody
	public List<NewsListResp> get3News() throws Exception {
		log.info("to get 3 list");
		return newsService.getNewsListWith3();
	}
	
	@RequestMapping(value = "/deleteNews/{newsId}", method = {RequestMethod.DELETE})
	@ResponseBody
	public void deleteNews(@PathVariable Long newsId) throws Exception {
		log.info("to delete news");
		newsService.deleteNews(newsId);
	}
	
	@RequestMapping(value = "/getNewsDetail/{newsId}", method = {RequestMethod.GET})
	@ResponseBody
	public NewsListResp getNewsDetail(@PathVariable Long newsId) throws Exception {
		log.info("to delete news");
		return newsService.getNewsDetail(newsId);
	}

}
