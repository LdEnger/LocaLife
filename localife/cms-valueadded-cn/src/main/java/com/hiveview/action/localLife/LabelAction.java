package com.hiveview.action.localLife;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.action.sys.ZoneTreeAction;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.localLife.Label;
import com.hiveview.entity.localLife.LabelContent;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.localLife.LabelService;
import com.singularsys.jep.functions.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Antony
 * Date: 16-6-3
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/localLifeLabel")
public class LabelAction {
    private static final Logger DATA = LoggerFactory.getLogger("data");

    @Autowired
    private LabelService labelService;

    private ZoneTreeAction zoneTreeAction;
    @RequestMapping(value="/show")
    public String show(HttpServletRequest req){
        try {
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
        List<ZoneCity> zoneCityList = JSONArray.parseArray(result,ZoneCity.class);
        req.setAttribute("zoneCityList", zoneCityList);
        return "localLife/label";
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ScriptPage getList(HttpServletRequest req,AjaxPage ajaxPage,Label label) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        String zoneIDs = (String)req.getSession().getAttribute("sessionZoneIDs");
        ScriptPage scriptPage = null;
        try {
            label.copy(ajaxPage);
            if("1".equals(roleID)){
                label.setUserId(Integer.valueOf(userID));
            }
            label.setZoneIDs(zoneIDs);
            scriptPage = labelService.getList(label);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }
    @RequestMapping(value = "/update")
    @ResponseBody
    public Data update(HttpServletRequest req,Label label) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        if(null != roleID){
            label.setUpdatedBy(Integer.valueOf(userID));
            label.setUpdatedTime(new Date());
        }
        Data data = new Data();
        try {
            boolean bool = labelService.update(label);
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
    public Data add(HttpServletRequest req,Label label) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        if(null != roleID){
            label.setCreatedBy(Integer.valueOf(userID));
        }
        Data data = new Data();
        try {
            boolean bool = labelService.add(label);
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
    public Data del(Label label) {
        Data data = new Data();
        try {
            boolean bool = labelService.del(label);
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

    /**
     *
     * @param type 此参数==1时，是上移。==2时是下移
     * @return
     */
    @RequestMapping(value = "/move")
    @ResponseBody
    public Data move(Label label,Integer type) {
        Data data = new Data();
        try {
            Integer minSequence = labelService.getMinSequence(label);
            Integer maxSequence = labelService.getMaxSequence(label);
            if(type == 3 && minSequence == label.getSeq()){
                //置顶
                data.setCode(3);
            }else if(type == 1 && label.getSeq() == minSequence){
                //上移
                data.setCode(4);
            }else if(type == 2 && label.getSeq() == maxSequence){
                //下移
                data.setCode(5);
            }else {
                boolean bool = labelService.move(label, type);
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

    /**
     *
     * @param sequence 当前要移动的活动的排序序号
     * @param type 此参数==1时，是上移。==2时是下移f
     * @return
     */
    @RequestMapping(value = "/moveLabelContent")
    @ResponseBody
    public Data moveLabelContent(int sequence,int type,int labelId) {
        Data data = new Data();
        try {
            boolean bool = labelService.moveLabelContent(sequence, type, labelId);
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

    @RequestMapping(value = "/delLabelContent")
    @ResponseBody
    public Data delLabelContent(LabelContent labelContent) {
        Data data = new Data();
        try {
            boolean bool = labelService.delLabelContent(labelContent);
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

    @RequestMapping(value = "/addLabelContent")
    @ResponseBody
    public Data addLabelContent(LabelContent labelContent) {
        Data data = new Data();
        try {
            boolean bool = labelService.addLabelContent(labelContent);
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

}
