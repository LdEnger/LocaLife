var parameter = {};

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
	}

	$("#startTime").datebox();
	$("#stopTime").datebox();
	initLiveInfo();
	initQuery();
	openLive();
	initRenewLive();
	initRefundLive();
});

// 初始化营业厅关联
function initHall(branchId) {
	var hall = {
		"branchId" : branchId
	};
	$.post("hall/getHallList.json", hall, function(data) {
		$("#hall").html("<option value='-1'>全部</option>");
		$.each(data, function(dataIndex, obj) {
			$("#hall").append('<option value=' + obj.id + '>' + obj.hallName + '</option>');
		});
	}, "json");
}

// 初始化查询
function initQuery() {
	$('#query_live').click(function() {
		$('#live_table').datagrid('unselectAll');
		initLiveInfo();
	});
}

// 初始直播查询信息
function initLiveInfo() {
	parameter = {};
	var uid = $('#uid').val();
	if (uid != "") {
		if (!/^[0-9]*$/.test(uid)) {
			parameter.uid = 99999999;
		} else {
			parameter.uid = uid;
		}
	}
	var startTime = $('#startTime').datebox("getValue");
	if (startTime != "") {
		parameter.submitTime = startTime;
	}
	var stopTime = $('#stopTime').datebox("getValue");
	if (stopTime != "") {
		parameter.openTime = stopTime;
	}
	var mac = $('#mac').val();
	if (mac != "") {
		parameter.mac = mac;
	}
	var sn = $('#sn').val();
	if (sn != "") {
		parameter.sn = sn;
	}
	var status = $('#status').val();
	if (status != "") {
		parameter.status = status;
	}
	var openname = $('#openname').val();
	if (openname != "") {
		parameter.openname = openname;
	}
	var orderType = $('#orderType').val();
	if (orderType != -1) {
		parameter.orderType = orderType;
	}
	// 非集团用户查询不能选择区域或者分公司
	var currentUserRoleId = $('#currentUserRoleId').val();
	var branchId;
	var hallId;
	if (currentUserRoleId != 1) {
		branchId = $('#currentUserBranchId').val();
		hallId = $('#currentUserHallId').val();
	} else {
		branchId = $('#branch').val();
		hallId = $('#hall').val();
	}
	if (branchId != -1) {
		parameter.branchId = branchId;
	}
	if (hallId != -1) {
		parameter.hallId = hallId;
	}
	$('#live_table').datagrid(
			{
				iconCls : 'icon-save',
				nowrap : true,
				autoRowHeight : false,
				striped : true,
				toolbar : '#live_toolbar',
				fit : true,
				fitColumns : true,
				collapsible : true,
				url : 'live/getLiveList.json',
				checkOnSelect : true,
				singleSelect :true,
				queryParams : parameter,
				remoteSort : false,
				idField : 'id',
				columns : [ [ {
						field : 'id',
						checkbox : true
						},
						{
							field : 'liveOrderId',
							title : '直播单号',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'uid',
							title : '用户ID',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'uname',
							title : '用户名称',
							hidden : true,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'mac',
							title : 'MAC',
							// width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'sn',
							title : 'SN',
							// width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'devicecode',
							title : '设备码',
							// width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'productName',
							title : '产品名称',
							// width : 40,
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'statusName',
							title : '开通状态',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'orderTypeName',
							title : '订单类型',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'overdueStatus',
							title : '过期状态',
							formatter : function(value, row, index) {
					            if(row.status != 2){
					            	return "无效";
					            }
					            var closeTime = new Date(Date.parse(row.closeTime));  
					            var sysTime = new Date(Date.parse(new Date()));  
								if(sysTime > closeTime){
									return "已过期";
								}else{
									return "未过期";
								}
							}
						},
						{
							field : 'openProvinceName',
							title : '省份',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'openCityName',
							title : '市',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'branchName',
							title : '分公司',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'openname',
							title : '开通人员',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'submitTime',
							title : '提交时间',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'openTime',
							title : '开通时间',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'closeTime',
							title : '关闭时间',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'lastUser',
							title : '退订人员',
							formatter : function(value, row, index) {
								return value;
							}
						},
						{
							field : 'lastTime',
							title : '退订时间',
							formatter : function(value, row, index) {
								return value;
							}
						}//,
//						{
//							field : 'id',
//							title : '操作',
//							// width : 40,
//							formatter : function(value, row, index) {
//								var str = '';
//								if (row.status != 2) {
//									str = '<a href="javascript:queryStatus(\''
//											+ row.liveOrderId
//											+ '\')">查询开通状态</a> ';
//								}
//								return str;// + '<a
//								// href="javascript:authEdit('+value+')">续费</a>
//								// <a
//								// href="javascript:authEdit('+value+')">关闭</a>';
//							}
//						} 
						] ],
				pagination : true,
				fitColumns : false,
				rownumbers : true,
				onClickRow : function(rowIndex, row) {
				}
			});
}
function openLive() {
	$('#openLive').click(function() {
		$('#single_mac').val('');
		$('#single_sn').val('');
		$('#single_endTime').val('');
		$('#single_year').val(0);
		$('#single_month').val(0);
		$('#single_day').val(0);
		$('#single_branchName').combobox({    
		    url:'live/getBranchList.json',   
		    valueField:'id',    
		    textField:'branchName',
		    editable : false
		}); 
		$('#single_productId').combobox({    
		    url:'live/getProductList.json',   
		    valueField:'productId',    
		    textField:'productName',
		    editable : false
		}); 
		// 初始化单个开通直播
		$('#single_live_dialog').dialog({
			buttons : [ {
				text : '开通',
				handler : function() {
					submit_single_form();
				}
			}, {
				text : '关闭',
				handler : function() {
					$('#live_table').datagrid('unselectAll');
					$('#single_live_dialog').dialog('close');
				}
			} ]
		});
		$('#single_live_dialog').dialog('open');
	});
}
//单个开通直播
function submit_single_form() {
	var branchName = $('#single_branchName').combobox('getValue');
	if(branchName == null || branchName == ''){
		$.messager.alert(titleInfo, '请选择分公司');
		return;
	}
	var productId = $('#single_productId').combobox('getValue');
	var productName = $('#single_productId').combobox('getText');
	if(productId == null || productId == '' || productName == null || productName ==''){
		$.messager.alert(titleInfo, '请选择产品包');
		return;
	}
	var endTime = $('#single_endTime').val();
	if(endTime == null || endTime == ''){
		$.messager.alert(titleInfo, '结束日期不能为空');
		return;
	}
	var year = $('#single_year').val();
	var month = $('#single_month').val();
	var day = $('#single_day').val();
	if((year == null || year == 0) && (month == null || month == 0) && (day == null || day == 0) ){
		$.messager.alert(titleInfo, '开通时长不能为零');
		return;
	}
	var mac = $('#single_mac').val();
	var sn = $('#single_sn').val();
	if (mac == null || mac == '' || sn == null || sn == '') {
		$.messager.alert(titleInfo, 'MAC/SN不能为空');
		return;
	}
	var live = {
		"productId" : productId,
		"productName" : productName,
		"closeTime" : endTime,
		"chargingDurationYear" : year,
		"chargingDurationMonth" : month,
		"chargingDurationDay" : day,
		"mac" : mac,
		"sn" : sn
	};
	var roleId =  $('#currentUserRoleId').val();
	if(1 == roleId){
		live.branchId = $('#single_branchName').combobox('getValue');
		live.branchName = $('#single_branchName').combobox('getText');
	}
	loadDiv('正在开通，请稍后...');
	$.post("live/open.json", live, function(data) {
		if (data.code == "000") {
			$('#single_live_dialog').dialog('close');
			$.messager.show({
				title : titleInfo,
				msg : '开通成功！',
				timeout : timeoutValue,
				showType : 'slide'
			});
			displayLoad();
			$('#live_table').datagrid('unselectAll');
			$('#live_table').datagrid('reload', parameter);
		} else {
			displayLoad();
			$.messager.alert(titleInfo, '开通失败:' + data.desc);
		}
	}, "json").error(function(){
		displayLoad();
		$.messager.alert(titleInfo, '系统错误，请稍后重试！');
    });
}
function initRenewLive() {
	$('#renew_live_dialog').dialog({
		buttons : [ {
			text : '续费',
			handler : function() {
				renew_open_live();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#live_table').datagrid('unselectAll');
				$('#renew_live_dialog').dialog('close');
			}
		} ]
	});
	$('#renewLive').click(function() {
		$('#renew_endTime').val('');
		$('#renew_year').val(0);
		$('#renew_month').val(0);
		$('#renew_day').val(0);
		var row = $('#live_table').datagrid('getChecked');
		var length = row.length;
		if (1 != length) {
			$.messager.alert(titleInfo, '请选择一条数据！');
			return;
		}
		var status = row[0].status;
		if(status != 2 ){
			$.messager.alert(titleInfo, '未开通成功订单不能续费');
			return;
		}
		var closeTime = new Date(Date.parse(row[0].closeTime));  
        var sysTime = new Date(Date.parse(new Date()));  
		if(sysTime > closeTime){
			$.messager.alert(titleInfo, '已过期订单不能续费');
			return;
		}
		$.each(row, function(index, item){
			$('#renew_liveOrderId').val(item.liveOrderId);
			$('#renew_uid').val(item.uid);
			$('#renew_mac').val(item.mac);
			$('#renew_sn').val(item.sn);
			$('#renew_branchId').val(item.branchId);
			$('#renew_branchName').val(item.branchName);
			$('#renew_productName').val(item.productName);
			$('#renew_productId').val(item.productId);
			$('#renew_openTime').val(item.openTime);
			$('#renew_closeTime').val(item.closeTime);
		});
		
		$('#renew_live_dialog').dialog('open');
	})
}
function renew_open_live(){
	var mac = $('#renew_mac').val();
	var sn = $('#renew_sn').val();
	if (mac == null || mac == '' || sn == null || sn == '') {
		$.messager.alert(titleInfo, 'MAC/SN不能为空');
		return;
	}
	var year = $('#renew_year').val();
	var month = $('#renew_month').val();
	var day = $('#renew_day').val();
	if((year == null || year == 0) && (month == null || month == 0) && (day == null || day == 0) ){
		$.messager.alert(titleInfo, '开通时长不能为零');
		return;
	}
//	var endTime = $('#renew_endTime').val();
//	if(endTime == null || endTime == '' ){
//		$.messager.alert(titleInfo, '结束日期不能为空');
//		return;
//	}
	var liveOrderId = $('#renew_liveOrderId').val();
	var live = {
		"mac" : mac,
		"sn" : sn,
		"productId" : $('#renew_productId').val(),
		"productName" : $('#renew_productName').val(),
		"branchId" : $('#renew_branchId').val(),
		//"closeTime" : endTime,
		"chargingDurationYear" : $('#renew_year').val(),
		"chargingDurationMonth" : $('#renew_month').val(),
		"chargingDurationDay" : $('#renew_day').val()
	};
	loadDiv('正在续费，请稍后...');
	$.post("live/renewLive.json", live, function(data) {
		if (data.code == "000") {
			$('#renew_live_dialog').dialog('close');
			$.messager.show({title : titleInfo,	msg : '续费成功！',	timeout : timeoutValue,	showType : 'slide'});
			displayLoad();
			$('#live_table').datagrid('unselectAll');
			$('#live_table').datagrid('reload', parameter);
		} else {
			displayLoad();
			$.messager.alert(titleInfo, '续费失败:' + data.desc);
		}
	}, "json").error(function(){
		displayLoad();
		$.messager.alert(titleInfo, '系统错误，请稍后重试！');
    });
}

