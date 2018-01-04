package main.java.com.hiveview.action.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hiveview.entity.bo.Data;
import com.hiveview.util.FileFunctionUtil;

@Controller
@RequestMapping(value = "/util")
public class UtilAction {
	

	@RequestMapping(value = "/upload")
	@ResponseBody
	public Data newsPic(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws ServletRequestBindingException {
		Data data = new Data();
		FileFunctionUtil ffu = new FileFunctionUtil();
		String downUrl = ffu.getUploadPath(file);
		if (downUrl != null) {
			data.setMsg(downUrl);
		} else {
			data.setMsg("");
		}
		return data;
	}
	
	/**
	 * @author heliangliang
	 * @description 按要求上传图片
	 * @param request
	 * @param file
	 * @param width
	 * @param height
	 * @param size
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value = "/uploadAsRequired")
	@ResponseBody
	public Map<String,Object> uploadAsRequired(HttpServletRequest request,@RequestParam(value = "file", required = false) MultipartFile file,int width,int height,Long size) 
			throws ServletRequestBindingException {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			FileFunctionUtil ffu = new FileFunctionUtil();
			String downUrl = ffu.getUploadPath(file);
			if (downUrl==null) {
				map.put("code", 0);
				map.put("msg", "文件上传失败[文件访问地址为空]！");
				return map;
			}
			map.put("code", 1);
			map.put("msg", downUrl);
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			map.put("code", 0);
			map.put("msg", "文件上传失败[异常]！");
			e.printStackTrace();
		}
		return map;
	}
	
}
