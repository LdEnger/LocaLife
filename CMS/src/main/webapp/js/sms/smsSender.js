var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
	initSmsRecordInfo();
	queryActivity();
	$('#testSenderDiv').dialog({
		buttons : [ {
			text : '确 定',
			handler : function() {
				submitTestSender();
			}
		}, {
			text : '取消',
			handler : function() {
				$('#testSenderDiv').dialog('close');
			}
		} ]
	});
	$('#daoSenderDiv').dialog({
		buttons : [ {
			text : '确定',
			handler : function() {
				submitSendInfo();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#daoSenderDiv').dialog('close');
			}
		} ]
	});
});


// 查询记录
function queryActivity() {
	$('#query_smsR').click(function() {
		initSmsRecordInfo();
	});
	$('#addsender').click(function() {
		resetSenderInfo();
		$('#daoSenderDiv').dialog('open');
	});
}

function resetSenderInfo(){
	$('#sender_id').val("");
	$('#user').val("");
	$('#pwd').val("");
	$('#branchId').val("");
}
// 初始化活动信息
function initSmsRecordInfo() {
	parameter = {};
	var q_sender = $('#q_sender').find("option:selected").text();
	if(q_sender=='全部'){
		q_sender ="-1";
	}
	parameter.branchId =q_sender;
	$('#smsRe_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#smsRe_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'sms/getSenderList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'branchId',
								title : '分公司名称',
								width : 30,
								formatter:function(value,row,index){
									return branchListData[value];
								}
							},
			 			{
							field : 'sender',
							title : '发送url',
							width : 50
						},
			 			{
							field : 'user',
							title : '用户名',
							width : 40
						},
							{
								field : 'pwd',
								title : '密码',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
							{
								field : 'updateBy',
								title : '修改工号',
								width : 30,
								formatter : function(value, row, index) {
									return value;
								}
							},
					{
						field : 'initSmsConf',
						title : '初始化状态',
						width : 20,
						hidden:true,
						formatter : function(value, row, index) {
							return value==0?'初始化':'初始';
						}
					},
					{
						field : 'id',
						title : '操作',
						width : 60,
						formatter : function(value, row, index) {
							var branchId =row.branchId;
							var user =row.user;
							var pwd =row.md5;
							var edit='<a href="javascript:updateSender('+ value+ ')">修改</a>| ';
							var testSender='<a href="javascript:testSender('+ branchId+ ')">测发短信</a>| ';
							var queryUrl ="http://api.app2e.com/getBalance.api.php?pwd="+pwd+"&username="+user;
							var queryBalance='<a href="'+ queryUrl+ '" target="_blank")">查剩余条数</a>';
							var editstr =edit+testSender+queryBalance;
							return editstr;
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}

function updateSender(id){
	var rowInfo = $('#smsRe_table').datagrid('getSelected');
	if (rowInfo) {
		// 设置弹出框信息
		$('#sender_id').val(rowInfo.id);
		$('#user').val(rowInfo.user);
		$('#pwd').val(rowInfo.pwd);
		$('#branchId').val(rowInfo.branchId);
		$('#signature').val(rowInfo.signature);
		$('#daoSenderDiv').dialog('open');
	}
}
function testSender(branchId){
	$('#testSenderDiv').dialog('open');
	$('#testPhoneNo').val("");
	$('#testSenderId').val(branchId);
}
function submitTestSender(){
	var sender ={
			id:$('#testSenderId').val(),
			sender:$('#testPhoneNo').val()
	};
	$.post('sms/testSender.json', sender, function(data) {
		if (data.code == 1) {
			$('#testSenderDiv').dialog('close');
			$.messager.show({
				title : titleInfo,
				msg : '发送成功！',
				timeout : timeoutValue,
				showType : 'slide'
			});
		}else if(data.code == 3){
			$('#testSenderDiv').dialog('close');
			$.messager.show({
				title : titleInfo,
				msg : '短信内容配置未上线！',
				timeout : timeoutValue,
				showType : 'slide'
			});
		} else {
			$.messager.alert(titleInfo, '发送失败！');
		}
	}, "json");
}

function submitSendInfo(){
	if($('#user').val()==''||$('#pwd').val()==''||$('#signature').val()==''||$('#branchId').val()==''){
		$.messager.alert(titleInfo, '所有必须填写！');
		return ;
	}
	var senderInfo ={
			id:$('#sender_id').val(),
			sender:$('#sender').val(),
			user:$('#user').val(),
			pwd:$('#pwd').val(),
			branchId:$('#branchId').val(),
			signature:$('#signature').val()
	};
	if($('#sender_id').val()==''){
		$.post('sms/addSender.json', senderInfo, function(data) {
			if (data.code == 1) {
				$('#daoSenderDiv').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '保存成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#smsRe_table').datagrid('reload', parameter);
			} else {
				$.messager.alert(titleInfo, '保存失败！');
			}
		}, "json");
	}else{
		$.post('sms/updateSender.json', senderInfo, function(data) {
			if (data.code == 1) {
				$('#daoSenderDiv').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '修改成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#smsRe_table').datagrid('reload', parameter);
			} else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		}, "json");
	}
	
}

