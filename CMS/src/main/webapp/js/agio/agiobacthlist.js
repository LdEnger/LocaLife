var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
	initActivityInfo();
	addActivity();
	queryActivity();
	// 初始化编辑弹窗
	$('#agio_package_conf_dialog').dialog({
		buttons : [ {
			text : '确 定',
			handler : function() {
				submit_activity_form(0);
			}
		},
		{
			text : '取消',
			handler : function() {
				$('#agio_package_conf_dialog').dialog('close');
			}
		} ]
	});
});

// 弹出活动信息窗口
function addActivity() {
	initUploadExcel();
	$('#add_agio_package_conf').click(function() {
		reset();
		$('#agio_package_conf_dialog').dialog('open');
	});
}

// 查询活动
function queryActivity() {
	$('#query_agio_package_conf').click(function() {
		initActivityInfo();
	});
}

// 重置活动信息
function reset() {
	$('#id').val('');
	$('#agioPackageId').val('');
	$('#hiddenPath').val('');
	$("#ImportFileName").text('');
}



// 初始化活动信息
function initActivityInfo() {
	parameter = {
			'branchId':$('#q_branch').val(),
			'opUserInfo':$("#q_sender").find("option:selected").text(),
			'state':$('#q_state').val()
	};
	$('#agio_package_conf_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#agio_package_conf_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'agiopackage/getAgioBatchList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'fileName',
								title : '文件名',
								width : 40,
								formatter : function(value, row, index) {
									return value;
								}
							},
						{
							field : 'zoneId',
							title : '战区',
							width : 40,
							hidden:true,
							formatter : function(value, row, index) {
								return zoneListData[value];
							}
						},
						{
							field : 'branchId',
							title : '分公司',
							width : 40,
							formatter : function(value, row, index) {
								return branchListData[value];
							}
						},
						{
							field : 'pkgId',
							title : '折扣包名称',
							width : 40,
							formatter : function(value, row, index) {
								return agioListData[value];
							}
						},
						{
							field : 'createTime',
							title : '添加时间',
							width : 40,
							formatter : function(value, row, index) {
								return formatDate(value);
							}
						},
						{
							field : 'text',
							title : '报告',
							width : 40,
							formatter : function(value, row, index) {
								var shortValue =value;
								if(value.length>4){
									shortValue=value.substring(0,4)+"...";
								}
								var formatterValue ="<span title="+value+">"+shortValue+"</span>";
								return formatterValue;
							}
						},
						{
							field : 'state',
							title : '状态',
							width : 40,
							formatter : function(value, row, index) {
								if(value==0){
									return "初始状态";
								}else if(value==1){
									return "开通中";
								}else if(value==2){
									return "开通完成";
								}else{
									return value;
								}
							}
						},
						{
							field : 'id',
							title : '操作',
							width : 40,
							formatter : function(value, row, index) {
								var state =row.state;
								if(state==2){
									var download =row.downloadUrl;
									var action1 ='&nbsp&nbsp<a href="'+download+'" target="_blank">下载详单</a>';
									return action1;
								}else{
									return "耐心等待";
								}
								
							}
						}] ],
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}

function updateAgio(id,state){
	var agio_package_pool ={
			'id':id,
			'state':state
	};
	$.post('agiopackage/agioUpdate.json', agio_package_pool, function(data) {
		if (data.code == 1) {
			$('#agio_package_conf_dialog').dialog('close');
			$.messager.show({
				title : titleInfo,
				msg : '修改成功！',
				timeout : timeoutValue,
				showType : 'slide'
			});
			$('#agio_package_conf_table').datagrid('load', parameter);
		} else {
			$.messager.alert(titleInfo, '修改失败！');
		}
	}, "json");
}

function submit_activity_form(state) {
		var id = $('#id').val();
		var hiddenRoleId = $('#hiddenRoleId').val();
		if(hiddenRoleId!='2'){
			$.messager.alert(titleInfo, '非分公司权限人员不准添加批量操作！');
    		return false;
		}
		$.messager.confirm('', '<font color=red>请仔细核对设置信息，保存之后不可修改</font>', function(result){  
	        if(result) {  
	        	if($('#pkgId').val()==''){
	        		$.messager.alert(titleInfo, '请选择折扣包！');
	        		return false;
	        	}
	        	if($('#hiddenPath').val()==''){
	        		$.messager.alert(titleInfo, '请上传文件！');
	        		return false;
	        	}
	        	var agio_package = {
	        		'id' : id,
	        		'fileUrl':$('#hiddenPath').val(),
	        		'pkgId' : $('#pkgId').val(),
	        		'fileName':$("#ImportFileName").text()
	        	};
	        	console.log(agio_package);
	        	$.post('agiopackage/agioBatchAdd.json', agio_package, function(data) {
        			if (data.code == 1) {
        				$('#agio_package_conf_dialog').dialog('close');
        				$.messager.show({
        					title : titleInfo,
        					msg : '添加成功！',
        					timeout : timeoutValue,
        					showType : 'slide'
        				});
        				$('#agio_package_conf_table').datagrid('load', parameter);
        			} else {
        				$.messager.alert(titleInfo, '添加失败！');
        			}
        		}, "json");
	        }  
	    });  
	
}
//根据战区初始化分公司信息
function initQueryBranch(zoneId) {
	if (zoneId == "-1" || zoneId == "") {
		$("#branchId").html("<option value='-1'>全部</option>");
	} else {
		$.post("zoneCity/getBranchByZone.json", { "zoneId" : zoneId }, function(data) {
			$("#branchId").html("<option value='-1'>全部</option>");
			$.each(data, function(dataIndex, obj) {
				$("#branchId").append( '<option value=' + obj.id + '>' + obj.branchName + '</option>');
			});
		}, "json");
	}
}
//导入excel
function initUploadExcel() {
	// $('#import_card').click(function(data){
	var import_button = $("#import_card");
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
				$("#ImportFileName").text(file);
			}
		},
		onSubmit : function(file, extension) {// 在提交的时候触发
		},
		onComplete : function(file, response) {
			$('#hiddenPath').val(response);
			$.messager.show({
				title : titleInfo,
				msg : '文件已选定！',
				timeout : timeoutValue,
				showType : 'slide'
			});
		}
	});
	// });
}
function formatDate(longnow) {
	var now =new Date(longnow);
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":" + second;
}

