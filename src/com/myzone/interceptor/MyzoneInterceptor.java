package com.myzone.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**   
 * @Title: MyzoneInterceptor.java 
 * @Package com.myzone.interceptor 
 * @Description: 全局拦截器
 * @author Jiangyf  
 * @date 2016年4月16日 下午6:59:06 
 * @version V1.0   
 */
public class MyzoneInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		/*HttpServletRequest request = controller.getRequest();
		String routepath = request.getScheme()+"://" //请求协议
    			+ request.getServerName() //服务器地址
                + ":" 
                + request.getServerPort()		//端口号
                + request.getContextPath()+"/myzone/";    //项目名称
		controller.setAttr("routepath",  routepath);*/
	}

}
