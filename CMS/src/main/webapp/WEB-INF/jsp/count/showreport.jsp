<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>制卡订单统计</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<style>
#boss_excel table{border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;background:#efefef;}
#boss_excel th,td{border-right:1px solid #888;border-bottom:1px solid #888;padding:5px 15px;}
#boss_excel th{font-weight:bold;background:#ccc;}
}
</style>
<script type="text/javascript">
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
	};
	$("#start").datebox();
	$("#end").datebox();
	 
});
function initDownLoad() {
	//	alert(1);
		var startTime = $('#start').datebox("getValue");
		var stopTime = $('#end').datebox("getValue");
		if(startTime==''||stopTime==''){
			$.messager.alert(titleInfo, '因数据量较大，导出excel，时间不能为空！');
			return;
		}
		$.ajax({
			url : "excel/bossExcel.json?start="+startTime+"&end="+stopTime+"&type=vip",
			async : true, // 
			type : "get",
			dataType : "json",
			
			success : function(data) {
				if (1 == data.code) {
					var excelUrl = data.msg;
//					sleep(1000);
					window.location.href = excelUrl;
				} else {
					$.messager.alert(titleInfo, '下载失败！');
				}
			}
		});
}
function submitForm(){
	var queryform = document.getElementById("queryForm"); 
	var startTime = $('#start').datebox("getValue");
	var stopTime = $('#end').datebox("getValue");
	if(startTime==''||stopTime==''){
		$.messager.alert(titleInfo, '因数据量较大，时间不能为空！');
		return;
	}
	queryform.submit();
}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'制卡订单统计'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="smsRe_toolbar">
			<form action="count/showReport.html" id="queryForm">
				<label>开始时间：	<input id="start" name="start" value="${start}"/> <label>
				<label>结束时间：	<input id="end" name="end" value="${end}"/> <label>
				<input type="button" id="query_smsR" class="btn btn-success" value="查询" onclick="submitForm();">
				<input type="button" id="excel_btn" class="btn btn-success" value="导出" onclick="initDownLoad();">
				<font color="red">先点查询，再导出页面结果</font>
			</form>
		</div>
		<table id="boss_excel">
			<c:forEach items="${list}" var ="boss" >
				<tr>
					<td>${boss.branchName}</td>
					<c:forEach items="${boss.map}" var="map">
					<td>
						${map.value.productName}
					</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
</body>
</html>