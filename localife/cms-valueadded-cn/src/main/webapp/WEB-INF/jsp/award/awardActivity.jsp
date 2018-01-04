<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/main.css" />
<link rel="stylesheet" href="css/detail_new.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/award/award_activity.js?v=2"></script>
<script type="text/javascript" src="js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="js/laydate/laydate.js"></script>
<style>
.tableC3 th {
	font-weight: normal;
	text-align: right;
	line-height: 35px;
	margin-bottom: 2px;
}
.required{
	color:red;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'活动管理'" class="regionCenter">
		<div id="award_toolbar">
			<input type="button" id="add_award" class="btn btn-success"
				value="添加活动"> <label>活动标题</label><input id="searchTitle"
				type="text" /> <input type="button" id="query_activity"
				class="btn btn-success" value="查询">
		</div>
		<table id="award_activity_table"></table>

		<div id="award_activity_dialog"
			data-options="closed:true,modal:true,title:'活动管理',iconCls:'icon-save'"
			style="padding: 5px; width: 570px; height: 440px;">
			<div>
				<span style="font-size: 24px;">添加活动</span>
			</div>
			<div id="tabOne" title="基础设置" style="height: 620px;">
				<input id="id" value="" type="hidden" />
				<table>
					<tr>
						<td><label><span class="required">*</span>活动标题：</label></td>
						<td><input type="text" id="title" /></td>
					</tr>
					<tr>
						<td><label><span class="required">*</span>活动类型：</label></td>
						<td><select id="type">
								<option value="0">请选择活动类型</option>
								<option value="1">砸金蛋</option>
								<option value="2">老虎机</option>
								<option value="3">大转盘</option>
						</select></td>
					</tr>
					<tr>
						<td><label>活动logo：</label></td>
						<td><img style="height: 80px; width: 160px" src=""
							id="logoUrl" /><input type="button" id="uploadLogoUrl"
							value="选择封面" /><label>（尺寸：280 X 160  ） 小于4M</label></td>
					</tr>
					<tr>
						<td><label>活动大背景图：</label></td>
						<td><img style="height: 80px; width: 160px" src="" id="bgUrl" /><input
							type="button" id="uploadBgUrl" value="选择封面" /><label>（尺寸：1920 X 1080  ） 小于4M</label></td>
					</tr>
					<tr>
						<td><label>活动进行时封面：</label></td>
						<td><img style="height: 80px; width: 160px" src=""
							id="playingBgUrl" /><input type="button" id="uploadPlayingBgUrl"
							value="选择封面" /><label>（尺寸：1920 X 1080  ） 小于4M</label></td>
					</tr>
					<!-- 
					<tr>
						<td><label>活动结束主题：</label></td>
						<td><input type="text" id="endTitle" /></td>
					</tr>
					 -->
					<input type="hidden" id="endTitle" />
					<tr>
						<td><label>活动详情页封面：</label></td>
						<td><img style="height: 80px; width: 160px" src=""
							id="infoBgUrl" /><input type="button" id="uploadInfoBgUrl"
							value="选择封面" /><label>（尺寸：1920 X 1080  ） 小于4M</label></td>
					</tr>
					<!-- 
					<tr>
						<td><label>活动结束说明：</label></td>
						<td><textarea id="endText"></textarea></td>
					</tr>
					 -->
					<input type="hidden" id="endText" />
					<tr>
						<td><label><span class="required">*</span>活动时间：</label></td>
						<td><input class="easyui-datetimebox" name="beginTime"
							id="beginTime" data-options="required:false,showSeconds:true"
							value="" style="width: 150px">至<input
							class="easyui-datetimebox" name="endTime" id="endTime"
							data-options="required:false,showSeconds:true" value=""
							style="width: 150px"></td>
					</tr>
					<tr>
						<td><label>活动说明：</label></td>
						<td><textarea id="activityDesc"></textarea></td>
					</tr>
					<tr>
						<td><label><span class="required">*</span>中奖概率：</label></td>
						<td><input type="text" id="awardWinRatio" /></td>
					</tr>
					<tr>
						<td><label>是否可以重复抽奖：</label></td>
						<td><label><input type="radio" value="1"
								name="awardWinAgain"checked="true" />是</label> <label><input type="radio"
								value="2" name="awardWinAgain" />否</label></td>
					</tr>
					<tr>
						<td><label><span class="required">*</span>适用人群：</label></td>
						<td><label><input type="radio" value="1"
								name="awardPlayerType" onclick="showPayInfo(1)" />全部用户</label> <label><input
								type="radio" value="2" name="awardPlayerType"
								onclick="showPayInfo(2)" />VIP用户</label> <label><input
								type="radio" value="3" name="awardPlayerType"
								onclick="showPayInfo(3)" />消费用户</label></td>
					</tr>
					<tr class="showPlayerType" style="display: none;">
						<td></td>
						<td><label>消费时间段</label><input class="easyui-datetimebox"
							name="payBeginTime" id="payBeginTime"
							data-options="required:false,showSeconds:true" value=""
							style="width: 150px">至<input class="easyui-datetimebox"
							name="payEndTime" id="payEndTime"
							data-options="required:false,showSeconds:true" value=""
							style="width: 150px"></td>
					</tr>
					<tr class="showPlayerType" style="display: none;">
						<td></td>
						<td><label>消费金额：>=</label><input type="text" id="paySum">元</td>
					</tr>
					<tr>
						<td><span class="required">*</span>活动备注</td>
						<td><input id="awardRemark" type="text" value="" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" value="下一步"
							onclick="secondTab()" /></td>
					</tr>
				</table>
			</div>
			<div id="tabTwo" title="活动设置" style="display: none">
				<table>
					<tr>
						<td><label><span class="required">*</span>绑定手机号方式：</label></td>
						<td><label><input type="radio" value="1"
								name="phoneBindType" />不绑定</label> <label><input type="radio"
								value="2" name="phoneBindType" />抽奖前绑定</label></td>
					</tr>
					<tr>
						<td><label><span class="required">*</span>每日抽取次数：</label></td>
						<td><input type="text" id="playLimitDay"></td>
					</tr>
					<tr>
						<td><label>重复抽奖（日抽取次数用完）提示：</label></td>
						<td><input type="text" id="playPromptDay"></td>
					</tr>
					<tr>
						<td><label><span class="required">*</span>每人最多允许抽取总次数：</label></td>
						<td><input type="text" id="playLimitTotal"></td>
					</tr>
					<tr>
						<td><label>重复抽奖（总抽取次数用完）提示：</label></td>
						<td><input type="text" id="playPromptTotal"></td>
					</tr>
					<tr>
						<td><label>中奖提示：</label></td>
						<td><input type="text" id="playPromptWin"></td>
					</tr>
					<tr>
						<td><label>未中奖提示：</label></td>
						<td><input type="text" id="playPromptLost"></td>
					</tr>
					<tr>
						<td><input type="button" value="后退" onclick="firstTab()" /></td>
						<td><input type="button" value="下一步" onclick="thirdTab()" /></td>
					</tr>
				</table>
			</div>
			<div id="tabThree" title="奖项设置" style="display: none">
				<table id="award1" class="award">
					<tr>
						<td><label>奖品1</label></td>
						<td><input type="hidden" id="id1" value=""></td>
					</tr>
					<tr>
						<td><label>奖品类别：</label></td>
						<td><input type="text" id="awardType1" value="" /></td>
					</tr>
					<tr>
						<td><label>奖品名称：</label></td>
						<td><input type="text" id="awardName1" value="" /></td>
					</tr>
					<tr>
						<td><label>活动奖品图片：</label></td>
						<td><img src="" id="awardPicUrl1"
							style="height: 30px; width: 60px;"><input type="button"
							value="选择图片" id="uploadAwardPic1"><label>（尺寸：700 X 700  ） 小于4M</label></td>
					</tr>
					<tr>
						<td><label>奖品icon：</label></td>
						<td><img src="" id="awardIconUrl1"
							style="height: 30px; width: 60px;"><input type="button"
							value="选择图片" id="uploadAwardIcon1"><label>（尺寸：134 x 134  ） 小于4M</label></td>
					</tr>
					<tr>
						<td><label>奖品说明：</label></td>
						<td><textarea id="awardDesc1"></textarea></td>
					</tr>
					<tr>
						<td><label>奖品有效时间：</label></td>
						<td>
						<!-- 
						<input class="laydate-icon" id="availableBeginTime1" value="" style="width: 150px" onclick="laydate()">至
						<input class="laydate-icon" id="availableEndTime1"  value="" style="width: 150px" onclick="laydate()">
						-->
						<input class="easyui-datebox" id="availableBeginTime1" data-options="required:false" value="" style="width: 150px">至
						<input class="easyui-datebox" id="availableEndTime1" data-options="required:false" value="" style="width: 150px">
						</td>
					</tr>
					<tr>
						<td><label>奖品数量：</label></td>
						<td><input type="text" id="awardAmount1" value="" /></td>
					</tr>
					<tr>
						<td><label>每天中奖上线：</label></td>
						<td><input type="text" id="winLimitDay1" value="" /></td>
					</tr>
					<tr>
						<td><label><span class="required">*</span>中奖码生成：</label></td>
						<td><select id="awardCodeType1"><option value="1">自动生成</option>
								<option value="2">批量导入</option></select></td>
					</tr>
					<tr>
						<td><label>奖品属性：</label></td>
						<td><select id="awardProperty1" onchange="selectProperty(1)"><option
									value="1">实物</option>
								<option value="2">电子兑换券</option>
								<option value="3">VIP时长</option>
								<option value="4">电影票</option>
								<option value="5">麦粒</option>
								<option value="6">折扣券</option>
								<option value="7">代金券</option></select></td>
					</tr>
				</table>
				<div id="realAward1" style="display: none">
					<label>兑奖形式：</label><span>是否需要扫码填写信息</span><select
						id="awardQrcodeFlag1" onchange="selectUserInfo(1)"><option
							value="1">是</option>
						<option value="2">否</option></select>
					<div id="userInfo1" style="display: none">
						<label>填写用户信息：</label><label><input type="checkbox"
							name="userInfoType1" value="1" />姓名</label> <label><input
							type="checkbox" name="userInfoType1" value="2" />邮寄地址</label> <label><input
							type="checkbox" name="userInfoType1" value="3" />手机号</label> <label><input
							type="checkbox" name="userInfoType1" value="4" />邮编</label>
					</div>
				</div>
				<div id="vipHours1" style="display: none;">
					<label><span class="required">*</span>VIP时长:</label><input id="awardVipDuration1" value=""
						type="text" />
				</div>
				<div id="ml1" style="display: none;">
					<label>麦粒:</label><input id="awardMlAmount1" value="" type="text" />
				</div>
				<div id="discount1" style="display: none;">
					<label>选择内容：</label><select id="awardVideoType1"
						onchange="setValue(1)">
						<option value="0">请选择</option>
						<option value="2009">VIP</option>
						<option value="2006">极清首映</option>
					</select> <select id="vipProducts1" style="display:none">
					</select><select id="blueProducts1" style="display:none">
					</select><br><label>折扣</label><input id="awardDiscount1" value="" />折
				</div>
				<div id="ticket1" style="disPlay: none">
					<label>影片信息：</label><select id="ticketProducts1">
					</select>
				</div>
				<div id="awardList"></div>
				<input type="button" onclick="addAwardList()" value="添加更多奖品" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
					onclick="delLastAward()" value="删除奖品" /><br> <input
					type="button" onclick="secondTab()" value="后退" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
					onclick="submitAward()" value="提交" />
			</div>
		</div>
	</div>
</body>
</html>