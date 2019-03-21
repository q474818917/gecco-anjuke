package com.dwarf.spider.tj;

import java.util.List;

import com.dwarf.spider.anjuke.HouseBrief;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="https://tj.lianjia.com/ershoufang/pg{currentPage}/", pipelines={"consolePipeline", "houseListPipeline"})
public class HouseList implements HtmlBean{

	private static final long serialVersionUID = 3192658614874493142L;

	@Request
	private HttpRequest request;
	
	@HtmlField(cssPath=".key-list .item-mod")
	private List<HouseContent> content;

	@Attr(value="page-data")
	@HtmlField(cssPath=".house-lst-page-box")
	private String page;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public List<HouseContent> getContent() {
		return content;
	}

	public void setContent(List<HouseContent> content) {
		this.content = content;
	}

	public static void main(String[] args) {
		HttpRequest request = new HttpGetRequest("https://tj.lianjia.com/ershoufang/pg1/");
		GeccoEngine.create()
		.classpath("com.dwarf.spider.tj")
		//开始抓取的页面地址
		.start(request)
		//.debug(true)
		//开启几个爬虫线程,线程数量最好不要大于start request数量
		.thread(1)
		//单个爬虫每次抓取完一个请求后的间隔时间
		.interval(100)
		.run();
	}
	
}
