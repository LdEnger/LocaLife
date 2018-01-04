package com.hiveview.action.sys;

import com.alibaba.fastjson.JSON;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.service.sys.ZoneTreeService;
import com.hiveview.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/zoneTree")
public class ZoneTreeAction {

 /*   private static String ZONETREE_URL = ApiConstants.ZONETREE_URL;*/

    private static String API_ZONE_TERR_URL_PID = ApiConstants.API_ZONE_TREE_PID;
    @RequestMapping(value = "/getProvinceList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String getProvinceList(String pid){
        byte[] rev = HttpUtils.httpGet(API_ZONE_TERR_URL_PID + pid + ".json");
        System.out.println(rev);
        String string = null;
        try {
           string = new String(rev, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return string;
    }
    @RequestMapping(value = "/getCityList",produces = { "application/json;charset=UTF-8"})
    @ResponseBody
    public String getCityList(String pid){
        byte[] rev = HttpUtils.httpGet(API_ZONE_TERR_URL_PID + pid + ".json");
        String string = null;

        try {
            if("00".equals(pid)){
                return "";
            }else {
                string = new String(rev,"utf-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return string;
    }

	@Autowired
	private ZoneTreeService zoneTreeService;
    @RequestMapping(value = "/getAllZoneTree", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public ScriptPage getAllZoneTree(AreaInfo areaInfo) {
        ScriptPage scriptPage = new ScriptPage();
        List<AreaInfo> areaInfoList = zoneTreeService.getZoneTreeList(areaInfo);
        scriptPage.setRows(areaInfoList);
        return scriptPage;
    }

    /**
     * 修改权限
     * @param areaInfo
     * @return
     */
    @RequestMapping(value = "/getZoneByUserId", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public ScriptPage getZoneByUserId(AreaInfo areaInfo) {
        ScriptPage scriptPage = new ScriptPage();
        List<AreaInfo> sysAuthList = zoneTreeService.getZoneTreeByUserId(areaInfo);
        scriptPage.setRows(sysAuthList);
        scriptPage.setTotal(sysAuthList.size());
        return scriptPage;
    }


    /**
     * 修改权限
     * @param areaInfo
     * @return
     */
    @RequestMapping(value = "/getZoneByValue", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public List<AreaInfo> getZoneByValue(AreaInfo areaInfo,HttpServletRequest req) {
    	List<AreaInfo> list = new ArrayList<AreaInfo>();
    	try{
//    		Integer fid = areaInfo.getFid();
//          Integer level = areaInfo.getLevel();
            String userID =  (String) req.getSession().getAttribute("sessionUserID");
            String roleID =  (String) req.getSession().getAttribute("sessionRoleID");
            if((!"1".equals(roleID)) && userID != null){
            	areaInfo.setUserId(Integer.valueOf(userID));
            }
            list = zoneTreeService.getZoneTreeByUserId(areaInfo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
//        String url = "";
//        if("1".equals(roleID)){
//            url = ZONETREE_URL+"?fid="+fid+"&level="+level;
//        }else {
//            url = ZONETREE_URL+"?fid="+fid+"&level="+level+"&userId="+userID;
//        }
//        String countryResultOne = HttpUtils.httpGetString(url);
//        List<AreaInfo> areaInfoList = JSONArray.parseArray(countryResultOne, AreaInfo.class);
//        //List<AreaInfo> areaInfoList = zoneTreeService.getZoneTreeByUserId(areaInfo);
        return list;
    }



}
