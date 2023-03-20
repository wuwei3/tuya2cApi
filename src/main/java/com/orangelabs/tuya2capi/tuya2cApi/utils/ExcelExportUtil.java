package com.orangelabs.tuya2capi.tuya2cApi.utils;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ParamsResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ProductResp;

import sun.misc.BASE64Decoder;

public class ExcelExportUtil {
	private static Logger log = Logger.getLogger(ExcelExportUtil.class);
	
	/**
     * <一句话功能简述> excel插入图片
     * <功能详细描述>
     * author: zhanggw
     * 创建时间:  2022/5/25
     * @param book poi book对象
     * @param drawingPatriarch 用于图片插入Represents a SpreadsheetML drawing
     * @param rowIndex 图片插入的单元格第几行
     * @param colIndex 图片插入的单元格第几列
     * @param localPicPath 本地图片路径
     */
    public static void insertExcelPic(XSSFWorkbook book, XSSFDrawing drawingPatriarch, int rowIndex, int colIndex, String localPicPath) throws IOException {
        // 获取图片后缀格式
        String fileSuffix = localPicPath.substring(localPicPath.lastIndexOf(".") + 1);
        fileSuffix = fileSuffix.toLowerCase();
 
        // 将图片写入到字节数组输出流中
        BufferedImage bufferImg;
        ByteArrayOutputStream picByteOut = new ByteArrayOutputStream();
        bufferImg = ImageIO.read(new File(localPicPath));
        ImageIO.write(bufferImg, fileSuffix, picByteOut);
 
        // 将图片字节数组输出流写入到excel中
        XSSFClientAnchor anchor = new XSSFClientAnchor(12, 3, 0, 0,
                (short) colIndex, rowIndex, (short) colIndex + 1, rowIndex + 1);
        anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);
        drawingPatriarch.createPicture(anchor, book.addPicture(picByteOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        picByteOut.close();
    }
    
    public static BufferedImage base64ToBufferedImage(String base64) {
        try {
            if (base64.contains("data:")) {
                int start = base64.indexOf(",");
                base64 = base64.substring(start + 1);
            }
            byte[] bytes1 = new BASE64Decoder().decodeBuffer(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<ProductResp> list, HSSFWorkbook wb) throws Exception{
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }
    	
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
    	
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 650);
        // 第四步，创建单元格，并设置表头值 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            sheet.setColumnWidth(i, 9000);
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            HSSFFont font = wb.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 15);//设置字体大小
            style.setFont(font);
            cell.setCellStyle(style);
        }
        
        HSSFCellStyle styleCon = wb.createCellStyle();
        styleCon.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
        styleCon.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        for(int i=0;i<list.size();i++){
        	
        	row = sheet.createRow(i + 1);
            row.setHeight((short) 2050);
            
            ProductResp product = list.get(i);
            
            //将内容按顺序赋给对应的列对象
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            
            String base64 = product.getImageBase64();

            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = base64ToBufferedImage(base64);
            
            if (bufferImg != null) {
            	ImageIO.write(bufferImg, "png", byteArrayOut);
                //图片一导出到单元格E2中
                HSSFClientAnchor anchor = new HSSFClientAnchor(480, 30, 700, 250,
                        (short) 0, i+1, (short) 0, i+1);
            	
                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut
                        .toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                
                
                cell = row.createCell(1);
                cell.setCellValue(product.getName());
                cell.setCellStyle(styleCon);
                
                cell = row.createCell(2);
                cell.setCellValue(getPramsByName(product, "Product Model"));
                cell.setCellStyle(styleCon);
                
                cell = row.createCell(3);
                cell.setCellValue(getPramsByName(product, "Certification"));
                cell.setCellStyle(styleCon);
                
                cell = row.createCell(4);
                cell.setCellValue(getPramsByName(product, "Protocol"));
                cell.setCellStyle(styleCon);
                
                cell = row.createCell(5);
                cell.setCellValue(getPramsByName(product, "Voice Service Activated"));
                cell.setCellStyle(styleCon);
                
                cell = row.createCell(6);
                String url = "https://orange-smart-home.apps.fr01.paas.tech.orange/products/";
                cell.setCellValue(url + product.get_id());
                cell.setCellStyle(styleCon);
                
                cell = row.createCell(7);
                cell.setCellValue(getPramsByName(product, "Orange Recommendation"));
                cell.setCellStyle(styleCon);
                
                cell = row.createCell(8);
                cell.setCellValue(getPramsByName(product, "Orange Validation"));
                cell.setCellStyle(styleCon);
                
            } else {
            	log.info("bufferImg read failed!!!!!!!");
            }
        }
        
        return wb;
    }
    
    private static String getPramsByName(ProductResp product, String name) {
    	
    	List<ParamsResp> list = product.getParams();
    	List<String> vals = new ArrayList<>();
    	if (list != null && list.size() > 0) {
    		for (ParamsResp resp: list) {
    			String keyname = resp.getName();
    			if (name.equals(keyname.trim())) {
    				vals = resp.getVals();
    				break;
    			}
    		}
    	}
    	
    	String val = "";
    	if (vals != null && vals.size() > 0) {
    		val = String.join(",", vals);
    	}
    	return val;
    }
    
//    public static XSSFWorkbook getXSSFWorkbook(String sheetName, String[] title, List<ProductResp> list, XSSFWorkbook wb) throws Exception{
//        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
//        if(wb == null){
//            wb = new XSSFWorkbook();
//        }
//    	
//        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
//        XSSFSheet sheet = wb.createSheet(sheetName);
//    	
//        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
//        XSSFRow row = sheet.createRow(0);
//        row.setHeight((short) 650);
//        // 第四步，创建单元格，并设置表头值 设置表头居中
//        XSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        
//        XSSFCell cell = null;
//
//        //创建标题
//        for(int i=0;i<title.length;i++){
//            sheet.setColumnWidth(i, 6000);
//            cell = row.createCell(i);
//            cell.setCellValue(title[i]);
//            XSSFFont font = wb.createFont();
//            font.setFontName("黑体");
//            font.setFontHeightInPoints((short) 15);//设置字体大小
//            style.setFont(font);
//            cell.setCellStyle(style);
//        }
//        
//        XSSFCellStyle styleCon = wb.createCellStyle();
//        styleCon.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//        styleCon.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        
//        for(int i=0;i<list.size();i++){
//        	
//        	row = sheet.createRow(i + 1);
//            row.setHeight((short) 550);
//            
//            ProductResp product = list.get(i);
//            
//            //将内容按顺序赋给对应的列对象
//            XSSFDrawing patriarch = sheet.createDrawingPatriarch();
//            
//            String base64 = product.getImageBase64();
//
//            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//            BufferedImage bufferImg = base64ToBufferedImage(base64);
//            
//            if (bufferImg != null) {
//            	ImageIO.write(bufferImg, "png", byteArrayOut);   
//                XSSFClientAnchor anchor = new XSSFClientAnchor(480, 30, 700, 250,
//                        (short) 0, i+1, (short) 0, i+1);
//            	
//                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut
//                        .toByteArray(), XSSFWorkbook.PICTURE_TYPE_PICT));
//                
//                
//                cell = row.createCell(1);
//                cell.setCellValue(product.getName());
//                cell.setCellStyle(styleCon);
//            } else {
//            	log.info("bufferImg read failed!!!!!!!");
//            }
//        }
//        
//        return wb;
//    }
}
