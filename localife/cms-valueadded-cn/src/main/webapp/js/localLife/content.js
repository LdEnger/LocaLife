$.ajaxSetup({ async: false });  //ajax同步
var parameter = {};             //表格参数
var titleInfo = "提示信息";    //消息框标题
var timeoutValue = 2000;       //消息框弹出时间

$(function() {
//-----------初始化-内容------start-------------
    //表格
    initContentInfo();
    //添加
    addContent();
    //查询
    queryContent();
    initProvinceList();
    initCityList($("#province").val());

    //上传

    uploadPic1();
    uploadPic2();
    uploadPic3();
    uploadPanorama();
    //uploadPic();//icon
    //uploadPanorama();//大图
    //提交
    $('#content_dialog').dialog({
        buttons : [ {
            text : '提交',
            handler : function() {
                submitContent();
            }
        },{
            text : '取消',
            handler : function() {
                resetBean();
                $('#content_dialog').dialog('close');
            }
        } ]
    });

//--------------初始化-内容-----end----------------------
//--------------初始化-版本------start-------------
    $("#content_version_list").dialog({
        width:850,
        closed:true,
        title:'版本管理',
        modal:true
    });
    $('#Q_content_version_dialog').dialog({
        width:450,
        closed:true,
        title:'版本设置',
        modal:true,
        buttons:[{
            text:'保存',
            handler:function(){
                submitContentVersion();
            }
        },{
            text:'取消',
            handler:function(){
                $("#Q_content_version_dialog").dialog('close');
                resetVersion();
            }
        }]
    });
    $('#addContentVersion').linkbutton({
        iconCls:'icon-add',
        plain:true
    });
    $('#searchContentVersion').linkbutton({
        iconCls:'icon-search',
        plain:true
    });
//--------------初始化-版本------end-------------
//--------------初始化-截图----start-------------
    $("#content_version_screenshot_list").dialog({
        width:550,
        closed:true,
        title:'版本截图管理',
        modal:true
    });
    $('#Q_content_version_screenshot_dialog').dialog({
        width:350,
        closed:true,
        title:'截图设置',
        modal:true,
        buttons:[{
            text:'保存',
            handler:function(){
                submitContentVersionScreenshot();
            }
        },{
            text:'取消',
            handler:function(){
                $("#Q_content_version_screenshot_dialog").dialog('close');
                resetVersionScreenshot();
            }
        }]
    });
    $('#addContentVersionScreenshot').linkbutton({
        iconCls:'icon-add',
        plain:true
    });
    $('#searchContentVersionScreenshot').linkbutton({
        iconCls:'icon-search',
        plain:true
    });
//--------------初始化-截图---end-------------
});

/*
 * -------------工具-------start---------------
 */
