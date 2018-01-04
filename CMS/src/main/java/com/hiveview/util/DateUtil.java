/**   
 * @Title: DateUtil.java 
 * @Package com.hiveview.ad.server.util 
 * @Description: TODO
 * @author shihai.liu 
 * @date 2014年6月4日 下午5:02:20 
 * @version V1.0   
 */
package main.java.com.hiveview.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

/**
 * @ClassName: DateUtil
 * @Description: 时间工具
 * @author shihai.liu
 * @date 2014年6月4日 下午5:02:20
 * 
 */
public class DateUtil {

	private static ThreadLocal<DateFormat> fullDateFormatter = new ThreadLocal<DateFormat>();
	private static ThreadLocal<DateFormat> shortDateFormatter = new ThreadLocal<DateFormat>();
	private static ThreadLocal<DateFormat> dateFormatter = new ThreadLocal<DateFormat>();
	private static ThreadLocal<DateFormat> yearFormatter = new ThreadLocal<DateFormat>();
	private static ThreadLocal<DateFormat> uploadFormatter = new ThreadLocal<DateFormat>();
	private final static int MONTH_SIZE = 12;
	private final static int DAY_SECOND = 86400;
	private final static int MILLISECOND = 1000;

	/**
	 * 
	 * @Title: dateToMin
	 * @Description: 转化到分
	 * @param date
	 * @return
	 * @return String
	 * @throws
	 */
	public static String dateToMin(final Date date, String arg) {
		DateFormat df = fullDateFormatter.get();
		if (df == null) {
			if (StringUtils.isEmpty(arg)) {
				df = new SimpleDateFormat("yyyyMMddHHmm");
			} else {
				df = new SimpleDateFormat(arg);
			}
			fullDateFormatter.set(df);
		}
		return df.format(date);
	}

