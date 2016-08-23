package com.myzone.model;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.kabao.ext.plugin.sqlinxml.SqlKit;

/**   
 * @Title: Log.java 
 * @Package com.myzone.model 
 * @Description: 日志
 * @author Jiangyf  
 * @date 2016年4月16日 下午7:49:22 
 * @version V1.0   
 */
public class Log extends Model<Log> {

	private static final long serialVersionUID = -2962885853359544666L;
	
	public static final Log dao = new Log();
	
	public int saveLog(int LogTypeID, int AdminID, int MemberID, int MessageID, String OperIp, 
			Date date, String OperDesc, String RequestContext) {
		return Db.update(SqlKit.sql("log.saveLog"), LogTypeID,AdminID,MemberID,MessageID,
				OperIp,date,OperDesc,RequestContext);
	}
	
	public Page<Log> getLogList(int pageNumber, int pageSize, int MemberID,String search){
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(MemberID);
		String sqlwhere = "";
		if (StrKit.notBlank(search)) {
			sqlwhere += "  AND (l.OperDesc LIKE ? OR lt.TypeName LIKE ?) ";
			params.add("%" + search + "%");
			params.add("%" + search + "%");
		}
		return Log.dao.paginate(pageNumber, pageSize, SqlKit.sql("log.getLogListU"), SqlKit.sql("log.getLogListD").replace("sqlwhere", sqlwhere), params.toArray());
	}
	
	public int updateIsDel(int id){
		return Db.update(SqlKit.sql("log.updateIsDel"), id);
	}

}
