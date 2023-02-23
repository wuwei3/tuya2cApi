package com.orangelabs.tuya2capi.tuya2cApi.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.enums.ExcelFormatEnum;

public class TestReader {
	
	public static void main(String[] args) {
		String filePath = "D:\\excelwenjian\\export2.xlsx";
		String filename = "export2.xlsx";
		
		String fileFormat = "xlsx";
		
		if (filename.lastIndexOf(".") > 0) {
			fileFormat = filename.substring(filename.lastIndexOf(".")+1);
		}
		System.out.println("houzui " + fileFormat);
		
		
        Workbook workbook = null;
        
        try {
            if (ExcelFormatEnum.XLS.getValue().equals(fileFormat)) {
                workbook = new HSSFWorkbook(new FileInputStream(filePath));
            } else if (ExcelFormatEnum.XLSX.getValue().equals(fileFormat)) {
                workbook = new XSSFWorkbook(new FileInputStream(filePath));
            } else {
                System.out.println("ba bi q");
            }
            
            
            
            if (workbook != null) {
            	String imagePath = "D:\\exceltupian\\";
            	Map<Integer, String> map = null;
            	
                //读取excel所有图片
                if (ExcelFormatEnum.XLS.getValue().equals(fileFormat)) {
                	map = ExcelReaderUtil.getPicturesXLS(workbook, imagePath);
                	
//                	String imagepath = "D:\\exceltupian\\b49c40aa-438b-4231-9e58-39c28e5189bd.jpeg";
//                	String base64 = ExcelReaderUtil.fileToBase64(imagepath);
//                	
//                	String filepath = "D:\\exceltupian\\test.txt";
//                	File f2 = new File(filepath);
//                	FileWriter fw = new FileWriter(f2,true);
//                	
//                	fw.write(base64);//向文件中写内容
//                	fw.flush();
//                	
//                	fw.close();
                } else {
                	//map = ExcelReaderUtil.getPicturesXLSX(workbook, imagePath);
                	
//                	String i1 = map.get(4);
//                	System.out.println("ben di de " + i1);
//                	
//                	String prefix = ExcelReaderUtil.getPrefix(i1);
//                	String base64 = ExcelReaderUtil.fileToBase64(i1);
//                	
//                	base64 = prefix + base64;
//                	
//                	String filepath = "D:\\exceltupian\\test.txt";
//                	File f2 = new File(filepath);
//                	FileWriter fw = new FileWriter(f2,true);
//                	
//                	fw.write(base64);//向文件中写内容
//                	fw.flush();
//                	
//                	fw.close();
                	
                }
                
                Sheet sheet = workbook.getSheetAt(0);
                
                Row sheetTitleRow = sheet.getRow(sheet.getFirstRowNum());
                // 取出最后一列
                short lastCellNum = sheetTitleRow.getLastCellNum();
                for (int i = 2; i < lastCellNum; i++) {
                    //取出每一列的名
                    String cellValue = sheetTitleRow.getCell(i).getStringCellValue();
                    System.out.println("lieming " + cellValue);
                }
//                int rows = sheet.getLastRowNum();
//                for (int i = 1; i < rows; i++) { // tiao guo biao tou
//                	Row row = sheet.getRow(i);
//                	System.out.println(ExcelReaderUtil.getCellValue(row.getCell(24)));
//                	
//                	String values = ExcelReaderUtil.getCellValue(row.getCell(24));
//                	
//                	List<String> list = getListWithsplt(values);
//                	if (list.size() > 0) {
//                		System.out.println(JSONObject.toJSONString(list));
//                	}
//                }
                
            }
            
// 
//            List<Shop> shopList = new ArrayList<>();
// 
//            Sheet sheet = workbook.getSheetAt(0);
//            int rows = sheet.getLastRowNum(); //读取行数
//            //行遍历 跳过表头直接从数据开始读取
//            for (int i = 1; i < sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                Shop shop = new Shop();
//                shop.setPosition(getCellValue(row.getCell(1)));
//                shop.setTitle(getCellValue(row.getCell(2)));
//                shop.setImageUrl(pictureMap.get(PicturePosition.newInstance(i, 3)));
//                shop.setCount(Integer.parseInt(getCellValue(row.getCell(4))));
//                shop.setUnit(getCellValue(row.getCell(5)));
//                shop.setSize(getCellValue(row.getCell(6)));
//                shop.setMaterial(getCellValue(row.getCell(7)));
//                shop.setRemark(getCellValue(row.getCell(8)));
//                shopList.add(shop);
//            }
//            System.out.println(shopList.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
	}
	
	private static List<String> getListWithsplt(String values) {
		List<String> list = new ArrayList<>();
		
		if (values != null && !"".equals(values)) {
			String[] sa = values.split(",");
			for (String s: sa) {
				list.add(s.trim());
			}
		}
		
		return list;
	}

}
