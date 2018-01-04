var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
	initActivityInfo();
	addActivity();
	queryActivity();
	// 初始化编辑弹窗
	$('#activity_detail_dialog').dialog({
		buttons : [ {
			text : '确 定',
			handler : function() {
				submit_activity_form();
			}
		}, {
			text : '取消',
			handler : function() {
				$('#activity_detail_dialog').dialog('close');
			}
		} ]
	});
});

// 弹出活动信息窗口
function addActivity() {
	$('#add_activity').click(function() {
		reset();
		$('#activity_detail_dialog').dialog('open');
	});
}

// 查询活动
function queryActivity() {
	$('#query_activity').click(function() {
		initActivityInfo();
	});
}

// 重置活动信息
function reset() {
	$('#productId').val('');
	$('#productName').val('');
	$('#chargingPrice').val('');
	$('#productCycle').val('');
	$('#productFreeDay').val('');
	$('#flag').val(0);
}



// 初始化活动信息
function initActivityInfo() {
	
	parameter = {};
//	var activityName = $('#activityName').val();
//	if (activityName != '') {
//		parameter.activityName = activityName;
//	}
	$('#activity_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#activity_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'activity/getListex.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'productId',
								title : 'ID',
								width : 10,
								formatter : function(value, row, index) {
									return value;
								}
							},
			 			{
							field : 'productName',
							title : '计费包名称',
							width : 70,
							formatter : function(value, row, index) {
								return value;
							}
						},
			 			{
							field : 'chargingPrice',
							title : '价格',
							width : 40
						},
							{
								field : 'productCycle',
								title : '计费周期(月)',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
							{
								field : 'productFreeDay',
								title : '免费时长（天）',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
					{
						field : 'productDay',
						title : '实际时长（天）',
						width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'flag',
						title : '状态',
						width : 40,
						formatter : function(value, row, index) {
								if (1 == value) {
									return '<div class="abtn"><a href="javascript:void(0)" class="a_on a_on2">已上线</a><a href="javascript:update('+ row.productId+ ',0)" class="a_off">切换</a></div>';
								} else {
									return '<div class="abtn"><a href="javascript:update('+ row.productId+ ',1)" class="a_off">切换</a><a href="javascript:void(0)" class="a_on a_on2">未上线</a></div>';
								}
						}
					},
					{
						field : 'id',
						title : '操作',
						width : 40,
						formatter : function(value, row, index) {
							if(isBranchUser==0){
								return "不能改";
							}
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}

// 上线
function update(id,flag) {
	$.messager.confirm('Warning', '确定要修改?', function(r) {
		if (r) {
			var activityex = {
					'productId' : id,
					'flag' : flag
			};
			$.post('activity/updateex.json', activityex, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '修改成功！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#activity_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '修改失败！');
				}
			}, 'json');
		}
	});
}

// 提交活动信息
function submit_activity_form() {
	var productId = $('#productId').val();
	var activityex = {
		'id' : productId,
		'productName' : $('#productName').val(),
		'productCycle' : $('#productCycle').val(),
		'productFreeDay' : $('#productFreeDay').val(),
		'flag' : $('#flag').val(),
		'chargingPrice':$('#chargingPrice').val()
	};
	var re = /^[0-9]\d*$/;
	if($('#productFreeDay').val()==0){
	}else{
		if(!re.test($('#productFreeDay').val())){
			$.messager.alert(titleInfo, '录入有误，仔细检查!！');
			return;
		}
	}
	if($('#productName').val()==''||$('#productName').val()==null
			||!re.test($('#productCycle').val())
			||!re.test($('#chargingPrice').val())){
		$.messager.alert(titleInfo, '录入有误，仔细检查！');
	}else{
	
		$.post("activity/addex.json", activityex, function(data) {
			if (data.code == 1) {
				$('#activity_detail_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#activity_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		}, "json");
	}
}

