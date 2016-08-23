package com.myzone.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RouteItem {
	@XmlAttribute
	String controllerkey;

	@XmlAttribute
	String className;
}
