package com.geccocrawler.gecco.demo.anjuke;

import java.util.List;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://{city}.fang.anjuke.com/loupan/s?p={currentPage}", pipelines={"consolePipeline", "houseListPipeline"})
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
	
}
