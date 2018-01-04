var parameter = {};
var dataList={};
var updateFormValid;
var validateResult;
var timeoutValue = 2000;
$(function() {
	
	//设置该页面ajax默认同步属性
	$.ajaxSetup({
        async: false
    });
	
	//初始化弹出框
	$('#user_detail_dialog').dialog({
		buttons:[{
			text:'确 定',
			handler:function(){
				submit_model_window();
			}
		},{
			text:'取消',
			handler:function(){
				$('#user_detail_dialog').dialog('close');
			}
		}]
	});
	//解决IE浏览器不支持.trim()方法
	String.prototype.trim = function () {
		return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
	};
	
	//初始化表格
	initAuth();
	initDataGrid();
	initAddUser();
	//初始化权限选择
	initRoleList();
	initBranchList();
	initProvinceList();
	initCityList();
	initHallList();
	initQuery();
});


//添加用户
function initAddUser(){
	$('#user_add').click(function(){
		$('#user_detail_form')[0].reset();
		changeView();//请勿更换本行代码位置
		$('#userId').val('');
		$('#user_detail_dialog').dialog('open');
	});
}

function initQuery(){
	$('#user_query').click(function(){
		initDataGrid();
	});
}

//初始化角色列表
function initRoleList(){
	$.post("sysRole/getSysRoleList.json",{page:1,rows:50},function(data){
		$("#roleId").html("--");
		$.each(data.rows,function(dataIndex,role){
			if(role.isEffective!=0){
				$("#roleId").append('<option value='+role.roleId+'>'+role.roleName+'</option>');
			}
		});
	},"json");
}

//初始化省列表
function initProvinceList(){
	$.post("areas/getProvinceList.json",{page:1,rows:50},function(data){
		$("#provincehId").html("<option value='-1' >-</option>");
		$.each(data,function(dataIndex,ProvinceVo){
			$("#provincehId").append('<option value='+ProvinceVo.provinceCode+'>'+ProvinceVo.provinceName+'</option>');
		});
	},"json");
}

//初始化市列表
function initCityList(provinceCode){
	$.post("areas/getCityList.json",{"provinceCode":provinceCode},function(data){
		$("#cityId").html("<option value='-1' >-</option>");
		initBranchList();
		initHallList();
		$.each(data,function(dataIndex,CityVo){
			$("#cityId").append('<option value='+CityVo.cityCode+'>'+CityVo.cityName+'</option>');
		});
	},"json");
}

//初始化分公司名单
function initBranchList(cityVoCode){
	$.post("branch/getBranchList.json",{"cityCode":cityVoCode},function(data){
		$("#branchId").html("<option value='-1' >-</option>");
		initHallList();
		$.each(data,function(dataIndex,branch){
			$("#branchId").append('<option value='+branch.id+'>'+branch.branchName+'</option>');
		});
	},"json");
}

//初始化营业厅名单
function initHallList(branchId){
	$.post("hall/getHallList.json",{"branchId":branchId},function(data){
		$("#hallId").html("<option value='-1' >-</option>");
		$.each(data,function(dataIndex,hall){
			$("#hallId").append('<option value='+hall.id+'>'+hall.hallName+'</option>');
		});
	},"json");
	
//	$.ajax({
//		type:"POST",
//		async: false,
//		url:'hall/getHallList.json',
//		data:{"branchId":branchId},
//		dataType:"json",
//		success:function(data){
//			$("#hallId").html("<option value='' >-</option>");
//			$.each(data,function(dataIndex,hall){
//				console.log(hall.id);
//				$("#hallId").append('<option value='+hall.id+'>'+hall.hallName+'</option>');
//			});
//		}
//	});
	
}

//修改
function userEdit(userId){
	$('#user_table').datagrid('selectRecord',userId);
	var rowInfo =  $('#user_table').datagrid('getSelected');
	if(rowInfo){
		//设置弹出框信息
		generateDialog(rowInfo);
		$('#user_detail_dialog').dialog('open');
	}
}

