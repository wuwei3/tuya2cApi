package com.orangelabs.tuya2capi.tuya2cApi.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;





public class DateUtil {
	
	public static final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

	public static final String datePattern = "yyyy-MM-dd";

	public static final String dateMmPattern = "yyyy-MM";
	
	public static final String datemmPattern = "yyyy-MM-dd HH:mm";
	
	public static final String dateHHPattern = "HH:mm:ss";

	public static final String dateEnglishPattern = "yyyyMMdd";

	public static final String dateChinesePattern = "yyyy年M月d日";

	public static final String dateMmChinesePattern = "M月";

	public static final String dateMdChinesePattern = "M月d日";
	
	public static final String dateMdFranchPattern = "MM/dd/yyyy";
	
	public static final String dateMdFranchPattern2 = "MM/dd/yyyy HH:mm";
	
	public static final String dateUTCPattern = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static SimpleDateFormat sdf1 = new SimpleDateFormat(dateTimePattern);

	public static SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

	public static SimpleDateFormat sdf2 = new SimpleDateFormat(dateMdChinesePattern);
	
	public static SimpleDateFormat sdfHH = new SimpleDateFormat(dateMdChinesePattern);
	
	public static SimpleDateFormat sdfutc = new SimpleDateFormat(dateHHPattern);
	
	public static Date getDateFromUTC(String utcStr) {
		Date date = null;
		try {
			date = sdfutc.parse(utcStr.substring(0,19));
		} catch (ParseException e) {
			System.out.println("transfer utc gets error, message " + e.getMessage());
		}
		
		return date;
	}

