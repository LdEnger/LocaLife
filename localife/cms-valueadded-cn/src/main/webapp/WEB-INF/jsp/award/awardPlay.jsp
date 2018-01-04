<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/award/award_play.js?v=2"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
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
		<div id="award_toolbar">
			<input type="hidden" id="activityId" value="${awardActivity.id}">
			<label>用户名：</label><input id="userName" type="text" /> 
			<label>手机号：</label><input id="userPhone" type="text" />
			<label>大麦通行证：</label><input id="mac" type="text" />
			<label>是否中奖：</label><select id="acceptFlag"><option value="0">请选择</option>
			<option value="1">中奖</option>
			<option value="2">未中奖</option></select>
			<input type="button" id="query_award" class="btn btn-success" value="查询">
			<input type="button" id="export_award_player" class="btn btn-success" value="导出Excel" onclick="initDownLoad(${awardActivity.id})">
			<input type="button" class="btn btn-success" value="返回" onclick="javascript:window.location.href='awardActivity/show.html'" >
		</div>
		<table id="award_play_table"></table>
	</div>
</body>
</html>