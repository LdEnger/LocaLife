<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<script type="text/javascript">
	var isBranchUser =${isBranchUser};
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>麦币卡券管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/card/agiocard.js?v=${js_version}"></script>
<script type="text/javascript" src="js/common/md5.js"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="js/load_wait.js"></script>
</head>

<body class="easyui-layout">
	<div data-options="region:'center',title:'麦币卡券管理'" class="regionCenter">
		<div id="card_toolbar">
			<div <c:if test="${isBranchUser == 0}">
						style="display:none"
					</c:if>>
				<input type="button" id="addSingle" class="btn btn-success"
					value="单个生成激活码"/>
					<input type="hidden" id="horizflag" value="${horizflag}"/>
				<!-- <input type="button" id="addBatch" class="btn btn-success" value="批量生成激活码"> -->
			</div>
			<div>
				<label>选择战区：</label> <select id="zone"
					onchange="javascript:initBranch(this.value);">
					<option value="-1">全部</option>
					<c:forEach items="${zoneList}" var="zL" varStatus="st">
						<option value="${zL.id}">${zL.zoneName}</option>
					</c:forEach>
				</select> <label>分公司：</label> <select id="branch"
					onchange="javascript:initHall(this.value);">
					<option value="-1">全部</option>
					<c:forEach items="${branchList}" var="bL" varStatus="st">
						<option value="${bL.id}">${bL.branchName}</option>
					</c:forEach>
				</select> <label>营业厅：</label> <select id="hall">
					<option value="-1">全部</option>
					<c:forEach items="${hallList}" var="hL" varStatus="st">
						<option value="${hL.id}">${hL.hallName}</option>
					</c:forEach>
				</select> <label>活动名称：</label> <select id="activity">
					<option value="-1">全部</option>
					<c:forEach items="${activityList}" var="aL" varStatus="st">
						<option value="${aL.id}">${aL.activityName}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label>MAC：</label> <input id="mac" type="text" /> <label>用户ID：</label>
				<input id="uid" type="text" /> <label>激活状态：</label> <select
					id="status">
					<option value="-1">全部</option>
					<option value="1">未激活</option>
					<option value="2">已激活</option>
					<option value="4">已注销</option>
					<option value="5">已失效</option>
				</select> <label>激活码：</label> <input id="cardPwd" type="text" />
			</div>
			<div>
				<label>用户号：</label> <input id="userNum" type="text" /> <label>业务单号：</label>
				<input id="orderNum" type="text" /> 
				<c:if test="${horizflag == '1' }">
				 <label>卡号：</label>
				<input id="cardNumber" type="text" />
				</c:if> 
			</div>
			<div>
				<label>卡生成时间：</label>
				 <input id="createTime" /> <label>--</label> 
				 <input id="createStopTime" />
				<label>卡激活时间：</label>
				 <input id="activationTime" /> <label>--</label> 
				 <input id="activationStopTime" /> 
					<input type="button" id="query_card" class="btn btn-success" value="查询">
				<input type="hidden" id="hidBranchId"
					value="${currentUser.branchId}"> <input type="hidden"
					id="hidBranchName" value="${currentUser.branchName}"> <input
					type="hidden" id="hidHallId" value="${currentUser.hallId}">
				<input type="hidden" id="hidHallName"
					value="${currentUser.hallName}"> <input type="hidden"
					id="hidUserName" value="${currentUser.userName}"> <input
					type="hidden" id="hidRoleId" value="${currentUser.roleId}">
				<input type="hidden" id="hidPartnerKey" value="${partnerKey}">
				<input type="hidden" id="hidIsBatch" value="${isBatch}"> <input
					type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
				<input type="hidden" id="hidZoneName"
					value="${currentUser.zoneName}"> <input type="hidden"
					id="hidCityId" value="${currentUser.cityCode}">
			</div>
			<div style="display: none">
				<input type="button" id="import_btn" class="btn btn-success"
					value="导入基础信息"> <input type="button" id="export_card"
					class="btn btn-success" value="导出卡信息"> <input type="button"
					id="batch_active" class="btn btn-success" value="批量激活"> 
					<input type="button" id="batch_cancel" style="display:none" value="批量注销">
			</div>
		</div>

		<table id="card_table"></table>
		<!-- 单个生成激活码对话框 -->
		<div id="single_card_dialog"
			data-options="closed:true,modal:true,title:'单个生成激活码',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 450px;">
			<table>
				<tr>
					<td>活动名称:</td>
					<td><select id="s_editActivity" style="width: 200px;">
							<c:forEach items="${activityList}" var="aL" varStatus="st">
								<option value="${aL.id}">${aL.activityName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>生成卡用户：</td>
					<td><input type="text" id="s_editUser"
						style="width: 200px; border: 0;"
						value="${currentUser.zoneName}/${currentUser.branchName}/${currentUser.hallName}/${currentUser.userName}"
						readonly></td>
				</tr>
				<tr>
					<td>用户号：</td>
					<td><input type="text" id="s_editUserNum"></td>
				</tr>
				<tr>
					<td>业务单号：</td>
					<td><input type="text" id="s_editOrderNum"></td>
				</tr>
				<tr>
					<td>MAC：</td>
					<td><input type="text" id="s_editMac"><font color=red>*</font></td>
				</tr>
				<tr>
					<td>SN：</td>
					<td><input type="text" id="s_editSn"><font color=red>*</font></td>
				</tr>
				<tr <c:if test="${currentUser.branchId!=215}">style="display:none"</c:if>>
					<td>手机号：</td>
					<td><input type="text" id="s_phone"><!-- <br><font color=red>北京用户不填MAC,SN，填手机号会自动发短信到用户哦</font> --></td>
				</tr>
				<tr>
					<td title="时间限制最少1天、最多为1095天、如不填默认为365天">激活卡到期时间：</td>
					<td title="时间限制最少1天、最多为1095天、如不填默认为365天"><input type="text" id="s_editAutoActiveTimeLength"><label>天（选填）</label></td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td><span class="fcR" style="color: red;">自激活卡生成之日起，在填写天数内未激活的激活卡自动失效</span></td>
				</tr>
				<!-- 
				<tr>
					<td>激活卡：</td>
					<td><input type="text" id="s_editActivationCode" readonly><a
						href="javascript:copyStr()">复制</a></td>
				</tr> -->
			</table>
		</div>
		<!-- 批量生成激活码对话框 -->
		<div id="batch_card_dialog"
			data-options="closed:true,modal:true,title:'批量生成激活码',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 300px;">
			<table>
				<tr>
					<td>活动名称:</td>
					<td><select id="b_editActivity" style="width: 200px;">
							<c:forEach items="${activityList}" var="aL" varStatus="st">
								<option value="${aL.id}">${aL.activityName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>生成卡用户：</td>
					<td><input type="text" id="b_editUser"
						style="width: 200px; border: 0;"
						value="${currentUser.branchName}/${currentUser.hallName}/${currentUser.userName}"
						readonly></td>
				</tr>
				<tr>
					<td>生成数量：</td>
					<td><input type="text" id="b_count"></td>
				</tr>
				<tr>
					<td>激活卡到期时间：</td>
					<td><input type="text" id="d_editAutoActiveTimeLength"><label>天（选填）</label></td>
				</tr>
			</table>
		</div>
		<!--  excel导入对话框 -->
		<div id="import_card_dialog"
			data-options="closed:true,modal:true,title:'导入',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 300px;">
			<table>
				<input type="hidden" id="hiddenPath" />
				<tr>
					<td>活动名称:</td>
					<td><select id="import_activity">
							<c:forEach items="${activityList}" var="aL" varStatus="st">
								<option value="${aL.id}">${aL.activityName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>导入文件:</td>
					<td><span id="ImportFileName"></span><input type="button"
						id="import_card" class="btn btn-success" value="选择文件"></td>
				</tr>
				<tr>
					<td>下载Excel模板:</td>
					<td><input type="button" class="btn btn-success" value="下载"
						onclick="javascript:window.location.href='<%=basePath%>excelDemo/batchUploadVipDemo.xlsx'" />
					</td>
				</tr>
				<tr>
					<td>激活卡到期时间：</td>
					<td><input type="text" id="i_editAutoActiveTimeLength"><label>天（选填）</label></td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td><span class="fcR" style="color: red;">自激活卡生成之日起，在填写天数内未激活的激活卡自动失效</span></td>
				</tr>
			</table>
		</div>
		<!-- 单个激活对话框 -->
		<div id="activition_card_dialog"
			data-options="closed:true,modal:true,title:'单个激活',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 300px;">
			<table>
				<input type="hidden" id="a_id">
				<!-- 	 	 	<tr>
	    		<td>用户信息：</td><td><input type="text" id="a_userInfo" readonly></td>
	    	</tr> -->
				<tr>
					<td>激活码：</td>
					<td><input type="text" id="a_actCode" readonly
						style="border: 0; width: 200px;"></td>
				</tr>
				<tr>
					<td>MAC：</td>
					<td><input type="text" id="a_mac"></td>
				</tr>
				<tr>
					<td>SN：</td>
					<td><input type="text" id="a_sn"></td>
				</tr>
			</table>
		</div>
		<!-- 生成卡券时弹出的警示对话框 -->
		<!-- 上传excel的结果报告框 -->
		<div id="excel_report_dialog"
			data-options="closed:true,modal:true,title:'上传报告',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 300px;">
				<textarea id= "excel_report"  readonly="readonly" style="width: 400px; height: 180px;"></textarea>
		</div>
		<div></div>
	</div>
</body>

</html>