package com.dwarf.spider.anjuke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dwarf.spider.utils.ESAction;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;

@PipelineName("contentPipeline")
public class ContentPipeline implements Pipeline<HouseContent>{
	
	private static Logger logger = LoggerFactory.getLogger(HouseListPipeline.class);
	
	@Override
	public void process(HouseContent bean) {
		JSONObject object = new JSONObject();
		object.put("name", bean.getName());
		object.put("price", bean.getPrice());
		object.put("address", bean.getAddress());
		object.put("developer", bean.getDeveloper());
		object.put("green", bean.getGreen());
		object.put("openingtime", bean.getOpeningtime());
		object.put("tenement", bean.getTenement());
		object.put("tenement_price", bean.getTenement_price());
		object.put("volumn", bean.getVolumn());
		
		boolean failed = ESAction.getInstance().insertData(bean.getId(), object.toJSONString(), "vampire", "house");
		
		if(failed){
			logger.info("houseBrief into ES has error, data is {}", object.toJSONString());
		}
	}

}
