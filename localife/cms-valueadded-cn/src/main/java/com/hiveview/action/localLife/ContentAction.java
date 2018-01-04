package com.hiveview.action.localLife;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.action.sys.ZoneTreeAction;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.Content;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.localLife.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Antony
 * Date: 16-6-3
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/localLifeContent")
public class ContentAction {
    private static final Logger DATA = LoggerFactory.getLogger("data");
/*    private static String ZONETREE_URL = ApiConstants.ZONETREE_URL;*/
    @Autowired
    private ContentService contentService;

    private ZoneTreeAction zoneTreeAction;

    @RequestMapping(value="/show")
    public String show(HttpServletRequest req){
        try {
            req.setCharacterEncoding("utf-8");
            String set = req.getParameter("data");
//*            String userName = new String(set.getBytes("ISO8859-1"),"UTF-8");*//*
            String userName = URLDecoder.decode(set, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(userName);
            String proCode = jsonObject.getString("proCode");
            String proName = jsonObject.getString("proName");
            String openRole = jsonObject.getString("openRole");
            String cityCode = jsonObject.getString("cityCode");
            String cityName = jsonObject.getString("cityName");
            req.setAttribute("proCode",proCode);
            req.setAttribute("proName",proName);
            req.setAttribute("cityCode",cityCode);
            req.setAttribute("cityName",cityName);
            req.setAttribute("openRole",openRole);


        }catch (Exception e){
            e.printStackTrace();
        }
        zoneTreeAction = new ZoneTreeAction();
        //根据URL发送请求
        String str = zoneTreeAction.getProvinceList("00");
        //将接收到的字符串转换成为JSON对象
        JSONObject jsonObject = JSONObject.parseObject(str);
        //根据result  取城市
        String result = jsonObject.getString("result");
        //将取出的城市转化成为LIST
        List<ZoneCity> zoneCityList = JSON.parseArray(result,ZoneCity.class);
        req.setAttribute("zoneCityList", zoneCityList);
        return "localLife/content";
    }
    @RequestMapping(value = "/getList")
    @ResponseBody
    public ScriptPage getList(HttpServletRequest req,AjaxPage ajaxPage,Content content) {
        ScriptPage scriptPage = null;
        try {
            content.copy(ajaxPage);
            scriptPage = contentService.getList(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Data update(HttpServletRequest req,Content content) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        if(null != userID){
            content.setUpdatedBy(Integer.valueOf(userID));
            content.setUpdatedTime(new Date());
        }
        Data data = new Data();
        try {
            boolean bool = contentService.update(content);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Data add(HttpServletRequest req,Content content) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        if(null != userID){
            content.setCreatedBy(Integer.valueOf(userID));
        }
        Data data = new Data();
        try {
            boolean bool = contentService.add(content);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Data del(Content content) {
        Data data = new Data();
        try {
            boolean bool = contentService.del(content);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /***
     * 获取推荐内容
     * @param request
     * @param ajaxPage
     * @param content
     * @return
     */
    @RequestMapping(value = "/getContentByRecommendID")
    @ResponseBody
    public ScriptPage getContentByRecommendID(HttpServletRequest request,AjaxPage ajaxPage,Content content) {
        String userID = (String)request.getSession().getAttribute("sessionUserID");
        String roleID = (String)request.getSession().getAttribute("sessionRoleID");
        String zoneIDs = (String)request.getSession().getAttribute("sessionZoneIDs");
        ScriptPage scriptPage = null;
        try {
            content.copy(ajaxPage);
            if("1".equals(roleID)){
                content.setUserId(Integer.valueOf(userID));
            }
            content.setZoneIDs(zoneIDs);
            scriptPage = contentService.getContentByRecommendID(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }

    /***
     * 获取标签内容
     * @param request
     * @param ajaxPage
     * @param content
     * @return
     */
    @RequestMapping(value = "/getContentByLabelID")
    @ResponseBody
    public ScriptPage getContentByLabelID(HttpServletRequest request,AjaxPage ajaxPage,Content content) {
        String userID = (String)request.getSession().getAttribute("sessionUserID");
        String roleID = (String)request.getSession().getAttribute("sessionRoleID");
        String zoneIDs = (String)request.getSession().getAttribute("sessionZoneIDs");
        ScriptPage scriptPage = null;
        try {
            content.copy(ajaxPage);
            if("1".equals(roleID)){
                content.setUserId(Integer.valueOf(userID));
            }
            content.setZoneIDs(zoneIDs);
            scriptPage = contentService.getContentByLabelID(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }

}
