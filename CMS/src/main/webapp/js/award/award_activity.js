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
		return y + '-' + m + '-' + d + " " + dataObj.getHours() + ":"+ dataObj.getMinutes() + ":" + dataObj.getSeconds() + "";
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
var idSum = 0;
//奖品控件id
var customId=0;
//活动类型
var typeMap={};
//将button按钮赋予图片上传功能
function buttonUpload(button, img,imgWidth,imgHeight,imgSize) {
	new AjaxUpload(button, {
		action : 'util/uploadAsRequired.json',
		responseType : 'json',
		data:{
			width:imgWidth,
			height:imgHeight,
			size:imgSize
		},
		autoSubmit : true,
		name : 'file',// 文件对象名称（不是文件名）
		onSubmit : function(file, ext) {
			if (!(ext && /^(png|PNG|JPEG|jpeg|JPG|jpg)$/.test(ext))) {
				alert("请上传正确格式的图片");
				return false;
			}
		},
		onComplete : function(file, data) {
			if(data.code==0){
				alert(data.msg);
				return;
			}
			if(data.code==1){
				img.attr("src", data.msg);
			}
		}
	});
}
//活动logo
function uploadLogo() {
	var button = $("#uploadLogoUrl");
	var img = $("#logoUrl");
	buttonUpload(button, img,280,160,4*1024*1024);
}
//活动大背景图
function uploadBg() {
	var button = $("#uploadBgUrl");
	var img = $("#bgUrl");
	buttonUpload(button, img,1920,1080,4*1024*1024);
}
//活动进行时封面
function uploadEndProfile() {
	var button = $("#uploadPlayingBgUrl");
	var img = $("#playingBgUrl");
	buttonUpload(button, img,1920,1080,4*1024*1024);
}
//活动详情页封面
function uploadBeginProfile() {
	var button = $("#uploadInfoBgUrl");
	var img = $("#infoBgUrl");
	buttonUpload(button, img,1920,1080,4*1024*1024);
}
//活动奖品图片
function uploadAwardPic(k) {
	var button = $("#uploadAwardPic" + k);
	var img = $("#awardPicUrl" + k);
	buttonUpload(button, img,700,700,4*1024*1024);
}
//奖品icon
function uploadAwardIcon(k) {
	var button = $("#uploadAwardIcon" + k);
	var img = $("#awardIconUrl" + k);
	buttonUpload(button, img,134,134,4*1024*1024);
}
/*//vip时长支付记录图片
function uploadVipAwardPayUrl(k) {
	var button = $("#uploadVipAwardPayUrl" + k);
	var img = $("#vipAwardPayUrl" + k);
	buttonUpload(button, img,134,134,4*1024*1024);
}*/
//折扣券支付记录图片
function uploadDiscountAwardPayUrl(k) {
	var button = $("#uploadDiscountAwardPayUrl" + k);
	var img = $("#discountAwardPayUrl" + k);
	buttonUpload(button, img,134,134,4*1024*1024);
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
//消费用户使用人群
function showPayInfo(type) {
	$(".showPlayerType").attr("style", "display:none");
	if (type == 3) {
		$(".showPlayerType").attr("style", "");
	}
}
//奖品属性联动模块显示功能
function selectProperty(id,isDisabled) {
//	$("#realAward" + id).attr("style", "display:none;");
//	if(isDisabled){
//		$('#awardCodeType'+id).attr('disabled',false);
//	}
	$("#vipHours" + id).attr("style", "display:none;");
	$("#ticket" + id).attr("style", "display:none;");
	$("#ml" + id).attr("style", "display:none;");
	$("#discount" + id).attr("style", "display:none;");
	var value = $("#awardProperty" + id).val();
//	if (value == 1) {
//		$("#realAward" + id).attr("style", "");
//	}
	if(value==2){
//		$('#awardCodeType'+id).val(1);
//		if(isDisabled){
//			$('#awardCodeType'+id).attr('disabled',true);
//		}
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
/*//填写用户信息显示隐藏
function selectUserInfo(id) {
	var value = $("#awardQrcodeFlag" + id).val();
	if (value == 1) {
		$("#userInfo" + id).attr("style", "");
	} else {
		$("#userInfo" + id).attr("style", "display:none;");
	}
}*/
//折扣券选择内容
function setValue(id){
	$("#vipProducts"+id).attr("style","display:none");
	$("#blueProducts"+id).attr("style","display:none");
	var awardVideoType = $('#awardVideoType'+id).val();
	if(awardVideoType==2009){
		$("#vipProducts"+id).attr("style"," ");
	}
	if(awardVideoType==2006){
		$("#blueProducts"+id).attr("style"," ");
	}
}
//显示基础设置模块
function firstTab() {
	$("#tabOne").attr("style", "");
	$("#tabTwo").attr("style", "display:none");
	$("#tabThree").attr("style", "display:none");
}
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
//添加更多奖品
function addAwardList() {
	customId = parseInt(customId) + parseInt(1);
	
	var table = "";
	table += "\<table id=\"award" + customId
			+ "\" class=\"award\"\>\<tr\>\<td\>\<label\>奖品" + customId
			+ "\</label\>\</td\>\<td\>\<input type=\"hidden\" id=\"id" + customId
			+ "\" value=\"\"\>\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>奖品类别：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"awardType"
			+ customId + "\" value=\"\" /\>\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>奖品名称：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"awardName"
			+ customId
			+ "\" value=\"\" /\>\</td\>\</tr\>\<tr\>\<td\>\<label\>\<span class=\"required\"\>*\</span\>活动奖品图片：\</label\>\</td\>"
			+ "\<td\>\<img src=\"\" id=\"awardPicUrl"
			+ customId
			+ "\" style=\"height: 30px;width: 60px;\"\>\<input type=\"button\" value=\"选择图片\" id=\"uploadAwardPic"
			+ customId
			+ "\"\>"
			+ "\<label\>（尺寸：700 X 700  ） 小于4M\</label\>\</td\>\</tr\>\<tr\>\<td\>\<label\>\<span class=\"required\"\>*\</span\>奖品icon：\</label\>\</td\>\<td\>\<img src=\"\" id=\"awardIconUrl"
			+ customId
			+ "\" style=\"height: 30px;width: 60px;\"\>"
			+ "\<input type=\"button\" value=\"选择图片\" id=\"uploadAwardIcon"
			+ customId
			+ "\"\>\<label\>（尺寸：134 x 134  ） 小于4M\</label\>\</td\>\</tr\>"
			+ "\<tr\>\<td\>\<label\>奖品说明：\</label\>\</td\>\<td\>\<textarea id=\"awardDesc"
			+ customId + "\"\>\</textarea\>\</td\>\</tr\>";
	/*hll:注释源码
	table += "\<td\>\<label\>奖品有效时间：\</label\>\</td\>\<td\>\<input class=\"laydate-icon\" " +
			"id=\"availableBeginTime"+ customId + "\" value=\"\" style=\"width: 150px\" onclick=\"laydate()\"\>" +
			"至\<input class=\"laydate-icon\" id=\"availableEndTime"+ customId + "\"  value=\"\" style=\"width: 150px\" " +
			"onclick=\"laydate()\"\>\</td\>";
	*/
	/*
	 * hll:增加源码
	 */
	table += "\<td\>\<label\>\<span class=\"required\"\>*\</span\>奖品有效时间：\</label\>\</td\>\<td\>\<input class=\"easyui-datebox\" " +
	"id=\"availableBeginTime"+ customId + "\" data-options=\"required:false\" value=\"\" style=\"width: 150px\" \>" +
	"至\<input class=\"easyui-datebox\" id=\"availableEndTime"+ customId + "\" data-options=\"required:false\" value=\"\" style=\"width: 150px\" " +
	"\>\</td\>";
	
	table += "\<tr\>\<td\>\<label\>\<span class=\"required\"\>*\</span\>奖品数量：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"awardAmount"
			+ customId + "\" value=\"\" /\>\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>\<span class=\"required\"\>*\</span\>每天中奖上限：\</label\>\</td\>\<td\>\<input type=\"text\" id=\"winLimitDay"
			+ customId + "\" value=\"\" /\>\</td\>\</tr\>";
	/*hll:注释源码
	 * table += "\<tr\>\<td\>\<label\>奖品属性：\</label\>\</td\>\<td\>\<select id=\"awardProperty"
			+ customId
			+ "\""
			+ " onchange=\"selectProperty("
			+ customId
			+ ")\"\>\<option value=\"1\"\>实物\</option\>\<option value=\"2\"\>卡券"
			+ "\</option\>\<option value=\"3\"\>VIP时长\</option\>\<option value=\"4\"\>电影票\</option\>"
			+ "\<option value=\"5\"\>麦币\</option\>\<option value=\"6\"\>折扣券\</option\>\<option value=\"7\""
			+ "\>代金券\</option\>\</select\>\</td\>\</tr\>"
			+ "\<tr\>\<td\>\<label\>\<span class=\"required\"\>*\</span\>中奖码生成：\</label\>\</td\>\<td\>\<select id=\"awardCodeType"
			+ customId
			+ "\"\>"
			+ "\<option value=\"1\"\>自动生成\</option\>\<option value=\"2\"\>批量导入\</option\>\</select\>"
			+ "\</td\>\</tr\>";*/
	table+= "\<tr\>\<td\>\<label\>\<span class=\"required\"\>*\</span\>中奖码生成：\</label\>\</td\>\<td\>\<select id=\"awardCodeType"
		+ customId
		+ "\"\>"
		+ "\<option value=\"1\"\>自动生成\</option\>\<option value=\"2\"\>批量导入\</option\>\</select\>"
		+ "\</td\>\</tr\>";
	table += "\<tr\>\<td\>\<label\>奖品属性：\</label\>\</td\>\<td\>\<select id=\"awardProperty"
		+ customId
		+ "\""
		+ " onchange=\"selectProperty("
		+ customId
		+ ",true)\"\>\<option value=\"1\"\>实物\</option\>\<option value=\"2\"\>电子兑换券"
		+ "\</option\>\<option value=\"3\"\>VIP时长\</option\>\<option value=\"4\"\>电影票\</option\>";
//	table+= "\<option value=\"5\"\>麦币\</option\>";
	table+="\<option value=\"6\"\>折扣券\</option\>"
		+ "\</select\>\</td\>\</tr\>";
	table += "\</table\>";
/*	table += "\<div id=\"realAward"
			+ customId
			+ "\" style=\"display: none\"\>\<label\>兑奖形式：\</label\>\<span\>是否需要扫码填写信息"
			+ "\</span\>\<select id=\"awardQrcodeFlag"
			+ customId
			+ "\" onchange=\"selectUserInfo("
			+ customId
			+ ")\"\>\<option value=\"1\"\>是\</option\>\<option value=\"2\"\>"
			+ "否\</option\>\</select\>\<div id=\"userInfo"
			+ customId
			+ "\" style=\"display: none\"\>\<label\>填写用户信息：\</label\>"
			+ "\<label\>\<input type=\"checkbox\" name=\"userInfoType"
			+ customId
			+ "\" value=\"1\"/\>姓名\</label\>\<label\>"
			+ "\<input type=\"checkbox\" name=\"userInfoType"
			+ customId
			+ "\" value=\"2\"/\>邮寄地址\</label\>\<label\>"
			+ "\<input type=\"checkbox\" name=\"userInfoType"
			+ customId
			+ "\" value=\"3\"/\>手机号\</label\>\<label\>"
			+ "\<input type=\"checkbox\" name=\"userInfoType"
			+ customId
			+ "\" value=\"4\"/\>邮编\</label\>\</div\>\</div\>";*/
	table+= "\<div id=\"vipHours"
			+ customId
			+ "\" style=\"display: none;\"\>\<label\>\<span class=\"required\"\>*\</span\>VIP时长:\</label\>"
			+ "\<input id=\"awardVipDuration"
			+ customId
			+ "\" value=\"\" type=\"text\"/\>\<span\>天\</span\>";
	
	/*table+="\</br\>";
	table+="\<label\>\<span class=\"required\"\>*\</span\>支付记录图片：\</label\>";
	table+="\<img id=\"vipAwardPayUrl"+customId+"\" src=\"\" style=\"height: 30px; width: 60px;\"\>";
	table+="\<input id=\"uploadVipAwardPayUrl"+customId+"\" type=\"button\" value=\"选择图片\" /\>";
	table+="\<label\>\</label\>";*/
	
	table+="\</div\>";
	table+= "\<div id=\"ml"
			+ customId
			+ "\" style=\"display: none;\"\>\<label\>\<span class=\"required\"\>*\</span\>麦币:\</label\>"
			+ "\<input id=\"awardMlAmount"
			+ customId
			+ "\" value=\"\" type=\"text\"/\>\<span\>个\</span\>\</div\>";
	table+= "\<div id=\"discount"
			+ customId
			+ "\" style=\"display: none;\"\>\<label\>\<span class=\"required\"\>*\</span\>选择内容：\</label\>"
			+ "\<select id=\"awardVideoType"
			+ customId
			+ "\" onchange=\"setValue("
			+ customId
			+ ")\"\>\<option value=\"0\"\>请选择\</option\>\<option value=\"2009\"\>"
			+ "VIP\</option\>\<option value=\"2006\"\>极清首映\</option\>\</select\>\<select id=\"vipProducts"+customId+"\" "
			+"style=\"display:none\"\>\</select\>\<select id=\"blueProducts"+customId+"\" style=\"display:none\"\>"
			+"\</select\>\<br\>\<label\>\<span class=\"required\"\>*\</span\>折扣：\</label\>"
			+ "\<input id=\"awardDiscount" + customId
			+ "\" value=\"\"/\>折";
	
	table+="\</br\>";
	table+="\<label\>\<span class=\"required\"\>*\</span\>支付记录图片：\</label\>";
	table+="\<img id=\"discountAwardPayUrl"+customId+"\" src=\"\" style=\"height: 30px; width: 60px;\"\>";
	table+="\<input id=\"uploadDiscountAwardPayUrl"+customId+"\" type=\"button\" value=\"选择图片\" /\>";
	table+="\<label\>小于4M\</label\>";
	
	table+="\</div\>";
	table+="\<div id=\"ticket" + customId + "\" "
			+ "style=\"disPlay:none\"\>\<span class=\"required\"\>*\</span\>\<select id=\"ticketProducts" + customId
			+ "\"\>\</select\>\</div\>";
	table+="\<input id=\"deleteAward"+customId+"\" type=\"button\" value=\"删除奖品\" onclick=\"delLastAward("+customId+")\" /\>";
	$('#awardList').append(table);
	$.parser.parse('#award'+customId);
	
	uploadAwardPic(customId);
	uploadAwardIcon(customId);
//	uploadVipAwardPayUrl(customId);
	uploadDiscountAwardPayUrl(customId);
	
	setTicket(customId);
	setVipAndBlue(customId);
	
	selectProperty(customId);
//	selectUserInfo(customId);
	
	idSum = parseInt(idSum) + parseInt(1);
}
//删除奖品
function delLastAward(id) {
	if (idSum == 1) {
		alert("请保留一个奖品");
		return;
	} else {
		$('#award' + id).remove();
		$('#realAward' + id).remove();
		$('#vipHours' + id).remove();
		$('#ticket'+id).remove();
		$('#ml' + id).remove();
		$('#discount'+id).remove();
		$('#deleteAward'+id).remove();
		idSum = parseInt(idSum) - parseInt(1);
	}
}



//清空窗口控件
function reset() {
	$('#id').val(0);
	$('#title').val('');
	$("#type").val(0);
	$('#logoUrl').attr("src", "");
	$('#bgUrl').attr("src", "");
	$('#playingBgUrl').attr("src", "");
	$('#endTitle').val('');
	$('#infoBgUrl').attr("src", "");
	$('#endText').val('');
	$('#beginTime').datebox('setValue', '');
	$('#endTime').datebox('setValue', '');
	$('#activityDesc').val('');
	$('#awardWinRatio').val('');
	$("input[name=awardWinAgain]").removeAttr("checked");// 清除是否可以重复抽奖
	$("input[name=awardWinAgain]")[0].checked=true;
	$("input[name=awardPlayerType]").removeAttr("checked");// 抽奖适用人群
	$("input[name=awardPlayerType]")[0].checked=true;
	$('#payBeginTime').datebox('setValue', '');
	$('#payEndTime').datebox('setValue', '');
	$("#paySum").val("");
	$(".showPlayerType").attr("style", "display:none");
	$('#awardRemark').val('');
	
	
	
	$("input[name=phoneBindType]").removeAttr("checked");
	$("input[name=phoneBindType]")[1].checked=true;
	$('#playLimitDay').val('');
	$('#playPromptDay').val('');
	$('#playLimitTotal').val('');
	$('#playPromptTotal').val('');
	$('#playPromptWin').val('');
	$('#playPromptLost').val('');
	
	
	
	/*$("#id1").val(0);
	$("#awardType1").val('');
	$("#awardName1").val('');
	$('#awardPicUrl1').attr("src", "");
	$('#awardIconUrl1').attr("src", "");
	$('#awardDesc1').val('');
	$('#availableBeginTime1').datebox('setValue','');
	$('#availableEndTime1').datebox('setValue','');
	$("#awardAmount1").val('');
	$("#winLimitDay1").val('');
	$("#awardCodeType1").val(1);
	$("#awardProperty1").val(1);
	selectProperty(1);
	$("#awardQrcodeFlag1").val(1);
	selectUserInfo(1);
	$("input[name=userInfoType1]").removeAttr("checked");
	$("#awardVipDuration1").val("");
//	$('#ticketProducts1').val(0);
	$("#awardMlAmount1").val("");
	$('#awardVideoType1').val(0);
	setValue(1);
	$("#awardDiscount1").val('');*/
	
	
	
	document.getElementById("awardList").innerHTML='';
	idSum=0;
	customId=0;
	addAwardList();
}
//增加活动信息
function addAwardActivity() {
	$('#add_award').click(function() {
		reset();
		firstTab();
		$("#submitDiv").attr("style", "");
		$("#award_activity_dialog").dialog('open');
	});
}
//查询活动信息
function queryActivity() {
	$('#query_activity').click(function() {
		initAwardInfo();
	});
}
$(function() {
	uploadLogo();
	uploadBg();
	uploadEndProfile();
	uploadBeginProfile();
/*	uploadAwardPic(1);
	uploadAwardIcon(1);*/
	setTicket(1);
	setVipAndBlue(1);
	
	//添加活动
	addAwardActivity();
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
	
	//查询
	queryActivity();
	
	initAwardInfo();
	
	$.ajax({
		async:false,
		type:'post',
		url:'awardActivity/selectAwardActivityType.json',
		traditional:true,
		data:{
		},
		success:function(data){
			if(data){
				for(var i=0;i<data.length;i++){
					typeMap[data[i].typeCode]=data[i].typeName;
					$('#type').append("<option value='"+data[i].typeCode+"'>"+data[i].typeName+"</option>");
				}
			}
		}
	});
});



/*
 * 查询活动信息
 */
function initAwardInfo() {
	parameter = {};
	var title = $('#searchTitle').val();
	if (title) {
		parameter.title = title;
	}
	$('#award_activity_table').datagrid({
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
										return typeMap[value];
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
										var result='';
										if(row.showFlag==2 && row.beginTime>row.serverTime){
											result+='<a href="javascript:editAwardActivity('+ value+ ',true)">编辑</a>|';
											result+='<a href="javascript:delAwardActivity('+ value+ ')">删除</a>|';
											result+='<a href="awardCode/show.html?id='+ value+ '">中奖码</a>';
											result+='|<a href="awardPlay/show.html?id='+ value + '">参与用户</a>';
										}
										else if(row.showFlag==2 && row.endTime<row.serverTime){
											result+='<a href="javascript:editAwardActivity('+ value+ ',false)">查看</a>|';
											result+='<a href="javascript:delAwardActivity('+ value+ ')">删除</a>|';
											result+='<a href="awardCode/show.html?id='+ value+ '">中奖码</a>';
											result+='|<a href="awardPlay/show.html?id='+ value + '">参与用户</a>';
										}
										else{
											result+='<a href="javascript:editAwardActivity('+ value+ ',false)">查看</a>|';
											result+='<a href="awardCode/show.html?id='+ value+ '">中奖码</a>';
											result+='|<a href="awardPlay/show.html?id='+ value + '">参与用户</a>';
										}
										return result;
									}
								} ] ],
						pagination : true,
						rownumbers : true,
						onClickRow : function(rowIndex) {
						}
					});
}



