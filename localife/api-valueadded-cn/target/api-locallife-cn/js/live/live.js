var parameter = {};

$(function() {
	
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		if(d<10){
			d="0"+d;
		}
		if(m<10){
			m="0"+m;
		}
		return y+'-'+m+'-'+d;
	}
	
	$("#startTime").datebox();
	$("#stopTime").datebox();
	initLiveInfo();
	initQuery();
	openLive();
	
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
				$('#single_live_dialog').dialog('close');
			}
		} ]
	});
	
});

//初始化营业厅关联
function initHall(branchId){
	var hall={
			"branchId":branchId
	};
	$.post("hall/getHallList.json",hall,function(data){
		$("#hall").html("<option value='-1'>全部</option>");
		$.each(data,function(dataIndex,obj){
			$("#hall").append('<option value='+obj.id+'>'+obj.hallName+'</option>');
		});
	},"json");
}



//初始化查询
function initQuery(){
	$('#query_live').click(function(){
		initLiveInfo();
	});
}

//初始直播查询信息
function initLiveInfo() {
	parameter = {};
	var uid=$('#uid').val();   
	if (uid != "") {
		if(!/^[0-9]*$/.test(uid)){
			parameter.uid =99999999;
		}else{
			parameter.uid =uid;
		}
	}
	var startTime=$('#startTime').datebox("getValue");  
	if (startTime != "") {
		parameter.submitTime = startTime;
	}
	var stopTime=$('#stopTime').datebox("getValue");  
	if (stopTime != "") {
		parameter.openTime = stopTime;
	}
	var mac =  $('#mac').val();
	if (mac != "") {
		parameter.mac = mac;
	}
	var sn =  $('#sn').val();
	if (sn != "") {
		parameter.sn = sn;
	}
	var status =  $('#status').val();
	if (status != "") {
		parameter.status = status;
	}
	
	//非集团用户查询不能选择区域或者分公司
	var currentUserRoleId = $('#currentUserRoleId').val();
	var branchId;
	var hallId;
	if(currentUserRoleId!=1){
		branchId = $('#currentUserBranchId').val();
		hallId = $('#currentUserHallId').val();  
	}else{
		branchId=$('#branch').val();    
		hallId=$('#hall').val();    
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
			checkOnSelect : false,
			queryParams : parameter,
			remoteSort : false,
			singleSelect : false,
			idField : 'id',
			columns : [ [
			             {
			            	 field : 'liveOrderId',
			            	 title : '直播流订购号',
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'statusName',
			            	 title : '订单状态',
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'productId',
			            	 title : '产品ID',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'productName',
			            	 title : '产品名称',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'chargingId',
			            	 title : '计费ID',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'chargingName',
			            	 title : '计费名称',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'chargingPrice',
			            	 title : '计费价格',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'chargingDuration',
			            	 title : '计费时长',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'submitTime',
			            	 title : '提交时间',
//			            	 width : 40,
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
			            	 field : 'openuid',
			            	 title : '开通人员ID',
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             }, 
			             {
			            	 field : 'openProvinceName',
			            	 title : '开通服务所在省',
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             }, 
			             {
			            	 field : 'openCityName',
			            	 title : '开通服务所在市',
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
			            	 hidden:true,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'mac',
			            	 title : 'MAC',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             } ,
			             {
			            	 field : 'sn',
			            	 title : 'SN',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             },
			             {
			            	 field : 'devicecode',
			            	 title : '设备码',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             }/*, 
			             {
			            	 field : 'del',
			            	 title : '操作',
//			            	 width : 40,
			            	 formatter : function(value, row, index) {
			            		 return value;
			            	 }
			             }*/
			             ] ],
				pagination : true,
				fitColumns:false,
				rownumbers : true,
				onClickRow : function(rowIndex,row) {
				}
	});
}


function openLive(){
	$('#openLive').click(function(){
		$('#s_editMac').val('');
		$('#s_editSn').val('');
		$('#single_live_dialog').dialog('open');
	});
}


//单个开通直播
function submit_single_form(){
	var live={
			"mac":$('#s_editMac').val(),
			"sn":$('#s_editSn').val(),
			"openuid":$('#currentUserId').val(),
			"productId":$('#s_editLive').val()
	};
	$.post("live/open.json",live,function(data){
		if (data.code == "000") {
			$('#single_live_dialog').dialog('close');
			$.messager.show({title : titleInfo,msg : '开通成功！',timeout : timeoutValue,showType : 'slide'});
			$('#live_table').datagrid('reload', parameter);
		} else {
			$.messager.alert(titleInfo, '开通失败！'+data.desc);
		}
	},"json");
	
}