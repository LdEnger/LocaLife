<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath(); 
	String basePath = request.getScheme() + "://" 
			+ request.getServerName() + ":" + request.getServerPort() 
			+ path + ""; 
%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<title>大麦活动 API</title>
<link href="../../js/api/api_main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="../../js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js/api/format.js"></script>
<script>
	$(function() {
// 		var tempdate = Date.parse(new Date());
// 		var a = document.getElementsByName("timestamp");
// 		for(var i=0; i<a.length; i++) {
// 			a[i].value=tempdate;
// 		}
		
		$('#ajaxloading').ajaxStart(function() {
			$(this).show();
		});
		$('#ajaxloading').ajaxStop(function() {
			$(this).hide();
		});

		$('.api_test_btn').click(function() {
			var div_block = $(this).parent().parent();
			var api_full_name = div_block.find('#api_name').html();
			var api_method = div_block.find('#api_method').html();
			var params = '';

			div_block.find('#params_list').find('input').each(function(i, v) {
				api_full_name += "/" + $(this).val();
			})

			var bp = "<%=basePath%>";
			api_full_name = bp + api_full_name + ".json";
			console.log(api_full_name);
			$.ajax({
				//data:params,
				type : api_method,
				dataType : 'json',
				url : api_full_name,
				success : function(resp) {
					var json = Process(JSON.stringify(resp));
					div_block.find('.return_area').html(json);
				},
				error : function() {
					alert('请求失败,请稍后再试!');
				}
			})
		});
		
		$('.api_test_btn_q').click(function() {
			var div_block = $(this).parent().parent();
			var api_full_name = div_block.find('#api_name').html();
			var api_method = div_block.find('#api_method').html();
			var params = '';
			var bp = "<%=basePath%>";
			api_full_name = bp + api_full_name + ".json";
			
			div_block.find('#params_list').find('input').each(function(i, v) {
				if(i==0){
					api_full_name+="?"+$(this).attr("name")+"="+$(this).val();
				}else{
					api_full_name+="&"+$(this).attr("name")+"="+$(this).val();
				}
			})
			
			console.log(api_full_name);
			$.ajax({
				//data:params,
				type : api_method,
				dataType : 'json',
				url : api_full_name,
				success : function(resp) {
					var json = Process(JSON.stringify(resp));
					div_block.find('.return_area').html(json);
				},
				error : function() {
					alert('请求失败,请稍后再试!');
				}
			})
		});

		$('.post_btn').click(
				function() {
					var div_block = $(this).parent().parent();
// 					var host = window.location.host;
// 					if (host.indexOf('localhost') == 0) {
// 						host += "/";
// 					}
					var bp = "<%=basePath%>";
					var host = bp + "/";
					var api_full_name = div_block.find('#api_name').html();
					var api_method = div_block.find('#api_method').html();
					var params = '{';
					div_block.find('#params_list').find('input').each(
							function() {
								params += $(this).attr('name') + ':' + "\""
										+ $(this).val() + "\"" + ',';
							})

					api_full_name = host + api_full_name + ".json";
					params = params.substr(0, params.length - 1) + '}';
					var obj = eval('(' + params + ')');
					$.ajax({
						data : obj,
						type : "POST",
						url : api_full_name,
						dataType : 'text',
						success : function(resp) {
							var json = resp;
							//var json = Process(JSON.stringify(resp));
							div_block.find('.return_area').html(json);
						},
						error : function() {
							alert('请求失败,请稍后再试!');
						}
					})
				});

		$('.api_reset_btn').click(function() {
			var div_block = $(this).parent().parent();
			div_block.find('.return_area').html('');
		})
	})
