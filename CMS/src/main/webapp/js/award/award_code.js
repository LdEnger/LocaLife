	/*
	 * 初始化组合框
	 */
	function initCombobox(id,dataOrUrl,onSelectfun){
		var initMap={
				editable:false,
				mode:'local',
				//url:'',
				//method:'post',
				//data:dataOrUrl,
				valueField:'id',
				textField:'text',
				//1.filter注释
//				filter:function(q,row){
//					return true;
//				},
				//2.formatter注释
//				formatter:function(row){
//				},
				onSelect:function(record){
					if(onSelectfun){
						onSelectfun(record);
					}
				}
		};
		if(typeof(dataOrUrl)=='string'){
			initMap.url=dataOrUrl;
			initMap.method='post';
		}
		else{
			initMap.data=dataOrUrl;
		}
		$(id).combobox(initMap);
	}
var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;



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
//下载excle模板
function downLoadModel(id){
	$.ajax({
		url : "excel/exportModel.json",
		async : true, // 
		type : "POST",
		dataType : "json",
		data:{"id":id},
		success : function(data) {
			if ( 1== data.code) {
				setTimeout(function(){window.location.href= data.msg;}, 1000);
			}
			else if(2==data.code){
				$.messager.alert(titleInfo,'该活动中奖码全部设置完成!');
			}else {
				$.messager.alert(titleInfo, '下载失败！');
			}
		}
	});
}
//执行生成中奖码操作
function submit_excel(){
	var codeExcelPath = $('#hiddenPath').val();
	$.post("awardCode/saveExcel.json",{"codeExcelPath":codeExcelPath},function(data){
		if (1==data) {
			$('#import_code_dialog').dialog('close');
			$.messager.show({title : titleInfo,msg : '保存成功！',timeout : timeoutValue,showType : 'slide'});
			$('#award_code_table').datagrid('reload', parameter);
		}
		else if(data==3){
			$.messager.alert(titleInfo, "有重复中奖码，不能导入！");
		}
		else if(data==4){
			$.messager.alert(titleInfo, "有空中奖码，不能导入！");
		}
		else if(data==5){
			$.messager.alert(titleInfo,"excel表为空，不能导入！");
		}
		else if(data==6){
			$.messager.alert(titleInfo,"ID已被修改，不能导入！");
		}
		else {
			$.messager.alert(titleInfo, '保存失败！');
		}
	},"json");
}
//清空中奖码
function clearAwardCode(){
	var detailId=parseInt($('#detail_id').combobox('getValue'));
	if(detailId>0){
		$.ajax({
			url : "awardCode/deleteAwardCodeByDetailId.json",
			async : false,
			type : "POST",
			dataType : "json",
			data:{
				detailId:detailId
			},
			success : function(data) {
				if ( 1== data.code) {
					$.messager.alert(titleInfo, '清空中奖码成功！');
					initAwardCodeInfo();
				} else {
					$.messager.alert(titleInfo, '清空中奖码失败！');
				}
			}
		});
	}
	else{
		$.messager.alert(titleInfo, '请先选择奖品，然后再清空中奖码！');
	}
}
//导出中奖码
function initDownLoad(id){
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
}
//查询活动
function queryAwardCode() {
	$('#query_award').click(function() {
		initAwardCodeInfo();
	});
}
/*
 * 初始化
 */
$(function() {
	addAwardCode();
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
	queryAwardCode();
	
	
	
	initAwardCodeInfo();
	
	if($('#detail_id').length>0){
		var awardActivityId=$('#awardActivityId').val();
		initCombobox('#detail_id','awardCode/selectAwardListByAwardCodeType.json?activityId='+awardActivityId);
		$('#detail_id').combobox('select',-1);
	}
});



/*
 * 查询中奖码
 */
function initAwardCodeInfo() {
	parameter = {
		activityId:$('#awardActivityId').val()
	};
	var userPhone = $('#userPhone').val();
	if (userPhone) {
		parameter.userPhone = userPhone;
	}
	var awardCode = $('#awardCode').val();
	if (awardCode) {
		parameter.awardCode = awardCode;
	}
	var awardName = $('#awardName').val();
	if (awardName) {
		parameter.awardName = awardName;
	}
	var awardType = $('#awardType').val();
	if (awardType) {
		parameter.awardType = awardType;
	}
	var acceptFlag = $('#acceptFlag').val();
	if (acceptFlag != 0) {
		parameter.acceptFlag = acceptFlag;
	}
	$('#award_code_table').datagrid(
		{
			iconCls : 'icon-save',
			nowrap : false,
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
							var result='';
							if(value){
								result+=value;
							}
							if(row.postcode){
								result+='/'+row.postcode;
							}
							return result;
						}
					}] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}