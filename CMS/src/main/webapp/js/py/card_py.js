var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function () {
    dialogInfo();
    branchInfo();
    openBranchInfo();
    addCardPy();
    queryPy();
    openSingleCardPy();
    batchOpenBranchInfo();
    batchOpenCardPy();
    initCardPyInfo();
});

function dialogInfo() {
    // 初始化编辑弹窗
    $('#card_detail_dialog').dialog({
        buttons: [{
            text: '确 定',
            handler: function () {
                submit_card_py();
            }
        }, {
            text: '取消',
            handler: function () {
                $('#card_detail_dialog').dialog('close');
            }
        }]
    });

    $('#card_single_open').dialog({
        buttons: [{
            text: '确 定',
            handler: function () {
                open_card_py();
            }
        }, {
            text: '取消',
            handler: function () {
                $('#card_single_open').dialog('close');
            }
        }]
    });

    $('#card_batch_open').dialog({
        buttons: [{
            text: '确 定',
            handler: function () {
                batch_open_card_py();
            }
        }, {
            text: '取消',
            handler: function () {
                $('#card_batch_open').dialog('close');
            }
        }]
    });

}

// 初始化查询卡列表
function initCardPyInfo() {
    parameter = {};
    var roleId = $('#hidRoleId').val();
    if (roleId == 2) {
        var branch_id = $('#hidBranchId').val();
        var branch_name = $('#hidBranchName').val();
        parameter.branch_id = branch_id;
        parameter.branch_name = branch_name;
    }
    var card_open = $('#card_open').val();
    parameter.card_open = card_open;
    var creator_name = $('#creator_name').val();
    if (creator_name != "") {
        parameter.creator_name = creator_name;
    }
    $('#card_py_table').datagrid({
        iconCls: 'icon-save',
        nowrap: true,
        autoRowHeight: false,
        striped: true,
        toolbar: '#card_py_toolbar',
        fit: true,
        fitColumns: true,
        collapsible: true,
        url: 'cardPy/getList.json',
        queryParams: parameter,
        remoteSort: false,
        singleSelect: true,
        idField: 'id',
        columns: [[
            {field: 'id', title: '批次号', width: 15},
            {
                field: 'card_open', title: '开通方式', width: 20,
                formatter: function (value, row, index) {
                    if (1 == value) {
                        return '激活码';
                    } else if (2 == value) {
                        return '直冲';
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
                field: 'create_time', title: '创建时间', width: 40,
                formatter: function (value, row, index) {
                    if(null != value){
                        return value.substring(0,19);
                    }
                }
            },
            {
                field: 'creator_name', title: '创建者', width: 35,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                field: 'effective_days_length', title: '激活码到期时间', width: 40,
                formatter: function (value, row, index) {
                    if (2 == row.card_open) {
                        return '-';
                    } else if ('2099-12-31' == value.substring(0, 10)) {
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
                field: 'card_num', title: '制卡总数', width: 20,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                field: 'success_card', title: '成功数目', width: 20,
                formatter: function (value, row, index) {
                    if (1 == row.card_open) {
                        return '-';
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'error_card', title: '失败数目', width: 20,
                formatter: function (value, row, index) {
                    if (1 == row.card_open) {
                        return '-';
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'export', title: '操作', width: 40,
                formatter: function (value, row, index) {
                    if (2 == row.card_open) {
                        return '-';
                    } else {
                        return '<a href="javascript:export_card(' + row.id + ')">导出</a>';
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

// 查询
function queryPy() {
    $('#query_card_py').click(function () {
        initCardPyInfo();
    });
}

function branchInfo() {
    var roleId = $('#hidRoleId').val();
    var branch_tr = document.getElementById("branch_tr");
    var zone_tr = document.getElementById("zone_tr");
    if (roleId == 2) {
        branch_tr.style.display = '';
        zone_tr.style.display = 'none';
    } else if (roleId == 1) {
        branch_tr.style.display = 'none';
        zone_tr.style.display = '';
        initBranchInf(0);
    }
}

function openBranchInfo() {
    var roleId = $('#hidRoleId').val();
    var open_branch_tr = document.getElementById("open_branch_tr");
    var open_zone_tr = document.getElementById("open_zone_tr");
    if (roleId == 2) {
        open_branch_tr.style.display = '';
        open_zone_tr.style.display = 'none';
    } else if (roleId == 1) {
        open_branch_tr.style.display = 'none';
        open_zone_tr.style.display = '';
        initBranchInf(0);
    }
}

function batchOpenBranchInfo() {
    var roleId = $('#hidRoleId').val();
    var batch_open_branch_tr = document.getElementById("batch_open_branch_tr");
    var batch_open_zone_tr = document.getElementById("batch_open_zone_tr");
    if (roleId == 2) {
        batch_open_branch_tr.style.display = '';
        batch_open_zone_tr.style.display = 'none';
    } else if (roleId == 1) {
        batch_open_branch_tr.style.display = 'none';
        batch_open_zone_tr.style.display = '';
        initBranchInf(0);
    }
}

/**
 * 初始话查询页面的 分公司信息
 */
function initBranchInf(branchId) {
    $.ajax({
        url: "branch/getAllList.json",
        async: false, // 同步
        type: "get",
        dataType: "json",
        success: function (data) {
            $('#py_add_branch').combobox({
                data: data,
                valueField: 'id',
                textField: 'branchName'
            });
            $('#open_py_add_branch').combobox({
                data: data,
                valueField: 'id',
                textField: 'branchName'
            });
            $('#batch_open_py_add_branch').combobox({
                data: data,
                valueField: 'id',
                textField: 'branchName'
            });
        }
    });
}

// 弹出制卡信息窗口
function addCardPy() {
    $('#add_card').click(function () {
        reset();
        $('#card_detail_dialog').dialog('open');
    });
}

// 重置制卡页面
function reset() {
    $("#card_num").val('');
    $("#duration").val('1');
    $("#effective_days_length").val('');
    document.getElementById('effective_days_length').style.display = '';
    document.getElementById('forever').checked = false;
    branchInfo();
}

function submit_card_py() {
    var roleId = $('#hidRoleId').val();
    var branch_id;
    var branch_name;
    if (roleId == 2) {
        branch_id = $('#hidBranchId').val();
        branch_name = $('#hidBranchName').val();
    } else if (roleId == 1) {
        branch_id = $('#py_add_branch').combobox('getValue');
        branch_name = $('#py_add_branch').combobox('getText');
    }
    var effective_days_length;
    var forever = document.getElementById('forever');
    if (forever.checked) {
        effective_days_length = $('#forever').val();
    } else {
        effective_days_length = $('#effective_days_length').val();
    }
    var cardPy = {
        'card_num': $('#card_num').val(),
        'duration': $('#duration').val(),
        'effective_days_length': effective_days_length,
        'branch_id': branch_id,
        'branch_name': branch_name
    };
    if (null == cardPy.card_num || cardPy.card_num > 5000 || 0 == cardPy.card_num) {
        $.messager.alert(titleInfo, "请输入数量，不要大于5000");
        return;
    }
    if (null == cardPy.branch_id || '' == cardPy.branch_id || null == cardPy.branch_name || '' == cardPy.branch_name) {
        $.messager.alert(titleInfo, "请选择分公司");
        return;
    }
    loadDiv('正在制卡，请稍后...');
    $.post("cardPy/add.json", cardPy, function (data) {
        if (data.code >= 1) {
            $('#card_detail_dialog').dialog('close');
            $.messager.show({
                title: titleInfo,
                msg: '添加成功',
                timeout: timeoutValue,
                showType: 'slide'
            });
            $('#card_py_table').datagrid('load', parameter);
            displayLoad();
        } else {
            $.messager.alert(titleInfo, data.msg);
            displayLoad();
        }
    }, "json");
}

function checkAll() {
    var forever = document.getElementById('forever');
    var effective_days_length = document.getElementById('effective_days_length');
    if (forever.checked) {
        effective_days_length.style.display = 'none';
    } else {
        effective_days_length.style.display = '';
    }
}

function export_card(py_id) {
    loadDiv('正在导出，请稍后...');
    $.post("cardPyDetail/exportCardPy.json", {'py_id': py_id}, function (data) {
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

// 打开开通框
function openSingleCardPy() {
    $('#single_open').click(function () {
        open_reset();
        $('#card_single_open').dialog('open');
    });
}

// 重置开通页面
function open_reset() {
    $("#open_mac").val('');
    $("#open_sn").val('');
    $("#open_duration").val('1');
    openBranchInfo();
}

function open_card_py() {
    var roleId = $('#hidRoleId').val();
    var branch_id;
    var branch_name;
    if (roleId == 2) {
        branch_id = $('#hidBranchId').val();
        branch_name = $('#hidBranchName').val();
    } else if (roleId == 1) {
        branch_id = $('#open_py_add_branch').combobox('getValue');
        branch_name = $('#open_py_add_branch').combobox('getText');
    }
    var duration = $('#open_duration').val();
    var mac = $('#open_mac').val();
    var sn = $('#open_sn').val();
    if (mac == null || sn == null || mac == '' || sn == '') {
        $.messager.alert(titleInfo, '没有MAC或SN！');
        return;
    }

    var _params = "mac=" + mac + "&sn=" + sn;
    var user_msg = "&branch_id=" + branch_id + "&branch_name=" + branch_name + "&duration=" + duration;
    _params = _params + user_msg;
    $.messager.confirm('Warning', '您确定要开通吗?', function (r) {
        if (r) {
            loadDiv('正在开通，请稍候...');
            $.ajax({
                url: "cardPy/openCardPy.json",
                async: false, // 同步
                type: "POST",
                dataType: "json",
                data: _params,
                success: function (data) {
                    if (data.code == "000") {
                        displayLoad();
                        $('#card_single_open').dialog('close');
                        $.messager.show({
                            title: titleInfo,
                            msg: '开通成功！',
                            timeout: timeoutValue,
                            showType: 'slide'
                        });
                        $('#card_py_table').datagrid('reload', parameter);
                    } else {
                        displayLoad();
                        $.messager.alert(titleInfo, '开通失败:' + data.desc);
                    }
                }
            });
            displayLoad();
        }
    });
}

function batch_open_reset() {
    $("#batch_open_duration").val('1');
    batchOpenBranchInfo();
    initUploadExcelCardPy();
    $('#hiddenCardPyPath').val('');
    $("#import_file_name").text("");
}

function batchOpenCardPy() {
    $('#batch_open').click(function () {
        batch_open_reset();
        $('#card_batch_open').dialog('open');
    });
}

// 导入excel
function initUploadExcelCardPy() {
    var import_button = $("#import_card_py");
    new AjaxUpload(import_button, {
        action: 'cardPy/uploadExcel.html',
        autoSubmit: true,
        name: 'file',// 文件对象名称（不是文件名）
        data: {},
        onChange: function (file, extension) {
            var d = /\.[^\.]+$/.exec(file); // 文件后缀
            if (d != ".xls" && d != ".xlsx") {
                $.messager.alert(titleInfo, '文件格式错误，请上传.xls或.xlsx格式！');
                return false;
            } else {
                $("#import_file_name").text(file);
            }
        },
        onSubmit: function (file, extension) {// 在提交的时候触发
        },
        onComplete: function (file, response) {
            $('#hiddenCardPyPath').val(response);
            $.messager.show({
                title: titleInfo,
                msg: '文件已选定！',
                timeout: timeoutValue,
                showType: 'slide'
            });
        }
    });
}

function batch_open_card_py() {
    var roleId = $('#hidRoleId').val();
    var branch_id;
    var branch_name;
    if (roleId == 2) {
        branch_id = $('#hidBranchId').val();
        branch_name = $('#hidBranchName').val();
    } else if (roleId == 1) {
        branch_id = $('#batch_open_py_add_branch').combobox('getValue');
        branch_name = $('#batch_open_py_add_branch').combobox('getText');
    }
    var duration = $('#batch_open_duration').val();

    var tmpData = {
        "branch_id": branch_id,
        "branch_name": branch_name,
        "duration": duration,
        "excelPath": $('#hiddenCardPyPath').val()
    }
    var fileName = $("#import_file_name").text();
    if (fileName == "" || fileName == null) {
        $.messager.alert(titleInfo, '请先选择文件！');
        return;
    }
    loadDiv('正在开通，请稍候...');
    $.ajax({
        url: "cardPy/batchOpenCardPy.json",
        async: true, // 同步
        type: "POST",
        dataType: "json",
        data: tmpData,
        success: function (data) {
            if (1 == data.code) {
                displayLoad();
                $('#card_batch_open').dialog('close');
                $.messager.show({
                    title: titleInfo,
                    msg: '开通成功！',
                    timeout: timeoutValue,
                    showType: 'slide'
                });
                $('#card_py_table').datagrid('reload', parameter);
            } else {
                displayLoad();
                $.messager.alert(titleInfo, '开通失败！');
            }
        },
        complete: function () {
            displayLoad();
        }
    });
}