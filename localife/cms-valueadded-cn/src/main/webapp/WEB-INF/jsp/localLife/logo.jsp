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
<title>占位图管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/localLife/logo.js?v=2"></script>
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
	<div data-options="region:'center',title:'占位图管理'" class="regionCenter">
		<div id="award_toolbar">
			<input type="button" id="add_award" class="btn btn-success" value="添加">
            <input id="openRole" value="${openRole}" type="hidden"/>
            <input id="proCode" value="${proCode}" type="hidden"/>
            <input id="proName" value="${proName}" type="hidden"/>
            <input id="cityCode" value="${cityCode}" type="hidden"/>
            <input id="cityName" value="${cityName}" type="hidden"/>
            <label>位置</label>
            <%--<input id="searchName" type="text" />--%>
            <select id="searchName" >
                <option value="1号运营位">1号运营位</option>
            </select>
            <label>地理位置：</label>
            <select id="province" onchange="javascript:initCityList(this.value);" >
            </select>
            <select id="city" >
            </select>
            <input type="button" id="query_activity" class="btn btn-success" value="查询" onclick="javascript:initContentInfo();">
            <br>
            <span class="required">*</span>温馨提示：每个位置的每个城市，只存在一个占位图。
		</div>
		<table id="content_table" ></table>

		<div id="content_dialog" data-options="closed:true,modal:true,title:'占位图管理',iconCls:'icon-save'"
			style="padding: 5px; width: 550px; height: 300px;">
			<div id="tabOne"  style="height: 150px;">
				<input id="id" value="" type="hidden" />
                <%--<img src="" id="pictureUrl"  style="display:none"/>--%>
				<table>
					<tr>
						<td><label><span class="required">*</span>位置：</label></td>
                        <td>
                            <select id="name1">
                                    <option value="1号运营位">1号运营位</option>
                            </select>
                        </td>
					</tr>
                    <tr>
                        <td><label><span class="required">*</span>省份：</label></td>
                        <td><select id="provinceIdLogo"onchange="javascript:initCityId(this.value);"> </select></td>
                    </tr>
                    <tr>
                        <td><label><span class="required">*</span>城市：</label></td>
                        <td>
                            <select id="cityId" >
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td ><label>图片：</label></td>
                        <td><img style="height: 80px; width: 160px" src=""
                                 id="logoUrl" /><input type="button" id="uploadLogoUrl"
                                                      value="选择文件" /><label>（尺寸：370 X 150  ） 小于4M <br>支持PNG,JPEG<br></label></td>
                        <input id="logoUrlHidden" value="" type="hidden" />
                    </tr>
				</table>
			</div>
		</div>
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