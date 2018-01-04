package com.hiveview.service.localLife;

import com.hiveview.dao.localLife.ContentDao;
import com.hiveview.dao.localLife.ContentVersionDao;
import com.hiveview.dao.localLife.ContentVersionScreenshotDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.localLife.ContentVersion;
import com.hiveview.entity.localLife.ContentVersionScreenshot;
import com.hiveview.service.RedisService;
import com.hiveview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title：ContentVersionService.java
 * Description：内容管理截图
 * Company：hiveview.com
 * Author：swj
 * Email：songwenjian@btte.net
 * 2016年06月22日 下午15:34:02
 */
@Service
public class ContentVersionService {

    @Autowired
    ContentDao contentDao;

    @Autowired
    ContentVersionDao contentVersionDao;

    @Autowired
    ContentVersionScreenshotDao contentVersionScreenshotDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private flushRedisService flushRedisService;
	//获取版本列表
	public ScriptPage getList(ContentVersion contentVersion) {
		List<ContentVersion> rows = contentVersionDao.getList(contentVersion);
		int total = contentVersionDao.count(contentVersion);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	public boolean update(ContentVersion contentVersion) {
		boolean flag = false;
		int count = contentVersionDao.update(contentVersion);
		if(count==1){
			flag = true;
            this.flushRedisService.flushRedisByContentId("contentCache",contentVersion.getId());
		}
		return flag;
	}

	public ContentVersion getContentVersionById(ContentVersion contentId){
	    return this.contentVersionDao.getContentVersionById(contentId.getId());
    }
	
	public boolean del(ContentVersion contentVersion){
		boolean flag = false;
        int count = contentVersionDao.delete(contentVersion);
        if(count>0){
            flag = true;
            this.flushRedisService.flushRedisByContentId("contentCache",contentVersion.getId());
        }
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(ContentVersion contentVersion) {
		boolean flag = false;
		int activityId = contentVersionDao.save(contentVersion);
        if(activityId>0){
            flag = true;
            this.flushRedisService.flushRedisByContentId("contentCache",contentVersion.getId());
        }
        return flag;
	}

	public int getMaxSequence(){
		Integer maxSequence = contentVersionScreenshotDao.getMaxSequence();
		if(maxSequence==null){
			maxSequence=0;
		}
		return maxSequence;
	}
	
	public ContentVersionScreenshot contentVersionScreenshotBySeq(int sequence){
        ContentVersionScreenshot result = contentVersionScreenshotDao.getBySeq(sequence);
		return result;
	}
	/**
	 * 通过sequence获取sequence大一的活动实体
	 * @param sequence
	 * @return
	 */
	public Integer getBiggerSeq(int sequence){
		Integer result = contentVersionScreenshotDao.getBigger(sequence);
		return result;
	}
	
	public Integer getSmallerSeq(int sequence){
		Integer result = contentVersionScreenshotDao.getSmaller(sequence);
		return result;
	}

    //获取内容版本截图
    public ScriptPage getVersionScreenshotList(ContentVersionScreenshot contentVersionScreenshot) {
        List<ContentVersionScreenshot> rows = contentVersionScreenshotDao.getList(contentVersionScreenshot);
        int total = contentVersionScreenshotDao.count(contentVersionScreenshot);
        ScriptPage scriptPage = new ScriptPage();
        scriptPage.setRows(rows);
        scriptPage.setTotal(total);
        return scriptPage;
    }

    //获取版本截图数量
    public int getCountByVersionId(ContentVersionScreenshot contentVersionScreenshot) {
        int total = contentVersionScreenshotDao.count(contentVersionScreenshot);
        return total;
    }

    public boolean delScreenshot(ContentVersionScreenshot contentVersionScreenshot){
        boolean flag = true;
        int count = contentVersionScreenshotDao.delete(contentVersionScreenshot);
        if(count>0){
            flag = true;
            ContentVersion contentVersion = contentVersionDao.getContentVersionById(contentVersionScreenshot.getVersionId());
            if(null != contentVersion){
                this.flushRedisService.flushRedisByContentId("contentCache",contentVersion.getId());
            }
        }
        return flag;
    }

    public boolean updateScreenshot(ContentVersionScreenshot contentVersionScreenshot) {
        boolean flag = false;
        int count = contentVersionScreenshotDao.update(contentVersionScreenshot);
        if(count==1){
            flag = true;
            ContentVersion contentVersion = contentVersionDao.getContentVersionById(contentVersionScreenshot.getVersionId());
            if(null != contentVersion){
               this.flushRedisService.flushRedisByContentId("contentCache",contentVersion.getId());
            }
        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    public boolean addScreenshot(ContentVersionScreenshot contentVersionScreenshot) {
        boolean flag = true;
        int sequence = this.getMaxSequence();
        sequence++;
        contentVersionScreenshot.setSeq(sequence);
        int count = contentVersionScreenshotDao.save(contentVersionScreenshot);
        if(count>0){
            flag = true;
            ContentVersion contentVersion = contentVersionDao.getContentVersionById(contentVersionScreenshot.getVersionId());
            if(null != contentVersion){
               this.flushRedisService.flushRedisByContentId("contentCache",contentVersion.getId());
            }
        }
        return flag;
    }

    public boolean move(int sequence,int type) {//type==1的时候是上移，其他的是下移
        boolean flag = false;
        ContentVersionScreenshot aa = this.contentVersionScreenshotBySeq(sequence);
        Integer changeSequence = 0;
        if(type==1){
            changeSequence=this.getBiggerSeq(sequence);
        }else{
            changeSequence=this.getSmallerSeq(sequence);
        }
        ContentVersionScreenshot biggerAa = this.contentVersionScreenshotBySeq(changeSequence);
        aa.setSeq(changeSequence);
        biggerAa.setSeq(sequence);
        boolean aaFlag = this.updateScreenshot(aa);
        boolean biggerFlag = false;
        if(aaFlag){
            biggerFlag = this.updateScreenshot(biggerAa);
        }
        if(biggerFlag){
            flag = true;
        }
        return flag;
    }

    public void redisDelByConstantId(Integer id){
        String key = Constant.CONTENT_CACHE+"_"+id;
        redisService.del(key);
    }

    public Integer getVersionCount(ContentVersion contentVersion){
        Integer integer = contentVersionDao.getVersionCount(contentVersion);
        return integer;
    }

}
