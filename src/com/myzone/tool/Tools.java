package com.myzone.tool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import freemarker.template.TemplateHashModel;
import freemarker.ext.beans.BeansWrapper;

public class Tools {
	
	public static TemplateHashModel useStaticFunction(String packageName)
	{
	    try
	    {
	      BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
	      TemplateHashModel staticModels = wrapper.getStaticModels();
	      TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName); //packageName为静态类全路径。例如cn.aibo.test.TestValue
	      
	      return fileStatics;
	    }
	    catch (Exception e)
	    {
	       e.printStackTrace(); 
	    }
		return null;
	 }
	
	public static int compareDate(Date d,int min)
	{
		Date date = new Date();//当前日期  
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -min);
		Date d1=calendar.getTime();
		return d.compareTo(d1);		
	}
	
	/*
	 * 判断传进来的日期是否在今天的日子里
	 */
	public static int ifInCurrentDate(Date memberCreateTime)
	{
		Date date = new Date();//当前日期  
		SimpleDateFormat currSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String sysDate=null;
		Date beginDate=null;
		Date endDate=null;
		calendar.setTime(date);
		sysDate=sdf.format(calendar.getTime());
		try {
			beginDate=currSdf.parse(sysDate+" 00:00:00");
			endDate=currSdf.parse(sysDate+" 23:59:59");
			memberCreateTime=currSdf.parse(currSdf.format(memberCreateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int returnValue=0;
		if(memberCreateTime.after(beginDate) && memberCreateTime.before(endDate))
			returnValue=1;
		return returnValue;
	}
	
	/*
	 * URL编码(Freemarker)
	 */
	public static String encode(String e_string,String method){
		String r_string = "";
		try {
			if("POST".equalsIgnoreCase(method))
				r_string =  URLEncoder.encode(e_string, "UTF-8");
			else
				r_string =  URLEncoder.encode(URLEncoder.encode(e_string, "UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return r_string;
	}
	/*
	 * URL解码(Freemarker)
	 */
	public static String decode(String d_string,String method){
		String r_string = "";
		try {
			r_string =  URLDecoder.decode(d_string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return r_string;
	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
	
}


