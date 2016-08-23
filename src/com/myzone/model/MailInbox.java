package com.myzone.model;

import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult.Status;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.kabao.ext.plugin.sqlinxml.SqlKit;
import com.myzone.tool.RegexUtils;

/**   
 * @Title: MailInbox.java 
 * @Package com.myzone.model 
 * @Description: 收件箱
 * @author Jiangyf  
 * @date 2016年4月16日 下午11:20:51 
 * @version V1.0   
 */
public class MailInbox extends Model<MailInbox> {

	private static final long serialVersionUID = -7703418327139677234L;
	
	public static final MailInbox dao = new MailInbox();
	
	public Page<MailInbox> getMailInboxList(int pageNumber, int pageSize, int MemberID,String search,int status){
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(MemberID);
		String sqlwhere = "";
		if (StrKit.notBlank(search)) {
			sqlwhere += "  AND (ml.Subject LIKE ? OR m.Name LIKE ?) ";
			params.add("%" + search + "%");
			params.add("%" + search + "%");
		}
		if (status > 0) {
			sqlwhere += "  AND mli.Status=? ";
			params.add(status);
		}
		return MailInbox.dao.paginate(pageNumber, pageSize, SqlKit.sql("mail.getMailInboxListU"), SqlKit.sql("mail.getMailInboxListD").replace("sqlwhere", sqlwhere), params.toArray());
	}

}
