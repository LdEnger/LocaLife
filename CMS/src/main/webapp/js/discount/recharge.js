var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
	initRechargeInfo();
	initAdd();
	initQuery();
	$('#recharge_detail_dialog').dialog({
		buttons : [ {
			text : '确认',
			handler : function() {
				submit_recharge();
			}
		}, {
			text : '取消',
			handler : function() {
				$('#recharge_detail_dialog').dialog('close');
			}
		} ]
	});
});

//初始化查询
function initQuery(){
	$('#query_recharge').click(function(){
		initRechargeInfo();
	});
}

//初始化新增
function initAdd(){
	$("#add_recharge").click(function(){
		reset();
		initUploadImg();
		$('#recharge_detail_dialog').dialog('open');
	});
}

//初始化编辑框
function reset(){
	$("#id").val("");
	$("#editRechargeAmount").val("");
	$("#editRechargeName").val("");
	$("#editDescription").val("");
	$("#thumbImg").attr("src","");
	$("#bigImg").attr("src","");
	$("#thumbImgUrl").val("");
	$("#bigImgUrl").val("");
}

function editRecharge(){
	var rowInfo = $('#recharge_table').datagrid('getSelected');
	if (rowInfo) {
		// 设置弹出框信息
		generateDialog(rowInfo);
		initUploadImg();
		$('#recharge_detail_dialog').dialog('open');
	}
}

//赋值
function generateDialog(rowInfo){
	$('#id').val(rowInfo.id);
	$('#editRechargeAmount').val(rowInfo.rechargeAmount);
	$('#editRechargeName').val(rowInfo.rechargeName);
	$('#editDescription').val(rowInfo.description);
	$('#thumbImgUrl').val(rowInfo.thumbnailImg);
	$('#bigImgUrl').val(rowInfo.bigImg);
	$("#thumbImg").attr("src",rowInfo.thumbnailImg);
	$("#bigImg").attr("src",rowInfo.bigImg);
}

// 初始化
function initRechargeInfo() {
	parameter = {};
	var rechargeName = $('#rechargeName').val();
	if (rechargeName != '') {
		parameter.rechargeName = rechargeName;
	}
	var status = $('#status').val();
	if (status != -1) {
		parameter.status = status;
	}
	$('#recharge_table').datagrid(
					{
						iconCls : 'icon-save',
						nowrap : true,
						autoRowHeight : true,
						striped : true,
						toolbar : '#recharge_toolbar',
						fit : true,
						fitColumns : true,
						collapsible : true,
						url : 'recharge/getList.json',
						checkOnSelect : false,
						queryParams : parameter,
						remoteSort : false,
						idField : 'id',
						columns : [ [
								{
									field : 'rechargeAmount',
									title : '数量',
									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'rechargeName',
									title : '名称',
									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'thumbnailImg',
									title : '缩略图',
									width : 40,
									formatter : function(value, row, index) {
										return '<img style="height:100px; width:100px;" src="' + value + '" alt="">';
									}
								},
								{
									field : 'bigImg',
									title : '大图',
									width : 40,
									formatter : function(value, row, index) {
										return '<img style="height:100px; width:100px;" src="' + value + '" alt="">';
									}
								},
								{
									field : 'description',
									title : '描述',
									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'status',
									title : '状态',
									width : 40,
									width : 40,
									formatter : function(value, row, index) {
											if (1 == value) {
												return '<div class="abtn"><a href="javascript:void(0)" class="a_on a_on2">已上线</a><a href="javascript:isOff('+ row.id+ ')" class="a_off">切换</a></div>';
											} else {
												return '<div class="abtn"><a href="javascript:isOn('+ row.id+ ')" class="a_off">切换</a><a href="javascript:void(0)" class="a_on a_on2">未上线</a></div>';
											}
									}
								},
								{
									field : 'oper',
									title : '操作',
									width : 40,
									formatter : function(value, row, index) {
											return '<a href="javascript:editRecharge('+row.id+')">修改</a>|<a href="javascript:delRecharge('+row.id+')">删除</a>';
									}
								}
								] ],
						pagination : true,
						fitColumns:true,
						rownumbers : true,
						onClickRow : function(rowIndex,row) {
						}
			});
}

function submit_recharge(){
	var id = $('#id').val();
	var rechargeAmount = $("#editRechargeAmount").val();
	var rechargeName = $("#editRechargeName").val();
	var description = $("#editDescription").val();
	var status =$("#editStatus").val();
	var thumbnailImg =$("#thumbImgUrl").val();
	var bigImg =$("#bigImgUrl").val();
	var recharge ={
			"id":id,
			"rechargeAmount":rechargeAmount,
			"rechargeName":rechargeName,
			"description":description,
			"thumbnailImg":thumbnailImg,
			"bigImg":bigImg,
			"status":status
	};
	if (id != null && id != "") {
		$.post("recharge/update.json", recharge, function(data) {
			if (data.code == 1) {
				$('#recharge_detail_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '更新成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#recharge_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '更新失败！');
			}
		}, "json");
	}else{
		$.post("recharge/add.json", recharge, function(data) {
			if (data.code == 1) {
				$('#recharge_detail_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#recharge_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		}, "json");
	}
}

function initUploadImg(){
	var thumbBtn = $("#thumbImgSubmit");
	new AjaxUpload(thumbBtn, {
		action : 'upload/newsPic.json?',
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
			$("#thumbImg").attr("src",data.picUrl);
			$("#thumbImgUrl").val(data.picUrl);
			$('#recharge_table').datagrid("reload",{});// 重新加载数据
		}
	});
	
	var bigBtn = $("#bigImgSubmit");
	new AjaxUpload(bigBtn, {
		action : 'upload/newsPic.json?',
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
			$("#bigImg").attr("src",data.picUrl);
			$("#bigImgUrl").val(data.picUrl);
			$('#recharge_table').datagrid("reload",{});// 重新加载数据
		}
	});
}

//删除
function delRecharge(id){
		$.messager.confirm('Warning', 'Warning：确定要删除?', function(r) {
			if(r){
				var recharge = {
						'id' : id
				};
				$.post('recharge/del.json', recharge, function(data) {
					if (data.code == 1) {
						$.messager.show({
							title : titleInfo,
							msg : '已删除！',
							timeout : timeoutValue,
							showType : 'slide'
						});
						$('#recharge_table').datagrid('load', parameter);
					} else {
						$.messager.alert(titleInfo, '删除失败！');
					}
				}, 'json');
			}
		});
}

//下线
function isOff(id) {
	$.messager.confirm('Warning', '确定要下线?', function(r) {
		if (r) {
			var recharge = {
				'id' : id,
				'status' : 0
			};
			$.post('recharge/update.json', recharge, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已下线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#recharge_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '下线失败！');
				}
			}, 'json');
		}
	});
}
	
//上线
function isOn(id) {
	$.messager.confirm('Warning', '确定要上线?', function(r) {
		if (r) {
			var recharge = {
				'id' : id,
				'status' : 1
			};
			$.post('recharge/update.json', recharge, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已上线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#recharge_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '上线失败！');
				}
			}, 'json');
		}
	});
}