<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>直播管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/live_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/live/live.js"></script>
<script type="text/javascript" src="js/common/md5.js"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="js/load_wait.js"></script>
<style type="text/css">
 .combobox-item{border-left:1px solid #e6e6e6;border-right:1px solid #e6e6e6;border-bottom:1px solid #e6e6e6}
</style>
</head>
<body  class="easyui-layout">
<div data-options="region:'center',title:'直播管理'" class="regionCenter">
	<div id="live_toolbar">
		<div style="height: 30px;">
			<input type="button" id="openLive" class="btn btn-success" value="单个开通直播服务">
			<input type="button" id="renewLive" name="renew" class="btn btn-success" value="续费">
			<input type="button" id="refundLive" name="refundLive" class="btn btn-success" value="退订">
			<input type="button" id="query_live" name="query_live" class="btn btn-success" value="查询">
		</div>
		<div>
		<input type="hidden" id="currentUserBranchId" value="${currentUser.branchId}">
		<input type="hidden" id="currentUserHallId" value="${currentUser.hallId}">
		<input type="hidden" id="currentUserProvinceCode" value="${currentUser.provinceCode}">
		<input type="hidden" id="currentUserCityCode" value="${currentUser.cityCode}">
		<input type="hidden" id="currentUserId" value="${currentUser.userId}">
		<input type="hidden" id="currentUserRoleId" value="${currentUser.roleId}">
		<input type="hidden"  id="hiddenPath"/>
			<label>订单状态：</label>
    			<select id="status">
    				<option value="-1">全部</option>
    				<option value="1">已提交</option>
    				<option value="2">已开通</option>
    				<option value="3">开通失败</option>
    				<option value="4">已退订</option>
    			</select>
    		<label>订单类型：</label>
    			<select id="orderType">
    				<option value="-1">全部</option>
    				<option value="0">开通单</option>
    				<option value="1">续费单</option>
    			</select>
    			<label>MAC：</label> <input id="mac" type="text" maxlength="17" onkeyup="this.value=this.value.replace(/[\W]/g,'')"/> 
				<label>SN：</label><input id="sn" type="text" maxlength="18" onkeyup="this.value=this.value.replace(/[\W]/g,'')"/>
			
			</div>
			<div style="height: 40px;padding-top: 5px;">
    			<label>用  户  I D ：</label><input id="uid" type="text" />
				<label>开通人员 ：</label><input id="openname" type="text" />
				<label>开通时间：</label>	<input id="startTime" /> <label>--</label> 	<input id="stopTime" /> 
			</div>
			<c:if test="${currentUser.roleId==1}">
			<div style="height: 40px;padding-top: 5px;">
				<label>公司名称：</label> 
				<select id="branch" onchange="javascript:initHall(this.value);" >
					<option value="-1">全部</option>
					<c:forEach items="${branchList}" var="bL" varStatus="st">
						<option value="${bL.id}">${bL.branchName}</option>
					</c:forEach>
				</select> 
				<label>营  业  厅 ：</label> 
				<select id="hall">
				<option value="-1">全部</option>
					<c:forEach items="${hallList}" var="hL" varStatus="st">
						<option value="${hL.id}">${hL.hallName}</option>
					</c:forEach>
				</select> 
			</div>
		</c:if>
	</div>
	<table id="live_table"></table>
	<div id="single_live_dialog" data-options="closed:true,modal:true,title:'单个开通直播服务',iconCls:'icon-save'" style="padding:5px;width:400px;height:350px;">
		<table>
			<c:if test="${currentUser.roleId==1}">
				<tr>
					<td>分公司名称：</td>
					<td><input id = "single_branchName" name="single_branchName" type="text" /></td>
				</tr>
			</c:if>
			<c:if test="${currentUser.roleId!=1}">
				<tr>
					<td>分公司名称：</td>
					<td><input id = "single_branchName" name="single_branchName" style="color:#D3D3D3" type="text"  value="${currentUser.branchName}"  data-options="disabled:true" /></td>
				</tr>
				<tr>
					<td>营业厅名称：</td>
					<td><input id = "single_hallName" name="single_hallName" type="text"  style="color:#D3D3D3" value="${currentUser.hallName}" readonly="readonly"/></td>
				</tr>
			</c:if>
			<tr>
				<td>产品包名称：</td>
				<td>
					<input id = "single_productId" name="single_productId" class="easyui-combobox"  type="text" />
				</td>
			</tr>
			<tr>
		    	<td>结束日期：</td>
		    	<td>
		    		<input type="text" id="single_endTime" class="Wdate" onClick="WdatePicker({el:'single_endTime',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',onpicked:singleComputeTimeDifference})" />
		    	</td>
		    </tr>
		    <tr>
		    	<td>开通时长：</td>
		    	<td>
		    		<input type="text" id="single_year" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" onblur="getYear('single_')" />年
		    		<input type="text" id="single_month" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" onblur="getMonth('single_')" />月
		    		<input type="text" id="single_day" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" onblur="getDay('single_')" />天
		    	</td>
		    </tr>
		    <tr>
		    	<td>MAC：</td>
		    	<td><input type="text" id="single_mac" maxlength="17" onkeyup="this.value=this.value.replace(/[\W]/g,'')"></td>
		    </tr>
		  	<tr>
		    	<td>SN：</td>
		    	<td><input type="text" id="single_sn" maxlength="18" onkeyup="this.value=this.value.replace(/[\W]/g,'')"></td>
    		</tr>
		</table>
	</div>
	
	<div id="renew_live_dialog" data-options="closed:true,modal:true,title:'续费直播服务',iconCls:'icon-save'" style="padding:5px;width:400px;height:500px;">
		<table>
			<input type="hidden" id="renew_productId">
			<input type="hidden" id="renew_status">
			<input type="hidden" id="renew_branchId">
			<tr>
	    		<td>订单号:</td>
		    	<td><input type="text" id="renew_liveOrderId"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>
    		</tr>
    		<tr>
    			<td>用户ID:</td>
				<td><input type="text" id="renew_uid"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		   
		    </tr>
		    <tr>
		    	<td>MAC地址:</td>
				<td><input type="text" id="renew_mac"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		  
		    </tr>
		    <tr>
		    	<td>SN号:</td>
				<td><input type="text" id="renew_sn"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		   
		   	</tr>
		   	<tr>
		   		<td>所属分公司:</td>
				<td><input type="text" id="renew_branchName"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		   
		    </tr>
		    <tr>
		    	<td>产品包名称:</td>
				<td><input type="text" id="renew_productName"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		  
			</tr>
			<tr>
		    	<td>生效时间:</td>
				<td><input type="text" id="renew_openTime"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		  
			</tr>
			<tr>
		    	<td>失效时间:</td>
				<td><input type="text" id="renew_closeTime"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		    
			</tr>
<!-- 			<tr> -->
<!-- 		    	<td>结束日期：</td> -->
<!-- 		    	<td> -->
<!-- 		    		<input type="text" id="renew_endTime" class="Wdate" onClick="WdatePicker({el:'renew_endTime',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',onpicked:renewComputeTimeDifference})" /> -->
<!-- 		    	</td> -->
<!-- 		    </tr> -->
		    <tr>
		    	<td>开通时长：</td>
		    	<td>
		    		<input type="text" id="renew_year" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0"  />年
		    		<input type="text" id="renew_month" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" />月
		    		<input type="text" id="renew_day" style="width:25px" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="0" />天
		    	</td>
		    </tr>
		</table>
	</div>
	
	<div id="refund_live_dialog" data-options="closed:true,modal:true,title:'续费直播服务',iconCls:'icon-save'" style="padding:5px;width:400px;height:450px;">
		<table>
			<input type="hidden" id="refund_productId">
			<input type="hidden" id="refund_orderType">
			<input type="hidden" id="refund_status">
			<tr>
	    		<td>订单号:</td>
		    	<td><input type="text" id="refund_liveOrderId"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>
    		</tr>
    		<tr>
    			<td>用户ID:</td>
				<td><input type="text" id="refund_uid"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		   
		    </tr>
		    <tr>
		    	<td>MAC地址:</td>
				<td><input type="text" id="refund_mac"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		  
		    </tr>
		    <tr>
		    	<td>SN号:</td>
				<td><input type="text" id="refund_sn"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		   
		   	</tr>
		   	<tr>
		   		<td>所属分公司:</td>
				<td><input type="text" id="refund_branchName"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		   
		    </tr>
		    <tr>
		    	<td>产品包名称:</td>
				<td><input type="text" id="refund_productName"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		  
			</tr>
			<tr>
		    	<td>生效时间:</td>
				<td><input type="text" id="refund_openTime"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		  
			</tr>
			<tr>
		    	<td>失效时间:</td>
				<td><input type="text" id="refund_closeTime"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		    
			</tr>
			<tr>
		    	<td>订单类型:</td>
				<td><input type="text" id="refund_orderTypeName"  style="width:200px;border:1;color:#D3D3D3;" value="" readonly="readonly"></td>		    
			</tr>
		</table>
	</div>
</div>
</body>
</html>