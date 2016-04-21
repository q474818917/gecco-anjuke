package com.geccocrawler.gecco.demo.anjuke;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://www.anjuke.com/sy-city.html", pipelines={"consolePipeline"})
public class Category implements HtmlBean {
	
	private static final long serialVersionUID = -3162454317004341456L;

	@Request
	private HttpRequest request;
	
	@HtmlField(cssPath=".left_side>dl>dd>a")
	private List<HomePage> leftList;
	
	/*@HtmlField(cssPath=".right_side>dl>dd>a")
	private List<HomePage> rightList;
	
	@HtmlField(cssPath="#otherCity>dl>dd>a")
	private List<HomePage> otherList;*/
	
	public static void main(String[] args) {
		HttpRequest request = new HttpGetRequest("http://www.anjuke.com/sy-city.html");
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

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	/*public List<HomePage> getRightList() {
		return rightList;
	}

	public void setRightList(List<HomePage> rightList) {
		this.rightList = rightList;
	}

	public List<HomePage> getOtherList() {
		return otherList;
	}

	public void setOtherList(List<HomePage> otherList) {
		this.otherList = otherList;
	}*/

	public List<HomePage> getLeftList() {
		return leftList;
	}

	public void setLeftList(List<HomePage> leftList) {
		this.leftList = leftList;
	}
	

}
