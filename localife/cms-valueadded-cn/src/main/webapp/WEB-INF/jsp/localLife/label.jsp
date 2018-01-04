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
<title>标签管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/localLife/label.js?v=2"></script>
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
	<div data-options="region:'center',title:'标签管理'" class="regionCenter">
		<div id="award_toolbar">
			<input type="button" id="add_label" class="btn btn-success" value="添加">
            <input id="openRole" value="${openRole}" type="hidden"/>
            <input id="proCode" value="${proCode}" type="hidden"/>
            <input id="proName" value="${proName}" type="hidden"/>
            <input id="cityCode" value="${cityCode}" type="hidden"/>
            <input id="cityName" value="${cityName}" type="hidden"/>
            <label>标签名称</label>
            <input id="searchName" type="text" />
            <label>地理位置：</label>
            <select id="province" onchange="javascript:initCityList(this.value);" >
            </select>
            <select id="city" >
            </select>
            <input type="button" id="query_activity" class="btn btn-success" value="查询" onclick="javascript:initContentInfo();">
		</div>
		<table id="content_table" ></table>

		<div id="recommend_dialog" data-options="closed:true,modal:true,title:'标签管理',iconCls:'icon-save'"
			style="padding: 5px; width: 500px; height: 250px;">
			<div id="tabOne"  style="height: 100px;">
				<input id="id" value="" type="hidden" />
				<table>
					<tr>
						<td><label><span class="required">*</span>标签名称：</label></td>
						<td><input type="text" id="name1" /></td>
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
				</table>
			</div>
		</div>
	</div>
    <!-- 推荐位管理 版本-------start  -->
    <div id="content_detail_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'详细内容'"
         style="padding: 5px; width: 900px; height: 500px;">
        <input id="labelAddId" type="hidden" />
        <!-- 已添加的内容 -->
        <div id="content_table_toolbar">
            <%--状态：<select id="content_select"><option value="">全部</option><option value="1">有效</option><option value="0">无效</option></select> --%>
            <%--<button id="contentBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"  >查询</button>--%>
            <span class="required">*</span> 温馨提示：一个标签可以关联多个内容。

        </div>
        <table id="label_content_table" style=" width: 850px; height: 240px;"></table>
        <!-- 查询内容 -->
        <div id="search_table_toolbar" style="margin-top: 5px;">
            名称：<input id="search_nameN" />
                <select id="provinceN" onchange="javascript:initCityListN(this.value);" style="width: 150px;">
                </select>
                <select id="cityN" style="width: 150px;">
                </select>
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
        <table id="search_table" style="width: 850px; height:  240px;"></table>
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