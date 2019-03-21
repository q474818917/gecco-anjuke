package com.dwarf.spider.douban;

import com.alibaba.fastjson.JSON;
import com.dwarf.spider.utils.DruidUtils;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@PipelineName("bookDetailPipeline")
public class BookDetailPipeline implements Pipeline<BookDetail> {

    @Override
    public void process(BookDetail bean) {

        Document document = Jsoup.parse(bean.getInfo());
        BookDto bookDto = convertDto(document.text());
        bookDto.setTitle(bean.getTitle());
        bookDto.setBookNo(bean.getId());
        insert(bookDto);
    }

    public static void main(String[] args) {
        String html = "<span> <span class=\"pl\"> 作者</span>: <a class=\"\" href=\"/search/%E4%BD%99%E5%8D%8E\">余华</a> </span>\n" +
                "<br> \n" +
                "<span class=\"pl\">出版社:</span> 作家出版社\n" +
                "<br> \n" +
                "<span class=\"pl\">出版年:</span> 2012-8-1\n" +
                "<br> \n" +
                "<span class=\"pl\">页数:</span> 191\n" +
                "<br> \n" +
                "<span class=\"pl\">定价:</span> 20.00元\n" +
                "<br> \n" +
                "<span class=\"pl\">装帧:</span> 平装\n" +
                "<br> \n" +
                "<span class=\"pl\">丛书:</span>&nbsp;\n" +
                "<a href=\"https://book.douban.com/series/1634\">余华作品（2012版）</a>\n" +
                "<br> \n" +
                "<span class=\"pl\">ISBN:</span> 9787506365437\n" +
                "<br>";
        Document document = Jsoup.parse(html);
        System.out.println(document.text());
        BookDto bookDto = convertDto(document.text());
        System.out.println(bookDto);
    }

    private static BookDto convertDto(String text) {
        BookDto bookDto = new BookDto();
        String[] strings = text.split(" ");
        for(int i = 0; i < strings.length; i++) {
            if(strings[i].contains("作者")) {
                bookDto.setAuthor(strings[i+1]);
            }else if(strings[i].equals("出版社:")) {
                bookDto.setPublishing(strings[i+1]);
            }else if(strings[i].equals("出版年:")) {
                bookDto.setYear(strings[i+1]);
            }else if(strings[i].contains("页数")) {
                bookDto.setPageNumber(strings[i+1]);
            }else if(strings[i].contains("定价")) {
                bookDto.setPrice(strings[i+1]);
            }else if(strings[i].contains("装帧")) {
                bookDto.setLayout(strings[i+1]);
            }else if(strings[i].contains("丛书")) {
                bookDto.setSeries(strings[i+1]);
            }else if(strings[i].contains("ISBN")) {
                bookDto.setIsbn(strings[i+1]);
            }
        }

        return bookDto;
    }

    private void insert(BookDto bookDto){
        Connection connection = DruidUtils.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("insert into t_book(bookNo, author, publishing, year, price,layout,series,isbn,pageNumber,title,createTime, updateTime) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(),now())");
            ps.setString(1, bookDto.getBookNo());
            ps.setString(2, bookDto.getAuthor());
            ps.setString(3, bookDto.getPublishing());
            ps.setString(4, bookDto.getYear());
            ps.setString(5, bookDto.getPrice());
            ps.setString(6, bookDto.getLayout());
            ps.setString(7, bookDto.getSeries());
            ps.setString(8, bookDto.getIsbn());
            ps.setString(9, bookDto.getPageNumber());
            ps.setString(10, bookDto.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}
