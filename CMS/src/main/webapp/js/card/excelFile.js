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
	$("#q_ctime").datebox();
	initSmsRecordInfo();
	queryActivity();
});


// 查询记录
function queryActivity() {
	$('#query_smsR').click(function() {
		initSmsRecordInfo();
	});
}


// 初始化活动信息
function initSmsRecordInfo() {
	parameter = {};
	var startTime = $('#q_ctime').datebox("getValue");
	parameter.msg =startTime;
	$('#smsRe_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#smsRe_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'boss/getList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'fileName',
								title : '文件名',
								width : 60
							},
			 			{
							field : 'path',
							title : '下载地址',
							width : 10,
							formatter:function(value,row,index){
								var str ="http://cms.activity.pthv.gitv.tv/ftp/"+value;
								return "<a href='"+str+"' target='_blank'>下载</a>";
							}
						},
			 			{
							field : 'type',
							title : '类型',
							width : 20,
							formatter:function(value,row,index){
								if(value==1){
									return "新boss产品类";
								}else if(value==2){
									return "新boss开通类";
								}else if(value==3){
									return "boss产品类";
								}else if(value==4){
									return "老boss开通类";
								}else{
									return "未知类型";
								}
							}
						},
							{
								field : 'status',
								title : '处理状态',
								width : 10,
								formatter : function(value, row, index) {
									if(value==1){
										return "未开始";
									}else if(value==2){
										return "正在进行";
									}else if(value==3){
										return "已完成";
									}else{
										return value;
									}
								}
							},
							{
								field : 'times',
								title : '处理次数',
								width : 10,
								formatter : function(value, row, index) {
									return value;
								}
							},
					{
						field : 'msg',
						title : '说明',
						width : 40
					},
					{
						field : 'ctime',
						title : '处理时间',
						width : 20,
						formatter : function(value, row, index) {
							var date =new Date(value);
							var y = date.getFullYear();
							var m = date.getMonth() + 1;
							var d = date.getDate();
							var h =date.getHours();
							var f =date.getMinutes();
							return y + '-' + m + '-' + d+" "+h+":"+f;
						}
					},{
						field : 'id',
						title : '处理动作',
						width : 40,
						formatter : function(value, row, index) {
							var edit='<a href="javascript:doExcelEdit('+ value+ ')">修复无关联数据</a> ';
							return edit;
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}
function doExcelEdit(id){
	var bossOrder ={"id":id};
	$.messager.confirm('提示信息', "运行时间可能比较长，请勿重复点击，过段时间回来刷新页面即可，是否继续", function(r) {
		if (r) {
			$.ajax({
				url : "boss/doExcelEdit.json",
				async : false, // 同步
				type : "post",
				dataType : "json",
				data : bossOrder,
				success : function(data) {
					if (data.code == 1) {
						$.messager.alert(titleInfo, "处理完成");
//						$('#smsRe_table').datagrid('reload', parameter);
					} else {
						$.messager.alert(titleInfo, '处理失败！');
					}
				},
			});	
		}
	}); 
}
function doBossOrder(){
	$.messager.confirm('提示信息', "运行时间可能比较长，请勿重复点击，过段时间回来刷新页面即可，是否继续", function(r) {
		if (r) {
			var time =$('#q_ctime').datebox("getValue");
			if(time ==''){
				$.messager.alert(titleInfo, "先选择处理时间");
				return;
			}
			var excel ={"msg":time};
			$.ajax({
				url : "boss/doBossOrder.json",
				async : false, // 同步
				type : "post",
				dataType : "json",
				data : excel,
				success : function(data) {
					if (data.code == 1) {
						$.messager.alert(titleInfo, "处理完成");
						$('#smsRe_table').datagrid('reload', parameter);
					} else {
						$.messager.alert(titleInfo, '处理失败！');
					}
				},
			});	
		}
	}); 
}

