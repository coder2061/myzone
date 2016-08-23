package com.myzone.config;

import com.jfinal.config.Routes;
import com.myzone.controller.MemberController;

/** 
* @ClassName: MyZoneRoutes 
* @Description: 个性定制路由 
* @author jiangyf
* @date 2016年1月8日 上午9:59:55  
*/
public class MyZoneRoutes extends Routes {

	/* (优点：让文件更简洁，便于团队开发，避免多人同时修改配置文件造成版本冲突)
	 * @see com.jfinal.config.Routes#config()
	 */
	@Override
	public void config() {
		//add("/myzone/member", MemberController.class);
	}

}
