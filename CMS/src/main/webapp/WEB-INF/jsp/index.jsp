<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.hiveview.entity.sys.SysAuth"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.String"%>
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
<script type="text/javascript" src="js/common.js?v=${js_version}"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="report/js/windowControl.js"></script>
<script type="text/javascript" src="report/js/mask.js"></script>

<style type="text/css">
#pageOverlay{visibility:hidden;position:fixed;top:0;left:0;z-index:1987;width:100%;height:100%;background:#000;filter:alpha(opacity=70);opacity:0.7;text-align:center;}
/*IE6 fixed*/
* html{background:url(*) fixed;}
* html body{margin:0;height:100%;}
* html #pageOverlay{position:absolute;left:expression(documentElement.scrollLeft+documentElement.clientWidth-this.offsetWidth);top:expression(documentElement.scrollTop+documentElement.clientHeight-this.offsetHeight);}
</style>
<script language="javascript">
(function() {
	// 获取对象
	var $ = function(id) {
		return document.getElementById(id);
	};
	// 遍历
	var each = function(a, b) {
		for (var i = 0,
		len = a.length; i < len; i++) b(a[i], i);
	};
	// 事件绑定
	var bind = function(obj, type, fn) {
		if (obj.attachEvent) {
			obj['e' + type + fn] = fn;
			obj[type + fn] = function() {
				obj['e' + type + fn](window.event);
			}
			obj.attachEvent('on' + type, obj[type + fn]);
		} else {
			obj.addEventListener(type, fn, false);
		};
	};
	// 移除事件
	var unbind = function(obj, type, fn) {
		if (obj.detachEvent) {
			try {
				obj.detachEvent('on' + type, obj[type + fn]);
				obj[type + fn] = null;
			} catch(_) {};
		} else {
			obj.removeEventListener(type, fn, false);
		};
	};
	// 阻止浏览器默认行为
	var stopDefault = function(e) {
		e.preventDefault ? e.preventDefault() : e.returnValue = false;
	};
	// 获取页面滚动条位置
	var getPage = function() {
		var dd = document.documentElement,
		db = document.body;
		return {
			left: Math.max(dd.scrollLeft, db.scrollLeft),
			top: Math.max(dd.scrollTop, db.scrollTop)
		};
	};
	// 锁屏
	var lock = {
		show: function() {
			$('pageOverlay').style.visibility = 'visible';
			var p = getPage(),
			left = p.left,
			top = p.top;
			// 页面鼠标操作限制
			this.mouse = function(evt) {
				var e = evt || window.event;
				stopDefault(e);
				scroll(left, top);
			};
			each(['DOMMouseScroll', 'mousewheel', 'scroll', 'contextmenu'],
			function(o, i) {
				bind(document, o, lock.mouse);
			});
			// 屏蔽特定按键: F5, Ctrl + R, Ctrl + A, Tab, Up, Down
			this.key = function(evt) {
				var e = evt || window.event,
				key = e.keyCode;
				if ((key == 116) || (e.ctrlKey && key == 82) || (e.ctrlKey && key == 65) || (key == 9) || (key == 38) || (key == 40)) {
					try {
						e.keyCode = 0;
					} catch(_) {};
					stopDefault(e);
				};
			};
			bind(document, 'keydown', lock.key);
		},
		close: function() {
			$('pageOverlay').style.visibility = 'hidden';
			each(['DOMMouseScroll', 'mousewheel', 'scroll', 'contextmenu'],
			function(o, i) {
				unbind(document, o, lock.mouse);
			});
			unbind(document, 'keydown', lock.key);
		}
	};
	bind(window, 'load',
	function() {
		//$('btn_lock').onclick = function() {
		//	lock.show();
		//};
		//$('pageOverlay').onclick = function() {
		//	lock.close();
		//};
	});
	var timesize =0;
	setInterval(function (){
		timesize++;
		if(timesize%2){
			//lock.show();
		}else{
			//lock.close();
		}
	},60000);
	
})();
</script>

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
	<div style="height:auto;overflow: hidden;overflow-y:auto;">
	
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
							String action = csa.getAuthAction();
							if(action.length()>9&&"action=".equals(action.substring(action.length()-8,action.length()-1))){
							%>
								<li><a href="${localLifeDomain}<%=csa.getAuthAction() %>&${link}" target="mainframe" name="menu"><%=csa.getAuthName() %></a></li>
							<%
							}else{
							%>
								<li><a href="<%=csa.getAuthAction() %>" target="mainframe" name="menu"><%=csa.getAuthName() %></a></li>
							<%
							}
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
<div id="pageOverlay">
	<br><br>
	<font color=red size=72>正在升级,<br><br>请勿继续操作</font>
</div>
</body>
</html>