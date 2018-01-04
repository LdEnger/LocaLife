package main.java.com.hiveview.service.py;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.py.CardPyDetailDao;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.py.CardPy;
import com.hiveview.entity.py.CardPyDetail;
import com.hiveview.entity.py.ExportCardPy;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.api.DeviceApi;
import com.hiveview.util.Constants;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

@Service
public class CardPyDetailService {

    private static final Logger DATA = LoggerFactory.getLogger("data");

    @Autowired
    private CardPyDetailDao cardPyDetailDao;

    @Autowired
    private DeviceApi deviceApi;

    public ScriptPage getCardDetailList(CardPyDetail cardPyDetail) {
        List<CardPyDetail> rows = new ArrayList<CardPyDetail>();
        int total = 0;
        try {
            rows = cardPyDetailDao.getList(cardPyDetail);
            total = cardPyDetailDao.count(cardPyDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScriptPage scriptPage = new ScriptPage();
        scriptPage.setRows(rows);
        scriptPage.setTotal(total);
        return scriptPage;
    }

    public int insertPyDetail(CardPy cardPy) {
        int result = 0;
        List<CardPyDetail> parm = new ArrayList<CardPyDetail>();
        int card_num = cardPy.getCard_num();//制卡数量
        Integer card_open = cardPy.getCard_open(); // 开通方式 1 激活码
        Integer card_service = cardPy.getCard_service();// 1 教育VIP
        Integer duration = cardPy.getDuration();//时长
        String effective_days_length = cardPy.getEffective_days_length();//激活码有效期
        String create_time = cardPy.getCreate_time();//创建时间
        Integer branch_id = cardPy.getBranch_id();//分公司id
        String branch_name = cardPy.getBranch_name();//分公司名
        Integer py_id = cardPy.getId();

        try {
            for (int i = 0; i < card_num; i++) {
                CardPyDetail cardPyDetail = new CardPyDetail();
                cardPyDetail.setCard_open(card_open);
                String card_order_py = getVerificationCode(Constants.PY_CARD_ACTIVATION);
                cardPyDetail.setCard_order_py(card_order_py);
                cardPyDetail.setUse_status(1);//未使用
                cardPyDetail.setCard_service(card_service);
                cardPyDetail.setDuration(duration);
                cardPyDetail.setCreate_time(create_time);
                cardPyDetail.setEffective_days_length(effective_days_length);
                cardPyDetail.setBranch_id(branch_id);
                cardPyDetail.setBranch_name(branch_name);
                cardPyDetail.setPy_id(py_id);
                cardPyDetail.setCard_cancel(1);//未激活
                parm.add(cardPyDetail);
            }
            result = this.cardPyDetailDao.insertPyDetail(parm);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            DATA.error("批量制卡失败" + e.toString());
            return 0;
        }


    }

    /**
     * 生成卡密
     *@card_provider 卡提供者
     * @return 17位字符串
     */
    public String getVerificationCode(String card_provider) {
        if(null==card_provider||"".equalsIgnoreCase(card_provider)){
            card_provider="0";//默认腾讯
        }
        return  this.getRandom(5) + " " + this.getRandom(5) + " " + this.getRandom(5) + " "
                + this.getRandom(5) + " " +card_provider;
    }
    /**
     * 生成相应位数的随机数
     */
    private String getRandom(int size) {
        Random random = new Random();
        int seed = (int) (Math.pow(10, size - 1));
        String number = random.nextInt(seed) + "";
        while (number.length() < size - 1) {
            number = random.nextInt(10) + "" + number;
        }
        return number;
    }

    public ExportCardPy createCardPyExcelFile(HttpServletRequest req, String excelPath, ExportCardPy exportCardPy) {
        ExportCardPy res=new ExportCardPy();
        DATA.info(" 导出批次号为： "+exportCardPy.getPy_id());
        ArrayList<CardPyDetail> allCardInfo= cardPyDetailDao.getExportAllList(exportCardPy);
        DATA.info("查询出的数量 "+ allCardInfo.size());

        FileOutputStream fos = null;
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("教育VIP列表");
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        XSSFRow titleRow = sheet.createRow(0);
        int titleIndex =0;
        titleRow.createCell(titleIndex).setCellValue("激活码");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("使用状态");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("激活码到期时间");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("分公司名称");titleIndex++;

        try {
            for (int i = 1; i <= allCardInfo.size(); i++) {
                int index = 0;
                XSSFRow row = sheet.createRow(i);
                CardPyDetail card = allCardInfo.get(i-1);
                row.createCell(index).setCellValue(card.getCard_order_py()==null?"":card.getCard_order_py());index++;
                row.createCell(index).setCellValue(card.getUse_status() == 1?"未使用":"已使用");index++;

                String effective_days_length = card.getEffective_days_length();
                String effective_days="";
                if(StringUtils.isNotEmpty(effective_days_length)){
                    if ("2099-12-31".equals(effective_days_length.substring(0, 10))) {
                        effective_days="永久有效";
                    } else {
                        effective_days = effective_days_length;
                    }
                }
                row.createCell(index).setCellValue(effective_days);index++;

                row.createCell(index).setCellValue(card.getBranch_name() == null?"":card.getBranch_name());index++;
            }
            fos = new FileOutputStream(excelPath + ".xls");
            wb.write(fos);
            fos.close();
            DATA.info("导出 教育VIP列表 文件创建 完成");
        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("3、系统提示：教育VIP Excel文件导出失败，原因：" + e.toString());
            res.setCode(0);
            return res;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    DATA.info("流关闭异常" + e.toString());
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    /**
     * 测试现在路径是否可用
     * @param url
     * @return
     */
    public int downLoad(String url) {
        DATA.info("下载教育VIP文件:  " + url);
        try {
            for (int i = 1; i < 5; i++) {
                HiveHttpResponse response = HiveHttpGet.getEntity(url, HiveHttpEntityType.STRING);
                if (response.statusCode == HttpStatus.SC_OK) {
                    return 1;
                }
                Thread.sleep(500);// 文件在服务器上同步存在时差，此处轮询三遍，若超过一定时限则代表文件同步有问题
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCardDetailCount(CardPyDetail cardPyDetail) {
       int total = cardPyDetailDao.count(cardPyDetail);
       return total;
    }

    public ExportCardPy createCardPyDetailExcelFile(HttpServletRequest req, String excelPath, CardPyDetail cardPyDetail) {

        ExportCardPy res=new ExportCardPy();
        ArrayList<CardPyDetail> allCardInfo= cardPyDetailDao.getExportDetailAllList(cardPyDetail);
        DATA.info("查询教育VIP详情的数量 "+ allCardInfo.size());

        FileOutputStream fos = null;
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("教育VIP详情列表");
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        XSSFRow titleRow = sheet.createRow(0);
        int titleIndex =0;
        titleRow.createCell(titleIndex).setCellValue("mac");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("sn");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("开通方式");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("使用状态");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("服务");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("时长");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("制卡时间");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("开通时间");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("激活码到期时间");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("分公司名称");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("激活码");titleIndex++;
        titleRow.createCell(titleIndex).setCellValue("注销状态");titleIndex++;

        try {
            for (int i = 1; i <= allCardInfo.size(); i++) {
                int index = 0;
                XSSFRow row = sheet.createRow(i);
                CardPyDetail card = allCardInfo.get(i-1);
                row.createCell(index).setCellValue(card.getMac()==null?"-":card.getMac());index++;
                row.createCell(index).setCellValue(card.getSn() == null?"-":card.getSn());index++;
                row.createCell(index).setCellValue(card.getCard_open()==1?"激活码":"直冲");index++;
                row.createCell(index).setCellValue(card.getUse_status()==1?"未使用":"已使用");index++;
                row.createCell(index).setCellValue(card.getCard_service() == 1?"教育VIP":"-");index++;

                Integer duration = card.getDuration();
                String duration_month="";
                if (1 == duration) {
                    duration_month="3个月";
                } else if (2 == duration) {
                    duration_month="6个月";
                } else if (3 == duration) {
                    duration_month= "12个月";
                } else if (4 == duration) {
                    duration_month="24个月";
                }
                row.createCell(index).setCellValue(duration_month);index++;
                row.createCell(index).setCellValue(card.getCreate_time() == null?"-":card.getCreate_time());index++;
                row.createCell(index).setCellValue(card.getOpen_time()==null?"-":card.getOpen_time());index++;

                String effective_days_length = card.getEffective_days_length();
                String effective_days="";
                if(StringUtils.isNotEmpty(effective_days_length)){
                    if ("2099-12-31".equals(effective_days_length.substring(0, 10))) {
                        effective_days="永久有效";
                    } else {
                        effective_days = effective_days_length;
                    }
                }
                row.createCell(index).setCellValue(effective_days);index++;
                row.createCell(index).setCellValue(card.getBranch_name()==null?"-":card.getBranch_name());index++;
                row.createCell(index).setCellValue(card.getCard_order_py()==null?"-":card.getCard_order_py());index++;
                Integer card_cancel = card.getCard_cancel();
                String cancel = "";
                if (1 == card_cancel) {
                    cancel = "未激活";
                } else if (2 == card_cancel) {
                    cancel = "可注销";
                } else if (3 == card_cancel) {
                    cancel = "已注销";
                } else if (4 == card_cancel) {
                    cancel = "已过期";
                } else {
                    cancel = "-";
                }
                row.createCell(index).setCellValue(cancel);index++;

            }
            fos = new FileOutputStream(excelPath + ".xls");
            wb.write(fos);
            fos.close();
            DATA.info("导出 教育VIP详情列表 文件创建 完成");
        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("3、系统提示：教育VIP详情 Excel文件导出失败，原因：" + e.toString());
            res.setCode(0);
            return res;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    DATA.info("流关闭异常" + e.toString());
                    e.printStackTrace();
                }
            }
        }
        return res;
    }


    public CardPyDetail getCardPyById(Integer id) {
        return cardPyDetailDao.getCardPyById(id);
    }

    public OpResult cardPyDetailCancel(String parameters) {
        try {
            parameters = URLDecoder.decode(parameters, "UTF-8");
            Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
            DATA.info("教育VIP注销 [cancelCard]parametersMap={}", new Object[] { parametersMap.toString() });
            if (parametersMap.isEmpty() || StringUtils.isEmpty(parametersMap.get("userId"))
                    || StringUtils.isEmpty(parametersMap.get("orderId"))) {
                return new OpResult(OpResultTypeEnum.MSGERR);
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", parametersMap.get("userId"));
            map.put("orderId", parametersMap.get("orderId"));
            String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
            if (link.equals(parameters)) {
                //本地验证通过，开始正式退单
                CardPyDetail cardPyDetail = this.cardPyDetailDao.getCardPyByOrderId(parametersMap.get("orderId"));
                if(cardPyDetail.getUse_status() == 1 || cardPyDetail.getCard_cancel() == 1){
                    //未激活的卡,点注销，直接消掉，
                    if (cardPyDetail != null) {
                        cardPyDetail.setCard_cancel(3);//已注销
                        cardPyDetail.setUse_status(2);//已使用
                        try {
                            this.cardPyDetailDao.updateCardPyDetail(cardPyDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return new OpResult(OpResultTypeEnum.SUCC);
                }else{
                    String mac =cardPyDetail.getMac();
                    String sn =cardPyDetail.getSn();
                    String model =deviceApi.getDeviceModel(mac, sn);
                    String vip32Url = ApiConstants.VIP32_URL;
                    String tempurl =vip32Url+"api/open/special/templet/getTemplet/"+model+"-"+mac+"-"+sn+"-1.0.json";
                    String exiturl =vip32Url+"api/open/special/vipOrder/refundFreeOrder.json";
                    HiveHttpResponse res = HiveHttpGet.getEntity(tempurl, HiveHttpEntityType.STRING);
                    String templetId = "1";
                    if (res != null) {
                        String entityString = res.entityString;
                        System.out.println(entityString);
                        DATA.info(sn+"教育VIP注销--->"+entityString);
                        if (entityString != null) {
                            JSONObject jo = JSONObject.parseObject(entityString);
                            templetId = jo.getJSONObject("data").getJSONObject("result").getString("templetId");
                        }
                    }
                    Map<String,String> cmap = new HashMap<String,String>();
                    cmap.put("templateId", templetId);
                    cmap.put("orderId", cardPyDetail.getCard_order_py());
                    cmap.put("userId", cardPyDetail.getUser_id());
                    //cmap.put("versionNum", cond.getVersions());
                    String clink =  DMPayHelper.toLinkForNotifyWithKey(cmap, "863b4ec37d93eb96276ca74d04edf66f");
                    System.out.println(clink);
                    DATA.info(sn+"教育VIP注销--->"+clink);
                    HiveHttpResponse response = HiveHttpPost.postString(exiturl, clink, HiveHttpEntityType.STRING);
                    DATA.info(sn+"教育VIP注销--->"+response.statusCode+"||"+response.entityString);

                    if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
                        DATA.info("教育VIP注销StatusCodeError:statusCode={},parametersMap={}",
                                new Object[] { response.statusCode, parametersMap.toString() });
                        return new OpResult(OpResultTypeEnum.SYSERR);
                    }
                    JSONObject result = JSONObject
                            .parseObject(JSONObject.parseObject(response.entityString).getString("data"));
                    if (!"N000000".equals(result.getString("code"))) {
                        DATA.info("教育VIP注销NotifyResultError:result={},parametersMap={}",
                                new Object[] { result, parametersMap.toString() });
                        return new OpResult(OpResultTypeEnum.SYSERR);
                    }
                    // 卡状态置为已注销
                    if (cardPyDetail != null) {
                        cardPyDetail.setCard_cancel(3);//已注销
                        cardPyDetail.setUse_status(2);//已使用
                        try {
                            this.cardPyDetailDao.updateCardPyDetail(cardPyDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return new OpResult(OpResultTypeEnum.SUCC);
                }
            }
            DATA.info("教育VIP注销Unsafe:link={},parameters={}", new Object[] { link, parameters });
            return new OpResult(OpResultTypeEnum.UNSAFE);
        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("教育VIP注销systemError:parameters={}", new Object[] { parameters });
            return new OpResult(OpResultTypeEnum.SYSERR);
        }
    }


}