</script>
</head>
<body>
	<div id="title">
		<img src="" style="padding-left: 300px;">大麦活动 API
	</div>
	<div>
		<table align="center" class="versiontable"
			style="border: 1px solid #EEE; width: 93%;">
			<tr style="background-color: #effeef; border: 1px;">
				<td colspan="3" align="center">更新说明</td>
			</tr>
			<tr>
				<td>更新日期</td>
				<td>版本编号</td>
				<td>版本说明</td>
			</tr>
			<tr>
				<td>2016-04-18</td>
				<td>v0.5</td>
				<td></td>
			</tr>
		</table>
	</div>
	<br />
	<div>
		<ol id="menu">
			<li><a href="#/api/award/getActivityHomePageList" title="接口">/api/award/getActivityHomePageList</a></li>
			<li><a href="#/api/award/getActivityHomePage" title="接口">/api/award/getActivityHomePage</a></li>
			<li><a href="#/api/award/getAwardList" title="接口">/api/award/getAwardList</a></li>
			<li><a href="#/api/award/getAwardDetail" title="接口">/api/award/getAwardDetail</a></li>
			<li><a href="#/api/award/drawAward" title="接口">/api/award/drawAward</a></li>
			<li><a href="#/api/activity/acceptAward" title="接口">/api/activity/acceptAward</a></li>
			<li><a href="#/api/award/getGameInfo" title="接口">/api/award/getGameInfo</a></li>
			<li><a href="#/api/award/getAwardDetailForPay" title="接口">/api/award/getAwardDetailForPay</a></li>
			<li><a href="#/api/award/getAwardAddressStatus" title="接口">/api/award/getAwardAddressStatus</a></li>
			<li><a href="#/api/award/getPayInfo" title="接口">/api/award/getPayInfo</a></li>
			<li><a href="#/api/activity/getActivityTypeList" title="接口">/api/activity/getActivityTypeList</a></li>
			<li><a href="#/api/activity/getActivityInfoList" title="接口">/api/activity/getActivityInfoList</a></li>
		</ol>
	</div>
	<!-- 开始 -->
	<a name="/api/award/getActivityHomePageList"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getActivityHomePageList</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getActivityHomePageList/{userId}/{isVip}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取活动专区首页列表</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
				<li>activityId 活动id</li>
				<li>isRight		int	是否拥有资格</li>
				<li>rightDesc		String	资格说明</li>
				<li>title 活动标题</li>
				<li>type 活动类型</li>
				<li>logoUrl 活动logo</li>
				<li>bgUrl 活动列表大背景图</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				isVip = <input type="text" name="isVip" value="2"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	<!-- 开始 -->
	<a name="/api/award/getActivityHomePage"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getActivityHomePage</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getActivityHomePage/{activityId}/{userId}/{isVip}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取单个活动首页信息</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
				<li>isRight 是否拥有资格</li>
				<li>rightDesc 说明</li>
				<li>activityId 活动id</li>
				<li>title 活动标题</li>
				<li>type 活动类型</li>
				<li>playingBgUrl 活动进行时封面</li>
				<li>phoneBindType 是否需要绑定手机</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				activityId = <input type="text" name="activityId" value="1"><span class="notes">*</span><br /> 
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				isVip = <input type="text" name="isVip" value="2"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	<!-- 开始 -->
	<a name="/api/award/getAwardList"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getAwardList</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getAwardList/{userId}/{awardProperty}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取用户所有奖品（折扣卷）信息列表</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
				<li>awardProperty		int	奖品属性</li>
				<li>activityId		int	活动ID</li>
				<li>awardCode		String	中奖码</li>
				<li>title		String	活动名称</li>
				<li>detailId		int	奖品id</li>
				<li>awardName		String	奖品名称</li>
				<li>awardPicUrl		String	奖品缩略图地址</li>
				<li>playTime		String	获奖时间</li>
				<li>availableBeginTime		long	奖品有效开始时间</li>
				<li>availableEndTime		long	奖品有效结束时间</li>
				<li>orderStatus		int	状态</li>
				<li>videoId		int	影片id</li>
				<li>awardDiscount		int	奖品折扣</li>
				<li>awardVideoName		String	影片名称</li>
				<li>awardPrice		Float	奖品原价</li>
				<li>discountType		String	折扣类型</li>
				<li>awardRemark		String	活动备注	解释权、归属权、声明</li>
				
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				awardProperty = <input type="text" name="awardProperty" value="1"><span class="notes">* 填写99为查询全部</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	<!-- 开始 -->
	<a name="/api/award/getAwardDetail"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getAwardDetail</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getAwardDetail/{userId}/{detailId}/{awardCode}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取用户某一奖品详情信息</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
