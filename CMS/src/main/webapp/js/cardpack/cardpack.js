var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
	initDateBox();//初始化 日期插件
	initBranchInf(0);//初始化 分公司信息
	initActivityInfo();
	addActivity();
	exportActivity();//初始化 导出框
	queryActivity(); //初始化 查询框
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

// 弹出制卡信息窗口
function addActivity() {
	$('#add_activity').click(function() {
		reset();
		$('#activity_detail_dialog').dialog('open');
	});
}
// 弹出提取卡信息窗口
function exportActivity() {
	$('#export_activity').click(function() {
        reset_export();
		$('#export_detail_dialog').dialog('open');
	});
	$('#export_detail_dialog').dialog({
		buttons : [ {
			text : '确 定',
			handler : function() {
				submit_export_form();
			}
		}, {
			text : '取消',
			handler : function() {
				$('#export_detail_dialog').dialog('close');
			}
		} ]
	});
}

function initDateBox(){

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

	$("#create_startTime").datebox();
	$("#create_endTime").datebox();
	$("#open_startTime").datebox();
	$("#open_endTime").datebox();
}

// 查询大麦卡
function queryActivity() {
	$('#query_activity').click(function() {
		initActivityInfo();
	});
}

// 重置制卡页面
function reset() {
	$("#card_provider").val('0');
    $("#cardNum").val('');
    $("#if_vip").val('0');
    $("#vip_days").val('0');
    $("#renew_num").val('12');
    select();
    initBranchInf(0);
}

function  reset_export() {
    $("#export_cardNum").val('');
    $("#export_cardProvider").val('0');
    select_aqytx();
    initBranchInf(0);
}



