package com.geccocrawler.gecco.demo.anjuke;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

public class HouseBrief implements HtmlBean {

	private static final long serialVersionUID = 6426537173182120627L;
	
	@Html
	@HtmlField(cssPath=".infos>.lp-name>h3>a")
	private String name;
	
	@Html
	@HtmlField(cssPath=".infos>.address>a")
	private String address;
	
	@Attr("src")
	@HtmlField(cssPath=".pic>img")
	private String pic;
	
	@Html
	@HtmlField(cssPath=".favor-pos>p>span")
	private String price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
