package com.dwarf.spider.anjuke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dwarf.spider.utils.ESAction;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("secondPagePipeline")
public class SecondPagePipeline implements Pipeline<SecondPage>{
	
	private static Logger logger = LoggerFactory.getLogger(SecondPagePipeline.class);
	
	@Override
	public void process(SecondPage secondPage) {
		HttpRequest request = secondPage.getRequest();
		String url = secondPage.getUrl();
		String nextUrl = url + "loupan/s?p=1";
		logger.info("secondPage url is {}", url);
		JSONObject object = new JSONObject();
		object.put("name", secondPage.getName());
		object.put("url", url);
		boolean flag = ESAction.getInstance().insertData(secondPage.getId(), object.toJSONString(), "city", "house");
		if(flag){
			logger.info("ES插入失败，失败ID为{}", secondPage.getId());
		}
		SchedulerContext.into(request.subRequest(nextUrl));
	}
	
	

}
