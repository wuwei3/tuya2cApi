package com.orangelabs.tuya2capi.tuya2cApi.business.news.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orangelabs.tuya2capi.tuya2cApi.business.news.mapping.OrangeNewsMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.news.model.OrangeNewsWithBLOBs;
import com.orangelabs.tuya2capi.tuya2cApi.business.news.reqt.NewsEditRequest;
import com.orangelabs.tuya2capi.tuya2cApi.business.news.resp.NewsListResp;
import com.orangelabs.tuya2capi.tuya2cApi.utils.DateUtil;
import com.orangelabs.tuya2capi.tuya2cApi.utils.ZipUtil;


@Service
public class NewsService {
	
	private Logger log = Logger.getLogger(NewsService.class);
	
	@Autowired
	private OrangeNewsMapper orangeNewsMapper;
	
	public String createOrUpdateNews(NewsEditRequest request) throws Exception {
		log.info("to add or update news");
		
		Long newsid = request.getNewsId();
		if (newsid == null || newsid == 0) {
			log.info("create new one");
			OrangeNewsWithBLOBs entity = createEntity(request, false);
			orangeNewsMapper.insert(entity);
		} else {
			log.info("update news, id " + newsid);
			OrangeNewsWithBLOBs entity = createEntity(request, true);
			orangeNewsMapper.updateByPrimaryKeyWithBLOBs(entity);
		}
		
		return "create Or update success";
	}
	
	
	private OrangeNewsWithBLOBs createEntity(NewsEditRequest request, boolean update) throws Exception {
		OrangeNewsWithBLOBs entity = null;
		if (update) {
			Long newsid = request.getNewsId();
			entity = orangeNewsMapper.selectByPrimaryKey(newsid);
		} else {
			entity = new OrangeNewsWithBLOBs();
			entity.setCreateTime(new Date());
			entity.setPublishTime(new Date());
			entity.setStatus(1);
		}
		
		String title = request.getNewsTitle();
		if (title != null && !"".equals(title)) {
			entity.setNewsTitle(title);
		}
		
		String content = request.getNewsContent();
		if (content != null && !"".equals(content)) {
			//byte[] bc = ZipUtil.zipBase64(content);
			byte[] bc = content.getBytes();
			entity.setNewsContent(bc);
		}
		
		String image = request.getNewsImage();
		if (image != null && !"".equals(image)) {
			//byte[] bc = ZipUtil.zipBase64(image);
			byte[] bc = image.getBytes();
			entity.setNewsImage(bc);
		}
		
		entity.setUpdateTime(new Date());
		
		Integer status = request.getStatus();
		if (status != null) {
			if (status == 1) {
				entity.setStatus(0); // archive
			} else {
				entity.setStatus(1); // hui fu
			}
		}
		
		return entity;
	}
	
	
	public List<NewsListResp> getAllNewsList() throws Exception {
		log.info("get all news list");
		List<OrangeNewsWithBLOBs> list = orangeNewsMapper.selectAllList();
		List<NewsListResp> resp = transferToResp(list);
		
		return resp;
	}
	
	public List<NewsListResp> getAllNewsListOnNewsModule() throws Exception {
		log.info("get all news list on admin en news");
		List<OrangeNewsWithBLOBs> list = orangeNewsMapper.selectAllListNewsModule();
		List<NewsListResp> resp = transferToResp(list);
		
		return resp;
	}
	
	public List<NewsListResp> getNewsListWith3() throws Exception {
		log.info("get 3 list");
		List<OrangeNewsWithBLOBs> list = orangeNewsMapper.selectTop3List();
		List<NewsListResp> resp = transferToResp(list);
		
		return resp;
	}
	
	public NewsListResp getNewsDetail(Long newsId) throws Exception {
		log.info("get news detail, id " + newsId);
		
		OrangeNewsWithBLOBs news = orangeNewsMapper.selectByPrimaryKey(newsId);
		NewsListResp resp = null;
		if (news != null) {
			resp = getSingelResp(news);
		}
		return resp;
	}
	
	private List<NewsListResp> transferToResp(List<OrangeNewsWithBLOBs> news) {
		
		List<NewsListResp> result = new ArrayList<>();
		
		if (news != null && news.size() > 0) {
			for (OrangeNewsWithBLOBs on:news) {
				NewsListResp resp = getSingelResp(on);
				result.add(resp);
			}
		}
		return result;
	}
	
	
	private NewsListResp getSingelResp(OrangeNewsWithBLOBs on) {
		
		NewsListResp resp = new NewsListResp();
		resp.setNewsId(on.getNewsId());
		resp.setKey(on.getNewsId());
		resp.setNewsTitle(on.getNewsTitle());
		
		byte[] content = on.getNewsContent();
		String contentToFront = new String(content);
		resp.setNewsContent(contentToFront);
		
		if (contentToFront != null && !"".equals(contentToFront)) {
			if (contentToFront.length() > 320) {
				String shortDesc = contentToFront.substring(0, 320);
				resp.setShortDesc(shortDesc + "...");
			} else {
				resp.setShortDesc(contentToFront);
			}
		}
		
		byte[] image = on.getNewsImage();
		if (image != null && image.length > 0) {
			String imageToFront = new String(image);
			resp.setNewsImage(imageToFront);
		}
		
		resp.setPublishTime(DateUtil.getDate(DateUtil.dateMdFranchPattern2, on.getPublishTime()));
		resp.setNewsUpdateTime(DateUtil.getDate(DateUtil.dateMdFranchPattern2, on.getUpdateTime()));
		resp.setStatus(on.getStatus());
		
		return resp;
	}
	
	public void deleteNews(Long newsId) throws Exception {
		log.info("delete news, id " + newsId);
		orangeNewsMapper.deleteByPrimaryKey(newsId);
	}
	

}
