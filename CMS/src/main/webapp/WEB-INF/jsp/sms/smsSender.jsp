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
<title>发短信配置</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/sms/smsSender.js?v=${js_version}"></script>
<style>
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
</style>
<script type="text/javascript">
	
	var branchListData=new Array();
	<c:forEach items="${branchList}" var="zL" varStatus="st">
		branchListData["${zL.id}"]="${zL.branchName}";
	</c:forEach>

</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'发短信配置'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="smsRe_toolbar">
<!-- 			<select id="q_sender" class="easyui-combobox" style="display:none"> -->
			<select id="q_sender" style="display:none">
				<option value="">全部</option>
					<c:forEach items="${branchList}" var="zL" varStatus="st">
						<option value="${zL.id}">${zL.branchName}</option>
					</c:forEach>
			</select>
			<input type="button" id="query_smsR" class="btn btn-success" value="查询" style="display:none">
			<input type="button" id="addsender" class="btn btn-success" value="新增" >
		</div>
		<table id="smsRe_table"></table>
		
		<div id="testSenderDiv"
			data-options="closed:true,modal:true,title:'测试发短信',iconCls:'icon-save'"
			style="padding: 5px; width: 400px; height: 240px;">
			<table class=tableC3>
				<tr>
					<th>手机号码：</th>
					<td><input type="text" id="testPhoneNo">
						<input type="hidden" id="testSenderId" >
					</td>
				</tr>
				<tr>
					<td colspan="2"><b>测试发短信也花钱，请慎重！</b></td>
				</tr>
			</table>
		</div>
		<div id="daoSenderDiv"
			data-options="closed:true,modal:true,title:'添加短信发送配置',iconCls:'icon-save'"
			style="padding: 5px; width: 450px; height: 340px;">
			<input type="hidden" id="id"> <input type="hidden"
				id="hiddenUserName" value="${currentUser.userName}"> <input
				type="hidden" id="hiddenUserId" value="${currentUser.userId}">
			<input type="hidden" id="hiddenRoleId" value="${currentUser.roleId}">
			<table class=tableC3>
				<tr>
					<th>分公司：</th>
					<td>
					<select id="branchId"> 
						<c:forEach items="${branchList}" var="zL" varStatus="st">
							<option value="${zL.id}">${zL.branchName}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<th>接口地址：</th>
					<td>
						<input type="text" id ="sender" value ="http://api.app2e.com/smsBigSend.api.php" readonly>
						<font color="red">尽量别改</font>
						<input type="hidden" id="sender_id">
					</td>
					</tr>
				<tr>
					<th>用户名：</th>
					<td>
						<input type="text" id ="user" value ="">
					</td>
					</tr>
				<tr>
					<th>密码：</th>
					<td>
						<input type="text" id ="pwd" value ="">
					</td>
					</tr>
				<tr>
					<th>签名：</th>
					<td>
						<input type="text" id ="signature" value ="【大麦科技】"><font color="red">尽量别改</font>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>