package com.dwarf.spider.douban;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://{city}.fang.anjuke.com/loupan/all/p{currentPage}/", pipelines={"consolePipeline", "houseListPipeline"})
public class BookList implements HtmlBean {


}
