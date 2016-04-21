package com.geccocrawler.gecco.demo.iteye;

import java.util.List;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://www.iteye.com/news?page={page}", pipelines={"consolePipeline", "articleListPipeline"})
public class PageList extends ArticleList implements HtmlBean {
	
	private static final long serialVersionUID = 2020546583044506478L;
	
	@Request
	private HttpRequest request;
	
	@HtmlField(cssPath="#index_main .news")
	private List<ArticleBrief> brief;
	
	@Text
	@HtmlField(cssPath="#index_main .pagination>.current")
	private int currPage;
	
	public List<ArticleBrief> getBrief() {
		return brief;
	}

	public void setBrief(List<ArticleBrief> brief) {
		this.brief = brief;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	

}