// 初始化查询卡列表
function initActivityInfo() {
	parameter = {};
    var card_provider=$('#search_card_provider').val();
    parameter.card_provider = card_provider;
    var mac=$('#mac').val();
    parameter.mac = mac;
    var sn=$('#sn').val();
    parameter.sn = sn;
	var status = $('#status').val();
	parameter.status = status;
	var extract_status = $('#extract_status').val();
	parameter.extract_status = extract_status;
	var create_status = $('#create_status').val();
	parameter.create_status = create_status;
	var dm_card_val = $('#dm_card_val').val();
	parameter.dm_card_val = dm_card_val;
	var user_id = $('#user_id').val();
	parameter.user_id = user_id;

	var create_startTime = $('#create_startTime').datebox("getValue");
	parameter.create_startTime = create_startTime;
	var create_endTime =   $('#create_endTime').datebox("getValue");
	parameter.create_endTime = create_endTime;

	var open_startTime = $('#open_startTime').datebox("getValue");
	parameter.open_startTime = open_startTime;
	var open_endTime =   $('#open_endTime').datebox("getValue");
	parameter.open_endTime = open_endTime;

	var select_renew_num =   $('#select_renew_num').val();
	parameter.select_renew_num = select_renew_num;

	var branch_id =  $('#q_branch').combobox('getValue')
	parameter.branch_id = branch_id;



	$('#activity_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#activity_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'cardPack/getList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'dm_card_val',
								title : '大麦激活码',
								width : 60,
								formatter : function(value, row, index) {
									var resultparm = row.dm_card_val;
									var result = resultparm.replace(/1/g,'*');
									var result = result.replace(/2/g,'*');
									var result = result.replace(/3/g,'*');
									var result = result.replace(/4/g,'*');
									var result = result.replace(/5/g,'*');
									return result;
								}
							},
						/*	{
								field : 'source_card_val',
								title : '腾讯激活码',
								width : 40,
								formatter : function(value, row, index) {
									 return row.source_card_val;
								}
							},*/
							{
								field : 'card_provider',
								title : '提供方卡类型',
								width : 40,
								formatter : function(value, row, index) {
									if(1==value){
										return '爱奇艺包月卡';
									}else if (0==value) {
										return '腾讯连续包月年卡';
									}else{
										return '';
									}
								}
							},
				   			{
								field : 'renew_num',
								title : '续费月数',
								width : 25,
							    formatter : function(value, row, index) {
								   return row.renew_num;
							    }
							},
							{
								field : 'if_vip',
								title : 'vip',
								width : 25,
								formatter : function(value, row, index) {
									if(0==value){
										return '<font color= red>否</font>';
									}else if (1==value) {
										return '<font color= blue>是</font>';
									}else{
										return '';
									}
								}
							},
							{
								field : 'vip_days',
								title : 'vip月数',
								width : 25,
								formatter : function(value, row, index) {
									return row.vip_days;
								}
							},
							{
								field : 'create_time',
								title : '生成时间',
								width : 35,
								formatter : function(value, row, index) {
									return row.create_time;
								}
							},

							{
								field : 'creator_name',
								title : '制卡人',
								width : 30,
								formatter : function(value, row, index) {
									return row.creator_name;
								}
							},
							{
								field : 'export_name',
								title : '提取人',
								width : 30,
								formatter : function(value, row, index) {
									return row.export_name;
								}
							},
							{
								field : 'branch_name',
								title : '分公司',
								width : 35,
								formatter : function(value, row, index) {
									return row.branch_name;
								}
							},

							{
								field : 'open_time',
								title : '开通时间',
								width : 35,
								formatter : function(value, row, index) {
									return row.open_time;
								}
							},
							{
								field : 'mac',
								title : 'mac',
								width : 30,
								formatter : function(value, row, index) {
									return row.mac;
								}
							},
							{
								field : 'sn',
								title : 'sn',
								width : 30,
								formatter : function(value, row, index) {
									return row.sn;
								}
							},
							{
								field : 'user_id',
								title : '用户id',
								width : 30,
								formatter : function(value, row, index) {
									return row.user_id;
								}
							},
							{
								field : 'status',
								title : '使用状态',
								width : 30,
								formatter : function(value, row, index) {
									if(1==value){
										return '<font color= red>已使用</font>';
									}else if (0==value) {
										return '<font color= blue>未使用</font>';
									}else
									{
										return "";
									}
								}
							},
							{
								field : 'create_status',
								title : '制卡状态',
								width : 30,
								formatter : function(value, row, index) {
									if(1==value){
										return '<font color= red>已制卡</font>';
									}else if (0==value) {
										return '<font color= blue>未制卡</font>';
									}else
									{
										return "";
									}
								}
							},
							{
								field : 'extract_status',
								title : '提取状态',
								width : 30,
								formatter : function(value, row, index) {
									if(1==value){
										return '<font color= red>已提取</font>';
									}else if (0==value) {
										return '<font color= blue>未提取</font>';
									}else
									{
										return "";
									}
								}
							}
					  ] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}

// 提交 导出卡  信息
var renew_num;
function submit_export_form() {
    var card_provider= $('#export_cardProvider').val();
    if(card_provider == 0){
        renew_num=$('#tx_export_renewNum').val();
	}else if(card_provider == 1){
    	renew_num=$('#export_renewNum').val();
	}
	var activity = {
		'export_cardProvider' : card_provider,
		'export_cardNum' : $('#export_cardNum').val(),
		'export_renewNum' :renew_num,
		'renew_num' : $('#renew_num').val(),
		'branch_id' : $('#export_branch').combobox('getValue'),
		'branch_name' : $('#export_branch').combobox('getText')
	};
    if(null==activity.export_cardProvider||''==activity.export_cardProvider){
        $.messager.alert(titleInfo, "请选择卡提供方！！！");
        return;
    }
	if(null==activity.export_cardNum||activity.export_cardNum>5000){
		$.messager.alert(titleInfo, "请输入导出数量，不要大于5000");
		return;
	}
	if(null==activity.branch_id||''==activity.branch_id||null==activity.branch_name||''==activity.branch_name){
		$.messager.alert(titleInfo, "请选择分公司");
		return;
	}
	loadDiv('正在导出，请稍后...');
	$.post("cardPack/export.json", activity, function(data) {
		if (data.code >= 1) {
			$('#export_detail_dialog').dialog('close');
			$.messager.show({
				title : titleInfo,
				msg : '导出成功',
				timeout : timeoutValue,
				showType : 'slide'
			});
			$('#activity_table').datagrid('load', parameter);
			//alert('open first');
			displayLoad();
			window.open(data.download_url);
			//window.location.href = data.download_url;
			//alert('open end');
		} else {
			$.messager.alert(titleInfo, data.msg);
			displayLoad();
		}
	}, "json");

}

