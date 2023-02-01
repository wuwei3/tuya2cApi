package com.orangelabs.tuya2capi.tuya2cApi.business.comments.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orangelabs.tuya2capi.tuya2cApi.business.comments.mapping.OrangeCommentFileMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.model.OrangeCommentFile;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.resp.CommentFileListResp;

@Service
public class CommentFileService {
	
	private Logger log = Logger.getLogger(CommentFileService.class);
	
	@Autowired
	private OrangeCommentFileMapper orangeCommentFileMapper;
	
	public CommentFileListResp insertOrUpdate(Long commentId, byte[] file, String fileName, boolean update) throws Exception {
		log.info("exec upload file");
		return createFileEntity(commentId, file, fileName,update);
	}
	
	private CommentFileListResp createFileEntity(Long commentId, byte[] file, String fileName, boolean update) throws Exception{
		OrangeCommentFile commentfile = null;
		if (update) {
			log.info("to update comment file,  commentId " + commentId + ", fileName " + fileName);
			
			commentfile = getCommentFileByCommentId(commentId);
			
			if (commentfile != null) {
				commentfile.setCommentFile(file);
				commentfile.setCommentFileName(fileName);
				commentfile.setUpdateTime(new Date());
				
				orangeCommentFileMapper.updateByPrimaryKeyWithBLOBs(commentfile);
			}
		} else {
			log.info("to create new comment file,  commentId " + commentId + ", fileName " + fileName);
			commentfile = new OrangeCommentFile();
			
			commentfile.setCommentFile(file);
			commentfile.setCommentFileName(fileName);
			commentfile.setUpdateTime(new Date());
			commentfile.setCommentId(commentId);
			commentfile.setCreateTime(new Date());
			orangeCommentFileMapper.insert(commentfile);
		}
		
		if (commentfile != null) {
			CommentFileListResp resp = new CommentFileListResp();
			resp.setCommentFileId(commentfile.getCommentFileId().toString());
			resp.setFileName(fileName);
			return resp;
		} else {
			return null;
		}
	}
	
	
	public void deleteCommentFile(Long commentId)throws Exception {
		log.info("to delte comment file,  commentId " + commentId);
		orangeCommentFileMapper.deleteByCommentId(commentId);
	}
	
	public OrangeCommentFile getCommentFileByCommentId(Long commentId) throws Exception {
		log.info("to GET comment file by comment,  commentId " + commentId);
		
		OrangeCommentFile file = orangeCommentFileMapper.selectByCommentId(commentId);
		if (file == null) {
			log.info("commentId " + commentId + " has no file!!!");
		}
		
		return file;
	}
	
	public OrangeCommentFile getCommentFileByCommentFileId(Long commentFileId) throws Exception {
		log.info("to GET comment file by itself id,  commentFileId " + commentFileId);
		
		OrangeCommentFile file = orangeCommentFileMapper.selectByPrimaryKey(commentFileId);
		if (file == null) {
			log.info("comment file Id " + commentFileId + " has no file!!!");
		}
		
		return file;
	}
	
	public String downloadFile(Long commentFileId,HttpServletResponse response) {
		log.info("to download the file, id " + commentFileId);
		String result;
		try {
			OrangeCommentFile cf = getCommentFileByCommentFileId(commentFileId);

			String filename = cf.getCommentFileName();
			byte[] b = cf.getCommentFile();
			InputStream inputStream = new ByteArrayInputStream(b);

			response.reset();
			response.setContentType("application/octet-stream");

			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			ServletOutputStream outputStream = response.getOutputStream();

			int len;
			//从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
			while ((len = inputStream.read(b)) > 0) {
				outputStream.write(b, 0, len);
			}
			inputStream.close();
			result = "download success";
		} catch (UnsupportedEncodingException e) {
			result = "download failedw with UnsupportedEncodingException";
			e.printStackTrace();
		} catch (IOException e) {
			result = "download failedw with IOException";
			e.printStackTrace();
		} catch (Exception e) {
			result = "download failedw with exception";
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static File byte2file(byte[] bytes, String fileFullPath) {
        if (bytes == null) {
            return null;
        }
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(fileFullPath);
            //判断文件是否存在
            if (file.exists()) {
                file.mkdirs();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }  catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return null;
    }
	
	

}
