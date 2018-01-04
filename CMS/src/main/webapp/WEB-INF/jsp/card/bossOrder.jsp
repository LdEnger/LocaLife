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
<title>boss订单详情列表</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/card/bossOrder.js?v=${js_version}"></script>
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
	<div data-options="region:'center',title:'boss订单详情列表'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
			<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
			<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
			<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">
		<div id="smsRe_toolbar">
			<label>添加时间：	<input id="q_ctime" size=12/> <label>
			<label>boss类型：	<select id ="q_bossType" style="width: 80px;">
								<option value="0">全部</option>
								<option value="1">新boss</option>
								<option value="0">老boss</option>
							</select> <label>  
			<label>状态：	<select id ="q_status" style="width: 90px;">
								<option value="5">错误数据</option>
								<option value="1">初始状态</option>
								<option value="2">产码</option>
								<option value="3">激活</option>
							</select> <label>
			<label>sn：	<input id="q_sn" /> <label>	
			<label>分公司：<select id="q_branch" style="width: 140px;"></select></label>
			<label>是否关联套餐<select id="q_state" style="width: 120px;">
				<option value="2">已关联</option>
				<option value="1">未关联</option>
			</select> </label>		
			<input type="button" id="query_smsR" class="btn btn-success" value="查询">
		</div>
		<table id="smsRe_table"></table>
		<div id="product_dialog"
			data-options="closed:true,modal:true,title:'关联套餐包',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 400px;">
			<table>
				<tr>
					<td>套餐信息：</td>
					<td><input type="text" id="e_product_info"
						style="width: 400px; border: 0;"
						value="" readonly>
						<input type="hidden" id=e_order_id>
					</td>
				</tr>
				<tr>
					<td>分公司:</td>
					<td><select id="e_branch" style="width: 400px;" onchange="ininActivity(-1);"></select></td>
				</tr>
				<tr>
					<td>活动包：</td>
					<td><select id="e_activity" style="width: 400px;"></select></td>
				</tr>
				<tr>
					<td>mac：</td>
					<td><input type="text" id="e_mac" style="width: 400px;"></td>
				</tr>
				<tr>
					<td>sn：</td>
					<td><input type="text" id="e_sn" style="width: 400px;"></td>
				</tr>
				<tr>
					<td>手机号：</td>
					<td><input type="text" id="e_phone" style="width: 400px;"></td>
				</tr>
				<tr>
					<td>激活码：</td>
					<td><input type="text" id="e_code" style="width: 400px;" readOnly></td>
				</tr>
				<tr>
					<td colspan="2"><font color="red">分公司活动包必填</font></td>
				</tr>
			</table>
		</div>
</body>
</html>