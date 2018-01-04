<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大麦增值业务运营平台</title>
<link rel="stylesheet" type="text/css" href="css/jquery/tree/zTreeStyle.css" />
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/sys/user_list.js?v=3"></script>
<script type="text/javascript" src="js/common/jquery/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'用户管理'" class="regionCenter">
		<div id="common_search" class="common_search">
				<input type="button" id="user_add" class="btn btn-success" value="添加用户"> 
				<input type="hidden" id="hidRoleId" value="${currentUser.roleId}"> 
				<input type="hidden" id="hidHallId" value="${currentUser.hallId}"> 
				<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
				<input type="hidden" id="hidBranchName" value="${currentUser.branchName}">
				<input type="hidden" id="hidProvinceId" value="${currentUser.provinceCode}"> 
				<input type="hidden" id="hidProvinceName" value="${currentUser.provinceName}"> 
				<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}"> 
				<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}"> 
				<input type="hidden" id="hidCityId" value="${currentUser.cityCode}"> 
				<input type="hidden" id="hidCityName" value="${currentUser.cityName}"> 
			<label>战区：</label> 			
			<select id="q_zone" onchange="javascript:initQueryBranch(this.value);" >
					<option value="-1">全部</option>
					<c:forEach items="${zoneList}" var="zL" varStatus="st">
						<option value="${zL.id}">${zL.zoneName}</option>
					</c:forEach>
				</select> 
			<label>分公司：</label> 
			<select id="q_branch" onchange="javascript:initQueryHall(this.value);">
				<option value="-1">全部</option>
				<c:forEach items="${branchList}" var="bL" varStatus="st">
					<option value="${bL.id}">${bL.branchName}</option>
				</c:forEach>
			</select> <label>营业厅：</label> <select id="q_hall">
				<option value="-1">全部</option>
				<c:forEach items="${hallList}" var="hL" varStatus="st">
					<option value="${hL.id}">${hL.hallName}</option>
				</c:forEach>
			</select> <label>用户名：</label> <input type="text" id="q_userName" /> <input
				type="button" id="user_query" class="btn btn-success" value="查询">
		</div>
		<table id="user_table"></table>
        <div id="add_zoneTree_dialog" data-options="closed:true,modal:true,title:'添加区域',iconCls:'icon-save'" style="padding: 5px; width: 400px; height: 500px;">
            <table>
                <tr>
                    <td colspan="2">
                        <div id="auth_tree">
                            <ul id="treeDemo" class="ztree"></ul>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
		<div id="user_detail_dialog"
			data-options="closed:true,modal:true,title:'用户信息',iconCls:'icon-save'"
			style="padding: 5px; width: 600px; height: 500px;">
			<form action="sysUser/getList.json" id="user_detail_form">
				<input type="hidden" id="userId">
				<table>
					<tr>
						<td>名称：</td>
						<td><input type="text" id="userName"></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" id="userPwd" autocomplete="off"></td>
					</tr>
					<tr>
						<td>用户邮箱：</td>
						<td><input type="text" id="userMail"><input
							type="hidden" id="oldUserMail"></td>
					</tr>
					<tr>
						<td>电话：</td>
						<td><input type="text" id="phoneNumber"></td>
					</tr>
					<tr>
						<td>角色：</td>
						<td><select id="roleId" onchange="javascript:changeView();"></select></td>
					</tr>
					<tr id="trZone">
						<td>战区：</td>
						<td><select id="zoneId" onchange="javascript:initCityList(this.value);">
						<option value="-1">-</option>
						<c:forEach var="zoneList" items="${zoneList}">
								<option value="${zoneList.id}">${zoneList.zoneName}</option>
						</c:forEach>
						</select>
						</td>
					</tr>
	              <tr id="trZoneTree">
                        <input id="zoneTreeIds" value="" type="hidden" />
                        <td>所属区域：</td>
                        <td>
                            <input type="button" id="addZoneTree" class="btn btn-success" value="添加区域">
                        </td>
                    </tr>
					<tr>
					<input type = "hidden" id="provinceId">
					<input type = "hidden" id="provinceName">
					</tr>
					<tr id="trArea">
						<td>市：</td>
						<td>
							<select id="cityId"	onchange="javascript:initBranchList(this.value);"></select>
						</td>
					</tr>
					<tr id="trBranch">
						<td>所属分公司：</td>
						<td>
							<select id="branchId" onchange="javascript:initHallList(this.value);"></select> 
							<!-- 隐藏分公司添加按钮 -->
							<!-- <input type="button" id="addBranch" class="btn btn-success" value="添加分公司"> -->
						</td>
					</tr>
					<tr id="trHall">
						<td>所属营业厅：</td>
						<td><select id="hallId"></select> 
							<input type="button" id="addHall" class="btn btn-success" value="添加营业厅">
						</td>
					</tr>
					<tr>
						<td>是否发布：</td>
						<td>
							<select id="isEffective">
								<option value="0">无效</optison>
								<option value="1">有效</option>
							</select>
						</td>
					</tr>
					<tr <c:if test="${isGod_User==0}">style="display:none"</c:if>>
						<td>特殊权限：</td>
						<td>
							新增时不可添加特殊权限<br>
							<div id="spAuthSelect" >
							<select id="authMap" multiple="multiple">
								<c:forEach  items="${daoAuthMap}" var="map">
									<option value="${map.key}">${map.value}</option>
								</c:forEach>
							</select>
							<input type="button" value="取消选中" onclick="$('#authMap').val('');">
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="add_branch_dialog" data-options="closed:true,modal:true,title:'添加分公司',iconCls:'icon-save'" style="padding: 5px; width: 300px; height: 200px;">
			<table>
				<tr>
					<td>分公司名称：</td>
					<td><input type="text" id="newBranchName"></td>
				</tr>
			</table>
		</div>
		<div id="add_hall_dialog" data-options="closed:true,modal:true,title:'添加分公司',iconCls:'icon-save'" style="padding: 5px; width: 300px; height: 200px;">
			<table>
				<tr>
					<td>营业厅名称：</td>
					<td><input type="text" id="newHallName"></td>
				</tr>
			</table>
		</div>
        <div id="add_zoneTree_select_dialog" data-options="closed:true,modal:true,title:'查看区域',iconCls:'icon-save'" style="padding: 5px; width: 400px; height: 500px;">
            <table>
                <tr>
                    <td colspan="2">
                        <div id="auth_tree_select">
                            <ul id="treeDemo_select" class="ztree"></ul>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
	</div>
</body>
</html>