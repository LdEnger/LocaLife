package main.java.com.hiveview.service.award;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.award.AwardPlayDao;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.po.award.AwardPlay;

/**
 * Title：AwardPlayService.java
 * Description：活动管理服务类
 * Company：hiveview.com
 * Author：周恩至
 * Email：zhouenzhi@btte.net 
 * 2016年03月15日 下午15:34:02
 */
@Service
public class AwardPlayService {
	
	@Autowired
	AwardPlayDao awardPlayDao;
	
	//获取活动列表
	public ScriptPage getPlayList(AwardPlay awardPlay) {
		ScriptPage scriptPage = new ScriptPage();
		List<AwardPlay> rows = awardPlayDao.getPlayerList(awardPlay);
		int total = awardPlayDao.count(awardPlay);
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
}
