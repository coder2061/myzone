package com.myzone.config;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.kabao.ext.kit.JaxbKit;
import com.kabao.ext.plugin.memcached.MemcachedKit;
import com.kabao.ext.plugin.memcached.MemcachedPlugin;
import com.kabao.ext.plugin.sqlinxml.SqlInXmlPlugin;
import com.kabao.ext.plugin.sqlinxml.SqlKit;
import com.myzone.controller.IndexController;
import com.myzone.controller.MemberController;
import com.myzone.model.Attachment;
import com.myzone.model.Contact;
import com.myzone.model.ContactGroup;
import com.myzone.model.Log;
import com.myzone.model.Mail;
import com.myzone.model.MailInbox;
import com.myzone.model.MailOutbox;
import com.myzone.model.Member;
import com.myzone.model.Template;

/** 
* @ClassName: MyZoneConfig 
* @Description: 项目API引导式配置
* @author jiangyf
* @date 2016年1月8日 上午9:44:02  
*/
public class MyZoneConfig extends JFinalConfig {
	public static Map<String, String> ROUTES =  new HashMap<String, String>();
	public static Map<String, String> STATUS_CODE_Map = new HashMap<String, String>();

	/* (配置常量)
	 * @see com.jfinal.config.JFinalConfig#configConstant(com.jfinal.config.Constants)
	 */
	@Override
	public void configConstant(Constants me) {
		//加载少量必要配置，第一次使用use加载的配置为主配置，用PropKit.get(key)获取值
		PropKit.use("configInfo.txt");
		//开发模式配置
		me.setDevMode(PropKit.getBoolean("devMode"));
		//模板路径配置
		//me.setBaseViewPath("WEB-INF/templates/");
		//视图类型设置（默认freemarker）
		me.setViewType(ViewType.FREE_MARKER);
	}

	/* (配置访问路由，注 ："/member/index"同"/member")
	 * @see com.jfinal.config.JFinalConfig#configRoute(com.jfinal.config.Routes)
	 */
	@Override
	public void configRoute(Routes me) {
		//配置个性定制路由
		//me.add(new MyZoneRoutes());
		
		//params：定位的controller		对应的controller类		响应路径（默认为第一个参数）
		/*me.add("/", IndexController.class);
		me.add("/member", MemberController.class, "/member");*/
		
		System.out.println("----init configRoute");
	    File[] files = new File(MyZoneConfig.class.getClassLoader().getResource("").getFile()).listFiles(new FileFilter(){
	      public boolean accept(File pathname){
	        return pathname.getName().endsWith("route.xml");
	      }
	    });
	    String type;
	    String pkge;
	    try {
	    	for (File xmlfile : files) {
	  	      RouteGroup group = (RouteGroup)JaxbKit.unmarshal(xmlfile, RouteGroup.class);
	  	      type = group.type;
	  	      pkge = group.pkge;
	  	      MyZoneConfig.ROUTES.put(pkge, ("".equals(type) ? "" : type+"/"));
	  	      for (RouteItem routeItem : group.routeItems) {
	  	    	  me.add(type + routeItem.controllerkey,(Class<? extends Controller>)Class.forName(pkge+"."+routeItem.className));
	  	      }
	  	    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (配置插件，插件结构是jfinal的主要拓展方式，方便创建插件并应用到项目中)
	 * @see com.jfinal.config.JFinalConfig#configPlugin(com.jfinal.config.Plugins)
	 */
	@Override
	public void configPlugin(Plugins me) {
		System.out.println("----init configPlugin");
		
		SqlInXmlPlugin sqlInXmlPlugin = new SqlInXmlPlugin();
		me.add(sqlInXmlPlugin);
		System.out.println("----sqlinXmlPlugin plugin added");
		
		//非第一次使用use加载的配置需通过每次指定配置文件名或创建对象的方式取值
		/*Prop prop = PropKit.use("dataSource.txt");
		String jdbcUrl2 = prop.get("jdbcUrl").trim();
		String user2 = prop.get("user").trim();
		String password2 = prop.get("password").trim();*/
		
		loadPropertyFile("dataSource.txt");
		String jdbcUrl = getProperty("jdbcUrl").trim();
		String user = getProperty("user").trim();
		String password = getProperty("password").trim();
		
		//配置数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(jdbcUrl,user, password);
		//配置数据库访问插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		//设置最大空闲时间
		c3p0Plugin.setMaxIdleTime(60);
		//设置连接池容量
		c3p0Plugin.setMaxPoolSize(15);
		me.add(c3p0Plugin);
		System.out.println("----C3p0Plugin plugin added");
		
		//映射数据库表到模型
		arp.addMapping("member", "ID",  Member.class);
		arp.addMapping("log", "ID",  Log.class);
		arp.addMapping("contact", "ID",  Contact.class);
		arp.addMapping("contactgroup", "ID",  ContactGroup.class);
		arp.addMapping("mail", "ID",  Mail.class);
		arp.addMapping("mailinbox", "ID",  MailInbox.class);
		arp.addMapping("mailoutbox", "ID",  MailOutbox.class);
		arp.addMapping("template", "ID",  Template.class);
		arp.addMapping("attachment", "ID",  Attachment.class);
		me.add(arp);
		System.out.println("----ActiveRecordPlugin plugin added");
		
		/*MemcachedPlugin memcached = new MemcachedPlugin();
		me.add(memcached);
		Object object = MemcachedKit.getInstance().getObject("memcached");
		System.out.println("----Memcached plugin added; "+(object!=null?object.toString():""));*/
		
		HashMap<String, String> sqlMap = new HashMap<String, String>();
        File file = new File(SqlKit.class.getClassLoader().getResource("").getFile());
        File[] files = file.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith("sql.xml")) {
                    return true;
                }
                return false;
            }
        });
        System.out.println("----load sql resource files");
        
        try {
	        for (File xmlfile : files) {
	           System.out.println(xmlfile.getPath());
	        }
        } catch (Exception ex){
        	ex.printStackTrace();
        }
	}

	/* (配置全局拦截器,配置粒度分为：global，class，method)
	 * @see com.jfinal.config.JFinalConfig#configInterceptor(com.jfinal.config.Interceptors)
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		System.out.println("----init configInterceptor");
	}

	/* (配置处理器，处理器可接管所有web请求，对应用有完全控制权，方便实现更高层的功能拓展)
	 * @see com.jfinal.config.JFinalConfig#configHandler(com.jfinal.config.Handlers)
	 */
	@Override
	public void configHandler(Handlers me) {
		System.out.println("----init configHandler");
		me.add(new ContextPathHandler());
	}
	
	/** 
	* @Title: initStatusCodeConfig 
	* @Description: 初始化状态码
	* @return void
	* @author jiangyf   
	* @date 2016年1月8日 下午2:39:36
	*/
	public void initStatusCodeConfig(){
		System.out.println("----init StatusCodeConfig");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("status-code.properties");  
        Properties props = new Properties();  
        try {  
            props.load(is);  
            Enumeration<Object> en = props.keys();  
            while(en.hasMoreElements()){  
                String name = en.nextElement().toString();  
                String path = props.getProperty(name);  
                STATUS_CODE_Map.put(name, path);  
            }  
            is.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
}
