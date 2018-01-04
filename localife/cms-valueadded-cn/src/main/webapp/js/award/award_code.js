var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
//------------------------------------------------
//-----------倒入中奖码
//------------------------------------------------
/*
 * 下载excle模板
 */
function downLoadModel(id){
	var excelName = "AwardCodeModel";
	$.ajax({
		url : "excel/exportModel.json",
		async : true, // 
		type : "POST",
		dataType : "json",
		data:{"id":id},
		success : function(data) {
			if ( 1== data.code) {
				var excelUrl =  data.msg+excelName+".xls";
				window.location.href= excelUrl;
			} else {
				$.messager.alert(titleInfo, '下载失败！');
			}
		}
	});
}
/*
 * 上传excle文件
 */
//导入excel
function initUploadExcel(){
	var import_button = $("#importCodeExcel");
	new AjaxUpload(
		import_button,{
			action : 'excel/uploadExcel.html',
			autoSubmit : true,
			name : 'file',//文件对象名称（不是文件名）
			data:{},
			onChange:function(file, extension){
				var d=/\.[^\.]+$/.exec(file); //文件后缀
				if(d!=".xls"&&d!=".xlsx"){
					$.messager.alert(titleInfo, '文件格式错误，请上传.xls或.xlsx格式！');
					return false;
				}else{
					$("#ImportFileName").text(file);
				}
			},
			onSubmit: function(file, extension){//在提交的时候触发
			},
			onComplete:function(file,response){
				$('#hiddenPath').val(response);
				$.messager.show({title : titleInfo,msg : '文件已选定！',timeout : timeoutValue,showType : 'slide'});
			}	
		});
};
//打开倒入中奖码窗口
function addAwardCode(){
	$('#add_award_code').click(function(){
		initUploadExcel();
		$('#hiddenPath').val('');
		$("#ImportFileName").text("上传的文件名显示在这里");
		$('#import_code_dialog').dialog('open');
	});
}
/*
 * 执行生成中奖码操作
 */
function submit_excel(){
	var codeExcelPath = $('#hiddenPath').val();
	$.post("awardCode/saveExcel.json",{"codeExcelPath":codeExcelPath},function(data){
		if (1==data) {
			$('#import_code_dialog').dialog('close');
			$.messager.show({title : titleInfo,msg : '保存成功！',timeout : timeoutValue,showType : 'slide'});
			$('#award_code_table').datagrid('reload', parameter);
		}
		else if(data==3){
			$.messager.alert(titleInfo, "有重复中奖码，不能倒入！");
		}
		else if(data==4){
			$.messager.alert(titleInfo, "有空中奖码，不能倒入！");
		}
		else {
			$.messager.alert(titleInfo, '保存失败！');
		}
	},"json");
}
//------------------------------------------------
//-----------查询中奖码
//------------------------------------------------
//初始化参与用户
function initAwardCodeInfo() {
	parameter = {
		activityId:$('#awardActivityId').val()
	};
	var userPhone = $('#userPhone').val();
	if (userPhone != ""||userPhone!=null) {
		parameter.userPhone = userPhone;
	}
	var awardCode = $('#awardCode').val();
	if (awardCode != ''||awardCode!=null) {
		parameter.awardCode = awardCode;
	}
	var awardName = $('#awardName').val();
	if (awardName != ''||awardName!=null) {
		parameter.awardName = awardName;
	}
	var awardType = $('#awardType').val();
	if (awardType != ''||awardType!=null) {
		parameter.awardType = awardType;
	}
	var acceptFlag = $('#acceptFlag').val();
	if (acceptFlag != 0) {
		parameter.acceptFlag = acceptFlag;
	}
	$('#award_code_table').datagrid(
		{
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#award_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'awardCode/getList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
					{field : 'awardCode',title : '中奖码',width : 80,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'awardName',title : '奖品名称',width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'awardType',title : '奖品类型',width : 40,hidden : false,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'realName',title : '领取者姓名',width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'userPhone',title : '领取者手机号',width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'mac',title : '大麦通行证',width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'playTime',title : '中奖时间',width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'availableEndTime',title : '到期时间',width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{field : 'acceptFlag',title : '中奖状态',width : 40,
						formatter : function(value, row, index) {
							if(value==1){
								return "中奖";
							}else{
								return "未中奖";
							}
						}
					},
					{field : 'userAddress',title : '邮寄信息',width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					}] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}
//查询活动
function queryAwardCode() {
	$('#query_award').click(function() {
		initAwardCodeInfo();
	});
}
//------------------------------------------------
//-----------导出中奖码
//------------------------------------------------
function initDownLoad(id){
	/*
	 * hll:注释源码
	 */
	//$('#export_award_code').click(function(data){
		var myDate = new Date();  
		var excelName = "awardCodeInfo_"+myDate.getTime();
		$.ajax({
			url : "excel/exportExcel.json",
			async : true, // 
			type : "POST",
			dataType : "json",
			data:{"excelName":excelName,"id":id},
			success : function(data) {
				if ( 1== data.code) {
					var excelUrl =  data.msg+excelName+".xls";
					window.location.href= excelUrl;
				} else {
					$.messager.alert(titleInfo, '下载失败！');
				}
			}
		});
	/*
	 * hll:注释源码
	 */
	//});
}
/*
 * 初始化
 */
$(function() {
	addAwardCode();
	queryAwardCode();
	initAwardCodeInfo();
    $('#import_code_dialog').dialog({
		buttons : [ {
			text : '生成',
			handler : function() {
				submit_excel();
			}
		}, {
			text : '取消',
			handler : function() {
				$('#import_code_dialog').dialog('close');
			}
		} ]
	});
});











