package com.geccocrawler.gecco.demo.anjuke;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("secondPagePipeline")
public class SecondPagePipeline implements Pipeline<SecondPage>{

	@Override
	public void process(SecondPage secondPage) {
		HttpRequest request = secondPage.getRequest();
		String url = secondPage.getUrl();
		String nextUrl = url + "loupan/s?p=1";
		SchedulerContext.into(request.subRequest(nextUrl));
	}

}
