package com.dwarf.spider.douban;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Gecco(matchUrl="https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4?start={currentSize}&type=T", pipelines={"consolePipeline", "bookCrawlPipeline"})
public class BookCrawl implements HtmlBean {

    @Request
    private HttpRequest request;

    @Text
    @HtmlField(cssPath=".paginator>.thispage")
    private int currentPage;

    private int currentSize;

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @HtmlField(cssPath=".subject-list .subject-item")
    private List<BookItem> itemList;

    public List<BookItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<BookItem> itemList) {
        this.itemList = itemList;
    }

    public static void main(String[] args) {
        HttpRequest request = new HttpGetRequest("https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4?start=0&type=T");
        GeccoEngine.create()
                .classpath("com.dwarf.spider.douban")
                //开始抓取的页面地址
                .start(request)
                //.debug(true)
                //开启几个爬虫线程,线程数量最好不要大于start request数量
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(100)
                .run();
    }
}
