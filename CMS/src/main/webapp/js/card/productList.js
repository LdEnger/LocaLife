var parameter = {};
var cityInfo ={};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
//	$.fn.datebox.defaults.formatter = function(date) {
//		var y = date.getFullYear();
//		var m = date.getMonth() + 1;
//		var d = date.getDate();
//		if (d < 10) {
//			d = "0" + d;
//		}
//		if (m < 10) {
//			m = "0" + m;
//		}
//		return y + '-' + m + '-' + d;
//	};
//	$("#q_ctime").datebox();
	initBranchInf(0);
	initSmsRecordInfo();
	queryActivity();
	//初始化修改编辑框
	$('#product_dialog').dialog({
		buttons : [ {
			text : '确认',
			handler : function() {
				submit_product_info();
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
	var startTime = $('#q_productName').val();
	parameter.productName =startTime;
	parameter.cityName =$('#q_cityName').val();
	parameter.activityId =$('#q_state').val();
	var q_branch =$('#q_branch').combobox('getValue');
	if(q_branch!=''){
		parameter.branchId =q_branch;
	}
	$('#smsRe_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#smsRe_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'boss/getProductList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'cityName',
								title : '地市名称',
								width : 30
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
							field : 'duration',
							title : '生效周期',
							width : 20,
							formatter:function(value,row,index){
								return value;
							}
						},
							{
								field : 'serviceFlag',
								title : '服务标识',
								width : 20,
								formatter : function(value, row, index) {
									return value;
								}
							},
							{
								field : 'ctime',
								title : '添加时间',
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
						field : 'activityName',
						title : '活动包名称',
						width : 30,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'type',
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
					},{
						field : 'id',
						title : '处理动作',
						width : 40,
						formatter : function(value, row, index) {
							var edit='<a href="javascript:updateProduct('+ value+ ')">关联活动包</a> ';
							return edit;
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}
function updateProduct(id){
	var rowInfo = $('#smsRe_table').datagrid('getSelected');
	if (rowInfo) {
		// 设置弹出框信息
		$('#e_product_id').val(rowInfo.id);
		var type =rowInfo.type;
		var productInfo ="";
		if(type==1){
			productInfo+="新boss|";
		}else{
			productInfo+="老boss|";
		}
		productInfo =productInfo+rowInfo.cityName+"|"+rowInfo.productName;
		$('#e_product_info').val(productInfo);
		$('#e_branch').combobox('setValue', rowInfo.branchId);
//		ininActivity(rowInfo.branchId);
		$('#e_activity').val(rowInfo.activityId);
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
			$.each(data, function(k, v) {
				 var id = v.id;
				 cityInfo[id]=v.branchName;
			});
			$('#q_branch').combobox({
	            data: data,
	            valueField: 'id',
	            textField: 'branchName'
	        });
			 $('#e_branch').combobox({
		            data: data,
		            valueField: 'id',
		            textField: 'branchName',
		            onChange:function(data){
		            	ininActivity(-1);
		            }
		        });
		},
	});
}
function ininActivity(branchId){
	$('#e_activity').empty();
	if(branchId ==-1){
		branchId=$('#e_branch').combobox('getValue');
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
function submit_product_info(){
	var id =$('#e_product_id').val();
	var branchId =$('#e_branch').combobox('getValue');
	var activityId =$('#e_activity').val();
	var activityName =$('#e_activity').find("option:selected").text();
	if(activityId==0){
		$.messager.alert(titleInfo, '请选择活动包');
		return ;
	}
	var productData ={"id":id,"branchId":branchId,"activityId":activityId,"activityName":activityName};
	//console.log(productData);
	$.ajax({
		url : "boss/updateProduct.json",
		async : false, // 同步
		type : "post",
		dataType : "json",
		data : productData,
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


