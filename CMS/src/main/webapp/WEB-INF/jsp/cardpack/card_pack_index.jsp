<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<script type="text/javascript">
	<%--var isBranchUser =${isBranchUser};--%>
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大麦卡列表</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/load_wait.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	<%--var activityDataList =new Array();--%>
	<%--<c:forEach var="list" items="${list}">--%>
		<%--activityDataList['${list.productName}']='${list.chargingStr}';--%>
	<%--</c:forEach>--%>

</script>
<script type="text/javascript" src="js/cardpack/cardpack.js?v=${js_version}"></script>
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
	<div data-options="region:'center',title:'大麦卡管理'" class="regionCenter">
		<input type="hidden" id="hidBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="hidHallId" value="${currentUser.hallId}">
		<input type="hidden" id="hidBranchName"
			value="${currentUser.branchName}"> <input type="hidden"
			id="hidHallName" value="${currentUser.hallName}">
		<input type="hidden" id="hidZoneId" value="${currentUser.zoneId}">
		<input type="hidden" id="hidZoneName" value="${currentUser.zoneName}">
		<input type="hidden" id="hidCityId" value="${currentUser.cityCode}">

		<div id="activity_toolbar">
			<input type="button" id="add_activity" class="btn btn-success"
				value="批量制卡" <%--<c:if test="${isBranchUser == 0}">
						style="display:none"
			</c:if>--%>>
			<input type="button" id="export_activity" class="btn btn-success"
				value="批量提取"  >
			<label>卡提供方：</label>
			<select id="search_card_provider">
				<option value="-1">全部</option>
				<option value="0">腾讯连续卡</option>
				<option value="1">奇艺包月卡</option>
			</select>
			<label>使用状态：</label>
			<select id="status">
				<option value="-1">全部</option>
				<option value="0">未使用</option>
				<option value="1">已使用</option>
			</select>
			<label>提取状态：</label>
			<select id="extract_status">
				<option value="-1">全部</option>
				<option value="0">未提取</option>
				<option value="1">已提取</option>
			</select>
			<label>制卡状态：</label>
			<select id="create_status">
				<option value="-1">全部</option>
				<option value="0">未制卡</option>
				<option value="1">已制卡</option>
			</select><br>

			<label>续费月数：</label>
			<input id="select_renew_num" type="text"  onkeyup="value=value.replace(/[^\d]/g,'')" />

			<label>卡密：</label>
			<input id="dm_card_val" type="text" />

			<label>UserID：</label>
			<input id="user_id" type="text"  onkeyup="value=value.replace(/[^\d]/g,'')" />

			<label>制卡时间：</label>	<input id="create_startTime" /> <label>--</label> <input id="create_endTime" /><br>
			<label>开通时间：</label>	<input id="open_startTime" /> <label>--</label> <input id="open_endTime" />

			<label>分公司：</label><select id="q_branch"></select>
			<label>mac：</label>
			<input id="mac" type="text" />
			<label>sn：</label>
			<input id="sn" type="text" />

			<input type="button" id="query_activity" class="btn btn-success" value="查询">


		</div>
		<table id="activity_table"></table>


		<div id="activity_detail_dialog"
			data-options="closed:true,modal:true,title:'批量制卡',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 340px;">
			<input type="hidden" id="id"> <input type="hidden"
				id="hiddenUserName" value="${currentUser.userName}"> <input
				type="hidden" id="hiddenUserId" value="${currentUser.userId}">
			<input type="hidden" id="hiddenRoleId" value="${currentUser.roleId}">
			<input type="hidden" id="hiddenProductId" value=""> <input
				type="hidden" id="hiddenProductName" value=""> <input
				type="hidden" id="hiddenChargingId" value=""> <input
				type="hidden" id="hiddenChargingName" value=""> <input
				type="hidden" id="hiddenPrice" value=""> <input
				type="hidden" id="hiddenChargingDuration" value=""> <input
				type="hidden" id="hiddenChargingPic" value="">
			<table class=tableC3>
				<tr>
					<th>数量：</th>
					<td><input type="text" id="cardNum" onkeyup="value=value.replace(/[^\d]/g,'')"  ></td>
				</tr>
				<tr>
					<th>提供方卡类型：</th>
					<td>
						<select id="card_provider" >
								<option value="0">腾讯连续卡</option>
								<option value="1">奇艺包月卡</option>
						</select>

					</td>
				</tr>
				<tr>
					<th>月数：</th>
					<td>
						<input type="text" id="renew_num" value="12" onkeyup="value=value.replace(/[^\d]/g,'')" >
					</td>
				</tr>
				<tr>
					<th>分公司：</th>
					<td>
						<select id="add_branch"></select>
					</td>
				</tr>
				<tr>
					<th>是否VIP：</th>
					<td>
					<select id="if_vip" onchange="select()">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
					</td>
				</tr>
				<tr id="vip_tr" style="display:none">
					<th>月数：</th>
					<td><input type="text" id="vip_days" value="0" onkeyup="value=value.replace(/[^\d]/g,'')" ></td>
				</tr>
			<%--	<tr>
					<th>分公司：</th>
					<td><input type="text" id="companyName"></td>
				</tr>--%>
			</table>
		</div>

		<!-- 提取信息 dialog -->
		<div id="export_detail_dialog"
			data-options="closed:true,modal:true,title:'批量提取',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 340px;">
			 <input type="hidden" id="export_hiddenUserName" value="${currentUser.userName}">
			 <input type="hidden" id="export_hiddenUserId" value="${currentUser.userId}">
			 <input type="hidden" id="export_hiddenRoleId" value="${currentUser.roleId}">
			<table class=tableC3>
				<tr>
					<th>数量：</th>
					<td><input type="text" id="export_cardNum" onkeyup="value=value.replace(/[^\d]/g,'')"></td>
				</tr>
				<tr>
					<th>提供方卡类型：</th>
					<td>
						<select id="export_cardProvider" onchange="select_aqytx()">
								<option value="0">腾讯连续卡</option>
								<option value="1">奇艺包月卡</option>
						</select>
					</td>
				</tr>

				<tr id="ayq_vip" style="display:none">
					<th>爱奇艺包月次数：</th>
					<td>
						<input type="text" id="export_renewNum" value="12" onkeyup="value=value.replace(/[^\d]/g,'')">
					</td>
				</tr>
				<tr id="tx_vip">
					<th>腾讯连续包月次数：</th>
					<td>
						<input type="text" id="tx_export_renewNum" value="12" onkeyup="value=value.replace(/[^\d]/g,'')">
					</td>
				</tr>

				<tr>
					<th>分公司：</th>
					<td>
						<select id="export_branch"></select>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>