/*
 * 修改活动信息
 */
function checkBoxOrRadio(boxs, value) {
	for (var i = 0; i < boxs.length; i++) {
		if (boxs[i].value == value) {
			boxs[i].checked = true;
		}
	}
}
function setAwardList(count, award) {
	$("#id" + count).val(award.id);
	$("#awardType" + count).val(award.awardType);
	$("#awardName" + count).val(award.awardName);
	$("#awardPicUrl" + count).attr("src", award.awardPicUrl);
	$("#awardIconUrl" + count).attr("src", award.awardIconUrl);
	$("#awardDesc" + count).val(award.awardDesc);
	var availableBeginTime = longToDate(award.availableBeginTime,2);
	$('#availableBeginTime'+count).datebox('setValue',availableBeginTime);
	var availableEndTime = longToDate(award.availableEndTime,2);
	$('#availableEndTime'+count).datebox('setValue',availableEndTime);
	$("#awardAmount" + count).val(award.awardAmount);
	$("#awardAmount" + count).attr('disabled',true);
	$("#awardAmount" + count).attr('style','background-color:#cfcece;');
	$("#winLimitDay" + count).val(award.winLimitDay);
	$("#awardCodeType" + count).val(award.awardCodeType);
	$("#awardCodeType" + count).attr('disabled',true);
	$("#awardProperty" + count).val(award.awardProperty);
	$("#awardProperty" + count).attr('onchange',"selectProperty("+count+")");
	selectProperty(count);
	var videoValue=0;
	if(award.awardVideoId){
		videoValue = award.awardVideoId+","+award.awardVideoPartnerId+","+award.awardPrice;
		if(award.awardDisVipDuration){
			videoValue+=","+award.awardDisVipDuration;
		}
		else{
			videoValue+=",null";
		}
	}
/*	if(award.awardProperty==1){
		//实物
		$('#awardQrcodeFlag'+count).val(award.awardQrcodeFlag);
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
	}*/
	if(award.awardProperty==2){
		//电子兑换券
	}
	else if(award.awardProperty==3){
		//VIP时长
		$("#awardVipDuration" + count).val(award.awardVipDuration);
//		$("#vipAwardPayUrl" + count).attr("src", award.awardPayUrl);
	}
	else if(award.awardProperty==4){
		//电影票
		setTicket(count);
		$("#ticketProducts"+count).val(videoValue);
	}
	else if(award.awardProperty==5){
		//麦粒
		var awardMlAmount=award.awardMlAmount;
		if(parseFloat(awardMlAmount)){
			awardMlAmount=awardMlAmount/100;//转换为元
		}
		$("#awardMlAmount" + count).val(awardMlAmount);
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
		$("#discountAwardPayUrl" + count).attr("src", award.awardPayUrl);
	}
	else if(award.awardProperty==7){
		//代金券
	}
}
function editAwardActivity(id,isEdit) {
	reset();
	firstTab();
	
	if(isEdit){
		$("#submitDiv").attr("style", "");
	}
	else{
		$("#submitDiv").attr("style", "display:none");
	}
	
	var rowInfo = $('#award_activity_table').datagrid('getSelected');
	if (rowInfo) {
		$('#id').val(rowInfo.id);
		$('#title').val(rowInfo.title);
		$('#type').val(rowInfo.type);
		$('#logoUrl').attr("src", rowInfo.logoUrl);
		$('#bgUrl').attr("src", rowInfo.bgUrl);
		$('#playingBgUrl').attr("src", rowInfo.playingBgUrl);
		$('#endTitle').val(rowInfo.endTitle);
		$('#infoBgUrl').attr("src", rowInfo.infoBgUrl);
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
		var payBeginTime = longToDate(rowInfo.payBeginTime,1);
		$('#payBeginTime').datebox('setValue', payBeginTime);
		var payEndTime = longToDate(rowInfo.payEndTime,1);
		$('#payEndTime').datebox('setValue', payEndTime);
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



/*
 * 提交
 */
//获取奖品List
function getAwardList() {
	var patt=/^(0|[1-9]\d*)$/;//非负整数
	var reg=/^[0-9]+(.[0-9]{2})?$/;//非负小数
	var tempBasePath=getAppBasePath();
	
	var awardList = "[";
	for (var i = 1; i <= customId; i++) {
		if($('#id'+i).length<=0){
			continue;
		}
		var id = $("#id" + i).val();
		if (!id) {
			id = 0;
		}
		var awardType = $("#awardType" + i).val();
		var awardName = $("#awardName" + i).val();
		var awardPicUrl = document.getElementById('awardPicUrl' + i).src;
		if(awardPicUrl.replace(tempBasePath,'').indexOf('.')<=0){
			alert('必须上传活动奖品图片!');
			return "";
		}
		var awardIconUrl = document.getElementById('awardIconUrl' + i).src;
		if(awardIconUrl.replace(tempBasePath,'').indexOf('.')<=0){
			alert('必须上传奖品icon!');
			return "";
		}
		var awardDesc = $("#awardDesc" + i).val();
		var availableBeginTime =$('#availableBeginTime' + i).datebox('getValue');
		var availableEndTime = $('#availableEndTime' + i).datebox('getValue');
		if(!(availableBeginTime && availableEndTime)){
			alert('奖品有效时间段不能为空！');
			return "";
		}
		if(availableBeginTime>availableEndTime){
			alert('奖品有效时间结束时间不能小于开始时间！');
			return "";
		}
		var awardAmount = $("#awardAmount" + i).val();
		if(!patt.test(awardAmount)){
			alert('奖品数量请填写非负整数！');
			return "";
		}
		var winLimitDay = $("#winLimitDay" + i).val();
		if(!patt.test(winLimitDay)){
			alert("每天中奖上限请填写非负整数！");
			return "";
		}
		var awardCodeType = $('#awardCodeType' + i).val();
		var awardProperty = $('#awardProperty' + i).val();
		
		var awardQrcodeFlag = 0;
		var userInfoType = "";
		
		var awardVipDuration = 0;
		
		var awardVideoId ="";
		var awardVideoName = "";
		
		var awardMlAmount = 0;
		
		var awardVideoType = $("#awardVideoType" + i).val();
		var awardDiscount = 0;
		
		var awardPayUrl="";
		/*if (awardProperty == 1) {
			awardQrcodeFlag = $("#awardQrcodeFlag" + i).val();
			var obj = document.getElementsByName('userInfoType' + i);
			for (var j = 0; j < obj.length; j++) {
				if (obj[j].checked)
					userInfoType += obj[j].value + ',';
			}*/
		if (awardProperty == 3) {
			awardVipDuration = $("#awardVipDuration" + i).val();
			if(!patt.test(awardVipDuration)){
				alert('vip时常请填写非负整数！');
				return "";
			}
			/*awardPayUrl=document.getElementById('vipAwardPayUrl' + i).src;
			if(awardPayUrl.indexOf('.')<=0){
				alert('必须上传支付记录图片!');
				return "";
			}*/
		} else if (awardProperty == 4) {
			awardVideoId = $("#ticketProducts" + i).val();
			var awardVideo = document.getElementById("ticketProducts"+i);
			awardVideoName = awardVideo.options[awardVideo.selectedIndex].text;
		} else if (awardProperty == 5) {
			awardMlAmount = $("#awardMlAmount" + i).val();
			if(!reg.test(awardMlAmount)){
				alert('麦粒请填写非负整数或非负小数（小数精度必须为2，例如1.11）！');
				return "";
			}
			awardMlAmount=parseFloat(awardMlAmount)*100;//元转换为分
		} else if (awardProperty == 6) {
			awardDiscount = $("#awardDiscount" + i).val();
			if(!patt.test(awardDiscount)){
				alert("折扣请填写非负整数！");
				return "";
			}
			var awardVideo ;
			if(awardVideoType==2009){
				awardVideoId = $("#vipProducts"+i).val();
				awardVideo = document.getElementById("vipProducts"+i);
			}else if(awardVideoType==2006){
				awardVideoId = $("#blueProducts"+i).val();
				awardVideo = document.getElementById("blueProducts"+i);
			}
			else{
				alert("请选择vip或极清首映！");
				return "";
			}
			awardVideoName = awardVideo.options[awardVideo.selectedIndex].text;
			awardPayUrl=document.getElementById('discountAwardPayUrl' + i).src;
			if(awardPayUrl.replace(tempBasePath,'').indexOf('.')<=0){
				alert('必须上传支付记录图片!');
				return "";
			}
		}
		awardList += "{\"id\":" + id + ",\"awardType\":\"" + awardType
				+ "\",\"awardName\":\"" + awardName + "\",\"awardAmount\":\""
				+ awardAmount +"\",\"winLimitDay\":\""+winLimitDay+ "\",\"awardProperty\":\"" + awardProperty
				+ "\"";
/*		awardList+=",\"userInfoType\":\"" + userInfoType
				+ "\"";*/
		awardList+=",\"awardVipDuration\":\"" + awardVipDuration + "\","
				+ "\"awardMlAmount\":\"" + awardMlAmount
				+ "\"";
/*		awardList+=",\"awardQrcodeFlag\":\"" + awardQrcodeFlag
				+ "\"";*/
		awardList+=",\"awardPayUrl\":\"" + awardPayUrl
		+ "\"";
		awardList+=",\"awardCodeType\":\"" + awardCodeType + "\""
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
	var patt=/^(0|[1-9]\d*)$/;//非负整数
	var reg=/^[0-9]+(.[0-9]{2})?$/;//非负小数
	var tempBasePath=getAppBasePath();
	
	var id = $('#id').val();
	var title = $('#title').val();// 不能为空
	var type = $('#type').val();// 不能为空
	var logoUrl = document.getElementById('logoUrl').src;
	var bgUrl = document.getElementById('bgUrl').src;
	var playingBgUrl = document.getElementById('playingBgUrl').src;
	var endTitle = $('#endTitle').val();
	var infoBgUrl = document.getElementById('infoBgUrl').src;
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
	var awardRemark = $("#awardRemark").val();
	//提交前判断
	if(!title){
		alert('活动标题不能为空！');
		return;
	}
	if(!type || type==0){
		alert('活动类型不能为空！');
		return;
	}
	if(logoUrl.replace(tempBasePath,'').indexOf('.')<=0){
		alert('必须上传活动logo!');
		return;
	}
	if(bgUrl.replace(tempBasePath,'').indexOf('.')<=0){
		alert('必须上传活动大背景图!');
		return;
	}
	if(playingBgUrl.replace(tempBasePath,'').indexOf('.')<=0){
		alert('必须上传活动进行时封面!');
		return;
	}
	if(infoBgUrl.replace(tempBasePath,'').indexOf('.')<=0){
		alert('必须上传活动详情页封面!');
		return;
	}
	if(!(beginTime && endTime)){
		alert('活动时间段不能为空！');
		return;
	}
	if(beginTime>endTime){
		alert('活动结束时间不能小于活动开始时间');
		return;
	}
	if(!patt.test(awardWinRatio)){
		alert('中奖概率请输入非负整数!');
		return;
	}
	if(!awardPlayerType){
		alert('适用人群不能为空！');
		return;
	}
	if(awardPlayerType==3){
		if(!(payBeginTime && payEndTime)){
			alert('消费时间段不能为空！');
			return;
		}
		if(payBeginTime>payEndTime){
			alert('消费时间段结束时间不能小于开始时间！');
			return;
		}
		if(!reg.test(paySum)){
			alert('消费金额请输入非负整数或非负小数（小数精度必须为2，例如1.11）！');
			return;
		}
		if(!isNaN(paySum)){
			paySum=paySum*100;//元转分
		}
	}
	if(!awardRemark){
		alert('活动备注不能为空！');
		return;
	}
	
	
	
	var phoneBindType = $("input[name='phoneBindType']:checked").val();// 不能为空
	var playLimitDay = $('#playLimitDay').val();
	var playPromptDay = $('#playPromptDay').val();
	var playLimitTotal = $('#playLimitTotal').val();
	var playPromptTotal = $('#playPromptTotal').val();
	var playPromptWin = $('#playPromptWin').val();
	var playPromptLost = $('#playPromptLost').val();
	if(!phoneBindType){
		alert('绑定手机号方式不能为空！');
		return;
	}
	if(!patt.test(playLimitDay)){
		alert('每日抽取次数请输入非负整数！');
		return;
	}
	if(!patt.test(playLimitTotal)){
		alert('每人最多允许抽取总次数请输入非负整数！');
		return;
	}
	if(parseFloat(playLimitTotal)<parseFloat(playLimitDay)){
		alert('每人最多允许抽取总次数必须大于等于每日抽取次数!');
		return;
	}
	
	
	
	var awardList = getAwardList();
	if (awardList == "") {
		return;
	}
	
	
	
	var awardActivity = {
		id : id,
		title : title,
		type : type,
		logoUrl : logoUrl,
		bgUrl : bgUrl,
		playingBgUrl : playingBgUrl,
		endTitle : endTitle,
		infoBgUrl : infoBgUrl,
		endText : endText,
		strBeginTime :beginTime,
		strEndTime : endTime,
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
	if (id!=0) {
		$.post('awardActivity/update.json', awardActivity, function(data) {
			if (data.code == 1) {
				$('#award_activity_dialog').dialog('close');
				$('#award_activity_table').datagrid('load', parameter);
				$.messager.show({
					title : titleInfo,
					msg : '修改成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
			} else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		}, "json");
	} else {
		$.post("awardActivity/add.json", awardActivity, function(data) {
			if (data.code == 1) {
				$('#award_activity_dialog').dialog('close');
				$('#award_activity_table').datagrid('load', parameter);
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		}, "json");
	}
}



/*
 * 删除活动信息
 */
function delAwardActivity(id) {
	$.messager.confirm('Warning', 'Warning：删除后，该活动的所有数据将不可恢复，是否继续此操作?',
			function(r) {
				if (r) {
					var activity = {
						'id' : id
					};
					$.post('awardActivity/del.json', activity, function(data) {
						if (data.code == 1) {
							$('#award_activity_table').datagrid('load',parameter);
							$.messager.show({
								title : titleInfo,
								msg : '已删除！',
								timeout : timeoutValue,
								showType : 'slide'
							});
						} else {
							$.messager.alert(titleInfo, '删除失败！');
						}
					}, 'json');
				}
			});
}



/*
 * 上下移
 */
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
				$('#award_activity_table').datagrid('load', parameter);
				$.messager.show({
					title : titleInfo,
					msg : '移动成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
			} else {
				$.messager.alert(titleInfo, '移动失败！');
			}
		}, "json");
	}
}
/*
 * 是否上线
 */
function isOn(id){
	$.messager.confirm('Warning', '确定要上线?', function(r) {
		if (r) {
			$.post('awardActivity/updateShowFlagById.json', {id:id,showFlag:1}, function(data) {
				if (data.code == 1) {
					$('#award_activity_table').datagrid('load', parameter);
					$.messager.show({
						title : titleInfo,
						msg : '已上线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
				} else {
					$.messager.alert(titleInfo, '上线失败！');
				}
			}, 'json');
		}
	});
}
/*
 * 是否下线
 */
function isOff(id){
	$.messager.confirm('Warning', '确定要下线?', function(r) {
		if (r) {
			$.post('awardActivity/updateShowFlagById.json', {id:id,showFlag:2}, function(data) {
				if (data.code == 1) {
					$('#award_activity_table').datagrid('load', parameter);
					$.messager.show({
						title : titleInfo,
						msg : '已下线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
				} else {
					$.messager.alert(titleInfo, '下线失败！');
				}
			}, 'json');
		}
	});
}