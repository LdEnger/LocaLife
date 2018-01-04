<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>鹏云教育VIP详单</title>
    <link rel="stylesheet" href="css/all.css"/>
    <link rel="stylesheet" href="css/jquery/easyui.css"/>
    <link rel="stylesheet" href="css/jquery/main.css"/>
    <link rel="stylesheet" href="css/detail_new.css"/>
    <script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="js/load_wait.js"></script>
    <script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/py/card_py_detail.js?v=${js_version}"></script>
    <script type="text/javascript" src="js/common/md5.js"></script>
    <style>
        .tableC3 th {
            font-weight: normal;
            text-align: right;
            line-height: 35px;
            margin-bottom: 2px;
        }
    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'鹏云教育VIP详单'" class="regionCenter">
    <div id="card_py_detail_toolbar">
        <input type="hidden" id="hidBranchId" value="${currentUser.branchId}"/>
        <input type="hidden" id="hidBranchName" value="${currentUser.branchName}"/>
        <input type="hidden" id="hidHallId" value="${currentUser.hallId}"/>
        <input type="hidden" id="hidHallName" value="${currentUser.hallName}"/>
        <input type="hidden" id="hidUserName" value="${currentUser.userName}"/>
        <input type="hidden" id="hidRoleId" value="${currentUser.roleId}"/>
        <input type="hidden" id="hidPartnerKey" value="${partnerKey}"/>
        <input type="hidden" id="hidZoneId" value="${currentUser.zoneId}"/>
        <input type="hidden" id="hidZoneName" value="${currentUser.zoneName}"/>
        <input type="hidden" id="hidCityId" value="${currentUser.cityCode}"/>

        <div>
            <label>开通方式：</label>
            <select id="card_open">
                <option value="-1">全部</option>
                <option value="1">激活码</option>
                <option value="2">直冲</option>
            </select>
            <label>使用状态：</label>
            <select id="use_status">
                <option value="-1">全部</option>
                <option value="1">未使用</option>
                <option value="2">已使用</option>
            </select>
            <label>mac：</label> <input id="mac" type="text"/>
            <label>sn：</label> <input id="sn" type="text"/>
        </div>
        <div>
            <label>制卡时间：</label> <input id="create_startTime"/> <label>--</label> <input id="create_endTime"/>
            <label>激活码：</label> <input id="card_order_py" type="text"/>
            <label>批次号：</label> <input id="py_id" type="text"/>
            <div <c:if test="${currentUser.roleId == 2}"> style="display:none" </c:if> >
                <label>分公司：</label><select id="py_detail_branch"></select>
            </div>
            <div>
                <input type="button" id="query_card_py_detail" class="btn btn-success" value="查询">
                <input type="button" id="export_card_py_detail" class="btn btn-success" value="导出"/>

            </div>
        </div>

    </div>

    <table id="card_py_detail_table"></table>

</div>
</body>
</html>