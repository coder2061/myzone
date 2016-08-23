package com.myzone.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import com.jfinal.kit.StrKit;
import com.myzone.model.Log;
import com.myzone.model.Member;

/** 
* @ClassName: MemberController 
* @Description:  用户模块
* @author jiangyf
* @date 2016年1月7日 下午6:13:17  
*/
public class MemberController extends IndexController {
	public void index() {
		setAttr("routepath", getRoutepath(getRequest()));
		render("/WEB-INF/templates/index/login.html");
	}
	
	public void login(){
		int status = 0;
		String account = getPara("account", "");
		String passwd = getPara("passwd", "");
		int isRember = getParaToInt("isRember", 0);
		Member member  = Member.dao.findMemberByArgs(account);
		if (member != null) {
			if (passwd.equals(member.getStr("Passwd"))) {
				setSessionAttr("member", member);
				if (isRember > 0) {
					setCookie("account", account, 60*60);
					setCookie("passwd", passwd, 60*60);
				}
				member.set("IsLogin", 1).update();
				Log.dao.saveLog(2, 0, member.getInt("ID"), 0, getRequestPath(getRequest()), new Date(), "用户登录", getRequestUrl(getRequest()));
				redirect(getRoutepath(getRequest()) + "mail/manage");
			} else {
				status = 1;
				setAttr("account", account);
				setAttr("isRember", isRember);
				setAttr("status", status);
				setAttr("routepath", getRoutepath(getRequest()));
				render("/WEB-INF/templates/index/login.html");
			}
		}
	}
	
	public void reg(){
		setAttr("routepath", getRoutepath(getRequest()));
		render("/WEB-INF/templates/index/register.html");
	}
	
	public void register(){
		int status = -1;
		String account = getPara("account", "");
		String passwd = getPara("passwd", "");
		Member member  = Member.dao.findMemberByArgs(account);
		if (member == null) {
			int id = Member.dao.insertMember(account, passwd);
			Log.dao.saveLog(1, 0, id, 0, getRequestPath(getRequest()), new Date(), "用户注册", getRequestUrl(getRequest()));
		} else {
			status = 1;
		}
		setAttr("routepath", getRoutepath(getRequest()));
		if (status > 0) {
			setAttr("account", account);
			setAttr("status", status);
			render("/WEB-INF/templates/index/register.html");
		} else {
			render("/WEB-INF/templates/index/login.html");
		}
	}
	
	public void memberInfo(){
		Member member = (Member) getSession().getAttribute("member");
		int MemberID = member!=null?member.getInt("ID"):0;
		setAttr("member", Member.dao.findById(MemberID));
		renderJson();
	}
	
	public void editMember(){
		int status = 0;
		 String Name = null;
		 String Email = getPara("EmailID", "");
		 String Mobile = getPara("MobileID", ""); 
		 String QQ = getPara("QQID", "");
		 String Address = null;
		try {
			Name = URLDecoder.decode(getPara("NameID", ""), "UTF-8");
			Address = URLDecoder.decode(getPara("AddressID", ""), "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		 }
		Date date = new Date();
		Member member2 = (Member) getSession().getAttribute("member");
		int MemberID = member2!=null?member2.getInt("ID"):0;
		if (MemberID > 0) {
			Member member = (Member) Member.dao.findById(MemberID);
			if (StrKit.notBlank(Name)) {
				member.set("Name", Name);
			}
			if (StrKit.notBlank(Email)) {
			member.set("Email", Email);
			}
			if (StrKit.notBlank(Mobile)) {
				member.set("Mobile", Mobile);
			}
			if (StrKit.notBlank(QQ)) {
				member.set("QQ", QQ);
			}
			if (StrKit.notBlank(Address)) {
				member.set("Address", Address);
			}
			member.update();
			Log.dao.saveLog(6, 0, MemberID, 0, getRequestPath(getRequest()), date, "修改用户信息", getRequestUrl(getRequest()));
		} else {
			status = 1;
		}
		setAttr("status", status);
		redirect(getRoutepath(getRequest()) + "mail/manage");
	}
	
	public void editPasswd(){
		int status = 0;
		 String oldPasswd = getPara("oldPasswd", "");
		 String newPasswd = getPara("newPasswd", ""); 
		Date date = new Date();
		Member member2 = (Member) getSession().getAttribute("member");
		int MemberID = member2!=null?member2.getInt("ID"):0;
		if (MemberID > 0) {
			Member member = (Member) Member.dao.findById(MemberID);
			if (member != null) {
				if (member.getStr("Passwd").equalsIgnoreCase(oldPasswd)) {
					member.set("Passwd", newPasswd).update();
					Log.dao.saveLog(7, 0, MemberID, 0, getRequestPath(getRequest()), date, "修改用户密码", getRequestUrl(getRequest()));
				} else {
					status = 3;
				}
			} else {
					status = 2;
			}
		} else {
			status = 1;
		}
		setAttr("status", status);
		redirect(getRoutepath(getRequest()) + "mail/manage");
	}
	
}
