//ajax同步
$.ajaxSetup({
  async: false
});
$(function() {
    //初始化内容
    initContentInfo();
    uploadPic();
    initProvinceList();
    initCityList($("#province").val());
    inttPrivinceId();
    initCityId($("#provinceId").val());
    // 初始化编辑弹窗
    $('#recommend_dialog').dialog({
        buttons : [ {
            text : '提交',
            handler : function() {
                submitAward();
            }
        },{
            text : '取消',
            handler : function() {
                reset();
                $('#recommend_dialog').dialog('close');
            }
        } ]
    });

    //uploadPanorama();
    //uploadBg();
    //uploadBeginProfile();
    //uploadEndProfile();
    //uploadAwardIcon(1);
    //uploadAwardPic(1);
    /*hll:注释原有代码
     // 选择奖品属性
     selectProperty(1);
     // 选择是否填写详细信息
     selectUserInfo(1);
     */
    // 加载极清首映table
    //setTicket(1);
    // 设置VIP、极清首映单片
    //setVipAndBlue(1);
    /*hll:注释原有代码
     firstTab();
     */
    // queryActivity();
    addAwardActivity();
    //--------------版本页面控制------start-------------
    $("#content_version_list").dialog({
        width:850,
        closed:true,
        title:'版本管理',
        modal:true
    });
    $('#addContentVersion').linkbutton({
        iconCls:'icon-add',
        plain:true
    });
    $('#searchContentVersion').linkbutton({
        iconCls:'icon-search',
        plain:true
    });
    $('#Q_content_version_dialog').dialog({
        width:650,
        closed:true,
        title:'版本设置',
        modal:true,
        buttons:[{
            text:'保存',
            handler:function(){
                submitContentVersion();
            }
        },{
            text:'取消',
            handler:function(){
                $("#Q_content_version_dialog").dialog('close');
                resetVersion();
            }
        }]
    });
    //--------------版本页面控制------end-------------
    //--------------版本页面控制--s----start-------------
    $("#content_version_screenshot_list").dialog({
        width:550,
        closed:true,
        title:'版本截图管理',
        modal:true
    });
    $('#addContentVersionScreenshot').linkbutton({
        iconCls:'icon-add',
        plain:true
    });
    $('#searchContentVersionScreenshot').linkbutton({
        iconCls:'icon-search',
        plain:true
    });
    $('#Q_content_version_screenshot_dialog').dialog({
        width:350,
        closed:true,
        title:'截图设置',
        modal:true,
        buttons:[{
            text:'保存',
            handler:function(){
                submitContentVersionScreenshot();
            }
        },{
            text:'取消',
            handler:function(){
                $("#Q_content_version_screenshot_dialog").dialog('close');
                resetVersionScreenshot();
            }
        }]
    });
    //--------------版本页面控制---s---end-------------

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
            $("#picUrlHidden").val(data.msg);
		}
	});
}
//将button按钮赋予图片上传功能
function buttonUploadBySize(button, img, size, Pwidth, Pheight) {
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
                img.attr("src", data.msg);
                $("#picUrlHidden").val(data.msg);
                alert("上传成功");

               /* ttimg.attr("src", data.msg);
                $("<img/>").attr("src", $(ttimg).attr("src")).load(function() {
                    if(Pwidth != this.width && Pheight != this.height){
                        alert("该图片宽高不符合要求,请上传："+Pwidth+" X "+Pheight+"!");
                        ttimg.attr("src", '');
                    }else{
                        img.attr("src", data.msg);
                        $("#picUrlHidden").val(data.msg);
                        alert("上传成功");
                    }
                });*/
            }
            if(data.code == 2){
                alert(data.msg);
            }

        }
    });
}
//活动logo
function uploadPic() {

	var button = $("#uploadPicUrl");
	var img = $("#picUrl");
    buttonUploadBySize(button, img, 4, 895, 366);
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
    $('#picUrl').attr("src", "");
    $('#picUrlHidden').val('');
}

//显示基础设置模块
function firstTab() {
	$("#tabOne").attr("style", "");
}

