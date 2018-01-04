package main.java.com.hiveview.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.hiveview.common.ApiConstants;

public class FileFunctionUtil {

	/**
	 * 文件流的写入
	 * 
	 * @param inputStream
	 * @param fileDir
	 * @param fileName
	 * @throws IOException
	 */
	public boolean writeFile(InputStream inputStream, String fileDir,
			String fileName) throws IOException {
		File isfiledir = new File(fileDir);
		if (!isfiledir.exists() && !isfiledir.isDirectory()) {
			System.out.println("//不存在");
			isfiledir.mkdir();
		} else {
			System.out.println("//目录存在");
		}
		String filePath = fileDir + System.getProperty("file.separator")
				+ fileName;
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

	public static String uploadFile(MultipartFile file) {
		FileFunctionUtil fileFunUtil = new FileFunctionUtil();
		String picUrl = null;
		if (!file.isEmpty()) {
			String uploadName = file.getOriginalFilename();
			// 截取文件类型并获取时间重新命名
			String ext = uploadName.substring(uploadName.lastIndexOf("."),
					uploadName.length());
			String fileName = Long.toString(System.currentTimeMillis()) + ext;
			try {
				fileFunUtil.writeFile(file.getInputStream(),
						ApiConstants.UPLOAD_PATH, fileName);
				picUrl = ApiConstants.WEBPATH + fileName + "";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return picUrl;
	}
	
	/**
	 * 
	* @Title: getUploadPath 
	* @Description: 组装上传路径 
	* @param multipartFile
	* @return   
	* @return String   
	* @throws
	 */
	public String getUploadPath(MultipartFile multipartFile)  {

		String path = ApiConstants.UPLOAD_PATH;
		String webpath = DateUtil.getFilePath();
		try {
			path = path + webpath;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			String extName = getFileExtName(multipartFile.getOriginalFilename());
			String fileName = System.currentTimeMillis() + "." + extName;
			webpath = webpath + fileName;
			String uploadPath = path + fileName;
			File files = new File(uploadPath);
			multipartFile.transferTo(files);
			Thread.sleep(2000);
		} catch (Exception e) {
			e.getSuppressed();
		}

		//如果需要同步
		//			if(ExceptionGlobal.ISUPLOAD.equals("1")){
		//				String commond = "/usr/bin/rsync -avP --password-file=/etc/rsyncd.psw  "+ExceptionGlobal.UPLOADPATH+" root@10.10.0.28::dataSource/blueray/";
		//				RsyncUtils.rsyncExec(commond);
		//			}
		return ApiConstants.WEBPATH + webpath;
	}
	
	public static String getFileExtName(String fileName)  {
		String extName = "";
		if (fileName != null && !"".equals(fileName)) {
			int i = fileName.lastIndexOf(".");
			if (i > 0) {
				extName = fileName.substring(i + 1, fileName.length());
			}
		}
		return extName;
	}

}
