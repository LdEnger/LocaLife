package main.java.com.hiveview.action.py;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.EnvConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.py.CardPyDetail;
import com.hiveview.entity.py.ExportCardPy;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.py.CardPyDetailService;
import com.hiveview.service.sys.ZoneCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/cardPyDetail")
public class CardPyDetailAction extends BaseAction {

    @Autowired
    private ZoneCityService zoneCityService;

    @Autowired
    private CardPyDetailService cardPyDetailService;

    private static final Logger DATA = LoggerFactory.getLogger("data");
    private static ResourceBundle R = ResourceBundle.getBundle(EnvConstants.ENV_VER + "_api");
    public static String UPLOAD_PATH = R.getString("upload.path");
    public static String FILE_PATH = R.getString("file.path");
    private static String lockKey = "cardPyExport";

    @RequestMapping(value = "/show")
    public String show(HttpServletRequest req) {
        Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        if (currentUser == null) {
            return "timeout";
        }
        // 战区信息从城市和战区关联表里查询
        currentUser = zoneCityService.setZoneInfo(currentUser);
        req.setAttribute("currentUser", currentUser);
        // 获取partnerKey 用于激活卡
        String partnerKey = ParamConstants.VIP_PARTNER_KEY;
        req.setAttribute("partnerKey", partnerKey);
        return "py/card_py_detail";
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ScriptPage getCardDetailList(AjaxPage ajaxPage, CardPyDetail cardPyDetail, HttpServletRequest request) {
        ScriptPage scriptPage = null;
        try {
            cardPyDetail.copy(ajaxPage);
            scriptPage = cardPyDetailService.getCardDetailList(cardPyDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }


    @RequestMapping(value = "/exportCardPy")
    @ResponseBody
    public ExportCardPy exportCardPy(HttpServletRequest req, ExportCardPy exportCardPy) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = df.format(new Date());
        ExportCardPy data = new ExportCardPy();
        data.setCode(0);
        synchronized (lockKey) {
            try {
                String fileName = "教育VIP_" + now;
                data = cardPyDetailService.createCardPyExcelFile(req, UPLOAD_PATH + fileName, exportCardPy);
                if (data.getCode() == 1) {//创建文件成功了
                    int result = cardPyDetailService.downLoad(FILE_PATH + fileName + ".xls");
//					int result = 1;//测试方便  就当检测 文件 是否存在  成功
                    if (result > 0) {
                        data.setDownload_url(FILE_PATH + fileName + ".xls");
                    } else {
                        data.setCode(0); //取不到 下载路径  返回无法生成订单
                        data.setMsg("无法获取后台生成的 excel文件");
                    }
                }
                return data;
            } catch (Exception e) {
                e.printStackTrace(); //记录错误日志
                return data;
            }
        }
    }


    @RequestMapping(value = "/exportCardPyDetail")
    @ResponseBody
    public ExportCardPy exportCardPyDetail(HttpServletRequest req, CardPyDetail cardPyDetail) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = df.format(new Date());
        ExportCardPy data = new ExportCardPy();
        data.setCode(0);
        /*首先查询数量不能超过5000条*/
        int total = cardPyDetailService.getCardDetailCount(cardPyDetail);
        if(total > 5000){
            data.setMsg("导出数量，请不要超过5000条！！！");
            return data;
        }
        synchronized (lockKey) {
            try {
                String fileName = "教育VIP详情_" + now;
                data = cardPyDetailService.createCardPyDetailExcelFile(req, UPLOAD_PATH + fileName, cardPyDetail);
                if (data.getCode() == 1) {//创建文件成功了
                    int result = cardPyDetailService.downLoad(FILE_PATH + fileName + ".xls");
//					int result = 1;//测试方便  就当检测 文件 是否存在  成功
                    if (result > 0) {
                        data.setDownload_url(FILE_PATH + fileName + ".xls");
                    } else {
                        data.setCode(0); //取不到 下载路径  返回无法生成订单
                        data.setMsg("无法获取后台生成的 excel文件");
                    }
                }
                return data;
            } catch (Exception e) {
                e.printStackTrace(); //记录错误日志
                return data;
            }
        }
    }


    @RequestMapping(value = "/getCardPyById")
    public CardPyDetail getCardPyById(Integer id) {
        return cardPyDetailService.getCardPyById(id);
    }

    /**
     * 注销教育VIP
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cardPyDetailCancel", method = RequestMethod.POST)
    @ResponseBody
    public OpResult cardPyDetailCancel(HttpServletRequest request) {
        String parameters = super.getParameters(request);
        if (parameters != null) {
            return cardPyDetailService.cardPyDetailCancel(parameters);
        }
        DATA.info("教育VIP注销 parameters is null");
        return new OpResult(OpResultTypeEnum.MSGERR, "缺少必要参数");
    }


}
