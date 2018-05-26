package com.dwarf.spider.anjuke;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://{city}.fang.anjuke.com/loupan/all/p{currentPage}/", pipelines={"consolePipeline", "houseListPipeline"})
public class HouseList implements HtmlBean{
	
	private static final long serialVersionUID = 3192658614874493142L;

	@Request
	private HttpRequest request;
	
	@HtmlField(cssPath=".key-list .item-mod")
	private List<HouseBrief> brief;
	
	@Text
	@HtmlField(cssPath=".pagination>.curr-page")
	private int currentPage;
	
	@Text
	@HtmlField(cssPath=".list-page>.total>em")
	private int totalCount;
	
	@Text
	@HtmlField(cssPath=".sel-city a>span")
	private String city;
	
	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public List<HouseBrief> getBrief() {
		return brief;
	}

	public void setBrief(List<HouseBrief> brief) {
		this.brief = brief;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 这是只是为了测试，不是入口
	 * @param args
	 */
	public static void main(String[] args) {
		HttpRequest request = new HttpGetRequest("http://wh.fang.anjuke.com/loupan/all/p1/");
		GeccoEngine.create()
		.classpath("com.dwarf.spider.anjuke")
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
