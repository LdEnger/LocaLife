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
	$("#startTime").datebox();
	$("#stopTime").datebox();
	initCountInfo();
	queryCount();
});

function queryCount(){
	$('#countByActivationTime').click(function(){
		parameter.queryMethod=1;
		initCountInfo();
	});
	$('#countByEffectTime').click(function(){
		parameter.queryMethod=2;
		initCountInfo();
	});
	$('#countByCreateTime').click(function(){
		parameter.queryMethod=3;
		initCountInfo();
	});
}

function initCountInfo() {
	var startTime = $('#startTime').datebox("getValue");
	if (startTime != "") {
		parameter.createTime = startTime;
	}
	var stopTime = $('#stopTime').datebox("getValue");
	if (stopTime != "") {
		parameter.activationTime = stopTime;
	}
	parameter.activityId =$('#activity').val();
	parameter.delStatus =$('#duration').val();
	$('#count_table').datagrid({
		iconCls : 'icon-save',
		nowrap : true,
		autoRowHeight : false,
		striped : true,
		toolbar : '#count_toolbar',
		fit : true,
		fitColumns : true,
		collapsible : true,
		url : 'count/getList.json',
		checkOnSelect : false,
		queryParams : parameter,
		remoteSort : false,
//		singleSelect : false,
		// idField : 'id',
		columns : [ [ 
		{
			field : 'branchId',
			title : '分公司ID',
			hidden:true,
			formatter : function(value, row, index) {
				return value;
			}
		},
		{
			field : 'branchName',
			title : '分公司',
			formatter : function(value, row, index) {
				if(-1==row.branchId){
					return '集团';						
				}
				return value;
			}
		},
		{
			field : 'total',
			title : '开通总数',
			formatter : function(value, row, index) {
				return value;
			}
		}
		] ],
		pagination : true,
		fitColumns : false,
		rownumbers : true,
		onClickRow : function(rowIndex, row) {
		}
	});
}

function initDownLoad() {
	//	alert(1);
		var startTime = $('#startTime').datebox("getValue");
		if (startTime != "") {
			parameter.createTime = startTime;
		}
		var stopTime = $('#stopTime').datebox("getValue");
		if (stopTime != "") {
			parameter.activationTime = stopTime;
		}
		parameter.activityId =$('#activity').val();
		parameter.delStatus =$('#duration').val();
		if(startTime==''||stopTime==''){
			$.messager.alert(titleInfo, '因数据量较大，导出excel，时间不能为空！');
			return;
		}
		
		if(parameter.queryMethod==null){
			parameter.queryMethod=3;
		}
		$.ajax({
			url : "excel/countExcel.json",
			async : true, // 
			type : "POST",
			dataType : "json",
			data : parameter,
			success : function(data) {
				if (1 == data.code) {
					var excelUrl = data.msg;
//					sleep(1000);
					window.location.href = excelUrl;
				} else {
					$.messager.alert(titleInfo, '下载失败！');
				}
			}
		});
}