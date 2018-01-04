var parameter = {};
var titleInfo = "提示信息";
var timeoutValue = 2000;

var args =[
            {code:'args_1',name:'激活码'},
            {code:'args_2',name:'有效期'}
          ];
var states =[{code:'1',name:'上线'},{code:'0',name:'下线'}];
$(function() {
	$('#query_smsR').click(function() {
		initSmsRecordInfo();
	});
	initSmsRecordInfo();
});


// 初始化活动信息
function initSmsRecordInfo() {
	parameter = {};
	
	parameter.branchId =$('#q_sender').val();
	$('#smsco_table').datagrid({
			iconCls : 'icon-save',
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			toolbar : '#smsco_toolbar',
			fit : true,
			fitColumns : true,
			collapsible : true,
			url : 'sms/getConfList.json',
			queryParams : parameter,
			remoteSort : false,
			singleSelect : true,
			idField : 'id',
			columns : [ [
				 			{
								field : 'text1',
								title : '内容',
								width : 80,
								editor:'text'
							},
			 			{
							field : 'args1',
							title : '变量',
							width : 15,
							editor:{
								type:'combobox',
								options:{
									valueField:'code',
									textField:'name',
									data:args,
									required:true
								}
							}
						},
			 			{
							field : 'text2',
							title : '内容',
							width : 80,
							editor:'text'
						},
							{
								field : 'args2',
								title : '变量',
								width : 15,
								editor:{
									type:'combobox',
									options:{
										valueField:'code',
										textField:'name',
										data:args,
										required:true
									}
								}

							},
							{
								field : 'text3',
								title : '内容',
								width : 80,
								editor:'text'
							},
							{
								field : 'state',
								title : '状态',
								width : 10,
								editor:{
									type:'combobox',
									options:{
										valueField:'code',
										textField:'name',
										data:states,
										required:true
									}
								}

							},
					{field:'id',title:'Action',width:20,align:'center',
		                formatter:function(value,row,index){
		                    if (row.editing){
		                        var s = '<a href="javascript:void(0)" onclick="saverow(this)">Save</a> ';
		                        var c = '<a href="javascript:void(0)" onclick="cancelrow(this)">Cancel</a>';
		                        return s+c;
		                    } else {
		                        var e = '<a href="javascript:void(0)" onclick="editrow(this)">Edit</a> ';
		                        var d = '<a href="javascript:void(0)" onclick="deleterow(this)">Delete</a>';
		                        return e;
		                    }
		                }
		            }
					] ],
//					onEndEdit:function(index,row){
//			            var ed = $(this).datagrid('getEditor', {
//			                index: index,
//			                field: 'phone'
//			            });
//			            row.name = $(ed.target).combobox('getText');
//			        },
			        onBeforeEdit:function(index,row){
			            row.editing = true;
			            $(this).datagrid('refreshRow', index);
			        },
			        onAfterEdit:function(index,row){
			            row.editing = false;
			            $(this).datagrid('refreshRow', index);
			        },
			        onCancelEdit:function(index,row){
			            row.editing = false;
			            $(this).datagrid('refreshRow', index);
			        },
			pagination : true,
			rownumbers : true,
			onClickRow : function(rowIndex) {
			}
		});
}

function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target){
    $('#smsco_table').datagrid('beginEdit', getRowIndex(target));
}
function deleterow(target){
    $.messager.confirm('Confirm','Are you sure?',function(r){
        if (r){
            $('#smsco_table').datagrid('deleteRow', getRowIndex(target));
        }
    });
}
function saverow(target){
    $('#smsco_table').datagrid('endEdit', getRowIndex(target));
    var rowInfo = $("#smsco_table").datagrid('getChanges');
    var canedit =$("#canedit").val();
   // console.log(rowInfo);
    if(canedit!=1){
    	$.messager.alert("温馨提示", '您没有修改权限！');
    	return false;
    }
    if(rowInfo.length>0){
    	var id =rowInfo[0].id;
    	var state =rowInfo[0].state;
    	var text1 =rowInfo[0].text1;
    	var text2 =rowInfo[0].text2;
    	var text3 =rowInfo[0].text3;
    	var args1 =rowInfo[0].args1;
    	var args2 =rowInfo[0].args2;
    	var conf ={
    		'id' : id,
    		'state' : state,
    		'text1' : text1,
    		'text2' : text2,
    		'text3' : text3,
    		'args1' : args1,
    		'args2' : args2
    	};
    	$.post('sms/daoConf.json', conf, function(data) {
			if (data.code == 1) {
				$.messager.show({
					title : titleInfo,
					msg : '修改成功！',
					timeout : timeoutValue,
					showType : 'slide'
				});
			} else {
				$.messager.alert(titleInfo, '修改失败！');
			}
		}, "json");
    }
}
function cancelrow(target){
    $('#smsco_table').datagrid('cancelEdit', getRowIndex(target));
}


