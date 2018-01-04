//ajax同步
$.ajaxSetup({
  async: false
});
$(function() {
    initContentInfo();
    uploadLogo();
    addAwardActivity();
    initProvinceList();
    initCityList($("#province").val());
    // 初始化编辑弹窗
    $('#content_dialog').dialog({
        buttons : [ {
            text : '提交',
            handler : function() {
                submitAward();
            }
        },{
            text : '取消',
            handler : function() {
                reset();
                $('#content_dialog').dialog('close');
            }
        } ]
    });
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
		initContentInfo();
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
	//buttonUpload(button, img);
    ButtonUploadByType_Size(button, img, 4, 370, 150)
}
//活动列表大背景图
function uploadPanorama() {
	var button = $("#uploadPanoramaUrl");
	var img = $("#panoramaUrl");
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
	
	/*
	 * hll:增加源码
	 */
	document.getElementById("awardList").innerHTML='';
	idSum=1;
}
//清空窗口控件
function reset() {
	$('#id').val(0);
	$('#name1').val('');
	$('#cityId').val('');
    $('#logoUrl').attr("src", "");
    $('#logoUrlHidden').val('');
}

//显示基础设置模块
function firstTab() {
	$("#tabOne").attr("style", "");
}

//增加活动信息
function addAwardActivity() {
	$('#add_award').click(function() {
        initProvinceListLogo();
        initCityId($('#provinceIdLogo').val());
		reset();
		//firstTab();
		$("#content_dialog").dialog('open');
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
//设置编辑信息
function setAward(rowInfo) {
    initCityId(rowInfo.provinceId);
	$('#id').val(rowInfo.id);
	$('#name1').val(rowInfo.name);
	$('#cityId').val(rowInfo.cityId);
	$('#provinceIdLogo').val(rowInfo.provinceId);
    $('#logoUrl').attr("src", rowInfo.logoUrl);
    $('#logoUrlHidden').val(rowInfo.logoUrl);

}

//编辑
function editContent(id,typeId,cityId) {
    initProvinceListLogo();
	reset();
	firstTab();
    //initCityQueryList(cityId);
    var rowInfo = $('#content_table').datagrid('getSelected');
	if (rowInfo) {
        setAward(rowInfo);
		$('#content_dialog').dialog('open');
	}
}

//-------------------------------------------------------
//--------------查询活动信息-------------
//-------------------------------------------------------
//查询内容信息
function initContentInfo() {
parameter = {};
var name = $('#searchName').val();
var countryId = $('#zone').val();
var provinceId = $('#province').val();
var cityId = $('#city').val();
    var openRole = $("#openRole").val();
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    if(openRole != 1){
        parameter.cityId =cityCode;
        parameter.provinceId = proCode;
    }else {
        if (provinceId != '' && provinceId!='0' ) {
            parameter.provinceId = provinceId;
        }
        if (cityId != '' && cityId!='0' ) {
            parameter.cityId = cityId;
        }
    }
if ($.trim(name) != '') {
    parameter.name = name;
}
$('#content_table')
    .datagrid(
    {
        iconCls : 'icon-save',
        nowrap : true,
        autoRowHeight : false,
        striped : true,
        toolbar : '#content_toolbar',
        fit : false,
        fitColumns : true,
        collapsible : true,
        url : 'localLifeLogo/getList.json',
        queryParams : parameter,
        remoteSort : false,
        singleSelect : true,
        idField : 'id',
        columns : [ [
            {
                field : 'name',
                title : '位置',
                width : 40,
                formatter : function(value, row, index) {
                    return value;
                }
            },
            {
                field : 'cityName',
                title : '城市',
                width : 40,
                formatter : function(value, row, index) {
                    return value;
                }
            },
            {
                field : 'onlineType',
                title : '上线/下线',
                width : 40,
                formatter : function(value, row, index) {
                    if (1 == value) {
                        return '<div class="abtn"><a href="javascript:isOff('+row.id+','+row.onlineType+')" class="a_off">下线</a><a href="javascript:isOn('+ row.id+ ','+row.onlineType+')" class="a_on a_on2">上线</a></div>';
                    } else {
                        return '<div class="abtn"><a href="javascript:isOff('+row.id+','+row.onlineType+')" class="a_on a_on2">下线</a><a href="javascript:isOn('+ row.id+','+row.onlineType+')" class="a_off">上线</a></div>';
                    }
                }
            },
            {
                field : 'createdTime',
                title : '创建时间',
                width : 40,
                formatter : function(value, row, index) {
                    return longToDate(value,0);
                }
            },
            {
                field : 'id',
                title : '操作',
                width : 40,
                formatter : function(value, row, index) {
                    var ret =  "";
                    ret += '<a href="javascript:editContent(' + value +','+ row.typeId +','+ row.cityId +');">编辑</a>&nbsp;&nbsp;';
                    ret += '|&nbsp;&nbsp;<a href="javascript:delContent(' + value + ','+row.provinceId + ',' + row.cityId +');">删除</a>';
                    return  ret;
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
	if (title == null || $.trim(title) == "") {
		alert("标题不能为空");
		flag = false;
	} else if (type == 0 || type == null || $.trim(type) == "") {
		alert("类型不能为空");
		flag = false;
	} else if (beginTime == null || $.trim(beginTime) == "") {
		alert("活动开始时间不能为空");
		flag = false;
	} else if (endTime == null || $.trim(endTime) == "") {
		alert("活动结束时间不能为空");
		flag = false;
	} else if (phoneBindType == null || $.trim(phoneBindType) == ""
			|| phoneBindType == 0) {
		alert("请选择绑定手机号方式");
		flag = false;
	} else if (awardWinRatio == null || $.trim(awardWinRatio) == ""
			|| awardWinRatio == 0) {
		alert("中奖概率不能为空");
		flag = false;
	} else if (awardPlayerType == null || $.trim(awardPlayerType) == ""
			|| awardPlayerType == 0) {
		alert("适用人群不能为空");
		flag = false;
	} else if (awardRemark == null || $.trim(awardRemark) == "") {
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
	debugger;
    var flag = true;
    var parameter = {};
    var id = $('#id').val();
    parameter.id = id;
    parameter.name = $('#name1').val();
    if ( $('#name1').val() == null ||  $.trim($('#name1').val()) == "") {
        alert("内容名称不能为空");
        flag = false;
    }
    parameter.provinceId = $('#provinceIdLogo').val();
    if ( $('#provinceIdLogo').val() == null ||  $.trim($('#provinceIdLogo').val()) == "") {
        alert("省份不能为空");
        flag = false;
    }
    parameter.provinceName = $("#provinceIdLogo").find("option:selected").text();
    parameter.cityName = $("#cityId").find("option:selected").text();
    parameter.cityId = $('#cityId').val();
    if ( $('#cityId').val() == null ||  $('#cityId').val() == "") {
        alert("城市不能为空");
        flag = false;
    }

    parameter.logoUrl = document.getElementById('logoUrl').src;
    if ( $('#logoUrlHidden').val() == null ||  $('#logoUrlHidden').val() == "") {
        alert("图片不能为空");
        flag = false;
    }
	if (flag == false) {
		return;
    }
	if (id != null && id != "" && id != 0) {
		$.post('localLifeLogo/update.json', parameter, function(data) {
			if (data.code == 1) {
				$('#content_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '修改成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				reset();// 清空页面残留信息
				$('#content_table').datagrid('reload');
			} else if(data.code == 2){
                alert("该城市已存在1号占位图！");
            } else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		}, "json");
	} else {
		$.post("localLifeLogo/add.json", parameter, function(data) {
			if (data.code == 1) {
				$('#content_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				reset();// 清空页面残留信息
				$('#content_table').datagrid('reload');
			} else if(data.code == 2){
                alert("该城市已存在1号占位图！");
            }else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		}, "json");
	}
}
//-------------------------------------------------------
//--------------删除活动信息-------------
//-------------------------------------------------------
//删除活动信息
function delContent(id,proId,cityId) {
	$.messager.confirm('Warning', 'Warning：该内容有可能已经绑定，删除可能引发一些问题！确定要删除?',
			function(r) {
				if (r) {
					var activity = {
						'id' : id,
						'provinceId' :proId,
                        'cityId':cityId
					};
					$.post('localLifeLogo/del.json', activity, function(data) {
						if (data.code == 1) {
							$.messager.show({
								title : titleInfo,
								msg : '已删除！',
								timeout : timeoutValue,
								showType : 'slide'
							});
							$('#content_table').datagrid('reload',
									parameter);
						} else {
							$.messager.alert(titleInfo, '删除失败！');
						}
					}, 'json');
				}
			});
}
//-------------------------------------------------------
//--------------是否上线-------------
//-------------------------------------------------------
function isOn(id,onlineType){
	if(onlineType){
		alert("已经是上线状态");
	}else {
        $.messager.confirm('Warning', '确定要上线?', function (r) {
            if (r) {
                $.post('localLifeLogo/update.json', {id: id, onlineType: 1}, function (data) {
                    if (data.code == 1) {
                        $.messager.show({
                            title: titleInfo,
                            msg: '已上线！',
                            timeout: timeoutValue,
                            showType: 'slide'
                        });
                        $('#content_table').datagrid('reload', parameter);
                    } else {
                        $.messager.alert(titleInfo, '上线失败！');
                    }
                }, 'json');
            }
        });
    }
}
function isOff(id,onlineType) {
    if (onlineType == 0) {
        alert("已经是下线状态");
    } else {
        $.messager.confirm('Warning', '确定要下线?', function (r) {
            if (r) {
                $.post('localLifeLogo/update.json', {id: id, onlineType: 0}, function (data) {
                    if (data.code == 1) {
                        $.messager.show({
                            title: titleInfo,
                            msg: '已下线！',
                            timeout: timeoutValue,
                            showType: 'slide'
                        });
                        $('#content_table').datagrid('reload', parameter);
                    } else {
                        $.messager.alert(titleInfo, '下线失败！');
                    }
                }, 'json');
            }
        });
    }
}



































//-------------------------------------------------------
//--------------无用-------------
//-------------------------------------------------------

// 根据战区初始化分公司信息
function initBranch(zoneId) {
//	alert("zoneId="+zoneId);
    if (zoneId == "-1" || zoneId == "") {
        $("#branch").html("<option <!--value='-1'-->>全部</option>");
    } else {
        $.post("zoneCity/getBranchByZone.json", { "zoneId" : zoneId }, function(data) {
            $("#branch").html("<option <!--value='-1'-->>全部</option>");
            $.each(data, function(dataIndex, obj) {
                $("#branch").append( '<option value=' + obj.id + '>' + obj.branchName + '</option>');
            });
        }, "json");
    }
}
//初始化区域（省、州）
function initProvinceList(){
    var openRole = $("#openRole").val();
    $("#province").empty();
    $("#city").empty();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#province").append("<option value='"+proCode+"'>" + proName + "</option>");
    }else{
        $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
            $("#province").append("<option value=''>全部</option>");
            $("#province").append("<option value='00'>默认省</option>");
            var resu = data.result;
            for (var o in resu){
            	if(resu[o].name  != ''){
                $("#province").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
                }
            }
        },"json");
    }
}

function initProvinceListLogo(){
    var openRole = $("#openRole").val();
    $("#provinceIdLogo").empty();
    $("#cityId").empty();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#provinceIdLogo").append("<option value='"+ proCode+"' >" + proName + "</option>");
    }else{
        $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
            var resu = data.result;
            $("#provinceIdLogo").append("<option value='00'>默认省</option>");
            for (var o in resu){
            	if(resu[o].name != ''){
            		if(resu[o].name != ''){
                		$("#provinceIdLogo").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
                    }
                }
            }
        },"json");
    }
}

//初始化城市列表
    function initCityList(provinceId){
        var proCode = $("#proCode").val();
        var cityCode = $("#cityCode").val();
        var cityName = $("#cityName").val();
        $("#city").empty();
        var openRole = $("#openRole").val();
    if(openRole != 1){
        $("#city").append("<option value='"+cityCode+"'>"+ cityName +"</option>")
    }else  if(provinceId == '') {
        $("#city").append("<option value=''>-</option>");}
	else if(provinceId == 00){
    	$("#city").append("<option value='0000'>默认市</option>");}
	else {
        $.post("zoneTree/getCityList.json",{'pid':provinceId},function(data){
            $("#city").html("<option value=''>全部</option>");
            var resu = data.result;
            for (var o in resu){
                $("#city").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
            }
        },"json");
    }
}
//编辑器城市列表
function initCityQueryList(cityId){
    $("#cityQuery").empty();
    if(cityId==null||cityId==""){
        $("#cityQuery").html("<option<!-- value='-1' -->>-</option>");
    }else{
        $.post("zoneCity/getCityByCityId.json",{"provinceId":provinceId},function(data){
            $("#cityQuery").html("<option <!--value='-1'--> >-</option>");
            $.each(data,function(dataIndex,ZoneCity){
                $("#cityQuery").append('<option value='+ZoneCity.cityId+'>'+ZoneCity.cityName+'</option>');
            });
        },"json");
    }
}


function initCityId(provinceId) {
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var cityName = $("#cityName").val();
    $("#cityId").empty();
    var openRole = $("#openRole").val();
    if(openRole != 1){
        $("#cityId").append("<option value='"+cityCode+"'>"+ cityName +"</option>");
        return;
    }if(provinceId == 00)  {
        $("#cityId").append("<option value='0000'>默认市</option>");
    } else{
        $.post("zoneTree/getCityList.json",{'pid':provinceId},function(data){
            var resu = data.result;
            for (var o in resu){
            	if(resu[o].name != ''){
                	$("#cityId").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
                }
            }
        },"json");
    }
}

//将button按钮赋予图片上传功能
function ButtonUploadByType_Size(button, img, size, Pwidth, Pheight) {
    /*var ttimg = $("#pictureUrl");*/
    new AjaxUpload(button, {
        action : 'util/uploadBySize.json?fileSize='+size,
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
            if(data.code == 1){
                alert("上传成功");
                img.attr("src", data.msg);
                $("#logoUrlHidden").val(data.msg);
                /*ttimg.attr("src", data.msg);
                 $("<img/>").attr("src", $(ttimg).attr("src")).load(function() {
                 if(Pwidth != this.width && Pheight != this.height){
                     alert("该图片宽高不符合要求,请上传："+Pwidth+" X "+Pheight+"!");
                    ttimg.attr("src", '');
                 }else{
                     img.attr("src", data.msg);
                     $("#logoUrlHidden").val(data.msg);
                     alert("上传成功");
                 }
                 });*/
            }
            if(data.code == 2){
                alert(data.msg);
/*                img.attr("src", '');
                $("#logoUrlHidden").val('');*/
            }

        }
    });
}