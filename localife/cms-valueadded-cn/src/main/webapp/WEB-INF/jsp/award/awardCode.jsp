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
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/award/award_code.js?v=2"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<style>
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
input[type="text"]{
	width:12%;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'活动管理'" class="regionCenter">
		<div id="award_toolbar">
			<input type="hidden" id="awardActivityId" value="${awardActivity.id}">
			<input type="button" id="add_award_code" class="btn btn-success"
				value="导入中奖码">
			<input type="button" id="export_award_code"
				onclick="initDownLoad(${awardActivity.id})" class="btn btn-success" value="导出中奖码">
			<input type="button" class="btn btn-success" value="返回" onclick="javascript:window.location.href='awardActivity/show.html'" style="float:right;">
			<br>
			<label>手机号：</label>
			<input id="userPhone" type="text" /> 
			<label>中奖码：</label>
			<input id="awardCode" type="text" />
			<label>奖品名称：</label>
			<input id="awardName" type="text" /> 
			<label>奖品类型：</label>
			<input id="awardType" type="text" /> 
			<label>中奖状态：</label>
			<select id="acceptFlag">
				<option value="0">请选择</option>
				<option value="1">中奖</option>
				<option value="2">未中奖</option>
			</select> 
			<input type="button" id="query_award" class="btn btn-success" value="查询" style="float:right;margin-top:5px;">
		</div>
		<table id="award_code_table"></table>
	</div>
	<div id="import_code_dialog"
		data-options="closed:true,modal:true,title:'导入',iconCls:'icon-save'"
		style="padding: 5px; width: 500px; height: 300px;">
		<input type="hidden" id="hiddenPath" />
		<table>
			<tr>
				<td>活动名称:</td>
				<td>${awardActivity.title}</td>
			</tr>
			<tr>
				<td>下载模板:</td>
				<td><input type="button" onclick="downLoadModel(${awardActivity.id})" value="下载模板"/></td>
			</tr>
			<tr>
				<td>导入:</td>
				<td><span id="ImportFileName">上传的文件名显示在这里</span><input
					type="button" id="importCodeExcel" class="btn btn-success" value="选择"></td>
			</tr>
		</table>
	</div>
</body>
</html>