package com.hiveview.action.localLife;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.util.HttpUtils;
import com.hiveview.service.localLife.ContentService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import com.hiveview.util.FuseUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Antony
 * Date: 16-6-3
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/localLife")
public class localLifeAction {
    private static final Logger DATA = LoggerFactory.getLogger("data");
    private static String DOMAIN = ApiConstants.API_UTIL_US;
/*    private static String ZONETREE_URL = ApiConstants.ZONETREE_URL;*/

   /* @RequestMapping(value="/show")
    public String show(HttpServletRequest req,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession hsession = request.getSession();
        String level = "";
        String action = req.getParameter("action");
        String userID = req.getParameter("userId");
        String roleID = req.getParameter("roleId");
        String sign = req.getParameter("sign");
      *//*  Map<String,String> map = new HashMap<String,String>();
        map.put("userId",userID);
        map.put("roleId",roleID);
        String returnSign =  FuseUtils.getSign(map);
        System.out.println(action+"___"+roleID+"____"+userID+"____"+sign+"_________"+returnSign);
        if(!returnSign.equals(sign)){
            return "timeout";
        }*//*
        String url =  "";
        if("1".equals(roleID)){
            url = ZONETREE_URL+"?userId=&level=1";
            String countryResultOne = HttpUtils.httpGetString(url);
            List<AreaInfo> zoneCountryList = JSONArray.parseArray(countryResultOne, AreaInfo.class);
            hsession.setAttribute("zoneCountryList", zoneCountryList);
            url = ZONETREE_URL+"?userId=&level=2";
            String countryResultTwo = HttpUtils.httpGetString(url);
            List<AreaInfo> zoneProvinceList = JSONArray.parseArray(countryResultTwo, AreaInfo.class);
            hsession.setAttribute("zoneProvinceList", zoneProvinceList);
            url = ZONETREE_URL+"?userId=&level=3";
            String countryResultThree = HttpUtils.httpGetString(url);
            List<AreaInfo> zoneCityList = JSONArray.parseArray(countryResultThree, AreaInfo.class);
            hsession.setAttribute("zoneCityList", zoneCityList);
        }
        if("5".equals(roleID)){
            url = ZONETREE_URL+"?userId="+userID+"&level=1";
            String countryResultOne = HttpUtils.httpGetString(url);
            List<AreaInfo> zoneCountryList = JSONArray.parseArray(countryResultOne, AreaInfo.class);
            hsession.setAttribute("zoneCountryList", zoneCountryList);
            url = ZONETREE_URL+"?userId="+userID+"&level=2";
            String countryResultTwo = HttpUtils.httpGetString(url);
            List<AreaInfo> zoneProvinceList = JSONArray.parseArray(countryResultTwo, AreaInfo.class);
            hsession.setAttribute("zoneProvinceList", zoneProvinceList);
            url = ZONETREE_URL+"?userId="+userID+"&level=3";
            String countryResultThree = HttpUtils.httpGetString(url);
            List<AreaInfo> zoneCityList = JSONArray.parseArray(countryResultThree, AreaInfo.class);
            hsession.setAttribute("zoneCityList", zoneCityList);
        }
        hsession.setAttribute("sessionUserID",userID);
        hsession.setAttribute("sessionRoleID",roleID);
        url = ZONETREE_URL+"?userId="+userID;
        String countryResultOne = HttpUtils.httpGetString(url);
        List<AreaInfo> list = JSONArray.parseArray(countryResultOne, AreaInfo.class);
        String zoneIDs = "";
        if(null != list){
            for(AreaInfo areaInfo : list){
                zoneIDs += areaInfo.getId()+",";
            }
        }
        hsession.setAttribute("sessionZoneIDs",zoneIDs);
        if("1".equals(action)){
            return "redirect:/localLifeContent/show.html?userID="+userID+"&roleID="+roleID;
        }
        if("2".equals(action)){
            return "redirect:/localLifeLabel/show.html?userID="+userID+"&roleID="+roleID;
        }
        if("3".equals(action)){
            return "redirect:/localLifeLogo/show.html?userID="+userID+"&roleID="+roleID;
        }
        if("4".equals(action)){
            return "redirect:/localLifeRecommend/show.html?userID="+userID+"&roleID="+roleID;
        }
        return "timeout";
    }
*/
}
