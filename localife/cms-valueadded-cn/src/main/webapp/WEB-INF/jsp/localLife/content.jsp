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
<title>内容管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/localLife/content.js?v=2"></script>
<script type="text/javascript" src="js/localLife/contentVersion.js?v=2"></script>
<script type="text/javascript" src="js/localLife/contentVersionScreenshot.js?v=2"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="js/laydate/laydate.js"></script>
<style>
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
.pagination-info{
    margin-right:30px;
}
.required{
	color:red;
}
.text1{width:300px; height:20px}
    #tabOne label{
        white-space: nowrap;
    }
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'内容管理'" class="regionCenter">
		<div id="award_toolbar">
            <input id="userId" value="${userId}" type="hidden" />
            <input id="openRole" value="${openRole}" type="hidden"/>
            <input id="proCode" value="${proCode}" type="hidden"/>
            <input id="proName" value="${proName}" type="hidden"/>
            <input id="cityCode" value="${cityCode}" type="hidden"/>
            <input id="cityName" value="${cityName}" type="hidden"/>
			<input type="button" id="add_award" class="btn btn-success" value="添加">
            <label>内容名称</label>
            <input id="searchName" type="text" />
            <label>地理位置：</label>
            <select id="province"onchange="javascript:initCityList(this.value);">
            </select>
            <select id="city" >
            </select>
            <label>类型：</label>
            <select id="typeIds" >
                <option value="">全部</option>
                <option value="1">应用</option>
                <option value="3">图片</option>
                <option value="2">视频</option>
            </select>
            <input type="button" id="query_activity" class="btn btn-success" value="查询" >
		</div>
		<table id="content_table" ></table>

		<div id="content_dialog" data-options="closed:true,modal:true,title:'内容管理',iconCls:'icon-save'"
			style="padding: 5px; width: 550px; height: 500px;"  >
			<div id="tabOne" style="height: 620px;">
				<input id="id" value="" type="hidden" />
                <%--<img src="" id="pictureUrl"  style="display:none"/>--%>
				<table>
					<tr>
						<td><label><span class="required">*</span>内容名称：</label></td>
						<td><input type="text" id="name1" /></td>
					</tr>
                    <tr>
                        <td><label><span class="required">*</span>省份：</label></td>
                      <td><select id="provinceId"onchange="javascript:initCityId(this.value);"> </select></td>
                    </tr>
                    <tr>
                        <td><label><span class="required">*</span>城市：</label></td>
                        <td><select id="cityId" ></select></td>
                    </tr>
                    <tr>
                        <td><label><span class="required">*</span>应用类型：</label></td>
                        <td>
                            <label><input type="radio"  value="1" name="typeId" onclick="showPayInfo(1)" checked="true"/>应用</label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio"  value="2" name="typeId" onclick="showPayInfo(2)"/>视频</label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio"  value="3" name="typeId" onclick="showPayInfo(3)"/>图片</label>
                        </td>
                    </tr>
                    <tr class="showPlayerType1" style="display: none;">
                        <td><label><span class="required">*</span>开发商：</label></td>
                        <td><input type="text" id="developerName" class="text1" /></td>
                    </tr>
                    <tr class="showPlayerType1" style="display: none;">
                        <td><label><span class="required">*</span>标识(apk包名)：</label></td>
                        <td><input type="text" id="mark" class="text1" /></td>
                    </tr>
                    <tr class="showPlayerType1" style="display: none;">
                        <td><label><span class="required">*</span>应用类别：</label></td>
                        <td><input type="text" id="category" class="text1" /></td>
                    </tr>
                    <tr class="showPlayerType1" style="display: none;">
                        <td ><label><span class="required">*</span>内容提要：</label></td>
                        <td ><textarea id="describe1" rows="3" cols="75"></textarea></td>
                    </tr>
                    <tr class="showPlayerType2" style="display: none;">
                        <td ><label><span class="required">*</span>视频介绍：</label></td>
                        <td ><textarea id="describe2" rows="3" cols="75"></textarea></td>
                    </tr>
                    <tr class="showPlayerType1" style="display: none;">
                        <td ><label><span class="required">*</span>应用icon：</label></td>
                        <td>
                            <img style="height: 80px; width: 160px" src="" id="picUrl1" />
                            <input type="button" id="uploadPicUrl1" value="选择文件" />
                            <label>（尺寸：280 X 160  ） 小于4M <br>支持PNG,JPEG<br></label>
                            <input id="picUrlHidden1" value="" type="hidden" />
                        </td>
                    </tr>
                    <tr class="showPlayerType2" style="display: none;">
                        <td ><label><span class="required">*</span>视频icon：</label></td>
                        <td>
                            <img style="height: 80px; width: 160px" src="" id="picUrl2" />
                            <input type="button" id="uploadPicUrl2" value="选择文件" />
                            <label>（尺寸：280 X 160  ） 小于4M <br>支持PNG,JPEG<br></label>
                            <input id="picUrlHidden2" value="" type="hidden" />
                        </td>
                    </tr>
                    <tr class="showPlayerType3" style="display: none;">
                        <td ><label><span class="required">*</span>图片icon：</label></td>
                        <td>
                            <img style="height: 80px; width: 160px" src="" id="picUrl3" />
                            <input type="button" id="uploadPicUrl3" value="选择文件" />


                            <label>（尺寸：280 X 160  ） 小于4M <br>支持PNG,JPEG<br></label>
                            <input id="picUrlHidden3" value="" type="hidden" />
                        </td>
                    </tr>
                    <tr class="showPlayerType2" style="display: none;">
                        <td >
                            <label><span class="required">*</span>视频URL：</label></td>
                        <td><input type="text" id="videoUrl" /></td>
                    </tr>
                    <tr class="showPlayerType3" style="display: none;">
                        <td><label><span class="required">*</span>上传图片：</label></td>
                        <td><img style="height: 80px; width: 160px" src=""
                                 id="panoramaUrl" /><input type="button" id="uploadPanoramaUrl"
                                                       value="选择文件" /><label>（尺寸：1920 X 1080  ）小于4M <br>支持PNG,JPEG<br></label></td>
                        <input id="panoramaUrlHidden" value="" type="hidden" />
                    </tr>
				</table>
			</div>
		</div>
	</div>
    <!-- 内容管理 版本-------start  -->
    <div id="content_version_list">
        <!-- 工具条 -->
        <div id="contentVersionToolbar">
            <input type="hidden" id="versionContentId" >
            <input type="button" id="QContent_Version_Add" class="btn btn-success" value="添加">
            <%--<input type="button" id="search_QContetn_Version" class="btn btn-success" value="查询" style="float:right;">--%>
            <%--<a id="addContentVersion" class="easyui-linkbutton" href="javascript:QContentVersionAdd();" data-options="iconCls:'icon-add',plain:true">添加</a>&nbsp;--%>
            <%--<a id="searchContentVersion" class="easyui-linkbutton" href="javascript:searchQContetnVersion();" data-options="iconCls:'icon-search',plain:true" style="float:right;">查询</a>--%>
        </div>
        <table id="QContent_version_table" style="height: 400px;"></table>
    </div>
    <div id="Q_content_version_dialog">
        <input type="hidden" id="versionId" />
        <table id="tableCr">
            <tr>
                <td><label><span class="required">*</span>版本号：</label></td>
                <td><input type="text" id="versionNumber" class="text1" /></td>
            </tr>
            <tr>
                <td><label><span class="required">*</span>上传apk：</label></td>
                <td><input type="button" id="uploadApkUrl" value="选择apk" /><label></label></td>
            </tr>
            <tr>
                <td><label><span class="required"></span>下载地址：</label></td>
                <td><input type="text" id="downloadUrl" class="text1" disabled="true" style="background:#CCCCCC" /></td>
                <input id="versionSize" value="" type="hidden" />
            </tr>
            <tr>
                <td ><label><span class="required">*</span>版本简介：</label></td>
                <td  ><textarea id="versionDescribe" rows="3" cols="75"></textarea></td>
            </tr>
        </table>
    </div>
    <!-- 内容管理 版本-------end  -->
    <!-- 内容管理 版本---截图----start  -->
    <div id="content_version_screenshot_list">
        <!-- 工具条 -->
        <div id="contentVersionScreenshotToolbar">
            <input type="hidden" id="contentVerNumber" >
            <input type="hidden" id="contentVersionId" >
            <input type="hidden" id="contentCountByVersionId" >
            <input type="button" id="QContent_Version_ScreenshotAdd" class="btn btn-success" value="添加">
            <%--<a id="addContentVersionScreenshot" class="btn-success" href="javascript:QContentVersionScreenshotAdd();" data-options="iconCls:'icon-add',plain:true">添加</a>&nbsp;--%>
            <%--<a id="searchContentVersionScreenshot" class="easyui-linkbutton" href="javascript:searchQContetnScreenshotVersion();" data-options="iconCls:'icon-search',plain:true" style="float:right;">查询</a>--%>
        </div>
        <table id="QContent_version_screenshot_table" style="height: 300px;"></table>
    </div>
    <div id="Q_content_version_screenshot_dialog">
        <input type="hidden" id="screenshotId" />
        <table id="tableCrq">
            <tr>
                <td><label><span class="required">*</span>上传截图：</label></td>
                <td><img style="height: 80px; width: 160px" src="" id="screenshotUrl" />
                    <input type="button" id="uploadScreenshotUrl"  value="选择截图" />
                    <br>
                    <label>（尺寸：1920 X 1080  ） 小于4M <br>支持PNG,JPEG<br></label></td>
                <input id="screenshotUrlHidden" value="" type="hidden" />
            </tr>

        </table>
    </div>
    <script>
        $(function(){
            var w = $(window).width();
            var h = $(window).height();

            $("#content_table").height(h);
            $("#content_table").width(w);
        });
    </script>

</body>
</html>