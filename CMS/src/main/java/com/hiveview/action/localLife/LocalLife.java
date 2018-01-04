package main.java.com.hiveview.action.localLife;

import com.alibaba.fastjson.JSON;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.liveManage.api.UserInfo;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.localLife.ZoneUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

@Controller
@RequestMapping("/localLife")
public class LocalLife {
    @Autowired
    private ZoneUserService zoneUserService;

    @RequestMapping(value = "/localLifeContent")
    public String localLifeContent(HttpServletRequest request) throws Exception {
        Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        if (currentUser == null) {
            return "timeout";
        }
        UserInfo info = new UserInfo();
        info.setOpenUid(currentUser.getUserId());
        info.setOpenName(currentUser.getUserName());
        info.setOpenRole(currentUser.getRoleId());
        info.setZoneId(currentUser.getZoneId());
        info.setZoneName(currentUser.getZoneName());
        info.setBranchId(currentUser.getBranchId());
        info.setBranchName(currentUser.getBranchName());
        info.setHallId(currentUser.getHallId());
        info.setHallName(currentUser.getHallName());

        if (currentUser.getRoleId() == 5) {
            List<ZoneCity> zoneByUserId = zoneUserService.getZone(currentUser.getUserId());
            for (ZoneCity zone : zoneByUserId) {
                if (100 != zone.getCityId()) {
                    int length = String.valueOf(zone.getCityId()).length();
                    if (length == 2) {
                        info.setProCode(String.valueOf(zone.getCityId()));
                        info.setProName(zone.getCityName());
                    } else if (length == 4) {
                        info.setCityCode(String.valueOf(zone.getCityId()));
                        info.setCityName(zone.getCityName());
                    }
                }
            }
        } else {
            info.setProCode(currentUser.getProvinceCode());
            info.setProName(currentUser.getProvinceName());
            info.setCityCode(currentUser.getCityCode());
            info.setCityName(currentUser.getCityName());
        }

        String data = JSON.toJSONString(info);
        data = URLEncoder.encode(data, "UTF-8");
        String url = ApiConstants.LOCAL_LIFE_CONTENT_URL;
        url = MessageFormat.format(url, data);
        System.out.println("播控URL:" + url);
        return "redirect:" + url;
    }

    @RequestMapping(value = "/localLifeRecommend")
    public String localLifeRecommend(HttpServletRequest request) throws Exception {
        Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        if (currentUser == null) {
            return "timeout";
        }
        UserInfo info = new UserInfo();
        info.setOpenUid(currentUser.getUserId());
        info.setOpenName(currentUser.getUserName());
        info.setOpenRole(currentUser.getRoleId());
        info.setZoneId(currentUser.getZoneId());
        info.setZoneName(currentUser.getZoneName());
        info.setBranchId(currentUser.getBranchId());
        info.setBranchName(currentUser.getBranchName());
        info.setHallId(currentUser.getHallId());
        info.setHallName(currentUser.getHallName());

        if (currentUser.getRoleId() == 5) {
            List<ZoneCity> zoneByUserId = zoneUserService.getZone(currentUser.getUserId());
            for (ZoneCity zone : zoneByUserId) {
                if (100 != zone.getCityId()) {
                    int length = String.valueOf(zone.getCityId()).length();
                    if (length == 2) {
                        info.setProCode(String.valueOf(zone.getCityId()));
                        info.setProName(zone.getCityName());
                    } else if (length == 4) {
                        info.setCityCode(String.valueOf(zone.getCityId()));
                        info.setCityName(zone.getCityName());
                    }
                }
            }
        } else {
            info.setProCode(currentUser.getProvinceCode());
            info.setProName(currentUser.getProvinceName());
            info.setCityCode(currentUser.getCityCode());
            info.setCityName(currentUser.getCityName());
        }

        String data = JSON.toJSONString(info);
        data = URLEncoder.encode(data, "UTF-8");
        String url = ApiConstants.LOCAL_LIFE_RECOMMEND_URL;
        url = MessageFormat.format(url, data);
        System.out.println("播控URL:" + url);
        return "redirect:" + url;
    }

