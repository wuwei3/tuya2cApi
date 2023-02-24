package com.orangelabs.tuya2capi.tuya2cApi.business.products.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.ResultEnums;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.enums.ExcelFormatEnum;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.req.ParamReq;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.req.ProductReq;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;
import com.orangelabs.tuya2capi.tuya2cApi.utils.ConstantUtil;
import com.orangelabs.tuya2capi.tuya2cApi.utils.ExcelReaderUtil;

@Service
public class UploadProductsExcelService {
	
	private Logger log = Logger.getLogger(UploadProductsExcelService.class);
	
	@Autowired
	private ProductService productService;
	
	public void uploadDataToDb(String fileStorepath, String imageStoreFolder, String filename) throws Exception {
		log.info("to insert data to DB, ");
		log.info("fileStorepath " + fileStorepath);
		log.info("imageStoreFolder " + imageStoreFolder);
		String fileFormat = getFileSuffix(filename);
		
		if (fileFormat != null && !"".equals(fileFormat)) {
			Workbook workbook = getWorkbook(fileStorepath, fileFormat);
			if (workbook == null) {
				throw new BussinessException(ResultEnums.BUSSINESS_ERROR, " workbook parse failed!!");
			}
			// get all pic, the start is from 1,exclude the title
			Map<Integer, String> imageMap = getImagesMap(workbook, fileFormat, imageStoreFolder);
			
	        // reader the row data
			Sheet sheet = workbook.getSheetAt(0);
			
			// get all columns name, from index 3,
			Map<Integer,String> columnNameMap = getColumnName(sheet);
			
            // get result list;
			List<ProductReq> result = getProductsList(sheet, imageMap, columnNameMap);
			
			if (result != null && result.size() > 0) {
				for (ProductReq req: result) {
					productService.createProduct(req);
				}
			
		    }
			log.info("upload end ");
	        
		} else {
			throw new BussinessException(ResultEnums.BUSSINESS_ERROR, " file is not excel!!!");
		}
		
	}
	
	private List<ProductReq> getProductsList(Sheet sheet, Map<Integer, String> imageMap, Map<Integer,String> columnNameMap) {
		List<ProductReq> result = new ArrayList<>();
		 int rows = sheet.getLastRowNum();
         for (int i = 1; i <= rows; i++) { // tiao guo biao tou
         	Row row = sheet.getRow(i);
         	ProductReq req = createProductRequest(row, imageMap, i, columnNameMap);
         	if (req != null) {
         		result.add(req);
         	}
         }
         return result;
	}
	
	
	private ProductReq createProductRequest(Row row, Map<Integer, String> imageMap, int mapIndex, Map<Integer,String> columnMap) {
		ProductReq req = new ProductReq();
		
		String name = ExcelReaderUtil.getCellValue(row.getCell(ConstantUtil.PRODUCTNAME_COLUMN_NUM));
		if (name != null && !"".equals(name)) {
			req.setName(name);
		}
		
		//req.setPrice("$0");
		
		String path = ExcelReaderUtil.getCellValue(row.getCell(ConstantUtil.PRODUCTPATH_COLUMN_NUM));
		req.setPath(dealwithpath(path));
		
		if (imageMap != null && imageMap.size() > 0) {
    		String imagepath = imageMap.get(mapIndex);
    		String base64 = tranferImg2Base64(imagepath);
    		req.setImageBase64(base64);
    	}
		
		List<ParamReq> params = getParams(row, columnMap);
		req.setParams(params);
		return req;
	}
	
	private Map<Integer,String> getColumnName(Sheet sheet) {
		Map<Integer,String> map = new HashMap<>();
		
		Row sheetTitleRow = sheet.getRow(sheet.getFirstRowNum());
        // 取出最后一列
        short lastCellNum = sheetTitleRow.getLastCellNum();
        for (int i = 3; i < lastCellNum; i++) {
            //取出每一列的名
            String cellValue = sheetTitleRow.getCell(i).getStringCellValue();
            
            map.put(i, cellValue);
        }
        
        return map;
	}
	
	private List<ParamReq> getParams(Row row, Map<Integer,String> columnMap) {
		List<ParamReq> result = new ArrayList<>();
		
		for (Map.Entry<Integer,String> entry : columnMap.entrySet()) {
	        Integer mapKey = entry.getKey();
			String keyName = columnMap.get(mapKey);
			
			if (!ConstantUtil.NODISPLAY_COLUMNS.contains(keyName)) {
				ParamReq pr = new ParamReq();
				pr.setName(keyName);
				
				String values = ExcelReaderUtil.getCellValue(row.getCell(mapKey));
				if (!ConstantUtil.DEALWITH_COLUMNS.contains(keyName)) {
					List<String> vals = getParamsValuesWithSplit(values);
					pr.setVals(vals);
				} else {
					values = values.replaceAll("\r|\n|\t", " ");
					List<String> vals = new ArrayList<>();
					vals.add(values);
					pr.setVals(vals);
				}
				result.add(pr);
			}
	    }
		
		return result;
	}
	
	private static List<String> getParamsValuesWithSplit(String values) {
		List<String> list = new ArrayList<>();
		
		if (values != null && !"".equals(values)) {
			String[] sa = values.split(",");
			for (String s: sa) {
				list.add(s.trim());
			}
		}
		
		return list;
	}
	
	private String tranferImg2Base64(String imagesPath) {
		log.info("image path " + imagesPath);
		
		String prefix = ExcelReaderUtil.getPrefix(imagesPath);
		String base64 = ExcelReaderUtil.fileToBase64(imagesPath);
		base64 = prefix + base64;
		
		return base64;
	}
	
	private Map<Integer, String> getImagesMap(Workbook workbook, String fileFormat, String imageStoreFolder) {
		Map<Integer, String> map = null;
    	
        //读取excel所有图片
        if (ExcelFormatEnum.XLS.getValue().equals(fileFormat)) {
        	map = ExcelReaderUtil.getPicturesXLS(workbook, imageStoreFolder);
        } else {
        	map = ExcelReaderUtil.getPicturesXLSX(workbook, imageStoreFolder);
        }
        
        return map;
	}
	
	private Workbook getWorkbook(String fileStorepath, String fileFormat) throws Exception{
		Workbook workbook = null;
		
		if (ExcelFormatEnum.XLS.getValue().equals(fileFormat)) {
            workbook = new HSSFWorkbook(new FileInputStream(fileStorepath));
        } else if (ExcelFormatEnum.XLSX.getValue().equals(fileFormat)) {
            workbook = new XSSFWorkbook(new FileInputStream(fileStorepath));
        } else {
           log.info("The file is not excel!!!!!");
        }
		return workbook;
	}
	
	private String getFileSuffix(String filename) {
        String fileFormat = "";
		
		if (filename.lastIndexOf(".") > 0) {
			fileFormat = filename.substring(filename.lastIndexOf(".")+1);
		}
		
		log.info("hou zui " + fileFormat);
		
		return fileFormat;
	}
	
	private String dealwithpath(String path) {
		path = path.toLowerCase();
		path = path.replaceAll(" ", "-");
		path = path.replaceAll("&", "-");
		return path;
	}

}
