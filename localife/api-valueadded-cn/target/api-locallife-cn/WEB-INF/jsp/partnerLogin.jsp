<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大麦增值业务运营平台</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<!-- <link rel="stylesheet" type="text/css" href="css/login.css">
<link rel="stylesheet" href="css/jquery/easyui.css" /> -->
<link rel="stylesheet" type="text/css" href="css/login_new.css">
<script>
var partnerFlag = true;
</script>
</head>
<body>
<form id="loginForm" method="post" class="loginForm" action="plogin.html">
<input type="hidden" id="exponent" value="${exponent}"/>
<input type="hidden" id="modulus" value="${modulus}"/>
<input type="hidden" name="p" value="1"/>
<input type="hidden" id="enPassword" name="enPassword" />
<div class="container">
	<div class="logP">
		<div class="inn">
		
		
			<ul class="dlC2">
				<li><h3 class="tit">大麦增值业务运营平台</h3></li>
				<li class="dl09">
					<p class="l_ts">${logfail}</p>
				</li>
				<li class="dl01">
					<div class="d_input"><i class="i_01"></i>
						<input type="text" class="inputC" title="请输入邮箱" id="userMail" name="username" size="100" value=""/>
						<label class="label">请输入邮箱</label>
					</div>
				</li>
				<li class="dl02">
					<div class="d_input"><i class="i_02"></i>
						<input type="password" class="inputC" title="请输入密码" id="userPwd" name="password"  size="100" value=""/>
						<label class="label">请输入密码</label>
					</div>
				</li>
				<c:if test="${kaptchaEbabled}">
				<li class="dl06">
					<div class="d_input"> <i class="i_02"></i>
						<input type="text" class="inputC" title="请输入验证码" id="kaptcha" name="kaptcha" value="" />
						<label class="label">请输入验证码</label>
					</div>
				</li>
				<li class="dl07">
					<div class="yzm">
						<img src="captcha-image.html" alt="" id="kaptchaImage" class="kaptchaImg" style="margin-bottom: -3px"/>
					</div>
					<a href="javascript:void(0)" class="l_ts">看不清?换一张</a>
				</li>
				</c:if>
				<li class="dl05"><a href="javascript:void(0)" class="wjmm" id="wjmm" onclick="showForget()">忘记密码？</a></li>
				<li class="dl03"><a href="javascript:void(0)" class="an_dl" id="submit_login">登录</a></li>
				<!-- <li class="dl04">
					<div class="dxC">
						<input type="checkbox" checked/>
						下次自动登录</div> -->
			</ul>
		</div>
	</div>
	
</div>
</form>
</body>
</html>
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="report/js/windowControl.js"></script>
<script type="text/javascript" src="report/js/mask.js"></script>
<script type="text/javascript" src="js/prng4.js"></script>
<script type="text/javascript" src="js/rng.js"></script>
<script type="text/javascript" src="js/jsbn.js"></script>
<script type="text/javascript" src="js/rsa.js"></script>
<script type="text/javascript" src="js/base64.js"></script>
<script type="text/javascript" src="js/user_login.js?v=31"></script>
<script>
$(function() {
    $(".dlC2 .inputC").val('');
    $(".dlC2 .inputC").focus(function() {
        var $len = $(this).length;
        if ($len > 0) {
            $(this).next(".label").css("display", "none");
        } else {
            $(this).next(".label").css("display", "block");
        }

    });
    $(".dlC2 .inputC").blur(function() {
        var value = $(this).val();
        if (value.length == 0) {
            $(this).next(".label").css("display", "block");
        }
    });
    $(".dlC2 .label").click(function() {
        $(this).prev(".dlC2 .inputC").focus();
    });
	if (typeof(kaptcha)!='undefined') {
		$('.l_ts').click(function () {//生成验证码  
			$('.kaptchaImg').hide().attr('src', 'captcha-image.html?' + Math.floor(Math.random()*100) ).fadeIn(); });
	}
})

var jq = jQuery;
function showForget(){
	jq.createWin({
        title:"忘记密码",
        url:'forget.html',
        height:280,
        width:540,
        buttons:[],
		onClose:function(targetjq){
		},
		onComplete:function(dailog,targetjq){
		}
    });
}
</script>