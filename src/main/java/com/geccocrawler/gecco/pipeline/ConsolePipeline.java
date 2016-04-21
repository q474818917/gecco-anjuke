package com.geccocrawler.gecco.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.spider.SpiderBean;

@PipelineName("consolePipeline")
public class ConsolePipeline implements Pipeline<SpiderBean> {
	
	private static Logger logger = LoggerFactory.getLogger(ConsolePipeline.class);
	
	@Override
	public void process(SpiderBean bean) {
		logger.info(JSON.toJSONString(bean));
	}

}
