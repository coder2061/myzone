package com.myzone.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.kabao.ext.plugin.sqlinxml.SqlKit;
import com.myzone.tool.RegexUtils;

/**   
 * @Title: Member.java 
 * @Package com.myzone.model 
 * @Description: 用户模块
 * @author jiangyf   
 * @date 2016年1月8日 上午10:48:33 
 * @version V1.0   
 */
@SuppressWarnings("rawtypes")
public class Member extends Model<Model> {
	private static final long serialVersionUID = 3445780782291092609L;
	public static final Member dao = new Member();
	
	public Member findMemberByArgs(String account) {
		List<Object> params = new ArrayList<Object>();
		String sqlWhere = "";
		if (StrKit.notBlank(account)) {
			if (RegexUtils.isMobile(account)) {
				sqlWhere = "  Mobile=? AND ";
			} if (RegexUtils.isEmail(account)) {
				sqlWhere = "  Email=? AND ";
			} else {
				sqlWhere = "  Name=? AND ";
			}
			params.add(account);
		}
		if (StrKit.notBlank(sqlWhere)) {
			sqlWhere = " WHERE " +  sqlWhere.substring(0, sqlWhere.lastIndexOf("AND"));
		}
		return (Member) Member.dao.findFirst(SqlKit.sql("member.findMemberByAccount")+sqlWhere, params.toArray());
	}
	
	public int insertMember(String account, String passwd){
		if (RegexUtils.isMobile(account)) {
			return Db.update(SqlKit.sql("member.insertMember2"),  account,  passwd,  account);
		} else {
			return Db.update(SqlKit.sql("member.insertMember"),  account,  passwd);
		}
	}
}
