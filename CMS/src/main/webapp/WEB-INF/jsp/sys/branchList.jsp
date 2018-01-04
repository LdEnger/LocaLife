<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>分公司管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/sys/branch_list.js?v=${js_version}"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'分公司管理'" class="regionCenter">
	<div id="zone_toolbar">
		<input type="button" id="add_zone" class="btn btn-success" value="新增">
		分公司名称：<input type="text" id ="q_branch_name">
		<input type="button" id="query_btn" class="btn btn-success" value="查询">
	</div>
	<table id="zone_table"></table>
	<div id="add_zone_dialog" data-options="closed:true,modal:true,title:'分公司信息',iconCls:'icon-save'" style="padding: 5px; width: 400px; height: 400px;">
		<input type="hidden" name ="id" id="id">
		<label>分公司：</label><input type="text"  id="branchName" maxlength="80"/><br>
		<label>行政省：</label><select id="province" onchange="javascript:initCityList(this.value,0);"></select><br> 
		<label>行政市：</label><select id="city"></select> <br>
		<label>新boss：</label><input type="text"  id="bossBranchNew" maxlength="80"/><br>
		<label>老boss：</label><input type="text"  id="bossBranchOld" maxlength="80"/><br>
	</div>
</div>
</body>
</html>