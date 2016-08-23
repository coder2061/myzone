package com.myzone.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.kabao.ext.plugin.sqlinxml.SqlKit;

/**   
 * @Title: ContactGroup.java 
 * @Package com.myzone.model 
 * @Description: 联系人分组
 * @author Jiangyf  
 * @date 2016年4月17日 下午5:04:55 
 * @version V1.0   
 */
public class ContactGroup extends Model<ContactGroup> {

	private static final long serialVersionUID = 672697720504574786L;
	
	public static final ContactGroup dao = new ContactGroup();
	
	public List<ContactGroup> getContactGroupList(){
		return ContactGroup.dao.find(SqlKit.sql("contact.getContactGroupList"));
	}

}
