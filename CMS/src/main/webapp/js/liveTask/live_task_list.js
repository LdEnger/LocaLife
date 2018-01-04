var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
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
$(function() {
	initQuery();
	initAddTask();
	initTaskInfo();
	batchImportLive();
});

//初始化营业厅关联
function initHall(branchId) {
	var hall = {
		"branchId" : branchId
	};
	$.post("hall/getHallList.json", hall, function(data) {
		$("#hall").html("<option value='-1'>全部</option>");
		$.each(data, function(dataIndex, obj) {
			$("#hall").append('<option value=' + obj.id + '>' + obj.hallName + '</option>');
		});
	}, "json");
}

function initAddTask() {
	$('#add_live_task').click(function() {
		reset();
		$('#batch_branchName').combobox({
			url:'live/getBranchList.json',
			valueField:'id',
			textField:'branchName',
			onChange:function (id) {
				$('#batch_hallName').combobox({
					url:'live/getHallList.json?branchId='+id,
					valueField:'id',
					textField:'hallName',
					editable : false
				});
			},
			onLoadSuccess:function () {
				var batchBranchNameList = $('#batch_branchName').combobox('getData');
				if(batchBranchNameList!=null && batchBranchNameList.length>0) {
					$("#batch_branchName").combobox('select',batchBranchNameList[0].id);
				}
			},
			editable : false
		});
		$('#batch_hallName').combobox({
			url:'live/getHallList.json',
			valueField:'id',
			textField:'hallName',
			onLoadSuccess:function () {
				var batchHallNameList = $('#batch_hallName').combobox('getData');
				if(batchHallNameList!=null && batchHallNameList.length>0) {
					$("#batch_hallName").combobox('select',batchHallNameList[0].id);
				}
			},
			editable : false
		});
		$('#batch_productId').combobox({
			url : 'live/getProductList.json',
			valueField : 'productId',
			textField : 'productName',
			editable : false
		});
		$('#task_dialog').dialog({
			buttons : [ {
				text : '确定',
				handler : function() {
					submit_live_task();
				}
			},{
				text : '取消',
				handler : function() {
					$('#task_dialog').dialog('close');
				}
			} ]
		});
		$('#task_dialog').dialog('open');
	});
}

function reset(){
	$('#batch_taskName').val('');
	$('#batch_taskDesc').val('');
	$('#batch_mac').val('');
	$('#batch_sn').val('');
	$('#batch_endTime').val('');
	$('#batch_year').val(0);
	$('#batch_month').val(0);
	$('#batch_day').val(0);
	$('#ImportFileName').empty();
//	$('#hiddenPath').val('');
}
// 初始化查询
function initQuery(){
	$('#query_live_task').click(function(){
		initTaskInfo();
	});
}