// easyui-datetimebox日期时间控件输出格式
$.fn.datebox.defaults.formatter = function(date) {
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
}
// 日期格式转化
function longToDate(l,type) {
	var dataObj = new Date(); // 日期对象
	dataObj.setTime(l);
	var y = dataObj.getFullYear();
	var m = dataObj.getMonth() + 1;
	var d = dataObj.getDate();
	if (d < 10) {
		d = "0" + d;
	}
	if (m < 10) {
		m = "0" + m;
	}
	if(type==1){
		return y + '-' + m + '-' + d + " " + dataObj.getHours() + ":"
		+ dataObj.getMinutes() + ":" + dataObj.getSeconds() + "";
	}else{
		return y + '-' + m + '-' + d;
	}
}
// 将button按钮赋予图片上传功能
function ButtonUploadByType(button, img, type) {
    new AjaxUpload(button, {
        action : 'util/upload.json',
        responseType : 'json',
        autoSubmit : true,
        name : 'file',// 文件对象名称（不是文件名）
        onSubmit : function(file, ext) {
            if (!(ext && /^(png|PNG|JPEG|jpeg|JPG|jpg)$/.test(ext))) {
                alert("请上传正确格式的图片");
                return false;
            }
        },
        onComplete : function(file, data) {
            alert("上传成功");
            img.attr("src", data.msg);
            if(1 == type){
                $("#picUrlHidden1").val(data.msg);
            }
            if(2 == type){
                $("#picUrlHidden2").val(data.msg);
            }
            if(3 == type){
                $("#picUrlHidden3").val(data.msg);
            }
            if(4 == type){
                $("#panoramaUrlHidden").val(data.msg);
            }
            if(5 == type){
                $("#screenshotUrlHidden").val(data.msg);
            }
        }
    });
}
//将button按钮赋予图片上传功能
function ButtonUploadByType_Size(button, img, type, size, Pwidth, Pheight) {
    //var ttimg = $("#pictureUrl");
    new AjaxUpload(button, {
        action : 'util/uploadBySize.json?fileSize='+size,
        responseType : 'json',
        autoSubmit : true,
        name : 'file',// 文件对象名称（不是文件名）
        onSubmit : function(file, ext) {
            if (!(ext && /^(png|PNG|JPEG|jpeg|JPG|jpg)$/.test(ext))) {
                alert("请上传正确格式的图片");
                return false;
            }

        },
        onComplete : function(file, data) {
            if(data.code == 1){
       /*         //ttimg.attr("src", "http://static.valueadded.usa.domybox.com/2016/06/18/16/29/28/1466238568329.jpg");
                /!*ttimg.attr("src", data.msg);
                $("<img/>").attr("src", $(ttimg).attr("src")).load(function() {
                    if(Pwidth != this.width && Pheight != this.height){
                        alert("该图片宽高不符合要求,请上传："+Pwidth+" X "+Pheight+"!");
                        ttimg.attr("src", '');
                    }else{*!/*/
                        img.attr("src", data.msg);
                        alert("上传成功");
                        if(1 == type){
                            $("#picUrlHidden1").val(data.msg);
                        }
                        if(2 == type){
                            $("#picUrlHidden2").val(data.msg);
                        }
                        if(3 == type){
                            $("#picUrlHidden3").val(data.msg);
                        }
                        if(4 == type){
                            $("#panoramaUrlHidden").val(data.msg);
                        }
                        if(5 == type){
                            $("#screenshotUrlHidden").val(data.msg);
                        }
                   // }
               // });
            }
            if(data.code == 2){
                alert(data.msg);
            }
        }
    });
}
// 将button按钮赋予图片上传功能
function ButtonUpload(button, img) {
    new AjaxUpload(button, {
        action : 'util/upload.json',
        responseType : 'json',
        autoSubmit : true,
        name : 'file',// 文件对象名称（不是文件名）
        onSubmit : function(file, ext) {
            if (!(ext && /^(png|PNG|JPEG|jpeg|JPG|jpg)$/.test(ext))) {
                alert("请上传正确格式的图片");
                return false;
            }
        },
        onComplete : function(file, data) {
            alert("上传成功");
            img.attr("src", data.msg);
        }
    });
}
// 将button按钮赋予APK上传功能
function ButtonApkUpload(button) {
    new AjaxUpload(button, {
        action : 'util/upload.json',
        responseType : 'json',
        autoSubmit : true,
        name : 'file',// 文件对象名称（不是文件名）
        onSubmit : function(file, ext) {
            if (!(ext && /^(apk)$/.test(ext))) {
                alert("请上传正确格式的apk");
                return false;
            }
        },
        onComplete : function(file, data) {
            alert("上传成功");
            //img.attr("src", data.msg);
            $("#downloadUrl").val(data.msg);
            $("#versionSize").val(data.otherMsg);
        }
    });
}
// check是否为数字
function checkNumber(value) {
    var reg = /^[0-9]*$/;
    var flag = true;
    if (!reg.test(value)) {
        alert("请输入数字!");
        flag = false;
    }
    return flag;
}
// 判空
function stringRegNull(value, name) {
    var flag = true;
    if (value == 0 || value == null || value == "") {
        alert(name+"不能为空");
        flag = false;
    }
    return flag;
}
/*
 * -------------工具-------end---------------
 */