	// 获得当天开始的时间
	public static Timestamp getDayBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 
	 * @Title: dateToHou
	 * @Description: 转化到时
	 * @param date
	 * @return
	 * @return String
	 * @throws
	 */
	public static String dateToHou(final Date date) {
		DateFormat df = shortDateFormatter.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyyMMddHH");
			shortDateFormatter.set(df);
		}
		return df.format(date);
	}

	/**
	 * 
	 * @Title: dateToDay
	 * @Description: 转化到天
	 * @param date
	 * @return
	 * @return String
	 * @throws
	 */
	public static String dateToDay(final Date date) {
		DateFormat df = dateFormatter.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyyMMdd");
			dateFormatter.set(df);
		}
		return df.format(date);
	}

	public static String dateToString(final Date date) {
		DateFormat df = dateFormatter.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyy-MM-dd");
			dateFormatter.set(df);
		}
		return df.format(date);
	}

	/**
	 * 
	 * @Title: dateToYear
	 * @Description: 转化到年
	 * @param date
	 * @return
	 * @return String
	 * @throws
	 */
	public static String dateToYear(final Date date) {
		DateFormat df = yearFormatter.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyy");
			yearFormatter.set(df);
		}
		return df.format(date);
	}

	/**
	 * 
	 * 
	 * @Title: getYear
	 * @Description: 获取年，公元纪年
	 * @return
	 * @return Integer
	 * @throws
	 */
	public static Integer getYear() {
		return Integer.parseInt(DateUtil.dateToYear(new Date()));
	}

	public static String getFilePath() {
		DateFormat df = uploadFormatter.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/");
			uploadFormatter.set(df);
		}
		return df.format(new Date());
	}
	public static String getFilePathFormaterByDay(){
		DateFormat df = uploadFormatter.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyy/MM/dd/");
			uploadFormatter.set(df);
		}
		return df.format(new Date());
	}

	public static Date date2Str(String Str) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date date3Str(String Str) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(Str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date longToDate(long l) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(l);
		return calendar.getTime();
	}

	/**
	 * 当前时间计算X年X月X日日期
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月4日
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static DateTime getAddTime(int year, int month, int day) {
		DateTime sysTime = new DateTime();
		sysTime = sysTime.plusDays(day);
		sysTime = sysTime.plusMonths(month);
		sysTime = sysTime.plusYears(year);
		return sysTime;
	}

	/**
	 * 计算两个时间差
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月4日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getTimeDifference(String startTime, String endTime) {
		DateTime start = new DateTime(startTime);
		DateTime end = new DateTime(endTime);
		int startY = start.getYear();
		int startM = start.getMonthOfYear();
		int startD = start.getDayOfMonth();
		int startDayOfMonth = start.dayOfMonth().withMaximumValue().getDayOfMonth();
		int endY = end.getYear();
		int endM = end.getMonthOfYear();
		int endD = end.getDayOfMonth() + 1;// 处理2011-01-10到2011-01-10，认为服务为一天
		int endDayOfMonth = end.dayOfMonth().withMaximumValue().getDayOfMonth();

		StringBuilder sBuilder = new StringBuilder();
		if (end.compareTo(start) < 0) {
			return sBuilder.append("过期").toString();
		}
		int lday = endD - startD;
		if (lday < 0) {
			endM = endM - 1;
			lday = startDayOfMonth + lday;
		}
		if (lday == endDayOfMonth) {// 处理天数问题，如：2011-01-01 到 2013-12-31
									// 2年11个月31天 实际上就是3年
			endM = endM + 1;
			lday = 0;
		}
		int mos = (endY - startY) * 12 + (endM - startM);
		int lyear = mos / 12;
		int lmonth = mos % 12;
		sBuilder.append(lyear + "年");
		sBuilder.append(lmonth + "月");
		if (lday == 0) {
			sBuilder.append(lday + "天");
		} else if (lday >= 28 && (endM == 2 || endM == 3)) {
			sBuilder.append(lday - 4 + "天");
		} else {
			sBuilder.append(lday - 1 + "天");
		}
		return sBuilder.toString();
	}

	public static String getEndTime(Date startTime, int year, int month, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		cl.setTime(startTime);
		cl.add(Calendar.YEAR, year);
		cl.add(Calendar.MONTH, month);
		cl.add(Calendar.DATE, day);
		return sdf.format(cl.getTime());
	}

	/**
	 * 返回两个时间段 之间的时间差 （总年月天）
	 * 
	 * @author BigLeung
	 * @param startTime
	 * @param endTime
	 * @return int 数组 下标0=年，下标1=月，下标2=天
	 * @version 1.0
	 */
	public static int[] getYMDArray(Object startTime, Object endTime) {
		String startTimeStr = null;
		String endTimeStr = null;
		boolean isDate = false;
		if (startTime instanceof Date) {
			isDate = true;
			if (startTime == null || endTime == null) {
				return null;
			}
		} else if (startTime instanceof String) {
			startTimeStr = String.valueOf(startTime);
			endTimeStr = String.valueOf(endTime);
			if (StringUtils.isEmpty(startTimeStr)
					|| StringUtils.isEmpty(endTimeStr)) {
				return null;
			}
		} else {
			return null;
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startC = Calendar.getInstance();
		Calendar endC = Calendar.getInstance();
		try {
			if (isDate) {
				startC.setTime((Date) startTime);
				endC.setTime((Date) endTime);
			} else {
				startC.setTime(df.parse(startTimeStr));
				endC.setTime(df.parse(endTimeStr));
			}
			final int startYear = startC.get(Calendar.YEAR);
			final int startMonth = startC.get(Calendar.MONTH) + 1;
			final int startDate = startC.get(Calendar.DATE);
			final int endYear = endC.get(Calendar.YEAR);
			final int endMonth = endC.get(Calendar.MONTH) + 1;
			final int endDate = endC.get(Calendar.DATE);
			int year = endYear - startYear;
			int month = endMonth - startMonth;
			int date = endDate - startDate;
			if (year < 0) {
				return null;
			}

			int[] result = null;
			if (year >= 0 && month >= 0 && date >= 0) {
				result = new int[] { year, month, date };
				return result;
			} else {
				boolean yearChange = false;
				if (month < 0) {
					month = MONTH_SIZE - (startMonth - endMonth);
					yearChange = true;
				}

				if (date < 0) {
					month--;

					if (month < 0) {
						month = month + MONTH_SIZE;
						yearChange = true;
					}

				}
				if (yearChange) {
					year--;
				}
				result = new int[] { year, month, date };
				final long oldEndTime = endC.getTime().getTime();
				final long oldStartTime = startC.getTime().getTime();
				final long time = ((oldEndTime - oldStartTime) / MILLISECOND)
						/ DAY_SECOND;
				if (isDate) {
					startC.setTime((Date) startTime);
				} else {
					startC.setTime(df.parse(startTimeStr));
				}
				startC.add(Calendar.YEAR, result[0]);
				startC.add(Calendar.MONTH, result[1]);
				final long newStartTime = startC.getTime().getTime();
				final long newTime = ((newStartTime - oldStartTime) / MILLISECOND)
						/ DAY_SECOND;
				result[2] = Integer.valueOf(String.valueOf(time - newTime));
				return result;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;

		}

	}
	public static Date getTargetDate(int a){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, a);
		return c.getTime();
		
	}
	public static Date getMotherTargetDate(Date date ,int a){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, a);
		return c.getTime();
	}
	public static Date stringToDate(String date, int kind){
		if(date!=null&&date.trim().length()!=0){
			String k = null;
			if(kind==0){
				k = "yyyy-MM-dd";
			}else if(kind==1){
				k = "HH:mm:ss";
			}else {
				k = "yyyy-MM-dd HH:mm:ss";
			}
			Date d = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat(k);
			try {
				d = dateFormat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(dateToString(getTargetDate(-180)));
		System.out.println(dateToString(getMotherTargetDate(stringToDate("2019-07-20", 0), -760)));
		// System.out.println("getAddTime="+getAddTime(0, 9, 16));
		System.out.println("getEndTime=" + getEndTime(new DateTime("2016-05-12").toDate(),0, 9, 16));
		System.out.println("getEndTime=" + getEndTime(new DateTime("2016-05-12").toDate(),0, 9, 17));
		System.out.println("getEndTime=" + getEndTime(new DateTime("2016-05-12").toDate(),4, 0, 0));
		String a1 = "2016-05-12";
		String a2 = "2017-02-28";
		int[] ymd = getYMDArray(a1, a2);
		System.out.println(a1 + "---" + a2 + ", year=" + ymd[0] + "month=" + ymd[1] + "day=" + ymd[2]);
		String b1 = "2016-05-12";
		String b2 = "2017-03-01";
		ymd = getYMDArray(b1, b2);
		System.out.println(b1 + "---" + b2 + ", year=" + ymd[0] + "month=" + ymd[1] + "day=" + ymd[2]);
		String c1 = "2016-05-12";
		String c2 = "2020-05-12";
		ymd = getYMDArray(c1, c2);
		System.out.println(c1 + "---" + c2 + ", year=" + ymd[0] + "month=" + ymd[1] + "day=" + ymd[2]);
		String d1 = "2010-01-01";
		String d2 = "2020-02-29";
		ymd = getYMDArray(d1, d2);
		System.out.println(d1 + "---" + d2 + ", year=" + ymd[0] + "month=" + ymd[1] + "day=" + ymd[2]);
		// String e1 = "2016-10-10";
		// String e2 = "2017-11-10";
		// System.out.println(e1 + "---" + e2 + "," + getTimeDifference(e1,
		// e2));
		// // 这一种的比较特殊的情况
		// String f1 = "2016-01-01";
		// String f2 = "2018-12-31";
		// System.out.println(f1 + "---" + f2 + "," + getTimeDifference(f1,
		// f2));
		// String g1 = "2016-01-01";
		// String g2 = "2018-03-02";
		// System.out.println(g1 + "---" + g2 + "," + getTimeDifference(g1,
		// g2));
		// String h1 = "2016-01-01";
		// String h2 = "2018-01-02";
		// System.out.println(h1 + "---" + h2 + "," + getTimeDifference(h1,
		// h2));
		// System.out.println(getAddTime(0, 0,
		// 3).toString("yyyy-MM-dd HH:mm:ss"));
		// System.out.println(getDateTimeBetween("2016-01-05","2017-01-01"));
		// System.out.println(getTimeDifference("2016-2-05","2017-01-01"));
		// System.out.println(DateUtil.dateToHou(new Date()));
		// System.out.println(DateUtil.dateToMin(new Date()));
		// System.out.println(DateUtil.dateToYear(new Date()));
		// System.out.println(DateUtil.getFilePath());
		// System.out.println(DateUtil.date2Str("2018-12-31 23:59:59"));
	}

	/**
	 * 指定几个月以后的日期
	 * @param dateparm
	 * @param arg
	 * @return
	 */
	public static String dateToMoreMonth(String dateparm,int month,String arg){
		String res=null;
		SimpleDateFormat sdf = new SimpleDateFormat(arg);
		Date d=new Date();
		try {
			d = sdf.parse(dateparm);
		}catch (Exception e){
			//转化出错
			return res;
		}
		try {
			Calendar cld = Calendar.getInstance();
			cld.setTime(d);
			cld.add(Calendar.MONTH, month);
			Date d2 = cld.getTime();
			res = sdf.format(d2);
			return res;
		}catch (Exception e){
			return null;
		}
	}



}
