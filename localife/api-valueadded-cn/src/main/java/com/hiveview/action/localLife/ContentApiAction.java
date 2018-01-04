package com.hiveview.action.localLife;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.bo.ApiResult;
import com.hiveview.entity.bo.ApiResultTypeEnum;
import com.hiveview.entity.localLife.*;
import com.hiveview.entity.vo.localLife.ContentVo;
import com.hiveview.entity.vo.localLife.LabelContentVo;
import com.hiveview.entity.vo.localLife.RecommendVo;
import com.hiveview.pay.util.HttpUtils;
import com.hiveview.service.RedisService;
import com.hiveview.service.localLife.ContentService;
import com.hiveview.service.localLife.LabelService;
import com.hiveview.service.localLife.LogoService;
import com.hiveview.service.localLife.RecommendService;
import com.hiveview.util.Constant;
import com.hiveview.util.IPUtil;
import com.hiveview.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Antony
 * Date: 16-6-3
 * Time: 下午12:56
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/api/localLife")
public class ContentApiAction {
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private LogoService logoService;
    /*@Autowired
    private CompositeCacheManager simpleCacheManager;*/
    @Autowired
    RedisService redisService;
    private static Integer localLife [] = {1,2,3,4,5,6,7,8,9,10};
    private static final Logger DATA = LoggerFactory.getLogger("data");

    private static String DOMAIN = ApiConstants.API_UTIL_US + "/api/ip/isGroupUser.json";