// 添加内容
function addContent() {
    $('#add_award').click(function() {
       inttPrivinceId();
        initCityId($('#provinceId').val());
        resetBean();
        firstTab();
        showPayInfo(1);
        $("[name=typeId]").attr("disabled",false);
        $("#content_dialog").dialog('open');
    });
}
// 查询内容按钮
function queryContent() {
	$('#query_activity').click(function() {
		initContentInfo();
	});
}
// icon
function uploadPic1() {
    var button = $("#uploadPicUrl1");
    var img = $("#picUrl1");
    ButtonUploadByType_Size(button, img, 1, 4, 280, 160);
}
function uploadPic2() {
    var button = $("#uploadPicUrl2");
    var img = $("#picUrl2");
    ButtonUploadByType_Size(button, img, 2, 4, 280, 160);
}
function uploadPic3() {
    var button = $("#uploadPicUrl3");
    var img = $("#picUrl3");
    ButtonUploadByType_Size(button, img, 3, 4, 280, 160);
}
// 大图
function uploadPanorama() {
    var button = $("#uploadPanoramaUrl");
    var img = $("#panoramaUrl");
    ButtonUploadByType_Size(button, img, 4, 4, 1920, 1080);
}
// 上传apk
function uploadApkfile() {
    var button = $("#uploadApkUrl");
    //var img = $("#apkUrl");
    ButtonApkUpload(button);
}
//显示基础设置模块
function firstTab() {
	$("#tabOne").attr("style", "");
}
//新增内容-显示与隐藏
function showPayInfo(type) {
    if(type == 1){
        $('input:radio[name=typeId]')[0].checked = true
        $(".showPlayerType1").attr("style", "");
        $(".showPlayerType2").attr("style", "display:none");
        $(".showPlayerType3").attr("style", "display:none");
        resetBeanByType2();
        resetBeanByType3();
    }
    if(type == 2){
        $('input:radio[name=typeId]')[1].checked = true
        $(".showPlayerType1").attr("style", "display:none");
        $(".showPlayerType2").attr("style", "");
        $(".showPlayerType3").attr("style", "display:none");
        resetBeanByType1();
        resetBeanByType3();
    }
	if (type == 3) {
        $('input:radio[name=typeId]')[2].checked = true
		$(".showPlayerType1").attr("style", "display:none");
		$(".showPlayerType2").attr("style", "display:none");
		$(".showPlayerType3").attr("style", "");
        resetBeanByType1();
        resetBeanByType2();
    }
}
// 清空Bean
function resetBean() {
    $('#id').val(0);
    $('#name1').val('');
    $('#cityId').val('');
    $("#typeId").val(1);
    $("#provinceId").val('');
    resetBeanByType1();
    resetBeanByType2();
    resetBeanByType3();
}
// 清空BeanByType1
function resetBeanByType1() {
    $("#developerName").val('');
    $("#mark").val('');
    $("#category").val('');
    $("#describe1").val('');
    $("#picUrl1").attr("src", "");
    $("#picUrlHidden1").val('');
}
// 清空BeanByType2
function resetBeanByType2() {
    $("#videoUrl").val('');
    $("#describe2").val('');
    $('#picUrl2').attr("src", "");
    $("#picUrlHidden2").val('');
}
// 清空BeanByType3
function resetBeanByType3() {
    $('#panoramaUrl').attr("src", "");
    $("#panoramaUrlHidden").val('');
    $('#picUrl3').attr("src", "");
    $("#picUrlHidden3").val('')
}
// 赋值Bean
function setBean(rowInfo) {
    initCityId(rowInfo.provinceId);

	$('#id').val(rowInfo.id);
	$('#name1').val(rowInfo.name);
	$('#cityId').val(rowInfo.cityId);
	$('#provinceId').val(rowInfo.provinceId);
	$('#typeId').val(rowInfo.typeId);
	/*$('#developerName').val(rowInfo.developerName);
	$('#mark').val(rowInfo.mark);
	$('#category').val(rowInfo.category);
    $('#describe').val(rowInfo.describe);
    $('#picUrl').attr("src", rowInfo.picUrl);
    $('#videoUrl').val(rowInfo.videoUrl);
    $('#panoramaUrl').attr("src", rowInfo.panoramaUrl);*/
    if(1 == rowInfo.typeId){
        setBeanByType1(rowInfo);
    }
    if(2 == rowInfo.typeId){
        setBeanByType2(rowInfo);
    }
    if(3 == rowInfo.typeId){
        setBeanByType3(rowInfo);
    }
}

