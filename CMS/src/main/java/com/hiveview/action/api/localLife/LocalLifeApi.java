package main.java.com.hiveview.action.api.localLife;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.service.localLife.ZoneTreeService;

@Controller
@RequestMapping(value = "/api/localLife/")
public class LocalLifeApi {
	@Autowired
	private ZoneTreeService zoneTreeService;
	
    @RequestMapping(value = "/getZoneTreeByUser", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public List<AreaInfo> getZoneByUserId(AreaInfo areaInfo) {
        List<AreaInfo> areaList = zoneTreeService.getZoneTreeByUserId(areaInfo);
        return areaList;
    }
}