function initRefundLive() {
	$('#refund_live_dialog').dialog({
		buttons : [ {
			text : '退订',
			handler : function() {
				refund_live();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#live_table').datagrid('unselectAll');
				$('#refund_live_dialog').dialog('close');
			}
		} ]
	});
	$('#refundLive').click(function() {
		var row = $('#live_table').datagrid('getChecked');
		var length = row.length;
		if (1 != length) {
			$.messager.alert(titleInfo, '请选择一条数据！');
			return;
		}
		var status = row[0].status;
		if(status != 2 ){
			$.messager.alert(titleInfo, '非已开通订单不能退订');
			return;
		}
		$.each(row, function(index, item){
			$('#refund_liveOrderId').val(item.liveOrderId);
			$('#refund_uid').val(item.uid);
			$('#refund_mac').val(item.mac);
			$('#refund_sn').val(item.sn);
			$('#refund_branchName').val(item.branchName);
			$('#refund_productName').val(item.productName);
			$('#refund_productId').val(item.productId);
			$('#refund_openTime').val(item.openTime);
			$('#refund_closeTime').val(item.closeTime);
			$('#refund_orderType').val(item.orderType);
			$('#refund_orderTypeName').val(item.orderTypeName);
			$('#refund_status').val(item.status);
		});
		$('#refund_live_dialog').dialog('open');
	})
}
function refund_live(){
	var liveOrderId = $('#refund_liveOrderId').val();
	if (liveOrderId == null || liveOrderId == '') {
		$.messager.alert(titleInfo, '订单号为空，请重试');
		return;
	}
	var orderType = $('#refund_orderType').val();
	if (orderType == null || orderType == '') {
		$.messager.alert(titleInfo, '订单类型为空，请重试');
		return;
	}
	var live = {
		"liveOrderId" : liveOrderId,
		"orderType" : orderType,
	};
	loadDiv('正在退订，请稍后...');
	$.post("live/refundLive.json", live, function(data) {
		if (data.code == "000") {
			$('#refund_live_dialog').dialog('close');
			$.messager.show({title : titleInfo,	msg : '退订成功！',	timeout : timeoutValue,	showType : 'slide'});
			displayLoad();
			$('#live_table').datagrid('unselectAll');
			$('#live_table').datagrid('reload', parameter);
		} else {
			displayLoad();
			$.messager.alert(titleInfo, '退订失败:' + data.desc);
		}
	}, "json").error(function(){
		displayLoad();
		$.messager.alert(titleInfo, '系统错误，请稍后重试！');
    });
}
function singleComputeTimeDifference(){
	var endTime = $('#single_endTime').val();
	$.ajax({
		url : "live/computeTimeDifference.json",
		async : true, 
		type : "POST",
		dataType : "json",
		data:{"endTime":endTime},
		success : function(data) {
			if (000== data.code) {
				var date = data.desc;
				$('#single_year').val(date.substring(0, date.indexOf("年")));
				$('#single_month').val(date.substring(date.indexOf("年")+1,date.indexOf("月") ));
				$('#single_day').val(date.substring(date.indexOf("月")+1, date.indexOf("天")));
			} else {
				$.messager.alert(titleInfo, data.desc);
			}
		}
	});
}
//计算年月日
function renewComputeTimeDifference(){
	var endTime = $('#renew_endTime').val();
	$.ajax({
		url : "live/computeTimeDifference.json",
		async : true, 
		type : "POST",
		dataType : "json",
		data:{"endTime":endTime},
		success : function(data) {
			if (000== data.code) {
				var date = data.desc;
				$('#renew_year').val(date.substring(0, date.indexOf("年")));
				$('#renew_month').val(date.substring(date.indexOf("年")+1,date.indexOf("月") ));
				$('#renew_day').val(date.substring(date.indexOf("月")+1, date.indexOf("天")));
			} else {
				$.messager.alert(titleInfo, data.desc);
			}
		}
	});
}
function getYear(type){
	var year = $("#"+type+"year").val();
	var month = $("#"+type+"month").val();
	var day = $("#"+type+"day").val();
	getEndDate(year,month,day,type)
}
function getMonth(type){
	var year = $("#"+type+"year").val();
	var month = $("#"+type+"month").val();
	var day = $("#"+type+"day").val();
	getEndDate(year,month,day,type)
}
function getDay(type){
	var year = $("#"+type+"year").val();
	var month = $("#"+type+"month").val();
	var day = $("#"+type+"day").val();
	getEndDate(year,month,day,type)
}
//通过年月日推算结束日期
function getEndDate(year,month,day,id){
	$.ajax({
		url : "live/computeEndDate.json",
		async : true, 
		type : "POST",
		dataType : "json",
		data:{"year":year,"month":month,"day":day},
		success : function(data) {
			if (000== data.code) {
				var date = data.desc;
				$('#'+id+'endTime').val(date);
			} else {
				$.messager.alert(titleInfo, data.desc);
			}
		}
	});
}
function queryStatus(liveOrderId) {
	var live = {
		"liveOrderId" : liveOrderId
	}
	$.post("live/queryStatus.json", live, function(data) {
		$.messager.show({
			title : titleInfo,
			msg : data.desc,
			timeout : timeoutValue,
			showType : 'slide'
		});
		if (data.code == "000") {
			$.messager.show({
				title : titleInfo,
				msg : data.desc,
				timeout : timeoutValue,
				showType : 'slide'
			});
		} else {
			$.messager.show({
				title : titleInfo,
				msg : data.desc,
				timeout : timeoutValue,
				showType : 'slide'
			});
		}
		$('#live_table').datagrid('unselectAll');
		$('#live_table').datagrid('reload', parameter);
	}, "json");
}