function setBeanByType1(rowInfo) {
    $("#developerName").val(rowInfo.developerName);
    $("#mark").val(rowInfo.mark);
    $("#category").val(rowInfo.category);
    $("#describe1").val(rowInfo.describe);
    $("#picUrl1").attr("src", rowInfo.picUrl);
    $("#picUrlHidden1").val(rowInfo.picUrl);
}
function setBeanByType2(rowInfo) {
    $("#videoUrl").val(rowInfo.videoUrl);
    $("#describe2").val(rowInfo.describe);
    $('#picUrl2').attr("src", rowInfo.picUrl);
    $("#picUrlHidden2").val(rowInfo.picUrl);
}
function setBeanByType3(rowInfo) {
    $('#panoramaUrl').attr("src", rowInfo.panoramaUrl);
    $("#panoramaUrlHidden").val(rowInfo.panoramaUrl);
    $('#picUrl3').attr("src", rowInfo.picUrl);
    $("#picUrlHidden3").val(rowInfo.picUrl)
}

// 查询内容信息
function initContentInfo() {
    parameter = {};
    var name = $('#searchName').val();
    var provinceId = $('#province').val();
    var cityId = $('#city').val();
    var typeId = $('#typeIds').val();
    var openRole = $("#openRole").val();
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    if(openRole != 1){
        parameter.cityId =cityCode;
        parameter.provinceId = proCode;
    }else {
            if (provinceId != '' && provinceId!=null) {
         parameter.provinceId = provinceId;
         }
         if (cityId != '' && cityId!=null ) {
         parameter.cityId = cityId;
         }
    }
    if ($.trim(name) != '') {
        parameter.name = name;
    }
    if (typeId != '' && typeId!='-1') {
        parameter.typeId = typeId;
    }
$('#content_table').datagrid(
    {
        iconCls:'icon-save',
        nowrap : true,
        autoRowHeight : false,
        striped : true,
        toolbar : '#content_toolbar',
        fitColumns : true,
        collapsible : true,
        url : 'localLifeContent/getList.json',
        queryParams : parameter,
        remoteSort : false,
        singleSelect : true,
        idField : 'id',
        columns : [ [
            { field : 'name', title : '内容名称', width : 40, align : 'center',
                formatter : function(value, row, index) {
                    return value;
                }
            },
            { field : 'cityName', title : '城市', width : 40, align : 'center',
                formatter : function(value, row, index) {
                    return value;
                }
            },
            { field : 'typeId', title : '类型',  width : 40, hidden : false, align : 'center',
                formatter : function(value, row, index) {
                    var result;
                    if(value==1){
                        result='应用';
                    } else
                    if(value==2){
                        result='视频';
                    } else
                    if(value==3){
                        result='图片';
                    }
                    return result;
                }
            },
            { field : 'createdTime', title : '创建时间', width : 40, align : 'center',
                formatter : function(value, row, index) {
                    return longToDate(value,0);
                }
            },
            { field : 'onlineType', title : '上线/下线', width : 40, align : 'center',
                formatter : function(value, row, index) {
                    if (1 == value) {
                        return '<div class="abtn"><a href="javascript:isOff('+row.id+','+ row.onlineType +')" class="a_off">下线</a><a href="javascript:isOn('+ row.id+ ','+ row.onlineType +')" class="a_on a_on2">上线</a></div>';
                    } else {
                        return '<div class="abtn"><a href="javascript:isOff('+row.id+','+ row.onlineType +')" class="a_on a_on2">下线</a><a href="javascript:isOn('+ row.id+','+ row.onlineType +')" class="a_off">上线</a></div>';
                    }
                }
            },
            { field : 'id', title : '操作', width : 40, align : 'center',
                formatter : function(value, row, index) {
                    var ret =  "";
                        ret += '<a href="javascript:editContent(' + value +','+ row.typeId + ');">编辑</a>&nbsp;&nbsp;';
                    if(row.typeId == 1){
                        ret += '|&nbsp;&nbsp;<a href="javascript:initContentVersionView('+ value + ');">版本</a>&nbsp;&nbsp;';
                    }
                        ret += '|&nbsp;&nbsp;<a href="javascript:delContent(' + value + ','+row.provinceId + ','  + row.cityId + ');">删除</a>';
                    return  ret;
                }
            } ] ],
        pagination : true,
        rownumbers : true,
        onClickRow : function(rowIndex) {
        }
    });
}