<li>awardProperty		int	奖品属性</li>
<li>discountType		String	折扣卷类型</li>
<li>userId		String	用户id</li>
<li>detailId		int	奖品id</li>
<li>awardCode		String	中奖码</li>
<li>awardRemark		String	活动备注</li>
<li>awardType		varchar	奖品类别</li>
<li>awardName		varchar	奖品名称</li>
<li>awardDesc		String	奖品说明</li>
<li>playTime		Long	获奖时间</li>
<li>availableBeginTime		varchar	奖品有效开始时间</li>
<li>availableEndTime		varchar	奖品有效结束时间</li>
<li>awardPicUrl		String	奖品图片</li>
<li>orderStatus		int	是否领奖</li>
<li>userPhone		varchar	电话</li>
<li>userAddr		varchar	地址</li>
<li>realName		varchar	姓名</li>
<li>qrCodeUrl		varchar	填写信息地址</li>
<li>awardDiscount int	奖品折扣	</li>
<li>awardVideoName String	奖品名称	</li>
<li>awardPrice int	奖品价格	</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				detailId = <input type="text" name="detailId" value="1"><span class="notes">*</span><br /> 
				awardCode = <input type="text" name="awardCode" value="1"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	<!-- 开始 -->
	<a name="/api/award/drawAward"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/drawAward</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/drawAward/{userId}/{userName}/{mac}/{sn}/{deviceCode}/{activityId}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 抽奖接口</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
<li>isWin		int	是否中奖</li>
<li>detailId		int	奖品id</li>
<li>awardProperty		Int	奖品属性</li>
<li>awardName		String	奖品名称</li>
<li>awardType		String	奖品类别</li>
<li>playLeftTimes		int	剩余次数</li>
<li>playPrompt		String	中奖/ 未中奖提示</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				userName = <input type="text" name="userName" value="1"><span class="notes">*</span><br /> 
				mac = <input type="text" name="mac" value="1"><span class="notes">*</span><br /> 
				sn = <input type="text" name="sn" value="1"><span class="notes">*</span><br /> 
				deviceCode = <input type="text" name="deviceCode" value="1"><span class="notes">*</span><br /> 
				activityId = <input type="text" name="activityId" value="1"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	<!-- 开始 -->
	<a name="/api/activity/acceptAward"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">api/activity/acceptAward</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">POST</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/activity/acceptAward.json</div>
		<div class="api_desc">接口说明： 领奖接口</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				orderId = <input type="text" name="orderId" value="1"><span class="notes">*</span><br /> 
			</div>
			<input type="button" class="post_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	
	<!-- 开始 -->
	<a name="/api/award/getGameInfo"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getGameInfo</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getGameInfo/{activityId}/{userId}/{type}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取游戏基本信息接口</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
<li>activityId		int	活动id</li>
<li>type		int	活动类型</li>
<li>infoBgUrl 活动详情页封面</li>
<li>
icons List 奖品图标集合
	<ul>
		<li>detailId int 奖品id</li>
		<li>awardIconUrl String 奖品图标集合</li>
	</ul>
</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				activityId = <input type="text" name="activityId" value="1"><span class="notes">*</span><br /> 
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				type = <input type="text" name="type" value="1"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	
	<!-- 开始 -->
	<a name="/api/award/getAwardDetailForPay"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getAwardDetailForPay</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getAwardDetailForPay/{detailId}/{userId}/{awardCode}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取奖品相关校验信息</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
