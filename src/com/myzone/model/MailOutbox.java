package com.myzone.model;

import java.util.ArrayList;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.kabao.ext.plugin.sqlinxml.SqlKit;

/**   
 * @Title: MailOutbox.java 
 * @Package com.myzone.model 
 * @Description: 发件箱
 * @author Jiangyf  
 * @date 2016年4月16日 下午11:21:06 
 * @version V1.0   
 */
public class MailOutbox extends Model<MailOutbox> {

	private static final long serialVersionUID = -391001045467870846L;
	
	public static final MailOutbox dao = new MailOutbox();
	
	public MailOutbox findOneByMailID(int MailID) {
		return MailOutbox.dao.findFirst(SqlKit.sql("mail.findMailOutboxByMailID"), MailID);
	}
	
	public Page<MailOutbox> getMailOutboxList(int pageNumber, int pageSize, int MemberID, int type, String search,int status){
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(MemberID);
		String sqlwhere = "";
		if (type >0) {
			sqlwhere += "   AND mlo.IsDraft=1 ";
		} else {
			sqlwhere += "   AND mlo.IsDraft=0 ";
		}
		if (StrKit.notBlank(search)) {
			sqlwhere += "  AND (ml.Subject LIKE ? OR m.Name LIKE ?) ";
			params.add("%" + search + "%");
			params.add("%" + search + "%");
		}
		if (status > 0) {
			sqlwhere += "  AND mlo.Status=? ";
			params.add(status);
		}
		return MailOutbox.dao.paginate(pageNumber, pageSize, SqlKit.sql("mail.getMailOutboxListU"), SqlKit.sql("mail.getMailOutboxListD").replace("sqlwhere", sqlwhere), params.toArray());
	}

}
