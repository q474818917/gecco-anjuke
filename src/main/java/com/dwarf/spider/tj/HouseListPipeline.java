package com.dwarf.spider.tj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("houseListPipeline")
public class HouseListPipeline implements Pipeline<HouseList>{
	private static Logger logger = LoggerFactory.getLogger(HouseListPipeline.class);
	
	@Override
	public void process(HouseList houseList) {
		HttpRequest currRequest = houseList.getRequest();
		logger.info("the pagesize url is {}, page info is {}", currRequest.getUrl(), houseList.getPage());
		JSONObject jsonObject = JSON.parseObject(houseList.getPage());
		int currentPage = jsonObject.getIntValue("curPage");
		int nextPage = currentPage + 1;
		int totalPage = jsonObject.getIntValue("totalPage");
		if(nextPage <= totalPage) {
			String nextUrl = "";
			String currUrl = currRequest.getUrl();
			if(currUrl.indexOf("p") != -1) {
				nextUrl = StringUtils.replaceOnce(currUrl, "pg" + currentPage, "pg" + nextPage);
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
