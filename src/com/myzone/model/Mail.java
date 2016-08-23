package com.myzone.model;

import com.jfinal.plugin.activerecord.Model;

/**   
 * @Title: Mail.java 
 * @Package com.myzone.model 
 * @Description: 邮件内容
 * @author Jiangyf  
 * @date 2016年4月16日 下午11:20:00 
 * @version V1.0   
 */
public class Mail extends Model<Mail> {

	private static final long serialVersionUID = 1L;
	
	public static final Mail dao = new Mail();

}