function initTaskInfo() {
	parameter = {
		taskName : $("#queryTaskName").val(),
		productName : $("#queryProductName").val(),
	};
	var openTime = $('#queryOpenTime').datebox("getValue");
	if (openTime != "") {
		parameter.openTime = openTime;
	}
	var closeTime = $('#queryCloseTime').datebox("getValue");
	if (closeTime != "") {
		parameter.closeTime = closeTime;
	}
	// 非集团用户查询不能选择区域或者分公司
	var roleId = $('#roleId').val();
	var branchId;
	var hallId;
	if (roleId != 1) {
		branchId = $('#branchId').val();
		hallId = $('#hallId').val();
	} else {
		branchId = $('#branch').val();
		hallId = $('#hall').val();
	}
	if (branchId != -1) {
		parameter.branchId = branchId;
	}
	if (hallId != -1) {
		parameter.hallId = hallId;
	}
	$('#task_table').datagrid(
					{
						iconCls : 'icon-save',
						nowrap : true,
						autoRowHeight : false,
						striped : true,
						toolbar : '#task_toolbar',
						fit : true,
						fitColumns : true,
						collapsible : true,
						url : 'liveTask/getLiveTaskList.json',
						checkOnSelect : false,
						queryParams : parameter,
						remoteSort : false,
						singleSelect : false,
						idField : 'id',
						columns : [ [
								{
									field : 'taskId',
									title : '任务ID',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'taskName',
									title : '任务名称',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'taskDesc',
									title : '任务描述',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'productName',
									title : '产品包名称',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'taskType',
									title : '订单类型',
									formatter : function(value, row, index) {
										if (value == 1) {
											return "开通"
										} else if (value == 2) {
											return "续费"
										} else if (value == 3) {
											return "退订"
										} else {
											return "未知"
										}
									}
								},
								{
									field : 'branchName',
									title : '分公司名称',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'hallName',
									title : '营业厅名称',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'executeResult',
									title : '执行结果',
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'openName',
									title : '开通人员',
									formatter : function(value, row, index) {
										return value;
									}
								},
//								{
//									field : 'openTime',
//									title : '生效时间',
//									formatter : function(value, row, index) {
//										return value;
//									}
//								},
//								{
//									field : 'closeTime',
//									title : '失效时间',
//									formatter : function(value, row, index) {
//										return value;
//									}
//								},
								{
									field : 'submitTime',
									title : '提交时间',
									formatter : function(value, row, index) {
										return value;
									}
								}
								,
								{
									field : 'id',
									title : '操作',
									formatter : function(value, row, index) {
										var executeResult = row.executeResult;
										if(executeResult.indexOf("失败：0")<0){
											return '<a href="javascript:openTaskDetail(\''+ row.taskId + '\')">失败明细</a>';
										}else{
											return '/';
										}
									}
								}
								] ],
						pagination : true,
						fitColumns:false,
						rownumbers : true,
						onClickRow : function(rowIndex,row) {
						}
			});
}

