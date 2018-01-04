var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;

//-----------------------------------
//--------查询参与用户-------------------
//-----------------------------------
//初始化参与用户
function initAwardPlayInfo() {
	parameter = {};
	/*var status = $('#status').val();
	var activityName = $('#activityName').val();
	if (status != -1) {
		parameter.status = status;
	}
	if (activityName != '') {
		parameter.activityName = activityName;
	}*/
	/*
	 * hll:增加源码
	 */
	var activityId=$('#activityId').val();
	var userName=$('#userName').val();
	var userPhone=$('#userPhone').val();
	var mac=$('#mac').val();
	var acceptFlag=$('#acceptFlag').val();
	if(!activityId){
		return;
	}
	parameter.activityId=activityId;
	if(userName){
		parameter.userName=userName;
	}
	if(userPhone){
		parameter.userPhone=userPhone;
	}
	if(mac){
		parameter.mac=mac;
	}
	if(acceptFlag){
		parameter.acceptFlag=acceptFlag;
	}
	$('#award_play_table')
			.datagrid(
					{
						iconCls : 'icon-save',
						nowrap : true,
						autoRowHeight : false,
						striped : true,
						toolbar : '#award_toolbar',
						fit : true,
						fitColumns : true,
						collapsible : true,
						url : 'awardPlay/getPlayList.json',
						queryParams : parameter,
						remoteSort : false,
						singleSelect : true,
						idField : 'id',
						columns : [ [
								{field : 'userName',title : '用户名',width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{field : 'mac',title : '大麦通行证',width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{field : 'realName',title : '姓名',width : 40,hidden : false,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{field : 'userPhone',title : '手机号',width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{field : 'playTime',title : '参与时间',width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{field : 'acceptFlag',title : '是否中奖',width : 40,
									formatter : function(value, row, index) {
										if(value==1){
											return "中奖";
										}else{
											return "未中奖";
										}
									}
								}] ],
						pagination : true,
						rownumbers : true,
						onClickRow : function(rowIndex) {
						}
					});
}
function queryAwardPlay() {
	$('#query_award').click(function() {
		initAwardPlayInfo();
	});
}
//-----------------------------------
//--------导出excle-------------------
//-----------------------------------
function initDownLoad(id){
	/*hll:注释源码
	$('#export_award_player').click(function(data){
	*/
		var myDate = new Date();  
		var excelName = "awardPlayerInfo_"+myDate.getTime();
		$.ajax({
			url : "excel/exportUserExcel.json",
			async : true, // 
			type : "POST",
			dataType : "json",
			data:{"excelName":excelName,"id":id},
			success : function(data) {
				if ( 1== data.code) {
					var excelUrl =  data.msg+excelName+".xls";
					window.location.href= excelUrl;
				} else {
					$.messager.alert(titleInfo, '下载失败！');
				}
			}
		});
	/*hll:注释源码
	});
	*/
}
$(function() {
	queryAwardPlay();
	initAwardPlayInfo();
	/*hll:注释源码
	addAwardActivity();
	*/
});