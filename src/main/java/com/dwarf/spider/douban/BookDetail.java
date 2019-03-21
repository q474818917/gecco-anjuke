package com.dwarf.spider.douban;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="https://book.douban.com/subject/{id}/", pipelines={"consolePipeline", "bookDetailPipeline"})
public class BookDetail implements HtmlBean {

    @RequestParameter
    private String id;

    @HtmlField(cssPath="#info")
    private String info;

    @Text
    @HtmlField(cssPath="#wrapper h1>span")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static void main(String[] args) {
        HttpRequest request = new HttpGetRequest("https://book.douban.com/subject/4913064/");
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