<li>acceptFlag		tinyint	是否使用</li>
<li>awardProperty		int	奖品属性</li>
<li>awardMlAmount		int	麦粒数量</li>
<li>awardVipDuration		int	vip时长</li>
<li>awardDiscount		int	折扣</li>
<li>awardPrice		Int	价格</li>
<li>awardVideoId		Int	影片id</li>
<li>availableBeginTime		Long	奖品有效开始时间</li>
<li>availableEndTime		Long	奖品有效结束时间</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				detailId = <input type="text" name="detailId" value="1"><span class="notes">*</span><br /> 
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				awardCode = <input type="text" name="awardCode" value="1"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	
	<!-- 开始 -->
	<a name="/api/award/getAwardAddressStatus"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getAwardAddressStatus</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getAwardAddressStatus/{detailId}/{userId}/{awardCode}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取邮寄地址是否填写</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
<li>addressStatus		tinyint	是否填写</li>
<li>qrCodeUrl		String	填写地址页面url</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				detailId = <input type="text" name="detailId" value="1"><span class="notes">*</span><br /> 
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				awardCode = <input type="text" name="awardCode" value="1"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	
	<!-- 开始 -->
	<a name="/api/award/getPayInfo"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/award/getPayInfo</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/award/getPayInfo/{detailId}/{userId}/{awardCode}/{timestamp}/{sign}.json</div>
		<div class="api_desc">接口说明： 获取支付相关信息</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
<li>tradeNo		int	奖品订单号</li>
<li>awardPrice		float	奖品（影片）价格(原价)</li>
<li>payPrice int	支付价格</li>
<li>awardDiscount		int	奖品折扣</li>
<li>awardVideoName		String	影片名称</li>
<li>chargingName			计费名称</li>
<li>chargingDuration			计费时长</li>
<li>partnerId			商户ID</li>
<li>partnerKey			商户密钥</li>
<li>notifyUrl			回调地址</li>
<li>orderAppend			回调时使用的参数</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				detailId = <input type="text" name="detailId" value="1"><span class="notes">*</span><br /> 
				userId = <input type="text" name="userId" value="1"><span class="notes">*</span><br /> 
				awardCode = <input type="text" name="awardCode" value="1"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	
	
	<!-- 开始 -->
	<a name="/api/activity/getActivityTypeList"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/activity/getActivityTypeList</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/activity/getActivityTypeList.json</div>
		<div class="api_desc">接口说明： 播控系统,用于获取所有活动类型</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
<li>acceptFlag		tinyint	是否使用</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
			</div>
			<input type="button" class="api_test_btn_q" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	
	<!-- 开始 -->
	<a name="/api/activity/getActivityInfoList"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/activity/getActivityInfoList</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/activity/getActivityInfoList.json</div>
		<div class="api_desc">接口说明： 获取（抽奖）活动信息列表</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
				<li>activityId 活动id</li>
				<li>title 活动标题</li>
				<li>type 活动类型</li>
				<li>logoUrl 活动logo</li>
				<li>bgUrl 活动列表大背景图</li>
				<li>beginProfileUrl 活动进行时封面</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
			<div id="params_list">
				type = <input type="text" name="type" value="1"><span class="notes">*</span><br /> 
				title = <input type="text" name="title" value="1"><span class="notes">*</span><br /> 
				timestamp = <input type="text" name="timestamp" value="${timestamp}"><span class="notes">*</span><br /> 
				sign = <input type="text" name="sign" value="${sign}"><span class="notes">*</span><br />
			</div>
			<input type="button" class="api_test_btn_q" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
	
	
	
	
	
	
	
	
	
	
	
	
	

	<!-- 返回值等 -->
	<a name="/RespCode"></a>
	<div class="api_block">
		<div class="api_name">
			状态码<span style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<ul id="RespCode">
			<li>N000 调用成功</li>
<li>E000 系统错误</li>
<li>E001 字段不能为空</li>
<li>E002 Result数据为空</li>
<li>E003鉴权失败</li>
		</ul>
	</div>
	<div style="position: relative; z-index: 9999999;">
		<div id="ajaxloading" style="display: none;">正在加载，请稍候 ...</div>
	</div>
</body>
</html>
