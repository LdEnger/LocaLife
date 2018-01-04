var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;
$(function(){
	initZone();// 初始化列表
	initProvinceList();
	initAddZone();//初始化添加窗口
	$('#add_zone_dialog').dialog({
		buttons : [{
			text : '保存地市信息',
			handler : function() {
				submit_zone_form(1);
			}
		}, {
			text : '保存',
			handler : function() {
				submit_zone_form(2);
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
	parameter={"branchName":$('#q_branch_name').val()};
	$('#zone_table').datagrid({
		iconCls:'icon-save',
		nowrap: true,
		autoRowHeight: false,
		striped: true,
		toolbar: "#zone_toolbar",
		fit:true,
		fitColumns:true,
		collapsible:true,
		url:'branch/getList.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		idField:'id',
		columns : [ [
				{
					field : 'branchName',
					title : '分公司名称',
					width : 50
				},
				{
					field : 'provinceName',
					title : '省名称',
					width : 50
				},
				{
					field : 'cityName',
					title : '市名称',
					width : 50,
					formatter : function(value, row, index) {
						return value;
					}
				},
				{
					field : 'bossBranchNew',
					title : '新boss',
					width : 50,
					formatter : function(value, row, index) {
						return value;
					}
				},
				{
					field : 'bossBranchOld',
					title : '老boss',
					width : 50,
					formatter : function(value, row, index) {
						return value;
					}
				},
				{
					field : 'id',
					title : '操作',
					width :50,
					formatter : function(value, row, index) {
						return '<a href="javascript:editBranch('+value+');" >编辑</a>';
					}
				} ] ],
		pagination:true,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
}

//初始化战区和城市的关联信息
function editBranch(id){
	var rowInfo = $('#zone_table').datagrid('getSelected');
	initCityList(rowInfo.provinceCode,rowInfo.cityCode);
	if(rowInfo){
		$("#branchName").val(rowInfo.branchName);
		$("#id").val(rowInfo.id);
		$("#province").val(rowInfo.provinceCode);
		$("#city").val(rowInfo.cityCode);
		$("#bossBranchNew").val(rowInfo.bossBranchNew);
		$("#bossBranchOld").val(rowInfo.bossBranchOld);
	}
	$("#add_zone_dialog").dialog("open");
}

//清空数据
function initData(){
	$("#province").val(-1);
	$("#city").val(-1);
	$("#branchName").val("");
	$("#id").val("");
	$("#bossBranchNew").val("");
	$("#bossBranchOld").val("");
}


//提交战区信息
function submit_zone_form(type){
	var id = $('#id').val();
	var branchName = $('#branchName').val();
	if(branchName==null &&$.trim(zoneName)==""){
		$.messager.alert(titleInfo, '请填写分公司名称！');
		return false;
	}
	if($("#province").val()==-1||$("#city").val()==-1){
		$.messager.alert(titleInfo, '请选择行政区域！');
		return false;
	}
	var branch={
			"id":id,
			"branchName":branchName,
			"provinceCode":$("#province").val(),
			"cityCode":$("#city").val(),
			"provinceName":$("#province").find("option:selected").text(),
			"cityName":$("#city").find("option:selected").text(),
			"bossBranchNew":$("#bossBranchNew").val(),
			"bossBranchOld":$("#bossBranchOld").val()
	};
//	console.log(branch);
	var url ="branch/updateBranch.json";
	if(type==1){
		url ="branch/updateBranch1.json";
	}
	if(id!= null && id != ""){
		$.post(url,branch,function(data){
//			console.log(branch);
			if (data.code == 000) {
				$('#add_zone_dialog').dialog('close');
				$.messager.show({ title : titleInfo, msg : '修改成功！', timeout : timeoutValue, showType : 'slide' });
				$('#zone_table').datagrid('load', parameter);
			} else {
				$.messager.alert(titleInfo, '修改失败！'+data.desc);
			}
		},"json");
	}else{
		if(type==2){
			$.post("branch/addBranch.json",branch,function(data){
				if (data.code == 000) {
					$('#add_zone_dialog').dialog('close');
					$.messager.show({ title : titleInfo, msg : '添加成功！', timeout : timeoutValue, showType : 'slide' });
					$('#zone_table').datagrid('load', parameter);
				} else {
					$.messager.alert(titleInfo, '添加失败！'+data.desc);
				}
			},"json");
		}else{
			$.messager.alert(titleInfo, '先添加分公司信息，之后在保存地市信息');
		}
	}
}
//初始化新增战区
function initAddZone(){
	$("#add_zone").click(function(){
		initData();
		$('#add_zone_dialog').dialog('open');
	});
	$("#query_btn").click(function(){
		initZone();
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
function initCityList(provinceCode,cityCode){
	$.post("areas/getCityList.json",{"provinceCode":provinceCode},function(data){
		$("#city").html("<option value='-1' >-</option>");
		$.each(data,function(dataIndex,CityVo){
			if(cityCode!=0&&CityVo.cityCode==cityCode){
				$("#city").append('<option value='+CityVo.cityCode+' selected>'+CityVo.cityName+'</option>');
			}else{
				$("#city").append('<option value='+CityVo.cityCode+'>'+CityVo.cityName+'</option>');
			}
		});
	},"json");
}