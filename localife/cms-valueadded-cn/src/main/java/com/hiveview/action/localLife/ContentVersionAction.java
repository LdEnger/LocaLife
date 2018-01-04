package com.hiveview.action.localLife;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.ContentVersion;
import com.hiveview.entity.localLife.ContentVersionScreenshot;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.localLife.ContentVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Antony
 * Date: 16-6-3
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/localLifeContentVersion")
public class ContentVersionAction {

    private static final Logger DATA = LoggerFactory.getLogger("data");

    @Autowired
    private ContentVersionService contentVersionService;

    @RequestMapping(value="/show")
    public String show(HttpServletRequest req){
        /*Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
        SysUser currentUser = (SysUser) obj;
        if(currentUser==null){
            return "timeout";
        }*/
        int contentId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("contentId", contentId);
        return "localLife/contentVersion";
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ScriptPage getList(AjaxPage ajaxPage,ContentVersion contentVersion) {
        ScriptPage scriptPage = null;
        try {
            contentVersion.copy(ajaxPage);
            scriptPage = contentVersionService.getList(contentVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Data update(ContentVersion contentVersion) {
        Data data = new Data();
        try {
            ContentVersion contentVersionCount = new ContentVersion();
/*            contentVersionCount.setContentId(contentVersion.getId());*/
            contentVersionCount.setId(contentVersion.getId());
            contentVersionCount.setVersionNumber(contentVersion.getVersionNumber());
/*            contentVersionCount.setVersionNumber(contentVersion.getVersionNumber());*/
            if(contentVersion.getPanDuan() == 1){
                boolean bool = contentVersionService.update(contentVersion);
                if (bool){
                    data.setCode(1);
                }else{
                    data.setCode(0);
                }
            }else{
            ContentVersion c = new ContentVersion();

            c = contentVersionService.getContentVersionById(contentVersion);
            Integer total = contentVersionService.getVersionCount(contentVersionCount);

            if( contentVersionCount.getContentId()==( contentVersion.getContentId())){
                boolean bool = contentVersionService.update(contentVersion);
                if (bool){
                    data.setCode(1);
                }else{
                    data.setCode(0);
                }
            }else {
                if (total > 0) {
                    data.setCode(3);
                    return data;
                }
                boolean bool = contentVersionService.update(contentVersion);
                if (bool) {
                    data.setCode(1);
                } else {
                    data.setCode(0);
                }
            }}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Data add(ContentVersion contentVersion) {
        Data data = new Data();
        try {
            ContentVersion contentVersionCount = new ContentVersion();
            contentVersionCount.setContentId(contentVersion.getContentId());
            contentVersionCount.setVersionNumber(contentVersion.getVersionNumber());
            Integer total = contentVersionService.getVersionCount(contentVersionCount);

            if(total > 0){
                data.setCode(3);
                return data;
            }
            boolean bool = contentVersionService.add(contentVersion);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Data del(ContentVersion contentVersion) {
        Data data = new Data();
        try {
            boolean bool = contentVersionService.del(contentVersion);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /***
     * 获取版本截图
     * @param ajaxPage
     * @param contentVersionScreenshot
     * @return
     */
    @RequestMapping(value = "/getVersionScreenshotList")
    @ResponseBody
    public ScriptPage getVersionScreenshotList(AjaxPage ajaxPage,ContentVersionScreenshot contentVersionScreenshot) {
        ScriptPage scriptPage = null;
        try {
            contentVersionScreenshot.copy(ajaxPage);
            scriptPage = contentVersionService.getVersionScreenshotList(contentVersionScreenshot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptPage;
    }

    /***
     * 删除版本截图
     * @param contentVersionScreenshot
     * @return
     */
    @RequestMapping(value = "/delScreenshot")
    @ResponseBody
    public Data delScreenshot(ContentVersionScreenshot contentVersionScreenshot) {
        Data data = new Data();
        try {
            boolean bool = contentVersionService.delScreenshot(contentVersionScreenshot);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /***
     * 修改版本截图
     * @param contentVersionScreenshot
     * @return
     */
    @RequestMapping(value = "/updateScreenshot")
    @ResponseBody
    public Data updateScreenshot(ContentVersionScreenshot contentVersionScreenshot) {
        Data data = new Data();
        try {
            boolean bool = contentVersionService.updateScreenshot(contentVersionScreenshot);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /***
     * 添加版本截图
     * @param contentVersionScreenshot
     * @return
     */
    @RequestMapping(value = "/addScreenshot")
    @ResponseBody
    public Data addScreenshot(ContentVersionScreenshot contentVersionScreenshot) {
        Data data = new Data();
        try {
            boolean bool = contentVersionService.addScreenshot(contentVersionScreenshot);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 移动版本截图
     * @param sequence 当前要移动的活动的排序序号
     * @param type 此参数==1时，是上移。==2时是下移
     * @return
     */
    @RequestMapping(value = "/move")
    @ResponseBody
    public Data up(int sequence,int type) {
        Data data = new Data();
        try {
            boolean bool = contentVersionService.move(sequence,type);
            if (bool){
                data.setCode(1);
            }else{
                data.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /***
     * 获取版本截图数量
     * @param versionId
     * @return
     */
    @RequestMapping(value = "/getCountByVersionId")
    @ResponseBody
    public Data getCountByVersionId(int versionId) {
        Data data = new Data();
        try {
            ContentVersionScreenshot cvs = new ContentVersionScreenshot();
            cvs.setVersionId(versionId);
            int count = contentVersionService.getCountByVersionId(cvs);
                data.setCode(1);
                data.setMsg(count+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
