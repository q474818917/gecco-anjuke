package com.dwarf.spider.anjuke;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://{city}.fang.anjuke.com/loupan/{id}.html", pipelines={"consolePipeline"})
public class HouseDirect implements HtmlBean {

	private static final long serialVersionUID = -8900958691275782688L;
	
	@Href(click=true)
	@HtmlField(cssPath=".lp-navtabs>li:eq(1)>a")
	private String directUrl;

	public String getDirectUrl() {
		return directUrl;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}

}
