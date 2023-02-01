package com.orangelabs.tuya2capi.tuya2cApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.orangelabs.tuya2capi.tuya2cApi.business.comments.model.OrangeCommentFile;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.service.CommentFileService;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.service.ProductService;

@SpringBootTest
class Tuya2cApiApplicationTests {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CommentFileService commentFileService;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testzhuanhuan() throws Exception{
//		Map<String, Object> list = productService.getAllProducts("all");
//		 
//		System.out.println(JSONObject.toJSONString(list));
		
	}
	
	@Test
	public void upload() throws Exception{
		
//		InputStream file =null;
//        byte[] finalBytes =null;
//        try {
//            file = new FileInputStream("F:\\文档\\2022-11-17log.out");
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            byte[] bytes = new byte[1024];
//            int temp;
//            while ((temp = file.read(bytes)) != -1) {
//                outputStream.write(bytes, 0, temp);
//            }
//           finalBytes = outputStream.toByteArray();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        commentFileService.insertOrUpdate(1l, finalBytes, "17log.out", false);
	}
	
	
	@Test
	public void download() throws Exception{
		
//		OrangeCommentFile e = commentFileService.getCommentFileByCommentId(1l);
//		
//		byte[] bytes = e.getCommentFile();
//        String filePath = "F:\\";
//        File file = new File(filePath+"nice.doc");
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bytes,0,bytes.length);
//            if(file.exists()){
//                System.out.println("success!!");
//            }else System.out.println("fail!!");
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
	}

}
