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
<title>战区管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/sys/zone.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'战区管理'" class="regionCenter">
	<div id="zone_toolbar">
		<input type="button" id="add_zone" class="btn btn-success" value="新增">
	</div>
	<table id="zone_table"></table>
	<div id="selected_city_dialog" data-options="closed:true,modal:true,title:'城市关联战区',iconCls:'icon-save'" style="padding: 5px; width: 500px; height: 500px;">
		<input type="hidden" id="id"> 
		<input type="hidden" id="zoneName"> 
		<label>省：</label><select id="province" onchange="javascript:initCityList(this.value);"></select> 
		<label>市：</label><select id="city"></select> 
		<input id="addCity" type="button" value="添加">
		<table id="selected_city_table"></table>
	</div>
	<div id="add_zone_dialog" data-options="closed:true,modal:true,title:'战区信息',iconCls:'icon-save'" style="padding: 5px; width: 400px; height: 200px;">
		<input type="hidden" id="zoneId" /> 
		<label>战区名称：</label><input type="text"  id="addZoneName" />
	</div>
	
	<div id="zone_branch_dialog" data-options="closed:true,modal:true,title:'分公司详情',iconCls:'icon-save'" style="padding: 5px; width: 500px; height: 500px;">
		<input type="hidden" id="zoneBranchId"> 
		<input type="hidden" id="zoneBranchName"> 
		<table id="zone_branch_table"></table>
	</div>
	
	<div id="branch_hall_dialog" data-options="closed:true,modal:true,title:'营业厅详情',iconCls:'icon-save'" style="padding: 5px; width: 400px; height: 400px;">
		<input type="hidden" id="branchId"> 
		<input type="hidden" id="branchName"> 
		<table id="branch_hall_table"></table>
	</div>
</div>
</body>
</html>