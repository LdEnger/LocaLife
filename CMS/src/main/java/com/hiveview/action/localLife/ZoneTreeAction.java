package main.java.com.hiveview.action.localLife;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.localLife.AreaInfo;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.localLife.ZoneTreeService;

@Controller
@RequestMapping(value = "/zoneTree")
public class ZoneTreeAction {
	
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
        Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        if(1 != currentUser.getRoleId()){
            areaInfo.setUserId(currentUser.getUserId());
        }
        List<AreaInfo> areaInfoList = zoneTreeService.getZoneTreeByUserId(areaInfo);
        return areaInfoList;
    }



}
