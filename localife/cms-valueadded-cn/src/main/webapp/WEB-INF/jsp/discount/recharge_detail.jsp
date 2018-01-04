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
<title>麦粒管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/discount/recharge.js?v=2"></script>
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
	<div data-options="region:'center',title:'大麦币管理'" class="regionCenter">
		<div id="discount_toolbar">
			<input type="button" id="add_recharge" class="btn btn-success" value="添加">
			<input type="txt" id="rechargeName" placeholder="请输入大麦币名称" >
			<label>状态：</label>
			<select id="status">
			<option value="-1">全部</option>
			<option value="1">已上线</option>
			<option value="0">未上线</option>
			</select>
				<input type="button" id="query_recharge" class="btn btn-success" value="查询">
		</div>
		<table id="recharge_table"></table>
		<div id="recharge_detail_dialog" data-options="closed:true,modal:true,title:'大麦币信息',iconCls:'icon-save'" style="padding: 5px; width: 500px; height: 400px;">
			<input type="hidden" id="id"> 
			<table class=tableC3>
				<tr>
					<th>数量：</th>
						<td><input type="text" id="editRechargeAmount"></td>
				</tr>
				<tr>
					<th>名称：</th>
					<td><input type="text" id="editRechargeName"></td>
				</tr>
				<tr>
					<th>描述：</th>
					<td><input type="text" id="editDescription"></td>
				</tr>
				<tr>
					<th>状态：</th>
					<td>
						<select id="editStatus">
						<option value ="0">无效</option>
						<option value ="1">有效</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>缩略图：</th>				
					<td>
						<img style="height: 100px; width: 100px;" src=""  id="thumbImg" />
					</td>
					<td><input type="hidden" id="thumbImgUrl"></td>
					<td><input type="button"  id="thumbImgSubmit" value="选择文件"></td>
				</tr>
				<tr>
					<th>大图：</th>				
					<td>
						<img style="height: 100px; width: 100px;" src=""  id="bigImg" />
					</td>
					<td><input type="hidden" id="bigImgUrl"></td>
					<td><input type="button"  id="bigImgSubmit" value="选择文件"></td>
				</tr>
			</table>
		</div>
</body>
</html>