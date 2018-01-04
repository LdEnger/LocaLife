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
<title>折扣名称管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/agio/agiopackageconf.js?v=${js_version}"></script>
<style>
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'折扣名称管理'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="agio_package_conf_toolbar">
			<input type="button" id="add_agio_package_conf" class="btn btn-success" value="新增"> 
			<input type="button" id="query_agio_package_conf" class="btn btn-success" value="查询">
		</div>
		<table id="agio_package_conf_table"></table>

		<div id="agio_package_conf_dialog"
			data-options="closed:true,modal:true,title:'折扣配置信息',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 340px;">
			<input type="hidden" id="id"> <input type="hidden"
				id="hiddenUserName" value="${currentUser.userName}"> <input
				type="hidden" id="hiddenUserId" value="${currentUser.userId}">
			<input type="hidden" id="hiddenRoleId" value="${currentUser.roleId}">
			<input type="hidden" id="hiddenProductId" value=""> <input
				type="hidden" id="hiddenProductName" value=""> <input
				type="hidden" id="hiddenChargingId" value=""> <input
				type="hidden" id="hiddenChargingName" value=""> <input
				type="hidden" id="hiddenPrice" value=""> <input
				type="hidden" id="hiddenChargingDuration" value=""> <input
				type="hidden" id="hiddenChargingPic" value="">
			<table class=tableC3>
				<tr><td colspan="2"><font color=red>折扣信息提交正式生效，若确认以后不改，请点提交按钮进行提交<br>点保存只修改，不生效</font></td></tr>
				<tr>
					<th>折扣配置名称：</th>
					<td><input type="text" id="agioName"></td>
				</tr>
				<tr>
					<th>折扣配置值：</th>
					<td><input type="text" id="agioValue"></td>
				</tr>
			</table>
		</div>
</body>
</html>