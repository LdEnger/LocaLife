package com.hiveview.action.localLife;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.action.sys.ZoneTreeAction;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.localLife.Logo;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.Zone;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.HallService;
import com.hiveview.service.localLife.LogoService;
import com.hiveview.service.sys.SysRoleService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.service.sys.ZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
@RequestMapping("/localLifeLogo")
public class LogoAction {
    private static final Logger DATA = LoggerFactory.getLogger("data");
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneCityService zoneCityService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private HallService hallService;
    @Autowired
    private LogoService logoService;

    private ZoneTreeAction zoneTreeAction;

    @RequestMapping(value="/show")
    public String show(HttpServletRequest req){
        try {
            req.setCharacterEncoding("utf-8");
            String set = req.getParameter("data");
          /*  String userName = new String(set.getBytes("ISO8859-1"),"UTF-8");*/
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
        req.setAttribute("zoneCityList",zoneCityList);
        return "localLife/logo";
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ScriptPage getList(HttpServletRequest req,AjaxPage ajaxPage,Logo logo) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        String zoneIDs = (String)req.getSession().getAttribute("sessionZoneIDs");
        ScriptPage scriptPage = null;
        try {
            logo.copy(ajaxPage);
            if("1".equals(roleID)){
                logo.setUserId(Integer.valueOf(userID));
            }
            logo.setZoneIDs(zoneIDs);
            scriptPage = logoService.getList(logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }
    @RequestMapping(value = "/update")
    @ResponseBody
    public Data update(HttpServletRequest req,Logo logo) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        if(null != roleID){
            logo.setUpdatedBy(Integer.valueOf(userID));
            logo.setUpdatedTime(new Date());
        }
        Data data = new Data();
        try {
            if(logo.getCityId() != null ) {
                Logo logoVal = new Logo();
                Logo logoId = new Logo();
                logoId.setId(logo.getId());
                logoId = logoService.getLogoById(logoId.getId());
                System.out.println(logoId.getCityId() + "==" + logo.getCityId());
                if (logoId.getCityId().equals(logo.getCityId())) {
                    boolean bool = logoService.update(logo);
                    if (bool){
                        data.setCode(1);
                        return data;
                    }else{
                        data.setCode(0);
                        return data;
                    }
                } else {
                    logoVal.setCityId(logo.getCityId());
                    int total = logoService.getTotal(logoVal);
                    if (total > 0) {
                        data.setCode(2);
                        return data;
                    }
                }
            }
            boolean bool = logoService.update(logo);
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
    public Data add(HttpServletRequest req,Logo logo) {
        String userID = (String)req.getSession().getAttribute("sessionUserID");
        String roleID = (String)req.getSession().getAttribute("sessionRoleID");
        if(null != roleID){
            logo.setCreatedBy(Integer.valueOf(userID));
        }
        Data data = new Data();
        try {
            Logo logoVal = new Logo();
            logoVal.setCityId(logo.getCityId());
            int total = logoService.getTotal(logoVal);
            if(total>0){
                data.setCode(2);
                return data;
            }
            boolean bool = logoService.add(logo);
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
    public Data del(Logo logo) {
        Data data = new Data();
        try {
            boolean bool = logoService.del(logo);
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