//增加活动信息
function addAwardActivity() {
	$('#add_recommend').click(function() {
        inttPrivinceRid();
        initCityListRs($('#provinceIdR').val());
		reset();
        uploadPic();
		$("#recommend_dialog").dialog('open');
	});
}
function initProvinceListEdit() {
    var openRole = $("#openRole").val();
    $("#provinceIdR").empty();
    $("#cityId").empty();
    var province = $("#province").val();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#provinceIdR").append("<option value='"+proCode+"' >" + proName + "</option>");
    }else{
        $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
            var resu = data.result;
            $("#provinceIdR").append("<option value='00'>默认省</option>");
            for (var o in resu){
                if(resu[o].name != '') {
                    $("#provinceIdR").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}
//设置编辑信息
function setAward(rowInfo) {
/*    var rowInfo = $('#content_table').datagrid('getSelected');
    if (rowInfo) {
        setAward(rowInfo);
        $('#recommend_dialog').dialog('open');
    }*/


    initCityListRs(rowInfo.provinceId);
	$('#id').val(rowInfo.id);
	$('#name1').val(rowInfo.name);
	$('#cityId').val(rowInfo.cityId);
	$('#provinceIdR').val(rowInfo.provinceId);
    $('#picUrl').attr("src", rowInfo.picUrl);
    $('#picUrlHidden').val(rowInfo.picUrl);
}

//编辑
function editContent(id,typeId) {
    inttPrivinceRid();
    reset();
	firstTab();
    uploadPic();
    var rowInfo = $('#content_table').datagrid('getSelected');
	if (rowInfo) {
        setAward(rowInfo);
		$('#recommend_dialog').dialog('open');
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
        if (provinceId != '' && provinceId!='0') {
            parameter.provinceId = provinceId;
        }
        if (cityId != '' && cityId!='0') {
            parameter.cityId = cityId;
        }
    }
if (name != '') {
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
        fitColumns : true,
        collapsible : true,
        url : 'localLifeRecommend/getList.json',
        queryParams : parameter,
        remoteSort : false,
        singleSelect : true,
        idField : 'id',
        columns : [ [
            {
                field : 'name',
                title : '推荐位名称',
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
             field : 'seq',
             title : '排序',
             width : 20,
             hidden : false,
             formatter : function(value, row, index) {
             var seq = row.seq;
             return '<a href="javascript:move('
             + seq + ',1)">上移</a>|'
             + '<a href="javascript:move('
             + seq + ',2)">下移</a>|'
             +'<a href="javascript:topMove('
                 + seq +')">置顶</a>';
             }
             },
            {
                field : 'id',
                title : '操作',
                width : 40,
                formatter : function(value, row, index) {
                    var ret =  "";
                    ret += '<a href="javascript:editContent(' + value +','+ row.typeId + ');">编辑</a>&nbsp;&nbsp;';
                    ret += '|&nbsp;&nbsp;<a href="javascript:subjectInfo('+ value + ');">关联内容</a>&nbsp;&nbsp;';
                    ret += '|&nbsp;&nbsp;<a href="javascript:delRecommend(' + value + ',' + row.provinceId + ',' + row.cityId + ');">删除</a>';
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
    parameter.provinceId = $('#provinceIdR').val();
    if ( $('#provinceIdR').val() == null ||  $('#provinceIdR').val() == "") {
        alert("省份不能为空");
        flag = false;
    }
    parameter.cityId = $("#cityId").val();
    if ( $('#cityId').val() == null ||  $('#cityId').val() == "") {
        alert("城市不能为空");
        flag = false;
    }
    parameter.provinceName = $("#provinceIdR").find("option:selected").text();
    parameter.cityName = $("#cityId").find("option:selected").text();
    parameter.picUrl = document.getElementById('picUrl').src;
    if ($('#picUrlHidden').val() == null || $('#picUrlHidden').val() == "") {
        alert("图片不能为空");
        flag = false;
    }
	if (flag == false) {
		return;
    }
	if (id != null && id != "" && id != 0) {
		$.post('localLifeRecommend/update.json', parameter, function(data) {
			if (data.code == 1) {
				$('#recommend_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '修改成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				reset();// 清空页面残留信息
				$('#content_table').datagrid('reload');
			} else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		}, "json");
	} else {
		$.post("localLifeRecommend/add.json", parameter, function(data) {
			if (data.code == 1) {
				$('#recommend_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				reset();// 清空页面残留信息
				$('#content_table').datagrid('reload');
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
function delRecommend(id,proId,cityId) {
    debugger;
	$.messager.confirm('Warning', 'Warning：该推荐位有可能已经绑定内容，删除可能引发一些问题！确定要删除?',
			function(r) {
				if (r) {
					var activity = {
						'id' : id,
                        'provinceId':proId,
                        'cityId':cityId
					};
					$.post('localLifeRecommend/del.json', activity, function(data) {
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
//--------------上下移-------------
//-------------------------------------------------------
//上下移
function move(value,type) {
    var name = $('#searchName').val();
    var proCode = $('#province').val();
    var cityCode  = $('#city').val();
		$.post('localLifeRecommend/move.json', {
            'name' : name,
			'seq' : value,
			"type" : type,
            'cityId' : cityCode,
            'provinceId' : proCode
		}, function(data) {
			if (data.code == 1) {
				$.messager.show({
					title : titleInfo,
					msg : '移动成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#content_table').datagrid('reload', parameter);
			} else if(data.code == 3){
                $.messager.alert(titleInfo, '已经是第一位！无法上移');
            }else if(data.code == 4){
				$.messager.alert(titleInfo, '已经是最后一位!无法下移');
			}else {
                $.messager.alert(titleInfo, '移动失败' +
                    '');
            }
		}, "json");
}
function topMove(value,index) {
        $.post('localLifeRecommend/topMove.json',{'seq':value},function (data) {
            if(data.code == 1){
                $.messager.show({
                    title : titleInfo,
                    msg : '移动成功！',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
                $('#content_table').datagrid('reload', parameter);
            }else if(data.code  == 3 ){
                $.messager.alert(titleInfo, '已经是第一位！');
            } else {
                $.messager.alert(titleInfo, '移动失败！');
            }
        },"json");
}
//-------------------------------------------------------
//--------------是否上线-------------
//-------------------------------------------------------
function isOn(id,onlineType) {
    if (onlineType == 1) {
        alert("已经是上线状态");
    } else {
        $.messager.confirm('Warning', '确定要上线?', function (r) {
            if (r) {
                $.post('localLifeRecommend/update.json', {id: id, onlineType: 1}, function (data) {
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
function isOff(id,onlineType){
    if(onlineType == 0){
        alert("已经是下线状态")
    }else {
	$.messager.confirm('Warning', '确定要下线?', function(r) {
		if (r) {
			$.post('localLifeRecommend/update.json', {id:id,onlineType:0}, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已下线！',
						timeout : timeoutValue,
						showType : 'slide'
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
/** 内容管理打开 */
function initContentVersionView(contentId){
    //initUploadJs();
    /*var rowInfo = $('#vipEntrance_detail_table').datagrid('getSelected');
    EntranceId=rowInfo.entranceId;*/
    initContentVersionGrid(contentId);
    //initContentAllList();
    $('#versionContentId').val(contentId);

    $("#content_version_list").dialog('open');
}



































//-------------------------------------------------------
//--------------无用-------------
//-------------------------------------------------------

// 根据战区初始化分公司信息
function initBranch(zoneId) {
//	alert("zoneId="+zoneId);
    if (zoneId == "-1" || zoneId == "") {
        $("#branch").html("<option value='-1'>全部</option>");
    } else {
        $.post("zoneCity/getBranchByZone.json", { "zoneId" : zoneId }, function(data) {
            $("#branch").html("<option value='-1'>全部</option>");
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
        var province = $("#province").val();
        var proCode = $("#proCode").val();
        var proName = $("#proName").val();
        if(openRole != 1){
            $("#province").append("<option value='"+proCode+"'>" + proName + "</option>");
        }else{
            $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
                var resu = data.result;
                $("#province").append("<option value=''>全部</option>");
                $("#province").append("<option value='00'>默认省</option>");
                for (var o in resu){
                    if(resu[o].name) {
                        $("#province").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
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
        $("#city").append("<option value='"+cityCode+"'>" + cityName + "</option>");
    }else if(provinceId == ''){
        $("#city").append("<option value=''>-</option>");
    }else if(provinceId == 00){
        $("#city").append("<option value='0000'>默认市</option>");
    }else {
        $("#city").append("<option value=''>全部</option>");
        $.post("zoneTree/getCityList.json",{'pid':provinceId},function(data){
            var resu = data.result;
            for (var o in resu){
                if(resu[o].name != '') {
                    $("#city").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}

//初始化区域（省、州）
function initProvinceListN(){
    var openRole = $("#openRole").val();
    $("#provinceN").empty();
    var province = $("#province").val();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#provinceN").append("<option value='"+proCode+"' >" + proName + "</option>");
    }else{
        $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
            var resu = data.result;
            $("#provinceN").html("<option>-</option>");
            $("#provinceN").append("<option value='00'>默认省</option>");
            for (var o in resu){
                if(resu[o].name != '') {
                    $("#provinceN").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}
function initCityListN(provinceId){
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var cityName = $("#cityName").val();
    console.log(cityCode);
    $("#cityN").empty();
    var openRole = $("#openRole").val();
    if(openRole != 1){
        $("#cityN").append("<option value='"+cityCode+"'>"+ cityName +"</option>")
    }else if(provinceId == 00){
        $("#city").append("<option value='0000'>默认市</option>")
    } else {
        $.post("zoneTree/getCityList.json",{'pid':provinceId},function(data){
            $("#cityN").html("<option>-</option>");
            var resu = data.result;
            for (var o in resu){
                if(resu[o].name != '') {
                    $("#cityN").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}
//点击信息
function subjectInfo() {
    var rowInfo = $('#content_table').datagrid('getSelected');
    var recommendId = rowInfo.id;
    var contentID = rowInfo.contentID;
    $('#recommendAddId').val(recommendId);
    $('#search_nameN').val('');
    $("#search_nameN").show();
    $("#_isShow").show();
    subjectContentInfo(rowInfo);
    initProvinceListN();
    $("#typeIds").val(-1);
    initCityListN($("#provinceN").val());
    $('#content_detail_dialog').dialog('open');
}

//内容
function subjectContentInfo(row) {
    //contentBtn onclick
    var paracc = {
        recommendId : row.id,
        recommendExist : 1
    };
    /*$("#contentBtn").bind("click", function() {
        var url = 'localLifeContent/getContentByRecommendID.json';
        $('#recommend_content_table').datagrid('options').queryParams = paracc;
        $('#recommend_content_table').datagrid('options').url = url;
        $('#recommend_content_table').datagrid('options').pageNumber = 1;
        $('#recommend_content_table').datagrid('getPager').pagination({pageNumber:1});
        $('#recommend_content_table').datagrid('reload');
    });*/
    //已添加的推荐内容
    var autoContentColumns = getContentAutoColumns(1);
    $('#recommend_content_table').datagrid(
        {
            iconCls : 'icon-save',
            nowrap : true,
            autoRowHeight : true,
            striped : true,
            toolbar : '#content_table_toolbar',
            fitColumns : true,
            collapsible : true,
            url : 'localLifeContent/getContentByRecommendID.json',
            queryParams : paracc,
            remoteSort : false,
            singleSelect : true,
            border : false,
            idField : 'id',
            columns : autoContentColumns,
            pagination : true,
            pageSize : 10,
            rownumbers : true,
            onClickRow : function(rowIndex) {
            }
        });
    //未添加的推荐内容
    $("#searchBtn").bind("click", function() {
        var paraT = {
            recommendId : row.id,
            recommendExist : 0
        };
        var proCode = $("#proCode").val();
        var cityCode = $("#cityCode").val();
        var openRole = $('#openRole').val();
        var searchName = $("#search_nameN").val();
        var provinceId = $('#provinceId').val();
        var cityId = $('#cityIdN').val();
        var typeId = $('#typeIds').val();
        var url = 'localLifeContent/getContentByRecommendID.json';
 /*       if(openRole != 1){
            paraT.provinceId = proCode;
            paraT.cityId = cityCode;

        }else {*/
            if (provinceId != '' && provinceId!='0' ) {
                paraT.provinceId = provinceId;
            }
            if (cityId != '' && cityId!='0') {
                paraT.cityId = cityId;
            }
      /*  }*/
        if (searchName != '' && searchName != null) {
            paraT.name = searchName;
        }
        if (typeId != '' && typeId!='-1') {
            paraT.typeId = typeId;
        }
        $('#search_table').datagrid('options').queryParams = paraT;
        $('#search_table').datagrid('options').url = url;
        $('#search_table').datagrid('options').pageNumber = 1;
        $('#search_table').datagrid('getPager').pagination({pageNumber:1});
        $('#search_table').datagrid('reload');
    });
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var openRole = $('#openRole').val();
    if(openRole != 1){
        var paraNo = {
            recommendId : row.id,
            recommendExist : 0,
            provinceId : proCode,
            cityId : cityCode
        };
    }else {
        var paraNo = {
            recommendId : row.id,
            recommendExist : 0
        };
    }
    var autoSearchColumns = getContentAutoColumns(2);
    $('#search_table').datagrid(
        {
            iconCls : 'icon-save',
            nowrap : true,
            autoRowHeight : true,
            striped : true,
            toolbar : '#search_table_toolbar',
            fitColumns : true,
            collapsible : true,
            url : 'localLifeContent/getContentByRecommendID.json',
            queryParams : paraNo,
            remoteSort : false,
            singleSelect : true,
            border : false,
            idField : 'id',
            columns : autoSearchColumns,
            pagination : true,
            pageSize : 10,
            rownumbers : true,
            onClickRow : function(rowIndex) {
            }
        });
}

function  getContentAutoColumns(id) {
    var autoColumns = null;
    autoColumns = [ [
        {
            field : 'name',
            title : '内容名称',
            width : 40,
            align : 'center',
            formatter : function(value, row, index) {
                return value;
            }
        },
        {
            field : 'picUrl',
            title : '图片',
            width : 40,
            align : 'center',
            formatter : function(value, row, index) {
                return "<img style='height: 80px; width: 160px' src='" + value + "' />";
            }
        },
        {
            field : 'cityName',
            title : '城市',
            width :20,
            align : 'center',
            formatter : function(value, row, index) {
                return value;
            }
        },
        {
            field : 'typeId',
            title : '类型',
            width : 20,
            align : 'center',
            hidden : false,
            formatter : function(value, row, index) {
                var result;
                if(value==1){
                    result='应用';
                }
                else if(value==2){
                    result='视频';
                }
                else if(value==3){
                    result='图片';
                }
                return result;
            }
        },
        {
            field : 'state',
            title : '状态',
            width : 20,
            align : 'center',
            formatter : function(value, row, index) {
                if (1 == value) {
                    return '有效';
                } else {
                    return '<span style="color:red;">无效</span>';
                }
            }
        },
        {
            field : 'onlineType',
            title : '上线/下线',
            width :20,
            align : 'center',
            formatter : function(value, row, index) {
                if (1 == value) {
                    return '上线';
                } else {
                    return '<span style="color:red;">下线</span>';
                }
            }
        },
        {
            field : 'id',
            title : '操作',
            width : 30,
            align : 'center',
            formatter : function(value, row, index) {
                var str = '';
                if (id == 1) {
                    str += '<a href="javascript:contentByRecommendDel()">取消关联</a>';
                }
                if (id == 2) {
                    str += '<a href="javascript:contentAdd()">关联</a>';
                }
                return str;
            }
        }
    ] ];

    return autoColumns;
}

//推荐内容删除
function contentByRecommendDel() {
    var provinceId = $("#provinceId").val();
    var cityIdN = $("#cityIdN").val();
    var typeIds = $("#typeIds").val();
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var openRole = $('#openRole').val();
    var rowInfo = $('#recommend_content_table').datagrid('getSelected');
    var id = $('#recommendAddId').val();
    if(openRole != 1){
        var parameter1={recommendId :$('#recommendAddId').val(),recommendExist : 1,provinceId:proCode,cityId:cityCode};
        var parameter0={recommendId :$('#recommendAddId').val(),recommendExist : 0,provinceId:proCode,cityId:cityCode};
    }else {
    //parameter={id:rowInfo.id};
        var parameter1={recommendId :$('#recommendAddId').val(),recommendExist : 1};
        var parameter0={recommendId :$('#recommendAddId').val(),recommendExist : 0/*,provinceId:provinceId,cityId:cityIdN,typeId:typeIds*/};
    }
    if (rowInfo) {
        if (confirm("是否确认取消关联？")) {
            $.post("localLifeRecommend/update.json", {
                //id : rowInfo.recommendId,
                id : $('#recommendAddId').val(),
                contentId : 0
            }, function(data) {
                if (data.code == 1) {
                    $('#recommend_content_table').datagrid('reload',parameter1);
                    $('#search_table').datagrid('reload',parameter0);
                    buttonS(id);
                } else {
                    $.messager.show({
                        title : titleInfo,
                        msg : '取消关联失败！',
                        timeout : timeoutValue,
                        showType : 'slide'
                    });
                }
            }, "json");
        } else {

        }
    }
}

function contentAdd() {
    var provinceId = $("#provinceId").val();
    var cityIdN = $("#cityIdN").val();
    var typeIds = $("#typeIds").val();
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var openRole = $('#openRole').val();
    var id = $('#recommendAddId').val();
    var rowInfo = $('#search_table').datagrid('getSelected');
    if(openRole != 1){
        var parameter1={recommendId :$('#recommendAddId').val(),recommendExist : 1,provinceId:proCode,cityId:cityCode};
        var parameter0={recommendId :$('#recommendAddId').val(),recommendExist : 0,provinceId:proCode,cityId:cityCode};
    }else {
        var parameter1 = {recommendId: $('#recommendAddId').val(), recommendExist: 1};
        var parameter0 = {recommendId: $('#recommendAddId').val(), recommendExist: 0/*,provinceId:provinceId,cityId:cityIdN,typeId:typeIds*/};
    }
    if (rowInfo) {
        $.post("localLifeRecommend/contentDel.json",{
            id : $('#recommendAddId').val(),
            contentId : rowInfo.id,
            typeId : rowInfo.typeId
        },  function(data) {
            if (data.code == 1) {
                $('#recommend_content_table').datagrid('reload',parameter1);
                $('#search_table').datagrid('reload',parameter0);
                buttonS(id);
                $.messager.show({
                    title : titleInfo,
                    msg : '关联成功！',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
            } else {
                $.messager.show({
                    title : titleInfo,
                    msg : '已经关联,请先取消关联',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
            }
        }, "json");
    }
}
function inttPrivinceRid() {
    var openRole = $("#openRole").val();
    $("#provinceIdR").empty();
    $("#cityId").empty();
    var province = $("#province").val();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#provinceIdR").append("<option value='"+ proCode+"' >" + proName + "</option>");
    }else{
        $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
            var resu = data.result;
            $("#provinceIdR").append("<option value='00'>默认省</option>");
            for (var o in resu){
                if(resu[o].name != '') {
                    $("#provinceIdR").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}
function initCityListRs(provinceId) {
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
                if(resu[o].name != '') {
                    $("#cityId").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}


function  inttPrivinceId(){
    var openRole = $("#openRole").val();
    $("#provinceId").empty();
    $("#cityId").empty();
    var province = $("#province").val();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#provinceId").append("<option value='"+ proCode+"' >" + proName + "</option>");
    }else{
        $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
            var resu = data.result;
            $("#provinceId").html("<option value=''>全部</option>");
            $("#provinceId").append("<option value='00'>默认</option>");
            for (var o in resu){
                if(resu[o].name != '') {
                    $("#provinceId").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}

function initCityId(provinceId) {
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var cityName = $("#cityName").val();
    $("#cityIdN").empty();
    var openRole = $("#openRole").val();
    if(openRole != 1){
        $("#cityIdN").append("<option value='"+cityCode+"'>"+ cityName +"</option>");
        return;
    }else if(provinceId == ''){
        $("#cityIdN").append("<option value=''>-</option>");
    }else if(provinceId == 00)  {
        $("#cityIdN").append("<option value='0000'>默认市</option>");
    } else{
        $.post("zoneTree/getCityList.json",{'pid':provinceId},function(data){
            $("#cityIdN").append("<option value=''>全部</option>");
            var resu = data.result;
            for (var o in resu){
                if(resu[o].name != '') {
                    $("#cityIdN").append("<option value='" + resu[o].code + "'>" + resu[o].name + "</option>");
                }
            }
        },"json");
    }
}

function buttonS(id) {
        var paraT = {
            recommendId : id,
            recommendExist : 0
        };
        var proCode = $("#proCode").val();
        var cityCode = $("#cityCode").val();
        var openRole = $('#openRole').val();
        var searchName = $("#search_nameN").val();
        var provinceId = $('#provinceId').val();
        var cityId = $('#cityIdN').val();
        var typeId = $('#typeIds').val();
        var url = 'localLifeContent/getContentByRecommendID.json';
        /*       if(openRole != 1){
         paraT.provinceId = proCode;
         paraT.cityId = cityCode;

         }else {*/
        if (provinceId != '' && provinceId!='0' ) {
            paraT.provinceId = provinceId;
        }
        if (cityId != '' && cityId!='0') {
            paraT.cityId = cityId;
        }
        /*  }*/
        if (searchName != '' && searchName != null) {
            paraT.name = searchName;
        }
        if (typeId != '' && typeId!='-1') {
            paraT.typeId = typeId;
        }
        $('#search_table').datagrid('options').queryParams = paraT;
        $('#search_table').datagrid('options').url = url;
        $('#search_table').datagrid('options').pageNumber = 1;
        $('#search_table').datagrid('getPager').pagination({pageNumber:1});
        $('#search_table').datagrid('reload');
}