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
        $('.api_test_btn_').click(function() {
            var div_block = $(this).parent().parent();
            var return_type = div_block.find('#return_type').html();
            var api_full_name = div_block.find('#api_name').html();
            var api_method = div_block.find('#api_method').html();
            var api_validate = div_block.find('#api_validate').html();
            var params = '';
            var MyCookie = document.cookie;
            div_block.find('#params_list').find('input').each(function() {
                //params += $(this).attr('name') + '=' + $(this).val() + '&';
                api_full_name += "-" + $(this).val();
            })

            var host = window.location.host;

            if (host.indexOf('localhost') == 0) {
                host += "/";
            }

            var pathname = window.location.pathname;

            if (pathname == "/dev/api/doc/index_cloud.html") {
                host += "/dev";
            }

            if (pathname == "/pro/api/doc/index_cloud.html") {
                host += "/pro";
            }
            //host="api.newapi.pthv.gitv.tv";
            api_full_name = "http://" + host + api_full_name + ".json";

            //为了getTvList 中增加额外的？参数设计的
            if(api_full_name.indexOf("/api/open/vip/live/getTvList")>0){
                debugger;
                var s_ = api_full_name.lastIndexOf('-');
                var temp = api_full_name.substring(0,s_);
                temp = temp.substring(0, temp.lastIndexOf('-'))+".json";
                temp += "?random="+$("#liveTvRandom").val()+"&sign="+$("#liveTvSign").val();
                api_full_name = temp;
                console.log(api_full_name)
            }
            //params = params.substr(0, params.length - 1);
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
            <li><a href="#/api/localLife/appRecommendList" title="获取本地生活推荐位列表">/api/localLife/appRecommendList</a>
                (获取本地生活推荐位列表)</li>
            <li><a href="#/api/localLife/appLableContentList" title="获取本地生活标签列表接口">/api/localLife/appLableContentList</a>
                (获取本地生活标签列表接口)</li>
            <li><a href="#/api/localLife/getAppContent" title="获取本地生活内容列表接口">/api/localLife/getAppContent</a>
                (获取本地生活内容列表接口)</li>
            <li><a href="#/api/localLife/getAppLogoList" title="获取本地生活Logo列表接口">/api/localLife/getAppLogoList</a>
                (获取本地生活Logo列表接口)</li>
		</ol>
	</div>
    <!-- 开始 -->
    <a name="/api/localLife/appRecommendList"></a>
    <div class="api_block">
        <div class="api_name">
            接口名称： <span id="api_name">/api/localLife/appRecommendList</span><span
                style="float: right; font-size: 12px;">[<a href="#"
                                                           title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
        </div>
        <div class="api_method">
            请求方式： <span id="api_method">GET</span>
        </div>
        <div class="api_url">接口地址：http://{Server}/api/localLife/appRecommendList-{sn}-{mac}-{countryId}-{provinceId}-{cityId}-{version}.json</div>
        <div class="api_desc">接口说明：获取本地生活推荐位列表接口</div>
        <div class="api_result">
            返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
            <ul>
                <li>contentId：推荐位内容ID</li>
                <li>typeId：内容类型ID 1:应用  2:视频广告  3:图片广告</li>
                <li>name：推荐位名称</li>
                <li>picUrl：推荐位图片</li>
                <li>seq：推荐位排序ID</li>
                <li>contentIconUrl：内容iconUrl</li>
                <li>videoUrl：视频播放URL</li>
                <li>panoramaUrl：图片全景图URL</li>
            </ul>
        </div>
        <div class="api_params">
            参数列表： <br /> <br />
            <div id="params_list">
                sn = <input type="text" name="sn" value="DUA31105150800700"><span class="notes">*</span><br />
                mac = <input type="text" name="mac" value="143DF290393A"><span class="notes">*</span><br />
                    countryId = <input type="text" name="countryId" value="0"><span class="notes">*中国：1,美国：2,韩国：3</span><br />
                    provinceId = <input type="text" name="provinceId" value="0"><span class="notes">*加州：8,埃塞俄比亚：10</span><br />
                    cityId = <input type="text" name="cityId" value="0"><span class="notes">*洛杉矶：9</span><br />
                version = <input type="text"  name="version" value="1.0"><span class="notes">*</span><br />
            </div>
            <input type="button" class="api_test_btn_" value="测试接口" /> <input
                type="button" class="api_reset_btn" value="重置结果" />
        </div>
        <div class="return_area"></div>
    </div>
    <!-- 结束 -->

    <a name="/api/localLife/appLableContentList"></a>
    <div class="api_block">
        <div class="api_name">
            接口名称： <span id="api_name">/api/localLife/appLableContentList</span><span
                style="float: right; font-size: 12px;">[<a href="#"
                                                           title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
        </div>
        <div class="api_method">
            请求方式： <span id="api_method">GET</span>
        </div>
        <div class="api_url">接口地址：http://{Server}/api/localLife/appLableContentList-{sn}-{mac}-{countryId}-{provinceId}-{cityId}-{version}.json</div>
        <div class="api_desc">接口说明：获取本地生活标签列表接口</div>
        <div class="api_result">
            返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
            <ul>
                <li>labelname：标签名称</li>
                <li>contentId：内容ID </li>
                <li>contentName：内容名称</li>
                <li>typeId：内容类型ID 1:应用  2:视频广告  3:图片广告</li>
                <li>developerName：开发商</li>
                <li>category：应用类别</li>
                <li>describe：内容提要/视频介绍</li>
                <li>picUrl：应用图标/视频图标</li>
                <li>panoramaUrl：全景图片</li>
                <li>videoUrl：视频URL</li>
                <li>mark：标记/包名</li>
            </ul>
        </div>
        <div class="api_params">
            参数列表： <br /> <br />
            <div id="params_list">
                sn = <input type="text" name="sn" value="DUA31105150800700"><span class="notes">*</span><br />
                mac = <input type="text" name="mac" value="143DF290393A"><span class="notes">*</span><br />
                countryId = <input type="text" name="countryId" value="0"><span class="notes">中国：1,美国：2,韩国：3</span><br />
                provinceId = <input type="text" name="provinceId" value="0"><span class="notes">加州：8,埃塞俄比亚：10</span><br />
                cityId = <input type="text" name="cityId" value="0"><span class="notes">洛杉矶：9</span><br />
                version = <input type="text"  name="version" value="1.0"><span class="notes">*</span><br />
            </div>
            <input type="button" class="api_test_btn_" value="测试接口" /> <input
                type="button" class="api_reset_btn" value="重置结果" />
        </div>
        <div class="return_area"></div>
    </div>
	<!-- 开始 -->
	<a name="/api/localLife/getAppContent"></a>
	<div class="api_block">
		<div class="api_name">
			接口名称： <span id="api_name">/api/localLife/getAppContent</span><span
				style="float: right; font-size: 12px;">[<a href="#"
				title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
		</div>
		<div class="api_method">
			请求方式： <span id="api_method">GET</span>
		</div>
		<div class="api_url">接口地址：http://{Server}/api/localLife/getAppContent-{contentId}.json</div>
		<div class="api_desc">接口说明：获取本地生活内容列表接口</div>
		<div class="api_result">
			返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
			<ul>
                <li>contentId：内容ID </li>
                <li>contentName：内容名称</li>
                <li>typeId：内容类型ID 1:应用  2:视频广告  3:图片广告</li>
                <li>developerName：开发商</li>
                <li>category：应用类别</li>
                <li>mark：标记/包名</li>
                <li>describe：内容提要/视频介绍</li>
                <li>picUrl：应用图标/视频图标</li>
                <li>panoramaUrl：全景图片</li>
                <li>videoUrl：视频URL</li>
                <li>versionNumber：版本号</li>
                <li>versionDescribe：版本介绍</li>
                <li>versionSize：版本大小</li>
                <li>downloadUrl：视频下载URL</li>
                <li>updatedTime：更新日期</li>
			</ul>
		</div>
		<div class="api_params">
			参数列表：<br />
            <div id="params_list">
                contentId = <input type="text" name="contentId" value="1"><span class="notes">*</span><br />
            </div>
			<input type="button" class="api_test_btn_" value="测试接口" /> <input
				type="button" class="api_reset_btn" value="重置结果" />
		</div>
		<div class="return_area"></div>
	</div>
	<!-- 结束 -->
    <!-- 开始 -->
    <a name="/api/localLife/getAppLogoList"></a>
    <div class="api_block">
        <div class="api_name">
            接口名称： <span id="api_name">/api/localLife/getAppLogoList</span><span
                style="float: right; font-size: 12px;">[<a href="#"
                                                           title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
			</span>
        </div>
        <div class="api_method">
            请求方式： <span id="api_method">GET</span>
        </div>
        <div class="api_url">接口地址：http://{Server}/api/localLife/getAppLogoList-{sn}-{mac}-{countryId}-{provinceId}-{cityId}-{version}.json</div>
        <div class="api_desc">接口说明：获取本地生活Logo列表接口</div>
        <div class="api_result">
            返回值说明：<a href="#/RespCode" title="状态码">响应状态码</a>
            <ul>
                <li>id：LogoID </li>
                <li>name：Logo名称</li>
                <li>logoUrl：Logo图片</li>
            </ul>
        </div>
        <div class="api_params">
            参数列表：<br />
            <div id="params_list">
                sn = <input type="text" name="sn" value="DUA31105150800700"><span class="notes">*</span><br />
                mac = <input type="text" name="mac" value="143DF290393A"><span class="notes">*</span><br />
                countryId = <input type="text" name="countryId" value="0"><span class="notes">中国：1,美国：2,韩国：3</span><br />
                provinceId = <input type="text" name="provinceId" value="0"><span class="notes">加州：8,埃塞俄比亚：10</span><br />
                cityId = <input type="text" name="cityId" value="0"><span class="notes">洛杉矶：9</span><br />
                version = <input type="text"  name="version" value="1.0"><span class="notes">*</span><br />
            </div>
            <input type="button" class="api_test_btn_" value="测试接口" /> <input
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
