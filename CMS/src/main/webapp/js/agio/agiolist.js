var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
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
	$('#state').val('');
	$('#agioPackageId').val('');
	$('#packageName').val('');
	$('#packAmount').val('');
}



// 初始化活动信息
function initActivityInfo() {
	parameter = {
			'branchId':$('#q_branch').val(),
			'createBy':$('#q_sender').val(),
			'state':$('#q_state').val()
	};
	$('#agio_package_conf_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#agio_package_conf_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'agiopackage/getAgioList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'packageName',
								title : '麦币包名称',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
			 			{
							field : 'packAmount',
							title : '麦币包金额',
							width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'zoneId',
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
							field : 'agioPackageId',
							title : '折扣包名称',
							width : 40,
							formatter : function(value, row, index) {
								return agioListData[value];
							}
						},
						{
							field : 'state',
							title : '状态',
							width : 40,
							formatter : function(value, row, index) {
								if(value==1){
									return "上线";
								}else if(value==0){
									return "下线";
								}else if(value==3){
									return "删除";
								}else{
									return value;
								}
							}
						},
						{
							field : 'id',
							title : '操作',
							width : 40,
							formatter : function(value, row, index) {
								if(isBranchUser==0){
									return "X";
								}
								var state =row.state;
								var delstr ='&nbsp|&nbsp<a href="javascript:updateAgio('+ value+ ',3)">删除</a>';
								if(state==3){
									return "已删除不可操作"
								}else if(state==0){
									return '<a href="javascript:updateAgio('+ value+ ',1)">上线</a>'+delstr;
								}else if(state==1){
									return '<a href="javascript:updateAgio('+ value+ ',0)">下线</a>'+delstr;
								}
							}
						}] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}

function updateAgio(id,state){
	var agio_package_pool ={
			'id':id,
			'state':state
	};
	$.post('agiopackage/agioUpdate.json', agio_package_pool, function(data) {
		if (data.code == 1) {
			$('#agio_package_conf_dialog').dialog('close');
			$.messager.show({
				title : titleInfo,
				msg : '修改成功！',
				timeout : timeoutValue,
				showType : 'slide'
			});
			$('#agio_package_conf_table').datagrid('load', parameter);
		} else {
			$.messager.alert(titleInfo, '修改失败！');
		}
	}, "json");
}

function submit_activity_form(state) {
		var id = $('#id').val();
		var hiddenRoleId = $('#hiddenRoleId').val();
		if(hiddenRoleId!='2'){
			$.messager.alert(titleInfo, '非分公司权限人员不准添加麦币包！');
    		return false;
		}
		$.messager.confirm('', '<font color=red>请仔细核对麦币包信息，保存之后不可修改</font>', function(result){  
	        if(result) {  
	        	var re = /^\d*$/;
	        	var check=re.test($('#packAmount').val());
	        	if(!check){
	        		$.messager.alert(titleInfo, '麦币包金额请输入正整数！');
	        		return false;
	        	};
	        	if($('#packAmount').val()<0||$('#amount').val()==''){
	        		$.messager.alert(titleInfo, '麦币包金额请输入大于0的数字！');
	        		return false;
	        	}
	        	if($('#packageName').val()==''||$('#agioPackageId').val()==''){
	        		$.messager.alert(titleInfo, '请录入所有项目！');
	        		return false;
	        	}
	        	var agio_package = {
	        		'id' : id,
	        		'packAmount':$('#packAmount').val(),
	        		'packageName' : $('#packageName').val(),
	        		'agioPackageId':$('#agioPackageId').val(),
	        		'state':$('#state').val()
	        	};
//	        	console.log(agio_package);
	        	$.post('agiopackage/agioAdd.json', agio_package, function(data) {
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
        				$.messager.alert(titleInfo, '添加失败！');
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

