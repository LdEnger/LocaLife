package com.hiveview.action.util;

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
            data.setOtherMsg(String.format("%.2f", Double.valueOf(file.getSize())/1024/1024));
        } else {
			data.setMsg("");
		}
		return data;
	}

    @RequestMapping(value = "/uploadBySize")
    @ResponseBody
    public Data uploadBySize(HttpServletRequest request,
                        @RequestParam(value = "file", required = false) MultipartFile file,
                        @RequestParam(value = "fileSize", required = false) Integer fileSize)
            throws ServletRequestBindingException {
        Data data = new Data();
        FileFunctionUtil ffu = new FileFunctionUtil();
        if( file.getSize()>fileSize*1024*1024 ){
            data.setCode(2);
            data.setMsg("文件大于"+fileSize+"M！");
            return data;
        }
        String downUrl = ffu.getUploadPath(file);
        if (downUrl != null) {
            data.setMsg(downUrl);
        } else {
            data.setMsg("");
        }
        return data;
    }
	
}
