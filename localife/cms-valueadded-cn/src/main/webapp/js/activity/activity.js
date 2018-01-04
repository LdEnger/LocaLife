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
	$('#id').val('');
	$('#editActivityName').val('');
	$('#editEffectiveTime').val('');
	$('#editStatus').val('');
}



// 初始化活动信息
function initActivityInfo() {
	parameter = {};
	var status = $('#status').val();
	var roleId = $('#hiddenRoleId').val();
	var activityName = $('#activityName').val();
	if (status != -1) {
		parameter.status = status;
	}
	if (activityName != '') {
		parameter.activityName = activityName;
	}
	var hallId=$('#hidHallId').val();    
	if (hallId != -1) {
		parameter.hallId = hallId;
	}
	var branchId=$('#hidBranchId').val();    
	if (branchId != -1) {
		parameter.branchId = branchId;
	}
	var cityId=$('#hidCityId').val();    
	if (cityId != -1&&roleId!=1) {
		parameter.cityId = cityId;
	}
	$('#activity_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#activity_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'activity/getList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
			 			{
							field : 'zoneName',
							title : '战区名称',
							width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
			 			{
							field : 'zoneId',
							title : '战区ID',
							width : 40,
							hidden:true
						},
							{
								field : 'branchName',
								title : '分公司',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
							{
								field : 'hallName',
								title : '营业厅',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
					{
						field : 'activityName',
						title : '活动卡名称',
						width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'duration',
						title : '活动时长（天）',
						width : 40,
						formatter : function(value, row, index) {
							return value/86400;
						}
					},
					{
						field : 'insertTime',
						title : '开始时间',
						width : 40,
						hidden:true,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'price',
						title : '价格（元）',
						width : 40,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'effectiveTime',
						title : '有效期（天）',
						width : 40,
						formatter : function(value, row, index) {
							return value/86400;
						}
					},
					{
						field : 'endTime',
						title : '到期时间',
						width : 40,
						formatter : function(value, row, index) {
							var beginTime = new Date(row.insertTime);
							var endTime = new Date(beginTime.valueOf() +row.effectiveTime*1000);
							return endTime.getFullYear()+"年"+(endTime.getMonth()+1)+"月"+endTime.getDate()+"日 ";
						}
					},
					{
						field : 'operatorName',
						title : '操作者名称',
						width : 40,
						hidden : true,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'operatorId',
						title : '操作者ID',
						width : 40,
						hidden : true,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'operatorRole',
						title : '操作者角色',
						width : 40,
						hidden : true,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'operatorRoleId',
						title : '操作者角色ID',
						width : 40,
						hidden : true,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'isEffective',
						title : '是否过期',
						width : 40,
						hidden : true,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'status',
						title : '状态',
						width : 40,
						formatter : function(value, row, index) {
							if(1==row.isEffective){
								if (1 == value) {
									return '<div class="abtn"><a href="javascript:void(0)" class="a_on a_on2">已上线</a><a href="javascript:isOff('+ row.id+ ')" class="a_off">切换</a></div>';
								} else {
									return '<div class="abtn"><a href="javascript:isOn('+ row.id+ ')" class="a_off">切换</a><a href="javascript:void(0)" class="a_on a_on2">未上线</a></div>';
								}
							}else{
								return "已过期";
							}
						}
					},
					{
						field : 'id',
						title : '操作',
						width : 40,
						formatter : function(value, row, index) {
							if(0==row.isEffective){
								return '<a href="javascript:delActivity('+ value + ')">删除</a>';
							}
							if(1 == row.status){
								return '已上线不能操作';
							}else{
								return '<a href="javascript:editActivity('+ value+ ')">编辑</a>|<a href="javascript:delActivity('+ value + ')">删除</a>';
							}
						}
					} ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}


function select(){
	var objS = document.getElementById("pkg_list");
    var pkg = objS.options[objS.selectedIndex].value;
    $("#price").text((pkg.split("||"))[4]+"元");
    $("#hiddenProductId").val((pkg.split("||"))[0]);
    $("#hiddenProductName").val((pkg.split("||"))[1]);
    $("#hiddenChargingId").val((pkg.split("||"))[2]);
    $("#hiddenChargingName").val((pkg.split("||"))[3]);
    $("#hiddenPrice").val((pkg.split("||"))[4]);
    $("#hiddenChargingDuration").val((pkg.split("||"))[5]);
    $("#hiddenChargingPic").val((pkg.split("||"))[6]);
}


// 编辑
function editActivity(id) {
	var rowInfo = $('#activity_table').datagrid('getSelected');
	if (rowInfo) {
		// 设置弹出框信息
		generateDialog(rowInfo);
		$('#activity_detail_dialog').dialog('open');
	}
}

// 活动信息赋值
function generateDialog(rowInfo) {
	$('#id').val(rowInfo.id);
	$('#editActivityName').val(rowInfo.activityName);
	$('#editEffectiveTime').val(rowInfo.effectiveTime/86400);
	$('#editStatus').val(rowInfo.status);
}

// 上线
function isOn(id) {
	var rowInfo = $('#activity_table').datagrid('getSelected');
	if(rowInfo.duration ==null||rowInfo.effectiveTime==null||rowInfo.price==null||rowInfo.activityName==null){
		$.messager.alert(titleInfo, '请完善活动信息！');
		return;
	}
	$.messager.confirm('Warning', '确定要上线?', function(r) {
		if (r) {
			var activity = {
				'id' : id,
				'status' : 1
			};
			$.post('activity/update.json', activity, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已上线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#activity_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '上线失败！');
				}
			}, 'json');
		}
	});
}

// 下线
function isOff(id) {
	$.messager.confirm('Warning', '确定要下线?', function(r) {
		if (r) {
			var activity = {
				'id' : id,
				'status' : 0
			};
			$.post('activity/update.json', activity, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已下线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#activity_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '下线失败！');
				}
			}, 'json');
		}
	});
}

