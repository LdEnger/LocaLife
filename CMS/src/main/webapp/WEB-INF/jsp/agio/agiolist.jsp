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
<title>麦币包制作</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	var zoneListData =new Array();
	<c:forEach items="${zoneList}" var="zL" varStatus="st">
		zoneListData["${zL.id}"]="${zL.zoneName}";
	</c:forEach>
	
	var userListData=new Array();
	<c:forEach items="${userList}" var="zL" varStatus="st">
		userListData["${zL.userId}"]="${zL.userMail}";
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
<script type="text/javascript" src="js/agio/agiolist.js?v=${js_version}"></script>
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
	<div data-options="region:'center',title:'麦币包制作'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="agio_package_conf_toolbar">
			<input type="button" id="add_agio_package_conf" class="btn btn-success" value="新增" <c:if test="${isBranchUser == 0}">
						style="display:none"
					</c:if>>
			分公司:<select id="q_branch" >
					<c:if test="${currentUser.roleId!=2}">
					<option value="-1">全部</option></c:if>
					<c:forEach items="${branchList}" var="zL" varStatus="st">
						<option value="${zL.id}">${zL.branchName}</option>
					</c:forEach>
				 </select>
			创建人员：<select id="q_sender" >
					<c:if test="${currentUser.roleId!=2}">
					<option value="-1">全部</option></c:if>
					<c:forEach items="${userList}" var="zL" varStatus="st">
						<c:choose>
							<c:when test="${zL.userId==currentUser.userId}">
								<option value="${zL.userId}" selected>${zL.userMail}</option>
							</c:when>
							<c:otherwise>
								<option value="${zL.userId}">${zL.userMail}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select>
			状态:<select id="q_state" >
				<option value="1">上线</option>
				<option value="0">下线</option>
				<option value="3">删除</option></select>
			<input type="button" id="query_agio_package_conf" class="btn btn-success" value="查询">
		</div>
		<table id="agio_package_conf_table"></table>

		<div id="agio_package_conf_dialog"
			data-options="closed:true,modal:true,title:'定制麦币包',iconCls:'icon-save'"
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
				<tr><th>操作人员:</th><td>${currentUser.userName}(${currentUser.userId})</td></tr>
				<tr>
					<th>麦币包名称：</th>
					<td><input type="text" id="packageName" ></td>
				</tr>
				<tr>
					<th>麦币包金额：</th>
					<td><input type="text" id="packAmount" maxlength="8" ></td>
				</tr>
				<tr>
					<th>折扣名称：</th>
					<td><select id="agioPackageId">
						<c:forEach items="${agioList}" var="zL" varStatus="st">
							<option value="${zL.id}">${zL.agioName}-${zL.agioValue}</option>
						</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>状态</th>
					<td>
						<select id="state">
							<option value="0">下线</option>
							<option value="1">上线</option>
							<option value="3">删除</option></select>
					</td>
				</tr>
				<c:if test="${currentUser.roleId!=2}">
					<tr><td colspan="2"><font color=red>检测您不是分公司权限人员，您添加的麦币包不生效，请使用分公司账号进行麦币包添加</font></td></tr>
				</c:if>
			</table>
		</div>
</body>
</html>