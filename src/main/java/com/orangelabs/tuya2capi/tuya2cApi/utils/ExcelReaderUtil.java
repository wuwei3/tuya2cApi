package com.orangelabs.tuya2capi.tuya2cApi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ExcelReaderUtil {

	// 将 file 转化为 Base64
	public static String fileToBase64(String path) {
		try {
			File file = new File(path);
			FileInputStream inputFile;
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return new BASE64Encoder().encode(buffer);
		} catch (Exception e) {
			throw new RuntimeException("file path is not valid " + e.getMessage());
		}
	}

	public static byte[] getByte(String base64) {
		byte[] buffer;
		try {
			buffer = new BASE64Decoder().decodeBuffer(base64);
		} catch (Exception e) {
			throw new RuntimeException("base64 exception or invalid " + e.getMessage());
		}

		return buffer;
	}
	
	public static String getPrefix(String imagepath) {
		String suffix = "";
		if (imagepath.lastIndexOf(".") > 0) {
			suffix = imagepath.substring(imagepath.lastIndexOf("."));
		}
		
		String prefix = "";
		if (".jpg".equalsIgnoreCase(suffix) || ".jpeg".equalsIgnoreCase(suffix) ) {
			prefix = "data:image/jpeg;base64,";
		} else if (".ico".equalsIgnoreCase(suffix)) {
			prefix = "data:image/x-icon;base64,";
		} else if (".gif".equalsIgnoreCase(suffix)) {
			prefix = "data:image/gif;base64,";
		} else if (".png".equalsIgnoreCase(suffix)) {
			prefix = "data:image/png;base64,";
		} else {
			System.out.println(("the pic suffix format is not correct!!"));
		}

		return prefix;
	}

	/**
	 * 保存图片并返回存储地址
	 *
	 * @param pic
	 * @return
	 */
	public static String printImg(PictureData pic, String path) {
		try {

			File f = new File(path);
			if (!f.exists()) {
				f.mkdir();
			}
			String ext = pic.suggestFileExtension(); // 图片格式
			String filePath = path + UUID.randomUUID().toString() + "." + ext;
			byte[] data = pic.getData();
			FileOutputStream out = new FileOutputStream(filePath);
			out.write(data);
			out.close();
			return filePath;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取Excel2007的图片
	 *
	 * @param workbook
	 */
	public static Map<Integer, String> getPicturesXLSX(Workbook workbook, String imagepath) {
		Map<Integer, String> pictureMap = new HashMap<>();
		XSSFSheet xssfSheet = (XSSFSheet) workbook.getSheetAt(0);
		for (POIXMLDocumentPart dr : xssfSheet.getRelations()) {
			if (dr instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing) dr;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					XSSFPicture pic = (XSSFPicture) shape;
					XSSFClientAnchor anchor = pic.getPreferredSize();
					CTMarker ctMarker = anchor.getFrom();
//					PicturePosition picturePosition = new PicturePosition();
//					picturePosition.setRow(ctMarker.getRow());
//					picturePosition.setCol(ctMarker.getCol());
					//pictureMap.put(picturePosition, printImg(pic.getPictureData(), imagepath));
					// hang shi dong tai, lie gu ding ,qu de shihou genju hang lai qu zhi
					pictureMap.put(ctMarker.getRow(), printImg(pic.getPictureData(), imagepath));
				}
			}
		}

		return pictureMap;
	}

	/**
	 * 获取Excel2003的图片
	 *
	 * @param workbook
	 */
	public static Map<Integer, String> getPicturesXLS(Workbook workbook, String imagepath) {
		Map<Integer, String> pictureMap = new HashMap<>();
		List<HSSFPictureData> pictures = (List<HSSFPictureData>) workbook.getAllPictures();
		HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
		if (pictures.size() != 0) {
			for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
				HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
				if (shape instanceof HSSFPicture) {
					HSSFPicture pic = (HSSFPicture) shape;
					int pictureIndex = pic.getPictureIndex() - 1;
					HSSFPictureData picData = pictures.get(pictureIndex);
//					PicturePosition picturePosition = new PicturePosition();
//					picturePosition.setRow(anchor.getRow1());
//					picturePosition.setCol(anchor.getCol1());
//					pictureMap.put(picturePosition, printImg(picData, imagepath));
					pictureMap.put(anchor.getRow1(), printImg(picData, imagepath));
				}
			}
		}

		return pictureMap;
	}

	public static String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字
			// 如果为时间格式的内容
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd
				// HH:mm:ss
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			} else {
				return new DecimalFormat("0").format(cell.getNumericCellValue());
			}
		case HSSFCell.CELL_TYPE_STRING: // 字符串
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			return cell.getBooleanCellValue() + "";
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			return cell.getCellFormula() + "";
		case HSSFCell.CELL_TYPE_BLANK: // 空值
			return "";
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			return null;
		default:
			return null;
		}
	}
}
