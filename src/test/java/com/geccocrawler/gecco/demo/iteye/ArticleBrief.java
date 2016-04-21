package com.geccocrawler.gecco.demo.iteye;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

public class ArticleBrief implements HtmlBean {
	
	private static final long serialVersionUID = 2461736561330817591L;

	@Html
	@HtmlField(cssPath=".content>h3>a")
	private String title;
	
	@Href(click=true)
	@HtmlField(cssPath="div>.content>h3>a")
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