    @RequestMapping(value = "/localLifeLabel")
    public String localLifeLabel(HttpServletRequest request) throws Exception {
        Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        if (currentUser == null) {
            return "timeout";
        }
        UserInfo info = new UserInfo();
        info.setOpenUid(currentUser.getUserId());
        info.setOpenName(currentUser.getUserName());
        info.setOpenRole(currentUser.getRoleId());
        info.setZoneId(currentUser.getZoneId());
        info.setZoneName(currentUser.getZoneName());
        info.setBranchId(currentUser.getBranchId());
        info.setBranchName(currentUser.getBranchName());
        info.setHallId(currentUser.getHallId());
        info.setHallName(currentUser.getHallName());

        if (currentUser.getRoleId() == 5) {
            List<ZoneCity> zoneByUserId = zoneUserService.getZone(currentUser.getUserId());
            for (ZoneCity zone : zoneByUserId) {
                if (100 != zone.getCityId()) {
                    int length = String.valueOf(zone.getCityId()).length();
                    if (length == 2) {
                        info.setProCode(String.valueOf(zone.getCityId()));
                        info.setProName(zone.getCityName());
                    } else if (length == 4) {
                        info.setCityCode(String.valueOf(zone.getCityId()));
                        info.setCityName(zone.getCityName());
                    }
                }
            }
        } else {
            info.setProCode(currentUser.getProvinceCode());
            info.setProName(currentUser.getProvinceName());
            info.setCityCode(currentUser.getCityCode());
            info.setCityName(currentUser.getCityName());
        }

        String data = JSON.toJSONString(info);
        data = URLEncoder.encode(data, "UTF-8");
        String url = ApiConstants.LOCAL_LIFE_LABEL_URL;
        url = MessageFormat.format(url, data);
        System.out.println("播控URL:" + url);
        return "redirect:" + url;
    }

    @RequestMapping(value = "/localLifeLogo")
    public String localLifeLogo(HttpServletRequest request) throws Exception {
        Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        if (currentUser == null) {
            return "timeout";
        }
        UserInfo info = new UserInfo();
        info.setOpenUid(currentUser.getUserId());
        info.setOpenName(currentUser.getUserName());
        info.setOpenRole(currentUser.getRoleId());
        info.setZoneId(currentUser.getZoneId());
        info.setZoneName(currentUser.getZoneName());
        info.setBranchId(currentUser.getBranchId());
        info.setBranchName(currentUser.getBranchName());
        info.setHallId(currentUser.getHallId());
        info.setHallName(currentUser.getHallName());

        if (currentUser.getRoleId() == 5) {
            List<ZoneCity> zoneByUserId = zoneUserService.getZone(currentUser.getUserId());
            for (ZoneCity zone : zoneByUserId) {
                if (100 != zone.getCityId()) {
                    int length = String.valueOf(zone.getCityId()).length();
                    if (length == 2) {
                        info.setProCode(String.valueOf(zone.getCityId()));
                        info.setProName(zone.getCityName());
                    } else if (length == 4) {
                        info.setCityCode(String.valueOf(zone.getCityId()));
                        info.setCityName(zone.getCityName());
                    }
                }
            }
        } else {
            info.setProCode(currentUser.getProvinceCode());
            info.setProName(currentUser.getProvinceName());
            info.setCityCode(currentUser.getCityCode());
            info.setCityName(currentUser.getCityName());
        }

        String data = JSON.toJSONString(info);
        data = URLEncoder.encode(data, "UTF-8");
        String url = ApiConstants.LOCAL_LIFE_LOGO_URL;
        url = MessageFormat.format(url, data);
        System.out.println("播控URL:" + url);
        return "redirect:" + url;
    }

}
