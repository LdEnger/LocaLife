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
	$("#q_createTime").datebox();
	initActivityInfo();
	addActivity();
	queryActivity();
	// 初始化编辑弹窗
	$('#agio_package_conf_dialog').dialog({
		buttons : [ {
			text : '确 定',
			handler : function() {
				submit_activity_form(0);
			}
		},
		{
			text : '取消',
			handler : function() {
				$('#agio_package_conf_dialog').dialog('close');
			}
		} ]
	});
});

// 弹出活动信息窗口
function addActivity() {
	$('#add_agio_package_conf').click(function() {
		reset();
		$('#agio_package_conf_dialog').dialog('open');
	});
}

// 查询活动
function queryActivity() {
	$('#query_agio_package_conf').click(function() {
		initActivityInfo();
	});
}

// 重置活动信息
function reset() {
	$('#id').val('');
	$('#amount').val('');
//	$('#agioName').val('');
}



// 初始化活动信息
function initActivityInfo() {
	parameter = {
			'branchId':$('#q_branch').val(),
			'createBy':$('#q_sender').val(),
//			'createTime':$('#q_createTime').datebox("getValue")
	};
	var createTimeval =$('#q_createTime').datebox("getValue");
	if(createTimeval!=''){
		parameter.orderId=createTimeval;//借用下字段
	}
//	console.log(parameter);
	$('#agio_package_conf_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#agio_package_conf_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'agiopackage/getPoolList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'orderId',
								title : '订单号',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
			 			{
							field : 'amount',
							title : '金额',
							width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'zondId',
							title : '战区',
							width : 40,
							formatter : function(value, row, index) {
								return zoneListData[value];
							}
						},
						{
							field : 'branchId',
							title : '分公司',
							width : 40,
							formatter : function(value, row, index) {
								return branchListData[value];
							}
						},
						{
							field : 'packageConfId',
							title : '折扣',
							width : 40,
							formatter : function(value, row, index) {
								return agioListData[value];
							}
						},
						{
							field : 'createTime',
							title : '添加时间',
							width : 40,
							formatter : function(value, row, index) {
								return formatDate(value);
							}
						},
						{
							field : 'createBy',
							title : '创建者',
							width : 40,
							formatter : function(value, row, index) {
								return userListData[value];
							}
						}] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}



function submit_activity_form(state) {
		var id = $('#id').val();
		$.messager.confirm('', '<font color=red>请仔细核对充值信息，保存之后不可修改</font>', function(result){  
	        if(result) {  
	        	var re = /^\d*$/;
	        	var check=re.test($('#amount').val());
	        	if(!check){
	        		$.messager.alert(titleInfo, '折扣配置值请输入正整数！');
	        		return false;
	        	};
	        	if($('#amount').val()<0||$('#amount').val()==''){
	        		$.messager.alert(titleInfo, '折扣配置值请输入大于0的数字！');
	        		return false;
	        	}
	        	if($('#zondId').val()==-1||$('#branchId').val()==-1||$('#packageConfId').val()==''){
	        		$.messager.alert(titleInfo, '请选择所有项目！');
	        		return false;
	        	}
	        	var today =new Date();
	        	var longToday =today.getTime();
	        	var agio_package_pool = {
	        		'id' : id,
	        		'orderId':"MB"+longToday,
	        		'amount' : $('#amount').val(),
	        		'zondId':$('#zondId').val(),
	        		'branchId':$('#branchId').val(),
	        		'packageConfId':$('#packageConfId').val(),
	        		'createBy':$('#hiddenUserId').val()
	        	};
	        	
	        	$.post('agiopackage/poolAdd.json', agio_package_pool, function(data) {
        			if (data.code == 1) {
        				$('#agio_package_conf_dialog').dialog('close');
        				$.messager.show({
        					title : titleInfo,
        					msg : '添加成功！',
        					timeout : timeoutValue,
        					showType : 'slide'
        				});
        				$('#agio_package_conf_table').datagrid('load', parameter);
        			} else {
        				$.messager.alert(titleInfo, '修改失败！');
        			}
        		}, "json");
	        }  
	    });  
	
}
//根据战区初始化分公司信息
function initQueryBranch(zoneId) {
	if (zoneId == "-1" || zoneId == "") {
		$("#branchId").html("<option value='-1'>全部</option>");
	} else {
		$.post("zoneCity/getBranchByZone.json", { "zoneId" : zoneId }, function(data) {
			$("#branchId").html("<option value='-1'>全部</option>");
			$.each(data, function(dataIndex, obj) {
				$("#branchId").append( '<option value=' + obj.id + '>' + obj.branchName + '</option>');
			});
		}, "json");
	}
}
function formatDate(longnow) {
	var now =new Date(longnow);
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":" + second;
}

