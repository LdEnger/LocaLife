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
<title>直播批量开通管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/liveTask/live_task_list.js?v=89"></script>
<script type="text/javascript" src="js/liveTask/live_renew_task.js?v=${js_version}"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="js/load_wait.js"></script>
<style type="text/css">
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'直播业务管理'" class="regionCenter">
		<div id="task_toolbar">
		<input type="hidden"  id="hiddenPath"/>
		<input type="hidden" id="roleId" value="${currentUser.roleId}">
		<input type="hidden" id="branchId" value="${currentUser.branchId}">
		<input type="hidden" id="hallId" value="${currentUser.hallId}">
			<div style="height: 30px;">
				<input type="button" id="add_live_task" class="btn btn-success" value="新增直播开通任务">
				<input type="button" id="renew_live_task" class="btn btn-success" value="新增直播续费任务">
			</div>
			<div style="height: 40px;padding-top: 5px;">
				<label>任务名称：</label><input id="queryTaskName"	type="text" />
				<label>产品包名称：</label><input id="queryProductName" type="text" />
				<label>生效时间：</label><input class="easyui-datebox" name="time" id="queryOpenTime" data-options="showSeconds:true"  style="width: 150px">
				<label>-- </label><input class="easyui-datebox" name="time" id="queryCloseTime" data-options="showSeconds:true" style="width: 150px">
			</div>
			<c:if test="${currentUser.roleId==1}">
			<div style="height: 40px;padding-top: 5px;">
				<label>公司名称：</label> 
				<select id="branch" onchange="javascript:initHall(this.value);" >
					<option value="-1">全部</option>
					<c:forEach items="${branchList}" var="bL" varStatus="st">
						<option value="${bL.id}">${bL.branchName}</option>
					</c:forEach>
				</select> 
				<label>营  业  厅 ：</label>
				<select id="hall">
				<option value="-1">全部</option>
					<c:forEach items="${hallList}" var="hL" varStatus="st">
						<option value="${hL.id}">${hL.hallName}</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
			<input type="button" id="query_live_task" class="btn btn-success" value="查询">
		</div>
		<table id="task_table"></table>
	
		<div id="task_dialog" data-options="closed:true,modal:true,title:'批量开通直播任务',iconCls:'icon-save'" style="padding: 5px; width: 400px; height: 400px;">
			<table>
				<tr>
					<td>任务名称：</td>
					<td>
						<input id = "batch_taskName" name="batch_taskName"  type="text" />
					</td>
				</tr>
				<tr>
					<td>任务描述：</td>
					<td>
						<input id = "batch_taskDesc" name="batch_taskDesc"  type="text" />
					</td>
				</tr>
				<tr>
					<input id = "batch_branchId" name="batch_branchId" style="color:#D3D3D3" type="hidden"  value="${currentUser.branchId}"  data-options="disabled:true" />
					<td>分公司名称：</td>
					<td><input id = "batch_branchName" name="batch_branchName" type="text" /></td>
				</tr>
				<tr>
					<td>营业厅名称：</td>
					<td><input id = "batch_hallName" name="batch_hallName" type="text" /></td>
				</tr>
				<tr>
					<td>产品包名称：</td>
					<td>
						<input id = "batch_productId" name="batch_productId" class="easyui-combobox"  type="text" />
					</td>
				</tr>
				<tr>
			    	<td>结束日期：</td>
			    	<td>
			    		<input type="text" id="batch_endTime" class="Wdate" onClick="WdatePicker({el:'batch_endTime',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',onpicked:batchComputeTimeDifference})" />
			    	</td>
			    </tr>
			    <tr>
			    	<td>开通时长：</td>
			    	<td>
			    		<input type="text" id="batch_year" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" onblur="getYear('batch_')" />年
			    		<input type="text" id="batch_month" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" onblur="getMonth('batch_')" />月
			    		<input type="text" id="batch_day" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" onblur="getDay('batch_')" />天
			    	</td>
			    </tr>
			    <tr>
			   		<td>导入Excel:</td>
			   		<td>
			   			<span id="ImportFileName" >上传的文件名显示在这里</span>
			   			<input type="button" id="import_live" class="btn btn-success" value="选择">
			   		</td>
			    </tr>
			    <tr>
			   		<td>下载Excel模板:</td>
			   		<td>
			   			<input type="button"  class="btn btn-success" value="下载" onclick="javascript:window.location.href='<%=basePath%>excelDemo/batchOpenLiveDemo.xlsx'"/>
			   		</td>
			    </tr>
			</table>
		</div>
		<div id="task_detail_dialog" data-options="closed:true,modal:true,title:'批量开通直播任务',iconCls:'icon-save'" style="padding: 5px; width: 500px; height: 500px;">
			<table id="task_detail_table"></table>
		</div>

		<div id="renew_task_dialog" data-options="closed:true,modal:true,title:'批量续费任务',iconCls:'icon-save'" style="padding: 5px; width: 400px; height: 400px;">
			<input type="hidden"  id="renew_hiddenPath"/>
			<table>
				<tr>
					<td>任务名称：</td>
					<td>
						<input id = "renew_batch_taskName" name="renew_batch_taskName"  type="text" />
					</td>
				</tr>
				<tr>
					<td>任务描述：</td>
					<td>
						<input id = "renew_batch_taskDesc" name="renew_batch_taskDesc"  type="text" />
					</td>
				</tr>
				<tr>
					<input id = "renew_batch_branchId" name="renew_batch_branchId" style="color:#D3D3D3" type="hidden"  value="${currentUser.branchId}"  data-options="disabled:true" />
					<td>分公司名称：</td>
					<td><select id = "renew_batch_branchName" name="renew_batch_branchName" type="text" ></select></td>
				</tr>
				<tr>
					<td>营业厅名称：</td>
					<td><select id = "renew_batch_hallName" name="renew_batch_hallName" type="text" ></select></td>
				</tr>
				<tr>
					<td>产品包名称：</td>
					<td>
						<input id = "renew_batch_productId" name="renew_batch_productId" class="easyui-combobox"  type="text" />
					</td>
				</tr>
				<tr>
					<td>开通时长：</td>
					<td>
						<input type="text" id="renew_batch_year" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0"  />年
						<input type="text" id="renew_batch_month" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0"  />月
						<input type="text" id="renew_batch_day" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0"  />天
					</td>
				</tr>
				<tr>
					<td>导入Excel:</td>
					<td>
						<span id="renew_ImportFileName" >上传的文件名显示在这里</span>
						<input type="button" id="renew_import_live" class="btn btn-success" value="选择">
					</td>
				</tr>
				<tr>
					<td>下载Excel模板:</td>
					<td>
						<input type="button"  class="btn btn-success" value="下载" onclick="javascript:window.location.href='<%=basePath%>excelDemo/batchOpenLiveDemo.xlsx'"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>