//保存
function submit_model_window(){
	var userId = $("#userId").val();
	var user = {
			"userName":$("#userName").val(),
			"userPwd":$("#userPwd").val(),
			"userMail":$("#userMail").val(),
			"phoneNumber":$("#phoneNumber").val(),
			"roleId":$("#roleId").val(),
			"roleName":$("#roleId  option:selected").text(),
			"provinceCode":$("#provincehId").val(),
			"provinceName":$("#provincehId  option:selected").text(),
			"cityCode":$("#cityId").val(),
			"cityName":$("#cityId  option:selected").text(),
			"branchId":$("#branchId").val(),
			"branchName":$("#branchId  option:selected").text(),
			"hallId":$("#hallId").val(),
			"hallName":$("#hallId  option:selected").text(),
			"isEffective":jQuery("#isEffective").val()
	};
	if($("#userName").val()==null||$("#userName").val().trim()==''){
		$.messager.alert(titleInfo,'请填写用户名！');
		return;
	}
	if($("#userPwd").val()==null||$("#userPwd").val().trim()==''){
		$.messager.alert(titleInfo,'请填写密码！');
		return;
	}
	if($("#userMail").val()==null||$("#userMail").val().trim()==''){
		$.messager.alert(titleInfo,'请填写邮箱！');
		return;
	}
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
    if (!pattern.test($("#userMail").val())) {  
    	$.messager.alert(titleInfo,'请输入正确的邮箱地址！');  
        return;  
    }  
	var reg = /^1\d{10}$/;
	if(!reg.test($("#phoneNumber").val())||$("#phoneNumber").val().trim()==""){
		$.messager.alert(titleInfo,'请输入正确手机号！');
		return;
	}
	if(1!=$("#roleId").val()&&($("#provincehId").val()==-1||$("#cityId").val()==-1)){
		$.messager.alert(titleInfo,'请先选择地区信息！');
		return;
	}
	if(2==$("#roleId").val()&&($("#branchId").val()==null||$("#branchId").val().trim()==''||$("#branchId").val()==-1)){
		$.messager.alert(titleInfo,'请先选择分公司！');
		return;
	}
	if(3==$("#roleId").val()&&($("#hallId").val()==null||$("#hallId").val().trim()==''||$("#hallId").val()==-1||$("#branchId").val()==-1)){
		$.messager.alert(titleInfo,'请先选择分公司和营业厅！');
		return;
	}
	if($("#branchId").val()==null&&$("#hallId").val()!=null){
		$.messager.alert(titleInfo,'请先选择分公司！');
		return;
	}
	if(userId!=null&&userId!=""){
		user['userId']=userId;
		if($("#userMail").val()==$("#oldUserMail").val()){
			delete user["userMail"];
		}
		$.post("sysUser/updateSysUserById.json",user,function(data){
			if(data.code==1){
				$('#user_detail_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'修改成功！',timeout:timeoutValue,showType:'slide'});
				$('#user_table').datagrid('load',parameter);
			}else{
				if(data.msg=="mail_same"){
					$.messager.alert(titleInfo,'您输入的邮箱已经存在！');
				}else{
					$.messager.alert(titleInfo,'修改失败！');
				}
			}
		},"json");
	}else{
		$.post("sysUser/addSysUser.json",user,function(data){
			if(data.code==1){
				$('#user_detail_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'添加成功！',timeout:timeoutValue,showType:'slide'});
				$('#user_table').datagrid('load',parameter);
			}else if(data.msg=="mail_same"){
				$.messager.alert(titleInfo,'您输入的邮箱已经存在！');
			}else{
				$.messager.alert(titleInfo,'添加失败！');
			}
		},"json");
	}
}

//初始化table
function initDataGrid(){
	parameter = {};
	var branchId = $('#q_branch').val();
	if (branchId != -1) {
		parameter.branchId = branchId;
	}
	var hallId = $('#q_hall').val();
	if (hallId != -1) {
		parameter.hallId = hallId;
	}
	var userName = $('#q_userName').val();
	if (userName != ''&&userName!=null) {
		parameter.userName = userName;
	}
	$('#user_table').datagrid({
		iconCls:'icon-save',
		nowrap: true,
		autoRowHeight: false,
		striped: true,
		toolbar: "#common_search",
		fit:true,
		fitColumns:true,
		collapsible:true,
		url:'sysUser/getList.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		idField:'userId',
		columns : [ [ {
			field : 'userName',
			title : '用户名称',
			width : 70
		}, {
			field : 'userMail',
			title : '邮箱',
			width : 70
		}, {
			field : 'phoneNumber',
			title : '电话',
			width : 70
		}, {
			field : 'userPwd',
			title : '密码',
			hidden : true
		}, {
			field : 'provinceCode',
			title : '省编码',
			hidden : true
		}, {
			field : 'provinceName',
			title : '省名称',
			hidden : true
		}, {
			field : 'cityCode',
			title : '市编码',
			hidden : true
		}, {
			field : 'cityName',
			title : '市名称',
			hidden : true
		}, {
			field : 'roleId',
			title : '用户角色',
			hidden : true
		}, {
			field : 'roleName',
			title : '用户角色',
			width : 70
		}, {
			field : 'branchId',
			title : '分公司ID',
			hidden : true
		}, {
			field : 'hallId',
			title : '营业厅ID',
			hidden : true
		}, {
			field : 'branchName',
			title : '分公司',
			width : 70,
			formatter : function(value, row, index) {
				if ('1' == row.roleId) {
					return '-';
				} else {
					return value;
				}
			}
		}, {
			field : 'hallName',
			title : '营业厅',
			width : 70,
			formatter : function(value, row, index) {
				if ('1' == row.roleId || '2' == row.roleId) {
					return '-';
				} else {
					return value;
				}
			}
		}, {
			field : 'isEffective',
			title : '状态',
			width : 70,
			formatter : function(value) {
				if (1 == value) {
					return '有效';
				} else {
					return '无效';
				}
			}
		}, {
			field : 'userId',
			title : '操作',
			width : 70,
			formatter : function(value, row, index) {
				if(1==row.isEffective){
					return '<a href="javascript:userEdit(' + value + ')">修改</a>';
				}else{
					return '<a href="javascript:userEdit(' + value + ')">修改</a>|<a href="javascript:userDel('+value+')">删除</a>';
				}
			}
		} ] ],
		pagination:true,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
}
//设置弹出框信息 
function generateDialog(rowInfo){
	
	$('#userId').val(rowInfo.userId);
	$('#userName').val(rowInfo.userName);
	$('#phoneNumber').val(rowInfo.phoneNumber);
	$('#userPwd').val(rowInfo.userPwd);
	$('#userMail').val(rowInfo.userMail);
	$('#oldUserMail').val(rowInfo.userMail);
	$('#roleId').val(rowInfo.roleId);
	$('#provincehId').val(rowInfo.provinceCode);
	initCityList(rowInfo.provinceCode);
	initBranchList(rowInfo.cityCode);
	initHallList(rowInfo.branchId);
	$('#cityId').val(rowInfo.cityCode);
	$('#branchId').val(rowInfo.branchId);
	$('#hallId').val(rowInfo.hallId);
	$('#isEffective').val(rowInfo.isEffective);
	changeView();//请勿更换本行代码位置
}

//添加用户时根据角色控制文本框的显示与隐藏
function changeView(){
	var roleId=$('#roleId').val();
	if('1'==roleId){//集团管理员
		document.getElementById('trArea').style.display = 'none';
		document.getElementById('trBranch').style.display = 'none';
		document.getElementById('trHall').style.display = 'none';
	}else if('2'==roleId){//分公司
		document.getElementById('trBranch').style.display = '';
		document.getElementById('trArea').style.display = '';
		document.getElementById('trHall').style.display = 'none';
	}else{//营业厅
		document.getElementById('trBranch').style.display = '';
		document.getElementById('trArea').style.display = '';
		document.getElementById('trHall').style.display = '';
	}
	
}

//初始化营业厅关联
function initHall(branchId){
	var hall={
			"branchId":branchId
	};
	$.post("hall/getHallList.json",hall,function(data){
		$("#q_hall").html("<option value='-1'>全部</option>");
		$.each(data,function(dataIndex,obj){
			$("#q_hall").append('<option value='+obj.id+'>'+obj.hallName+'</option>');
		});
	},"json");
}

//删除用户
function userDel(userId){
	$.messager.confirm('Warning', '您确定要删除?', function(r){
		   if (r){
				$.post("sysUser/del.json",{"userId":userId},function(data){
					if(data.code==1){
						$.messager.show({title:titleInfo,msg:'删除成功！',timeout:timeoutValue,showType:'slide'});
						$('#user_table').datagrid('load',parameter);
					}else{
						$.messager.alert(titleInfo,'删除失败！');
					}
				},"json");
		   }
	});
}

function initAuth(){
	var roleId=$('#hidRoleId').val();
	var branchId=$('#hidBranchId').val();
	var hallId=$('#hidHallId').val();
	if(3==roleId){
		$('#q_branch').val(branchId);
		document.getElementById('q_branch').disabled = 'disabled';
//		initHall(branchId);
		$('#q_hall').val(hallId);
		document.getElementById('q_hall').disabled = 'disabled';
	}else if(2==roleId){
		$('#q_branch').val(branchId);
		initHall(branchId);
		document.getElementById('q_branch').disabled = 'disabled';
	}else{
		
	}
}
