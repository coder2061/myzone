package com.myzone.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.google.common.collect.Lists;

@XmlRootElement
public class RouteGroup {
	@XmlAttribute
	String type;
	@XmlAttribute
	String pkge;
	
	@XmlElement(name = "route")
	List<RouteItem> routeItems = Lists.newArrayList();

	/*void addRoutegroup(RouteItem routeItem) {
		this.routeItems.add(routeItem);
	}*/
}
