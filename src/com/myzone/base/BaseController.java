package com.myzone.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.myzone.config.MyZoneConfig;

/**   
 * @Title: BaseController.java 
 * @Package com.myzone.config 
 * @Description: 系统资源
 * @author jiangyf   
 * @date 2016年1月8日 下午2:41:53 
 * @version V1.0  
 */
public abstract class BaseController extends Controller {
	/**
	 * GLOBALATTRIBUTENAME HttpRequest UUID VALUE 标识
	 */
	private String GLOBALATTRIBUTENAME;
	public String getGLOBALATTRIBUTENAME() {
		return GLOBALATTRIBUTENAME;
	}
	public void setGLOBALATTRIBUTENAME(String gLOBALATTRIBUTENAME) {
		GLOBALATTRIBUTENAME = gLOBALATTRIBUTENAME;
	}
	
	public BaseController(){
		init();
	}
	public abstract void init();
	
	/**
	 * 获取UUID
	 * @return UUID
	 */
	public String getUUID(){
		return this.getRequest().getAttribute(this.GLOBALATTRIBUTENAME)==null?"":this.getRequest().getAttribute(this.GLOBALATTRIBUTENAME).toString();
	}
	
	private String ROUTEPATH;
	public String getROUTEPATH() {
		return ROUTEPATH;
	}
	public void setROUTEPATH(String rOUTEPATH) {
		ROUTEPATH = rOUTEPATH;
	}
	
	public static String getRoutepath(HttpServletRequest request){
    	return request.getScheme()+"://" //请求协议
    			+ request.getServerName() //服务器地址
                + ":" 
                + request.getServerPort()		//端口号
                + request.getContextPath()+"/myzone/";    //项目名称
    }
	
	public static String getRequestUrl(HttpServletRequest request){
    	return request.getScheme()+"://" //请求协议
    			+ request.getServerName() //服务器地址
                + ":" 
                + request.getServerPort()		//端口号
                + request.getContextPath()      //项目名称
                + request.getServletPath()      //请求页面或其他地址
                + "?" 
                + (request.getQueryString()); 	//参数
    }
	
	public static String getRequestPath(HttpServletRequest request){
    	return  request.getContextPath()      //项目名称
                + request.getServletPath();      //请求页面或其他地址
    }
	
	/**
	 * 渲染Json返回
	 */
	public void returnJson(){
		this.removeAttr("CONTEXT_PATH");
		String origin = getRequest().getHeader("origin");
		if(!StrKit.isBlank(origin)){
			if (origin.indexOf("localhost") != -1){
				this.getResponse().addHeader("Access-Control-Allow-Origin", origin);
			}else{
				this.getResponse().addHeader("Access-Control-Allow-Origin", null);
			}
		}
		this.renderJson();
	}
	
	/**
	 * 根据状态码获取对应提示信息 
	 * @param status
	 * @return String
	 */
	public String getResult(int status){
		String statusCode = MyZoneConfig.STATUS_CODE_Map.get(status+"");
		return statusCode==null?"没有此状态的错误描述":statusCode;
	}
	
	/**
	 * 通过 url get 请求，获得接口放回值
	 * @param url
	 * @return String
	 */
	public static String getResultByHttpRequest(String url)
	{
		String rs="";
		InputStream inputStream = null;
		BufferedReader reader = null;
		HttpURLConnection con = null;
		StringBuffer html = new StringBuffer();
		try {
			String result = "";
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "text/html");
			con.setRequestProperty("Accept-Charset", "utf-8");
			con.setRequestProperty("contentType", "utf-8");
			con.setReadTimeout(1 * 60 * 1000);
			con.setConnectTimeout(1 * 60 * 1000);
			con.connect();
			inputStream = con.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((result = reader.readLine()) != null) {
				html.append(result);
				html.append("\n");
			}
			rs=html.toString();
			inputStream.close();
			reader.close();
			con.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			CloseInputStream(inputStream);
			CloseBufferedReader(reader);
			con.disconnect();
			throw new RuntimeException();
		}
		return rs;
	}
	
	/**
     * 向指定 URL发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
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
            //in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
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
	
	public static void CloseBufferedReader(BufferedReader bufferedReader){
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void CloseInputStream(InputStream inputStream){
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
