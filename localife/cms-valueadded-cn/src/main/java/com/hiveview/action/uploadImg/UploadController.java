package com.hiveview.action.uploadImg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Title： 
 * Description： 上传图片
 * Company：hiveview.com 
 * Author：韩贺鹏 
 * Email：hanhepeng@btte.net
 * 2015年9月8日下午5:42:22
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	@RequestMapping(value = "/newsPic")
	@ResponseBody
	public Map<String, Object> newsPic(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws ServletRequestBindingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String downUrl = uploadFile(file,request);
		if (downUrl != null) {
				map.put("success", "true");
				map.put("picUrl", downUrl);
		}
		return map;
	}

	public static String uploadFile(MultipartFile file,HttpServletRequest req) {
//		String UPLOAD_PATH = req.getSession().getServletContext().getRealPath("/resources/image/");
		String UPLOAD_PATH = req.getSession().getServletContext().getRealPath("/picture/");
		System.out.println("路径为----"+UPLOAD_PATH);
//		String UPLOAD_URL = "resources/image/";
		String UPLOAD_URL = "picture/";
		String picUrl = null;
		if (!file.isEmpty()) {
			String uploadName = file.getOriginalFilename();
			// 截取文件类型并获取时间重新命名
			String ext = uploadName.substring(uploadName.lastIndexOf("."), uploadName.length());
			String fileName = Long.toString(System.currentTimeMillis()) + ext;
			try {
				writeFile(file.getInputStream(), UPLOAD_PATH + "/" + "", fileName);
				picUrl = UPLOAD_URL + fileName + "";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return picUrl;

	}

	public static boolean writeFile(InputStream inputStream, String fileDir, String fileName) throws IOException {
		File isfiledir = new File(fileDir);
		if (!isfiledir.exists() && !isfiledir.isDirectory()) {
			System.out.println("//不存在");
			isfiledir.mkdir();
		} else {
			System.out.println("//目录存在");
		}
		String filePath = fileDir + System.getProperty("file.separator") + fileName;
		FileOutputStream outputStream = null;
		byte[] bs = new byte[1024];
		try {
			outputStream = new FileOutputStream(filePath);
			int len = bs.length;
			while ((len = inputStream.read(bs)) != -1) {
				outputStream.write(bs, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}