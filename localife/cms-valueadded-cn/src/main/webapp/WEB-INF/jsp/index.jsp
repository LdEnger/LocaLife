<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.hiveview.entity.sys.SysAuth"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<!-- <link rel="stylesheet" type="text/css" href="css/main.css?v=4"> -->
<link rel="stylesheet" type="text/css" href="css/main_new.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/common.js?v=11"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="report/js/windowControl.js"></script>
<script type="text/javascript" src="report/js/mask.js"></script>

</head>
<body>
<div data-options="fit:true" class="easyui-layout"> 
<div data-options="region:'north',split:true,border:false" class="header">
	<div class="inn"><div class="logo">
		<h1>大麦活动卡券管理系统</h1>
		<a href="javascript:void(0)"><img src="pic/logo.png" alt="大麦活动卡券管理系统" /></a> </div>
		
	<div class="h_yh"><span class="user">欢迎您： ${currentUser.userName }</span>
	<!-- <a href="helpdoc/help.html" class="an_tc" target="mainframe" style="margin-right:10px;">帮助</a> -->
	<!-- <a href="partner/changePwd.html" class="an_tc" target="mainframe" style="margin-right:10px;">修改密码</a> -->
	<a href="plogout.html" class="an_tc">退出</a>
	</div></div>
</div>
<!--s1-->
	<div data-options="region:'west',split:false" class="s1">
	<div style="height:480px;overflow: hidden;overflow-y:auto;">
	
	<%
		List<SysAuth> topMenues = (ArrayList<SysAuth>)session.getAttribute("topMenues");
		HashMap<Integer,List<SysAuth>> menuMap = (HashMap<Integer,List<SysAuth>>)session.getAttribute("menuMap");
		if(topMenues==null)topMenues = new ArrayList<SysAuth>();
		for(int i=0;i<topMenues.size();i++){
			SysAuth sa = topMenues.get(i);
			%>
		<div class="s_nav">		
		<div class="s_cap">
			<h3><%=sa.getAuthName()%></h3>
		</div>
		<div class="s_tip"> 
			<ul>
				<%
					List<SysAuth> children = menuMap.get(sa.getAuthId());
					if(children!=null){
						for(SysAuth csa:children){
							%>
							<li><a href="<%=csa.getAuthAction() %>" target="mainframe" name="menu"><%=csa.getAuthName() %></a></li>
							<%
						}
					}
				%>
			</ul>
		</div>
		</div>
			<%		
		}
	%>
	</div>
</div>
<div data-options="region:'center'" class="center">
<iframe frameborder="0" scrolling="no" allowtransparency="true" name="mainframe" id="mainframe" width="100%" height="100%" src="welcome.html" style="moz-margin-top:10px;"></iframe>
</div>
</div>
</body>
</html>