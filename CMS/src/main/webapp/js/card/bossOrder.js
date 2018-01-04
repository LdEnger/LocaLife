var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
var cityInfo={};
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
	initBranchInf(0);
	$('#product_dialog').dialog({
		buttons : [ {
			text : '确认',
			handler : function() {
				submit_BossOrder_info();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#product_dialog').dialog('close');
			}
		} ]
	});
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
	parameter.ctime =startTime;
	parameter.sn =$('#q_sn').val();
	parameter.status =$('#q_status').val();
	parameter.bossType =$('#q_bossType').val();
	parameter.branchId=$('#q_branch').val();
	parameter.activityId=$('#q_state').val();
	$('#smsRe_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#smsRe_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'boss/getBossOrderList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'customerId',
								title : '宽带证号',
								width : 30,
								hidden:true
							},
			 			{
							field : 'customerName',
							title : '客户名称',
							width : 30,
							formatter:function(value,row,index){
								return value;
							}
						},
			 			{
							field : 'productName',
							title : '套餐名称',
							width : 30,
							formatter:function(value,row,index){
									return value;
							}
						},
							{
								field : 'amount',
								title : '金额',
								width : 10,
								formatter : function(value, row, index) {
										return value;
								},
								hidden:true
							},
							{
								field : 'ctime',
								title : '创建时间',
								width : 20,
								formatter : function(value, row, index) {
									return value;
								},
								hidden:true
							},
					{
						field : 'cuser',
						title : '操作员',
						width : 30,
						hidden:true
					},
					{
						field : 'mac',
						title : 'mac',
						width : 25
					},
					{
						field : 'sn',
						title : 'sn',
						width : 25
					},
					{
						field : 'cityName',
						title : '地市名称',
						width : 20,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'branchId',
						title : '分公司',
						width : 30,
						formatter:function(value){
							return cityInfo[value];
						}
					},
					{
						field : 'bossType',
						title : 'boss类型',
						width : 20,
						formatter : function(value, row, index) {
							if(value==1){
								return "新boss";
							}else if(value==2){
								return "老boss";
							}else{
								return value;
							}
						}
					},
					{
						field : 'msg',
						title : '状态',
						width : 30
					},
					{
						field:'activityCode',
						title:'激活码',
						width:30
					},
					{
						field : 'id',
						title : '处理动作',
						width : 40,
						formatter : function(value, row, index) {
							var status =row.status;
							if(status==2||status==3){
								return "无操作";
							}
							var edit='<a href="javascript:updateBossOrder('+ value+ ')">修改</a> |';
							var getCode ='<a href="javascript:getActivityCode('+ value+ ')">提卡密</a> |';
							var smsSend ='<a href="javascript:sendSms('+ value+ ')">发短信</a> |';
							return edit+getCode+smsSend;
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}
function sendSms(id){
	var bossOrder ={"id":id};
	$.ajax({
		url : "boss/sendActivitySms.json",
		async : false, // 同步
		type : "post",
		dataType : "json",
		data : bossOrder,
		success : function(data) {
			if (data.code == 1) {
				$.messager.alert(titleInfo, data.msg);
//				$('#smsRe_table').datagrid('reload', parameter);
			} else {
				$.messager.alert(titleInfo, '提取失败！');
			}
		},
	});
}
function getActivityCode(id){
	var bossOrder ={"id":id};
	
	$.ajax({
		url : "boss/getActivityCode.json",
		async : false, // 同步
		type : "post",
		dataType : "json",
		data : bossOrder,
		success : function(data) {
			if (data.code == 1) {
				$.messager.alert(titleInfo, data.msg);
				$('#smsRe_table').datagrid('reload', parameter);
			} else {
				$.messager.alert(titleInfo, '提取失败！');
			}
		},
	});
}
function updateBossOrder(){
		var rowInfo = $('#smsRe_table').datagrid('getSelected');
		if (rowInfo) {
			// 设置弹出框信息
			$('#e_order_id').val(rowInfo.id);
			var type =rowInfo.type;
			var productInfo ="";
			if(type==1){
				productInfo+="新boss|";
			}else{
				productInfo+="老boss|";
			}
			productInfo =productInfo+rowInfo.cityName+"|"+rowInfo.productName;
			$('#e_product_info').val(productInfo);
			
			$('#e_branch').val(rowInfo.branchId);
			ininActivity(rowInfo.branchId);
			$('#e_activity').val(rowInfo.activityId);
			$('#e_mac').val(rowInfo.mac);
			$('#e_sn').val(rowInfo.sn);
			$('#e_phone').val(rowInfo.phone);
			$('#e_code').val(rowInfo.activityCode);
			$('#product_dialog').dialog('open');
		}
}
function initBranchInf(branchId){
	$.ajax({
		url : "branch/getAllList.json",
		async : false, // 同步
		type : "get",
		dataType : "json",
		success : function(data) {
			$('#q_branch').append("<option value='0'>请选择</option>");
			$.each(data, function(k, v) {
				 var id = v.id;
				 cityInfo[id]=v.branchName;
				 
				 if(id==branchId){
					 $('#e_branch').append("<option value='"+ v.id+"' selected>"+ v.branchName+"</option>");
				 }else{
					 $('#e_branch').append("<option value='"+ v.id+"'>"+ v.branchName+"</option>");
				 }
				 $('#q_branch').append("<option value='"+ v.id+"'>"+ v.branchName+"</option>");
			});
		},
	});
}
function ininActivity(branchId){
	$('#e_activity').empty();
	if(branchId ==-1){
		branchId=$('#e_branch').val();
	}
	if(branchId==0){
		$('#e_activity').append("<option value='0' selected>请选择分公司</option>");
	}else{
		$('#e_activity').append("<option value='0' selected>请选择</option>");
		$.ajax({
			url : "boss/getActivityList.json",
			async : false, // 同步
			type : "get",
			dataType : "json",
			data:{"branchId":branchId},
			success : function(data) {
				$.each(data, function(k, v) {
					$('#e_activity').append("<option value='"+ v.id+"' selected>"+ v.activityName+"</option>");
				});
			},
		});
	}
	
}
function submit_BossOrder_info(){
	var id =$('#e_order_id').val();
	var branchId =$('#e_branch').val();
	var activityId =$('#e_activity').val();
	var activityName =$('#e_activity').find("option:selected").text();
	if(activityId==0){
		$.messager.alert(titleInfo, '请选择活动包');
		return ;
	}
	var mac =$('#e_mac').val();
	var sn =$('#e_sn').val();
	var phone =$('#e_phone').val();
	var bossOrder ={"id":id,"branchId":branchId,"activityId":activityId,"activityName":activityName,"mac":mac,"sn":sn,"phone":phone};
//	console.log(bossOrder);
	$.ajax({
		url : "boss/updateBossOrder.json",
		async : false, // 同步
		type : "post",
		dataType : "json",
		data : bossOrder,
		success : function(data) {
			if (data.code == 1) {
				$('#product_dialog').dialog('close');
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
		},
	});
}

