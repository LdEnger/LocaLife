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
<title>推荐位管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/localLife/recommend.js?v=2"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="js/laydate/laydate.js"></script>
<style>
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
.required{
	color:red;
}
.pagination-info{
    margin-right:30px;
}
.text1{width:300px; height:20px}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'推荐位管理'" class="regionCenter">
		<div id="award_toolbar">

			<input type="button" id="add_recommend" class="btn btn-success" value="添加">
            <input id="openRole" value="${openRole}" type="hidden"/>
            <input id="proCode" value="${proCode}" type="hidden"/>
            <input id="proName" value="${proName}" type="hidden"/>
            <input id="cityCode" value="${cityCode}" type="hidden"/>
            <input id="cityName" value="${cityName}" type="hidden"/>
            <label>推荐位名称</label>
            <input id="searchName" type="text" />
            <label>地理位置：</label>
            <select id="province" onchange="javascript:initCityList(this.value);" >
            </select>
            <select id="city" >
            </select>
            <input type="button" id="query_activity" class="btn btn-success" value="查询" onclick="javascript:initContentInfo();">
            <br>
            <span class="required">*</span>温馨提示：为了保证功能美观，每个城市的推荐位必须大于3个。为了保证系统稳定，每个城市的推荐位务必小于10个。
		</div>
		<table id="content_table" ></table>

		<div id="recommend_dialog" data-options="closed:true,modal:true,title:'推荐位管理',iconCls:'icon-save'"
			style="padding: 5px; width: 550px; height: 300px;">
			<div id="tabOne"  style="height: 100px;">
				<input id="id" value="" type="hidden" />
                <%--<img src="" id="pictureUrl"  style="display:none"/>--%>
				<table>
					<tr>
						<td><label><span class="required">*</span>推荐位名称：</label></td>
						<td><input type="text" id="name1"  /></td>
					</tr>
                    <tr>
                        <td><label><span class="required">*</span>省份：</label></td>
                        <td>
                            <select id="provinceIdR" onclick="javascript:initCityListRs(this.value);" >
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label><span class="required">*</span>城市：</label></td>
                        <td>
                            <select id="cityId" >
                            </select>
                        </td>
                    </tr>
					<tr>
						<td ><label><span class="required">*</span>图片：</label></td>
						<td><img style="height: 80px; width: 160px" src=""
							id="picUrl" /><input type="button" id="uploadPicUrl"
                                                 value="选择文件" /><td><label>（尺寸：895 X 366  ） 小于4M <br>支持PNG,JPEG<br></label></td></td>

                        <input id="picUrlHidden" value="" type="hidden" />
					</tr>
				</table>
			</div>
		</div>
	</div>
    <!-- 推荐位管理 版本-------start  -->
    <div id="content_detail_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'详细内容'"
         style="padding: 5px; width: 900px; height: 500px;">
        <input id="recommendAddId" type="hidden" />
        <!-- 已添加的内容 -->
        <div id="content_table_toolbar">
            <%--状态：<select id="content_select"><option value="">全部</option><option value="1">有效</option><option value="0">无效</option></select> --%>
            <%--<button id="contentBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"  >查询</button>--%>
            <span class="required">*</span> 温馨提示：一个推荐位只能关联一个内容，多次关联会替换掉当前已关联内容。
        </div>
        <table id="recommend_content_table" style=" width: 850px; height: 200px;"></table>

        <!-- 查询内容 -->
        <div id="search_table_toolbar" style="margin-top: 5px;">
            <%--<span id="_isShow">类型：<select id="search_select"></select></span>--%>
            名称：<input id="search_nameN" />
                <tr>
                    <td><label><span class="required">*</span>省份：</label></td>
                    <td><select id="provinceId"onchange="javascript:initCityId(this.value);"> </select></td>
                </tr>
                <tr>
                    <td><label><span class="required">*</span>城市：</label></td>
                    <td><select id="cityIdN" ></select></td>
                </tr>
                <label>类型：</label>
                <select id="typeIds" style="width: 100px; height:  35px;">
                    <option value="-1">全部</option>
                    <option value="1">应用</option>
                    <option value="3">图片</option>
                    <option value="2">视频</option>
                </select>
            <button id="searchBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" >查询</button>
        </div>
        <div id="showTypeDiv"></div>
        <table id="search_table" style="width: 850px; height:  280px;"></table>
    </div>
    <script>
        $(function(){
            var w = $(window).width();
            var h = $(window).height();

            $("#content_table").height(h);
            $("#content_table").width(w);
        });
    </script>
    <!-- 推荐位管理 版本---截图----end  -->
</body>
</html>