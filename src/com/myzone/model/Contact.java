package com.myzone.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.kabao.ext.plugin.sqlinxml.SqlKit;
import com.myzone.tool.RegexUtils;

/**   
 * @Title: Contact.java 
 * @Package com.myzone.model 
 * @Description: 联系人
 * @author Jiangyf  
 * @date 2016年4月16日 下午11:18:32 
 * @version V1.0   
 */
public class Contact  extends Model<Contact>{

	private static final long serialVersionUID = -5806741629852628397L;
	
	public static final Contact dao = new Contact();
	
	public Page<Contact> getContactList(int pageNumber, int pageSize, int MemberID,String search,int groupID){
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(MemberID);
		String sqlwhere = "";
		if (StrKit.notBlank(search)) {
			if (RegexUtils.IsIntNumber(search)) {
				sqlwhere += "  AND (c.Mobile LIKE ? OR c.QQ LIKE ?) ";
			} else {
				sqlwhere += "  AND (c.Name LIKE ? OR m.Name LIKE ?) ";
			}
			params.add("%" + search + "%");
			params.add("%" + search + "%");
		}
		if (groupID > 0) {
			sqlwhere += "  AND c.ContactGroupID=? ";
			params.add(groupID);
		}
		return Contact.dao.paginate(pageNumber, pageSize, SqlKit.sql("contact.getContactListU"), SqlKit.sql("contact.getContactListD").replace("sqlwhere", sqlwhere), params.toArray());
	}
	
	public int updateIsDel(int id){
		return Db.update(SqlKit.sql("contact.updateIsDel"), id);
	}
	
	public int updateStatus(int id, int status){
		return Db.update(SqlKit.sql("contact.updateStatus"), status, id);
	}
	
	public List<Contact> getContactList(int MemberID, int IsLogin){
		return Contact.dao.find(SqlKit.sql("contact.getContactList2"),MemberID,IsLogin);
	}

}