    @RequestMapping(value = "/show")
    public ModelAndView show() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("localLife/localLife_api");
        return mv;
    }

    @SuppressWarnings("rawtypes")
    public static Map parseString(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        Map map = (Map) jsonObject;
        return map;
    }

    public  DeviceInfo getDevice(HttpServletRequest request){
        String ip = IPUtil.getIpAddress(request);
        //String ip = "218.247.246.235";
        DeviceInfo device = new DeviceInfo();
        String key = Constant.AREA_LIST_CACHE+"_"+ip;

        //redis缓存配置
        if(redisService.exists(key)){
            String val = redisService.get(key);
            device = JSONObject.parseObject(val,DeviceInfo.class);
        }else{
            Integer index = new Random().nextInt(localLife.length);
            synchronized (localLife[index]) {
                if (redisService.exists(key)) {
                    String val = redisService.get(key);
                    device = JSONObject.parseObject(val, DeviceInfo.class);
                } else {
                    Map<String,String> param = new HashMap<String, String>();
                    param.put("userIP",ip);
                    param.put("version","1.0");
                    String countryResult = HttpUtils.httpPostString(DOMAIN,param);
                    JSONObject obj = JSONObject.parseObject(countryResult);
                    String result = obj.getString("result");
                    String area_id = "";
                    String city_id = "";
                    if(null != result){
                        String _countryResult = parseString(countryResult).get("result").toString();
                        obj = JSONObject.parseObject(_countryResult);
                        area_id = obj.getString("pcode");
                        city_id = obj.getString("ccode");
                    }
                    if(("99".equals(area_id) && "9999".equals(city_id)) || (("".equals(area_id) && "".equals(city_id)))){
                        device.setArea_id(String.valueOf(ApiConstants.API_UTIL_US_PROVINCEID));
                        device.setCity_id(String.valueOf(ApiConstants.API_UTIL_US_CITYID));
                    }else{
                        device.setArea_id(String.valueOf(area_id));
                        device.setCity_id(String.valueOf(city_id));
                    }
                    if (device != null) {
                        redisService.set(key, JSON.toJSONString(device, SerializerFeature.WriteMapNullValue),getRandomTime());
                    }
                }
            }
        }


        return device;
    }
    /***
     * 获取本地生活推荐位接口
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/appRecommendList-{sn}-{mac}-{countryId}-{provinceId}-{cityId}-{version}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult appRecommendList(HttpServletRequest request,@PathVariable String sn,@PathVariable String mac
            ,@PathVariable Integer countryId,@PathVariable Integer provinceId,@PathVariable Integer cityId) {
        ApiResult op = new ApiResult();
        List<RecommendVo> list = null;
        try {
            /*if(StringUtils.isEmpty(mac) || StringUtils.isEmpty(sn)){
                op.setCode(ApiResultTypeEnum.ERR_1.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_1.getType());
                op.setResult(null);
                return op;
            }*/
            DeviceInfo device = getDevice(request);
            if(null == device){
                op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_NULL.getType());
                op.setResult(null);
                return op;
            }
            /*Cache cacheList= simpleCacheManager.getCache("getRecommendListCache");
            String keyList =device.getCountry_id()+"_"+device.getArea_id()+"_"+device.getCity_id();
            Cache.ValueWrapper vwList = cacheList.get(keyList);
            if(vwList!=null){
                list = (List<RecommendVo>)vwList.get();
            }else{
                list = recommendService.getRecommendList(device.getCountry_id(),device.getArea_id(),device.getCity_id());
                //cacheList.put(keyList, list);
            }*/
            //long expire = 15*60L;//三分钟
            String key = Constant.RECOMMEND_LIST_CACHE+"_"+device.getArea_id()+"_"+device.getCity_id();

            //redis缓存配置
            if(redisService.exists(key)){
                String val = redisService.get(key);
                list = JSONObject.parseObject(val,List.class);
            }else{
                Integer index = new Random().nextInt(localLife.length);
                synchronized (localLife[index]) {
                    if (redisService.exists(key)) {
                        String val = redisService.get(key);
                        list = JSONObject.parseObject(val, List.class);
                    } else {
                        list = recommendService.getRecommendList(device.getCountry_id(),device.getArea_id(),device.getCity_id());
                        if (list != null && list.size() != 0) {
                            redisService.set(key, JSON.toJSONString(list, SerializerFeature.WriteMapNullValue),getRandomTime());
                        }else{
                            list = recommendService.getRecommendList(device.getCountry_id(),"00","0000");
                            redisService.set(key, JSON.toJSONString(list, SerializerFeature.WriteMapNullValue),getRandomTime());
                        }

                    }
                }
            }
            //原来的Redis配置
            /*if (!redisService.exists(key)) {
                list = recommendService.getRecommendList(device.getCountry_id(),device.getArea_id(),device.getCity_id());
                redisService.rPush(key, list, RecommendVo.class, 0L);
            }else{
                list= redisService.lRange(key,0L,-1L, RecommendVo.class);
            }*/
            op.setResult(list);
        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("推荐位获取活动列表异常", e);
            op.setCode(ApiResultTypeEnum.ERR.getCode());
            op.setDesc(ApiResultTypeEnum.ERR.getType());
        }
        return op;
    }
    /***
     * 获取本地生活标签列表接口
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/appLableContentList-{sn}-{mac}-{countryId}-{provinceId}-{cityId}-{version}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult appLableContentList(HttpServletRequest request,@PathVariable String sn,@PathVariable String mac
            ,@PathVariable Integer countryId,@PathVariable Integer provinceId,@PathVariable Integer cityId) {
        ApiResult op = new ApiResult();
        List<LabelContentVo> list = null;
        try {
            /*if(StringUtils.isEmpty(mac) || StringUtils.isEmpty(sn)){
                op.setCode(ApiResultTypeEnum.ERR_1.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_1.getType());
                op.setResult(null);
                return op;
            }*/
            DeviceInfo device = getDevice(request);
            if(null == device){
                op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_NULL.getType());
                op.setResult(null);
                return op;
            }
            String key = Constant.LABEL_LIST_CACHE+"_"+device.getArea_id()+"_"+device.getCity_id();
            //redis缓存配置
            if(redisService.exists(key)){
                String val = redisService.get(key);
                list = JSONObject.parseObject(val,List.class);
            }else{
                Integer index = new Random().nextInt(localLife.length);
                synchronized (localLife[index]) {
                    if (redisService.exists(key)) {
                        String val = redisService.get(key);
                        list = JSONObject.parseObject(val, List.class);
                    } else {
                        Label label = new Label();
                        label.setCountryId(device.getCountry_id());
                        label.setProvinceId(device.getArea_id());
                        label.setCityId(device.getCity_id());
                        list = labelService.getLabelContentList(label);
                        if (list != null && list.size() != 0) {
                            redisService.set(key, JSON.toJSONString(list, SerializerFeature.WriteMapNullValue),getRandomTime());
                        }else{
                            Label label1 = new Label();
                            label1.setCountryId(device.getCountry_id());
                            label1.setProvinceId("00");
                            label1.setCityId("0000");
                            list = labelService.getLabelContentList(label1);
                            redisService.set(key, JSON.toJSONString(list, SerializerFeature.WriteMapNullValue),getRandomTime());
                        }
                    }
                }
            }
            //原来的Redis配置
            /*if (!redisService.exists(key)) {
                Label label = new Label();
                label.setCountryId(device.getCountry_id());
                label.setProvinceId(device.getArea_id());
                label.setCityId(device.getCity_id());
                list = labelService.getLabelContentList(label);
                redisService.rPush(key, list, LabelContentVo.class, 0L);
            }else{
                list= redisService.lRange(key,0L,-1L, LabelContentVo.class);
            }*/
            op.setResult(list);
        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("标签获取活动列表异常", e);
            op.setCode(ApiResultTypeEnum.ERR.getCode());
            op.setDesc(ApiResultTypeEnum.ERR.getType());
        }
        return op;
    }
    /***
     * 获取本地生活内容列表接口
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getAppContent-{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getAppContent(@PathVariable Integer contentId) {
        ApiResult op = new ApiResult();
        List<ContentVo> list = null;
        try {
            // contentId 不能为空
            if (contentId == null) {
                op.setCode(ApiResultTypeEnum.ERR_1.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_1.getType());
                op.setResult(null);
                return op;
            }
            String key = Constant.CONTENT_CACHE+"_"+contentId;

            //redis缓存配置
            if(redisService.exists(key)){
                String val = redisService.get(key);
                list = JSONObject.parseObject(val,List.class);
            }else{
                Integer index = new Random().nextInt(localLife.length);
                synchronized (localLife[index]) {
                    if (redisService.exists(key)) {
                        String val = redisService.get(key);
                        list = JSONObject.parseObject(val, List.class);
                    } else {
                        Content content = new Content();
                        content.setId(contentId);
                        list = contentService.getContent(content);
                        if (list != null && list.size() != 0) {
                            redisService.set(key, JSON.toJSONString(list, SerializerFeature.WriteMapNullValue),getRandomTime());
                        }
                    }if(null == list || list.size() ==0){
                        op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
                        op.setDesc(ApiResultTypeEnum.ERR_NULL.getType());
                        op.setResult(null);
                        return op;
                    }
                }
            }

            //原来的Redis配置
            /*if (!redisService.exists(key)) {
                Content content = new Content();
                content.setId(contentId);
                list = contentService.getContent(content);
                redisService.rPush(key, list, ContentVo.class, 0L);
            }else{
                list= redisService.lRange(key,0L,-1L, ContentVo.class);
            }
            if(null == list || list.size() ==0){
                op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_NULL.getType());
                op.setResult(null);
                return op;
            }*/
            op.setResult(list);
        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("内容取活动列表异常", e);
            op.setCode(ApiResultTypeEnum.ERR.getCode());
            op.setDesc(ApiResultTypeEnum.ERR.getType());
        }
        return op;
    }
    /***
     * 获取本地生活logo列表接口
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getAppLogoList-{sn}-{mac}-{countryId}-{provinceId}-{cityId}-{version}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getAppLogoList(HttpServletRequest request,@PathVariable String sn,@PathVariable String mac
            ,@PathVariable Integer countryId,@PathVariable Integer provinceId,@PathVariable Integer cityId) {
        ApiResult op = new ApiResult();
        List<Logo> list = null;
        try {
            /*if(StringUtils.isEmpty(mac) || StringUtils.isEmpty(sn)){
                op.setCode(ApiResultTypeEnum.ERR_1.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_1.getType());
                op.setResult(null);
                return op;
            }*/
            DeviceInfo device = getDevice(request);
            if(null == device){
                op.setCode(ApiResultTypeEnum.ERR_NULL.getCode());
                op.setDesc(ApiResultTypeEnum.ERR_NULL.getType());
                op.setResult(null);
                return op;
            }
            String key = Constant.LOGO_LIST_CACHE+"_"+device.getArea_id()+"_"+device.getCity_id();
            //redis缓存配置
            if(redisService.exists(key)){
                String val = redisService.get(key);
                list = JSONObject.parseObject(val,List.class);
            }else{
                Integer index = new Random().nextInt(localLife.length);
                synchronized (localLife[index]) {
                    if (redisService.exists(key)) {
                        String val = redisService.get(key);
                        list = JSONObject.parseObject(val, List.class);
                    } else {
                        Logo logo = new Logo();
                        logo.setCountryId(device.getCountry_id());
                        logo.setProvinceId(device.getArea_id());
                        logo.setCityId(device.getCity_id());
                        list = logoService.getLogoList(logo);
                        if (list != null && list.size() != 0) {
                            redisService.set(key, JSON.toJSONString(list, SerializerFeature.WriteMapNullValue),getRandomTime());
                        }else{
                            Logo logo1 = new Logo();
                            logo1.setCountryId(device.getCountry_id());
                            logo1.setProvinceId("00");
                            logo1.setCityId("0000");
                            list = logoService.getLogoList(logo1);
                            redisService.set(key, JSON.toJSONString(list, SerializerFeature.WriteMapNullValue),getRandomTime());
                        }
                    }
                }
            }
            //原来的Redis配置
            /*if (!redisService.exists(key)) {
                Logo logo = new Logo();
                logo.setCountryId(device.getCountry_id());
                logo.setProvinceId(device.getArea_id());
                logo.setCityId(device.getCity_id());
                list = logoService.getLogoList(logo);
                redisService.rPush(key, list, Logo.class, 0L);
            }else{
                list= redisService.lRange(key,0L,-1L, Logo.class);
             }*/
            op.setResult(list);
        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("Logo获取活动列表异常", e);
            op.setCode (ApiResultTypeEnum.ERR.getCode());
            op.setDesc(ApiResultTypeEnum.ERR.getType());
        }
        return op;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/toflushDB", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult toflushDB() {
        ApiResult op = new ApiResult();
        redisService.flushDB();
        return  op;
    }
    /***
     * 设置Redis缓存过期时间
     */
    public int getRandomTime(){
        return 3600 + new Random().nextInt(3600);
    }
}
