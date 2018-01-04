var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
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
	var q_sender = $('#q_sender').find("option:selected").text();
	if(q_sender=='全部'){
		q_sender ="";
	}
	parameter.sender =q_sender;
	$('#smsRe_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#smsRe_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'sms/getList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'branchName',
								title : '分公司名称',
								width : 40
							},
			 			{
							field : 'activityName',
							title : '活动名称',
							width : 40
						},
			 			{
							field : 'activityCode',
							title : '激活码',
							width : 60
						},
							{
								field : 'phone',
								title : '接收手机',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
							{
								field : 'sender',
								title : '发送者',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
					{
						field : 'state',
						title : '发送状态',
						width : 40,
						formatter : function(value, row, index) {
							return value==1?'已发送':'失败';
						}
					},
					{
						field : 'smsTemplet',
						title : '短信模板ID',
						width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}


