/**
    * Created by wengjingchang on 2016/6/28.
    */
$(function() {
    initRenewTask();
    batchRenewImportLive();
});

function initRenewTask() {
    $("#renew_live_task").click(function() {
        renew_reset();
        $('#renew_batch_branchName').combobox({
            url:'live/getBranchList.json',
            valueField:'id',
            textField:'branchName',
            onChange:function (id) {
                $('#renew_batch_hallName').combobox({
                    url:'live/getHallList.json?branchId='+id,
                    valueField:'id',
                    textField:'hallName',
                    editable : false
                });
            },
            onLoadSuccess:function () {
                var renewBatchBranchNameList = $('#renew_batch_branchName').combobox('getData');
                if(renewBatchBranchNameList!=null && renewBatchBranchNameList.length>0) {
                    $("#renew_batch_branchName").combobox('select',renewBatchBranchNameList[0].id);
                }
            },
            editable : false
        });
        $('#renew_batch_hallName').combobox({
            url:'live/getHallList.json',
            valueField:'id',
            textField:'hallName',
            onLoadSuccess:function () {
                var renewBatchHallNameList = $('#renew_batch_hallName').combobox('getData');
                if(renewBatchHallNameList!=null && renewBatchHallNameList.length>0) {
                    $("#renew_batch_hallName").combobox('select',renewBatchHallNameList[0].id);
                }
            },
            editable : false
        });
        $('#renew_batch_productId').combobox({
            url : 'live/getProductList.json',
            valueField : 'productId',
            textField : 'productName',
            editable : false
        });
        $('#renew_task_dialog').dialog({
            title:"批量续费任务",
            buttons : [ {
                text : '批量续费',
                handler : function() {
                    submit_live_renew_task();
                }
            },{
                text : '取消',
                handler : function() {
                    $('#renew_task_dialog').dialog('close');
                }
            } ]
        });
        $('#renew_task_dialog').dialog('open');
    });
}

//批量续费
function submit_live_renew_task() {
    var taskName = $('#renew_batch_taskName').val();
    if(taskName == null || taskName == ''){
        $.messager.alert(titleInfo, '任务名称不能为空');
        return;
    }
    var roleId = $('#roleId').val();
    var branchId = $('#renew_batch_branchName').combobox('getValue');
    var branchName = $('#renew_batch_branchName').combobox('getText');

    var hallId = $('#renew_batch_hallName').combobox('getValue');
    var hallName = $('#renew_batch_hallName').combobox('getText');

    if(branchId == null || branchId == '' || branchName == null || branchName == ''){
        $.messager.alert(titleInfo, '请选择分公司');
        return;
    }
    var productId = $('#renew_batch_productId').combobox('getValue');
    var productName = $('#renew_batch_productId').combobox('getText');
    if(productId == null || productId == '' || productName == null || productName ==''){
        $.messager.alert(titleInfo, '请选择产品包');
        return;
    }
    var year = $('#renew_batch_year').val();
    var month = $('#renew_batch_month').val();
    var day = $('#renew_batch_day').val();
    if((year == null || year == 0) && (month == null || month == 0) && (day == null || day == 0) ){
        $.messager.alert(titleInfo, '续费时长不能为零');
        return;
    }
    var excelPath = $('#renew_hiddenPath').val();
    if (excelPath == "" || excelPath == null) {
        $.messager.alert(titleInfo, '请先上传附件！');
        return;
    }
    var live = {
        taskName : taskName,
        taskDesc : $('#renew_batch_taskDesc').val(),
        branchId :branchId,
        branchName : branchName,
        hallName : hallName,
        hallId : hallId,
        productId : productId,
        productName : productName,
        chargingDurationYear : year,
        chargingDurationMonth : month,
        chargingDurationDay : day,
        excelPath : excelPath
    };
    loadDiv('正在进行续费任务，请稍后...');
    $.ajax({
        url : "liveTask/batchRenewLiveTask.json",
        async : true, //
        type : "POST",
        timeout : 6000000 ,
        dataType : "json",
        data:live,
        success : function(data) {
            if (data.code == "000") {
                $('#renew_task_dialog').dialog('close');
                $.messager.show({ title : titleInfo,msg : '续费任务执行完成:' + data.desc,timeout : timeoutValue,showType : 'slide'});
                displayLoad();
                $('#task_table').datagrid('reload', parameter);
            } else {
                displayLoad();
                $.messager.alert(titleInfo, data.desc);
                $('#task_table').datagrid('reload', parameter);
            }
        },
        complete:  function(){
            $('#renew_task_dialog').dialog('close');
            displayLoad();
            $('#task_table').datagrid('reload', parameter);
        }
    });
}

function batchRenewImportLive() {
    var import_button = $("#renew_import_live");
    new AjaxUpload(import_button, {
        action : 'excel/uploadExcel.html',
        autoSubmit : true,
        name : 'file',// 文件对象名称（不是文件名）
        data : {},
        onChange : function(file, extension) {
            var d = /\.[^\.]+$/.exec(file); // 文件后缀
            if (d != ".xls" && d != ".xlsx") {
                $.messager.alert(titleInfo, '文件格式错误，请上传.xls或.xlsx格式！');
                return false;
            } else {
                $("#renew_ImportFileName").text(file);
            }
        },
        onSubmit : function(file, extension) {// 在提交的时候触发
        },
        onComplete : function(file, response) {
            $('#renew_hiddenPath').val(response);
            $.messager.show({title : titleInfo,	msg : '文件已选定！',timeout : timeoutValue, showType : 'slide'});
        }
    });
}

function renew_reset(){
    $('#renew_batch_taskName').val('');
    $('#renew_batch_taskDesc').val('');
    $('#renew_batch_year').val(0);
    $('#renew_batch_month').val(0);
    $('#renew_batch_day').val(0);
    $('#renew_ImportFileName').empty();
	$('#renew_hiddenPath').val('');
}