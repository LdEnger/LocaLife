var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
	$("#startTime").datebox();
	$("#stopTime").datebox();
	initAuth();
	initQuery();
	addSingle();
	addBatch();
	openImportCard();
	initDownLoad();
	initBatchActive();
	initBatchCancel();
	isBatch();
	initCardInfo();
//	initUploadExcel();
	
	
	// 初始化单个激活
	$('#activition_card_dialog').dialog({
		buttons : [ {
			text : '激活',
			handler : function() {
				actCard();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#activition_card_dialog').dialog('close');
			}
		} ]
	});
	
	// 初始化单个生成框
	$('#single_card_dialog').dialog({
		buttons : [ {
			text : '生成',
			handler : function() {
				submit_single_form();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#single_card_dialog').dialog('close');
			}
		} ]
	});
	
	// 初始化批量生成框
	$('#batch_card_dialog').dialog({
		buttons : [ {
			text : '生成',
			handler : function() {
				submit_batch_form();
			}
		}, {
			text : '取消',
			handler : function() {
				$('#batch_card_dialog').dialog('close');
			}
		} ]
	});
	
	$('#import_card_dialog').dialog({
		buttons : [ {
			text : '生成',
			handler : function() {
				submit_excel();
//				alert("暂时没用到这个按钮");
			}
		}, {
			text : '取消',
			handler : function() {
				$('#import_card_dialog').dialog('close');
			}
		} ]
	});
});

//初始化批量操作的权限视角
function isBatch(){
	var isBatch=$('#hidIsBatch').val();
	if(0==isBatch){
		document.getElementById('batch_active').style.display = 'none';
		document.getElementById('batch_cancel').style.display = 'none';
		document.getElementById('addBatch').style.display = 'none';
	}else{
		
	}
}

//复制文字
function copyStr(){ 
		var txt=document.getElementById("s_editActivationCode"); 
		txt.select(); // 选择对象 
		document.execCommand("Copy"); // 执行浏览器复制命令
		$.messager.alert(titleInfo,"已复制好，可贴粘");
}

//打开导入信息框
function openImportCard(){
	$('#import_btn').click(function(){
		initUploadExcel();
		$('#hiddenPath').val('');
		$('#import_activity').val('');
		$("#ImportFileName").text("上传的文件名显示在这里");
		$('#import_card_dialog').dialog('open');
	});
}

//初始化登入用户可看到的内容
function initAuth(){
		var roleId=$('#hidRoleId').val();
		var branchId=$('#hidBranchId').val();
		var hallId=$('#hidHallId').val();
		if(3==roleId){
			$('#branch').val(branchId);
			document.getElementById('branch').disabled = 'disabled';
//			initHall(branchId);
			$('#hall').val(hallId);
			document.getElementById('hall').disabled = 'disabled';
		}else if(2==roleId){
			$('#branch').val(branchId);
			initHall(branchId);
			document.getElementById('branch').disabled = 'disabled';
		}else{
			
		}
}

//初始化单个生成激活码
function addSingle(){
	$('#addSingle').click(function(){
		$('#s_editMac').val('');
		$('#s_editSn').val('');
		$('#s_editActivationCode').val('');
		$('#single_card_dialog').dialog('open');
	});
}

//初始化批量生成激活码
function addBatch(){
	$('#addBatch').click(function(){
		$('#b_count').val('');
		$('#batch_card_dialog').dialog('open');
	});
}

//初始化查询
function initQuery(){
	$('#query_card').click(function(){
		initCardInfo();
	});
}

