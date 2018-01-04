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
<title>直播管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/live_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/live/live.js?v=1"></script>
<script type="text/javascript" src="js/common/md5.js"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
</head>

<body  class="easyui-layout">
<div data-options="region:'center',title:'直播管理'" class="regionCenter">
	<div id="live_toolbar">
		<div style="height: 30px;">
			<input type="button" id="openLive" class="btn btn-success" value="单个开通直播服务">
		</div>
		<div>
		<input type="hidden" id="currentUserBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="currentUserHallId" value="${currentUser.hallId}">
		<input type="hidden" id="currentUserProvinceCode" value="${currentUser.provinceCode}">
		<input type="hidden" id="currentUserCityCode" value="${currentUser.cityCode}">
		<input type="hidden" id="currentUserId" value="${currentUser.userId}">
		<input type="hidden" id="currentUserRoleId" value="${currentUser.roleId}">
		 <c:if test="${currentUser.roleId==1}">
				<label>开通人员：</label> 
				<select id="branch" onchange="javascript:initHall(this.value);" >
					<option value="-1">全部</option>
					<c:forEach items="${branchList}" var="bL" varStatus="st">
						<option value="${bL.id}">${bL.branchName}</option>
					</c:forEach>
				</select> 
				<select id="hall">
				<option value="-1">全部</option>
					<c:forEach items="${hallList}" var="hL" varStatus="st">
						<option value="${hL.id}">${hL.hallName}</option>
					</c:forEach>
				</select> 
			</c:if>
			<td>订单状态：</td>
    		<td>
    			<select id="status">
    				<option value="-1">全部</option>
    				<option value="1">已提交</option>
    				<option value="2">已开通</option>
    				<option value="3">开通失败</option>
    			</select>
    		</td>
			<label>MAC：</label> <input id="mac" type="text" /> <label>SN：</label><input id="sn" type="text" />
			</div>
			<div style="height: 40px;padding-top: 5px;">
				<label>用  户  I D ：</label><input id="uid" type="text" />
				<label>开通时间：</label> 
				<input id="startTime" /> 
				<label>--</label> 
				<input id="stopTime" /> 
				<input type="button" id="query_live" name="query_live" class="btn btn-success" value="查询">
			</div>
	</div>
	<table id="live_table"></table>
	
	<div id="single_live_dialog" data-options="closed:true,modal:true,title:'单个开通直播服务',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
		<table>
			<tr>
	    		<td>产品名称:</td><td>
		    		<select id="s_editLive" style="width:200px;">
						<c:forEach items="${liveList}" var="lL" varStatus="st">
							<option value="${lL.productId}">${lL.productName}</option>
						</c:forEach>
					</select>
				</td>
    		</tr>
    		<tr>
		    	<td>开通者：</td>
		    	<td><input type="text" id="s_editUser"  style="width:200px;border:0;" value="${currentUser.branchName}/${currentUser.hallName}/${currentUser.userName}" readonly></td>
		    </tr>
		    <tr>
		    	<td>服务开通所在省份：</td>
		    	<td><input type="text" id="s_editProvince"  style="width:200px;border:0;" value="${currentUser.provinceName}" readonly></td>
		    </tr>
		    <tr>
		    	<td>服务开通所在市区：</td>
		    	<td><input type="text" id="s_editCity"  style="width:200px;border:0;" value="${currentUser.cityName}" readonly></td>
		    </tr>
		   	<tr>
		    	<td>MAC：</td>
		    	<td><input type="text" id="s_editMac"></td>
		    </tr>
		  	<tr>
		    	<td>SN：</td>
		    	<td><input type="text" id="s_editSn"></td>
    		</tr>
		</table>
	</div>
</div>
</body>

</html>