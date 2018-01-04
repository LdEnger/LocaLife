package main.java.com.hiveview.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysRole;
import com.hiveview.entity.sys.SysRoleAuth;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.sys.SysRoleService;

@Controller
@RequestMapping("/sysRole")
public class SysRoleAction {
	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping(value = "/show")
	public ModelAndView show() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/role_list");
		return mv;
	}

	@RequestMapping(value = "/getSysRoleList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ScriptPage getSysRoleList(HttpServletRequest request,SysRole sysRole, AjaxPage ajaxPage) {
		sysRole.copy(ajaxPage);
		ScriptPage scriptPage = new ScriptPage();
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		if (null == obj) {
			return scriptPage;
		}
		SysUser currentUser = (SysUser) obj;
		sysRole.setRoleId(currentUser.getRoleId());
		List<SysRole> rows = sysRoleService.getSysRoleList(sysRole);
		int total = sysRoleService.getCount(sysRole);
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	@RequestMapping(value="/handleAuth", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public int getrolehandleAuth(String roleId){
		return sysRoleService.getSysRoleAuth();
	}

	@RequestMapping("/updateSysRole")
	@ResponseBody
	public Data updateSysRole(SysRole sysRole, String sysRoleAuth) {
		Data data = new Data();
		/*** 获得要添加的权限 start ***/
		String authIdArray[] = sysRoleAuth.split(",");
		List<SysRoleAuth> roleAuthlist = new ArrayList<SysRoleAuth>();
		for (int authIdIndex = 0; authIdIndex < authIdArray.length; authIdIndex++) {
			SysRoleAuth ra = new SysRoleAuth();
			ra.setRoleId(sysRole.getRoleId());
			ra.setAuthId(new Integer(authIdArray[authIdIndex]));
			roleAuthlist.add(ra);
		}
		/*** 获得要添加的权限 end ***/
		if (sysRoleService.updateSysRole(sysRole, roleAuthlist) > 0) {
			data.setCode(1);
		} else {
			data.setCode(0);
		}
		return data;
	}

	@RequestMapping("/addSysRole")
	@ResponseBody
	public Data addSysRole(SysRole sysRole, String sysRoleAuth) {
		Data data = new Data();
		if (sysRoleService.addSysRole(sysRole, sysRoleAuth) > 0) {
			data.setCode(1);
		} else {
			data.setCode(0);
		}
		return data;
	}

	@RequestMapping("/deleteRole")
	@ResponseBody
	public Data deleteRole(Integer roleId) {
		Data data = new Data();
		if (sysRoleService.deleteRole(roleId) > 0) {
			data.setCode(1);
		} else {
			data.setCode(0);
		}
		return data;
	}
}
