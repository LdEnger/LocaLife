<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>用户地址</title>
<script type="text/javascript" src="../../js/common/jquery/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="../../css/award/award_user.css">
</head>
<body>
<div class="container">
<div id="msg1" class="form-signin">
<span class="glyphicon glyphicon-save" aria-hidden="true" style="font-size:50px"></span>
<br/>
<br/>
<div style="font-size:30px">${msg}</div>
</div>
</div>
</body>
</html>