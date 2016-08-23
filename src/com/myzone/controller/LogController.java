package com.myzone.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.myzone.model.Log;
import com.myzone.model.Member;
import com.myzone.tool.Tools;

/**   
 * @Title: LogController.java 
 * @Package com.myzone.controller 
 * @Description: 日志管理
 * @author Jiangyf  
 * @date 2016年4月16日 下午9:02:55 
 * @version V1.0   
 */
public class LogController  extends IndexController{
	
	public void logList() {
		 int  pageNumber = getParaToInt("startPage", 1);
		 int  pageSize = getParaToInt("pageSize", 10);
		 String search = null;
		try {
			search = URLDecoder.decode(getPara("search", ""), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 Member member = (Member) getSession().getAttribute("member");
		 int memberID = member!=null?member.getInt("ID"):1;
		 Page<Log> list = Log.dao.getLogList(pageNumber, pageSize, memberID,search);
		 setAttr("list", list);
		 setAttr("search", search);
		 setAttr("titlename", "日志");
		 setAttr("routepath", getRoutepath(getRequest()));
		 setAttr("tools", Tools.useStaticFunction("com.myzone.tool.Tools"));
		 render("/WEB-INF/templates/log/logList.html");
	}
	
	public void delLog() {
		int status = 0;
		String idsStr = getPara("ids", "");
		if (StrKit.notBlank(idsStr)) {
			String [] ids = idsStr.split(",");
			for (String id : ids) {
				Log.dao.updateIsDel(Integer.parseInt(id));
			}
		} else {
			status = 1;
		}
		setAttr("status", status);
		renderJson();
	}

}
