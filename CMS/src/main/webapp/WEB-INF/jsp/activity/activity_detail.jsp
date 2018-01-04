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
	<c:forEach var="list" items="${list}">
		activityDataList['${list.productName}']='${list.chargingStr}';
	</c:forEach>

</script>
<script type="text/javascript" src="js/activity/activity.js?v=${js_version}"></script>
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
	<div data-options="region:'center',title:'活动管理'" class="regionCenter">
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
				value="新增" <c:if test="${isBranchUser == 0}">
						style="display:none"
					</c:if>> 
				<label>活动卡状态：</label> <select id="status">
				<option value="-1">全部</option>
				<option value="1">上线</option>
				<option value="0">下线</option>
			</select> <label>活动卡名称：</label> <input id="activityName" type="text" /> <input
				type="button" id="query_activity" class="btn btn-success" value="查询">
		</div>
		<table id="activity_table"></table>

		<div id="activity_detail_dialog"
			data-options="closed:true,modal:true,title:'活动信息',iconCls:'icon-save'"
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
				<tr>
					<th>活动卡名称：</th>
					<td><input type="text" id="editActivityName"></td>
				</tr>
				<tr>
					<th>配置计费包：</th>
					<td><select id="pkg_list" onchange="select()">
							<option style="display: none">请选择</option>
							<c:forEach var="list" items="${list}">
								<option value="${list.chargingStr}">${list.productName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>促销卡价格：</th>
					<td id="price"></td>
				</tr>
				<tr>
					<th>状态：</th>
					<td><select id="editStatus">
							<option value="1">上线</option>
							<option value="0">下线</option>
					</select></td>
				</tr>
			</table>
		</div>
</body>
</html>