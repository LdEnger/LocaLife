var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function() {
	initDiscountInfo();
	initAdd();

	$('#discount_detail_dialog').dialog({
		buttons : [ {
			text : '确认',
			handler : function() {
				discount_submit();
			}
		}, {
			text : '取消',
			handler : function() {
				$('#discount_detail_dialog').dialog('close');
			}
		} ]
	});
});

//编辑
function editDiscount(id) {
	var rowInfo = $('#discount_table').datagrid('getSelected');
	if (rowInfo) {
		// 设置弹出框信息
		generateDialog(rowInfo);
		initUploadImg();
		$('#discount_detail_dialog').dialog('open');
	}
}

//赋值
function generateDialog(rowInfo){
	$('#id').val(rowInfo.id);
	$('#discountName').val(rowInfo.discountName);
	$('#discountAmount').val(rowInfo.discountAmount);
	$('#imgUrl').val(rowInfo.imgUrl);
	$("#img").attr("src",rowInfo.imgUrl);
}

//初始化新增
function initAdd(){
	$("#add_discount").click(function(){
		reset();
		initUploadImg();
		$('#discount_detail_dialog').dialog('open');
	});
}

//初始化编辑框
function reset(){
	$("#id").val("");
	$("#discountName").val("");
	$("#discountAmount").val("");
	$("#imgUrl").val("");
	$("#img").attr("src","");
}

// 初始化卡券信息
function initDiscountInfo() {
	parameter = {};
	$('#discount_table').datagrid(
					{
						iconCls : 'icon-save',
						nowrap : true,
						autoRowHeight : true,
						striped : true,
						toolbar : '#discount_toolbar',
						fit : true,
						fitColumns : true,
						collapsible : true,
						url : 'discount/getList.json',
						checkOnSelect : false,
						queryParams : parameter,
						remoteSort : false,
						idField : 'id',
						columns : [ [
								{
									field : 'discountName',
									title : '优惠名称',
									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'rechargeAmount',
									title : '充值金额',
									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'discountAmount',
									title : '赠送金额',
									width : 40,
									formatter : function(value, row, index) {
										return value;
									}
								},
								{
									field : 'imgUrl',
									title : '图片',
									width : 40,
									formatter : function(value, row, index) {
										return '<img style="height:100px; width:100px;" src="' + value + '" alt="">';
									}
								},
								{
									field : 'isEffective',
									title : '有效性',
									width : 40,
									width : 40,
									formatter : function(value, row, index) {
											if (1 == value) {
												return '<div class="abtn"><a href="javascript:void(0)" class="a_on a_on2">有效</a><a href="javascript:isOff('+ row.id+ ')" class="a_off">切换</a></div>';
											} else {
												return '<div class="abtn"><a href="javascript:isOn('+ row.id+ ')" class="a_off">切换</a><a href="javascript:void(0)" class="a_on a_on2">无效</a></div>';
											}
									}
								},
								{
									field : 'oper',
									title : '操作',
									width : 40,
									formatter : function(value, row, index) {
											return '<a href="javascript:editDiscount('+row.id+')">编辑</a>|<a href="javascript:delDiscount('+row.id+')">删除</a>';
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

function discount_submit(){
	var id = $('#id').val();
	var discountName = $("#discountName").val();
	var rechargeAmount = $("#rechargeAmount").val();
	var discountAmount = $("#discountAmount").val();
	var imgUrl = $("#imgUrl").val();
	var isEffective =$("#isEffective").val();
	if(discountName==""||imgUrl==""){
		alert("请完善信息");
	}
	var discount ={
			"id":id,
			"discountName":discountName,
			"discountAmount":discountAmount,
			"rechargeAmount":rechargeAmount,
			"imgUrl":imgUrl,
			"isEffective":isEffective
	};
	
	if (id != null && id != "") {
		$.post("discount/update.json", discount, function(data) {
			if (data.code == 1) {
				$('#discount_detail_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '更新成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#discount_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '更新失败！');
			}
		}, "json");
		
	}else{
		$.post("discount/add.json", discount, function(data) {
			if (data.code == 1) {
				$('#discount_detail_dialog').dialog('close');
				$.messager.show({
					title : titleInfo,
					msg : '添加成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
				$('#discount_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		}, "json");
	}
}

//删除
function delDiscount(id){
		$.messager.confirm('Warning', 'Warning：确定要删除?', function(r) {
			if(r){
				var discount = {
						'id' : id
				};
				$.post('discount/del.json', discount, function(data) {
					if (data.code == 1) {
						$.messager.show({
							title : titleInfo,
							msg : '已删除！',
							timeout : timeoutValue,
							showType : 'slide'
						});
						$('#discount_table').datagrid('load', parameter);
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
			var discount = {
				'id' : id,
				'isEffective' : 0
			};
			$.post('discount/update.json', discount, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已下线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#discount_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '下线失败！');
				}
			}, 'json');
		}
	});
}
	
//上线
function isOn(id) {
	$.messager.confirm('Warning', '注：上线前请先确认当前活动麦粒是否充足。确定要上线?', function(r) {
		if (r) {
			var discount = {
				'id' : id,
				'isEffective' : 1
			};
			$.post('discount/update.json', discount, function(data) {
				if (data.code == 1) {
					$.messager.show({
						title : titleInfo,
						msg : '已上线！',
						timeout : timeoutValue,
						showType : 'slide'
					});
					$('#discount_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '上线失败！');
				}
			}, 'json');
		}
	});
}

//上传文件
function initUploadImg(){
		var button = $("#imgSubmit");
		new AjaxUpload(button, {
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
				$("#img").attr("src",data.picUrl);
				$("#imgUrl").val(data.picUrl);
				$('#discount_table').datagrid("reload",{});// 重新加载数据
			}
		});
}