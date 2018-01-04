var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function () {
    queryPyDetail();
    initDateBox();
    initBranchInfo(0);
    exportCardPyDetail();
    initCardPyDetailInfo();
});

function initDateBox() {
    $.fn.datebox.defaults.formatter = function (date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        if (d < 10) {
            d = "0" + d;
        }
        if (m < 10) {
            m = "0" + m;
        }
        return y + '-' + m + '-' + d;
    };
    $("#create_startTime").datebox();
    $("#create_endTime").datebox();
}

// 查询
function queryPyDetail() {
    $('#query_card_py_detail').click(function () {
        initCardPyDetailInfo();
    });
}

// 初始化查询卡列表
function initCardPyDetailInfo() {
    parameter = {};
    var card_open = $('#card_open').val();
    parameter.card_open = card_open;
    var use_status = $('#use_status').val();
    parameter.use_status = use_status;
    var mac = $('#mac').val();
    parameter.mac = mac;
    var sn = $('#sn').val();
    parameter.sn = sn;
    var create_startTime = $('#create_startTime').datebox("getValue");
    parameter.create_startTime = create_startTime;
    var create_endTime = $('#create_endTime').datebox("getValue");
    parameter.create_endTime = create_endTime;
    var card_order_py = $('#card_order_py').val();
    parameter.create_status = card_order_py;
    var py_id = $('#py_id').val();
    parameter.py_id = py_id;

    var roleId = $('#hidRoleId').val();
    var branch_id;
    var branch_name;
    if (roleId == 2) {
        branch_id = $('#hidBranchId').val();
        branch_name = $('#hidBranchName').val();
    } else if (roleId == 1) {
        branch_id = $('#py_detail_branch').combobox('getValue');
        branch_name = $('#py_detail_branch').combobox('getText');
    }
    parameter.branch_id = branch_id;
    parameter.branch_name = branch_name;

    $('#card_py_detail_table').datagrid({
        iconCls: 'icon-save',
        nowrap: true,
        autoRowHeight: false,
        striped: true,
        toolbar: '#card_py_detail_toolbar',
        fit: true,
        fitColumns: true,
        collapsible: true,
        url: 'cardPyDetail/getList.json',
        queryParams: parameter,
        remoteSort: false,
        singleSelect: true,
        idField: 'id',
        columns: [[
            {field: 'id', title: 'id', width: 20},
            {
                field: 'mac', title: 'mac', width: 30,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                field: 'sn', title: 'sn', width: 35,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                field: 'card_order_py', title: '激活码', width: 30,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                field: 'card_open', title: '开通方式', width: 25,
                formatter: function (value, row, index) {
                    if (1 == value) {
                        return '激活码';
                    } else if (2 == value) {
                        return '直冲';
                    }
                }
            },
            {
                field: 'use_status', title: '使用状态', width: 25,
                formatter: function (value, row, index) {
                    if (1 == value) {
                        return '未使用';
                    } else if (2 == value) {
                        return '已使用';
                    }
                }
            },
            {
                field: 'card_service', title: '服务', width: 20,
                formatter: function (value, row, index) {
                    if (1 == value) {
                        return '教育VIP';
                    }
                }
            },
            {
                field: 'duration', title: '时长', width: 20,
                formatter: function (value, row, index) {
                    if (1 == value) {
                        return '3个月';
                    } else if (2 == value) {
                        return '6个月';
                    } else if (3 == value) {
                        return '12个月';
                    } else if (4 == value) {
                        return '24个月';
                    }
                }
            },
            {
                field: 'create_time', title: '制卡时间', width: 40,
                formatter: function (value, row, index) {
                    if(null != value){
                        return value.substring(0,19);
                    }
                }
            },
            {
                field: 'open_time', title: '开通时间', width: 40,
                formatter: function (value, row, index) {
                    if(null != value){
                        return value.substring(0,19);
                    }
                }
            },
            {
                field: 'end_time', title: '结束时间', width: 40,
                formatter: function (value, row, index) {
                    if(null != value){
                        return value.substring(0,19);
                    }
                }
            },
            {
                field: 'effective_days_length', title: '激活码到期时间', width: 40,
                formatter: function (value, row, index) {
                    if (2 == row.card_open) {
                        return '-';
                    }else if ('2099-12-31' == value.substring(0, 10)) {
                        return '永久有效';
                    } else {
                        if(null != value){
                            return value.substring(0,19);
                        }
                    }
                }
            },
            {
                field: 'branch_name', title: '分公司名称', width: 30,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                field: 'card_cancel', title: '操作', width: 20,
                formatter: function (value, row, index) {
                    if (1 == value) {
                        return '未激活';
                    } else if (2 == value) {
                        return '<a href="javascript:cancelPyCard(' + row.id + ')">注销</a>';
                    } else if (3 == value) {
                        return '已注销';
                    } else if (4 == value) {
                        return '已过期';
                    } else {
                        return '-';
                    }
                }
            }
        ]],
        pagination: true,
        rownumbers: true,
        onClickRow: function (rowIndex) {
        }
    });
}