// 提交
function submitContent() {
    debugger;
    var flag = true;
    var parameter = {};
    var id = $('#id').val();
    parameter.id = id;
    parameter.name = $('#name1').val();
    if ( $('#name1').val() == null ||  $.trim($('#name1').val()) == "") {
        alert("内容名称不能为空");
        flag = false;
    }
    parameter.cityId = $('#cityId').val();
    if ( $('#cityId').val() == null ||  $('#cityId').val() == "") {
        alert("城市不能为空");
        flag = false;
    }
    parameter.provinceId = $('#provinceId').val();
    if ( $('#provinceId').val() == null ||  $('#provinceId').val() == "") {
        alert("城市不能为空");
        flag = false;
    }
    parameter.provinceName = $("#provinceId").find("option:selected").text();
    parameter.cityName = $("#cityId").find("option:selected").text();
    parameter.typeId = $("input[name='typeId']:checked").val();
    if ( $("input[name='typeId']:checked").val() == null || $("input[name='typeId']:checked").val() == "") {
        alert("应用类型不能为空");
        flag = false;
    }
    if($("input[name='typeId']:checked").val()==1){

        parameter.developerName = $('#developerName').val();
        if ( $('#developerName').val() == null ||  $.trim($('#developerName').val()) == "") {
            alert("开发商不能为空");
            flag = false;
        }
        parameter.mark = $('#mark').val();
        if ( $('#mark').val() == null ||  $.trim($('#mark').val()) == "") {
            alert("标识(apk包名)不能为空");
            flag = false;
        }
        parameter.category = $('#category').val();
        if ( $('#category').val() == null ||  $.trim($('#category').val()) == "") {
            alert("应用类别不能为空");
            flag = false;
        }
        parameter.describe = $('#describe1').val();
        if ( $('#describe1').val() == null ||  $('#describe1').val() == "") {
            alert("内容提要不能为空");
            flag = false;
        }
        parameter.picUrl = document.getElementById('picUrl1').src;
        if ($('#picUrlHidden1').val() == null ||  $('#picUrlHidden1').val() == "") {
            alert("应用icon不能为空");
            flag = false;
        }
    }
    if($("input[name='typeId']:checked").val()==2){
        parameter.describe = $('#describe2').val();
        if ( $('#describe2').val() == null ||  $('#describe2').val() == "") {
            alert("视频介绍不能为空");
            flag = false;
        }
        parameter.picUrl = document.getElementById('picUrl2').src;
        if ($('#picUrlHidden2').val() == null ||  $('#picUrlHidden2').val() == "") {
            alert("视频icon不能为空");
            flag = false;
        }
        parameter.videoUrl = $('#videoUrl').val();
        if (  $('#videoUrl').val() == null ||   $('#videoUrl').val() == "") {
            alert("视频URL不能为空");
            flag = false;
        }
    }
    if($("input[name='typeId']:checked").val()==3){
        parameter.picUrl = document.getElementById('picUrl3').src;
        if ($('#picUrlHidden3').val() == null || $('#picUrlHidden3').val() == "") {
            alert("图片icon不能为空");
            flag = false;
        }
        parameter.panoramaUrl = document.getElementById('panoramaUrl').src;
        if ( $('#panoramaUrlHidden').val() == null ||  $('#panoramaUrlHidden').val() == "") {
            alert("上传图片不能为空");
            flag = false;
        }
    }
	if (flag == false) {
		return;
    }
	if (id != null && id != "" && id != 0) {
		$.post('localLifeContent/update.json', parameter, function(data) {
			if (data.code == 1) {
				$('#content_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '修改成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				resetBean();// 清空页面残留信息
				$('#content_table').datagrid('reload');
			} else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		}, "json");
	} else {
		$.post("localLifeContent/add.json", parameter, function(data) {
			if (data.code == 1) {
				$('#content_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				resetBean();// 清空页面残留信息
				$('#content_table').datagrid('reload');
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		}, "json");
	}
}

function inputDigLog() {
    $('#province_and_city').dialog('open');
}
// 编辑
function editContent(id,typeId) {
    inttPrivinceId();
    $("[name=typeId]").attr("disabled",true);
    resetBean();
    firstTab();
    showPayInfo(typeId);
    var rowInfo = $('#content_table').datagrid('getSelected');
    if (rowInfo) {
        setBean(rowInfo);
        $('#content_dialog').dialog('open');
    }
}
// 删除
function delContent(id,provinceId,cityId) {
	$.messager.confirm('Warning', 'Warning：该内容有可能已经绑定标签或推荐，删除会把已关联的标签和推荐一同删除！确定要删除？',
        function(r) {
            if (r) {
                var activity = {
                    'id' : id,
                    'provinceId': provinceId,
                    'cityId':cityId
                };
                $.post('localLifeContent/del.json', activity, function(data) {
                    if (data.code == 1) {
                        $.messager.show({
                            title : titleInfo,
                            msg : '已删除！',
                            timeout : timeoutValue,
                            showType : 'slide'
                        });
                        $('#content_table').datagrid('reload',
                                parameter);
                        initContentInfo();
                    } else {
                        $.messager.alert(titleInfo, '删除失败！');
                    }
                }, 'json');
            }
        }
    );
}

// 上下移动
function move(value, index, type) {

	var rows = $("#content_table").datagrid('getRows').length;
	rows = parseInt(rows) - parseInt(1);
	if (type == 1 && index == 0) {
		alert("已经是第一个了！");
	} else if (type == 2 && index == rows) {
		alert("已经是最后一个了");
	} else {
		$.post('awardActivity/move.json', {
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
				$('#content_table').datagrid('reload', parameter);
			} else {
				$.messager.alert(titleInfo, '移动失败！');
			}
		}, "json");
	}
}
// 上线
function isOn(id,onlineType){
    if(onlineType  == 1){
        alert("已经是上线状态");
    }else {
	$.messager.confirm('Warning', '确定要上线?', function(r) {
		if (r) {
			$.post('localLifeContent/update.json', {id:id,onlineType:1}, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已上线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#content_table').datagrid('reload', parameter);
                    initContentInfo();
				} else {
					$.messager.alert(titleInfo, '上线失败！');
				}
			}, 'json');
		}
	});
    }
}
// 下线
function isOff(id,onlineType){
    if(onlineType == 0){
        alert("已经是下线状态");
    }else {
	$.messager.confirm('Warning', '确定要下线?', function(r) {
		if (r) {
			$.post('localLifeContent/update.json', {id:id,onlineType:0}, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已下线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#content_table').datagrid('reload', parameter);
                    initContentInfo();
				} else {
					$.messager.alert(titleInfo, '下线失败！');
				}
			}, 'json');
		}
	});
    }
}
function  inttPrivinceId(){
    var openRole = $("#openRole").val();
    $("#provinceId").empty();
    $("#cityId").empty();
    var province = $("#province").val();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#provinceId").append("<option value='"+ proCode+"' >" + proName + "</option>");
    }else{
        $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
            var resu = data.result;
            $("#provinceId").append("<option value='00'>默认省</option>");
            for (var o in resu){
                if(resu[o].name != ''){
                    $("#provinceId").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
                }
            }
        },"json");
    }
}

