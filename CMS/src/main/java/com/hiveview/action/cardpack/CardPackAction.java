package main.java.com.hiveview.action.cardpack;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.bo.*;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.cardpack.CardPack;
import com.hiveview.entity.cardpack.ExportCardPack;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.Zone;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.HallService;
import com.hiveview.service.card.CardService;
import com.hiveview.service.cardPack.CardPackService;
import com.hiveview.service.sys.SysRoleService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.service.sys.ZoneService;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.DateUtil;
import com.hiveview.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.common.EnvConstants;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 腾讯卡 奇艺   制作大麦卡
 */
@Controller
@RequestMapping("/cardPack")
public class CardPackAction extends BaseAction {

	@Autowired
	private CardPackService cardPackService;
	@Autowired
	private ZoneCityService zoneCityService;

	private static final Logger DATA = LoggerFactory.getLogger("data");
	private static ResourceBundle R = ResourceBundle.getBundle(EnvConstants.ENV_VER + "_api");
	public static String UPLOAD_PATH = R.getString("upload.path");
	public static String FILE_PATH = R.getString("file.path");

	private static String lockKey="cardPackExport";


	/**
	 * 卡管理 首页
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		//战区信息从城市和战区关联表里查询
		currentUser = zoneCityService.setZoneInfo(currentUser);
		req.setAttribute("currentUser", currentUser);
//		DATA.info("ActivityDisplayInitSessionParam：userId={},roleId={}", new Object[] {currentUser.getUserId(), currentUser.getRoleId()});
//		try{
//			List<VipPackagePriceVo> list = chargePriceApi.getVipChargPriceList();
//			req.setAttribute("list",list);
//		}catch(Exception e){
//			DATA.info("获取计费包异常");
//			e.printStackTrace();
//		}
		return "cardpack/card_pack_index";
	}

	/**
	 * 获取列表
	 * @param ajaxPage
	 * @param cardpack
	 * @return
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getCardList(AjaxPage ajaxPage, CardPack cardpack) {
		ScriptPage scriptPage = null;
		try {
			cardpack.copy(ajaxPage);
			scriptPage = cardPackService.getCardList(cardpack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}

	/**
	 * 批量制卡
	 * @param cardpack
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(HttpServletRequest req,final CardPack cardpack) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Data data = new Data();
		data.setCode(0);
		data.setMsg("添加失败");
		int cardNum=cardpack.getCardNum();
		int branch_id=cardpack.getBranch_id();
		String branch_name=cardpack.getBranch_name();
		if(0==cardNum||cardNum>5000||branch_id<0||null==branch_name||""==branch_name){
			data.setMsg("核对参数后重新输入");
			return data;
		}
		try {
			int bool=0;
			int cardprovider=cardpack.getCard_provider();
			if(null!=currentUser){
				cardpack.setCreator_id(currentUser.getUserId());
				cardpack.setCreator_name(currentUser.getUserName());
			}
			if(cardprovider==0){
				bool = cardPackService.txaddList(cardpack);
			}else if (cardprovider==1) {
				bool = cardPackService.qyaddList(cardpack);
			}else {
				return data;
			}
			if (bool>0){
				data.setCode(bool);
			}
			return data;
		} catch (Exception e) {
//			e.printStackTrace(); //记录错误日志
			return data;
		}

	}


	/**
	 * 批量导出
	 * @return
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public ExportCardPack export(HttpServletRequest req, ExportCardPack exportCard) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String now= df.format(new Date());
		ExportCardPack data = new ExportCardPack();
		data.setCode(0);
		int cardNum=exportCard.getExport_cardNum();//用户要导出多少数量的卡
		int export_renewNum =exportCard.getExport_renewNum();// 要导出 次数(月数)
		int branch_id=exportCard.getBranch_id();
		if(0==cardNum||0==export_renewNum||branch_id<0){
			data.setMsg("请输入导出参数");
			return data;
		}
		if(null == exportCard.getExport_cardProvider()){
			data.setMsg("请输入卡提供方");
			return data;
		}
		synchronized (lockKey) {
			//需要对传入的  exportCard 信息 进行 校验  这里可以放锁操作   先对库进行检索  查看是否有足够的卡信息 可以导出 避免重复按钮 操作 或者 多次请求 造成的 错误。

			int storgeNum=cardPackService.getStorgeNum(exportCard); //库存数量
			if(cardNum>storgeNum){
				data.setMsg("库存"+storgeNum+"数量不足 请重新选择导出数量");
				return data;
			}
			try {
				String fileName = now + "_" + exportCard.getExport_cardNum() + "_" + exportCard.getExport_renewNum();
				data = cardPackService.createCardPackExcelFile(req, UPLOAD_PATH + fileName, exportCard); //创建文件 并 复制到服务器
				if (data.getCode() == 1) {//创建文件成功了
				int result =cardPackService.downLoad(FILE_PATH + fileName + ".xls"); //判断 文件是否可取得   正式 上线 使用这段话 来判断
//					int result = 1;//测试方便  就当检测 文件 是否存在  成功
					if (result > 0) {
						data.setDownload_url(FILE_PATH + fileName + ".xls");//如果文件可以取得  将路径返回给页面 进行下载   并且 对这批导出的卡 写状态 标识已提取
						//标识 已提取
						exportCard.setMaxId(data.getMaxId());
						//设置了导出人员信息
						if (null != currentUser) {
							exportCard.setExport_id(currentUser.getUserId() + "");
							exportCard.setExport_name(currentUser.getUserName());
							exportCard.setExport_version(fileName + ".xls");
						}
						cardPackService.updateExportCardPack(exportCard);
					} else {
						data.setCode(0); //取不到 下载路径  返回无法生成订单
						data.setMsg("无法获取后台生成的 excel文件");
					}
				}
				return data;
			} catch (Exception e) {
//			e.printStackTrace(); //记录错误日志
				return data;
			}
		}
	}

}