/**
 * 初始话查询页面的 分公司信息
 */
function initBranchInfo(branchId) {
    $.ajax({
        url: "branch/getAllList.json",
        async: false, // 同步
        type: "get",
        dataType: "json",
        success: function (data) {
            $('#py_detail_branch').combobox({
                data: data,
                valueField: 'id',
                textField: 'branchName'
            });
            $('#export_branch').combobox({
                data: data,
                valueField: 'id',
                textField: 'branchName'
            });
        }
    });
}

function exportCardPyDetail() {
    $('#export_card_py_detail').click(function () {
        submit_export();
    });
}

function submit_export() {
    var card_open = $('#card_open').val();
    var use_status = $('#use_status').val();
    var mac = $('#mac').val();
    var sn = $('#sn').val();
    var create_startTime = $('#create_startTime').datebox("getValue");
    var create_endTime = $('#create_endTime').datebox("getValue");
    var card_order_py = $('#card_order_py').val();
    var py_id = $('#py_id').val();

    var roleId = $('#hidRoleId').val();
    var branch_id;
    var branch_name;
    if (roleId == 2) {
        branch_id = $('#hidBranchId').val();
        branch_name = $('#hidBranchName').val();
    } else if (roleId == 1) {
        branch_id = $('#py_detail_branch').combobox('getValue');
        branch_name = $('#py_detail_branch').combobox('getText');
    }
    if (null == create_startTime || '' == create_startTime) {
        $.messager.alert(titleInfo, "制卡时间开始时间不能为空");
        return;
    }
    if (null == create_endTime || '' == create_endTime) {
        $.messager.alert(titleInfo, "制卡结束开始时间不能为空");
        return;
    }
    if (null == branch_id || '' == branch_id || null == branch_name || '' == branch_name) {
        $.messager.alert(titleInfo, "请选择分公司");
        return;
    }

    var cardPyDetail = {
        'card_open': card_open,
        'use_status': use_status,
        'mac': mac,
        'sn': sn,
        'create_startTime': create_startTime,
        'create_endTime': create_endTime,
        'card_order_py': card_order_py,
        'py_id': py_id,
        'branch_id': branch_id,
        'branch_name': branch_name
    };

    loadDiv('正在导出，请稍后...');
    $.post("cardPyDetail/exportCardPyDetail.json", cardPyDetail, function (data) {
        if (data.code >= 1) {
            $.messager.show({
                title: titleInfo,
                msg: '导出成功',
                timeout: timeoutValue,
                showType: 'slide'
            });
            displayLoad();
            window.open(data.download_url);
        } else {
            $.messager.alert(titleInfo, data.msg);
            displayLoad();
        }
    }, "json");
}


function cancelPyCard(id) {
    $.ajax({
        url : "cardPyDetail/getCardPyById.json",
        async : false, // 同步
        type : "POST",
        dataType : "json",
        data : {"id" : id},
        success : function(data) {
            debugger;
            var userId = data.cardPyDetail.user_id;
            var orderId = data.cardPyDetail.card_order_py;
            if (userId == null || orderId == null) {
                $.messager.alert(titleInfo, '没有用户ID或订单号！');
                return;
            }
            // 生成签名
            var partnerKey = $('#hidPartnerKey').val();
            var _params = "orderId=" + orderId + "&userId=" + userId;
            var tmp = _params + "&partnerKey=" + partnerKey;
            var sign = md5(tmp);
            _params = _params + "&sign=" + sign;
            $.messager.confirm('Warning', '您确定要注销?', function(r) {
                if (r) {
                    $.ajax({
                        url : "cardPyDetail/cardPyDetailCancel.json",
                        async : false, // 同步
                        type : "POST",
                        dataType : "json",
                        data : _params,
                        success : function(data) {
                            if (data.code == "000") {
                                $.messager.show({
                                    title : titleInfo,
                                    msg : '已注销！',
                                    timeout : timeoutValue,
                                    showType : 'slide'
                                });
                                $('#card_py_detail_table').datagrid('reload', parameter);
                            } else {
                                $.messager.alert(titleInfo, '注销失败:' + data.desc);
                            }
                        }
                    });
                }
            });
        },
    });
}

