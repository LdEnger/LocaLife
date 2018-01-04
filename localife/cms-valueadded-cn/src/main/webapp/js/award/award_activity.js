//ajax同步
$.ajaxSetup({
  async: false
});


/*
 * 控件修改
 */
//easyui-datetimebox日期时间控件输出格式
$.fn.datebox.defaults.formatter = function(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	if (d < 10) {
		d = "0" + d;
	}
	if (m < 10) {
		m = "0" + m;
	}
	return y + '-' + m + '-' + d;
}



/*
 * 工具 
 */
//日期格式转化
function longToDate(l,type) {
	var dataObj = new Date(); // 日期对象
	dataObj.setTime(l);
	var y = dataObj.getFullYear();
	var m = dataObj.getMonth() + 1;
	var d = dataObj.getDate();
	if (d < 10) {
		d = "0" + d;
	}
	if (m < 10) {
		m = "0" + m;
	}
	if(type==1){
		return y + '-' + m + '-' + d + " " + dataObj.getHours() + ":"
		+ dataObj.getMinutes() + ":" + dataObj.getSeconds() + "";
	}else{
		return y + '-' + m + '-' + d;
	}
}



/*
 * 业务
 */
//表格参数
var parameter = {};
//消息框标题
var titleInfo = "提示信息";
//消息框弹出时间
var timeoutValue = 2000;
//奖品设置数量
var idSum = 1;
//查询活动信息
function queryActivity() {
	$('#query_activity').click(function() {
		initAwardInfo();
	});
}
//将button按钮赋予图片上传功能
function buttonUpload(button, img) {
	new AjaxUpload(button, {
		action : 'util/upload.json',
		responseType : 'json',
		autoSubmit : true,
		name : 'file',// 文件对象名称（不是文件名）
		onSubmit : function(file, ext) {
			if (!(ext && /^(png|PNG|JPEG|jpeg|JPG|jpg)$/.test(ext))) {
				alert("请上传正确格式的图片");
				return false;
			}
		},
		onComplete : function(file, data) {
			alert("上传成功");
			img.attr("src", data.msg);
		}
	});
}
//活动logo
function uploadLogo() {
	var button = $("#uploadLogoUrl");
	var img = $("#logoUrl");
	buttonUpload(button, img);
}
//活动列表大背景图
function uploadBg() {
	var button = $("#uploadBgUrl");
	var img = $("#bgUrl");
	buttonUpload(button, img);
}
//活动进行时封面
function uploadBeginProfile() {
	var button = $("#uploadInfoBgUrl");
	var img = $("#infoBgUrl");
	buttonUpload(button, img);
}
//活动结束封面
function uploadEndProfile() {
	var button = $("#uploadPlayingBgUrl");
	var img = $("#playingBgUrl");
	buttonUpload(button, img);
}
//奖品icon
function uploadAwardIcon(k) {
	var button = $("#uploadAwardIcon" + k);
	var img = $("#awardIconUrl" + k);
	buttonUpload(button, img);
}
//活动奖品图片
function uploadAwardPic(k) {
	var button = $("#uploadAwardPic" + k);
	var img = $("#awardPicUrl" + k);
	buttonUpload(button, img);
}
//奖品属性联动模块显示功能
function selectProperty(id) {
	/*
	 * hll:增加源码
	 */
	$('#awardCodeType'+id).attr('disabled',false);
	
	var value = $("#awardProperty" + id).val();
	$("#realAward" + id).attr("style", "display:none;");
	$("#vipHours" + id).attr("style", "display:none;");
	$("#ml" + id).attr("style", "display:none;");
	$("#ticket" + id).attr("style", "display:none;");
	$("#discount" + id).attr("style", "display:none;");
	if (value == 1) {
		$("#realAward" + id).attr("style", "");
	}
	else if(value==2){
		/*
		 * hll:增加源码
		 */
		$('#awardCodeType'+id).val(1);
		$('#awardCodeType'+id).attr('disabled',true);
	}
	else if (value == 3) {
		$("#vipHours" + id).attr("style", "");
	} else if (value == 4) {
		$("#ticket" + id).attr("style", "");
	} else if (value == 5) {
		$("#ml" + id).attr("style", "");
	} else if (value == 6) {
		$("#discount" + id).attr("style", "");
	}
}
//填写用户信息显示隐藏
function selectUserInfo(id) {
	var value = $("#awardQrcodeFlag" + id).val();
	if (value == 1) {
		$("#userInfo" + id).attr("style", "");
	} else {
		$("#userInfo" + id).attr("style", "display:none;");
	}
}
//ajax动态给选项框赋值
function setVideoSelect(url, select) {
	$.post(url, function(json) {
		var indexText = "";
		for (var i = 0; i < json.length; i++) {
			indexText += '<option value="' + json[i].id + '">' + json[i].name
					+ '</option>';
		}
		select.html(indexText);
	}, "json");
}
//电影票
//给电影票选项框赋值
function setTicket(id) {
	var url = "video/getBlueVideoList.json";
	var select = $("#ticketProducts" + id);
	setVideoSelect(url, select);
}
//折扣券
//给VIP或是极清影厅选项框赋值
function setVipAndBlue(id) {
	var vipSelect = $("#vipProducts" + id);
	var vipUrl = "video/getVipVideoList.json";
	setVideoSelect(vipUrl, vipSelect);
	var blueSelect = $("#blueProducts" + id);
	var blueUrl = "video/getBlueVideoList.json";
	setVideoSelect(blueUrl, blueSelect);
}
//折扣券选择内容
function setValue(id){
	$("#vipProducts"+id).attr("style","display:none");
	$("#blueProducts"+id).attr("style","display:none");
	var awardVideoType = $('#awardVideoType'+id).val();
	/*hll:注释源码
	if(awardVideoType==2009){
		$("#vipProducts"+id).attr("style"," ");
	}else{
		$("#blueProducts"+id).attr("style"," ");
	}
	*/
	
	/*
	 * hll:增加源码
	 */
	if(awardVideoType==2009){
		$("#vipProducts"+id).attr("style"," ");
	}
	if(awardVideoType==2006){
		$("#blueProducts"+id).attr("style"," ");
	}
	
}
function setHidden() {
	$("#id1").val(0);
	$("#awardType1").val('');
	$("#awardName1").val('');
	$('#awardPicUrl1').attr("src", "");
	$('#awardIconUrl1').attr("src", "");
	
	/*
	 * hll:增加代码
	 */
	$('#awardDesc1').val('');
	
	/*
	 * hll:增加源码
	 */
	$('#availableBeginTime1').datebox('setValue','');
	$('#availableEndTime1').datebox('setValue','');
	
	$("#awardAmount1").val('');
	$("#winLimitDay1").val('');
	$("#awardCodeType1").val(1);
	/*hll:注释
	$("#awardProperty1").val(1);
	*/
	
	
	/*
	 * hll:增加源码
	 */
	$("#awardProperty1").val(1);
	selectProperty(1);
	$("#awardQrcodeFlag1").val(1);
	selectUserInfo(1);
	$("input[name=userInfoType1]").removeAttr("checked");
	$("#awardVipDuration1").val("");
	$("#awardMlAmount1").val("");
		/*
		 * hll:增加源码
		 */
		$('#awardVideoType1').val(0);
		setValue(1);
	$("#awardDiscount1").val('');
	
	
	
	
	/*hll:注释源码
	$("#vipHours1").attr("style", "display:none");
	$(".showPlayerType").attr("style", "display:none");
	$("#ml1").attr("style", "display:none");
	$("#discount1").attr("style", "display:none");
	$("#ticket1").attr("style", "display:none");
	*/
	
	/*hll:注释源码
	var count = idSum;
	for (var int = 2; int <= count; int++) {
		if (idSum == 1) {
			return;
		} else {
			$('#award' + int).remove();
			$('#realAward' + int).remove();
			$('#vipHours' + int).remove();
			$('#ml' + int).remove();
			$('#discount' + int).remove();
			$('#ticket' + int).remove();
			idSum = parseInt(idSum) - parseInt(1);
		}
	}
	*/
	
	/*
	 * hll:增加源码
	 */
	document.getElementById("awardList").innerHTML='';
	idSum=1;
}
//清空窗口控件
function reset() {
	$('#id').val(0);
	$('#title').val('');
	$("#type").val(0);
	$('#logoUrl').attr("src", "");
	$('#bgUrl').attr("src", "");
	$('#infoBgUrl').attr("src", "");
	$('#endTitle').val(' ');
	$('#playingBgUrl').attr("src", "");
	$('#endText').val('');
	$('#beginTime').datebox('setValue', '');
	$('#endTime').datebox('setValue', '');
	$('#activityDesc').val('');
	$('#awardWinRatio').val('');
	$("input[name=awardWinAgain]").removeAttr("checked");// 清除是否可以重复抽奖
	
	/*
	 * hll:增加源码
	 */
	$("input[name=awardWinAgain]")[0].checked=true;
	
	$("input[name=awardPlayerType]").removeAttr("checked");// 抽奖适用人群
	
	/*
	 * hll:增加源码
	 */
	$("input[name=awardPlayerType]")[0].checked=true;
	
	$('#payBeginTime').datebox('setValue', '');
	$('#payEndTime').datebox('setValue', '');
	$("#paySum").val(" ");
	
	/*
	 * hll:增加源码
	 */
	$(".showPlayerType").attr("style", "display:none");
	
	$('#awardRemark').val('');
	$("input[name=phoneBindType]").removeAttr("checked");
	
	/*
	 * hll:增加源码
	 */
	$("input[name=phoneBindType]")[1].checked=true;
	
	$('#playLimitDay').val(0);
	$('#playPromptDay').val('');
	$('#playLimitTotal').val(0);
	$('#playPromptTotal').val('');
	$('#playPromptWin').val('');
	$('#playPromptLost').val('');
	
	/*hll:注释源码
	$("#awardProperty1").val(1);
	selectProperty(1);
	$("#awardQrcodeFlag1").val(1);
	selectUserInfo(1);
	$("input[name=userInfoType1]").removeAttr("checked");
	$("#awardVipDuration1").val(" ");
	$("#awardMlAmount1").val(" ");
	$("#awardDiscount1").val('');
	*/
	setHidden();// 将该隐藏界面隐藏
}
//显示基础设置模块
function firstTab() {
	$("#tabOne").attr("style", "");
	$("#tabTwo").attr("style", "display:none");
	$("#tabThree").attr("style", "display:none");
}
//增加活动信息
function addAwardActivity() {
	$('#add_award').click(function() {
		reset();
		firstTab();
		$("#award_activity_dialog").dialog('open');
	});
}
//-------------------------------------------------------
//--------------增加或删除奖品信息-------------
//-------------------------------------------------------
/**
* 生成table
*/
function addAwardList() {
	idSum = parseInt(idSum) + parseInt(1);
	var awardDiv = document.getElementById("awardList");
	var table = ""
	table += "\<table id=\"award" + idSum
			+ "\" class=\"award\"\>\<tr\>\<td\>\<label\>奖品" + idSum
			+ "\</label\>\</td\>\<td\>\<input type=\"hidden\" id=\"id" + idSum
			+ "\" value=\"\"\>\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>奖品类别：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"awardType"
			+ idSum + "\" value=\"\" /\>\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>奖品名称：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"awardName"
			+ idSum
			+ "\" value=\"\" /\>\</td\>\</tr\>\<tr\>\<td\>\<label\>活动奖品图片：\</label\>\</td\>"
			+ "\<td\>\<img src=\"\" id=\"awardPicUrl"
			+ idSum
			+ "\" style=\"height: 30px;width: 60px;\"\>\<input type=\"button\" value=\"选择图片\" id=\"uploadAwardPic"
			+ idSum
			+ "\"\>"
			+ "\<label\>（尺寸：700 X 700  ） 小于4M\</label\>\</td\>\</tr\>\<tr\>\<td\>\<label\>奖品icon：\</label\>\</td\>\<td\>\<img src=\"\" id=\"awardIconUrl"
			+ idSum
			+ "\" style=\"height: 30px;width: 60px;\"\>"
			+ "\<input type=\"button\" value=\"选择图片\" id=\"uploadAwardIcon"
			+ idSum
			+ "\"\>\<label\>（尺寸：134 x 134  ） 小于4M\</label\>\</td\>\</tr\>"
			+ "\<tr\>\<td\>\<label\>奖品说明：\</label\>\</td\>\<td\>\<textarea id=\"awardDesc"
			+ idSum + "\"\>\</textarea\>\</td\>\</tr\>";
	/*hll:注释源码
	table += "\<td\>\<label\>奖品有效时间：\</label\>\</td\>\<td\>\<input class=\"laydate-icon\" " +
			"id=\"availableBeginTime"+ idSum + "\" value=\"\" style=\"width: 150px\" onclick=\"laydate()\"\>" +
			"至\<input class=\"laydate-icon\" id=\"availableEndTime"+ idSum + "\"  value=\"\" style=\"width: 150px\" " +
			"onclick=\"laydate()\"\>\</td\>";
	*/
	/*
	 * hll:增加源码
	 */
	table += "\<td\>\<label\>奖品有效时间：\</label\>\</td\>\<td\>\<input class=\"easyui-datebox\" " +
	"id=\"availableBeginTime"+ idSum + "\" data-options=\"required:false\" value=\"\" style=\"width: 150px\" \>" +
	"至\<input class=\"easyui-datebox\" id=\"availableEndTime"+ idSum + "\" data-options=\"required:false\" value=\"\" style=\"width: 150px\" " +
	"\>\</td\>";
	
	table += "\<tr\>\<td\>\<label\>奖品数量：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"awardAmount"
			+ idSum + "\" value=\"\" /\>\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>每天中奖上线：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"winLimitDay"
			+ idSum + "\" value=\"\" /\>\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>奖品属性：\</label\>\</td\>\<td\>\<select id=\"awardProperty"
			+ idSum
			+ "\""
			+ " onchange=\"selectProperty("
			+ idSum
			+ ")\"\>\<option value=\"1\"\>实物\</option\>\<option value=\"2\"\>卡券"
			+ "\</option\>\<option value=\"3\"\>VIP时长\</option\>\<option value=\"4\"\>电影票\</option\>"
			+ "\<option value=\"5\"\>麦粒\</option\>\<option value=\"6\"\>折扣券\</option\>\<option value=\"7\""
			+ "\>代金券\</option\>\</select\>\</td\>\</tr\>"
			+ "\<tr\>\<td\>\<label\>\<span class=\"required\"\>*\</span\>中奖码生成：\</label\>\</td\>\<td\>\<select id=\"awardCodeType"
			+ idSum
			+ "\"\>"
			+ "\<option value=\"1\"\>自动生成\</option\>\<option value=\"2\"\>批量导入\</option\>\</select\>"
			+ "\</td\>\</tr\>";
	table += "\</table\>";
	table += "\<div id=\"realAward"
			+ idSum
			+ "\" style=\"display: none\"\>\<label\>兑奖形式：\</label\>\<span\>是否需要扫码填写信息"
			+ "\</span\>\<select id=\"awardQrcodeFlag"
			+ idSum
			+ "\" onchange=\"selectUserInfo("
			+ idSum
			+ ")\"\>\<option value=\"1\"\>是\</option\>\<option value=\"2\"\>"
			+ "否\</option\>\</select\>\<div id=\"userInfo"
			+ idSum
			+ "\" style=\"display: none\"\>\<label\>填写用户信息：\</label\>"
			+ "\<label\>\<input type=\"checkbox\" name=\"userInfoType"
			+ idSum
			+ "\" value=\"1\"/\>姓名\</label\>\<label\>"
			+ "\<input type=\"checkbox\" name=\"userInfoType"
			+ idSum
			+ "\" value=\"2\"/\>邮寄地址\</label\>\<label\>"
			+ "\<input type=\"checkbox\" name=\"userInfoType"
			+ idSum
			+ "\" value=\"3\"/\>手机号\</label\>\<label\>"
			+ "\<input type=\"checkbox\" name=\"userInfoType"
			+ idSum
			+ "\" value=\"4\"/\>邮编\</label\>\</div\>\</div\>"
			+ "\<div id=\"vipHours"
			+ idSum
			+ "\" style=\"display: none;\"\>\<label\>\<span class=\"required\"\>*\</span\>VIP时长:\</label\>"
			+ "\<input id=\"awardVipDuration"
			+ idSum
			+ "\" value=\"\" type=\"text\"/\>\</div\>"
			+ "\<div id=\"ml"
			+ idSum
			+ "\" style=\"display: none;\"\>\<label\>麦粒:\</label\>"
			+ "\<input id=\"awardMlAmount"
			+ idSum
			+ "\" value=\"\" type=\"text\"/\>\</div\>"
			+ "\<div id=\"discount"
			+ idSum
			+ "\" style=\"display: none;\"\>\<label\>选择内容：\</label\>"
			+ "\<select id=\"awardVideoType"
			+ idSum
			+ "\" onchange=\"setValue("
			+ idSum
			+ ")\"\>\<option value=\"0\"\>请选择\</option\>\<option value=\"2009\"\>"
			+ "VIP\</option\>\<option value=\"2006\"\>极清首映\</option\>\</select\>\<select id=\"vipProducts"+idSum+"\" "
			+"style=\"display:none\"\>\</select\>\<select id=\"blueProducts"+idSum+"\" style=\"display:none\"\>"
			+"\</select\>\<br\>\<label\>折扣\</label\>"
			+ "\<input id=\"awardDiscount" + idSum
			+ "\" value=\"\"/\>折\</div\>\<div id=\"ticket" + idSum + "\" "
			+ "style=\"disPlay:none\"\>\<select id=\"ticketProducts" + idSum
			+ "\"\>\</select\>\</div\>";
	/*hll:注释源码
	awardDiv.innerHTML += table;
	*/
	
	/*
	 * hll:增加源码
	 */
	$('#awardList').append(table);
	
	/*
	 * hll:增加源码
	 */
	$.parser.parse('#award'+idSum);
	
	uploadAwardIcon(idSum);
	uploadAwardPic(idSum);
	
	setTicket(idSum);
	setVipAndBlue(idSum);
	
	selectProperty(idSum);
	selectUserInfo(idSum);
}
function delLastAward() {
	if (idSum == 1) {
		alert("请保留一个奖品");
		return;
	} else {
		$('#award' + idSum).remove();
		$('#realAward' + idSum).remove();
		$('#vipHours' + idSum).remove();
		$('#ml' + idSum).remove();
		
		/*
		 * hll:增加源码
		 */
		$('#discount'+idSum).remove();
		$('#ticket'+idSum).remove();
		
		idSum = parseInt(idSum) - parseInt(1);
	}
}
//-------------------------------------------------------
//--------------修改活动信息-------------
//-------------------------------------------------------
function checkBoxOrRadio(boxs, value) {
	for (var i = 0; i < boxs.length; i++) {
		if (boxs[i].value == value) {
			boxs[i].checked = true;
		}
	}
}
//消费用户使用人群
function showPayInfo(type) {
	$(".showPlayerType").attr("style", "display:none");
	if (type == 3) {
		$(".showPlayerType").attr("style", "");
	}
}
//设置编辑信息
function setAward(rowInfo) {
	$('#id').val(rowInfo.id);
	$('#title').val(rowInfo.title);
	$('#type').val(rowInfo.type);// 单选按钮
	$('#logoUrl').attr("src", rowInfo.logoUrl);
	$('#bgUrl').attr("src", rowInfo.bgUrl);
	$('#infoBgUrl').attr("src", rowInfo.infoBgUrl);
	$('#endTitle').val(rowInfo.endTitle);
	$('#playingBgUrl').attr("src", rowInfo.playingBgUrl);
	$('#endText').val(rowInfo.endText);
	var beginTime = longToDate(rowInfo.beginTime,1);
	$('#beginTime').datebox('setValue', beginTime);
	var endTime = longToDate(rowInfo.endTime,1);
	$('#endTime').datebox('setValue', endTime);
	$('#activityDesc').val(rowInfo.activityDesc);
	$('#awardWinRatio').val(rowInfo.awardWinRatio);
	var awardWinAgain = document.getElementsByName("awardWinAgain");
	checkBoxOrRadio(awardWinAgain, rowInfo.awardWinAgain);
	var awardPlayerType = document.getElementsByName("awardPlayerType");
	checkBoxOrRadio(awardPlayerType, rowInfo.awardPlayerType);
	showPayInfo(rowInfo.awardPlayerType);
	var payEndTime = longToDate(rowInfo.payEndTime,1);
	$('#payEndTime').datebox('setValue', payEndTime);
	var payBeginTime = longToDate(rowInfo.payBeginTime,1);
	$('#payBeginTime').datebox('setValue', payBeginTime);
	$('#paySum').val(rowInfo.paySum/100);
	$('#awardRemark').val(rowInfo.awardRemark);
	
	var phoneBindType = document.getElementsByName("phoneBindType");
	checkBoxOrRadio(phoneBindType, rowInfo.phoneBindType);
	$('#playLimitDay').val(rowInfo.playLimitDay);
	$('#playPromptDay').val(rowInfo.playPromptDay);
	$('#playLimitTotal').val(rowInfo.playLimitTotal);
	$('#playPromptTotal').val(rowInfo.playPromptTotal);
	$('#playPromptWin').val(rowInfo.playPromptWin);
	$('#playPromptLost').val(rowInfo.playPromptLost);
	
}
function setAwardList(count, award) {
	$("#id" + count).val(award.id);
	$("#awardType" + count).val(award.awardType);
	$("#awardName" + count).val(award.awardName);
	$("#awardPicUrl" + count).attr("src", award.awardPicUrl);
	$("#awardIconUrl" + count).attr("src", award.awardIconUrl);
	$("#awardDesc" + count).val(award.awardDesc);
	/*hll:注释源码
	var availableBeginTime = longToDate(award.availableBeginTime,2);
	$('#availableBeginTime'+count).val(availableBeginTime);
	var availableEndTime = longToDate(award.availableEndTime,2);
	$('#availableEndTime'+count).val(availableEndTime);
	*/
	
	/*
	 * hll:增加源码
	 */
	var availableBeginTime = longToDate(award.availableBeginTime,2);
	$('#availableBeginTime'+count).datebox('setValue',availableBeginTime);
	var availableEndTime = longToDate(award.availableEndTime,2);
	$('#availableEndTime'+count).datebox('setValue',availableEndTime);
	
	$("#awardAmount" + count).val(award.awardAmount);
	$("#winLimitDay" + count).val(award.winLimitDay);
	$("#awardCodeType" + count).val(award.awardCodeType);
	$("#awardProperty" + count).val(award.awardProperty);
	selectProperty(count);
	
	/*
	 * hll:增加源码
	 */
	var videoValue=0;
	if(award.awardVideoId){
		videoValue = award.awardVideoId+","+award.awardVideoPartnerId+","+award.awardPrice;
	}
	if(award.awardProperty==1){
		//实物
		$('awardQrcodeFlag'+count).val(award.awardQrcodeFlag);
		selectUserInfo(count);
		var strs = new Array();
		strs = award.userInfoType.split(",");
		var cb = document.getElementsByName("userInfoType" + count);
		for (var j = 0; j < strs.length; j++) {
			for (var k = 0; k < cb.length; k++) {
				if (cb[k].value == strs[j]) {
					cb[k].checked = true;
					break;
				}
			}
		}
	}
	else if(award.awardProperty==2){
		//电子兑换券
	}
	else if(award.awardProperty==3){
		//VIP时长
		$("#awardVipDuration" + count).val(award.awardVipDuration);
	}
	else if(award.awardProperty==4){
		//电影票
		setTicket(count);
		$("#ticketProducts"+count).val(videoValue);
	}
	else if(award.awardProperty==5){
		//麦粒
		$("#awardMlAmount" + count).val(award.awardMlAmount);
	}
	else if(award.awardProperty==6){
		//折扣券
		$('#awardVideoType'+count).val(award.awardVideoType);
		setValue(count);
		if(award.awardVideoType==2006){
			$("#blueProducts"+count).val(videoValue);
		}
		else if(award.awardVideoType==2009){
			$("#vipProducts"+count).val(videoValue);
		}
		$("#awardDiscount" + count).val(award.awardDiscount);
	}
	else if(award.awardProperty==7){
		//代金券
	}
	
	
	
	/*hll:注释源码
	$("#awardVipDuration" + count).val(award.awardVipDuration);
	$("#awardMlAmount" + count).val(award.awardMlAmount);
	*/
	
	/*hll:注释源码
	$("#getVIPOrBlue" + count).val(award.awardVipBlue);
	*/
	
	/*hll:注释源码
	$('#awardVideoType'+count).val(award.awardVideoType);
	*/
	
	/*hll:注释源码
	if(award.awardVideoId==0||award.awardVideoId==null||award.awardVideoId==""){
		videoValue = 0;
	}else{
		videoValue = award.awardVideoId+","+award.awardVideoPartnerId+","+award.awardPrice;
	}
	*/
	
	/*hll:注释
	if(award.awardProperty == 6){
		setValue(count);// 设置极清或是VIP产品下拉数据TODO
	}
	if (award.awardProperty == 4) {
		setTicket(count);
	}
	$("#vipProducts"+count).val(videoValue);
	$("#blueProducts"+count).val(videoValue);
	$("#awardDiscount" + count).val(award.awardDiscount);
	$("#ticketProducts"+count).val(videoValue);
	*/
	
	/*hll:注释
	$("#awardCodeType" + count).val(award.awardCodeType);
	*/
	
	
	/*hll:注释
	var strs = new Array();
	strs = award.userInfoType.split(",");
	var cb = document.getElementsByName("userInfoType" + count);
	for (var j = 0; j < strs.length; j++) {
		for (var k = 0; k < cb.length; k++) {
			if (cb[k].value == strs[j]) {
				cb[k].checked = true;
				break;
			}
		}
	}
	*/
}
//编辑
function editAwardActivity(id) {
	/*
	 * hll:增加源码
	 */
	reset();
	firstTab();
	
	var rowInfo = $('#award_activity_table').datagrid('getSelected');
	if (rowInfo) {
		// 设置弹出框信息
		setAward(rowInfo);
		var awards = eval(rowInfo.awards);
		for (var i = 0; i < awards.length; i++) {
			var count = parseInt(i) + parseInt(1);
			if(count!=1){
				addAwardList();
			}
		}
		for (var i = 0; i < awards.length; i++) {
			var count = parseInt(i) + parseInt(1);
			setAwardList(count, awards[i]);
		}
		$('#award_activity_dialog').dialog('open');
	}
}
$(function() {
	queryActivity();
	
	uploadLogo();
	uploadBg();
	uploadBeginProfile();
	uploadEndProfile();
	uploadAwardIcon(1);
	uploadAwardPic(1);
	/*hll:注释原有代码
	// 选择奖品属性
	selectProperty(1);
	// 选择是否填写详细信息
	selectUserInfo(1);
	*/
	// 加载极清首映table
	setTicket(1);
	// 设置VIP、极清首映单片
	setVipAndBlue(1);
	/*hll:注释原有代码
	firstTab();
	*/
	addAwardActivity();
	// queryActivity();
	// 初始化编辑弹窗
	$('#award_activity_dialog').dialog({
		buttons : [ {
			text : '取消',
			handler : function() {
				reset();
				$('#award_activity_dialog').dialog('close');
			}
		} ]
	});
	
	initAwardInfo();
});
//-------------------------------------------------------
//--------------查询活动信息-------------
//-------------------------------------------------------
//查询活动信息
function initAwardInfo() {
	parameter = {};
	var title = $('#searchTitle').val();
	if (title != '') {
		parameter.title = title;
	}
	$('#award_activity_table')
			.datagrid(
					{
						iconCls : 'icon-save',
						nowrap : true,
						autoRowHeight : false,
						striped : true,
						toolbar : '#award_toolbar',
						fit : true,
						fitColumns : true,
						collapsible : true,
						url : 'awardActivity/getList.json',
						queryParams : parameter,
						remoteSort : false,
						singleSelect : true,
						idField : 'id',
						columns : [ [
								{
									field : 'title',
									title : '活动标题',
									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'type',
									title : '活动类型',
									width : 40,
									formatter : function(value, row, index) {
										var result;
										if(value==1){
											result='砸金蛋';
										}
										else if(value==2){
											result='老虎机';
										}
										else if(value==3){
											result='大转盘';
										}
										return result;
									}
								},
								{
									field : 'sequence',
									title : '序号',
									width : 40,
									hidden : false,
									formatter : function(value, row, index) {
										return index+1;
									}
								},
								{
									field : 'beginTime',
									title : '活动开始时间',
									width : 40,
									formatter : function(value, row, index) {
										return longToDate(value,1);
									}
								},
								{
									field : 'endTime',
									title : '活动结束时间',
									width : 40,
									formatter : function(value, row, index) {
										return longToDate(value,1);
									}
								},
								{
									field : 'logoUrl',
									title : '活动logo',
									width : 40,
									hidden : true
								},
								{
									field : 'bgUrl',
									title : '背景图',
									width : 40,
									hidden : true
								},
								{
									field : 'infoBgUrl',
									title : '开始封面',
									width : 40,
									hidden : true
								},
								{
									field : 'playingBgUrl',
									title : '结束封面',
									width : 40,
									hidden : true
								},
								{
									field : 'endTitle',
									title : '结束标题',
									width : 40,
									hidden : true
								},
								{
									field : 'endText',
									title : '活动结束说明',
									width : 40,
									hidden : true
								},
								{
									field : 'playLimitDay',
									title : '一天抽奖次数限制',
									width : 40,
									hidden : true
								},
								{
									field : 'playLimitTotal',
									title : '总抽奖次数限制',
									width : 40,
									hidden : true
								},
								{
									field : 'playPromptDay',
									title : '每日抽奖提示',
									width : 40,
									hidden : true
								},
								{
									field : 'playPromptTotal',
									title : '抽奖总次数提示',
									width : 40,
									hidden : true
								},
								{
									field : 'playPromptWin',
									title : '中奖提示',
									width : 40,
									hidden : true
								},
								{
									field : 'playPromtLost',
									title : '未中奖提示',
									width : 40,
									hidden : true
								},
								{
									field : 'phoneBindType',
									title : '电话绑定方式',
									width : 40,
									hidden : true
								},
								{
									field : 'awardCodeType',
									title : '中奖码生成方式',
									width : 40,
									hidden : true
								},
								{
									field : 'awardWinRatio',
									title : '出奖概率',
									width : 40,
									hidden : true
								},
								{
									field : 'awardWinLimit',
									title : '每日中奖上限',
									width : 40,
									hidden : true
								},
								{
									field : 'awardWinAgain',
									title : '是否可以重复中奖',
									width : 40,
									hidden : true
								},
								{
									field : 'awardPlayerType',
									title : '参与用户类型',
									width : 40,
									hidden : true
								},
								{
									field : 'payBeginTime',
									title : '出奖开始时间',
									width : 40,
									hidden : true
								},
								{
									field : 'payEndTime',
									title : '出奖结束时间',
									width : 40,
									hidden : true
								},
								{
									field : 'sequenceMove',
									title : '排序',
									width : 20,
									hidden : false,
									formatter : function(value, row, index) {
										var seq = row.sequence;
										return '<a href="javascript:move('
												+ seq + ',' + index
												+ ',1)">上移</a>|'
												+ '<a href="javascript:move('
												+ seq + ',' + index
												+ ',2)">下移</a>';
									}
								},
								{
									field : 'showFlag',
									title : '是否显示',
									width : 40,
									formatter : function(value, row, index) {
										if (1 == value) {
											return '<div class="abtn"><a href="javascript:isOff('+row.id+')" class="a_off">不显示</a><a href="javascript:isOn('+ row.id+ ')" class="a_on a_on2">显示</a></div>';
										} else {
											return '<div class="abtn"><a href="javascript:isOff('+row.id+')" class="a_on a_on2">不显示</a><a href="javascript:isOn('+ row.id+')" class="a_off">显示</a></div>';
										}
									}
								},
								{
									field : 'id',
									title : '操作',
									width : 40,
									formatter : function(value, row, index) {
										return '<a href="javascript:editAwardActivity('
												+ value
												+ ')">编辑</a>|<a href="javascript:delAwardActivity('
												+ value
												+ ')">删除</a>|<a href="awardCode/show.html?id='
												+ value
												+ '">中奖码</a>|<a href="awardPlay/show.html?id='
												+ value + '">参与用户</a>';
									}
								} ] ],
						pagination : true,
						rownumbers : true,
						onClickRow : function(rowIndex) {
						}
					});
}
/**
 * check是否为数字
 * 
 * @param playLimitDay
 * @param playLimitTotal
 * @param awardWinRatio
 * @returns {Boolean}
 */
