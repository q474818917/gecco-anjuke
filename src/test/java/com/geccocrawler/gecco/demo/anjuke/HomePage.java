package com.geccocrawler.gecco.demo.anjuke;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

public class HomePage implements HtmlBean {

	private static final long serialVersionUID = 5805097984515944949L;
	
	@Text
	@HtmlField(cssPath="a")
	private String name;
	
	@Href(click=true)
	@HtmlField(cssPath="a")
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
