var parameter = {};
var dataList={};
var updateFormValid;
var validateResult;
var timeoutValue = 2000;
var treeData;
var my_treeData;
var my_select_treeData;
$(function() {
	//设置该页面ajax默认同步属性
	$.ajaxSetup({
        async: false
    });
    $('#add_zoneTree_select_dialog').dialog({
        buttons:[{
            text:'取消',
            handler:function(){
                $('#add_zoneTree_select_dialog').dialog('close');
            }
        }]
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

    $('#add_zoneTree_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                submit_add_zoneTree();
            }
        },{
            text:'取消',
            handler:function(){
                $('#add_zoneTree_dialog').dialog('close');
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
	initCityList();
	initHallList();
	initBranchList();
	initRoleList();
//	initProvinceList();
//	initZoneList();
	initQuery();
//	initAddBranch();
	initAddHall();
	initZoneTree();
	initZoneTreeData();
});


//添加用户
function initAddUser(){
	$('#user_add').click(function(){
		$('#user_detail_form')[0].reset();
		changeView();//请勿更换本行代码位置
		$('#userId').val('');
		$("#zoneTreeIds").val('');
		getZoneTree('');
		$('#spAuthSelect').css('display','none');
		$('#user_detail_dialog').dialog('open');
	});
}

function initZoneTree(){
	  $('#addZoneTree').click(function(){
		  $('#add_zoneTree_dialog').dialog('open');
	  });
}

function initQuery(){
	$('#user_query').click(function(){
		initDataGrid();
	});
}

//初始化角色列表
//暂时写死
function initRoleList(){
	var roleId = $("#hidRoleId").val();
	var zoneId = $("#hidZoneId").val();
	var zoneName = $("#hidZoneName").val();
	var provinceId = $("#hidProvinceId").val();
	var provinceName = $("#hidProvinceName").val();
	var cityId = $("#hidCityId").val();
	var cityName = $("#hidCityName").val();
	var branchId = $("#hidBranchId").val();
	var branchName = $("#hidBranchName").val();
	if('1'==roleId){
		$("#roleId").html("");
		$("#roleId").append('<option value='+1+'>集团管理员</option>');
		$("#roleId").append('<option value='+4+'>战区管理员</option>');
		$("#roleId").append('<option value='+2+'>分公司人员</option>');
		$("#roleId").append('<option value='+3+'>营业厅人员</option>');
		$("#roleId").append('<option value='+5+'>本地生活管理员</option>');
		$("#roleId").append('<option value='+6+'>百事通MAC/SN</option>');
	}
	if('4'==roleId){
		$("#roleId").html("");
		$("#roleId").append('<option value='+4+'>战区管理员</option>');
		$("#roleId").append('<option value='+2+'>分公司人员</option>');
		$("#roleId").append('<option value='+3+'>营业厅人员</option>');
		$("#zoneId").html("");
		$("#zoneId").append('<option value='+zoneId+'>'+zoneName+'</option>');
		initCityList(zoneId);
	}
	if('2'==roleId){
		$("#roleId").html("");
		$("#roleId").append('<option value='+2+'>分公司人员</option>');
		$("#roleId").append('<option value='+3+'>营业厅人员</option>');
		$("#zoneId").html("");
		$("#zoneId").append('<option value='+zoneId+'>'+zoneName+'</option>');
		$("#cityId").html("");
		$("#cityId").append('<option value='+cityId+'&'+provinceId+'&'+provinceName+'>'+cityName+'</option>');
		$("#branchId").html("");
		$("#branchId").append('<option value='+branchId+'>'+branchName+'</option>');
//		document.getElementById('#addBranch').style.display = 'none';
		initHallList(branchId);
	}
	if('3'==roleId){
		$("#roleId").html("");
	}
//	$.post("sysRole/getSysRoleList.json",{page:1,rows:50},function(data){
//		$("#roleId").html("--");
//		$.each(data.rows,function(dataIndex,role){
//			if(role.isEffective!=0){
//				$("#roleId").append('<option value='+role.roleId+'>'+role.roleName+'</option>');
//			}
//		});
//	},"json");
}

//初始化省列表
//function initProvinceList(){
//	$.post("areas/getProvinceList.json",{page:1,rows:50},function(data){
//		$("#provincehId").html("<option value='-1' >-</option>");
//		$.each(data,function(dataIndex,ProvinceVo){
//			$("#provincehId").append('<option value='+ProvinceVo.provinceCode+'>'+ProvinceVo.provinceName+'</option>');
//		});
//	},"json");
//}


//初始化战区信息
//function initZoneList(){
//	$.post("zone/getAllZone.json",{page:1,rows:50},function(data){
//		$("#zoneId").html("<option value='-1' >-</option>");
//		$.each(data,function(dataIndex,Zone){
//			$("#zoneId").append('<option value='+Zone.id+'>'+Zone.zoneName+'</option>');
//		});
//	},"json");
//}

//初始化市列表
function initCityList(zoneId){
	if(zoneId==null||zoneId==""){
		$("#cityId").html("<option value='-1' >-</option>");
	}else{
//	$.post("areas/getCityList.json",{"provinceCode":provinceCode},function(data){
		$.post("zoneCity/getCityByZone.json",{"zoneId":zoneId},function(data){
			$("#cityId").html("<option value='-1' >-</option>");
//			initBranchList();
//			initHallList();
			$.each(data,function(dataIndex,ZoneCity){
				$("#cityId").append('<option value='+ZoneCity.cityId+'&'+ZoneCity.provinceId+'&'+ZoneCity.provinceName+'>'+ZoneCity.cityName+'</option>');
			});
		},"json");
	}
}

//初始化分公司名单
function initBranchList(cityVoCode){
	var cityId = cutStr(cityVoCode)[0];
	$.post("branch/getBranchList.json",{"cityCode":cityId},function(data){
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
}


//修改
function userEdit(userId){
	$('#user_table').datagrid('selectRecord',userId);
	var rowInfo =  $('#user_table').datagrid('getSelected');
	if(rowInfo){
		//设置弹出框信息
		generateDialog(rowInfo);
		getMyTreeData(rowInfo.userId,"update");
		$('#spAuthSelect').css('display','');
		//赋值给select
		$.post('sysUser/getSpecialListByUserId.json',{"userId":userId},function(data){
			var useAuths =eval(data.data.msg);
//	        console.log(useAuths);
	        var selectValue=new Array();
			for(var i=0;i<useAuths.length;i++){
				var authCode =useAuths[i].authCode;
//				console.log(authCode);
				selectValue[i]=authCode;
				$('select[multiple] > option:eq('+authCode+')').attr('selected',true);
				var selects =$("#authMap");
				for(var cc=0;cc<selects.length;cc++){
					selects[cc].selected=true;
				}
			}
//			console.log(selectValue);
			$('#authMap').val(selectValue);
	    },"json");
//		$('#authMap').val(['isGod']);
		$('#user_detail_dialog').dialog('open');
	}
}

function getMyTreeData(userId,type){
    $.post('zoneTree/getZoneByUserId.json',{"userId":userId},function(data){
        my_treeData = data.rows;
        if("update" == type){
            getZoneTree("update");
        }
    },"json");
}

//保存
function submit_model_window(){
	var userId = $("#userId").val();
	var strs = cutStr($("#cityId").val());
	var cityId=-1;
	var provinceId=-1;
	var provinceName='-';
	if(strs!=null&&strs!=""){
		cityId=strs[0];
		provinceId=strs[1];
		provinceName=strs[2];
	}
	var authMap =$("#authMap").val();
	var authMapstr ="";
	if(authMap!=null){
		authMapstr =authMap.join(',');
	}
//	console.log(authMapstr);
	var user = {
			"userName":$("#userName").val(),
			"userPwd":$("#userPwd").val(),
			"userMail":$("#userMail").val(),
			"phoneNumber":$("#phoneNumber").val(),
			"roleId":$("#roleId").val(),
			"roleName":$("#roleId  option:selected").text(),
			"isEffective":jQuery("#isEffective").val(),
			"provinceCode":provinceId,
			"provinceName":provinceName,
			"cityCode":cityId,
			"authMap":authMapstr,
			"cityName":$("#cityId  option:selected").text()
	};
	//集团管理员，默认北京地区（满足直播开通时需要地区信息的需求）
	if($("#roleId").val() == 1){ 
		user.provinceCode='10';
		user.provinceName='北京市';
		user.cityCode='1010';
		user.cityName='北京';
		user.branchId = '-1';
		user.branchName = '-';
		user.hallId = '-1';
		user.hallName = '-';
	}
	if($("#roleId").val() == 4){ //战区管理员
		user.branchId = '-1';
		user.branchName = '-';
		user.hallId = '-1';
		user.hallName = '-';
	}
	if($("#roleId").val() == 2){ //分公司人员
		user.branchId = $("#branchId").val();
		user.branchName = $("#branchId  option:selected").text();
		user.hallId = '-1';
		user.hallName = '-';
	}
	if($("#roleId").val() == 3){ //营业厅人员
		user.branchId = $("#branchId").val();
		user.branchName = $("#branchId  option:selected").text();
		user.hallId = $("#hallId").val();
		user.hallName = $("#hallId  option:selected").text();
	}
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
	if($("#roleId").val() == 5){
        user.zoneTreeIds = $("#zoneTreeIds").val();
        if($("#zoneTreeIds").val()==null||$("#zoneTreeIds").val().trim()==''){
            $.messager.alert(titleInfo,'所属区域不能为空！');
            return;
        }
        user.provinceCode = '0';
        user.provinceName = '-';
        user.cityCode = '0';
        user.cityName = '-';
        user.branchId = '-1';
        user.branchName = '-';
        user.hallId = '-1';
        user.hallName = '-';
	}else if($("#roleId").val() == 6){
        user.provinceCode = '0';
        user.provinceName = '-';
        user.cityCode = '0';
        user.cityName = '-';
        user.branchId = '-1';
        user.branchName = '-';
        user.hallId = '-1';
        user.hallName = '-';
	}else{
		if(1!=$("#roleId").val()&&($("#zoneId").val()==""||$("#zoneId").val()==-1)){
			$.messager.alert(titleInfo,'战区信息不能为空！');
			return;
		}
		if(user.provinceCode==-1||user.cityCode==-1){
			$.messager.alert(titleInfo,'请先选择地区信息！');
			return;
		}
		if(2==$("#roleId").val()&&($("#branchId").val()==null||$("#branchId").val().trim()==''||$("#branchId").val()==-1||$("#zoneId").val()=="")){
			$.messager.alert(titleInfo,'请先选择战区和分公司！');
			return;
		}
		if(3==$("#roleId").val()&&($("#hallId").val()==null||$("#hallId").val().trim()==''||$("#hallId").val()==-1||$("#branchId").val()==-1||$("#zoneId").val()=="")){
			$.messager.alert(titleInfo,'请先选择战区、分公司和营业厅！');
			return;
		}
		if($("#branchId").val()==null&&$("#hallId").val()!=null){
			$.messager.alert(titleInfo,'请先选择分公司！');
			return;
		}
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
//	parameter.zoneId = $('#hidZoneId').val();
	var zoneId = $('#q_zone').val();
	var branchId = $('#q_branch').val();
	var hallId = $('#q_hall').val();
	var roleId = $('#hidRoleId').val();
	if (1 == roleId) {
		if (zoneId != -1) {
			parameter.zoneId = zoneId;
		}
		if (branchId != -1) {
			parameter.branchId = branchId;
		}
		if (hallId != -1) {
			parameter.hallId = hallId;
		}
	}else if (2 == roleId) {
		parameter.zoneId = $('#hidZoneId').val();
		parameter.branchId = $('#hidBranchId').val();
		if (hallId != -1) {
			parameter.hallId = hallId;
		}
	} else if (3 == roleId){
		parameter.zoneId = $('#hidZoneId').val();
		parameter.branchId = $('#hidBranchId').val();
		parameter.hallId = $('#hidHallId').val();
	}else if(4 == roleId){
		parameter.zoneId = $('#hidZoneId').val();
		if (branchId != -1) {
			parameter.branchId = branchId;
		}
		if (hallId != -1) {
			parameter.hallId = hallId;
		}
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
			width : 30
		}, {
			field : 'userMail',
			title : '邮箱',
			width : 50
		}, {
			field : 'phoneNumber',
			title : '电话',
			width : 30
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
			width : 30
		}, {
			field : 'branchId',
			title : '分公司ID',
			hidden : true
		}, {
			field : 'hallId',
			title : '营业厅ID',
			hidden : true
		},
		 {
			field : 'zoneId',
			title : '战区ID',
			hidden:true,
			formatter : function(value, row, index) {
					return value;
			}
		},
		 {
			field : 'zoneName',
			title : '战区',
			width : 30,
			formatter : function(value, row, index) {
					return value;
			}
		},{
			field : 'branchName',
			title : '分公司',
			width : 30,
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
			width : 30,
			formatter : function(value, row, index) {
				if ('1' == row.roleId || '2' == row.roleId) {
					return '-';
				} else {
					return value;
				}
			}
		},{
			field : 'createdTime',
			title : '更新时间',
			width : 60,
			formatter : function(value, row, index) {
				return getLocalTime(value);
//				return value;
			}
		}, {
			field : 'isEffective',
			title : '状态',
			width : 30,
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
			width : 40,
			formatter : function(value, row, index) {
				if(5!=row.roleId){
					if(1==row.isEffective){
						return '<a href="javascript:userEdit(' + value + ')">修改</a>';
					}else{
						return '<a href="javascript:userEdit(' + value + ')">修改</a>|<a href="javascript:userDel('+value+')">删除</a>';
					}
				}else if(1==row.isEffective){
						return '<a href="javascript:userEdit(' + value + ')">修改</a>|<a href="javascript:getZoneTreeSelect('+value+')">区域详情</a>';
				}else{
						return '<a href="javascript:userEdit(' + value + ')">修改</a>|<a href="javascript:getZoneTreeSelect('+value+')">区域详情</a>|<a href="javascript:userDel('+value+')">删除</a>';
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
	$('#provincehId').val(rowInfo.provinceCode);
	$('#zoneId').val(rowInfo.zoneId);
	initCityList(rowInfo.zoneId);
	initBranchList(rowInfo.cityCode);
	initHallList(rowInfo.branchId);
	$('#cityId').val(rowInfo.cityCode+"&"+rowInfo.provinceCode+"&"+rowInfo.provinceName);
	$('#branchId').val(rowInfo.branchId);
	$('#hallId').val(rowInfo.hallId);
	$('#isEffective').val(rowInfo.isEffective);
	initRoleList();
	$('#roleId').val(rowInfo.roleId);
	changeView();
}

//添加用户时根据角色控制文本框的显示与隐藏
function changeView(){
	var roleId=$('#roleId').val();
	if('1'==roleId){//集团管理员
		document.getElementById('trZone').style.display = 'none';
		document.getElementById('trBranch').style.display = 'none';
		document.getElementById('trArea').style.display = 'none';
		document.getElementById('trHall').style.display = 'none';
		document.getElementById('trZoneTree').style.display = 'none';
	}else if('2'==roleId){//分公司
		document.getElementById('trZone').style.display = '';
		document.getElementById('trArea').style.display = '';
		document.getElementById('trBranch').style.display = '';
		document.getElementById('trHall').style.display = 'none';
		document.getElementById('trZoneTree').style.display = 'none';
	}else if('3'==roleId){//营业厅
		document.getElementById('trZone').style.display = '';
		document.getElementById('trArea').style.display = '';
		document.getElementById('trBranch').style.display = '';
		document.getElementById('trHall').style.display = '';
		document.getElementById('trZoneTree').style.display = 'none';
	}else if('4'==roleId){ //战区
		document.getElementById('trZone').style.display = '';
		document.getElementById('trArea').style.display = '';
		document.getElementById('trBranch').style.display = 'none';
		document.getElementById('trHall').style.display = 'none';
		document.getElementById('trZoneTree').style.display = 'none';
	}else if('5'==roleId){//本地生活管理员
		document.getElementById('trZone').style.display = 'none';
		document.getElementById('trArea').style.display = 'none';
		document.getElementById('trBranch').style.display = 'none';
		document.getElementById('trHall').style.display = 'none';
		document.getElementById('trZoneTree').style.display = '';
	}else if('6'==roleId){//上海百事通
		document.getElementById('trZone').style.display = 'none';
		document.getElementById('trArea').style.display = 'none';
		document.getElementById('trBranch').style.display = 'none';
		document.getElementById('trHall').style.display = 'none';
		document.getElementById('trZoneTree').style.display = 'none';
	}
}

//根据战区初始化分公司信息
function initQueryBranch(zoneId) {
	if (zoneId == "-1" || zoneId == "") {
		$("#q_branch").html("<option value='-1'>全部</option>");
	} else {
		$.post("zoneCity/getBranchByZone.json", { "zoneId" : zoneId }, function(data) {
			$("#q_branch").html("<option value='-1'>全部</option>");
			$.each(data, function(dataIndex, obj) {
				$("#q_branch").append( '<option value=' + obj.id + '>' + obj.branchName + '</option>');
			});
		}, "json");
	}
}

//初始化营业厅关联
function initQueryHall(branchId){
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
	var zoneId=$('#hidZoneId').val();
	var roleId=$('#hidRoleId').val();
	var branchId=$('#hidBranchId').val();
	var hallId=$('#hidHallId').val();
	if(3==roleId){
		$('#q_zone').val(zoneId);
		$('#q_branch').val(branchId);
		$('#q_hall').val(hallId);
		document.getElementById('q_zone').disabled = 'disabled';
		document.getElementById('q_branch').disabled = 'disabled';
		document.getElementById('q_hall').disabled = 'disabled';
	}else if(2==roleId){
		$('#q_zone').val(zoneId);
		$('#q_branch').val(branchId);
		initQueryHall(branchId);
		document.getElementById('q_zone').disabled = 'disabled';
		document.getElementById('q_branch').disabled = 'disabled';
	}else if(4==roleId){
		$('#q_zone').val(zoneId);
		initQueryBranch(zoneId);
		document.getElementById('q_zone').disabled = 'disabled';
	}
}
function initAddBranch(){
	$('#addBranch').click(function(){
//		var cityName = $("#cityId  option:selected").text();
//		if(cityName == null  || cityName == ""){
//			$.messager.alert(titleInfo,"请选择城市");
//			return;
//		}
		var strs = cutStr($("#cityId").val());
		if($.trim(strs[2])==null||$.trim(strs[2])==""){
			$.messager.alert(titleInfo,"请先选择城市");
			return;
		}
		//初始化弹出框
		$('#add_branch_dialog').dialog({
			buttons:[{
				text:'确 定',
				handler:function(){
					submit_add_branch();
				}
			},{
				text:'取消',
				handler:function(){
					$('#add_branch_dialog').dialog('close');
				}
			}]
		});
		$("#newBranchName").val('');
		$('#add_branch_dialog').dialog('open');
	});
}
function submit_add_branch(){
	var branchName=$("#newBranchName").val();
	var strs = cutStr($("#cityId").val());
	if($.trim(strs[2])==null||$.trim(strs[2])==""){
		$.messager.alert(titleInfo,"请先选择城市");
		return;
	}
	if($.trim(branchName)==null||$.trim(branchName)==""){
		$.messager.alert(titleInfo,"分公司名称不能为空");
		return;
	}
	var branch = {
 			"branchName":branchName,
 			"provinceCode":strs[1],
			"provinceName":strs[2],
			"cityCode":strs[0],
			"cityName":$("#cityId  option:selected").text()
	};
	$.post("branch/addBranch.json",branch,function(data){
		if(data.code==000){
			$.messager.show({title:titleInfo,msg:data.desc,timeout:timeoutValue,showType:'slide'});
			$('#add_branch_dialog').dialog('close');
			initBranchList(branch.cityCode);
		}else{
			$.messager.alert(titleInfo,data.desc);
		}
	},"json");
}

function initAddHall(){
	$('#addHall').click(function(){
//		var provincehId = $('#provincehId').val();
		var cityName = $("#cityId  option:selected").text();
		var branchId = $('#branchId').val();
//		if(provincehId == null  || provincehId == "" || provincehId <= 0){
//			$.messager.alert(titleInfo,"请选择省份");
//			return;
//		}
		if(cityName == null  || cityName == ""){
			$.messager.alert(titleInfo,"请选择城市");
			return;
		}
		if(branchId == null  || branchId == "" || branchId <= 0){
			$.messager.alert(titleInfo,"请选择分公司");
			return;
		}
		//初始化弹出框
		$('#add_hall_dialog').dialog({
			buttons:[{
				text:'确 定',
				handler:function(){
					submit_add_hall();
				}
			},{
				text:'取消',
				handler:function(){
					$('#add_hall_dialog').dialog('close');
				}
			}]
		});
		$("#newHallName").val('');
		$('#add_hall_dialog').dialog('open');
	});
}
function submit_add_hall(){
	var hallName=$("#newHallName").val();
	var branchId=$("#branchId").val();
	if($.trim(branchId)==null||$.trim(branchId)==""){
		$.messager.alert(titleInfo,"请先选择分公司");
		return;
	}
	if($.trim(hallName)==null||$.trim(hallName)==""){
		$.messager.alert(titleInfo,"营业厅名称不能为空");
		return;
	}
	var hall = {
 			"hallName":hallName,
 			"branchId":branchId
	};
	$.post("hall/addHall.json",hall,function(data){
		if(data.code==000){
			$.messager.show({title:titleInfo,msg:data.desc,timeout:timeoutValue,showType:'slide'});
			$('#add_hall_dialog').dialog('close');
			initHallList(hall.branchId);
		}else{
			$.messager.alert(titleInfo,data.desc);
		}
	},"json");
}



//分割字符
function cutStr(str){
	if(str==null||str==""){
		return "";
	}
	var strs= new Array(); //定义一数组
	strs=str.split("&"); //字符分割 
	return strs;
}

function getLocalTime(nS) {     
    return new Date(parseInt(nS)).toLocaleString().substr(0,20);      
 } 

//区域树
function submit_add_zoneTree(){
    $("#zoneTreeIds").val("");
    var zTree=$.fn.zTree.getZTreeObj("treeDemo");
    var tree_nodes=zTree.getCheckedNodes(true);
    if(tree_nodes.length==0){
        $.messager.alert(titleInfo,'请为用户选择所属区域!');
        return;
    }
    var zoneTreeId = "";
    for(var tree_nodes_index=0;tree_nodes_index<tree_nodes.length;tree_nodes_index++){
        zoneTreeId += tree_nodes[tree_nodes_index].id+",";
    }

    var split = zoneTreeId.split(",");
	var count = 0;
	var total = 0;
	for(var i = 0 ;i < split.length;i++){
		var zone = split[i];
		if("" != zone){
            if(zone.length == 2){
                count = count + 1;
                total = total + 1;
            }else {
                total = total + 1;
            }
            if(count > 1 || total > 3){
                $.messager.alert(titleInfo,'只能选择一个省下的一个城市！！！');
                return;
            }
        }
	}

    $("#zoneTreeIds").val(zoneTreeId);
    $('#add_zoneTree_dialog').dialog('close');
}

function initZoneTreeData(){
    $.post('zoneTree/getAllZoneTree.json',{"isEffective":1},function(data){
        treeData = data.rows;
    },"json");
}

var zNodes =[];
var treeObject;

function getZoneTree(operate){
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    $.each(treeData,function(zoneIndex,zone){
        var flag = false;
        if(operate=="update"){
            for(var i=0;i<my_treeData.length;i++){
                if(my_treeData[i].id==zone.id){
                    flag=true;
                    break;
                }
            }
        }
        if(flag){
            zNodes[zoneIndex]={id:zone.id,pId:zone.fid,name:zone.str,checked:true,open:true};
        }else{
            zNodes[zoneIndex]={id:zone.id,pId:zone.fid,name:zone.str,open:true};
        }
    });
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    //treeObject = setCheck();
}

function getZoneTreeSelcet(){
    var zNodes_select =[];
    var setting = {
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    $.each(my_select_treeData,function(zoneIndex,zone){
        zNodes_select[zoneIndex]={id:zone.id,pId:zone.fid,name:zone.str,checked:true,open:true};
    });
    $.fn.zTree.init($("#treeDemo_select"), setting, zNodes_select);
}

function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        py = $("#py").attr("checked")? "p":"",
        sy = $("#sy").attr("checked")? "s":"",
        pn = $("#pn").attr("checked")? "p":"",
        sn = $("#sn").attr("checked")? "s":"",
        type = { "Y":py + sy, "N":pn + sn};
    zTree.setting.check.chkboxType = type;
    var str='setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };';
    var code = $("#code");
    code.empty();
    code.append("<li>"+str+"</li>");
}

function getZoneTreeSelect(userId){
    $.post('zoneTree/getZoneByUserId.json',{"userId":userId},function(data){
        my_select_treeData = data.rows;
    },"json");
    getZoneTreeSelcet();
    $("#add_zoneTree_select_dialog").dialog("open");
}