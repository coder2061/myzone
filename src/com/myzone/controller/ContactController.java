package com.myzone.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.myzone.model.Contact;
import com.myzone.model.ContactGroup;
import com.myzone.model.Log;
import com.myzone.model.Member;
import com.myzone.tool.Tools;

/**   
 * @Title: ContactController.java 
 * @Package com.myzone.controller 
 * @Description: 联系人管理
 * @author Jiangyf  
 * @date 2016年4月11日 下午9:57:32 
 * @version V1.0   
 */
public class ContactController extends IndexController {
	
	public void contactList() {
		 int  pageNumber = getParaToInt("startPage", 1);
		 int  pageSize = getParaToInt("pageSize", 10);
		 int  groupID = getParaToInt("groupID", 0);
		 String search = null;
		try {
			search = URLDecoder.decode(getPara("search", ""), "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		 }
		 Member member = (Member) getSession().getAttribute("member");
		 int memberID = member!=null?member.getInt("ID"):1;
		 Page<Contact> list = Contact.dao.getContactList(pageNumber, pageSize, memberID, search, groupID);
		 setAttr("list", list);
		 setAttr("groups", ContactGroup.dao.getContactGroupList());
		 setAttr("groupID", groupID);
		 setAttr("search", search);
		 setAttr("titlename", "通讯录");
		 setAttr("routepath", getRoutepath(getRequest()));
		 setAttr("tools", Tools.useStaticFunction("com.myzone.tool.Tools"));
		 render("/WEB-INF/templates/contact/contactList.html");
	}
	
	public void delContact() {
		int status = 0;
		String idsStr = getPara("ids", "");
		 Member member = (Member) getSession().getAttribute("member");
		 int memberID = member!=null?member.getInt("ID"):1;
		if (StrKit.notBlank(idsStr)) {
			String [] ids = idsStr.split(",");
			for (String id : ids) {
				Contact.dao.updateIsDel(Integer.parseInt(id));
			}
			Log.dao.saveLog(5, 0, memberID, 0, getRequestPath(getRequest()), new Date(), "联系人ID："+idsStr, getRequestUrl(getRequest()));
		} else {
			status = 1;
		}
		setAttr("status", status);
		renderJson();
	}
	
	public void contactInfo(){
		setAttr("contact", Contact.dao.findById(getParaToInt("ContactID", 0)));
		renderJson();
	}
	
	public void saveContact(){
		int  ContactID = getParaToInt("ContactID", 0);
		 String Name = null;
		 String Email = getPara("Email", "");
		 String Mobile = getPara("Mobile", ""); 
		 String QQ = getPara("QQ", "");
		 int  ContactGroupID = getParaToInt("ContactGroupID", 0);
		 String Address = null;
		try {
			Name = URLDecoder.decode(getPara("Name", ""), "UTF-8");
			Address = URLDecoder.decode(getPara("Address", ""), "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		 }
		Date date = new Date();
		Member member2 = (Member) getSession().getAttribute("member");
		 int memberID = member2!=null?member2.getInt("ID"):0;
		if (ContactID > 0) {
			Contact contact = Contact.dao.findById(ContactID);
			if (StrKit.notBlank(Name)) {
				contact.set("Name", Name);
			}
			if (StrKit.notBlank(Email)) {
				contact.set("Email", Email);
			}
			if (StrKit.notBlank(Mobile)) {
				contact.set("Mobile", Mobile);
			}
			if (StrKit.notBlank(QQ)) {
				contact.set("QQ", QQ);
			}
			contact.set("ContactGroupID", ContactGroupID);
			if (StrKit.notBlank(Address)) {
				contact.set("Address", Address);
			}
			contact.set("UpdateTime", date);
			contact.update();
			Log.dao.saveLog(4, 0, memberID, 0, getRequestPath(getRequest()), date, "联系人ID："+contact.getInt("ID"), getRequestUrl(getRequest()));
		} else {
			Contact  contact = new Contact();
			Member member = Member.dao.findMemberByArgs(Name);
			if (member != null) {
				contact.set("ContactID", member.getInt("ID"));
				contact.set("Status", 2);
			} else {
				contact.set("Status", 0);
			}
			contact.set("MemberID", memberID);
			if (StrKit.notBlank(Name)) {
				contact.set("Name", Name);
			}
			if (StrKit.notBlank(Mobile)) {
				contact.set("Mobile", Mobile);
			}
			if (StrKit.notBlank(QQ)) {
				contact.set("QQ", QQ);
			}
			contact.set("ContactGroupID", ContactGroupID);
			if (StrKit.notBlank(Address)) {
				contact.set("Address", Address);
			}
			contact.set("CreateTime", date);
			contact.save();
			Log.dao.saveLog(3, 0, memberID, 0, getRequestPath(getRequest()), date, "联系人ID："+contact.getInt("ID"), getRequestUrl(getRequest()));
		}
		redirect(getRoutepath(getRequest()) + "contact/contactList");
	}
	
	public void ajaxContactList(){
		Member member = (Member) getSession().getAttribute("member");
		 int memberID = member!=null?member.getInt("ID"):0;
		 setAttr("online", Contact.dao.getContactList(memberID, 1));
		 setAttr("offline", Contact.dao.getContactList(memberID, 0));
		 renderJson();
	}
}
