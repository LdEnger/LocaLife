<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大麦增值业务运营平台</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/sys/user_list.js?v=3"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'center',title:'用户管理'" class="regionCenter">
		<div id="common_search" class="common_search">
			<input type="button" id="user_add" class="btn btn-success" value="添加用户"> 
			<label>分公司名称：</label> 
			<select id="q_branch" onchange="javascript:initHall(this.value);">
				<option value="-1">全部</option>
				<c:forEach items="${branchList}" var="bL" varStatus="st">
					<option value="${bL.id}">${bL.branchName}</option>
				</c:forEach>
			</select> 
			<label>营业厅名称：</label> 
			<select id="q_hall">
			<option value="-1">全部</option>
					<c:forEach items="${hallList}" var="hL" varStatus="st">
						<option value="${hL.id}">${hL.hallName}</option>
					</c:forEach>
			</select> 
			<label>用户名：</label> 
			<input type="text" id="q_userName" />
			<input type="button" id="user_query" class="btn btn-success" value="查询"> 
			<input type="hidden" id="hidRoleId" value = "${currentUser.roleId}">
			<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
			<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		</div>
		<table id="user_table"></table>
	<div id="user_detail_dialog" data-options="closed:true,modal:true,title:'用户信息',iconCls:'icon-save'" style="padding:5px;width:600px;height:500px;">
		<form action="sysUser/getList.json" id="user_detail_form">
    	<input type="hidden" id="userId">
    	<table>
    	<tr>
    	<td>名称：</td><td><input type="text" id="userName"></td>
    	</tr>
    	<tr>
    	<td>密码：</td><td><input type="text" id="userPwd"></td>
    	</tr>
    	<tr>
    	<td>用户邮箱：</td><td><input type="text" id="userMail"><input type="hidden" id="oldUserMail"></td>
    	</tr>
    	<tr>
    	<td>电话：</td><td><input type="text" id="phoneNumber"></td>
    	</tr>
    	<tr>
    	<td>角色：</td><td><select id="roleId" onchange="javascript:changeView();"></select></td>
    	</tr>
    	<tr id="trArea">
    	<td>省：</td><td><select id="provincehId" onchange="javascript:initCityList(this.value);"></select>
    	</td>
    	<td>市：</td><td><select id="cityId" onchange="javascript:initBranchList(this.value);"></select>
    	</td>
    	</tr>
    	<tr id="trBranch">
    	<td>所属分公司：</td><td><select id="branchId" onchange="javascript:initHallList(this.value);"></select>
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addBranch()">添加分公司</a></td>
    	</tr>
    	<tr id="trHall">
    	<td>所属营业厅：</td><td><select id="hallId"></select>
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addHall()">添加营业厅</a></td>
    	</tr>
    	<tr>
    	<td>是否发布：</td>
    	<td><select id="isEffective">
    		<option value="0">无效</optison>
    		<option value="1">有效</option>
    		</select></td>
    	</tr>
    	</table>
		</form>
    </div></div>
</body>
</html>
<script>
function addBranch(){
	var jq = top.jQuery;
	var winId = 'branchadd';
	var user = {
			"provinceCode":$("#provincehId").val(),
			"provinceName":$("#provincehId  option:selected").text(),
			"cityCode":$("#cityId").val(),
			"cityName":$("#cityId  option:selected").text()
	}
	jq.createWin({
        title:"添加分公司",
        url:'branch/openAdd.html?',
        data:user,
        height:200,
        width:300,
        winId:winId,
        buttons:[{
			text:'确 定',
			handler:function(){
				var branchObj = parent.branchAdd(winId);
				$("#branchId").append("<option value='"+branchObj.id+"'>"+branchObj.branchName+"</option>");
				$("#branchId").val(branchObj.id);
			}}],
		onClose:function(targetjq){
		},
		onComplete:function(dailog,targetjq){
		}
    });	
}
function addHall(){
	var jq = top.jQuery;
	var branchId=$('#branchId').val();
	var winId = 'halladd';
	jq.createWin({
        title:"添加营业厅",
        url:'hall/openAdd.html?branchId='+branchId,
        height:200,
        width:300,
        winId:winId,
        buttons:[{
			text:'确 定',
			handler:function(){
				var hallObj = parent.hallAdd(winId);
				$("#hallId").append("<option value='"+hallObj.id+"'>"+hallObj.hallName+"</option>");
				$("#hallId").val(hallObj.id);
			}}],
		onClose:function(targetjq){
		},
		onComplete:function(dailog,targetjq){
		}
    });	
}
</script>