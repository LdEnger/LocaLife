package com.hiveview.service.localLife;

import com.hiveview.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 根据redis的key刷新缓存
 * Created by user on 2017/12/21.
 */
@Service
public class flushRedisService {
    @Autowired
    private RedisService redisService;
    /**
     *(本地生活推荐位列表RedisKey)"recommendListCache"
     *(本地生活标签列表RedisKey)"labelListCache"
     * (本地生活Logo列表RedisKey)"logoListCache"
     * @param redisKey,areaId,cityId
     * @return
     */
    public Integer flushCityByIp(String redisKey,String areaId,String cityId){
    try{
        String key = redisKey+"_"+areaId+"_"+cityId;
        if(redisService.exists(key)){
           redisService.del(key);
           return 1;
        }else{
            return 2;
            }
    }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     *(获取本地生活内容列表接口)"contentCache"+"_"+contentId
     * @param redisKey,contentId
     * @return
     */
    public Integer flushRedisByContentId(String redisKey,Integer contentId){

        try{
            String key = redisKey+"_"+contentId;
            if(redisService.exists(key)){
                redisService.del(key);
                return 1;
            }else{
                return 2;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
