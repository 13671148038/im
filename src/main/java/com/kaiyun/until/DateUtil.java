/*
 *@(#)DateUtil.java 
 *
 *Copyright (c) 2014 SIC-CA  LTD. All rights reserved. 
 */
package com.kaiyun.until;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public abstract class  DateUtil {

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat searchTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 
	 * @param dataFormat "yyyy-MM-dd HH:mm"
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dataFormat) throws ParseException {
		Date date = searchTime.parse(dataFormat);
		return date;
	}
	/**
	 * 
	 * @param dataFormat "yyyy-MM-dd"
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate2(String dataFormat) throws ParseException {
		Date date = sdfDay.parse(dataFormat);
		return date;
	}



	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static Date getCurrDate(){
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获得下一年的今天
	 * 
	 * @return
	 */
	public static Date getNextYear(){
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
		Date date=curr.getTime();
		return date;
	}

	public static Date getNext100Year(){
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+100);
		Date date=curr.getTime();
		return date;
	}

	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getSdfTimes() {
		return sdfTimes.format(new Date());
	}
	/**
	 * 获取yyyy—MM格式
	 */
	public static String getSdfMonth(){
		return sdfMonth.format(new Date());
	}
	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式 当前时间
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	/**
	 * 获取YYYY-MM-DD格式 指定时间
	 * @return
	 */
	public static String getDay(Date date) {
		return sdfDay.format(date);
	}

	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}
	
	public static String getWeek(){
		Calendar dayOfWeek = Calendar.getInstance();
		int i = dayOfWeek.get(Calendar.DAY_OF_WEEK);
		String[] arr = new String[]{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		return arr[i-1];
	}
	
	public static String getLastWeekDate(Date date,int week){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		int j = c.get(Calendar.DAY_OF_WEEK);
		if(j==1){
			j=7;
		}else{
			j=j-1;	
		}
		int sd = week>j?(-(7-(week-j))):(week-j);
		c.roll(Calendar.DATE,sd);
		int y = c.get(Calendar.YEAR);
		String m = c.get(Calendar.MONTH)+1+"";
		m=m.length()<2?('0'+m):m;
		String d = c.get(Calendar.DATE)+"";
		d=d.length()<2?('0'+d):d;
		int j2 = c.get(Calendar.DAY_OF_WEEK);
		return y+"-"+m+"-"+d;
	}

}
