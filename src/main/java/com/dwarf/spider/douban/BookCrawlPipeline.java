package com.dwarf.spider.douban;

import com.dwarf.spider.anjuke.HouseList;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import org.apache.commons.lang3.StringUtils;

@PipelineName("bookCrawlPipeline")
public class BookCrawlPipeline implements Pipeline<BookCrawl> {

    private final static int PAGESIZE = 20;

    @Override
    public void process(BookCrawl bean) {
        System.out.println(bean);
        HttpRequest currRequest = bean.getRequest();

        int currentPage = bean.getCurrentPage();
        int nextPage = currentPage + 1;
        int totalPage = 500;
        if(nextPage <= totalPage) {
            String nextUrl = "";
            String currUrl = currRequest.getUrl();
            nextUrl = StringUtils.replaceOnce(currUrl, "start=" + (currentPage - 1) * PAGESIZE, "start=" + (nextPage - 1) * PAGESIZE);
            SchedulerContext.into(currRequest.subRequest(nextUrl));
        }
    }



}
