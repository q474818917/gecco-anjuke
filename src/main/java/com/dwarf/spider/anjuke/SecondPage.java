package com.dwarf.spider.anjuke;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://{id}.anjuke.com", pipelines={"consolePipeline", "secondPagePipeline"})
public class SecondPage implements HtmlBean{
	
	private static final long serialVersionUID = 8044902695773591001L;
	
	@Attr("href")
	@HtmlField(cssPath="#glbNavigation>div>ul li:eq(1)>a")
	private String url;
	
	@HtmlField(cssPath="#switch_apf_id_8>.city")
	private String name;
	
	@RequestParameter
	private String id;
	
	@Request
	private HttpRequest request;

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
