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
<title>活动管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/award/award_code.js?v=${js_version}"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<style>
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
input[type="text"]{
	width:12%;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="title:'活动管理',region:'center'" class="regionCenter">
		<div id="award_toolbar">
			<input id="awardActivityId" type="hidden" value="${awardActivity.id}" />
			<c:if test="${isShow}">
				<input id="add_award_code" type="button" value="导入中奖码" class="btn btn-success" />
				<input id="detail_id" type="text" />
				<input type="button" value="清空中奖码" class="btn btn-success" onclick="clearAwardCode()" />
			</c:if>
			
			
			<input id="export_award_code" type="button" value="导出中奖码" class="btn btn-success" onclick="initDownLoad(${awardActivity.id})" />
			<input type="button" value="返回" class="btn btn-success" style="float:right;" onclick="javascript:window.location.href='awardActivity/show.html'" />
			<br>
			<label>手机号：</label>
			<input id="userPhone" type="text" /> 
			<label>中奖码：</label>
			<input id="awardCode" type="text" />
			<label>奖品名称：</label>
			<input id="awardName" type="text" /> 
			<label>奖品类型：</label>
			<input id="awardType" type="text" /> 
			<label>中奖状态：</label>
			<select id="acceptFlag">
				<option value="0">请选择</option>
				<option value="1">中奖</option>
				<option value="2">未中奖</option>
			</select> 
			<input id="query_award" type="button" value="查询" class="btn btn-success" style="float:right;margin-top:5px;" />
		</div>
		<table id="award_code_table">
		</table>
	</div>
	<div id="import_code_dialog" data-options="closed:true,modal:true,title:'导入',iconCls:'icon-save'" style="padding: 5px; width: 500px; height: 300px;">
		<input id="hiddenPath" type="hidden" />
		<table>
			<tr>
				<td>
					活动名称:
				</td>
				<td>
					${awardActivity.title}
				</td>
			</tr>
			<tr>
				<td>
					下载模板:
				</td>
				<td>
					<input type="button" value="下载模板" onclick="downLoadModel(${awardActivity.id})" />
				</td>
			</tr>
			<tr>
				<td>
					导入:
				</td>
				<td>
					<span id="ImportFileName">上传的文件名显示在这里</span>
					<input id="importCodeExcel" type="button" value="选择" class="btn btn-success" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span style="color:red;">您只需你填写模板中的中奖码一栏中即可。若修改其他项内容，会导致导出失败！请勿修改！</span>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>