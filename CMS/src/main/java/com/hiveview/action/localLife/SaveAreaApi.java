package main.java.com.hiveview.action.localLife;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.localLife.AreaChinaInfo;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.pay.util.HttpUtils;
import com.hiveview.service.localLife.ZoneTreeService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.service.sys.ZoneService;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/api/area")
public class SaveAreaApi {
	
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private ZoneCityService zoneCityService;
    @Autowired
    private ZoneTreeService zoneTreeService;
	
	@SuppressWarnings("rawtypes")
	public static Map parseString(String str) {
		JSONObject jsonObject = JSONObject.parseObject(str);
		Map map = (Map) jsonObject;
		return map;
	}

    @RequestMapping(value = "/completAreaInfo")
    public String saveAreaTree(HttpServletRequest req){
        String countryUrl = ApiConstants.API_UTIL_AREA.replace("{0}", "00");
        System.out.println("###############更新地区信息，url="+countryUrl);
        try {
            String countryResult = HttpUtils.httpGetString(countryUrl);
            String _countryResult = parseString(countryResult).get("result").toString();
            List<AreaChinaInfo> countryList = JSONArray.parseArray(_countryResult, AreaChinaInfo.class);
            List<AreaInfo> list = new ArrayList<AreaInfo>();
            //初始化中国
            AreaInfo aInfo = new AreaInfo();
            aInfo.setFid(0);
            aInfo.setId(100);
            aInfo.setStr("中国");
            aInfo.setType(0);
            aInfo.setIs_effective(1);
            aInfo.setLevel(1);
            list.add(aInfo);
            //省
            for (AreaChinaInfo areaChinaInfo : countryList) {
                String countryId = areaChinaInfo.getCode();
                AreaInfo area = new AreaInfo();
                area.setFid(100);
                area.setId(Integer.valueOf(areaChinaInfo.getCode()));
                area.setStr(areaChinaInfo.getName());
                area.setType(areaChinaInfo.getType());
                area.setIs_effective(1);
                area.setLevel(2);
                list.add(area);
                String stateUrl = ApiConstants.API_UTIL_AREA.replace("{0}",areaChinaInfo.getCode().toString());
                try {
                    String stateResult = HttpUtils.httpGetString(stateUrl);
                    try {
                        String _stateResult = parseString(stateResult).get("result").toString();
                        List<AreaChinaInfo> stateList = JSONArray.parseArray(_stateResult, AreaChinaInfo.class);
                        for (AreaChinaInfo areaChinaInfo2 : stateList) {
                            AreaInfo area2 = new AreaInfo();
                            area2.setFid(Integer.valueOf(countryId));
                            area2.setId(Integer.valueOf(areaChinaInfo2.getCode()));
                            area2.setStr(areaChinaInfo2.getName());
                            area2.setType(areaChinaInfo2.getType());
                            area2.setIs_effective(1);
                            area2.setLevel(3);
                            list.add(area2);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if(null != list){
                if(zoneTreeService.del(new AreaInfo())>=0){
                    for(AreaInfo areaInfo : list){
                        zoneTreeService.add(areaInfo);
                    }
                    System.out.println("##################地区信息更新完成###################");
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            req.setAttribute("result", "执行失败");
            return "result";
        }
        req.setAttribute("result", "执行成功");
        return "result";
    }

}
