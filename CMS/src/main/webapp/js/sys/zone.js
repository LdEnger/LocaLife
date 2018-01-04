var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function(){
	initZone();
	initAddZone();
	addCityToZone();
	$('#selected_city_dialog').dialog({
		buttons : [{
			text : '关闭',
			handler : function() {
				$('#selected_city_dialog').dialog('close');
			}
		} ]
	});
	$('#zone_branch_dialog').dialog({
		buttons : [{
			text : '关闭',
			handler : function() {
				$('#zone_branch_dialog').dialog('close');
			}
		} ]
	});
	$('#branch_hall_dialog').dialog({
		buttons : [{
			text : '关闭',
			handler : function() {
				$('#branch_hall_dialog').dialog('close');
			}
		} ]
	});
	$('#add_zone_dialog').dialog({
		buttons : [ {
			text : '保存',
			handler : function() {
				submit_zone_form();
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#add_zone_dialog').dialog('close');
			}
		} ]
	});
});

function initZone(){
	$('#zone_table').datagrid({
		iconCls:'icon-save',
		nowrap: true,
		autoRowHeight: false,
		striped: true,
		toolbar: "#zone_toolbar",
		fit:true,
		fitColumns:true,
		collapsible:true,
		url:'zone/getList.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		idField:'id',
		columns : [ [
				{
					field : 'zoneName',
					title : '战区名称',
					width : 50
				},
				{
					field : 'updateTime',
					title : '更新时间',
					width : 50
				},
				{
					field : 'detail',
					title : '详情',
					width : 50,
					formatter : function(value, row, index) {
						return '<a href="javascript:initZoneBranch('+row.id+');" >详情</a>';
					}
				},
				{
					field : 'id',
					title : '操作',
					width :50,
					formatter : function(value, row, index) {
						return '<a href="javascript:zoneDetail('+value+');" >编辑</a>|<a href="javascript:editZone('+value+');" >修改</a>';
					}
				} ] ],
		pagination:true,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
}

//初始化战区和城市的关联信息
function zoneDetail(id){
	initData();
	var rowInfo = $('#zone_table').datagrid('getSelected');
	if(rowInfo){
		$("#zoneName").val(rowInfo.zoneName);
		$("#id").val(id);
	}
	initProvinceList();
	initSelectedCity(id);
	initCityList();
	$("#selected_city_dialog").dialog("open");
}

//清空数据
function initData(){
	$("#province").val(-1);
	$("#city").val(-1);
	$("#zoneName").val("");
	$("#id").val();
}

//初始化战区和分公司的关联信息
function initZoneBranch(id){
	$("#zoneBranchId").val("");
	$("#zoneBranchName").val("");
	var rowInfo = $('#zone_table').datagrid('getSelected');
	if(rowInfo){
		$("#zoneBranchId").val(id);
		$("#zoneBranchName").val(rowInfo.zoneName);
	}
	initZoneBranchInfo(id);
	$("#zone_branch_dialog").dialog("open");
}

function initZoneBranchInfo(id){
	parameter.zoneId = id;
	$('#zone_branch_table').datagrid({
		iconCls:'icon-save',
		nowrap: true,
		autoRowHeight: false,
		striped: true,
		toolbar: "",
		fit:true,
		fitColumns:true,
		collapsible:true,
		url:'zoneCity/getBranchByZone.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		idField:'id',
		columns : [ [
				{
					field : 'branchName',
					title : '分公司名称',
					width : 100
				},
				{
					field : 'containHall',
					title : '是否包含分公司',
					hidden:true
				},
				{
					field : 'id',
					title : '操作',
					width : 70,
					formatter : function(value, row, index) {
						if(row.containHall){
							return '<a href="javascript:queryHall('+value+')">查看下属营业厅</a>';
						}else{
							return '<a href="javascript:delBranch('+value+')" >删除</a>';
						}
					}
				}
				] ],
		pagination:true,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
}

//删除分公司
function delBranch(id){
	$.messager.confirm('Warning', '确定要删除?', function(r) {
		if (r) {
			var branch = {
					'id' : id
				};
			$.post("branch/del.json",branch,function(data){
				if (data.code == 1) {
					$.messager.show({ title : titleInfo, msg : '已删除！', timeout : timeoutValue, showType : 'slide' });
					$('#zone_branch_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '删除失败！');
				}
			},"json");
		}});
}

//查看分公司下所有营业厅
function queryHall(id){
	$("#branchId").val('');
	$("#branchName").val('');
	var rowInfo = $('#zone_branch_table').datagrid('getSelected');
	if(rowInfo){
		$("#branchId").val(id);
		$("#branchName").val(rowInfo.branchName);
	}
	initBranchHall(id);
	$("#branch_hall_dialog").dialog("open");
}

//初始化分公司下辖营业厅
function initBranchHall(id){
	parameter.branchId = id;
	$('#branch_hall_table').datagrid({
		iconCls:'icon-save',
		nowrap: true,
		autoRowHeight: false,
		striped: true,
		toolbar: "",
		fit:true,
		fitColumns:true,
		collapsible:true,
		url:'hall/getHallList.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		idField:'id',
		columns : [ [
					{
						field : 'containUser',
						title : '是否包含用户',
						hidden:true
					},
				{
					field : 'hallName',
					title : '营业厅名称',
					width : 100
				},
				{
					field : 'id',
					title : '操作',
					width : 70,
					formatter : function(value, row, index) {
						if(row.containUser){
							return '/';	
						}else{
							return '<a href="javascript:delHall('+value+')" >删除</a>';
						}
					}
				}
				] ],
		pagination:true,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
}

//删除营业厅
function delHall(id){
	$.messager.confirm('Warning', '确定要删除?', function(r) {
		if (r) {
			var hall = {
					'id' : id
				};
			$.post("hall/del.json",hall,function(data){
				if (data.code == 1) {
					$.messager.show({ title : titleInfo, msg : '已删除！', timeout : timeoutValue, showType : 'slide' });
					$('#branch_hall_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '删除失败！');
				}
			},"json");
	}});
}

//初始化该战区已经选择的城市
function initSelectedCity(id){
	parameter.zoneId = id;
	$('#selected_city_table').datagrid({
		iconCls:'icon-save',
		nowrap: true,
		autoRowHeight: false,
		striped: true,
		toolbar: "",
		fit:true,
		fitColumns:true,
		collapsible:true,
		url:'zoneCity/getList.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		idField:'id',
		columns : [ [
						{
							field : 'containBranch',
							title : '是否包含分公司',
							hidden:true
						},
				{
					field : 'provinceName',
					title : '省',
					width : 100
				},
				{
					field : 'cityName',
					title : '市',
					width : 100
				},
				{
					field : 'id',
					title : '操作',
					width : 70,
					formatter : function(value, row, index) {
						if(row.containBranch){
							return '/';
						}else{
							return '<a href="javascript:delZoneCity('+value+')" >移除</a>';
						}
					}
				} ] ],
		pagination:true,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
}

//初始化省列表
function initProvinceList(){
	$.post("areas/getProvinceList.json",{page:1,rows:50},function(data){
		$("#province").html("<option value='-1' >-</option>");
		$.each(data,function(dataIndex,ProvinceVo){
			$("#province").append('<option value='+ProvinceVo.provinceCode+'>'+ProvinceVo.provinceName+'</option>');
		});
	},"json");
}

//初始化市列表
function initCityList(provinceCode){
	$.post("areas/getCityList.json",{"provinceCode":provinceCode},function(data){
		$("#city").html("<option value='-1' >-</option>");
		$.each(data,function(dataIndex,CityVo){
			$("#city").append('<option value='+CityVo.cityCode+'>'+CityVo.cityName+'</option>');
		});
	},"json");
}
//向战区添加城市
function addCityToZone(){
	$("#addCity").click(function(){
		var provinceId=$("#province").val();
		var provinceName = $("#province  option:selected").text();
		var cityId=$("#city").val();
		var cityName = $("#city  option:selected").text();
		var zoneName =$("#zoneName").val();
		var zoneId=$("#id").val();
		if(zoneId==""||zoneId==null){
			$.messager.alert(titleInfo, '没有获取到战区信息，请关闭重新添加！');
			return false;
		}
		if(cityId=="-1"||cityId==""||cityId==null){
			$.messager.alert(titleInfo, '请先选择城市！');
			return false;
		}
		var zoneCity = {
				"provinceId":provinceId,
				"provinceName":provinceName,
				"cityId":cityId,
				"cityName":cityName,
				"zoneId":zoneId,
				"zoneName":zoneName
		};
		$.post("zoneCity/addCityToZone.json",zoneCity,function(data){
			if(data.code == 1){
				$.messager.show({ title : titleInfo, msg : '已添加！', timeout : timeoutValue, showType : 'slide' });
				$('#selected_city_table').datagrid('load', parameter);
			}else if(data.code == 2){
				$.messager.alert(titleInfo, '该城市已经存在于战区信息中！');
			}else{
				$.messager.alert(titleInfo, '添加失败！');
			}
		},"json");
	});
}

//删除一条关联
function delZoneCity(id){
	$.messager.confirm('Warning', '确定要移除?', function(r) {
		if (r) {
			var zoneCity = {
					'id' : id
				};
			$.post("zoneCity/del.json",zoneCity,function(data){
				if (data.code == 1) {
					$.messager.show({ title : titleInfo, msg : '已移除！', timeout : timeoutValue, showType : 'slide' });
					$('#selected_city_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '移除失败！');
				}
			},"json");
		}});
}

//初始化新增战区
function initAddZone(){
	$("#add_zone").click(function(){
		$("#zoneId").val("");
		$("#addZoneName").val("");
		$("#add_zone_dialog").dialog("open");
	});
}

//初始化编辑战区信息
function editZone(id){
	var rowInfo = $('#zone_table').datagrid('getSelected');
	if(rowInfo){
		$("#zoneId").val(rowInfo.id);
		$("#addZoneName").val(rowInfo.zoneName);
		$("#add_zone_dialog").dialog("open");
	}
}

//提交战区信息
function submit_zone_form(){
	var zoneId = $('#zoneId').val();
	var zoneName = $('#addZoneName').val();
	var zone={
			'id' : zoneId,
			"zoneName":zoneName
	};
	if(zoneName==null &&$.trim(zoneName)==""){
		$.messager.alert(titleInfo, '请填写战区名称！');
	}
	if(zoneId!= null && zoneId != ""){
		$.post("zone/update.json",zone,function(data){
			if (data.code == 1) {
				$('#add_zone_dialog').dialog('close');
				$.messager.show({ title : titleInfo, msg : '修改成功！', timeout : timeoutValue, showType : 'slide' });
				$('#zone_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		},"json");
	}else{
		$.post("zone/add.json",zone,function(data){
			if (data.code == 1) {
				$('#add_zone_dialog').dialog('close');
				$.messager.show({ title : titleInfo, msg : '添加成功！', timeout : timeoutValue, showType : 'slide' });
				$('#zone_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '添加失败！');
			}
		},"json");
	}
}