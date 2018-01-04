var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
    $('#QContent_Version_ScreenshotAdd').click(function() {
        resetVersionScreenshot();
        uploadScreenshotfile();
        getCountByVersionId($("#contentVersionId").val());
        if($("#contentCountByVersionId").val()<4){
            $("#Q_content_version_screenshot_dialog").dialog('open');
        }else{
            alert("版本截图只能有四个！");
        }
    });
})



// 跨域JS调用-版本截图数据
function initContentVersionScreenshotGrid(contentVersionId){
    parameter = {
        versionId:contentVersionId
    };
    $("#QContent_version_screenshot_table").datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        toolbar:"#contentVersionScreenshotToolbar",
        fitColumns:true,
        collapsible:true,
        singleSelect:true,
        url:'localLifeContentVersion/getVersionScreenshotList.json',
        queryParams:parameter,
        remoteSort: false,
        border:false,
        idField:'id',
        columns:[[
            { field : 'screenshotUrl',title : '截图',width : 60,
                formatter : function(value, row, index) {
                    return "<img style='height: 80px; width: 160px' src='"+value+"' />";
                }
            },
            { field : 'seq', title : '排序操作', width : 40, hidden : false,
                formatter : function(value, row, index) {
                    var seq = row.seq;
                    return '<a href="javascript:moveScreenshot('
                        + seq + ',' + index
                        + ',1)">上移</a>|'
                        + '<a href="javascript:moveScreenshot('
                        + seq + ',' + index
                        + ',2)">下移</a>';
                }
            },
            { field : 'id', title : '操作', width : 50,
                formatter : function(value, row, index) {
                    var ret =  "";
                    ret += '<a href="javascript:editContentVersionScreenshot(' + value + ')">替换图片</a>&nbsp;&nbsp;';
                    ret += '|&nbsp;&nbsp;<a href="javascript:delContentScreenshotVersion(' + value +','+ contentVersionId + ')">删除</a>';
                    return  ret;
                }
            }
        ]],
        pagination:true,
        pageSize:10,
        rownumbers:true,
        onClickRow:function(rowIndex){

        }
    });
}
//编辑
function editContentVersionScreenshot(id) {
    uploadScreenshotfile();
    var rowInfo = $('#QContent_version_screenshot_table').datagrid('getSelected');
    if (rowInfo) {
        $('#screenshotId').val(rowInfo.id);
        $('#screenshotUrl').attr("src", rowInfo.screenshotUrl);
        $('#screenshotUrlHidden').val(rowInfo.screenshotUrl);
        $('#Q_content_version_screenshot_dialog').dialog('open');
    }
}
//删除活动信息
function delContentScreenshotVersion(id,contentVersionId) {
    $.messager.confirm('Warning', 'Warning：该内容有可能已经绑定，删除可能引发一些问题！确定要删除?',
        function(r) {
            if (r) {
                var activity = {
                    'id' : id,
                    'versionId':contentVersionId
                };
                $.post('localLifeContentVersion/delScreenshot.json', activity, function(data) {
                    if (data.code == 1) {
                        $.messager.show({
                            title : titleInfo,
                            msg : '已删除！',
                            timeout : timeoutValue,
                            showType : 'slide'
                        });
                        $('#QContent_version_screenshot_table').datagrid('reload',
                            parameter);
                    } else {
                        $.messager.alert(titleInfo, '删除失败！');
                    }
                }, 'json');
            }
        }
    );
}
//上下移
function moveScreenshot(value, index, type) {
    var rows = $("#QContent_version_screenshot_table").datagrid('getRows').length;
    rows = parseInt(rows) - parseInt(1);
    if (type == 1 && index == 0) {
        alert("已经是第一个了！");
    } else if (type == 2 && index == rows) {
        alert("已经是最后一个了");
    } else {
        $.post('localLifeContentVersion/move.json', {
            'sequence' : value,
            "type" : type
        }, function(data) {
            if (data.code == 1) {
                $.messager.show({
                    title : titleInfo,
                    msg : '移动成功！',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
                $('#QContent_version_screenshot_table').datagrid('reload', parameter);
            } else {
                $.messager.alert(titleInfo, '移动失败！');
            }
        }, "json");
    }
}
// 清空Bean
function resetVersionScreenshot() {
    $('#screenshotId').val(0);
    $("#screenshotUrl").attr("src", "");
    $('#screenshotUrlHidden').val('');
    $('#contentCountByVersionId').val('');
}

// 查询截图
function searchQContetnScreenshotVersion(){
    var para={};
    var versionId=$.trim($('#contentVersionId').val());
    if(versionId!=null && versionId!=''){
        para.versionId = versionId;
    }
    $("#QContent_version_screenshot_table").datagrid('reload',para);
}
// 提交版本截图
function submitContentVersionScreenshot() {
    var flag = true;
    var parameter = {};
    var id = $('#screenshotId').val();
    parameter.id = id;
    parameter.versionId = $('#contentVersionId').val();

    parameter.screenshotUrl = document.getElementById('screenshotUrl').src;
    if (  $('#screenshotUrlHidden').val() == null ||  $('#screenshotUrlHidden').val() == "") {
        alert("上传截图不能为空");
        flag = false;
    }
    if (flag == false) {
        return;
    }
    if (id != null && id != "" && id != 0) {
        $.post('localLifeContentVersion/updateScreenshot.json', parameter, function(data) {
            if (data.code == 1) {
                $('#Q_content_version_screenshot_dialog').dialog('close');
                $.messager.show({
                    title : titleInfo,
                    msg : '修改成功！',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
                resetVersionScreenshot();// 清空页面残留信息
                $('#QContent_version_screenshot_table').datagrid('reload');
            } else {
                $.messager.alert(titleInfo, '修改失败！');
            }
        }, "json");
    } else {
        $.post("localLifeContentVersion/addScreenshot.json", parameter, function(data) {
            if (data.code == 1) {
                $('#Q_content_version_screenshot_dialog').dialog('close');
                $.messager.show({
                    title : titleInfo,
                    msg : '添加成功！',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
                resetVersionScreenshot();// 清空页面残留信息
                $('#QContent_version_screenshot_table').datagrid('reload');
            } else {
                $.messager.alert(titleInfo, '添加失败！');
            }
        }, "json");
    }
}
// 上传截图
function uploadScreenshotfile() {

    var button = $("#uploadScreenshotUrl");
    var img = $("#screenshotUrl");
    //ButtonUploadByType(button, img, 5);
    ButtonUploadByType_Size(button, img, 5, 4, 455, 259);
}

//上下移
function getCountByVersionId(id) {
    $.post('localLifeContentVersion/getCountByVersionId.json', {
        versionId:id
    }, function(data) {
        if (data.code == 1) {
            $("#contentCountByVersionId").val(data.msg);
        }
    }, "json");
}