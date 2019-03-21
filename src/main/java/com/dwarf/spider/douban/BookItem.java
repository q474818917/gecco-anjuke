package com.dwarf.spider.douban;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

public class BookItem implements HtmlBean {

    private static final long serialVersionUID = 6426537173182120627L;

    @Href(click=true)
    @HtmlField(cssPath="a")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