// 通过战区动态加载（省、州）
function initProvinceList(){

    var openRole = $("#openRole").val();
    $("#province").empty();
    $("#city").empty();
    var province = $("#province").val();
    var proCode = $("#proCode").val();
    var proName = $("#proName").val();
    if(openRole != 1){
        $("#province").append("<option value='"+proCode+"'>" + proName + "</option>");
    }else{
    $.post("zoneTree/getProvinceList.json",{'pid':'00'},function(data){
        var resu = data.result;
        $("#province").append("<option value=''>全部</option>");
        $("#province").append("<option value='00'>默认省</option>");
        for (var o in resu){
            if(resu[o].name != ''){
                $("#province").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
            }
        }
    },"json");
    }
}
function initCityId(provinceId) {
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var cityName = $("#cityName").val();
    $("#cityId").empty();
    var openRole = $("#openRole").val();
    if(openRole != 1){
        $("#cityId").append("<option value='"+cityCode+"'>"+ cityName +"</option>");
        return;
    }if(provinceId == 00)  {
         $("#cityId").append("<option value='0000'>默认市</option>");
    } else{
        $.post("zoneTree/getCityList.json",{'pid':provinceId},function(data){
            var resu = data.result;
            for (var o in resu){
                if(resu[o].name !=''){
                    $("#cityId").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
                }
            }
        },"json");
    }
}
// 通过省、州 加载城市
function initCityList(provinceId){
    var proCode = $("#proCode").val();
    var cityCode = $("#cityCode").val();
    var cityName = $("#cityName").val();
    $("#city").empty();
    var openRole = $("#openRole").val();
    if(openRole != 1){
        $("#city").append("<option value='"+cityCode+"'>" + cityName + "</option>");
    }else
    if(provinceId == ''){
        $("#city").append("<option value=''>-</option>");
    }else if(provinceId == 00){
        $("#city").append("<option value='0000'>默认市</option>");
    }else {
        $.post("zoneTree/getCityList.json",{'pid':provinceId},function(data){
            $("#city").html("<option value=''>全部</option>");
            var resu = data.result;
            for (var o in resu){
                if(resu[o].name != ''){
                        $("#city").append("<option value='"+resu[o].code+"'>"+resu[o].name+"</option>");
                }
            }
        },"json");
    }
}
/**
 * ------------跨域调用其他JS---------start-------------------
 */
