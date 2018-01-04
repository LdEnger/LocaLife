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
<title>充送管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/discount/discount.js?v=2"></script>
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
	<div data-options="region:'center',title:'充送管理'" class="regionCenter">
		<div id="discount_toolbar">
			<input type="button" id="add_discount" class="btn btn-success" value="添加">
		</div>
		<table id="discount_table"></table>
		<div id="discount_detail_dialog" data-options="closed:true,modal:true,title:'充送信息',iconCls:'icon-save'" style="padding: 5px; width: 500px; height: 400px;">
			<input type="hidden" id="id"> 
			<table class=tableC3>
				<tr>
					<th>优惠名称：</th>
					<td><input type="text" id="discountName"></td>
				</tr>
				<tr>
					<th>充值金额：</th>
					<td>
						<select id="rechargeAmount">
							<c:forEach items="${rechargeList}" var="rL" varStatus="st">
								<option value="${rL.rechargeAmount}">${rL.rechargeAmount}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>赠送金额：</th>
					<td><input type="text" id="discountAmount"></td>
				</tr>
				<tr>
					<th>是否有效：</th>
					<td>
						<select id="isEffective">
						<option value ="0">无效</option>
						<option value ="1">有效</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>图片：</th>				
					<td>
						<img style="height: 100px; width: 100px;" src=""  id="img" />
					</td>
					<td><input type="hidden" id="imgUrl"></td>
					<td><input type="button"  id="imgSubmit" value="选择文件"></td>
				</tr>
			</table>
		</div>
</body>
</html>