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
<script type="text/javascript">
$(function(){
	if(${notHide1}){
		$("#realNameDiv").css('display','block'); 
	}
	if(${notHide2}){
		$("#userAddrDiv").css('display','block'); 
	}
	if(${notHide3}){
		$("#userPhoneDiv").css('display','block'); 
	}
	if(${notHide4}){
		$("#postcodeDiv").css('display','block'); 
	}
	login();
});
function login(){
	$('#login').click(function(){
		var prom = 1;
		if(${notHide1}){
			if($.trim($('#realName').val())==''){
				prom=2;
			}
		}
		if(${notHide2}){
			if($.trim($('#userPhone').val())==''){
				prom=2;
			}
		}
		if(${notHide3}){
			if($.trim($('#userAddr').val())==''){
				prom=2;
			}
		}
		if(${notHide4}){
			if($.trim($('#postcode').val())==''){
				prom=2;
			}
		}
		if(prom==2){
			return;
		}
		var reNum = /[^0-9]/;
		//邮编校验
		if(reNum.test($('#postcode').val())){
			alert("邮编校验应为数字");
			return false;
		}
		//邮编校验
		if($('#postcode').val().length!=6){
			alert("邮编编码应为6位");
			return false;
		}
		//手机校验
		var phonRe = /^1[3|4|5|8][0-9]\d{4,8}$/;
		if(!phonRe.test($('#userPhone').val())){
			alert("手机号填写错误,手机号应为11位数字");
			return false;
		}
		var userId=$('#userId').val();
		var awardCode=$('#awardCode').val();
		var detailId=$('#detailId').val();
		if($.trim(awardCode)==''||$.trim(userId)==''||$.trim(detailId)==''){
			alert("用户中奖数据不完整");
			return false;
		}
		$("#form1").attr("action", "<%=basePath %>award/awardMail/add.html"); 
		$("#form1").attr("method", "post"); 
		$("#form1").submit();
	});
}
</script>
</head>
<body>
<div class="container">
		<form id="form1" class="form-signin">
		<h2 class="form-signin-heading">请输入地址</h2>
		<input type="hidden" id="userId" name="userId" value="${userId}" />
		<input type="hidden" id="awardCode" name="awardCode" value="${awardCode}" />
		<input type="hidden" id="detailId" name="detailId" value="${detailId}" />
		<input type="hidden" id="awardOrderId" name="awardOrderId" value="${awardOrderId}" />
		<input type="hidden" id="awardPlayId" name="awardPlayId" value="${awardPlayId}" />
		<div id="realNameDiv" class="form-group" style="display:none">
			<label>姓名</label> 
			<input type="text" class="form-control" id="realName" name="realName" placeholder="收件人姓名（先生/女士）" required>
		</div>
		<div id="userPhoneDiv" class="form-group" style="display:none">
			<label>手机号</label> 
			<input type="text" class="form-control" id="userPhone" name="userPhone" placeholder="输入您的手机号" required>
		</div>
		<div id="postcodeDiv" class="form-group" style="display:none">
			<label>邮编</label> 
			<input type="text" class="form-control" id="postcode" name="postcode" placeholder="输入邮编" required>
		</div>
		<div id="userAddrDiv" class="form-group" style="display:none">
			<label>邮寄地址</label> 
<!-- 			<input type="text" class="form-control" id="userAddr" name="userAddr" placeholder="邮寄地址"> -->
			<textarea class="form-control" id="userAddr" name="userAddr" placeholder="邮寄地址" rows="3" required></textarea>
		</div>
		<!-- <input type="button" value="提交" id="login" class="btn btn-lg btn-primary btn-block"> -->
		<button class="btn btn-lg btn-primary btn-block" type="submit" id="login">提交</button>
		</form>
</div>
</body>

</html>