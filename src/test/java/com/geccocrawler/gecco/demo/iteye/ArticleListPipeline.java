package com.geccocrawler.gecco.demo.iteye;

import org.apache.commons.lang3.StringUtils;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("articleListPipeline")
public class ArticleListPipeline implements Pipeline<ArticleList>{

	@Override
	public void process(ArticleList articleList) {
		HttpRequest currRequest = articleList.getRequest();
		int currPage = articleList.getCurrPage();
		int nextPage = currPage + 1;
		int totalPage = 300;
		if(nextPage <= totalPage) {
			String nextUrl = "";
			String currUrl = currRequest.getUrl();
			if(currUrl.indexOf("page=") != -1) {
				nextUrl = StringUtils.replaceOnce(currUrl, "page=" + currPage, "page=" + nextPage);
			} else {
				nextUrl = currUrl + "?" + "page=" + nextPage;
			}
			SchedulerContext.into(currRequest.subRequest(nextUrl));
		}
	}

}
