package com.myzone.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement(name = "resources")
public class ResourcesGroup {
	
	@XmlElement(name = "array")
	public List<ArrayItem> arrayItems = Lists.newArrayList();
	
}