// 版本
function initContentVersionView(contentId){
    QContentVersionAdd();
    //searchQContetnVersion();
    $("#versionNumber").keyup(function(){
            $(this).val($(this).val().replace(/[^0-9.]/g,''));
        }).bind("paste",function(){
            $(this).val($(this).val().replace(/[^0-9.]/g,''));
        }).css("ime-mode", "disabled");
    // 初始化 版本 数据
    initContentVersionGrid(contentId);
    $('#versionContentId').val(contentId);
    $("#content_version_list").dialog('open');
}

function inputRadioOclick(){
   var int = $("input[name='certType']:checked").val();
   var ss = $("#dx").val();
   console.log(ss);
   $("#inputDig").val(int);
    $("#province_and_city").dialog('close');
}
/**
 * ------------跨域调用其他JS---------end-------------------
 */
/*$(function () {
    $('#cityId').combotree
        ({ url: '../Views/DownLoad.aspx?ExportType=getDepartment',
            valueField: 'id',
            textField: 'text',
            required: true,
            editable: false,
            onClick: function (node) {
                JJ.Prm.GetDepartmentUser(node.id, 'selUserFrom');
            }, //全部折叠
            onLoadSuccess: function (node, data) {
                $('#selDepartmentFrom').combotree('tree').tree("collapseAll");
            }
        });
});*/