package com.geccocrawler.gecco.demo.iteye;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://www.iteye.com/news/{id}", pipelines="consolePipeline")
public class ArticleDetail implements HtmlBean{

	private static final long serialVersionUID = -5814738295521439381L;
	
	@Request
	private HttpRequest request;
	
	@RequestParameter
	private String id;
	
	@Html
	@HtmlField(cssPath=".news_main .title>h3>a")
	private String title;

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

}
