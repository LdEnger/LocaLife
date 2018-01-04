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
<title>短信模板配置</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/sms/smsconfList.js?v=${js_version}"></script>
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
	<div data-options="region:'center',title:'短信模板配置'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="canedit" value="${canedit}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="smsco_toolbar" >
			<div <c:if test="${currentUser.roleId == 2}">
						style="display:none"
					</c:if>>
			<select id="q_sender">
				<option value="-2">请选择</option>
					<c:forEach items="${branchList}" var="zL" varStatus="st">
						<option value="${zL.id}">${zL.branchName}</option>
					</c:forEach>
			</select>
			<input type="button" id="query_smsR" class="btn btn-success" value="查询" >
			<font color=red>请选择分公司查询后对记录进行修改</font>
			</div>
		</div>
		<table id="smsco_table"></table>
</body>
</html>