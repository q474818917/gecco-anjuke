package com.geccocrawler.gecco.demo.iteye;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://www.iteye.com/news", pipelines={"consolePipeline", "articleListPipeline"})
public class ArticleList implements HtmlBean{
	
	private static final long serialVersionUID = 2020546583044506478L;
	
	@Request
	private HttpRequest request;
	
	@HtmlField(cssPath="#index_main .news")
	private List<ArticleBrief> brief;
	
	@Text
	@HtmlField(cssPath="#index_main .pagination>.current")
	private int currPage;
	
	public static void main(String[] args) {
		HttpRequest request = new HttpGetRequest("http://www.iteye.com/news");
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo")
		//开始抓取的页面地址
		.start(request)
		//.debug(true)
		//开启几个爬虫线程,线程数量最好不要大于start request数量
		.thread(1)
		//单个爬虫每次抓取完一个请求后的间隔时间
		.interval(2000)
		.run();
	}

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