// 提交活动信息
function submit_activity_form() {
	var id = $('#id').val();
	var activity = {
		'id' : id,
		'activityName' : $('#editActivityName').val(),
		'effectiveTime' : $('#editEffectiveTime').val()*86400,
		'status' : $('#editStatus').val(),
		'operatorId' : $('#hiddenUserId').val(),
		'operatorName' : $('#hiddenUserName').val(),
		'operatorRoleId' : $('#hiddenRoleId').val(),
		'productId':$('#hiddenProductId').val(),
		'productName':$('#hiddenProductName').val(),
		'chargingId':$('#hiddenChargingId').val(),
		'chargingName':$('#hiddenChargingName').val(),
		'price':$('#hiddenPrice').val(),
		'duration':$('#hiddenChargingDuration').val()*86400,
		'chargingPic':$('#hiddenChargingPic').val(),
		'branchId':$('#hidBranchId').val(),
		'hallId':$('#hidHallId').val(),
		'branchName':$('#hidBranchName').val(),
		'hallName':$('#hidHallName').val(),
		'zoneId':$('#hidZoneId').val(),
		'zoneName':$('#hidZoneName').val(),
		'cityId':$('#hidCityId').val()
	};
	var editEffectiveTimeVal = $('#editEffectiveTime').val();
	var re = /^\d*$/;
	var timeCheck=re.test(editEffectiveTimeVal);
	if($('#hiddenChargingId').val()==''||$('#hiddenChargingId').val()==null||$('#editActivityName').val()==''||$('#editActivityName').val()==null||
			!timeCheck){
		$.messager.alert(titleInfo, '活动信息不完整！');
	}else{
	if (id != null && id != "") {
		$.post('activity/update.json', activity, function(data) {
			if (data.code == 1) {
				$('#activity_detail_dialog').dialog('close');
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
		}, "json");
	} else {
		$.post("activity/add.json", activity, function(data) {
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
}

//删除
function delActivity(id) {
	$.messager.confirm('Warning', 'Warning：该活动有可能已经关联卡券，删除可能引发一些问题！确定要删除?', function(r) {
		if(r){
			var activity = {
					'id' : id
			};
			$.post('activity/del.json', activity, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已删除！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#activity_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '删除失败！');
				}
			}, 'json');
		}
	});
}