// 提交 制卡 信息
function submit_activity_form() {
	var activity = {
		'card_provider' : $('#card_provider').val(),
		'cardNum' : $('#cardNum').val(),
		'if_vip' : $('#if_vip').val(),
		'vip_days' : $('#vip_days').val(),
		'renew_num' : $('#renew_num').val(),
		'branch_id' : $('#add_branch').combobox('getValue'),
		'branch_name' : $('#add_branch').combobox('getText')
	};
	if(null==activity.cardNum||activity.cardNum>5000||0==activity.cardNum){
		$.messager.alert(titleInfo, "请输入数量，不要大于5000");
		return;
	}
	if(null==activity.branch_id||''==activity.branch_id||null==activity.branch_name||''==activity.branch_name){
		$.messager.alert(titleInfo, "请选择分公司");
		return;
	}
	loadDiv('正在制卡，请稍后...');
	$.post("cardPack/add.json", activity, function(data) {
		if (data.code >= 1) {
			$('#activity_detail_dialog').dialog('close');
			$.messager.show({
				title : titleInfo,
				msg : '添加成功:数量 '+data.code,
				timeout : timeoutValue,
				showType : 'slide'
			});
			$('#activity_table').datagrid('load', parameter);
			displayLoad();
		} else {
			$.messager.alert(titleInfo, data.msg);
			displayLoad();
		}
	}, "json");

}

/**
 * 初始话查询页面的 分公司信息
 * @param branchId
 */
function initBranchInf(branchId){
	$.ajax({
		url : "branch/getAllList.json",
		async : false, // 同步
		type : "get",
		dataType : "json",
		success : function(data) {
			//$.each(data, function(k, v) {
			//	var id = v.id;
			//	cityInfo[id]=v.branchName;
			//});
			$('#q_branch').combobox({
				data: data,
				valueField: 'id',
				textField: 'branchName'
			});
			$('#add_branch').combobox({
				data: data,
				valueField: 'id',
				textField: 'branchName'
			});
			$('#export_branch').combobox({
				data: data,
				valueField: 'id',
				textField: 'branchName'
			});
			//$('#e_branch').combobox({
			//	data: data,
			//	valueField: 'id',
			//	textField: 'branchName',
			//	onChange:function(data){
			//		ininActivity(-1);
			//	}
			//});
		}
	});
}


function select(){
	var objS = document.getElementById("if_vip");
	var pkg = objS.options[objS.selectedIndex].value;
	var vip_days = document.getElementById("vip_tr");
	if(pkg==0){
		vip_days.style.display='none';
		document.getElementById("vip_days").value=0;
	}if(pkg==1){
		vip_days.style.display='';
	}

}

function select_aqytx(){
    var objS = document.getElementById("export_cardProvider");
    var pkg = objS.options[objS.selectedIndex].value;
    var ayq_vip = document.getElementById("ayq_vip");
    var tx_vip = document.getElementById("tx_vip");
    if(pkg==0){
        ayq_vip.style.display='none';
        document.getElementById("export_renewNum").value=0;
        tx_vip.style.display='';
        document.getElementById("tx_export_renewNum").value=12;
    }if(pkg==1){
        tx_vip.style.display='none';
        document.getElementById("tx_export_renewNum").value=0;
        ayq_vip.style.display='';
        document.getElementById("export_renewNum").value=12;
    }

}




