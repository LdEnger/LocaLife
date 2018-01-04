package main.java.com.hiveview.service.award;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.po.award.AwardVideo;
import com.hiveview.pay.util.HttpUtils;

/**
 * Title：AwardVideoService
 * Description：极清播控VIP拉来的数据
 * Company：hiveview.com
 * Author：周恩至
 * Email：zhouenzhi@btte.net 
 * 2016年03月25日 下午16:21:02
 */
@Service
public class AwardVideoService {
	
	public List<HashMap<String,String>> getBlueOrVipVideoList(String url,int type) throws IOException{
		List<AwardVideo> list = new ArrayList<AwardVideo>();
		String body = HttpUtils.httpGetString(url);//
		if(type==1){//1的时候是极清首映的
			list = this.getBlueList(body);
		}else{//2的时候是VIP的
			list = this.getVipList(body);
		}
		List<HashMap<String,String>> json = new ArrayList<HashMap<String,String>>();
		if(list!=null&&!list.isEmpty()){
			for (AwardVideo awardVideo : list) {
				HashMap<String,String> jb = new HashMap<String,String>();
				jb.put("id",awardVideo.getAwardVideoId()+","+awardVideo.getPartnerId()+","+awardVideo.getAwardPrice()+","+awardVideo.getExpiryTime());
				jb.put("name", awardVideo.getAwardVideoName());
				json.add(jb);
			}
		}else{
			HashMap<String,String> jb = new HashMap<String,String>();
			jb.put("id","-1");
			jb.put("name", "数据为空");
			json.add(jb);
		}
		return json;
	}
	
	private List<AwardVideo> getBlueList(String str){
		/*
		 * hll:增加源码
		 */
		int albumId=-1;
		String urlGetPriceByIds=null;
		List<AwardVideo> list = new ArrayList<AwardVideo>();
		if ("".equals(str)) {
			return null;
		} else {
			JSONObject result = JSON.parseObject(str);
			JSONObject jo = JSON.parseObject(result.getString("data"));
			JSONArray t = JSON.parseArray(jo.getString("result"));
			Iterator<Object> iterator = t.iterator();
			while (iterator.hasNext()) {
				AwardVideo awardVideo = new AwardVideo();
				JSONObject record = (JSONObject) iterator.next();
				
				/*
				 * hll:修改源码
				 */
				albumId=record.getInteger("albumId");
				awardVideo.setAwardVideoId(albumId);
				
				/*
				 * hll:增加源码
				 */
				urlGetPriceByIds=ApiConstants.BLUE_URL+"blue-ray/api/j/getPrice/"+albumId+"-2006.json";
				String jsonResult= HttpUtils.httpGetString(urlGetPriceByIds);
				if(StringUtils.isEmpty(jsonResult)){
					continue;
				}
				JSONObject joBase=JSON.parseObject(jsonResult);
				JSONObject joData=JSON.parseObject(joBase.getString("data"));
				JSONArray jaResult=JSON.parseArray(joData.getString("result"));
				if(jaResult.size()<=0){
					continue;
				}
				JSONObject joResult0=(JSONObject)jaResult.get(0);
				awardVideo.setAwardPrice((int)(joResult0.getDouble("vipPrice")*100));//获取的price单位为元,需转为分
				
				
				awardVideo.setAwardVideoName(record.getString("albumName"));
				/*hll:注释源码
				awardVideo.setAwardPrice(record.getInteger("price"));
				*/
				String partnerId = record.getString("partnerId");
				awardVideo.setPartnerId(partnerId);
				if(partnerId!=null&&!"".equals(partnerId)){
					list.add(awardVideo);
				}
			}
		}
		return list;
	}
	
	private List<AwardVideo> getVipList(String str){
		List<AwardVideo> list = new ArrayList<AwardVideo>();
		if ("".equals(str)) {
			return null;
		} else {
			JSONObject result = JSON.parseObject(str);
			JSONObject jo = JSON.parseObject(result.getString("data"));
			JSONArray t = JSON.parseArray(jo.getString("result"));
			Iterator<Object> iterator = t.iterator();
			while (iterator.hasNext()) {
				AwardVideo awardVideo = new AwardVideo();
				JSONObject record = (JSONObject) iterator.next();
				awardVideo.setAwardVideoName(record.getString("name"));
				JSONObject vo = record.getJSONObject("vipPackageContentPriceVo");
				awardVideo.setAwardVideoId(vo.getInteger("pricePkgId"));
				awardVideo.setAwardPrice((int)(vo.getDouble("vipPrice")*100));//获取的price单位为元,需转为分
				awardVideo.setPartnerId(ParamConstants.VIP_PARTNER_ID);
				awardVideo.setExpiryTime(vo.getInteger("expiryTime")/60/60/24);
				list.add(awardVideo);
			}
		}
		return list;
	}
}
