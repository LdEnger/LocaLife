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
    <title>鹏云教育VIP</title>
    <link rel="stylesheet" href="css/all.css"/>
    <link rel="stylesheet" href="css/jquery/easyui.css"/>
    <link rel="stylesheet" href="css/jquery/main.css"/>
    <link rel="stylesheet" href="css/detail_new.css"/>
    <script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="js/load_wait.js"></script>
    <script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/py/card_py.js?v=${js_version}"></script>
    <script type="text/javascript" src="js/common/md5.js"></script>
    <script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
    <script type="text/javascript" src="js/load_wait.js"></script>
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
<div data-options="region:'center',title:'鹏云教育VIP'" class="regionCenter">
    <div id="card_py_toolbar">
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
            <input type="button" id="add_card" class="btn btn-success" value="制卡"/>
            <input type="button" id="single_open" class="btn btn-success" value="开通"/>
            <input type="button" id="batch_open" class="btn btn-success" value="批量开通"/>
        </div>
        <div>
            <label>开通方式：</label>
            <select id="card_open">
                <option value="-1">全部</option>
                <option value="1">激活码</option>
                <option value="2">直冲</option>
            </select>
            <label>操作账户：</label> <input id="creator_name" type="text"/>
            <input type="button" id="query_card_py" class="btn btn-success" value="查询">
        </div>
    </div>

    <table id="card_py_table"></table>

    <%--鹏云VIP制卡--%>
    <div id="card_detail_dialog"
         data-options="closed:true,modal:true,title:'批量制卡',iconCls:'icon-save'"
         style="padding: 5px; width: 500px; height: 340px;">
        <table class=tableC3>
            <tr>
                <th>制卡张数：</th>
                <td><input type="text" id="card_num" onkeyup="value=value.replace(/[^\d]/g,'')"  ></td>
            </tr>
            <tr>
                <th>时长：</th>
                <td>
                    <select id="duration" >
                        <option value="1">3个月</option>
                        <option value="2">6个月</option>
                        <option value="3">12个月</option>
                        <option value="4">24个月</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>激活码有效时长(<font color="red">月</font>)：</th>
                <td>
                    <input type="text" id="effective_days_length" onkeyup="value=value.replace(/[^\d]/g,'')" ><br/>
                    <input type="checkbox" id="forever" value="999" onclick="checkAll()"/><span style="font-size: 15px;" >永久有效</span>
                </td>
            </tr>

            <tr id="branch_tr">
                <th>分公司名称：</th>
                <td>
                    <input type="text" id="branch_name" style="width: 300px; border: 0;"
                           value="${currentUser.branchName}" readonly="true">
                </td>
            </tr>

            <tr id="zone_tr">
                <th>分公司：</th>
                <td>
                    <select id="py_add_branch"></select>
                </td>
            </tr>
        </table>
    </div>


    <div id="card_single_open"
         data-options="closed:true,modal:true,title:'开通教育VIP',iconCls:'icon-save'"
         style="padding: 5px; width: 500px; height: 340px;">
        <table class=tableC3>
            <tr>
                <th>mac：</th>
                <td><input type="text" id="open_mac"></td>
            </tr>
            <tr>
                <th>sn：</th>
                <td><input type="text" id="open_sn" ></td>
            </tr>
            <tr>
                <th>时长：</th>
                <td>
                    <select id="open_duration" >
                        <option value="1">3个月</option>
                        <option value="2">6个月</option>
                        <option value="3">12个月</option>
                        <option value="4">24个月</option>
                    </select>
                </td>
            </tr>

            <tr id="open_branch_tr">
                <th>分公司名称：</th>
                <td>
                    <input type="text" id="open_branch_name" style="width: 300px; border: 0;"
                           value="${currentUser.branchName}" readonly="true">
                </td>
            </tr>

            <tr id="open_zone_tr">
                <th>分公司：</th>
                <td>
                    <select id="open_py_add_branch"></select>
                </td>
            </tr>
        </table>
    </div>

    <div id="card_batch_open"
         data-options="closed:true,modal:true,title:'开通教育VIP',iconCls:'icon-save'"
         style="padding: 5px; width: 500px; height: 340px;">
        <table class=tableC3>
            <input type="hidden" id="hiddenCardPyPath" />
            <tr>
                <th>时长：</th>
                <td>
                    <select id="batch_open_duration" >
                        <option value="1">3个月</option>
                        <option value="2">6个月</option>
                        <option value="3">12个月</option>
                        <option value="4">24个月</option>
                    </select>
                </td>
            </tr>

            <tr id="batch_open_branch_tr">
                <th>分公司名称：</th>
                <td>
                    <input type="text" id="batch_open_branch_name" style="width: 300px; border: 0;"
                           value="${currentUser.branchName}" readonly="true">
                </td>
            </tr>

            <tr id="batch_open_zone_tr">
                <th>分公司：</th>
                <td>
                    <select id="batch_open_py_add_branch"></select>
                </td>
            </tr>

            <tr>
                <td>下载Excel模板:</td>
                <td><input type="button" class="btn btn-success" value="下载"
                           onclick="javascript:window.location.href='<%=basePath%>excelDemo/CardPy.xlsx'" />
                </td>
            </tr>
            <tr>
                <td>导入文件:</td>
                <td><span id="import_file_name"></span><input type="button"
                                                            id="import_card_py" class="btn btn-success" value="选择文件"></td>
            </tr>
        </table>
    </div>




</div>
</body>
</html>