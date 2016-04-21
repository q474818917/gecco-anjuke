package com.geccocrawler.gecco.demo.anjuke;

import org.apache.commons.lang3.StringUtils;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("houseListPipeline")
public class HouseListPipeline implements Pipeline<HouseList>{
	
	private final static int PAGESIZE = 30;

	@Override
	public void process(HouseList houseList) {
		HttpRequest currRequest = houseList.getRequest();
		int currentPage = houseList.getCurrentPage();
		int totalCount = houseList.getTotalCount();
		int nextPage = currentPage + 1;
		int totalPage = totalCount / PAGESIZE;
		if(nextPage <= totalPage) {
			String nextUrl = "";
			String currUrl = currRequest.getUrl();
			if(currUrl.indexOf("p=") != -1) {
				nextUrl = StringUtils.replaceOnce(currUrl, "p=" + currentPage, "p=" + nextPage);
			} else {
				nextUrl = currUrl + "?" + "p=" + nextPage;
			}
			SchedulerContext.into(currRequest.subRequest(nextUrl));
		}
	}
	
}