function checkNumber(playLimitDay, playLimitTotal, awardWinRatio) {
	var reg = /^[0-9]*$/;
	var flag = true;
	if (!reg.test(playLimitDay)) {
		alert("每天抽奖次数请输入数字!");
		flag = false;
	} else if (!reg.test(playLimitTotal)) {
		alert("总抽奖次数请输入数字!");
		flag = false;
	} else if (!reg.test(awardWinRatio)) {
		alert("中奖概率请输入数字!");
		flag = false;
	}
	return flag;
}
/**
 * 判空
 * 
 * @param title
 * @param type
 * @param beginTime
 * @param endTime
 * @param phoneBindType
 * @param awardCodeType
 * @param awardWinRatio
 * @returns {Boolean}
 */
function regNull(title, type, beginTime, endTime, phoneBindType, awardWinRatio,
		awardPlayerType, awardRemark) {
	var flag = true;
	if (title == null || title == "") {
		alert("标题不能为空");
		flag = false;
	} else if (type == 0 || type == null || type == "") {
		alert("类型不能为空");
		flag = false;
	} else if (beginTime == null || beginTime == "") {
		alert("活动开始时间不能为空");
		flag = false;
	} else if (endTime == null || endTime == "") {
		alert("活动结束时间不能为空");
		flag = false;
	} else if (phoneBindType == null || phoneBindType == ""
			|| phoneBindType == 0) {
		alert("请选择绑定手机号方式");
		flag = false;
	} else if (awardWinRatio == null || awardWinRatio == ""
			|| awardWinRatio == 0) {
		alert("中奖概率不能为空");
		flag = false;
	} else if (awardPlayerType == null || awardPlayerType == ""
			|| awardPlayerType == 0) {
		alert("适用人群不能为空");
		flag = false;
	} else if (awardRemark == null || awardRemark == "") {
		alert("活动备注不能为空");
		flag = false;
	}
	return flag;
}
/**
 * 获取奖品List
 * 
 * @returns {String}
 */
