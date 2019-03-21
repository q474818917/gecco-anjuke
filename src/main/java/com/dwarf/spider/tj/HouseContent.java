package com.dwarf.spider.tj;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

public class HouseContent implements HtmlBean {

	private static final long serialVersionUID = 1698745978796139403L;
	
	@RequestParameter
	private String id;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
