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
<title>活动卡券管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/card/card.js?v=2"></script>
<script type="text/javascript" src="js/common/md5.js"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
</head>

<body  class="easyui-layout">
<div data-options="region:'center',title:'活动卡券管理'" class="regionCenter">
		<div id="card_toolbar">
			<div>
				<input type="button" id="addSingle" class="btn btn-success" value="单个生成激活码">
				<input type="button" id="addBatch" class="btn btn-success" value="批量生成激活码">
			</div>
			<div>
				<label>卡生成人：</label> 
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
				<label>活动名称：</label> 
				<select id="activity">
					<option value="-1">全部</option>
					<c:forEach items="${activityList}" var="aL" varStatus="st">
						<option value="${aL.id}">${aL.activityName}</option>
					</c:forEach>
				</select> 
				<label>MAC：</label> <input id="mac" type="text" /> <label>用户ID：</label>
				<input id="uid" type="text" />
			</div>
			<div>
				 <label>激活状态：</label> 
				 <select id="status">
				 	<option value="-1">全部</option>
					<option value="1">未激活</option>
					<option value="2">已激活</option>
					<option value="3">激活失败</option>
					<option value="4">已注销</option>
					<option value="5">已过期</option>
				</select> 
				<label>激活时间：</label> 
				<input id="startTime" /> 
				<label>--</label> 
				<input id="stopTime" /> 
				<label>激活码：</label> <input id="cardPwd" type="text" /> 
				<input type="button" id="query_card" class="btn btn-success" value="查询">
				<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
				<input type="hidden" id="hidBranchName" value="${currentUser.branchName}">
				<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
				<input type="hidden" id="hidHallName" value="${currentUser.hallName}">
				<input type="hidden" id="hidUserName" value = "${currentUser.userName}">
				<input type="hidden" id="hidRoleId" value = "${currentUser.roleId}">
				<input type="hidden" id="hidPartnerKey" value = "${partnerKey}">
				<input type="hidden" id="hidIsBatch" value = "${isBatch}">
			</div>
			<div><input type="button" id="import_btn" class="btn btn-success" value="导入基础信息">
			<input type="button" id="export_card" class="btn btn-success" value="导出卡信息">
			<input type="button" id="batch_active" class="btn btn-success" value="批量激活">
			<input type="button" id="batch_cancel" class="btn btn-success" value="批量注销">
			</div>
		</div>

		<table id="card_table"></table>
	
		<div id="single_card_dialog" data-options="closed:true,modal:true,title:'单个生成激活码',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    	<table>
    	<tr>
    	<td>活动名称:</td><td>
    		<select id="s_editActivity" style="width:200px;">
					<c:forEach items="${activityList}" var="aL" varStatus="st">
						<option value="${aL.id}">${aL.activityName}</option>
					</c:forEach>
			</select></td>
    	</tr>
  	   	<tr>
    	<td>生成卡用户：</td><td><input type="text" id="s_editUser"  style="width:200px;border:0;" value="${currentUser.branchName}/${currentUser.hallName}/${currentUser.userName}" readonly></td>
    	</tr>
   		<tr>
    	<td>MAC：</td><td><input type="text" id="s_editMac"></td>
    	</tr>
  	    <tr>
    	<td>SN：</td><td><input type="text" id="s_editSn"></td>
    	</tr>
 	  	 <tr>
    	<td>激活卡：</td><td><input type="text" id="s_editActivationCode" readonly><a href ="javascript:copyStr()">复制</a></td>
    	</tr>
    	</table>
    </div>	
    
    <div id="batch_card_dialog" data-options="closed:true,modal:true,title:'批量生成激活码',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    	<table>
    	<tr>
    	<td>活动名称:</td><td>
    		<select id="b_editActivity" style="width:200px;">
					<c:forEach items="${activityList}" var="aL" varStatus="st">
						<option value="${aL.id}">${aL.activityName}</option>
					</c:forEach>
			</select></td>
    	</tr>
  	  	<tr>
    	<td>生成卡用户：</td><td><input type="text" id="b_editUser" style="width:200px;border:0;"value="${currentUser.branchName}/${currentUser.hallName}/${currentUser.userName}" readonly></td>
    	</tr>
 	  	 <tr>
    	<td>生成数量：</td><td><input type="text" id="b_count"></td>
    	</tr>
    	</table>
    </div>	
    
       <div id="import_card_dialog" data-options="closed:true,modal:true,title:'导入',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    	<table><input type="hidden"  id="hiddenPath"/>
    	<tr>
    	<td>活动名称:</td><td>
    		<select id="import_activity">
					<c:forEach items="${activityList}" var="aL" varStatus="st">
						<option value="${aL.id}">${aL.activityName}</option>
					</c:forEach>
			</select></td>
    	</tr>
  	  	<tr>
    	<td>导入:</td><td><span id="ImportFileName" >上传的文件名显示在这里</span><input type="button" id="import_card" class="btn btn-success" value="选择"></td>
    	</tr>
    	</table>
    </div>	
    
      <div id="activition_card_dialog" data-options="closed:true,modal:true,title:'单个激活',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    	<table><input type="hidden" id="a_id">
	 	 	<tr>
	    		<td>用户信息：</td><td><input type="text" id="a_userInfo"></td>
	    	</tr>
  		 	<tr>
	    		<td>激活码：</td><td><input type="text" id="a_actCode" readonly style="border:0;width:200px;"></td>
	    	</tr>
  	 	 	<tr>
	    		<td>MAC：</td><td><input type="text" id="a_mac"></td>
	    	</tr>
  	 	 	<tr>
	    		<td>SN：</td><td><input type="text" id="a_sn"></td>
	    	</tr>
    	</table>
    </div>	
</body>

</html>