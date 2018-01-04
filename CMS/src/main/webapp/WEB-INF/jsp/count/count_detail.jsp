<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/count/count.js?v=${js_version}"></script>
<script type="text/javascript" src="js/common/md5.js"></script>
</head>
<body   class="easyui-layout">
<div data-options="region:'center',title:'统计管理'" class="regionCenter">
		<div id="count_toolbar">
				<label>时间：</label> 
				<input id="startTime" size="16"/> 
				<label>--</label> 
				<input id="stopTime" size="16"/> 
				<select id="duration">
					<option value="0">不选择</option>
					<option value="1">大于360</option>
					<option value="2">100～360</option>
				</select>
				<label>活动名称：</label> <select id="activity">
					<option value="-1">全部</option>
					<c:forEach items="${activityList}" var="aL" varStatus="st">
						<option value="${aL.productId}">${aL.productName}</option>
					</c:forEach>
				</select>
				<input type="button" id="countByActivationTime" class="btn btn-success" value="激活时间查询">
				<input type="button" id="countByEffectTime" value="生效时间查询" style="display:none">
				<input type="button" id="countByCreateTime" class="btn btn-success" value="生成时间查询">
				<input type="button" id="excel_btn" class="btn btn-success" value="导出结果" onclick="initDownLoad()">
				<br>
				<label><font color="red">例：查询一周数据，起止时间选为 上周一--本周一，默认按开通时间查询;  ||导出前请点查询按钮查到结果，否则默认按开通时间导出</font></label>
		</div>
		<table id="count_table"></table>
</div>
</body>
</html>