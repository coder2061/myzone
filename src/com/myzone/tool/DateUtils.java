package com.myzone.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**   
 * @Title: DateUtils.java 
 * @Package com.myzone.tool 
 * @Description: 日期时间操作
 * @author jiangyf   
 * @date 2016年1月8日 下午2:30:14 
 * @version V1.0   
 */
public class DateUtils {
	
	/** 
	* @Title: DateToStr 
	* @Description: 日期转字符串 
	* @param date
	* @return String
	*/
	public static String DateToStr(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(date); 
	}
	
	/** 
	* @Title: afterHours 
	* @Description: 若干小时后的时间 
	* @return Date
	*/
	public static Date afterHours(int hours){
		Date d = new Date();  
        Calendar now = Calendar.getInstance();  
        now.setTime(d); 
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hours);  
        return now.getTime();
	}
	
	/** 
	* @Title: beforeHours 
	* @Description: 若干小时前的时间
	* @param hours
	* @return Date
	*/
	public static Date beforeHours(int hours){
		Date d = new Date();  
		Calendar now = Calendar.getInstance();  
		now.setTime(d); 
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) - hours);  
		return now.getTime();
	}
	
	/**
     * @Description: 本周周一
     * @author: Jiangyf
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
		 Calendar c = Calendar.getInstance();
		 int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		 if (day_of_week == 0)
		 day_of_week = 7;
		 c.add(Calendar.DATE, -day_of_week + 1);
		 return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
    }
    
    /**
     * @Description: 本周周日
     * @author Jiangyf
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
		 Calendar c = Calendar.getInstance();
		 int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		 if (day_of_week == 0)
		 day_of_week = 7;
		 c.add(Calendar.DATE, -day_of_week + 7);
		 return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
    }
    
	/** 
	* @Title: afterTimeHours 
	* @Description: 获取给定时间N小时后的时间
	* @param date
	* @param hour
	* @return Date
	*/
	public static Date afterTimeHours(Date date, int hour) 
	{
		try  
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar now = Calendar.getInstance();  
	        now.setTime(date);  
	        now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) + hour);  
	        String strDate=SDF.format(now.getTime());
	        return SDF.parse(strDate);
		}
		catch (ParseException e)  
		{  
		    e.printStackTrace();
		}	
        return date;
	}
	
	/** 
	* @Title: afterTimeDays 
	* @Description: 获取给定时间N天后的时间
	* @param date
	* @param day
	* @return Date
	*/
	public static Date afterTimeDays(Date date, int day) {
		try  
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar now = Calendar.getInstance();  
	        now.setTime(date);  
	        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
	        String strDate=sdf.format(now.getTime())+" 00:00:00";
	        return SDF.parse(strDate);
		}
		catch (ParseException e)  
		{  
		    e.printStackTrace();
		}	
        return date;
	}
	
	/** 
	* @Title: afterTimeDaysToStr
	* @Description: 获取给定时间N天后的时间的字符串
	* @param date
	* @param day
	* @return String
	*/
	public static String afterTimeDaysToStr(Date date, int day) {
		try  
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();  
	        now.setTime(date);  
	        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
	        String strDate=sdf.format(now.getTime())+" 23:59:59";
	        return strDate;
		}
		catch (Exception e)  
		{  
		    e.printStackTrace();
		}	
        return "";
	}
	
	/** 
	* @Title: afterTimeMonths 
	* @Description: 获取给定时间N个月后的时间
	* @param date
	* @param month
	* @return Date
	*/
	public static Date afterTimeMonths(Date date, int month) {
		Calendar now = Calendar.getInstance();  
        now.setTime(date);  
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);  
        return now.getTime();
	}

}
