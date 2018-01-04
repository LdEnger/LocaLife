var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;

// 跨域JS调用-版本数据
function initContentVersionGrid(contentId){
    parameter = {
        contentId:contentId
    };
    $("#QContent_version_table").datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        toolbar:"#contentVersionToolbar",
        fitColumns:true,
        collapsible:true,
        singleSelect:true,
        url:'localLifeContentVersion/getList.json',
        queryParams:parameter,
        remoteSort: false,
        border:false,
        idField:'id',
        columns:[[
            {field : 'versionNumber',title : '版本号',width : 40,align : 'center',
                formatter : function(value, row, index) {
                    return value;
                }
            },
            {field : 'versionDescribe',title : '版本简介',width : 80,align : 'center',
                formatter : function(value, row, index) {
                    return value;
                }
            },
            {field : 'versionSize',title : '容量',width : 40,hidden : false,align : 'center',
                formatter : function(value, row, index) {
                    return value;
                }
            },
            {field : 'updatedTime',title : '最后修改时间',width : 40,align : 'center',
                formatter : function(value, row, index) {
                    return longToDate(value,0);
                }
            },
            {field : 'id',title : '操作', width : 60,align : 'center',
                formatter : function(value, row, index) {
                    var ret =  "";
                    ret += '<a href="javascript:editContentVersion(' + value + ');">编辑</a>&nbsp;&nbsp;';
                    ret += '|&nbsp;&nbsp;<a href="javascript:initContentVersionScreenshotView(' + value + ');">版本截图</a>&nbsp;&nbsp;';
                    ret += '|&nbsp;&nbsp;<a href="javascript:delContentVersion(' + value +','+ row.contentId + ');">删除</a>';
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
// 编辑版本
function editContentVersion(id) {
    resetVersion();
    uploadApkfile();
    var rowInfo = $('#QContent_version_table').datagrid('getSelected');
    if (rowInfo) {
        $('#contentVerNumber').val(rowInfo.versionNumber);
        $('#versionId').val(rowInfo.id);
        $('#versionNumber').val(rowInfo.versionNumber);
        $('#versionDescribe').val(rowInfo.versionDescribe);
        $("#versionSize").val(rowInfo.versionSize);
        $('#downloadUrl').val(rowInfo.downloadUrl);
        $('#Q_content_version_dialog').dialog('open');
    }
}
// 删除版本
function delContentVersion(id,contentId) {
    $.messager.confirm('Warning', 'Warning：确定继续该操作?',
        function(r) {
            if (r) {
                var activity = {
                    'id' : id,
                    contentId : contentId
                };
                $.post('localLifeContentVersion/del.json', activity, function(data) {
                    if (data.code == 1) {
                        $.messager.show({
                            title : titleInfo,
                            msg : '已删除！',
                            timeout : timeoutValue,
                            showType : 'slide'
                        });
                        $('#QContent_version_table').datagrid('reload',
                            parameter);
                    } else {
                        $.messager.alert(titleInfo, '删除失败！');
                    }
                }, 'json');
            }
        });
}

// 跨域
function initContentVersionScreenshotView(contentVersionId){
    //uploadApkfile();
    //QContentVersionScreenshotAdd();
    uploadScreenshotfile();
    initContentVersionScreenshotGrid(contentVersionId);
    $('#contentVersionId').val(contentVersionId);
    $('#contentCountByVersionId').val(contentCountByVersionId);
    $("#content_version_screenshot_list").dialog('open');
}



// 清空Bean
function resetVersion() {
    $('#contentVerNumber').val('');
    $('#versionId').val(0);
    $('#versionNumber').val('');
    $('#versionDescribe').val('');
    $('#downloadUrl').val('');
    $('#versionSize').val('');
}
// 添加button版本
function QContentVersionAdd(){
    /*resetVersion();
    uploadApkfile();
    $("#Q_content_version_dialog").dialog('open');*/
    $('#QContent_Version_Add').click(function() {
        resetVersion();
        uploadApkfile();
        $("#Q_content_version_dialog").dialog('open');
    });
}

// 查询button版本
function searchQContetnVersion(){
    /*var para={};
    var contentId=$.trim($('#versionContentId').val());
    if(contentId!=null && contentId!=''){
        para.contentId = contentId;
    }
    $("#QContent_version_table").datagrid('load',para);*/
    /*$('#search_QContetn_Version').click(function() {
        var para={};
        var contentId=$.trim($('#versionContentId').val());
        if(contentId!=null && contentId!=''){
            para.contentId = contentId;
        }
        $("#QContent_version_table").datagrid('load',para);
    });*/
}
// 提交
function submitContentVersion() {
    var flag = true;
    var parameter = {};
    var id = $('#versionId').val();
    parameter.id = id;
    parameter.contentId = $('#versionContentId').val();
    parameter.versionNumber = $('#versionNumber').val();
    if ( $('#versionNumber').val() == null ||  $.trim($('#versionNumber').val()) == "") {
        alert("版本号不能为空");
        flag = false;
    }else{
        if($('#versionNumber').val().indexOf('..')>-1 || $('#versionNumber').val().substring(0,1) == '.'){
            alert("版本号格式不正确，请输入正确格式：xxx 或 xxx.xxx.xxx");
            flag = false;
        }
    }

    parameter.downloadUrl = $('#downloadUrl').val();
    parameter.versionSize = $('#versionSize').val();
    if ( $('#downloadUrl').val() == null ||  $.trim($('#downloadUrl').val()) == "") {
        alert("上传apk不能为空");
        flag = false;
    }
    parameter.versionDescribe = $('#versionDescribe').val();
    if ( $('#versionDescribe').val() == null ||  $.trim($('#versionDescribe').val()) == "") {
        alert("版本简介不能为空");
        flag = false;
    }
    if (flag == false) {
        return;
    }
    if (id != null && id != "" && id != 0) {
        var contentVerNumber = $('#contentVerNumber').val();
        if(contentVerNumber == $('#versionNumber').val()){
            parameter.panDuan = 1;
            $.post('localLifeContentVersion/update.json', parameter, function(data) {
                if (data.code == 1) {
                    $('#Q_content_version_dialog').dialog('close');
                    $.messager.show({
                        title : titleInfo,
                        msg : '修改成功！',
                        timeout : timeoutValue,
                        showType : 'slide'
                    });
                    resetVersion();// 清空页面残留信息
                    $('#QContent_version_table').datagrid('reload');
                } else {
                    $.messager.alert(titleInfo, '修改失败！');
                }   }, "json");
        }else{
            parameter.panDuan = 0;
        $.post('localLifeContentVersion/update.json', parameter, function(data) {
            if (data.code == 1) {
                $('#Q_content_version_dialog').dialog('close');
                $.messager.show({
                    title : titleInfo,
                    msg : '修改成功！',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
                resetVersion();// 清空页面残留信息
                $('#QContent_version_table').datagrid('reload');
            } else if(data.code == 3){

                    $.messager.alert(titleInfo, '版本号不可重复！');

            }else {
                $.messager.alert(titleInfo, '修改失败！');
            }
        }, "json");
    }} else {


        $.post("localLifeContentVersion/add.json", parameter, function(data) {
            if (data.code == 1) {
                $('#Q_content_version_dialog').dialog('close');
                $.messager.show({
                    title : titleInfo,
                    msg : '添加成功！',
                    timeout : timeoutValue,
                    showType : 'slide'
                });
                resetVersion();// 清空页面残留信息
                $('#QContent_version_table').datagrid('reload');
            }else  if(data.code == 3) {

                    $.messager.alert(titleInfo, '版本号不可重复！');

            }else {
                $.messager.alert(titleInfo, '添加失败！');
            }
        }, "json");
    }
}