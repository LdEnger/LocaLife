package com.hiveview.action.localLife;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.action.sys.ZoneTreeAction;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.Recommend;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.localLife.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
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
@RequestMapping("/localLifeRecommend")
public class RecommendAction {
    private static final Logger DATA = LoggerFactory.getLogger("data");

    @Autowired
    private RecommendService recommendService;

    private static ZoneTreeAction zoneTreeAction;
    @RequestMapping(value="/show")
    public String show(HttpServletRequest req){
        try {
            /*            String userName = new String(set.getBytes("ISO8859-1"),"UTF-8");*/
            req.setCharacterEncoding("utf-8");
            String set = req.getParameter("data");
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
        String str = zoneTreeAction.getProvinceList("00");
        JSONObject jsonObject = JSONObject.parseObject(str);
        String result = jsonObject.getString("result");
        List<ZoneCity> zoneCityList = JSON.parseArray(result,ZoneCity.class);
        req.setAttribute("zoneCityList", zoneCityList);
        return "localLife/recommend";
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ScriptPage getList(HttpServletRequest req,AjaxPage ajaxPage,Recommend recommend) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        String zoneIDs = (String)req.getSession().getAttribute("sessionZoneIDs");
        ScriptPage scriptPage = null;
        try {
            recommend.copy(ajaxPage);
            if("1".equals(roleID)){
                recommend.setUserId(Integer.valueOf(userID));
            }
            recommend.setZoneIDs(zoneIDs);
            scriptPage = recommendService.getList(recommend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }
    public static Map parseString(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        Map map = (Map) jsonObject;
        return map;
    }
    @RequestMapping(value = "/update")
    @ResponseBody
    public Data update(HttpServletRequest req,Recommend recommend) {
       /* String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");*/
        /*if(null != roleID){
            recommend.setUpdatedBy(Integer.valueOf(userID));
            recommend.setUpdatedTime(new Date());
        }*/
        Data data = new Data();
        try {
            boolean bool = recommendService.update(recommend);
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

    @RequestMapping("/contentDel")
    @ResponseBody
    public Data contentDel(HttpServletRequest req,Recommend recommend){
        Data data = new Data();
        try {
            boolean bool = recommendService.contentDel(recommend);
            if(bool){
                data.setCode(1);
            }else {
                data.setCode(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    @RequestMapping(value = "/add")
    @ResponseBody
    public Data add(HttpServletRequest req,Recommend recommend) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        if(null != roleID){
            recommend.setCreatedBy(Integer.valueOf(userID));
        }
        Data data = new Data();
        try {
            boolean bool = recommendService.add(recommend);
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
    public Data del(Recommend recommend) {
        Data data = new Data();
        try {
            boolean bool = recommendService.del(recommend);
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

    @RequestMapping(value = "/move")
    @ResponseBody
    public Data up(Recommend recommend,int type) {
        Data data = new Data();
        try {
            Integer maxSequence = this.recommendService.getMaxSequence(recommend);
            Integer minSequence = this.recommendService.getMinSequence(recommend);
            if(type == 1  && recommend.getSeq() == minSequence){
                data.setCode(3);
            } else if(type == 2 && recommend.getSeq() == maxSequence){
                data.setCode(4);
            }else {
                boolean bool = recommendService.move(recommend, type);
                if (bool) {
                    data.setCode(1);
                } else {
                    data.setCode(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping("/topMove")
    @ResponseBody
    public Data top(Recommend recommend){
        Data data = new Data();
        try {
            Integer minSequence = this.recommendService.getMinSequence(recommend);
            if(recommend.getSeq() == minSequence){
                data.setCode(3);
            }else {
                boolean bool = recommendService.topMove(recommend);
                if (bool) {
                    data.setCode(1);
                } else {
                    data.setCode(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
