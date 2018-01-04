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
<script type="text/javascript">
	var isBranchUser =${isBranchUser};
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	var activityDataList =new Array();
</script>
<script type="text/javascript" src="js/activity/activityex.js?v=${js_version}"></script>
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
	<div data-options="region:'center',title:'计费包管理'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="activity_toolbar">
			<input type="button" id="add_activity" class="btn btn-success"
				value="新增计费包"> 
<!-- 				<label>计费包状态：</label> <select id="status"> -->
<!-- 				<option value="-1">全部</option> -->
<!-- 				<option value="1">上线</option> -->
<!-- 				<option value="0">下线</option> -->
<!-- 			</select> <label>计费包名称：</label> <input id="activityName" type="text" />  -->
			<input	type="button" id="query_activity"  value="查询" style="display: none">
		</div>
		<table id="activity_table"></table>

		<div id="activity_detail_dialog"
			data-options="closed:true,modal:true,title:'计费包信息',iconCls:'icon-save'"
			style="padding: 5px; width: 600px; height: 410px;">
			<input type="hidden" id="productId"> <input type="hidden"
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
				<tr>
					<th>计费包名称：</th>
					<td><input type="text" id="productName" placeholder="录入计费包名称，别太长" size=50 maxlength="50"></td>
				</tr>
				<tr>
					<th>计费包价格：</th>
					<td><input type="text" id="chargingPrice" placeholder="录入计费包名称，整数型" size=50 maxlength="6"></td>
				</tr>
				<tr>
					<th>计费包周期（月）：<br><font color="red">一个计费周期默认30天</font></th>
					<td><input type="text" id="productCycle" placeholder="录入计费包周期，整数型" size=50 maxlength="6"></td>
				</tr>
				<tr>
					<th>免费时长(天)：</th>
					<td><input type="text" id="productFreeDay" placeholder="录入免费时长，整数型" size=50 maxlength="6"></td>
				</tr>
				<tr>
					<th>状态：</th>
					<td><select id="flag">
							<option value="1">上线</option>
							<option value="0">下线</option>
					</select></td>
				</tr>
				<tr><td align="center" colspan="2"><font color="red">计费包录入后，只准上下线，不准修改</font></td></tr>
			</table>
		</div>
</body>
</html>