package main.java.com.hiveview.service.py;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.py.CardPyDao;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.py.CardPy;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.util.Constants;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.DateUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class CardPyService {

    private static final Logger DATA = LoggerFactory.getLogger("data");

    @Autowired
    private CardPyDao cardPyDao;
    @Autowired
    private CardPyDetailService cardPyDetailService;

    public ScriptPage getCardPyList(CardPy cardPy) {
        List<CardPy> rows = new ArrayList<CardPy>();
        int total = 0;
        try {
            rows = cardPyDao.getList(cardPy);
            total = cardPyDao.count(cardPy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScriptPage scriptPage = new ScriptPage();
        scriptPage.setRows(rows);
        scriptPage.setTotal(total);
        return scriptPage;
    }

    public int pyaddList(CardPy cardPy) {
        int result = 0;
        int resultDetail = 0;
        Date d = new Date();
        String time = DateUtil.dateToMin(d, "yyyy-MM-dd HH:mm:ss");
        String effective_days = cardPy.getEffective_days_length();
        if ("999".equals(effective_days)) {
            cardPy.setEffective_days_length("2099-12-31 23:59:59");
        } else {
            String effective_days_length = DateUtil.dateToMoreMonth(time, Integer.parseInt(effective_days), "yyyy-MM-dd HH:mm:ss"); //乘以月份以后的时间
            cardPy.setEffective_days_length(effective_days_length);
        }
        cardPy.setCard_open(1);//激活码
        cardPy.setCard_service(1);//教育VIP
        cardPy.setCreate_time(time);
        try {
            result = this.cardPyDao.pyaddList(cardPy);

            if (result > 0) {
                resultDetail = cardPyDetailService.insertPyDetail(cardPy);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            DATA.error("制卡失败" + e.toString());
            return 0;
        }
    }


    public OpResult openCardPy(String mac, String sn,  String branch_id, String branch_name, String duration, String creator_id, String creator_name) {
       // String url = ApiConstants.AGIO_URL;
        //url = url+"apiCardPy/openCardPy.json";
        String url = "http://localhost:8082/apiCardPy/openCardPy.json";
        Map<String,String> map = new HashMap<String,String>();
        String cardPwd = cardPyDetailService.getVerificationCode(Constants.PY_CARD_STRAIGHT);
        map.put("cardPwd", cardPwd);
        map.put("mac", mac);
        map.put("sn", sn);
        map.put("branch_id", branch_id);
        map.put("branch_name", branch_name);
        map.put("duration", duration);
        map.put("creator_id", creator_id);
        map.put("creator_name", creator_name);
        String link =  DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
        DATA.debug(link);
        HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
        DATA.debug(response.statusCode+"--开通教育VIP->"+response.entityString);
        OpResult op =new OpResult(OpResultTypeEnum.SYSERR, "通讯异常");
        if(response.statusCode==200){
            //通信正常
            JSONObject json =JSONObject.parseObject(response.entityString);
            String code = json.getString("code");
            String desc =json.getString("desc");
            op.setCode(code);
            op.setDesc(desc);
        }
        return op;
    }

    /**
     * @param file
     * @param upload_path
     * @param userName
     * @return
     */
    public String upExcelServer(MultipartFile file, String upload_path, String userName) {
        String fileName = file.getOriginalFilename();
        String name = fileName.substring(0, fileName.indexOf("."));
        String type = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        File uploadFile = new File(upload_path);
        String date = new DateTime().toString("yyyyMMddHHmmss");
        // 如果文件夹不存在则创建
        if (!uploadFile.exists() && !uploadFile.isDirectory()) {
            uploadFile.mkdir();
        }
        String excelPath = upload_path + name + "_" + date + "_" + userName + type;
        FileOutputStream out = null;
        InputStream in = null;
        byte[] buffer = new byte[1024];
        try {
            out = new FileOutputStream(excelPath);
            in = file.getInputStream();
            int len = buffer.length;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
        }
        return excelPath;
    }


    public Data batchOpenCardPy(HttpServletRequest req, String excelPath, Integer branch_id, String branch_name, Integer duration, String creator_id, String creator_name) {
        // String url = ApiConstants.AGIO_URL;
        //url = url+"apiCardPy/batchOpenCardPy.json";
        String url = "http://localhost:8082/apiCardPy/batchOpenCardPy.json";
        Map<String,String> map = new HashMap<String,String>();
        map.put("excelPath", excelPath);
        map.put("branch_id", String.valueOf(branch_id));
        map.put("branch_name", branch_name);
        map.put("duration", String.valueOf(duration));
        map.put("creator_id", creator_id);
        map.put("creator_name", creator_name);
        String link =  DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
        DATA.debug(link);
        HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
        DATA.debug(response.statusCode+"--批量开通教育VIP->"+response.entityString);
        Data data = new Data();
        if(response.statusCode==200){
            data.setCode(1);
            data.setMsg("开通成功");
            return data;
        }
        return data;
    }



    /*public Data batchOpenCardPy(HttpServletRequest req, String excelPath, Integer branch_id, String branch_name, Integer duration, String creator_id, String creator_name) {

        Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        InputStream inputStream = null;
        int plan = 0, suc = 0, reNew = 0, noPara = 0, badMacOrSn = 0, repeatOrderNum = 0;
        String reportLog = "";// 总上传报告
        String reNewInfo = ""; // 重复的明细
        String badMacOrSnInfo = ""; // mac\sn不合法的明细
        String repeatOrderNumInfo = "";// 订单号重复明细
        try {
            inputStream = new FileInputStream(excelPath);
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet readsheet = wb.getSheetAt(0);
            int rowLen = readsheet.getLastRowNum();
            for (int i = 1; i <= rowLen; i++) {
                Row row = readsheet.getRow(i);
                // 当前行为空
                if (this.isBlankRow(row)) {
                    continue;
                }
                try {
                    Card card = new Card();
                    card.setCardType(cardType);
                    card.setSource(4);
                    if (row.getCell(0) != null) {
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        Integer uid = null;
                        try {
                            uid = Integer.parseInt(row.getCell(0).getStringCellValue().trim());
                        } catch (Exception ec) {
                            return this.cardService.retRes(5, "导入的excel第一列格式错误");
                        }
                        card.setUid(uid);
                    }
                    plan = plan + 1; // 总计划数量+1
                    String userNum = null;
                    String orderNum = null;
                    String phone="";
                    if (ParamConstants.BJ_BRANCH.equals(currentUser.getBranchId()) && (row.getCell(3) == null)) {
                        noPara = noPara + 1; // 北京分公司缺少用户号数量+1
                        continue;
                    }
                    String mac = "";
                    String sn = "";
                    if (row.getCell(1) != null) {
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        mac = row.getCell(1).getStringCellValue().trim();
                    }
                    if (row.getCell(2) != null) {
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        sn = row.getCell(2).getStringCellValue().trim();
                    }

                    if(StringUtils.isEmpty(mac) && StringUtils.isEmpty(sn)){
                        //mac sn 都不录入属于合法录入
                    }else{
                        // 判定mac sn合法性
                        if (!this.cardService.isRealUser(mac, sn)) {
                            // 北京分公司mac，sn可以传空
                            if (!StringUtils.isEmpty(mac) || !StringUtils.isEmpty(sn)
                                    || !ParamConstants.BJ_BRANCH.equals(currentUser.getBranchId())) {
                                badMacOrSn = badMacOrSn + 1; // mac,sn信息不合法数量+1
                                if (ParamConstants.BJ_BRANCH.equals(currentUser.getBranchId())) {
                                    badMacOrSnInfo = badMacOrSnInfo + orderNum + ",";
                                } else {
                                    badMacOrSnInfo = badMacOrSnInfo + sn + ",";
                                }
                                continue;
                            }
                        }
                    }
                    if (row.getCell(3) != null) {
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        userNum = row.getCell(3).getStringCellValue().trim();
                    }
                    if (row.getCell(4) != null) {
                        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        orderNum = row.getCell(4).getStringCellValue().trim();
                    }
                    if (row.getCell(5) != null) {
                        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                        phone = row.getCell(5).getStringCellValue().trim();
                    }
                    card = this.getCard(currentUser, mac, sn, phone,activityId, activityName, autoActiveTimeLength, userNum,
                            orderNum,cardType);
                    Integer code = cardService.add(card).getCode();
                    switch (code) {
                        case 1:
                            suc = suc + 1;
                            break;
                        case 2:
                            noPara = noPara + 1;
                            break;
                        case 3:
                            repeatOrderNum = repeatOrderNum + 1;
                            repeatOrderNumInfo = repeatOrderNumInfo + userNum + ",";
                            break;
                        case 4:
                            reNew = reNew + 1;
                            suc = suc + 1;
                            reNewInfo = reNewInfo + userNum + ",";
                            break;
                        default:
                            break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            reportLog = this.getReport(plan, suc, reNew, badMacOrSn, noPara, repeatOrderNum, reNewInfo, badMacOrSnInfo,
                    repeatOrderNumInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return this.cardService.retRes(0, "输入流异常");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    LOG.info("################STREAM CLOSE ERROR#################");
                    ioe.printStackTrace();
                    return this.cardService.retRes(2, "流关闭异常");
                }
            }
        }
        DATA.info(reportLog);
        // System.out.println(reportLog);
        return this.cardService.retRes(1, reportLog);

    }

    public boolean isBlankRow(Row row) {
        if (row == null)
            return true;
        boolean result = true;
        for (int i = row.getFirstCellNum(); i < 10; i++) {
            Cell cell = row.getCell(i);
            String value = "";
            if (cell != null) {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        value = String.valueOf((int) cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        value = String.valueOf(cell.getCellFormula());
                        break;
                    default:
                        break;
                }
                if (!value.trim().equals("")) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
*/


}