function getAwardList() {
	var awardList = "[";
	for (var i = 1; i <= idSum; i++) {
		var id = $("#id" + i).val();
		if (id == null || id == "") {
			id = 0;
		}
		var awardType = $("#awardType" + i).val();
		var awardName = $("#awardName" + i).val();
		var awardPicUrl = document.getElementById('awardPicUrl' + i).src;
		var awardIconUrl = document.getElementById('awardIconUrl' + i).src;
		var awardDesc = $("#awardDesc" + i).val();
		/*hll:注释源码
		var availableBeginTime =$('#availableBeginTime' + i).val();
		var availableEndTime = $('#availableEndTime' + i).val();
		*/
		/*
		 * hll:增加源码
		 */
		var availableBeginTime =$('#availableBeginTime' + i).datebox('getValue');
		var availableEndTime = $('#availableEndTime' + i).datebox('getValue');
		
		var awardAmount = $("#awardAmount" + i).val();
		var winLimitDay = $("#winLimitDay" + i).val();
		var awardCodeType = $('#awardCodeType' + i).val();
		var awardProperty = $('#awardProperty' + i).val();
		
		
		var awardQrcodeFlag = 0;
		var userInfoType = "";
		
		var awardVipDuration = 0;
		
		var awardMlAmount = 0;
		
		var awardVideoType = $("#awardVideoType" + i).val();
		var awardVideoId = 0;
		var awardVideoName = "";
		var awardDiscount = 0;
		
		var reg = /^[0-9]*$/;
		if (awardProperty == 1) {// awardProperty 为1的时候代表实物
			awardQrcodeFlag = $("#awardQrcodeFlag" + i).val();
			var obj = document.getElementsByName('userInfoType' + i); // 选择所有name="'test'"的对象，返回数组
			// 取到对象数组后，我们来循环检测它是不是被选中
			for (var j = 0; j < obj.length; j++) {
				if (obj[j].checked)
					userInfoType += obj[j].value + ','; // 如果选中，将value添加到变量s中
			}
		} else if (awardProperty == 3) {// awardProperty 为3的时候代表VIP时长
			awardVipDuration = $("#awardVipDuration" + i).val();
			if (!reg.test(awardVipDuration)) {
				alert("VIP时长请填写数字");
				return "";
			}
		} else if (awardProperty == 6) {
			awardDiscount = $("#awardDiscount" + i).val();
			if (!reg.test(awardDiscount)) {
				alert("折扣请填写数字");
				return "";
			}
			if(awardDiscount<=0){
				alert("折扣不能为0，必须大于0");
				return "";
			}
			var awardVideo ;
			if(awardVideoType==2009){
				awardVideoId = $("#vipProducts"+i).val();
				awardVideo = document.getElementById("vipProducts"+i);
			}else{
				awardVideoId = $("#blueProducts"+i).val();
				awardVideo = document.getElementById("blueProducts"+i);
			}
			/*hll:注释源码
			awardVideoName = awardVideo.options[awardVideo.selectedIndex].innerText;
			*/
			awardVideoName = awardVideo.options[awardVideo.selectedIndex].text;
			awardDiscount = $("#awardDiscount" + i).val();
		} else if (awardProperty == 4) {
			awardVideoId = $("#ticketProducts" + i).val();
			var awardVideo = document.getElementById("ticketProducts"+i);
			/*hll:注释源码
			awardVideoName = awardVideo.options[awardVideo.selectedIndex].innerText;
			*/
			awardVideoName = awardVideo.options[awardVideo.selectedIndex].text;
		} else if (awardProperty == 5) {// awardProperty 为5的时候代表麦粒
			awardMlAmount = $("#awardMlAmount" + i).val();
		}
		awardList += "{\"id\":" + id + ",\"awardType\":\"" + awardType
				+ "\",\"awardName\":\"" + awardName + "\",\"awardAmount\":\""
				+ awardAmount +"\",\"winLimitDay\":\""+winLimitDay+ "\",\"awardProperty\":\"" + awardProperty
				+ "\",\"userInfoType\":\"" + userInfoType
				+ "\",\"awardVipDuration\":\"" + awardVipDuration + "\","
				+ "\"awardMlAmount\":\"" + awardMlAmount
				+ "\",\"awardQrcodeFlag\":\"" + awardQrcodeFlag
				+ "\",\"awardCodeType\":\"" + awardCodeType + "\""
				+ ",\"awardVideoName\":\"" + awardVideoName + "\""
				+ ",\"awardVideoId\":\"" + awardVideoId + "\""
				+ ",\"awardDiscount\":\"" + awardDiscount + "\""
				+ ",\"awardVideoType\":\"" + awardVideoType + "\""
				+ ",\"awardPicUrl\":\"" + awardPicUrl + "\""
				+ ",\"awardIconUrl\":\"" + awardIconUrl + "\""
				+ ",\"awardDesc\":\"" + awardDesc + "\""
				+ ",\"availableBeginTime\":\"" + availableBeginTime + "\""
				+ ",\"availableEndTime\":\"" + availableEndTime + "\"}";
	}
	awardList += "]";
	return awardList;
}
//提交增加或修改活动信息
function submitAward() {
	var id = $('#id').val();
	var title = $('#title').val();// 不能为空
	var type = $('#type').val();// 不能为空
	var logoUrl = document.getElementById('logoUrl').src;
	var bgUrl = document.getElementById('bgUrl').src;
	var infoBgUrl = document.getElementById('infoBgUrl').src;
	var endTitle = $('#endTitle').val();
	var playingBgUrl = document.getElementById('playingBgUrl').src;
	var endText = $('#endText').val();
	var beginTime = $('#beginTime').datetimebox('getValue');// 不能为空
	var endTime = $('#endTime').datetimebox('getValue');// 不能为空
	var activityDesc = $('#activityDesc').val();
	var awardWinRatio = $('#awardWinRatio').val();
	var awardWinAgain = $("input[name='awardWinAgain']:checked").val();
	var awardPlayerType = $("input[name='awardPlayerType']:checked").val();
	var payBeginTime = $('#payBeginTime').datetimebox('getValue');
	var payEndTime = $('#payEndTime').datetimebox('getValue');
	var paySum = $("#paySum").val();
	if(!isNaN(paySum)){
		paySum=paySum*100;//元转分
	}
	var awardRemark = $("#awardRemark").val();
	
	var phoneBindType = $("input[name='phoneBindType']:checked").val();// 不能为空
	var playLimitDay = $('#playLimitDay').val();
	var playPromptDay = $('#playPromptDay').val();
	var playLimitTotal = $('#playLimitTotal').val();
	var playPromptTotal = $('#playPromptTotal').val();
	var playPromptWin = $('#playPromptWin').val();
	var playPromptLost = $('#playPromptLost').val();
	
	if (awardPlayerType == 3) {// 这里的3是指用户类型为消费用户，这时
		if (payBeginTime == null || payBeginTime == "" || payEndTime == null
				|| payEndTime == "") {
			alert("消费时间段不能为空");
			return;
		} else if (paySum == null || paySum == "") {
			alert("消费金额不能为空");
			return;
		}
	} else {
		payBeginTime = '2000-01-01 00:00:00';
		payEndTime = '2000-01-01 00:00:00';
	}
	var awardList = getAwardList();
	if (awardList == "") {
		return;
	}
	var flagNumber = checkNumber(playLimitDay, playLimitTotal, awardWinRatio);
	var flag = regNull(title, type, beginTime, endTime, phoneBindType,
			awardWinRatio, awardPlayerType, awardRemark);
	/*
	 * hll:判断活动时间区间是否正确
	 */
	if(beginTime>endTime){
		alert('活动结束时间不能小于活动开始时间');
		return;
	}
	
	if (flag == false || flagNumber == false) {
		return;
	}
	var awardActivity = {
		id : id,
		title : title,
		type : type,
		logoUrl : logoUrl,
		bgUrl : bgUrl,
		infoBgUrl : infoBgUrl,
		endTitle : endTitle,
		playingBgUrl : playingBgUrl,
		endText : endText,
		strBeginTime : $('#beginTime').datetimebox('getValue'),
		strEndTime : $('#endTime').datetimebox('getValue'),
		activityDesc : activityDesc,
		awardWinRatio : awardWinRatio,
		awardWinAgain : awardWinAgain,
		awardPlayerType : awardPlayerType,
		strPayBeginTime : payBeginTime,
		strPayEndTime : payEndTime,
		paySum : paySum,
		awardRemark : awardRemark,
		phoneBindType : phoneBindType,
		playLimitDay : playLimitDay,
		playPromptDay : playPromptDay,
		playLimitTotal : playLimitTotal,
		playPromptTotal : playPromptTotal,
		playPromptWin : playPromptWin,
		playPromptLost : playPromptLost,
		awardList : awardList
	};

	if (id != null && id != "" && id != 0) {
		$.post('awardActivity/update.json', awardActivity, function(data) {
			if (data.code == 1) {
				$('#award_activity_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '修改成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				reset();// 清空页面残留信息
				$('#award_activity_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		}, "json");
	} else {
		$.post("awardActivity/add.json", awardActivity, function(data) {
			if (data.code == 1) {
				$('#award_activity_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				reset();// 清空页面残留信息
				$('#award_activity_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		}, "json");
	}
}
//-------------------------------------------------------
//--------------删除活动信息-------------
//-------------------------------------------------------
//删除活动信息
function delAwardActivity(id) {
	$.messager.confirm('Warning', 'Warning：该活动有可能正在进行中，删除可能引发一些问题！确定要删除?',
			function(r) {
				if (r) {
					var activity = {
						'id' : id
					};
					$.post('awardActivity/del.json', activity, function(data) {
						if (data.code == 1) {
							$.messager.show({
								title : titleInfo,
								msg : '已删除！',
								timeout : timeoutValue,
								showType : 'slide'
							});
							$('#award_activity_table').datagrid('load',
									parameter);
						} else {
							$.messager.alert(titleInfo, '删除失败！');
						}
					}, 'json');
				}
			});
}
//-------------------------------------------------------
//--------------上下移-------------
//-------------------------------------------------------
//上下移
function move(value, index, type) {
	var rows = $("#award_activity_table").datagrid('getRows').length;
	rows = parseInt(rows) - parseInt(1);
	if (type == 1 && index == 0) {
		alert("已经是第一个了！");
	} else if (type == 2 && index == rows) {
		alert("已经是最后一个了");
	} else {
		$.post('awardActivity/move.json', {
			'sequence' : value,
			"type" : type
		}, function(data) {
			if (data.code == 1) {
				$.messager.show({
					title : titleInfo,
					msg : '移动成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#award_activity_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '移动失败！');
			}
		}, "json");
	}
}
//-------------------------------------------------------
//--------------是否上线-------------
//-------------------------------------------------------
function isOn(id){
	$.messager.confirm('Warning', '确定要上线?', function(r) {
		if (r) {
			$.post('awardActivity/update.json', {id:id,showFlag:1}, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已上线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#award_activity_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '上线失败！');
				}
			}, 'json');
		}
	});
}
function isOff(id){
	$.messager.confirm('Warning', '确定要下线?', function(r) {
		if (r) {
			$.post('awardActivity/update.json', {id:id,showFlag:2}, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已下线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#award_activity_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '下线失败！');
				}
			}, 'json');
		}
	});
}



































//-------------------------------------------------------
//--------------无用-------------
//-------------------------------------------------------
function secondTab() {
	$("#tabTwo").attr("style", "");
	$("#tabOne").attr("style", "display:none");
	$("#tabThree").attr("style", "display:none");
}
function thirdTab() {
	$("#tabThree").attr("style", "");
	$("#tabOne").attr("style", "display:none");
	$("#tabTwo").attr("style", "display:none");
}