package com.dwarf.spider.anjuke;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("houseListPipeline")
public class HouseListPipeline implements Pipeline<HouseList>{
	private static Logger logger = LoggerFactory.getLogger(HouseListPipeline.class);
	
	private final static int PAGESIZE = 30;

	@Override
	public void process(HouseList houseList) {
		/*List<HouseBrief> briefList = houseList.getBrief();
		Map<String, String> esMap = new HashMap<>();
		if(briefList != null && briefList.size() > 0){
			for(HouseBrief brief : briefList){
				JSONObject object = new JSONObject();
				object.put("name", brief.getName());
				object.put("address", brief.getAddress());
				object.put("pic", brief.getPic());
				long price = 0;
				try{
					price = Long.parseLong(brief.getPrice());
				}catch(Exception e){
					logger.info("price convert error " + e);
				}finally{
					object.put("price", price);
				}
				object.put("time", new Date());
				object.put("city", houseList.getCity());
				object.put("source", "anjuke");
				String id = this.getNumbers(brief.getId());
				logger.info("every houseBrief line es Data id is {}, json is {}", id, object.toJSONString());
				esMap.put(id, object.toJSONString());
			}
		}
		boolean failed = ESAction.getInstance().batchInsertData(esMap, "vampire", "house");
		if(failed){
			logger.info("houseBrief into ES has error, data is {}", esMap);
		}
		*/
		
		HttpRequest currRequest = houseList.getRequest();
		logger.info("the pagesize url is {}, and the city is {}", currRequest.getUrl(), houseList.getCity());
		int currentPage = houseList.getCurrentPage();
		int totalCount = houseList.getTotalCount();
		int nextPage = currentPage + 1;
		int totalPage = totalCount / PAGESIZE + 1;
		if(nextPage <= totalPage) {
			String nextUrl = "";
			String currUrl = currRequest.getUrl();
			if(currUrl.indexOf("p") != -1) {
				nextUrl = StringUtils.replaceOnce(currUrl, "p" + currentPage, "p" + nextPage);
			} else {
				nextUrl = currUrl + "?" + "p" + nextPage;
			}
			SchedulerContext.into(currRequest.subRequest(nextUrl));
		}
	}
	
	private String getNumbers(String content) {  
       Pattern pattern = Pattern.compile("\\d+");  
       Matcher matcher = pattern.matcher(content);  
       while (matcher.find()) {  
           return matcher.group(0);  
       }  
       return "";  
    }  
	
}
