var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
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
	};
	$("#createTime").datebox();
	$("#createStopTime").datebox();
	$("#activationTime").datebox();
	$("#activationStopTime").datebox();
	
//	initBranch();
	initAuth();
	initQuery();
	addSingle();
	addBatch();
	openImportCard();
	initDownLoad();
	initBatchDel();
	initBatchActive();
	initBatchCancel();
	isBatch();
	initCardInfo();
	// initUploadExcel();

	// 初始化单个激活
	$('#activition_card_dialog').dialog({
		buttons : [ {
			text : '激活',
			handler : function() {
				actCard();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#activition_card_dialog').dialog('close');
			}
		} ]
	});
	
	//初始化上传报告框
	$('#excel_report_dialog').dialog({
		buttons : [ {
			text : '复制结果',
			handler : function() {
				copyReport();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#excel_report_dialog').dialog('close');
			}
		} ]
	});

	// 初始化单个生成框
	$('#single_card_dialog').dialog({
		buttons : [ {
			text : '生成',
			handler : function() {
				var autoActiveTimeLength = $('#s_editAutoActiveTimeLength').val();
				if(""!=$.trim(autoActiveTimeLength)){
					var re = /^[1-9]\d*$/;
					var check=re.test(autoActiveTimeLength);
					if(!check){
						$.messager.alert(titleInfo, '有效期请填写正整数或置空！');
						return false;
					};
					if(autoActiveTimeLength>1095){
						$.messager.alert(titleInfo, '有效期不能大于1095！');
						return false;
					}
					$.messager.confirm('提示信息', "激活卡到期时间<span style='font-size:20px;color:red;'>"+autoActiveTimeLength+"</span>天，生成后不可修改", function(r) {
						if (r) {
							submit_single_form();
						}
					});
				}else{
					$.messager.confirm('提示信息', "激活卡到期时间<span style='font-size:20px;color:red;'>365</span>天，生成后不可修改", function(r) {
						if (r) {
							$('#s_editAutoActiveTimeLength').val('365');
							submit_single_form();
						}
					});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#single_card_dialog').dialog('close');
			}
		} ]
	});

	// 初始化批量生成框
	$('#batch_card_dialog').dialog({
		buttons : [ {
			text : '生成',
			handler : function() {
				submit_batch_form();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#batch_card_dialog').dialog('close');
			}
		} ]
	});
//导入excel
	$('#import_card_dialog').dialog({
		buttons : [ {
			text : '生成',
			handler : function() {
				var autoActiveTimeLength = $('#i_editAutoActiveTimeLength').val();
				if(""!=$.trim(autoActiveTimeLength)){
					var re = /^[1-9]\d*$/;
					var check=re.test(autoActiveTimeLength);
					if(!check){
						$.messager.alert(titleInfo, '有效期请填写正整数或置空！');
						return false;
					};
					if(autoActiveTimeLength>1095){
						$.messager.alert(titleInfo, '有效期不能大于1095！');
						return false;
					}
					$.messager.confirm('提示信息', "激活有效期<span style='font-size:20px;color:red;'>"+autoActiveTimeLength+"</span>天，生成后不可修改", function(r) {
						if (r) {
							submit_excel();
						}
					});
				}else{
					$.messager.confirm('提示信息', "激活有效期<span style='font-size:20px;color:red;'>365</span>天，生成后不可修改", function(r) {
						if (r) {
							$('#i_editAutoActiveTimeLength').val(365);
							submit_excel();
						}
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				$('#import_card_dialog').dialog('close');
			}
		} ]
	});
});

// 初始化批量操作的权限视角
function isBatch() {
	var isBatch = $('#hidIsBatch').val();
	if (0 == isBatch) {
		document.getElementById('batch_active').style.display = 'none';
		document.getElementById('batch_cancel').style.display = 'none';
		document.getElementById('batch_del').style.display = 'none';
		document.getElementById('addBatch').style.display = 'none';
	}
}

// 复制激活码
function copyStr() {
	var txt = document.getElementById("s_editActivationCode");
	txt.select(); // 选择对象
	document.execCommand("Copy"); // 执行浏览器复制命令
	$.messager.alert(titleInfo, "已复制好，可贴粘");
}

//复制上传报告
function copyReport(){
	var txt = document.getElementById("excel_report");
	txt.select(); // 选择对象
	document.execCommand("Copy"); // 执行浏览器复制命令
	$.messager.alert(titleInfo, "已复制好，可贴粘");
}

// 打开导入信息框
function openImportCard() {
	$('#import_btn').click(function() {
		initUploadExcel();
		$('#hiddenPath').val('');
		$('#import_activity').val('');
		$('#i_editAutoActiveTimeLength').val('');
		$("#ImportFileName").text("");
		$('#import_card_dialog').dialog('open');
	});
}

// 初始化登入用户可看到的内容
function initAuth() {
	var zoneId = $('#hidZoneId').val();
	var roleId = $('#hidRoleId').val();
	var branchId = $('#hidBranchId').val();
	var hallId = $('#hidHallId').val();
	if (3 == roleId) {
		$('#zone').val(zoneId);
		$("#branch").html("<option value="+branchId+">"+$('#hidBranchName').val()+"</option>"); 
		$("#hall").html("<option value="+hallId+">"+$('#hidHallName').val()+"</option>"); 
		document.getElementById('zone').disabled = 'disabled';
		document.getElementById('branch').disabled = 'disabled';
		document.getElementById('hall').disabled = 'disabled';
	} else if (2 == roleId) {
		$('#zone').val(zoneId);
		$("#branch").html("<option value="+branchId+">"+$('#hidBranchName').val()+"</option>"); 
		initHall(branchId);
		document.getElementById('zone').disabled = 'disabled';
		document.getElementById('branch').disabled = 'disabled';
	} else if (4 == roleId) {
		$('#zone').val(zoneId);
		initBranch(zoneId);
		initHall(branchId);
		document.getElementById('zone').disabled = 'disabled';
	}
}

// 初始化单个生成激活码
function addSingle() {
	$('#addSingle').click(function() {
		$('#s_editMac').val('');
		$('#s_editSn').val('');
		$('#s_editActivationCode').val('');
		$('#s_editAutoActiveTimeLength').val('');
		$('#s_editUserNum').val('');
		$('#s_editOrderNum').val('');
		$('#s_phone').val('');
		$('#single_card_dialog').dialog('open');
	});
}

// 初始化批量生成激活码
function addBatch() {
	$('#addBatch').click(function() {
		$('#b_count').val('');
		$('#d_editAutoActiveTimeLength').val('');
		$('#batch_card_dialog').dialog('open');
	});
}

// 初始化查询
function initQuery() {
	$('#query_card').click(function() {
		initCardInfo();
	});
}

// 初始化卡券信息
function initCardInfo() {
	parameter = {};
	var status = $('#status').val();
	if (status != -1) {
		parameter.status = status;
	}
	var activityId = $('#activity').val();
	if (activityId != -1) {
		parameter.activityId = activityId;
	}
	var zoneId = $('#zone').val();
	if(zoneId != -1){
		parameter.zoneId = zoneId;
	}
	var roleId = $('#hidRoleId').val();
	var branchId = $('#branch').val();
	var hallId = $('#hall').val();
	if (1 == roleId) {
		if (branchId != -1) {
			parameter.cardFromBranch = branchId;
		}
		if (hallId != -1) {
			parameter.cardFromHall = hallId;
		}
	}else if(4==roleId){
		if (branchId != -1) {
			parameter.cardFromBranch = branchId;
		}
		if (hallId != -1) {
			parameter.cardFromHall = hallId;
		}
	}else if (2 == roleId) {
		parameter.cardFromBranch = $('#hidBranchId').val();
		if (hallId != -1) {
			parameter.cardFromHall = hallId;
		}
	} else if (3 == roleId){
		parameter.cardFromBranch = $('#hidBranchId').val();
		parameter.cardFromHall = $('#hidHallId').val();
	}
	var activationTime = $('#activationTime').datebox("getValue");
	var activationStopTime = $('#activationStopTime').datebox("getValue");
	if(parseInt(activationTime.replace(/-/g,""))>parseInt(activationStopTime.replace(/-/g,""))){
		$.messager.alert(titleInfo, '卡激活开始时间不能超过结束时间！');
		return;
	}
	var createTime = $('#createTime').datebox("getValue");
	var createStopTime = $('#createStopTime').datebox("getValue");
	if(parseInt(createTime.replace(/-/g,""))>parseInt(createStopTime.replace(/-/g,""))){
		$.messager.alert(titleInfo, '卡生成开始时间不能超过结束时间！');
		return;
	}
	parameter.createTime = createTime;
	parameter.createStopTime = createStopTime;
	parameter.activationTime = activationTime;
	parameter.activationStopTime = activationStopTime;
	
	var uid = $('#uid').val();
	if (uid != "") {
		if (!/^[0-9]*$/.test(uid)) {
			parameter.uid = 99999999;
		} else {
			parameter.uid = uid;
		}
	}
	
	var cardPwd = $('#cardPwd').val();
	if (cardPwd != "") {
		parameter.activationCode = cardPwd;
	}
	var cardNumber = $('#cardNumber').val();
	if (cardNumber != "") {
		parameter.cardNumber = cardNumber;
	}
	var userNum = $('#userNum').val();
	if (userNum != "") {
		parameter.userNum = userNum;
	}
	var orderNum = $('#orderNum').val();
	if (orderNum != "") {
		parameter.orderNum = orderNum;
	}
	var cardNumber = $('#cardNumber').val();
	if (cardNumber != "") {
		parameter.cardNumber = cardNumber;
	}
	parameter.terminalMac = $('#mac').val();
	parameter.cardType ='vip';
	$('#card_table')
			.datagrid(
					{
						iconCls : 'icon-save',
						nowrap : true,
						autoRowHeight : false,
						striped : true,
						toolbar : '#card_toolbar',
						fit : true,
						fitColumns : true,
						collapsible : true,
						url : 'card/getList.json',
						checkOnSelect : false,
						queryParams : parameter,
						remoteSort : false,
						singleSelect : false,
						// idField : 'id',
						columns : [ [
								{
									field : 'ck',
									checkbox : true
								},
								{
									field : 'uid',
									title : '用户ID',
									// width : 40,
									hidden:true,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'cardOrderId',
									title : '订单号',
//									hidden : true,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'cardFrom',
									title : '卡生成员',
									// width : 40,
									formatter : function(value, row, index) {
										if (row.cardFromBranch == '-1' && row.cardFromHall == '-1') {
											return  row.creatorName;
										} else if (row.cardFromHall == '-1') {
											return row.branchName + '-' + row.creatorName;
										} else {
											return row.branchName + '-' + row.hallName + '-' + row.creatorName;
										}
									}
								},
								{
									field : 'activityName',
									title : '活动名',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'userNum',
									title : '用户号',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'orderNum',
									title : '业务单号',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'cardNumber',
									title : '卡号',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'activationCode',
									title : '激活码',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'id',
									title : '激活操作',
									// width : 40,
									formatter : function(value, row, index) {
										if(isBranchUser==0){
											return "X";
										}
										if (1 == row.status) {
											return '<a href="javascript:openActCard('
													+ row.id + ')">激活</a>';
										} else if (2 == row.status) {
											return '<a href="javascript:cancelCard('
													+ row.id + ')">注销</a>';
										}else if (5 == row.status) {
											return '已失效';
										} else {
											return '-';
										}
									}
								},
								{
									field : 'status',
									title : '卡状态',
									// width : 40,
									formatter : function(value, row, index) {
										if (1 == value) {
											return '未激活';
										} else if (2 == value) {
											return '已激活';
										} else if (3 == value) {
											return '激活失败';
										} else if (4 == value) {
											return '已注销';
										} else if (5 == value) {
											return '已失效';
										} else {
											return '错误状态';
										}
									}
								},
								{
									field : 'createTime',
									title : '卡生成时间',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'autoActiveTimeLength',
									title : '激活卡到期时间(天)',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'activationTime',
									title : '激活时间',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'effectTime',
									title : '生效时间',
									// width : 40,
									formatter : function(value, row, index) {
										return timeFormat(value);
									}
								},
								{
									field : 'invalidTime',
									title : '失效时间',
									// width : 40,
									formatter : function(value, row, index) {
										//当已经激活且生效时间为空时，说明此卡已经自动激活，而且没有实际使用
//										if(2==row.status&&(row.effectTime==null||""==row.effectTime)){
//											return null;
//										}else{
//											return timeFormat(value);
//										}
										return timeFormat(value);
									}
								},
								{
									field : 'cancelTime',
									title : '注销时间',
									// width : 40,
									formatter : function(value, row, index) {
										if (value != null) {
											var tt = new Date(value)
													.toLocaleString();
											return tt;
										} else {
											return value;
										}
									}
								},
								{
									field : 'terminalMac',
									title : 'MAC',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'terminalSn',
									title : 'SN',
									// width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'effectiveTimeLength',
									title : '有效时长',
									// width : 40,
									formatter : function(value, row, index) {
										return value / 86400;
									}
								},
								{
									field : 'del',
									title : '操作',
									// width : 40,
									formatter : function(value, row, index) {
										if (2 == row.status) {
											return '已激活不能删除';
										} else {
//											return '<a href="javascript:delCard('+ row.id + ')">删除</a>';
											//小黑屋需求，不可删除
											return '-';
										}
									}
								} ] ],
						pagination : true,
						fitColumns : false,
						rownumbers : true,
						onClickRow : function(rowIndex, row) {
						}
					});
	var horizflag = $("#horizflag").val();
	if(horizflag=='1'){
		$('#card_table').datagrid('showColumn','cardNumber');
	}else{
		$('#card_table').datagrid('hideColumn','cardNumber');
	}
}

// 单个添加激活码
function submit_single_form() {
	var activityId = $('#s_editActivity').val();
	if (activityId == "" || activityId == null) {
		$.messager.alert(titleInfo, '活动不能为空！');
		return false;
	}
	var phone =$('#s_phone').val();
	if(phone!=''){
		if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){ 
	        alert("手机号码有误，请重填");  
	        return false; 
	    } 
	}
	
	var card = {
		"activityId" : activityId,
		"activityName" : $("#s_editActivity  option:selected").text(),
		"terminalMac" : $('#s_editMac').val(),
		"terminalSn" : $('#s_editSn').val(),
		"activationCode" : $('#s_editActivationCode').val(),
		"creatorName" : $('#hidUserName').val(),
		"cardFromHall" : $('#hidHallId').val(),
		"hallName" : $('#hidHallName').val(),
		"branchName" : $('#hidBranchName').val(),
		"cardFromBranch" : $('#hidBranchId').val(),
		"cityId" : $('#hidCityId').val(),
		"autoActiveTimeLength" : $('#s_editAutoActiveTimeLength').val(),
		"userNum" : $('#s_editUserNum').val(),
		"phone" :phone,
		"orderNum" : $('#s_editOrderNum').val()
	};
	$.post("card/add.json", card, function(data) {
		if (data.code == 1) {
			$('#s_editActivationCode').val(data.msg);
			$.messager.show({
				title : titleInfo,
				msg : '已生成！',
				timeout : timeoutValue,
				showType : 'slide'
			});
			$('#single_card_dialog').dialog('close');//关闭窗口
			$('#card_table').datagrid('reload', parameter);
		} else if(data.code == 4){
			$('#s_editActivationCode').val(data.msg);
			$.messager.alert(titleInfo, "已生成，系统认定该卡是续费卡！" );
			$('#single_card_dialog').dialog('close');//关闭窗口
			$('#card_table').datagrid('reload', parameter);
		}else {
			$.messager.alert(titleInfo, data.msg );
		}
	}, "json");

}

// 批量添加激活码
function submit_batch_form() {
	var activityId = $('#b_editActivity').val();
	if (activityId == "" || activityId == null) {
		$.messager.alert(titleInfo, '活动不能为空！');
		return false;
	}
	var d_editAutoActiveTimeLength = $('#d_editAutoActiveTimeLength').val();
	if(""!=$.trim(d_editAutoActiveTimeLength)){
		var re = /^[1-9]\d*$/;//大于0的正整数
		var check=re.test(d_editAutoActiveTimeLength);
		if(!check){
			$.messager.alert(titleInfo, '有效期请填写正整数！');
			return false;
		};
		if(d_editAutoActiveTimeLength>1095){
			$.messager.alert(titleInfo, '有效期不能大于1095！');
			return false;
		}
	}else{
		$.messager.alert(titleInfo, '有效期请填写正整数！');
		return false;
	}
	var card = {
		"activityId" : activityId,
		"activityName" : $("#b_editActivity  option:selected").text(),
		"creatorName" : $('#hidUserName').val(),
		"cardFromHall" : $('#hidHallId').val(),
		"hallName" : $('#hidHallName').val(),
		"branchName" : $('#hidBranchName').val(),
		"cardFromBranch" : $('#hidBranchId').val(),
		"terminalMac" : "",
		"terminalSn" : "",
		"autoActiveTimeLength" : d_editAutoActiveTimeLength,
		"cityId" : $('#hidCityId').val(),
		"cardType" : "vip"
	};
	// ^\+?[1-9][0-9]*$ ^[1-9]\d*$
	var reg = new RegExp("^\\+?[1-9][0-9]*$");
	var count = $('#b_count').val();
	if (!reg.test(count)) {
		$.messager.alert(titleInfo, '生成数量请输入非零数字！');
		return;
	}
	var limit = 5000;
	if(count>limit){
		$.messager.alert(titleInfo, '批量一次不能超过'+limit+'张卡！');
		return;
	}
	var suc = 0;// 成功生成的数量
	
		$.ajax({
			url : "card/addBatch.json?batchSize="+count,
			async : false, // 同步
			type : "POST",
			dataType : "json",
			data : card,
			success : function(data) {
				$.messager.alert(titleInfo, '计划生成' + count + "张卡，生成结果："+data.msg);
			},
		});
	$('#card_table').datagrid('reload', parameter);
	$('#batch_card_dialog').dialog('close');
	
}

// 删除卡券，软删除
function delCard(id) {
	$.messager.confirm('Warning', '您确定要删除?', function(r) {
		if (r) {
			var card = {
				"id" : id,
				"delStatus" : 1
			};
			$.post("card/update.json", card, function(data) {
				if (1 == data.code) {
					$.messager.show({
						title : titleInfo,
						msg : '删除成功！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#card_table').datagrid('reload', parameter);
				} else {
					$.messager.alert(titleInfo, '删除失败！');
				}
			}, "json");
		}
	});
}

// 初始化营业厅关联
function initHall(branchId) {
	if (branchId == "-1" || branchId == "") {
		$("#hall").html("<option value='-1'>全部</option>");
	} else {
		var hall = {
			"branchId" : branchId
		};
		$.post("hall/getHallList.json", hall, function(data) {
			$("#hall").html("<option value='-1'>全部</option>");
			$.each(data, function(dataIndex, obj) {
				$("#hall").append(
						'<option value=' + obj.id + '>' + obj.hallName
								+ '</option>');
			});
		}, "json");
	}
}

// #######################################导入导出excel#############################

// 导入excel
function initUploadExcel() {
	// $('#import_card').click(function(data){
	var import_button = $("#import_card");
	new AjaxUpload(import_button, {
		action : 'excel/uploadExcel.html',
		autoSubmit : true,
		name : 'file',// 文件对象名称（不是文件名）
		data : {},
		onChange : function(file, extension) {
			var d = /\.[^\.]+$/.exec(file); // 文件后缀
			if (d != ".xls" && d != ".xlsx") {
				$.messager.alert(titleInfo, '文件格式错误，请上传.xls或.xlsx格式！');
				return false;
			} else {
				$("#ImportFileName").text(file);
			}
		},
		onSubmit : function(file, extension) {// 在提交的时候触发
		},
		onComplete : function(file, response) {
			$('#hiddenPath').val(response);
			$.messager.show({
				title : titleInfo,
				msg : '文件已选定！',
				timeout : timeoutValue,
				showType : 'slide'
			});
		}
	});
	// });
};

function submit_excel() {
	var activityId = $("#import_activity").val();
	if(activityId==""||activityId==null){
		$.messager.alert(titleInfo, '请先选择活动！');
		return;
	}
	var tmpData = {
		"activityId" : activityId,
		"activityName" : $("#import_activity  option:selected").text(),
		"excelPath" : $('#hiddenPath').val(),
		"cardType" : 'vip',
		"autoActiveTimeLength" : $('#i_editAutoActiveTimeLength').val()
	};
	var fileName = $("#ImportFileName").text();
	if(fileName==""||fileName==null){
		$.messager.alert(titleInfo, '请先选择文件！');
		return;
	}
	loadDiv('正在上传，请稍候...');
	$.ajax({
		url : "excel/saveExcel.json",
		async : true, // 同步
		type : "POST",
		dataType : "json",
		data : tmpData,
		success : function(data) {
			if (1 == data.code) {
				displayLoad();
				$('#import_card_dialog').dialog('close');
				$('#excel_report').val("");
				$('#excel_report').val(data.msg);
				$('#excel_report_dialog').dialog('open');
//				$.messager.alert(titleInfo, data.msg);
//				$.messager.show({
//					title : titleInfo,
//					msg : '保存成功！',
//					timeout : timeoutValue,
//					showType : 'slide'
//				});
				$('#card_table').datagrid('reload', parameter);
			} else if(5==data.code){
				displayLoad();
				$.messager.alert(titleInfo, '文件内容格式不符合规范！');
			}else{
				displayLoad();
				$.messager.alert(titleInfo, '保存失败！');
			}
		},
		complete:  function(){
			displayLoad(); 
        }
	});
}

// 导出excel
var bol = false;
function initDownLoad() {
	$('#export_card').click(function(data) {
		var myDate = new Date();
//		var excelName = "cardInfo_" + myDate.getTime();
		var card = {};
		card.excelName="cardInfo_" + myDate.getTime();
		var status = $('#status').val();
		if (status != -1) {
			card.status = status;
		}
		var activityId = $('#activity').val();
		if (activityId != -1) {
			card.activityId = activityId;
		}
		var zoneId = $('#zone').val();
		if(zoneId != -1){
			card.zoneId = zoneId;
		}
		var roleId = $('#hidRoleId').val();
		var branchId = $('#branch').val();
		var hallId = $('#hall').val();
		if(zoneId!=-1&&branchId==-1){
			$.messager.alert(titleInfo, '选择战区后,请选择对应分公司进行导出!');
			return;
		}
		if (1 == roleId) {
			if (branchId != -1) {
				card.cardFromBranch = branchId;
			}
			if (hallId != -1) {
				card.cardFromHall = hallId;
			}
		}else if(4==roleId){
			if (branchId != -1) {
				card.cardFromBranch = branchId;
			}
			if (hallId != -1) {
				card.cardFromHall = hallId;
			}
		}else if (2 == roleId) {
			card.cardFromBranch = $('#hidBranchId').val();
			if (hallId != -1) {
				card.cardFromHall = hallId;
			}
		} else if (3 == roleId){
			card.cardFromBranch = $('#hidBranchId').val();
			card.cardFromHall = $('#hidHallId').val();
		}
		
		var activationTime = $('#activationTime').datebox("getValue");
		var activationStopTime = $('#activationStopTime').datebox("getValue");
		var createTime = $('#createTime').datebox("getValue");
		var createStopTime = $('#createStopTime').datebox("getValue");
		if(createTime==''||createStopTime==''){
			$.messager.alert(titleInfo, '因数据量较大，导出excel，卡生成时间不能为空！');
			return;
		}
		
		if(parseInt(activationTime.replace(/-/g,""))>parseInt(activationStopTime.replace(/-/g,""))){
			$.messager.alert(titleInfo, '卡激活开始时间不能超过结束时间！');
			return;
		}
		if(parseInt(createTime.replace(/-/g,""))>parseInt(createStopTime.replace(/-/g,""))){
			$.messager.alert(titleInfo, '卡生成开始时间不能超过结束时间！');
			return;
		}
		card.createTime = createTime;
		card.createStopTime = createStopTime;
		card.activationTime = activationTime;
		card.activationStopTime = activationStopTime;
		
//		var startTime = $('#startTime').datebox("getValue");
//		if (startTime != "") {
//			card.createTime = startTime;
//		}
		var uid = $('#uid').val();
		if (uid != "") {
			if (!/^[0-9]*$/.test(uid)) {
				card.uid = 99999999;
			} else {
				card.uid = uid;
			}
		}
//		var stopTime = $('#stopTime').datebox("getValue");
//		if (stopTime != "") {
//			card.activationTime = stopTime;
//		}
		var cardPwd = $('#cardPwd').val();
		if (cardPwd != "") {
			card.activationCode = cardPwd;
		}
		var userNum = $('#userNum').val();
		if (userNum != "") {
			card.userNum = userNum;
		}
		var orderNum = $('#orderNum').val();
		if (orderNum != "") {
			card.orderNum = orderNum;
		}
		card.terminalMac = $('#mac').val();
		card.cardType ='vip';
		$.ajax({
			url : "excel/downloadExcel.json",
			async : true, // 
			type : "POST",
			dataType : "json",
			data : card,
			success : function(data) {
				if (1 == data.code) {
					var excelUrl = data.msg + card.excelName + ".xls";
//					sleep(1000);
					window.location.href = excelUrl;
				} else {
					$.messager.alert(titleInfo, '下载失败！');
				}
			}
		});
	});
}

// ################################卡券激活##############################

// 打开激活框
function openActCard(id) {
	clearDialog();
	initDialog(id);
}

function clearDialog() {
	$('#a_id').val('');
	$('#a_userInfo').val('');
	$('#a_actCode').val('');
	$('#a_mac').val('');
	$('#a_sn').val('');
}

function initDialog(id) {
	// $('#card_table').datagrid('unselectAll');//取消之前选定的行
	// $('#card_table').datagrid('selectRecord',id);
	// var row = $('#card_table').datagrid('getSelected');
	$('#a_mac').removeAttr("readonly");
	$('#a_sn').removeAttr("readonly");
	$.ajax({
		url : "card/getCardById.json",
		async : false, // 同步
		type : "POST",
		dataType : "json",
		data : {
			"id" : id
		},
		success : function(data) {
			$('#a_id').val(id);
			$('#a_userInfo').val(data.card.uid);
			$('#a_actCode').val(data.card.activationCode);
			if(""!=data.card.terminalMac&&""!=data.card.terminalSn){
				$('#a_mac').attr("readonly","readonly");
				$('#a_sn').attr("readonly","readonly");
			}
			$('#a_mac').val(data.card.terminalMac);
			$('#a_sn').val(data.card.terminalSn);
			$('#activition_card_dialog').dialog('open');
		},
	});

}

// 单个激活
function actCard() {
	var cardPwd = $('#a_actCode').val();
	var mac = $('#a_mac').val();
	var sn = $('#a_sn').val();
	if (mac == null || sn == null || mac == '' || sn == '') {
		$.messager.alert(titleInfo, '没有MAC或SN！');
		return;
	}
	// 生成签名
	var partnerKey = $('#hidPartnerKey').val();
	var _params = "cardPwd=" + cardPwd + "&mac=" + mac + "&sn=" + sn;
	var tmp = _params + "&partnerKey=" + partnerKey;
	var sign = md5(tmp);
	_params = _params + "&sign=" + sign;
	$.messager.confirm('Warning', '您确定要激活?', function(r) {
		if (r) {
			loadDiv('正在激活，请稍候...');
			$.ajax({
				url : "card/activation.json",
				async : false, // 同步
				type : "POST",
				dataType : "json",
				data : _params,
				success : function(data) {
					if (data.code == "000") {
						displayLoad();
						$('#activition_card_dialog').dialog('close');
						$.messager.show({
							title : titleInfo,
							msg : '激活成功！',
							timeout : timeoutValue,
							showType : 'slide'
						});
						$('#card_table').datagrid('reload', parameter);
					} else {
						displayLoad();
						$.messager.alert(titleInfo, '激活失败:' + data.desc);
					}
				}
			});
			displayLoad();
		}
	});
}

// 初始化批量激活
function initBatchActive() {
	$('#batch_active').click(
			function() {
				var cardArray = $('#card_table').datagrid('getChecked');
				var length = cardArray.length;
				if (0 == length) {
					$.messager.alert(titleInfo, '您没有选中任何记录！');
				} else {
					$.messager.confirm('Warning', '您是否确认激活当前所选的' + length+ '张卡?', function(r) {
						if (r) {
							loadDiv('正在激活，请稍候...');
							var noMacOrSn = 0; // 没有mac sn的卡的数量
							var fail = 0;// 激活失败
							var suc = 0; // 激活成功
							for (var i = 0; i < length; i++) {
								var row = cardArray[i];
								var cardPwd = row.activationCode;
								var mac = row.terminalMac;
								var sn = row.terminalSn;
								if (mac == null || sn == null) {
									noMacOrSn += 1;
									continue;
								}
								// 生成签名
								var partnerKey = $('#hidPartnerKey').val();
								var _params = "cardPwd=" + cardPwd+ "&mac=" + mac + "&sn=" + sn;
								var tmp = _params + "&partnerKey="+ partnerKey;
								var sign = md5(tmp);
								_params = _params + "&sign=" + sign;
								$.ajax({
									url : "card/activation.json",
									async : false, // 同步
									type : "POST",
									dataType : "json",
									data : _params,
									success : function(data) {
										if (data.code == "000") {
											suc += 1;
										} else {
											fail += 1;
										}
									}
								});
							};
							displayLoad();
							$('#card_table').datagrid('reload',parameter);
							$.messager.alert(titleInfo, "本次计划激活"+ length + "张卡，" + suc + "张激活成功，"+ fail + "张激活失败，" + noMacOrSn+ "张没有mac&sn");}
					});
				}
			});
}

// ################################卡券注销##############################
// 单个注销卡券
function cancelCard(id) {
	// $('#card_table').datagrid('unselectAll');
	// $('#card_table').datagrid('selectRecord',id);
	// var row = $('#card_table').datagrid('getSelected');
	$.ajax({
		url : "card/getCardById.json",
		async : false, // 同步
		type : "POST",
		dataType : "json",
		data : {
			"id" : id
		},
		success : function(data) {
			var userId = data.card.uid;
			var orderId = data.card.cardOrderId;
			if (userId == null || orderId == null) {
				$.messager.alert(titleInfo, '没有用户ID或订单号！');
				return;
			}
			// 生成签名
			var partnerKey = $('#hidPartnerKey').val();
			var _params = "orderId=" + orderId + "&userId=" + userId;
			var tmp = _params + "&partnerKey=" + partnerKey;
			var sign = md5(tmp);
			_params = _params + "&sign=" + sign;
			$.messager.confirm('Warning', '您确定要注销?', function(r) {
				if (r) {
					$.ajax({
						url : "card/cancel.json",
						async : false, // 同步
						type : "POST",
						dataType : "json",
						data : _params,
						success : function(data) {
							if (data.code == "000") {
								$.messager.show({
									title : titleInfo,
									msg : '已注销！',
									timeout : timeoutValue,
									showType : 'slide'
								});
								$('#card_table').datagrid('reload', parameter);
							} else {
								$.messager.alert(titleInfo, '注销失败:' + data.desc);
							}
						}
					});
				}
			});
		},
	});
}

// 初始化批量删除
function initBatchDel() {
	$('#batch_del').click(
			function() {
				var cardArray = $('#card_table').datagrid('getChecked');
				var length = cardArray.length;
				if (0 == length) {
					$.messager.alert(titleInfo, '您没有选中任何记录！');
				} else {
					$.messager.confirm('Warning', '确认删除当前选中的' + length + '张卡?',
							function(r) {
								if (r) {
									var actived = 0; // 已经激活不能删除的卡数量
									var fail = 0;// 删除出问题的数量
									var suc = 0; // 删除成功的数量
									for (var i = 0; i < length; i++) {
										var row = cardArray[i];
										var id = row.id;
										var status = row.status;
										if (2 == status) {
											actived += 1;
											// $.messager.alert(titleInfo,
											// row.activationCode+'已经激活不能删除');
											continue;
										}
										$.ajax({
											url : "card/delCard.json",
											async : false, // 
											type : "POST",
											dataType : "json",
											data : {
												"id" : id
											},
											success : function(data) {
												if (1 == data.code) {
													suc += 1;
												} else {
													fail += 1;
												}
											}
										});
									}
									var totalFail = fail + actived;// 删除失败总数
									$('#card_table').datagrid('reload',parameter);
									var result = "";
									if(0==totalFail){
										result="计划删除" + length+ "张卡，" + suc + "张删除成功";
									} else if(0==actived){
										result="计划删除" + length+ "张卡，" + suc + "张删除成功，"+ totalFail + "张删除失败";
									}else{
										result ="计划删除" + length+ "张卡，" + suc + "张删除成功，"+ totalFail + "张删除失败（包含" + actived+ "张因已经激活不能删除）";
									}
									$.messager.alert(titleInfo, result);
								};
							});
				}
			});
}

// 初始化批量注销
function initBatchCancel() {
	$('#batch_cancel').click(function() {
						var cardArray = $('#card_table').datagrid('getChecked');
						var length = cardArray.length;
						if (0 == length) {
							$.messager.alert(titleInfo, '您没有选中任何记录！');
						} else {
							$.messager.confirm('Warning','您是否确认注销当前所选的' + length + '张卡?',
											function(r) {
												if (r) {
													for (var i = 0; i < length; i++) {
														var row = cardArray[i];
														var userId = row.uid;
														var orderId = row.cardOrderId;
														if (userId == null|| orderId == null) {
															$.messager.alert(titleInfo,row.activationCode+ '没有用户ID或订单号！');
															continue;
														}
														// 生成签名
														var partnerKey = $('#hidPartnerKey').val();
														var _params = "orderId="+ orderId+ "&userId="+ userId;
														var tmp = _params+ "&partnerKey="+ partnerKey;
														var sign = md5(tmp);
														_params = _params+ "&sign="+ sign;
														$.ajax({
																	url : "card/cancel.json",
																	async : false, // 同步
																	type : "POST",
																	dataType : "json",
																	data : _params,
																	success : function(data) {
																		if (data.code == "000") {
																			$.messager.show({title : titleInfo,msg : '已注销！',timeout : timeoutValue,showType : 'slide'});
																			$( '#card_table').datagrid('reload',parameter);
																		} else {
																			$.messager.alert( titleInfo, row.activationCode + '注销失败:' + data.desc);
																		}
																	}
																});
													};
												}
											});
						}
					});
}

// 根据战区初始化分公司信息
function initBranch(zoneId) {
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
	initHall();
}

function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while (true) {
	now = new Date();
	if (now.getTime() > exitTime)
	return;
	}
}

function timeFormat(str){
	if(str!=null){
		var _value = new Date(str);
		var year = _value.getFullYear();
		var month = _value.getMonth()+1;
		var day = _value.getDate();
		if(month <10){
			month = "0"+month;
		}
		if(day <10){
			day = "0"+day;
		}
		return year+"-"+month+"-"+day;
	}
	return null;
}