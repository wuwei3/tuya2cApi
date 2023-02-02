package com.orangelabs.tuya2capi.tuya2cApi.business.comments.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.req.CommentReqt;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.resp.CommentFileListResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.service.CommentFileService;

@BaseResponse
@RestController
public class UploadFileController {

	private Logger log = Logger.getLogger(UploadFileController.class);

	@Autowired
	private CommentFileService commentFileService;

	@RequestMapping(value = "/uploadFileAck", method = { RequestMethod.POST })
	@ResponseBody
	public String uploadFileAck(@RequestParam(value = "upload_file", required = false) MultipartFile upload_file,
			HttpServletRequest request) throws Exception {
		log.info("this is for check receive file request from front, nothing to do ");
		if (upload_file != null) {
			log.info("upload from Upload component  " + upload_file.getOriginalFilename() + "   "
					+ upload_file.getSize());
		} else {
			log.info("not recieve anything from  Upload component");
		}
		return "Received the file from Upload component";
	}

	@RequestMapping(value = "/uploadFileAfterComment", method = { RequestMethod.POST })
	@ResponseBody
	public CommentFileListResp uploadFileAfterComment(
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			@RequestParam(value = "commentId") String commentId,
			@RequestParam(value = "isUpdateFile", required = false) String isUpdateFile, HttpServletRequest request)
			throws Exception {
		log.info("upload file After Comment, commentId  " + commentId);
		CommentFileListResp resp = new CommentFileListResp();
		if (files != null && files.length > 0 && commentId != null && !"".equals(commentId)) {
			log.info("files length  " + files.length);
			boolean update = "true".equals(isUpdateFile) ? true : false;
			MultipartFile file = files[0];
			
			byte[] content = file.getBytes();
			String fileName = file.getOriginalFilename();

			resp = commentFileService.insertOrUpdate(Long.valueOf(commentId), content, fileName,
					update);
		}
		return resp;
	}
	
	@RequestMapping(value = "/downloadFile", method = { RequestMethod.GET })
	@ResponseBody
	public String downloadFile(Long commentFileId, HttpServletResponse response) throws Exception {
		log.info("download filecommentId  ");
		String result = commentFileService.downloadFile(commentFileId, response);
		return result;
	}
	
	@RequestMapping(value = "/deleteFile/{commentFileId}", method = { RequestMethod.DELETE})
	@ResponseBody
	public void deleteFile(@PathVariable String commentFileId) throws Exception {
		log.info("delete.....comment file");
		commentFileService.deleteFile(Long.valueOf(commentFileId));
	}
}
