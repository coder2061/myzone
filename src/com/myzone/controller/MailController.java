package com.myzone.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.myzone.model.ContactGroup;
import com.myzone.model.Mail;
import com.myzone.model.MailInbox;
import com.myzone.model.MailOutbox;
import com.myzone.model.Member;
import com.myzone.tool.Tools;

/**   
 * @Title: MailController.java 
 * @Package com.myzone.controller 
 * @Description: 邮件
 * @author Jiangyf  
 * @date 2016年4月11日 下午9:55:22 
 * @version V1.0   
 */
public class MailController extends IndexController {
	public void manage(){
		 int  pageNumber = getParaToInt("startPage", 1);
		 int  pageSize = getParaToInt("pageSize", 10);
		 int  status = getParaToInt("status", 0);
		 String search = null;
		try {
			search = URLDecoder.decode(getPara("search", ""), "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		 }
		 Member member = (Member) getSession().getAttribute("member");
		 int memberID = member!=null?member.getInt("ID"):1;
		 Page<MailInbox> list = MailInbox.dao.getMailInboxList(pageNumber, pageSize, memberID, search, status);
		 setAttr("list", list);
		 setAttr("groups", ContactGroup.dao.getContactGroupList());
		 setAttr("status", status);
		 setAttr("search", search);
		 setAttr("titlename", "收件箱");
		 setAttr("routepath", getRoutepath(getRequest()));
		 setAttr("tools", Tools.useStaticFunction("com.myzone.tool.Tools"));
		 render("/WEB-INF/templates/mail/mail.html");
	}
	
	public void mailOutbox(){
		 int  type = getParaToInt("type", 0);
		 int  pageNumber = getParaToInt("startPage", 1);
		 int  pageSize = getParaToInt("pageSize", 10);
		 int  status = getParaToInt("status", 0);
		 String search = null;
		try {
			search = URLDecoder.decode(getPara("search", ""), "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		 }
		 Member member = (Member) getSession().getAttribute("member");
		 int memberID = member!=null?member.getInt("ID"):1;
		 Page<MailOutbox> list = MailOutbox.dao.getMailOutboxList(pageNumber, pageSize, memberID, type, search, status);
		 setAttr("list", list);
		 setAttr("groups", ContactGroup.dao.getContactGroupList());
		 setAttr("status", status);
		 setAttr("search", search);
		 setAttr("titlename", "收件箱");
		 setAttr("routepath", getRoutepath(getRequest()));
		 setAttr("tools", Tools.useStaticFunction("com.myzone.tool.Tools"));
		 render("/WEB-INF/templates/mail/mailOutbox.html");
	}
	
	public void  editMail(){
		 int  mailID = getParaToInt("mailID", 0);
		 int  mailtype = getParaToInt("mailtype", 0);
		 String  ReceiverID = null;
		 String  CopyerID = null;
		 String Subject = null;
		 String Content = null;
		try {
			ReceiverID = URLDecoder.decode(getPara("ReceiverID", ""), "UTF-8");
			CopyerID = URLDecoder.decode(getPara("CopyerID", ""), "UTF-8");
			Subject = URLDecoder.decode(getPara("Subject", ""), "UTF-8");
			Content = URLDecoder.decode(getPara("Content", ""), "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		 }
		Date date = new Date();
		
		if (mailID > 0) {//编辑邮件
			Mail mail = Mail.dao.findById(mailID);
			MailOutbox mailOutbox = MailOutbox.dao.findOneByMailID(mailID);
			if (StrKit.notBlank(Subject)) {
				mail.set("Subject", Subject);
			}
			if (StrKit.notBlank(Content)) {
				mail.set("Content", Content);
			}
			mail.set("UpdateTime", date);
			mail.update();
			if (mailtype  > 0) {//保存草稿
				if (StrKit.notBlank(ReceiverID)) {
					mailOutbox.set("ReceiverID", ReceiverID);
				}
				if (StrKit.notBlank(CopyerID)) {
					mailOutbox.set("CopyerID", CopyerID);
				}
				mailOutbox.set("IsDraft", 1);
			} else {//发送邮件
				if (StrKit.notBlank(ReceiverID)) {
					mailOutbox.set("ReceiverID", ReceiverID);
					if (ReceiverID.indexOf(";") > 0) {
						String[] ReceiverIDs = ReceiverID.split(";");
						for (String id : ReceiverIDs) {
							MailInbox mailInbox = new MailInbox();
							mailInbox.set("ReceiverID", id);
							mailInbox.set("MailID", mailID);
							mailInbox.set("CreateTime", date);
							mailInbox.set("ReceiveType", 0);
							mailInbox.save();
						}
					} else {
						MailInbox mailInbox = new MailInbox();
						mailInbox.set("ReceiverID", ReceiverID);
						mailInbox.set("MailID", mailID);
						mailInbox.set("CreateTime", date);
						mailInbox.set("ReceiveType", 0);
						mailInbox.save();
					}
				}
				if (StrKit.notBlank(CopyerID)) {
					mailOutbox.set("CopyerID", CopyerID);
					if (CopyerID.indexOf(";") > 0) {
						String[] CopyerIDs = CopyerID.split(";");
						for (String id : CopyerIDs) {
							MailInbox mailInbox = new MailInbox();
							mailInbox.set("ReceiverID", id);
							mailInbox.set("MailID", mailID);
							mailInbox.set("CreateTime", date);
							mailInbox.set("ReceiveType", 1);
							mailInbox.save();
						}
					} else {
						MailInbox mailInbox = new MailInbox();
						mailInbox.set("ReceiverID", ReceiverID);
						mailInbox.set("MailID", mailID);
						mailInbox.set("CreateTime", date);
						mailInbox.set("ReceiveType", 1);
						mailInbox.save();
					}
				}
			}
			mailOutbox.update();
		} else {//新增邮件
			Mail mail = new Mail();
			MailOutbox mailOutbox = new MailOutbox();
			if (StrKit.notBlank(Subject)) {
				mail.set("Subject", Subject);
			}
			if (StrKit.notBlank(Content)) {
				mail.set("Content", Content);
			}
			mail.set("CreateTime", date);
			mail.save();
			mailID = mail.getInt("ID");
			Member member = (Member) getSession().getAttribute("member");
			int memberID = member!=null?member.getInt("ID"):1;
			mailOutbox.set("SenderID", memberID);
			mailOutbox.set("MailID", mailID);
			if (mailtype  > 0) {//保存草稿
				if (StrKit.notBlank(ReceiverID)) {
					mailOutbox.set("ReceiverID", ReceiverID);
				}
				if (StrKit.notBlank(CopyerID)) {
					mailOutbox.set("CopyerID", CopyerID);
				}
				mailOutbox.set("IsDraft", 1);
			} else {//发送邮件
				if (StrKit.notBlank(ReceiverID)) {
					mailOutbox.set("ReceiverID", ReceiverID);
					if (ReceiverID.indexOf(";") > 0) {
						String[] ReceiverIDs = ReceiverID.split(";");
						for (String id : ReceiverIDs) {
							MailInbox mailInbox = new MailInbox();
							mailInbox.set("ReceiverID", id);
							mailInbox.set("MailID", mailID);
							mailInbox.set("CreateTime", date);
							mailInbox.set("ReceiveType", 0);
							mailInbox.save();
						}
					} else {
						MailInbox mailInbox = new MailInbox();
						mailInbox.set("ReceiverID", ReceiverID);
						mailInbox.set("MailID", mailID);
						mailInbox.set("CreateTime", date);
						mailInbox.set("ReceiveType", 0);
						mailInbox.save();
					}
				}
				if (StrKit.notBlank(CopyerID)) {
					mailOutbox.set("CopyerID", CopyerID);
					if (CopyerID.indexOf(";") > 0) {
						String[] CopyerIDs = CopyerID.split(";");
						for (String id : CopyerIDs) {
							MailInbox mailInbox = new MailInbox();
							mailInbox.set("ReceiverID", id);
							mailInbox.set("MailID", mailID);
							mailInbox.set("CreateTime", date);
							mailInbox.set("ReceiveType", 1);
							mailInbox.save();
						}
					} else {
						MailInbox mailInbox = new MailInbox();
						mailInbox.set("ReceiverID", ReceiverID);
						mailInbox.set("MailID", mailID);
						mailInbox.set("CreateTime", date);
						mailInbox.set("ReceiveType", 1);
						mailInbox.save();
					}
				}
			}
			mailOutbox.save();
		}
		if (mailtype  > 0) {//保存草稿
			redirect(getRoutepath(getRequest()) + "mail/manage");
		} else {//发送邮件
			redirect(getRoutepath(getRequest()) + "mail/mailOutbox?type=1");
		}
	}
}
