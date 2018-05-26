package com.dwarf.spider.tj;

import com.alibaba.fastjson.JSONObject;
import com.dwarf.spider.anjuke.HouseBrief;
import com.dwarf.spider.anjuke.HouseList;
import com.dwarf.spider.utils.ESAction;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PipelineName("houseListPipeline")
public class HouseListPipeline implements Pipeline<com.dwarf.spider.anjuke.HouseList>{
	private static Logger logger = LoggerFactory.getLogger(HouseListPipeline.class);

	private final static int PAGESIZE = 30;

	@Override
	public void process(HouseList houseList) {
		HttpRequest currRequest = houseList.getRequest();
		logger.info("the pagesize url is {}, and the city is {}", currRequest.getUrl(), houseList.getCity());
		int currentPage = houseList.getCurrentPage();
		int totalCount = houseList.getTotalCount();
		int nextPage = currentPage + 1;
		int totalPage = totalCount / PAGESIZE + 1;
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
	
	private String getNumbers(String content) {  
       Pattern pattern = Pattern.compile("\\d+");  
       Matcher matcher = pattern.matcher(content);  
       while (matcher.find()) {  
           return matcher.group(0);  
       }  
       return "";  
    }  
	
}
