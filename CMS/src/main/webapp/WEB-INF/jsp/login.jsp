<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大麦增值业务运营平台</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/user_login.js?v=${js_version}"></script>
<style type="text/css">
#container{text-align: center;height: 100%;}
.content{margin:auto auto;position:relative;width:890px;height:254px;background-image: url("images/loginBg.png");}
.leftInfo{float:left;width:480px;}
.fontCss_login{font-size:14px;font-family:"微软雅黑","宋体";}
.formInfo{float:left;padding-top: 51px;height:254px;}
.inputCss{width:180px;}
.submit_reset{width:78px; height:25px; border:none; background-image: url("images/btnBg.jpg");color:#FFFFFF}
#loginerrorinfo{color:red;height:20px;}
#loginForm .warm{border:1px solid red;}
.tdH{height:30px;}
</style>
<script type="text/javascript">
$.ajaxSetup({ 
	cache: false 
}); 

$(function(){
	locationCenter();
});
$(window).resize(function() {
	locationCenter();
});

function locationCenter(){
	var windowobj = $(window);
	var browserheight = windowobj.height();
	var	scrollTop = windowobj.scrollTop();
	$("#container").height(browserheight);
	$("#content").css("top",(browserheight-270)/2-51);
}
</script>
</head>
<body bgcolor="#F8F8F8">
<div id="container">
<div id="content" class="content">
<div class="leftInfo">　</div>
<div id="formInfo" class="formInfo">
<div id="loginerrorinfo">${loginInfo}</div>
<form id="loginForm" method="post" class="loginForm" action="<%=basePath%>login.html">
<table>
<tr>
<td class="tdH"><span class="fontCss_login">用户名：</span></td><td><input id="userMail" type="text" class="inputCss" name="userMail" value=""></td>
</tr>
<tr>
<td class="tdH"><span class="fontCss_login">密　码：</span></td><td><input id="userPwd" type="password" class="inputCss" name="userPwd" value=""></td>
</tr>
<tr>
<td class="tdH" colspan="2"><button id="submit_login" class="submit_reset" type="button">登录</button>　<button id="reset_login" class="submit_reset" type="button">重填</button></td>
</tr>
</table>
</form>
</div>
<div style="clear:both;"></div>
<div id="Copyright">家视天下 &copy; 2013 hiveview.com</div>
</div>
</div>
</body>
</html>