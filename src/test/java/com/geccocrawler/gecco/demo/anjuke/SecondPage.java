package com.geccocrawler.gecco.demo.anjuke;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://{second}.anjuke.com", pipelines={"consolePipeline", "secondPagePipeline"})
public class SecondPage implements HtmlBean{
	
	private static final long serialVersionUID = 8044902695773591001L;
	
	@Attr("href")
	@HtmlField(cssPath="#glbNavigation>div>ul li:eq(1)>a")
	private String url;
	
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
	
	
	
}
