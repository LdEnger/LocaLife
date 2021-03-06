<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>分公司管理</title>
<link href="css/pop.css?v=3" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="js/common/uploadify3.1/uploadify.css" type="text/css" />
<script type="text/javascript" src="js/common/uploadify3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="report/js/extend.js"></script>
</head>
<style type="text/css">
.bzP {
	margin-bottom: 10px;
}

.bzP,.bzC,.bzC li,.fgx {
	clear: both;
	width: 100%;
	overflow: hidden;
}

.fgx {
	width: 98%;
	height: 1px;
	margin: 0 auto;
	border-bottom: 1px dashed #ccc;
}

.bzC {
	line-height: 22px;
	padding: 5px 0;
}

.bzC .b01,.bzC .b02 {
	float: left;
}

.bzC .b01 {
	margin-right: 15px;
}

.bzC label {
	float: left;
	padding: 0 5px;
}

.b03 .b_info {
float:left;
width:400px;
padding:0;
	word-wrap: break-word;
}

.bzC .fcH {
	color: #666;
}
.d_m1 {
	color: red;
}
</style>
<body class="easyui-layout">
	<input type="hidden" id="hidProvinceCodeId" value = "${user.provinceCode}">
	<input type="hidden" id="hidProvinceNameId" value="${user.provinceName}">
	<input type="hidden" id="hidCityCodeId" value="${user.cityCode}">
	<input type="hidden" id="hidCityNameId" value="${user.cityName}">
	<form id="partnerform" >
		<center>
			<table>
			<tr>
				<td>分公司</td>
				<td><input type="text" id="newBranchName"/>
				</td>
			</tr>
			<tr>
				<td colspan="2"><div id="branchNameText"></div></td>
			</tr>
			</table>	
		</center>
	</form>
</body>
<script type="text/javascript">
var flag=true;
$("#newBranchName").blur(function(){
	  var branchName=$("#newBranchName").val();
	  var url="branch/getBranchName.json";
	  $.post(url,{"branchName":branchName},function(data){
		  if(data.success=="true"){
			  $("#branchNameText").html("<span class='d_m1'>该名称已存在，请重新输入！</span>");
			  flag=false;
		  }
	  },"json");
});
	function branchAdd(winId) {
		if(flag==false){
			return;
		}else if(flag){
			var returnval = {};
			var branchName = $("#newBranchName").val();
		 	if (branchName== "") {
				alert("请填写分公司名称");
				return;
			}
		 	var branch = {
		 			"branchName":branchName,
					"provinceCode":$("#hidProvinceCodeId").val(),
					"provinceName":$("#hidProvinceNameId").val(),
					"cityCode":$("#hidCityCodeId").val(),
					"cityName":$("#hidCityNameId").val()
			}
			$.ajax({ //加载活动明细
				type:"post",
				url:"branch/addBranch.json", 
				data:branch,
				async : false,
				success:function(data) {
					$("#" + winId).dialog("close");
					returnval = data;
				},
				dataType:"json"
			});
			return returnval;
		}
	}
</script>
</html>