	public static final String getDate(String aMask, Date aDate) {
		if (aDate == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(aMask);
		return df.format(aDate);
	}

	// 获取昨天的日期 字符串 yyyyMMdd
	public static String getYesterday() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		long lo = date.getTime() - (long) 86400000;
		return sd.format(lo).toString();
	}

	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		return new SimpleDateFormat(aMask).parse(strDate);
	}
	
	public static final String convertStringToAnotherMask(String aMask, String strDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(aMask);
		return sdf.format(sdf.parse(strDate));
	}

	/**
	 * 取得指定日期所在周的第一天
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Monday
		return c.getTime();
	}

	/**
	 * 取得指定日期所在月的第一天
	 *
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth() throws ParseException {
		Calendar c = new GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String date = year + "-" + month + "-01";
		return convertStringToDate(datePattern, date);
	}

	/**
	 * 返回一个日期 月的第一天（String）
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonthString(Date d) {
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String date = year + "-" + month + "-01";
		return date;
	}

	/**
	 * 获取一个月份第一天加n天的日期
	 * 
	 * @param n
	 * @return
	 */
	public static String getDateByAddDays(Date date, int n) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		// 设置日历中月份的第1天
		c.set(Calendar.DAY_OF_MONTH, 1 + n);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return sdf.format(c.getTime());
	}

	/**
	 * 加n天
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDateAddDay(Date date, int n) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, n);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return date;
	}

	/**
	 * 获取一个日期 月的 最后一天（String）
	 * 
	 * @return
	 */
	public static String getEndDayOfMonthString(Date d) {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(calendar.DATE));
		// 设置日期格式
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(calendar.getTime());
		return date;
	}

	/** 获取本月最大天数 **/
	public static int getEndDayOfMonth(Date date) {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 设置日期为本月最大日期
		return calendar.getActualMaximum(calendar.DATE);
	}

	/***
	 * 根据date和间隔 获取当前月中的n个日期
	 * 
	 * @param date
	 * @param n
	 *            =5
	 * @return
	 */
	public static String[] getDaysOfMonths(Date date, int n) {
		String[] months = new String[6];
		// 获取本月第一天
		months[0] = DateUtil.getFirstDayOfMonthString(date);
		months[1] = getDateByAddDays(date, n);
		months[2] = getDateByAddDays(date, n * 2);
		months[3] = getDateByAddDays(date, n * 3);
		months[4] = getDateByAddDays(date, n * 4);
		// 获取本月最后一天
		months[5] = DateUtil.getEndDayOfMonthString(date);
		return months;
	}

	/**
	 * 取得当前日期
	 *
	 * @throws ParseException
	 */
	public static int getCurrentDay() throws ParseException {
		Calendar c = new GregorianCalendar();
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 取得本周第一天期
	 *
	 * @throws ParseException
	 */
	public static Date getFirstDay() throws ParseException {
		Date data = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		Date firstDay = null;
		if (weekDay == 1) {
			Date lastDay = new Date(data.getTime() - 24 * 3600 * 1000);
			c.setTime(lastDay);
			firstDay = getFirstDayOfWeek(lastDay);
		} else {
			Date tempDay = getFirstDayOfWeek(data);
			String strDay = DateUtil.getDate(datePattern, tempDay);
			firstDay = DateUtil.convertStringToDate(dateTimePattern, strDay + " 00:00:00");
		}
		return firstDay;
	}

	/**
	 * 当前日所在的周在本年中的周次
	 *
	 * @return
	 * @see
	 */
	public static int getWeekNumberWithYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 取得当前日在本周为第几天 ，周一为1，以此类推
	 *
	 * @return
	 * @see
	 */
	public static int getWeekDay(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int day = c.get(Calendar.DAY_OF_WEEK) - 1;

		return day > 0 ? day : day + 7;
	}

	/**
	 * 当前月份
	 *
	 * @param date
	 * @return
	 * @see
	 */
	public static int getMonthWithYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		return month + 1;
	}

	/**
	 * 指定日期在月中为第几天，从1开始，以此类推
	 *
	 * @param date
	 * @return
	 * @see
	 */
	public static int getDayWithMonth(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 取得当前周
	 *
	 * @throws ParseException
	 */
	public static Map<Integer, String> getCurrentWeekDay() throws ParseException {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		Date data = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		Date firstDay = null;
		if (weekDay == 1) {
			Date lastDay = new Date(data.getTime() - 24 * 3600 * 1000);
			c.setTime(lastDay);
			firstDay = getFirstDayOfWeek(lastDay);
		} else {
			firstDay = getFirstDayOfWeek(data);
		}
		c.setTime(firstDay);
		int days = (int) ((data.getTime() - c.getTime().getTime()) / (24 * 3600 * 1000) + 1);
		for (int i = 1; i <= days; i++) {
			if (i == 1) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				map.put(day, "周一");
			} else if (i == 2) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				map.put(day, "周二");
			} else if (i == 3) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				map.put(day, "周三");
			} else if (i == 4) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				map.put(day, "周四");
			} else if (i == 5) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				map.put(day, "周五");
			} else if (i == 6) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				map.put(day, "周六");
			}
		}
		if (weekDay == 1) {
			c.setTime(data);
			int day = c.get(Calendar.DAY_OF_MONTH);
			map.put(day, "周日");
		}
		return map;
	}

	public static long getDays(Date date1, Date date2) {
		return (date1.getTime() - date2.getTime()) / (24 * 3600 * 1000);
	}

	public static Date date2date(Date date, Boolean isEnd) throws Exception {
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		String timeString = format.format(date);
		String end = " 00:00:00";
		if (isEnd) {
			end = " 23:59:59";
		}
		String dateStr = timeString + end;
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
	}

	public static Date getTimestampSeveralDaysAfter(Date today, int days) throws Exception {
		return new Date(today.getTime() + days * 24 * 3600 * 1000L);
	}

	public static Date getTimestampSeveralDaysBefore(Date today, int days) throws Exception {
		return new Date(today.getTime() - days * 24 * 3600 * 1000L);
	}

	/**
	 * 开始结束日期之间相差几天
	 */
	public static int daysBetweenStartEnd(String smdate, String bdate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 拿到开始日期，结束日期之间的所有日期
	 */
	public static String[] getDatesBetweenStartEnd(String begin, String end) throws Exception {
		int betweenDays = daysBetweenStartEnd(begin, end);

		String[] xData = new String[betweenDays + 1]; // 服务结束时间自己也必须算上

		Date beginate = sdf.parse(begin);// 定义起始日期
		Date endDate = sdf.parse(end);// 定义结束日期

		Calendar dd = Calendar.getInstance();// 定义日期实例
		dd.setTime(beginate);// 设置日期起始时间
		int j = 1;
		while (dd.getTime().before(endDate)) {// 判断是否到结束日期
			String str = sdf.format(dd.getTime());
			// 输出日期结果
			xData[j - 1] = str;
			dd.add(Calendar.DAY_OF_MONTH, 1);// 进行当前日期月份加1
			j++;
		}
		// 服务结束时间自己本身也需要被放进xdata
		String str2 = sdf.format(dd.getTime());
		dd.add(Calendar.DAY_OF_MONTH, 1);
		xData[j - 1] = str2;

		return xData;

	}

	/**
	 * 获取一个日期开始结束中间的所有月份
	 */
	public static List<Date> getMonthBetween(Date minDate, Date maxDate) throws ParseException {
		ArrayList<Date> result = new ArrayList<Date>();
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(minDate);
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(maxDate);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(curr.getTime());
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	private static List<String> getMonthBetweenString(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}
	
	public static List<String> getMonthBetweenStringEnglish(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.ENGLISH);// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}
	
	public static String getCurrentMonthStringEnglish() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
		String date = dateFormat.format(new Date());
		return date;
	}
	
	public static Date convertFrenchToChineseDate(String strDate) throws ParseException {
		String s2 = convertDate(strDate, dateMdFranchPattern, dateTimePattern);
		Date d2 = convertStringToDate(dateTimePattern, s2);
		
		return d2;
	}
	
	public static String convertChToFrDate(Date date) throws ParseException {
		String d2 = getDate(dateMdFranchPattern2, date);
		return d2;
	}
	
	public static String getLastYear() throws ParseException {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		
		c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = sdf1.format(y);
        System.out.println("过去一年："+year);
        
        return year;
	}
	
	public static String[] getLastYearBeginAndEndTime() throws ParseException{
		String[] time = new String[2];
		
		String lastyear = getLastYear();
		time[0] = lastyear + "-01-01 00:00:01";
		time[1] = lastyear + "-12-31 23:59:59";
		
		return time;
	}
	
	public static String[] getThisYearBeginAndEndTime() throws ParseException{
		String[] time = new String[2];
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		Date today = new Date();
		String todayStr = sdf1.format(today);
		
		String year = yearFormat.format(today);
		String begin = year + "-01-01 00:00:01";
		
		time[0] = begin;
		time[1] = todayStr;
		
		return time;
	}
	
	public static String getThisYear() throws ParseException{
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		Date today = new Date();
		String todayStr = yearFormat.format(today);
		return todayStr;
	}
	
	public static String getFromStringToEnglishFormat(String sDate) throws ParseException{
		Date s2 = convertFrenchToChineseDate(sDate);
		SimpleDateFormat df = new SimpleDateFormat("EEEEEEE,MMMMMMMMM dd, yyyy", Locale.ENGLISH);
		String s1 = df.format(s2);
		System.out.println("english time " + s1);
		
		return s1;
	}
	
	public static int minsBetween2(Date startTime, Date endTime) {
		Calendar cal = Calendar.getInstance();
		long time1 = 0;
		long time2 = 0;

		try {
			cal.setTime(startTime);
			time1 = cal.getTimeInMillis();
			cal.setTime(endTime);
			time2 = cal.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long between_days = (time2 - time1) / 1000;
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static void main(String[] args) throws Exception {
		Map<String, List<String>> map = getWeekNumAndWeeks(42);
		
//		List<String> dates = map.get(42);
//		for (String date: dates) {
//	    	 System.out.println("          " + date);
//	     }
		
//		for (Map.Entry<String, List<String>> m : map.entrySet()) {
//		     System.out.println("di ji zhou:" + m.getKey());
//		     
//		     List<String> dates = m.getValue();
//		     for (String date: dates) {
//		    	 System.out.println("          " + date);
//		     }
//		     
//		}
		
//		List<String> list = getNextWholeWeek();
//		for (String s : list) {
//			System.out.println(s);
//		}
		
		List<String> left = getCurrentWholeWeek();
		for (String s : left) {
			System.out.println("dd " + s);
		}
		
//		List<String> last = getLastWeelDates();
//		for (String s: last) {
//			System.out.println(s);
//		}
		
//		String reservedate = "2020-09-07";
//		Date reserve = DateUtil.convertStringToDate(DateUtil.datePattern, reservedate);
//		
//		List<String> next = DateUtil.getNextWholeWeek();
//		String nextmonday = next.get(0);
//		Date nextMon = DateUtil.convertStringToDate(DateUtil.dateTimePattern, nextmonday + " 00:00:01");
//		
//		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
//		String timeHH = sdf1.format(new Date());
//		
//		Date reserve2 = DateUtil.convertStringToDate(DateUtil.dateTimePattern, reservedate + " " + timeHH);
//		
//		boolean exceed = DateUtil.compare2Date(reserve2, nextMon);
//		System.out.println("monday " + exceed);
//		
//		String nextsunday = next.get(next.size() - 1);
//		Date nextSun = DateUtil.convertStringToDate(DateUtil.datePattern, nextsunday);
//		
//		boolean exceed2 = DateUtil.compare2Date(reserve, nextSun);
//		System.out.println("sunday " + exceed2);
		
		String reserveDate = "2021-02-08";
//		List<String> nextWeeks = DateUtil.getCurrentWholeWeek();
//		
//		Date reserve = DateUtil.convertStringToDate(DateUtil.datePattern, reserveDate);
//		String nextsunday = nextWeeks.get(nextWeeks.size() - 1);
//		Date nextSun = DateUtil.convertStringToDate(DateUtil.datePattern, nextsunday);
//		boolean exceedNextSunday = DateUtil.compare2Date(reserve, nextSun);
//		System.out.println("Exceed Next Sunday: " + exceedNextSunday);
//		
//		
//		Date reserve2 = DateUtil.convertStringToDate(DateUtil.dateTimePattern, reserveDate + " 22:00:00");
//		
//		boolean e24 = isLessThan24(new Date(), reserve2);
//		System.out.println("24 " + e24);
//		
//		boolean e11 = currentTimeExceed11(new Date());
//		System.out.println("11 " + e11);
//		
//		
//		List<String> lastweek = getLastWeelDates();
//		for (String last: lastweek) {
//			System.out.println(last);
//		}
		
	}

	/**
	 * 转换日期字符串格式
	 *
	 * @param dateStr
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 */
	public static String convertDate(String dateStr, String fromFormat, String toFormat) throws ParseException {

		return getDate(toFormat, convertStringToDate(fromFormat, dateStr));
	}

	/**
	 * 获取指定日期所在月份第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfAssignedMonth(Date activeBegin) throws ParseException {
		Calendar c = new GregorianCalendar();
		c.setTime(activeBegin);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String date = year + "-" + month + "-01";
		return convertStringToDate(datePattern, date);

	}

	public static Boolean compare2Date(Date beginDate, Date endDate){
		long beginTimes = beginDate.getTime();
		long endTimes = endDate.getTime();
		return (beginTimes - endTimes) > 0;
	}

	/**
	 * 将时间段按照月份分组，封装成Map
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Map<String, List<String>> getDateInterval(Date begin, Date end) {

		Map<String, List<String>> dateMap = new HashMap<String, List<String>>();

		// 开始日期不能大于结束日期
		if (!begin.before(end)) {
			return null;
		}

		Calendar cal_begin = Calendar.getInstance();
		cal_begin.setTime(begin);

		Calendar cal_end = Calendar.getInstance();
		cal_end.setTime(end);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		StringBuffer strbuf = new StringBuffer();
		int beginDay = 1, endDay = 1;

		Calendar cal_curr = Calendar.getInstance();

		List<String> dateList = null;

		while (true) {
			dateList = new ArrayList<>();
			if (cal_begin.get(Calendar.YEAR) == cal_end.get(Calendar.YEAR) && cal_begin.get(Calendar.MONTH) == cal_end.get(Calendar.MONTH)) {
				cal_curr.setTime(cal_begin.getTime());

				endDay = cal_end.get(Calendar.DAY_OF_MONTH);

				for (int i = beginDay; i <= endDay; i++) {
					cal_curr.set(Calendar.DAY_OF_MONTH, i);
					dateList.add(sdf.format(cal_curr.getTime()));
				}
				dateMap.put(DateUtil.formatDateByFormat(cal_curr.getTime(), "yyyy-MM"), dateList);
				break;
			}
			strbuf.append("\r\n");
			cal_curr.setTime(cal_begin.getTime());

			endDay = getLastOfMonth(cal_begin.getTime());

			for (int i = beginDay; i <= endDay; i++) {
				cal_curr.set(Calendar.DAY_OF_MONTH, i);
				dateList.add(sdf.format(cal_curr.getTime()));
			}

			cal_begin.add(Calendar.MONTH, 1);
			cal_begin.set(Calendar.DAY_OF_MONTH, 1);
			dateMap.put(DateUtil.formatDateByFormat(cal_curr.getTime(), "yyyy-MM"), dateList);
		}

		return dateMap;

	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthBegin(Date date) {
		return formatDateByFormat(date, "yyyy-MM") + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDateByFormat(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static int getLastOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static boolean isAfter9Clock() throws Exception {
		SimpleDateFormat sdf2 = new SimpleDateFormat(DateUtil.dateTimePattern);

		Date current = new Date();
		Date nine = sdf2.parse(sdf.format(current) + " 09:01:01");

		if (current.after(nine)) {
			return true;
		}
		return false;
	}
	
	
	public static List<String> getCurrentWholeWeek() throws Exception{
		List<String> weeks = new ArrayList<>();
		
		String yz_time = getTimeInterval(new Date());// 获取本周时间
		String array[] = yz_time.split(",");
		String start_time = array[0];// 本周第一天
		String end_time = array[1]; // 本周最后一天
		// 格式化日期
		Date dBegin = sdf.parse(start_time);
		Date dEnd = sdf.parse(end_time);
		List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
		for (Date date : lDate) {
			String strdate = sdf.format(date);
			//boolean h = ChineseCalendarUtils.isHoliday(strdate);
			//if (!h) {
				weeks.add(strdate);
			//}
		}
		return weeks;
	}
	
	
	public static List<String> getNextWholeWeek() throws Exception{
//		Date date = getNextMonday(new Date());
//		List<String> list = getWholeWeekForNext(date);
		
//		List<String> nextweek = new ArrayList<>();
//		
//		for (String date2: list) {
//			boolean h = ChineseCalendarUtils.isHoliday(date2);
//			if (!h) {
//				nextweek.add(date2);
//			}
//		}
		
		List<String> list = getNextWeekDateList(new Date());
		
		return list;
	}
	
	public static List<String> getNextWeekDateList(Date date){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 =Calendar.getInstance();

        cal1.setTime(date);

        cal2.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal1.get(Calendar.DAY_OF_WEEK);

        if(dayWeek == 1){
            cal1.add(Calendar.DAY_OF_MONTH, 1);
            cal2.add(Calendar.DAY_OF_MONTH, 7);
        } else {
            cal1.add(Calendar.DAY_OF_MONTH, 1-dayWeek+8);
            cal2.add(Calendar.DAY_OF_MONTH, 1-dayWeek+14);
        }
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(cal1.getTime());

        List<String> dateList = new ArrayList<>();
        //别忘了，把起始日期加上
        dateList.add(getDate(datePattern, cal1.getTime()));
        // 此日期是否在指定日期之后
        while (cal2.getTime().after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(getDate(datePattern, cStart.getTime()));
        }
        return dateList;
    }
	
	public static List<String> getWholeWeekForNext(Date date) throws Exception{
		List<String> weeks = new ArrayList<>();
		
		String yz_time = getTimeInterval(date);// 获取本周时间
		String array[] = yz_time.split(",");
		String start_time = array[0];// 本周第一天
		String end_time = array[1]; // 本周最后一天
		// 格式化日期
		Date dBegin = sdf.parse(start_time);
		Date dEnd = sdf.parse(end_time);
		List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
		for (Date date2 : lDate) {
			weeks.add(sdf.format(date2));
		}
		return weeks;
	}
	//ben zhou ri qi
	public static String getTimeInterval(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		System.out.println("monday: " + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		System.out.println("sunday: " + imptimeEnd);
		return imptimeBegin + "," + imptimeEnd;
	}
	

	// huo qu shang zhou
    public static String getLastTimeInterval() {  
         Calendar calendar1 = Calendar.getInstance();  
         Calendar calendar2 = Calendar.getInstance();  
         int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;  
         int offset1 = 1 - dayOfWeek;  
         int offset2 = 7 - dayOfWeek;  
         calendar1.add(Calendar.DATE, offset1 - 7);  
         calendar2.add(Calendar.DATE, offset2 - 7);  
         // System.out.println(sdf.format(calendar1.getTime()));// last Monday  
         String lastBeginDate = sdf.format(calendar1.getTime());  
         // System.out.println(sdf.format(calendar2.getTime()));// last Sunday  
         String lastEndDate = sdf.format(calendar2.getTime());  
         return lastBeginDate + "," + lastEndDate;  
    }
	
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	
	public static List<String> getLastWeelDates() throws Exception{
		String yz_time = getLastTimeInterval();//shang zhou
        String array[]=yz_time.split(",");
        String start_time=array[0];//第一天
        String end_time=array[1];  //最后一天 
          //格式化日期 
        Date dBegin = sdf.parse(start_time);  
        Date dEnd = sdf.parse(end_time);  
        List<Date> lDate = findDates(dBegin, dEnd);//获取这周所有date
        
        List<String> lastweek = new ArrayList<>();
        for (Date date : lDate) {  
        	lastweek.add(sdf.format(date));
        }
        return lastweek;
	}
	
	// 获得下周星期一的日期
	public static Date getNextMonday(Date gmtCreate) {
		int mondayPlus = getMondayPlus(gmtCreate);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		System.out.println("monday-->  " + monday);
		return monday;
	}
	
	private static int getMondayPlus(Date gmtCreate) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(gmtCreate);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		System.out.println("dayOfWeek " + dayOfWeek);
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}
	
	
	public static Map<String, List<String>> getWeekNumAndWeeks(Integer weekNum) throws Exception{
		  Map<String, List<String>> map = new HashMap<>();
		
		  Calendar calendar = Calendar.getInstance();
		  calendar.setFirstDayOfWeek(Calendar.MONDAY);
		  
		  int weekOfYear = 0;
		  
		  if (weekNum == null) {
			  calendar.setTime(new Date());
			  weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		  } else {
			  weekOfYear = weekNum;
		  }
		  
		  int weekYear = calendar.get(Calendar.YEAR);//获得当前的年
		  calendar.setWeekDate(weekYear, weekOfYear, 2);//获得指定年的第几周的开始日期
          long starttime = calendar.getTime().getTime();//创建日期的时间该周的第一天，
          calendar.setWeekDate(weekYear, weekOfYear, 1);//获得指定年的第几周的结束日期
          long endtime = calendar.getTime().getTime();
        
          String dateStart = sdf.format(starttime);//将时间戳格式化为指定格式
          String dateEnd = sdf.format(endtime);
          
          String[] dateArr = getDatesBetweenStartEnd(dateStart, dateEnd);
          List<String> list = Arrays.asList(dateArr);
		
          map.put(String.valueOf(weekOfYear), list);
          
          return map;
	}
	
	
	
	public static final String getWeekZhCN(Date d1){
        String weekDay = "";
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
              w = 0;
        weekDay = weekDays[w];
        return weekDay;
    }
	
	public static List<String> getPastNDate(int intervals) {
		List<String> pastDaysList = new ArrayList<>();
		for (int i = 0; i < intervals; i++) {
			pastDaysList.add(getPastDate(i));
		}
		return pastDaysList;
	}

	/**
	 * 获取过去第几天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past-1);
		Date today = calendar.getTime();
		String result = sdf.format(today);
		return result;
	}
	
	public static Long getUnixTime(String date, boolean begin) throws Exception{
		if (begin) {
			date = date + " " + "08:00:00";
		} else {
			date = date + " " + "22:00:00";
		}
		Date d1 = sdf1.parse(date);
		
		return d1.getTime() / 1000;
	}
	
	public static String getWindowsTime(Long unixTime, SimpleDateFormat sd) throws Exception{
		Date d2 = new Date(unixTime * 1000);
		String t = sd.format(d2);
		return t;
	}
	
	public static List<String> getCurrentWeekLeftDays() throws Exception {
		String today = getDate(dateTimePattern, new Date());
		List<String> cureentWeek = getCurrentWholeWeek();
		
		List<String> leftDays = new ArrayList<>();
		
		int index = 0;
		for (int i = 0; i < cureentWeek.size(); i++) {
			String date = cureentWeek.get(i);
			if (today.equals(date)) {
				index = i;
				break;
			}
		}
		
		for (int i = index; i < cureentWeek.size(); i++) {
			String date = cureentWeek.get(i);
			leftDays.add(date);
		}
		return leftDays;
	}
	
	public static boolean isToday(String requestDate) {
		String today = getDate(datePattern, new Date());
		if (requestDate.equals(today)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static boolean isLessThan24(Date start, Date end) throws Exception { 
        long cha = end.getTime() - start.getTime();
        if(cha<0){
          return false; 
        }

        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result <= 24){ 
           return true; 
        }else{ 
           return false; 
        }
    }
	
	public static boolean currentTimeExceed11(Date current) throws Exception { 
        
		String latstime = "11:00:00";
		Date last11 = convertStringToDate(dateHHPattern, latstime);
		
		String curr = getDate(dateHHPattern, current);
		Date curr11 = convertStringToDate(dateHHPattern, curr);
		
		if (curr11.after(last11)) {
			return true;
		} else {
			return false;
		}
		
    }

}
