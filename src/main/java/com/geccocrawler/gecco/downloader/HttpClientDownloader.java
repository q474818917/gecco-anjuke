package com.geccocrawler.gecco.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

import com.geccocrawler.gecco.request.HttpPostRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;
import com.geccocrawler.gecco.utils.UrlUtils;

import javax.net.ssl.SSLException;

/**
 * 利用httpclient下载
 *  
 * @author huchengyi
 *
 */
@com.geccocrawler.gecco.annotation.Downloader("httpClientDownloader")
public class HttpClientDownloader extends AbstractDownloader {
	
	private static Log log = LogFactory.getLog(HttpClientDownloader.class);
	
	private CloseableHttpClient httpClient;
	
	public HttpClientDownloader() {
		RequestConfig clientConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
		PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();
		syncConnectionManager.setMaxTotal(1000);
		syncConnectionManager.setDefaultMaxPerRoute(50);
		httpClient = HttpClientBuilder.create().
				setDefaultRequestConfig(clientConfig).
				setConnectionManager(syncConnectionManager).
				setRetryHandler(getRetryHandler()).
				build();
	}

	@Override
	public HttpResponse download(HttpRequest request, int timeout) throws DownloadException {
		if(log.isDebugEnabled()) {
			log.debug("downloading..." + request.getUrl());
		}
		HttpRequestBase reqObj = null;
		if(request instanceof HttpPostRequest) {//post
			HttpPostRequest post = (HttpPostRequest)request;
			reqObj = new HttpPost(post.getUrl());
			//post fields
			List<NameValuePair> fields = new ArrayList<NameValuePair>();
			for(Map.Entry<String, Object> entry : post.getFields().entrySet()) {
				NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				fields.add(nvp);
			}
			try {
				HttpEntity entity = new UrlEncodedFormEntity(fields, "UTF-8");
				((HttpEntityEnclosingRequestBase) reqObj).setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {//get
			reqObj = new HttpGet(request.getUrl());
		}
		//header
		boolean isMobile = SpiderThreadLocal.get().getEngine().isMobile();
		reqObj.addHeader("User-Agent", UserAgent.getUserAgent(isMobile));
		for(Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
			reqObj.addHeader(entry.getKey(), entry.getValue());
		}
		//request config
		RequestConfig.Builder builder = RequestConfig.custom()
		.setConnectionRequestTimeout(timeout)
		.setSocketTimeout(timeout)
		.setConnectionRequestTimeout(timeout)
		.setRedirectsEnabled(false);
		//proxy
		HttpHost proxy = Proxys.getProxy();
		if(proxy != null) {
			builder.setProxy(proxy);
		}
		reqObj.setConfig(builder.build());
		//request and response
		try {
			 HttpClientContext context = HttpClientContext.create();  
			org.apache.http.HttpResponse response = httpClient.execute(reqObj, context);
			int status = response.getStatusLine().getStatusCode();
			HttpResponse resp = new HttpResponse();
			resp.setStatus(status);
			if(status == 302 || status == 301) {
				String redirectUrl = response.getFirstHeader("Location").getValue();
				resp.setContent(UrlUtils.relative2Absolute(request.getUrl(), redirectUrl));
			} else if(status == 200) {
				HttpEntity responseEntity = response.getEntity();
				resp.setRaw(responseEntity.getContent());
				String contentType = responseEntity.getContentType().getValue();
				resp.setContentType(contentType);
				String charset = getCharset(request.getCharset(), contentType);
				resp.setCharset(charset);
				//String content = EntityUtils.toString(responseEntity, charset);
				String content = getContent(responseEntity, charset);
				resp.setContent(content);
			} else {
				//404，500等
				throw new DownloadServerException("" + status);
			}
			return resp;
		} catch (IOException e) {
			//超时等
			throw new DownloadException(e);
		} finally {
			reqObj.releaseConnection();
		}
	}
	
	@Override
	public void shutdown() {
		try {
			httpClient.close();
		} catch (IOException e) {
			httpClient = null;
		}
	}
	
	public String getContent(HttpEntity entity, String charset) throws IOException {
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        int i = (int)entity.getContentLength();
        if (i < 0) {
            i = 4096;
        }
        Reader reader = new InputStreamReader(instream, charset);
        CharArrayBuffer buffer = new CharArrayBuffer(i);
        char[] tmp = new char[1024];
        int l;
        while((l = reader.read(tmp)) != -1) {
            buffer.append(tmp, 0, l);
        }
        return buffer.toString();
    }

	private HttpRequestRetryHandler getRetryHandler() {
		return new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount > 3) {
					return false;
				}
				if (exception instanceof UnknownHostException || exception instanceof ConnectTimeoutException
						|| !(exception instanceof SSLException) || exception instanceof NoHttpResponseException) {
					return true;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				org.apache.http.HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// 如果请求被认为是幂等的，那么就重试。即重复执行不影响程序其他效果的
					return true;
				}
				return false;
			}

		};
	}
}
