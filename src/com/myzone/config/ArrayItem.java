package com.myzone.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
public class ArrayItem {
	@XmlAttribute
	public String name;
	
	@XmlElement(name = "areaitem")
	public List<String> areaItems = Lists.newArrayList();
}
