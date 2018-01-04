package main.java.com.hiveview.action.py;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.*;
import com.hiveview.entity.py.CardPy;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.py.CardPyService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;
import java.util.ResourceBundle;

import static com.hiveview.common.EnvConstants.ENV_VER;

@Controller
@RequestMapping("/cardPy")
public class CardPyAction extends BaseAction {

    @Autowired
    private ZoneCityService zoneCityService;

    @Autowired
    private CardPyService cardPyService;

    private static final Logger DATA = LoggerFactory.getLogger("data");

    private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_api");

    public static String UPLOAD_PATH = R.getString("upload.path");
    public static String FILE_PATH = R.getString("file.path");

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
        return "py/card_py";
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ScriptPage getCardList(AjaxPage ajaxPage, CardPy cardPy, HttpServletRequest request) {
        ScriptPage scriptPage = null;
        try {
            cardPy.copy(ajaxPage);
            scriptPage = cardPyService.getCardPyList(cardPy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }

    /**
     * 批量制卡
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Data add(HttpServletRequest req, final CardPy cardPy) {
        Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        Data data = new Data();
        data.setCode(0);
        data.setMsg("添加失败");
        int card_num = cardPy.getCard_num();
        int branch_id = cardPy.getBranch_id();
        String branch_name = cardPy.getBranch_name();
        if (0 == card_num || card_num > 5000 || branch_id < 0 || null == branch_name || "" == branch_name) {
            data.setMsg("核对参数后重新输入");
            return data;
        }
        try {
            int bool = 0;
            if (null != currentUser) {
                cardPy.setCreator_id(currentUser.getUserId());
                cardPy.setCreator_name(currentUser.getUserName());
            }
            bool = cardPyService.pyaddList(cardPy);
            if (bool > 0) {
                data.setCode(bool);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 卡开通
     */
    @RequestMapping(value = "/openCardPy", method = RequestMethod.POST)
    @ResponseBody
    public OpResult openCardPy(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        String creator_id = String.valueOf(currentUser.getUserId());
        String creator_name = currentUser.getUserName();

        String parameters = super.getParameters(request);
        try {
            parameters = URLDecoder.decode(parameters, "UTF-8");
            Map<String, String> parametersMap = StringUtils.linkToMap(parameters);

            String mac = parametersMap.get("mac");
            String sn = parametersMap.get("sn");
            String branch_id = parametersMap.get("branch_id");
            String branch_name = parametersMap.get("branch_name");
            String duration = parametersMap.get("duration");
            if (parametersMap.isEmpty() || StringUtils.isEmpty(mac) || StringUtils.isEmpty(sn)) {
                return new OpResult(OpResultTypeEnum.MSGERR, "参数不全");
            }
            DATA.info("CMS教育VIP parameters={}", new Object[]{parameters});
            OpResult opResult = cardPyService.openCardPy(mac, sn, branch_id, branch_name, duration, creator_id, creator_name);
            return opResult;

        } catch (Exception e) {
            e.printStackTrace();
            DATA.info("CMS教育VIP SystemError:parameters={}", new Object[]{parameters});
            return new OpResult(OpResultTypeEnum.SYSERR, "系统处理异常");
        }
    }

    /**
     * 导入Excel
     */
    @RequestMapping(value = "/uploadExcel")
    @ResponseBody
    public String upLoadExcel(HttpServletRequest req, @RequestParam(value = "file", required = false) MultipartFile file) {
        Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        String result = cardPyService.upExcelServer(file, UPLOAD_PATH, currentUser.getUserName());
        if ("error".equals(result)) {
            return null;
        }
        return result;
    }

    /**
     * 批量开通
     *
     */
    @RequestMapping(value = "/batchOpenCardPy")
    @ResponseBody
    public Data batchOpenCardPy(HttpServletRequest request, String excelPath, Integer branch_id, String branch_name,Integer duration) {
        Data data = new Data();
        Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        String creator_id = String.valueOf(currentUser.getUserId());
        String creator_name = currentUser.getUserName();
        try {
            data = cardPyService.batchOpenCardPy(request, excelPath, branch_id, branch_name,duration,creator_id,creator_name);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.setCode(0);
        return data;
    }

}
