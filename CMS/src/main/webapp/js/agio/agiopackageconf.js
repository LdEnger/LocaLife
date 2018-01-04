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
			text : '保存',
			handler : function() {
				submit_activity_form(0);
			}
		}, {
			text : '提交',
			handler : function() {
				submit_activity_form(1);
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
	$('#agioValue').val('');
	$('#agioName').val('');
}



// 初始化活动信息
function initActivityInfo() {
	parameter = {};
	$('#agio_package_conf_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#agio_package_conf_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'agiopackage/getList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'agioValue',
								title : '折扣值',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
			 			{
							field : 'agioName',
							title : '折扣名称',
							width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
			 			{
							field : 'state',
							title : '状态',
							width : 40,
							formatter : function(value, row, index) {
								return value==1?"已提交":"未提交";
							}
						},
					{
						field : 'id',
						title : '操作',
						width : 40,
						formatter : function(value, row, index) {
							var con_state =row.state;
							if(con_state==1){
								return '-';
							}else{
								return '<a href="javascript:editActivity('+ value+ ')">编辑</a>';
							}
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}


// 编辑
function editActivity(id) {
	var rowInfo = $('#agio_package_conf_table').datagrid('getSelected');
	if (rowInfo) {
		// 设置弹出框信息
		generateDialog(rowInfo);
		$('#agio_package_conf_dialog').dialog('open');
	}
}

// 活动信息赋值
function generateDialog(rowInfo) {
	$('#id').val(rowInfo.id);
	$('#agioName').val(rowInfo.agioName);
	$('#agioValue').val(rowInfo.agioValue);
}

function submit_activity_form(state) {
	var id = $('#id').val();
		if($('#agioName').val()==''||$('#agioValue').val()==''){
			$.messager.alert(titleInfo, '请输入数据！');
			return false;
		}
		$.messager.confirm('', '<font color=red>折扣信息提交正式生效，提交之后不可修改</font>', function(result){  
	        if(result) {  
	        	var re = /^\d*$/;
	        	var check=re.test($('#agioValue').val());
	        	if(!check){
	        		$.messager.alert(titleInfo, '折扣配置值请输入正整数！');
	        		return false;
	        	};
	        	if($('#agioValue').val()<0||$('#agioValue').val()>100){
	        		$.messager.alert(titleInfo, '折扣配置值请输入1-100的数字！');
	        		return false;
	        	}
	        	var agio_package_conf = {
	        		'id' : id,
	        		'agioName' : $('#agioName').val(),
	        		'agioValue':$('#agioValue').val(),
	        		'state':state
	        	};
	        	
	        	if (id != null && id != "") {
	        		$.post('agiopackage/update.json', agio_package_conf, function(data) {
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
	        				$.messager.alert(titleInfo, '修改失败！'+data.msg);
	        			}
	        		}, "json");
	        	} else {
	        		$.post("agiopackage/add.json", agio_package_conf, function(data) {
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
	        				$.messager.alert(titleInfo, '添加失败！'+data.msg);
	        			}
	        		}, "json");
	        	}
	        }  
	    });  
	
}

