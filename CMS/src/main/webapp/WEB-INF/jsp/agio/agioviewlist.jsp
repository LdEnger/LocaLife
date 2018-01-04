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
<title>分公司充值余额</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	
	var userListData=new Array();
	<c:forEach items="${userList}" var="zL" varStatus="st">
		userListData["${zL.userId}"]="${zL.userName}";
	</c:forEach>
	
	var agioListData =new Array();
	<c:forEach items="${agioList}" var="zL" varStatus="st">
		agioListData["${zL.id}"]="${zL.agioName}";
	</c:forEach>
	
	var branchListData=new Array();
	<c:forEach items="${branchList}" var="zL" varStatus="st">
		branchListData["${zL.id}"]="${zL.branchName}";
	</c:forEach>

</script>
<script type="text/javascript" src="js/agio/agiopackageview.js?v=${js_version}"></script>
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
	<div data-options="region:'center',title:'分公司充值余额'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="agio_package_conf_toolbar">
		</div>
		<table id="agio_package_conf_table"></table>

		<div id="agio_package_conf_dialog"
			data-options="closed:true,modal:true,title:'分公司充值界面',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 340px;">
			<input type="hidden" id="id"> 
			<input type="hidden" id="hiddenUserName" value="${currentUser.userName}"> <input
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
				<tr><td colspan="2"><font color=red>提示信息：录入一定要仔细检查，保存之后不可修改</font></td></tr>
				<tr><td>操作人员</td><td>${currentUser.userName}(${currentUser.userId})</td></tr>
				<tr><td>充值时间</td><td><script type="text/javascript">
				var showdate =new Date();
				var month =showdate.getMonth()+1;
				var showdatestr =showdate.getFullYear()+"-"+month+"-"+showdate.getDay();
				document.write(showdatestr);
				</script></td></tr>
				<tr>
					<th>充值金额：</th>
					<td><input type="text" id="amount" maxlength="8"></td>
				</tr>
				<tr>
					<th>战区：</th>
					<td><select id="zondId" onchange="javascript:initQueryBranch(this.value);">
						<option value="-1">全部</option>
						<c:forEach items="${zoneList}" var="zL" varStatus="st">
							<option value="${zL.id}">${zL.zoneName}</option>
						</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>分公司：</th>
					<td><select id="branchId"></select></td>
				</tr>
				<tr>
					<th>折扣名称：</th>
					<td><select id="packageConfId">
						<c:forEach items="${agioList}" var="zL" varStatus="st">
							<option value="${zL.id}">${zL.agioName}-${zL.agioValue}</option>
						</c:forEach>
					</select></td>
				</tr>
			</table>
		</div>
</body>
</html>