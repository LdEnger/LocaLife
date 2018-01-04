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
	$('#amount').val('');
//	$('#agioName').val('');
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
			url : 'agiopackage/getViewList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
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
							title : '费率名称',
							width : 40,
							formatter : function(value, row, index) {
								return agioListData[value];
							}
						},
						{
							field : 'totalAmount',
							title : '总金额',
							width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'useAmount',
							title : '已用金额',
							width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'leftAmount',
							title : '剩余金额',
							width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						}] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
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

