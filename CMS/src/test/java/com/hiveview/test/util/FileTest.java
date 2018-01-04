package test.java.com.hiveview.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import org.aspectj.apache.bcel.classfile.Field;

import com.csvreader.CsvReader;
import com.hiveview.entity.card.ExcelFile;
import com.hiveview.util.DateUtil;
import com.hiveview.util.FileUtil;


/**
 * Title：
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-3-20下午2:05:15
 */
public class FileTest {
	public static void main(String[] args) throws Exception {
//		moveFile();
		readcsv(new File("/data/ops/app/ftp/CD_BOSS_0_20170316.csv"));
	}
	public static void moveFile(){


		String rootFilePath ="/data/ops/app/ftp/upload/";
		String uploadFilePath ="/data/ops/app/ftp/";
		String yearpath =DateUtil.getFilePathFormaterByDay();
		String newbossTargetPath =uploadFilePath+"zz/"+yearpath+"upload/";
		String oldbossTargetPath =uploadFilePath+"zz/"+yearpath;
		File rootFile =new File(rootFilePath);
		File[] files =rootFile.listFiles();
		
		for(File file:files){
			String fileName =file.getName();
			if(!file.isDirectory()){
				String path = file.getPath();
				int type =0;
				if(fileName.indexOf("product")>-1){
					type =1;//新boss产品包类文件
				}else if(fileName.indexOf("customer")>-1){
					type =2;//新boss开通类文件
				}else{
					type =99;
				}
				ExcelFile exfile =new ExcelFile();
				exfile.setFileName(fileName);
				exfile.setPath(newbossTargetPath+fileName);
				exfile.setType(type);
				exfile.setStatus(0);
				exfile.setTimes(0);
				exfile.setMsg("转移成功");
				exfile.setCtime(new Date());
				FileUtil.copyFile(path, newbossTargetPath+fileName, true);
				//file.delete();
			}
		}
		
		File uploadFile =new File(uploadFilePath);
		files =uploadFile.listFiles();
		for(File file:files){

			String fileName =file.getName();
			if(!file.isDirectory()){
				//文件，老boss上传的
				String path = file.getPath();
				int type =4;
				String names [] =fileName.split("_");
				System.out.println(names.length>3);
				System.out.println(names[2]);
				ExcelFile exfile =new ExcelFile();
				exfile.setFileName(fileName);
				exfile.setPath(oldbossTargetPath+fileName);
				exfile.setType(type);
				exfile.setStatus(0);
				exfile.setTimes(0);
				exfile.setMsg("转移成功");
				exfile.setCtime(new Date());
				FileUtil.copyFile(path, oldbossTargetPath+fileName, true);
				//file.delete();
			}
		
		}
	
	
	}
	public static void readcsv(File file) throws Exception{
		//生成CsvReader对象，以，为分隔符，GBK编码方式
        CsvReader r = new CsvReader(file.getPath(), ',',Charset.forName("GBK"));
        //读取表头
        r.readHeaders();
        
        //逐条读取记录，直至读完
        System.out.println("--->"+r.getHeaders().length);
        while (r.readRecord()) {
            //读取一条记录
            System.out.println(r.getRawRecord());
            //按列名读取这条记录的值
//            System.out.println(r.get(0));
//            System.out.println(r.get(1));
//            System.out.println(r.get(2));
//            System.out.println(r.get(3));
//            System.out.println(r.get(4));
//            System.out.println(r.get(5));
//            System.out.println(r.get(6));
        }
        r.close();
	}
}
