package com.dwarf.spider.douban;

import com.alibaba.fastjson.JSONObject;
import com.dwarf.spider.utils.DruidUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:65.0) Gecko/20100101 Firefox/65.0");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            httpPost.setHeader("Host", "www.duozhuayu.com");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.setHeader("Referer", "https://www.duozhuayu.com/sell");
            httpPost.setHeader("x-request-page", "/sell");
            httpPost.setHeader("x-request-prev-page", "/sell/add");

            httpPost.setHeader("x-request-id", "285098478399527505-1553152388143-80803");
            httpPost.setHeader("x-refer-request-id", "285098478399527505-1553152386197-71064");
            httpPost.setHeader("x-request-misc", "{\"platform\":\"browser\"}");
            httpPost.setHeader("x-timestamp", "1553152388144");
            httpPost.setHeader("x-security-key", "24921707");
            httpPost.setHeader("x-request-token", "646132756af73c13a7d1688d655fd29e6577a01755b782945d7a13d4e3264ab7737de3ba0c9bf6057e");
            httpPost.setHeader("x-user-id", "285098478399527505");
            httpPost.setHeader("content-type", "application/json");

            httpPost.setHeader("x-api-version", "0.0.11");
            httpPost.setHeader("Origin", "https://www.duozhuayu.com");
            //httpPost.setHeader("Content-Length", "24");
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("Cookie", "_ga=GA1.2.2080696959.1551431836; _gid=GA1.2.1790848076.1553134193; fish_c0=\"2|1:0|10:1553152284|7:fish_c0|24:Mjg1MDk4NDc4Mzk5NTI3NTA1|96956ee7895b2c1babbb05558d4ca46b34f3ab4037a9cc94e84230b765603d55\"; _gat=1");
            // 创建参数列表
            if (param != null) {
                //List<NameValuePair> paramList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                for (String key : param.keySet()) {
                    //paramList.add(new BasicNameValuePair(key, param.get(key)));
                    jsonObject.put(key, param.get(key));
                }
                // 模拟表单
                //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                StringEntity entity = new StringEntity(jsonObject.toJSONString(),"utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 请求的参数类型为json
     * @param url
     * @param json
     * @return
     * {username:"",pass:""}
     */
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static void main(String args[]) {
        List<String> isbnList = Lists.newArrayList();
        Connection connection = DruidUtils.getInstance().getConnection();
        ResultSet rs = DruidUtils.getInstance().getResult("select * from t_book");
        try {
            while (rs.next()) {
                isbnList.add(rs.getString("isbn"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        DruidUtils.getInstance().close();

        for(String isbn : isbnList) {
            Map<String, String> map = new HashMap<>();
            map.put("isbn", isbn);
            String content = HttpClientUtil.doPost("https://www.duozhuayu.com/api/user/books", map);
            System.out.println(content);
        }

    }

}