// 初始化卡券信息
function initCardInfo() {
	parameter = {};
	var status = $('#status').val();
	if (status != -1) {
		parameter.status = status;
	}
	var activityId = $('#activity').val();
	if (activityId != -1) {
		parameter.activityId = activityId;
	}
	var hallId=$('#hall').val();    
	if (hallId != -1) {
		parameter.cardFromHall = hallId;
	}
	var branchId=$('#branch').val();    
	if (branchId != -1) {
		parameter.cardFromBranch = branchId;
	}
	var startTime=$('#startTime').datebox("getValue");  
	if (startTime != "") {
		parameter.createTime = startTime;
	}
	var uid=$('#uid').val();   
	if (uid != "") {
		if(!/^[0-9]*$/.test(uid)){
			parameter.uid =99999999;
		}else{
			parameter.uid =uid;
		}
	}
	var stopTime=$('#stopTime').datebox("getValue");  
	if (stopTime != "") {
		parameter.activationTime = stopTime;
	}
	var cardPwd=$('#cardPwd').val();   
	if (cardPwd != "") {
		parameter.activationCode = cardPwd;
	}
		parameter.terminalMac = $('#mac').val();
	$('#card_table').datagrid(
					{
						iconCls : 'icon-save',
						nowrap : true,
						autoRowHeight : false,
						striped : true,
						toolbar : '#card_toolbar',
						fit : true,
						fitColumns : true,
						collapsible : true,
						url : 'card/getList.json',
						checkOnSelect : false,
						queryParams : parameter,
						remoteSort : false,
						singleSelect : false,
						idField : 'id',
						columns : [ [
						 			{
									    field : 'ck',
									    checkbox : true
									},
								{
									field : 'uid',
									title : '用户ID',
//									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'cardFrom',
									title : '卡生成员',
//									width : 40,
									formatter : function(value, row, index) {
										if(row.cardFromBranch==null&&row.cardFromHall==null){
											return '集团管理员-'+row.creatorName;
										}else if(row.cardFromHall==null){
											return row.branchName+'-'+row.creatorName;
										}else{
											return row.branchName+'-'+row.hallName+'-'+row.creatorName;
										}
									}
								},
								{
									field : 'activityName',
									title : '活动名',
//									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'activationCode',
									title : '激活码',
//									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'id',
									title : '激活操作',
//									width : 40,
									formatter : function(value, row, index) {
										if(1==row.status){
											return '<a href="javascript:openActCard('+row.id+')">激活</a>';
										}else if(2==row.status){
											return '<a href="javascript:cancelCard('+row.id+')">注销</a>';
										}else{
											return '-';
										}
									}
								},
								{
									field : 'status',
									title : '卡状态',
//									width : 40,
									formatter : function(value, row, index) {
										if(1==value){
											return '未激活';
										}else if(2==value){
											return '已激活';
										}else if(3==value){
											return '激活失败';
										}else  if(4==value){
											return '已注销';
										}else if(5==value){
											return '已过期';
										}else{
											return '错误状态';
										}
									}
								},
								{
									field : 'createTime',
									title : '卡生成时间',
//									width : 40,
									formatter : function(value, row, index) {
										if(value !=null){
											var tt=new Date(value).toLocaleString(); 
											return tt; 
										}else {
										return value;
										}
									}
								},
								{
									field : 'activationTime',
									title : '激活时间',
//									width : 40,
									formatter : function(value, row, index) {
										if(value !=null){
											var tt=new Date(value).toLocaleString(); 
											return tt; 
										}else {
										return value;
										}
									}
								},
								{
									field : 'cancelTime',
									title : '注销时间',
//									width : 40,
									formatter : function(value, row, index) {
										if(value !=null){
											var tt=new Date(value).toLocaleString(); 
											return tt; 
										}else {
										return value;
										}
									}
								},
								{
									field : 'terminalMac',
									title : 'MAC',
//									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								} ,
								{
									field : 'terminalSn',
									title : 'SN',
//									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'effectiveTimeLength',
									title : '有效时长',
//									width : 40,
									formatter : function(value, row, index) {
										return value/86400;
									}
								}, 
								{
									field : 'cardOrderId',
									title : '订单号',
									hidden:true,
									formatter : function(value, row, index) {
										return value;
									}
								}, 
								{
									field : 'del',
									title : '操作',
//									width : 40,
									formatter : function(value, row, index) {
										if(2==row.status){
											return '已激活不能删除';
										}else{
											return '<a href="javascript:delCard('+row.id+')">删除</a>';
										}
									}
								}
								] ],
						pagination : true,
						fitColumns:false,
						rownumbers : true,
						onClickRow : function(rowIndex,row) {
						}
			});
}


//单个添加激活码
function submit_single_form(){
	var card={
			"activityId":$('#s_editActivity').val(),
			"activityName": $("#s_editActivity  option:selected").text(),
			"terminalMac":$('#s_editMac').val(),
			"terminalSn":$('#s_editSn').val(),
			"activationCode":$('#s_editActivationCode').val(),
			"creatorName":$('#hidUserName').val(),
			"cardFromHall":$('#hidHallId').val(),
			"hallName":$('#hidHallName').val(),
			"branchName":$('#hidBranchName').val(),
			"cardFromBranch":$('#hidBranchId').val()
	};
	$.post("card/add.json",card,function(data){
		if (data.code == 1) {
			$('#s_editActivationCode').val(data.msg);
			$.messager.show({title : titleInfo,msg : '已生成！',timeout : timeoutValue,showType : 'slide'});
			$('#card_table').datagrid('reload', parameter);
		} else {
			$.messager.alert(titleInfo, '添加失败！'+data.msg);
		}
	},"json");
	
}

//批量添加激活码
function submit_batch_form(){
	var card={
			"activityId":$('#b_editActivity').val(),
			"activityName": $("#b_editActivity  option:selected").text(),
			"creatorName":$('#hidUserName').val(),
			"cardFromHall":$('#hidHallId').val(),
			"hallName":$('#hidHallName').val(),
			"branchName":$('#hidBranchName').val(),
			"cardFromBranch":$('#hidBranchId').val()
	};
	
	//^\+?[1-9][0-9]*$          ^[1-9]\d*$
	var reg = new RegExp("^\\+?[1-9][0-9]*$");
	var count=$('#b_count').val();
    if(!reg.test(count)){
    	$.messager.alert(titleInfo, '生成数量请输入非零数字！');
    	return;
    }
	for(var i =0;i<count;i++){
		$.post("card/add.json",card,function(data){
			if (data.code == 1) {
				$('#batch_card_dialog').dialog('close');
				$.messager.show({title : titleInfo,msg : '添加成功！',timeout : timeoutValue,showType : 'slide'});
				$('#card_table').datagrid('reload', parameter);
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		},"json");
	}
}

//删除卡券，软删除
function delCard(id){
	$.messager.confirm('Warning', '您确定要删除?', function(r){
		   if (r){
			   var card={
					   "id":id,
					   "delStatus":1
			   };
			   $.post("card/update.json",card,function(data){
					if ( 1== data.code) {
						$.messager.show({title : titleInfo,msg : '删除成功！',timeout : timeoutValue,showType : 'slide'});
						$('#card_table').datagrid('reload', parameter);
					} else {
						$.messager.alert(titleInfo, '删除失败！');
					}
			   },"json");
		   }
	   });
}

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

//#######################################导入导出excel#############################

//导入excel
function initUploadExcel(){
//		$('#import_card').click(function(data){
			var import_button = $("#import_card");
			new AjaxUpload(
					import_button,{
						action : 'excel/uploadExcel.json',
						autoSubmit : true,
						name : 'file',//文件对象名称（不是文件名）
						data:{},
						onChange:function(file, extension){
							var d=/\.[^\.]+$/.exec(file); //文件后缀
							if(d!=".xls"&&d!=".xlsx"){
								$.messager.alert(titleInfo, '文件格式错误，请上传.xls或.xlsx格式！');
								return false;
							}else{
								$("#ImportFileName").text(file);
							}
						},
						onSubmit: function(file, extension){//在提交的时候触发
							$('#hiddenPath').val(file);
						},
						onComplete:function(file,response){
							$.messager.show({title : titleInfo,msg : '文件已选定！',timeout : timeoutValue,showType : 'slide'});
						}	
					});
//		});
};

function submit_excel(){
	var tmpData={
			"activityId":$("#import_activity").val(),
			"activityName":$("#import_activity  option:selected").text(),
			"excelPath":$('#hiddenPath').val()
	};
	$.post("excel/saveExcel.json",tmpData,function(data){
		if (1==data) {
			$('#import_card_dialog').dialog('close');
			$.messager.show({title : titleInfo,msg : '保存成功！',timeout : timeoutValue,showType : 'slide'});
			$('#card_table').datagrid('reload', parameter);
		} else {
			$.messager.alert(titleInfo, '保存失败！');
		}
	},"json");
}

//导出excel
var bol = false;
function initDownLoad(){
	$('#export_card').click(function(data){
		var myDate = new Date();  
		var excelName = "cardInfo_"+myDate.getTime();
		$.ajax({
			url : "excel/downloadExcel.json",
			async : true, // 
			type : "POST",
			dataType : "json",
			data:{"excelName":excelName},
			success : function(data) {
				if ( 1== data.code) {
					var excelUrl =  data.msg+excelName+".xls";
					window.location.href= excelUrl;
				} else {
					$.messager.alert(titleInfo, '下载失败！');
				}
			}
		});
	});
}

//################################卡券激活##############################

//打开激活框
function openActCard(id){
	clearDialog();
	initDialog(id);
//	$('#activition_card_dialog').dialog('open');
}

function clearDialog(){
	$('#a_id').val('');
	$('#a_userInfo').val('');
	$('#a_actCode').val('');
	$('#a_mac').val('');
	$('#a_sn').val('');
}

function initDialog(id){
//	$('#card_table').datagrid('unselectAll');//取消之前选定的行
//	$('#card_table').datagrid('selectRecord',id);
//	var row = $('#card_table').datagrid('getSelected');
	
	$.ajax({
		url : "card/getCardById.json",
		async : false, // 同步
		type : "POST",
		dataType : "json",
		data:{"id":id},
		success : function(data) {
                   $('#a_id').val(id);
                   $('#a_userInfo').val(data.card.uid);
                   $('#a_actCode').val(data.card.activationCode);
                   $('#a_mac').val(data.card.terminalMac);
                   $('#a_sn').val(data.card.terminalSn);
                   $('#activition_card_dialog').dialog('open');
		},
	});
	
}

//单个激活
function actCard(){
	var id=$('#a_id').val();
	var cardPwd =$('#a_actCode').val();
	var mac = $('#a_mac').val();
	var sn = $('#a_sn').val();
	if(mac==null||sn==null||mac==''||mac==''){
		$.messager.alert(titleInfo, '没有MAC或SN！');
		return;
	}
	//生成签名
	var partnerKey = $('#hidPartnerKey').val();
	var _params="cardPwd="+cardPwd+"&mac="+mac+"&sn="+sn;
	var tmp = _params + "&partnerKey=" + partnerKey;
	var sign = md5(tmp);
	_params = _params + "&sign=" + sign;
	$.messager.confirm('Warning', '您确定要激活?', function(r){
		   if (r){
				$.ajax({
					url : "card/activation.json",
					async : false, // 同步
					type : "POST",
					dataType : "json",
					data:_params,
					success : function(data) {
						if (data.code =="000") {
//							var card={
//									"id":id,
//									"status":2,
//									"cardOrderStatus":2,
//									"terminalMac":mac,
//									"terminalSn":sn
//							};
//							$.post("card/update.json",card,function(data){
//								if (data.code == 1) {
//									$('#activition_card_dialog').dialog('close');
//									$.messager.show({title : titleInfo,msg : '激活状态更改成功！',timeout : timeoutValue,showType : 'slide'});
//									$('#card_table').datagrid('reload', parameter);
//								} else {
//									$.messager.alert(titleInfo, '状态更改失败,but已经成功激活！');
//								}
//							},"json");
							
							$('#activition_card_dialog').dialog('close');
							$.messager.show({title : titleInfo,msg : '激活成功！',timeout : timeoutValue,showType : 'slide'});
							$('#card_table').datagrid('reload', parameter);
						} else {
							$.messager.alert(titleInfo,'激活失败:'+data.desc);
						}
					}
				});
		   }
	});
}

//初始化批量激活
function initBatchActive(){
	$('#batch_active').click(function(){
		var cardArray = $('#card_table').datagrid('getChecked');
		var length=cardArray.length;
		if(0==length){
			$.messager.alert(titleInfo, '您没有选中任何记录！');
		}else{
			$.messager.confirm('Warning', '您是否确认激活当前所选的'+length+'张卡?', function(r){
				if (r){
					for (var i = 0; i < length; i++) {
						var row =  cardArray[i];
						var cardPwd = row.activationCode;
						var mac = row.terminalMac;
						var sn = row.terminalSn;
						if(mac==null||sn==null){
							$.messager.alert(titleInfo, row.activationCode+'没有MAC或SN！');
							return;
						}
						//生成签名
						var partnerKey = $('#hidPartnerKey').val();
						var _params="cardPwd="+cardPwd+"&mac="+mac+"&sn="+sn;
						var tmp = _params + "&partnerKey=" + partnerKey;
						var sign = md5(tmp);
						_params = _params + "&sign=" + sign;
						$.ajax({
							url : "card/activation.json",
							async : false, // 同步
							type : "POST",
							dataType : "json",
							data:_params,
							success : function(data) {
								if (data.code =="000") {
//									var card={
//											"id":row.id,
//											"status":2,
//											"cardOrderStatus":2
//									};
//									$.post("card/update.json",card,function(data){
//										if (data.code == 1) {
//											$.messager.show({title : titleInfo,msg : '激活状态更改成功！',timeout : timeoutValue,showType : 'slide'});
//											$('#card_table').datagrid('reload', parameter);
//										} else {
//											$.messager.alert(titleInfo, row.activationCode+'状态更改失败,but已经成功激活！');
//										}
//									},"json");
									
									$.messager.show({title : titleInfo,msg : '激活成功！',timeout : timeoutValue,showType : 'slide'});
									$('#card_table').datagrid('reload', parameter);
								} else {
									$.messager.alert(titleInfo, row.activationCode+'激活失败:'+data.desc);
								}
							}
						});
					};
				}
			});
		}
	});
}

//################################卡券注销##############################
//单个注销卡券
function cancelCard(id){
//	 $('#card_table').datagrid('unselectAll');
//	 $('#card_table').datagrid('selectRecord',id);
//	var row =  $('#card_table').datagrid('getSelected');
	$.ajax({
		url : "card/getCardById.json",
		async : false, // 同步
		type : "POST",
		dataType : "json",
		data:{"id":id},
		success : function(data) {
               var userId = data.card.uid;
               var orderId = data.card.cardOrderId;
               if(userId==null||orderId==null){
            	   $.messager.alert(titleInfo, '没有用户ID或订单号！');
            	   return;
               }
               //生成签名
               var partnerKey = $('#hidPartnerKey').val();
               var _params="orderId="+orderId+"&userId="+userId;
               var tmp = _params + "&partnerKey=" + partnerKey;
               var sign = md5(tmp);
               _params = _params + "&sign=" + sign;
               $.messager.confirm('Warning', '您确定要注销?', function(r){
            	   if (r){
            		   $.ajax({
            			   url : "card/cancel.json",
            			   async : false, // 同步
            			   type : "POST",
            			   dataType : "json",
            			   data:_params,
            			   success : function(data) {
            				   if (data.code =="000") {
//            					   var card={
//            							   "id":id,
//            							   "status":4,
//            							   "cardOrderStatus":4
//            					   };
//            					   $.post("card/update.json",card,function(data){
//            						   if (data.code == 1) {
//            							   $.messager.show({title : titleInfo,msg : '已更改为注销状态！',timeout : timeoutValue,showType : 'slide'});
//            							   $('#card_table').datagrid('reload', parameter);
//            						   } else {
//            							   $.messager.alert(titleInfo, '状态更改失败，但是已经注销！');
//            						   }
//            					   },"json");
            					   
            					   $.messager.show({title : titleInfo,msg : '已注销！',timeout : timeoutValue,showType : 'slide'});
    							   $('#card_table').datagrid('reload', parameter);
            				   }else{
            					   $.messager.alert(titleInfo, '注销失败:'+data.desc);
            				   }
            			   }
            		   });
            	   }
               });
		},
	});
}

//初始化批量注销
function initBatchCancel(){
	$('#batch_cancel').click(function(){
		var cardArray = $('#card_table').datagrid('getChecked');
		var length=cardArray.length;
		if(0==length){
			$.messager.alert(titleInfo, '您没有选中任何记录！');
		}else{
			$.messager.confirm('Warning', '您是否确认注销当前所选的'+length+'张卡?', function(r){
				if (r){
					for (var i = 0; i < length; i++) {
						var row =  cardArray[i];
						var userId = row.uid;
						var orderId = row.cardOrderId;
						if(userId==null||orderId==null){
							$.messager.alert(titleInfo, row.activationCode+'没有用户ID或订单号！');
							return;
						}
						//生成签名
						var partnerKey = $('#hidPartnerKey').val();
						var _params="orderId="+orderId+"&userId="+userId;
						var tmp = _params + "&partnerKey=" + partnerKey;
						var sign = md5(tmp);
						_params = _params + "&sign=" + sign;
						$.ajax({
							url : "card/cancel.json",
							async : false, // 同步
							type : "POST",
							dataType : "json",
							data:_params,
							success : function(data) {
								if (data.code =="000") {
//									var card={
//										"id":row.id,
//										"status":4,
//										"cardOrderStatus":4
//									};
//									$.post("card/update.json",card,function(data){
//										if (data.code == 1) {
//											$.messager.show({title : titleInfo,msg : '已更改为注销状态！',timeout : timeoutValue,showType : 'slide'});
//											$('#card_table').datagrid('reload', parameter);
//										} else {
//											$.messager.alert(titleInfo, '注销状态更改失败，但是已经注销！');
//										}
//									},"json");
									
									$.messager.show({title : titleInfo,msg : '已注销！',timeout : timeoutValue,showType : 'slide'});
									$('#card_table').datagrid('reload', parameter);
								}else{
										$.messager.alert(titleInfo, row.activationCode+'注销失败:'+data.desc);
								}
							}
						});
					};
				}
			});
		}
	});
}

