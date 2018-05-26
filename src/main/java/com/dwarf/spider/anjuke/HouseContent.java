package com.dwarf.spider.anjuke;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://{city}.fang.anjuke.com/loupan/canshu-{id}.html", pipelines={"consolePipeline", "contentPipeline"})
public class HouseContent implements HtmlBean {

	private static final long serialVersionUID = 1698745978796139403L;
	
	@RequestParameter
	private String id;
	
	@Text
	@HtmlField(cssPath=".can-border>ul li:eq(0)>.des")
	private String name;
	
	@Text
	@HtmlField(cssPath=".can-border>ul li:eq(2)>.des>span")
	private String price;
	
	@Text
	@HtmlField(cssPath=".can-border>ul li:eq(7)>.des")
	private String address;
	
	@Text
	@HtmlField(cssPath=".can-border>ul li:eq(5)>.des>a")
	private String developer;
	
	@Text
	@HtmlField(cssPath=".can-left>div:eq(2)>.can-border>ul li:eq(9)>.des>a")
	private String tenement;
	
	@Text
	@HtmlField(cssPath=".can-left>div:eq(2)>.can-border>ul li:eq(8)>.des")
	private String tenement_price;
	
	@Text
	@HtmlField(cssPath=".can-left>div:eq(2)>.can-border>ul li:eq(3)>.des")
	private String volumn;
	
	@Text
	@HtmlField(cssPath=".can-left>div:eq(2)>.can-border>ul li:eq(4)>.des")
	private String green;
	
	@Text
	@HtmlField(cssPath=".can-left>div:eq(1)>.can-border>ul li:eq(3)>.des")
	private String openingtime;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getTenement() {
		return tenement;
	}

	public void setTenement(String tenement) {
		this.tenement = tenement;
	}

	public String getVolumn() {
		return volumn;
	}

	public void setVolumn(String volumn) {
		this.volumn = volumn;
	}

	public String getGreen() {
		return green;
	}

	public void setGreen(String green) {
		this.green = green;
	}

	public String getOpeningtime() {
		return openingtime;
	}

	public void setOpeningtime(String openingtime) {
		this.openingtime = openingtime;
	}

	public String getTenement_price() {
		return tenement_price;
	}

	public void setTenement_price(String tenement_price) {
		this.tenement_price = tenement_price;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 这是只是为了测试，不是入口
	 * @param args
	 */
	public static void main(String[] args) {
		HttpRequest request = new HttpGetRequest("http://wx.fang.anjuke.com/loupan/canshu-239786.html");
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