function batchImportLive() {
	var import_button = $("#import_live");
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
			$.messager.show({title : titleInfo,	msg : '文件已选定！',timeout : timeoutValue, showType : 'slide'});
		}
	});
}
//批量开通直播
function submit_live_task() {
	var taskName = $('#batch_taskName').val();
	if(taskName == null || taskName == ''){
		$.messager.alert(titleInfo, '任务名称不能为空');
		return;
	}
	var roleId = $('#roleId').val();
	var branchId = $('#batch_branchName').combobox('getValue');
	var branchName = $('#batch_branchName').combobox('getText');
	var hallId = $('#batch_hallName').combobox('getValue');
	var hallName = $('#batch_hallName').combobox('getText');
	if(branchId == null || branchId == '' || branchName == null || branchName == ''){
		$.messager.alert(titleInfo, '请选择分公司');
		return;
	}
	var productId = $('#batch_productId').combobox('getValue');
	var productName = $('#batch_productId').combobox('getText');
	if(productId == null || productId == '' || productName == null || productName ==''){
		$.messager.alert(titleInfo, '请选择产品包');
		return;
	}
	var endTime = $('#batch_endTime').val();
	if(endTime == null || endTime == ''){
		$.messager.alert(titleInfo, '结束日期不能为空');
		return;
	}
	var year = $('#batch_year').val();
	var month = $('#batch_month').val();
	var day = $('#batch_day').val();
	if((year == null || year == 0) && (month == null || month == 0) && (day == null || day == 0) ){
		$.messager.alert(titleInfo, '开通时长不能为零');
		return;
	}
	var excelPath = $('#hiddenPath').val();
	if (excelPath == "" || excelPath == null) {
		$.messager.alert(titleInfo, '请先上传附件！');
		return;
	}
	var live = {
		taskName : taskName,
		taskDesc : $('#batch_taskDesc').val(),
		branchId :branchId,
		branchName : branchName,
		hallId : hallId,
		hallName : hallName,
		productId : productId,
		productName : productName,
		closeTime : endTime,
		chargingDurationYear : year,
		chargingDurationMonth : month,
		chargingDurationDay : day,
		excelPath : excelPath
	};
//	 var defer = $.Deferred();
	loadDiv('正在开通，请稍后...');
	$.ajax({
		url : "liveTask/batchOpenLiveTask.json",
		async : true, // 
		type : "POST",
		timeout : 6000000 ,
		dataType : "json",
		data:live,
		success : function(data) {
			if (data.code == "000") {
				$('#task_dialog').dialog('close');
				$.messager.show({ title : titleInfo,msg : '执行完成:' + data.desc,timeout : timeoutValue,showType : 'slide'});
				displayLoad();
				$('#task_table').datagrid('reload', parameter);
			} else {
				displayLoad();
				$.messager.alert(titleInfo, data.desc);
				$('#task_table').datagrid('reload', parameter);
			}
		},
		complete:  function(){
			$('#task_dialog').dialog('close');
			displayLoad(); 
			$('#task_table').datagrid('reload', parameter);
        }
	});
}
function getYear(type){
	var year = $("#"+type+"year").val();
	var month = $("#"+type+"month").val();
	var day = $("#"+type+"day").val();
	getEndDate(year,month,day,type)
}
function getMonth(type){
	var year = $("#"+type+"year").val();
	var month = $("#"+type+"month").val();
	var day = $("#"+type+"day").val();
	getEndDate(year,month,day,type)
}
function getDay(type){
	var year = $("#"+type+"year").val();
	var month = $("#"+type+"month").val();
	var day = $("#"+type+"day").val();
	getEndDate(year,month,day,type)
}
//通过年月日推算结束日期
function getEndDate(year,month,day,id){
	$.ajax({
		url : "live/computeEndDate.json",
		async : true, 
		type : "POST",
		dataType : "json",
		data:{"year":year,"month":month,"day":day},
		success : function(data) {
			if (000== data.code) {
				var date = data.desc;
				$('#'+id+'endTime').val(date);
			} else {
				$.messager.alert(titleInfo, data.desc);
			}
		}
	});
}
//计算年月日
function batchComputeTimeDifference(){
	var endTime = $('#batch_endTime').val();
	$.ajax({
		url : "live/computeTimeDifference.json",
		async : true, 
		type : "POST",
		dataType : "json",
		data:{"endTime":endTime},
		success : function(data) {
			if (000== data.code) {
				var date = data.desc;
				$('#batch_year').val(date.substring(0, date.indexOf("年")));
				$('#batch_month').val(date.substring(date.indexOf("年")+1,date.indexOf("月") ));
				$('#batch_day').val(date.substring(date.indexOf("月")+1, date.indexOf("天")));
			} else {
				$.messager.alert(titleInfo, data.desc);
			}
		}
	});
}
function openTaskDetail(taskId){
	$('#task_detail_dialog').dialog({
		buttons : [ {
			text : '关闭',
			handler : function() {
				$('#task_detail_dialog').dialog('close');
			}
		} ]
	});
	$('#task_detail_dialog').dialog('open');
	getTaskDetail(taskId);
}
function getTaskDetail(taskId){
	var taskDetail = {
			taskId : taskId
	};
	$('#task_detail_table').datagrid(
			{
				iconCls : 'icon-save',
				nowrap : true,
				autoRowHeight : false,
				striped : true,
				fit : true,
				fitColumns : true,
				collapsible : true,
				url : 'liveTask/getLiveTaskDetailList.json',
				checkOnSelect : false,
				queryParams : taskDetail,
				remoteSort : false,
				singleSelect : false,
				idField : 'id',
				columns : [ [
						{
							field : 'mac',
							title : 'MAC',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'sn',
							title : 'SN',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'openResultName',
							title : '失败原因',
							formatter : function(value, row, index) {
								return value;
							}
						}
						] ],
				pagination : true,
				fitColumns:false,
				rownumbers : true,
				onClickRow : function(rowIndex,row) {
				}
	});
	
}
