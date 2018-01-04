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
<script type="text/javascript" src="js/award/award_activity.js?v=${js_version}"></script>
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
	<div data-options="title:'活动管理',region:'center'" class="regionCenter">
		<div id="award_toolbar">
			<input id="add_award" type="button" value="添加活动" class="btn btn-success"/>
			<label>活动标题</label>
			<input id="searchTitle" type="text" />
			<input id="query_activity" type="button" value="查询" class="btn btn-success"/>
		</div>
		
		<table id="award_activity_table">
		</table>
		
		<div id="award_activity_dialog" data-options="iconCls:'icon-save',title:'活动管理',closed:true,modal:true" style="padding:5px;width:590px;height:440px;">
			<div>
				<span style="font-size:24px;">添加活动</span>
			</div>
			<div id="tabOne" title="基础设置" style="height:620px;">
				<input id="id" type="hidden" value=""/>
				<table>
					<tr>
						<td>
							<label><span class="required">*</span>活动标题：</label>
						</td>
						<td>
							<input id="title" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>活动类型：</label>
						</td>
						<td>
							<select id="type">
								<option value="0">请选择活动类型</option>
								<!-- <option value="1">砸金蛋</option>
								<option value="2">老虎机</option>
								<option value="3">大转盘</option> -->
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>活动logo：</label>
						</td>
						<td>
							<img id="logoUrl" src="" style="height: 80px; width: 160px" />
							<input id="uploadLogoUrl" type="button" value="选择封面" />
							<label>（尺寸：280 X 160  ） 小于4M</label>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>活动大背景图：</label>
						</td>
						<td>
							<img id="bgUrl" src="" style="height: 80px; width: 160px" />
							<input id="uploadBgUrl" type="button" value="选择封面" />
							<label>（尺寸：1920 X 1080  ） 小于4M</label>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>活动进行时封面：</label>
						</td>
						<td>
							<img id="playingBgUrl" src="" style="height: 80px; width: 160px" />
							<input id="uploadPlayingBgUrl" type="button" value="选择封面" />
							<label>（尺寸：1920 X 1080  ） 小于4M</label>
						</td>
					</tr>
					<!-- 
					<tr>
						<td><label>活动结束主题：</label></td>
						<td><input type="text" id="endTitle" /></td>
					</tr>
					 -->
					<input id="endTitle" type="hidden" />
					<tr>
						<td>
							<label><span class="required">*</span>活动详情页封面：</label>
						</td>
						<td>
							<img id="infoBgUrl" src="" style="height: 80px; width: 160px" />
							<input id="uploadInfoBgUrl" type="button" value="选择封面" />
							<label>（尺寸：1920 X 1080  ） 小于4M</label>
						</td>
					</tr>
					<!-- 
					<tr>
						<td><label>活动结束说明：</label></td>
						<td><textarea id="endText"></textarea></td>
					</tr>
					 -->
					<input id="endText" type="hidden" />
					<tr>
						<td>
							<label><span class="required">*</span>活动时间：</label>
						</td>
						<td>
							<input id="beginTime" name="beginTime" class="easyui-datetimebox" data-options="required:false,showSeconds:true" value="" style="width: 150px">
							至
							<input id="endTime" name="endTime" class="easyui-datetimebox" data-options="required:false,showSeconds:true" value="" style="width: 150px">
						</td>
					</tr>
					<tr>
						<td>
							<label>活动说明：</label>
						</td>
						<td>
							<textarea id="activityDesc"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>中奖概率：</label>
						</td>
						<td>
							<input id="awardWinRatio" type="text" />
							<span>&nbsp;&nbsp;&nbsp;&nbsp;即用户每次抽奖的中奖概率</span>
						</td>
					</tr>
					<tr>
						<td>
							<label>是否可以重复抽奖：</label>
						</td>
						<td>
							<label>
								<input name="awardWinAgain" type="radio" value="1" checked="true" />是
							</label>
							<label>
								<input name="awardWinAgain" type="radio" value="2"  />否
							</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;（用户中奖以后将不会再次抽中奖品，概率为0）</span>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>适用人群：</label>
						</td>
						<td>
							<label>
								<input name="awardPlayerType" type="radio" value="1" onclick="showPayInfo(1)" />全部用户
							</label>
							<label>
								<input name="awardPlayerType" type="radio" value="2" onclick="showPayInfo(2)" />VIP用户
							</label>
							<label>
								<input name="awardPlayerType" type="radio" value="3" onclick="showPayInfo(3)" />消费用户
							</label>
						</td>
					</tr>
					<tr class="showPlayerType" style="display: none;">
						<td>
						</td>
						<td>
							<label>消费时间段</label>
							<input id="payBeginTime" name="payBeginTime" class="easyui-datetimebox" data-options="required:false,showSeconds:true" value="" style="width: 150px">
							至
							<input id="payEndTime" name="payEndTime" class="easyui-datetimebox" data-options="required:false,showSeconds:true" value="" style="width: 150px">
						</td>
					</tr>
					<tr class="showPlayerType" style="display: none;">
						<td>
						</td>
						<td>
							<label>消费金额：>=</label>
							<input id="paySum" type="text" />元或麦币
						</td>
					</tr>
					<tr>
						<td>
							<span class="required">*</span>活动备注
						</td>
						<td>
							<input id="awardRemark" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="下一步" onclick="secondTab()" />
						</td>
					</tr>
				</table>
			</div>
			<div id="tabTwo" title="活动设置" style="display: none">
				<table>
					<tr>
						<td>
							<label><span class="required">*</span>绑定手机号方式：</label>
						</td>
						<td>
							<label>
								<input name="phoneBindType" type="radio" value="1" />不绑定
							</label>
							<label>
								<input name="phoneBindType" type="radio" value="2" />抽奖前绑定
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>每日抽取次数：</label>
						</td>
						<td>
							<input id="playLimitDay" type="text">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;大于0</span>
						</td>
					</tr>
					<tr>
						<td>
							<label>重复抽奖（日抽取次数用完）提示：</label>
						</td>
						<td>
							<input id="playPromptDay" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>每人最多允许抽取总次数：</label>
						</td>
						<td>
							<input id="playLimitTotal" type="text" />
							<span>&nbsp;&nbsp;&nbsp;&nbsp;大于0</span>
						</td>
					</tr>
					<tr>
						<td>
							<label>重复抽奖（总抽取次数用完）提示：</label>
						</td>
						<td>
							<input id="playPromptTotal" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							<label>中奖提示：</label>
						</td>
						<td>
							<input id="playPromptWin" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							<label>未中奖提示：</label>
						</td>
						<td>
							<input id="playPromptLost" type="text">
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="后退" onclick="firstTab()" />
						</td>
						<td>
							<input type="button" value="下一步" onclick="thirdTab()" />
						</td>
					</tr>
				</table>
			</div>
			<div id="tabThree" title="奖项设置" style="display: none">
				<span class="required">注意：活动类型为大转盘的，奖品必须设置为4个。活动类型为老虎机的，奖品数量至少为3个。</span>
				<!-- <table id="award1" class="award">
					<tr>
						<td>
							<label>奖品1</label>
						</td>
						<td>
							<input id="id1" type="hidden" value="" />
						</td>
					</tr>
					<tr>
						<td>
							<label>奖品类别：</label>
						</td>
						<td>
							<input id="awardType1" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td>
							<label>奖品名称：</label>
						</td>
						<td>
							<input id="awardName1" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>活动奖品图片：</label>
						</td>
						<td>
							<img id="awardPicUrl1" src="" style="height: 30px; width: 60px;">
							<input id="uploadAwardPic1" type="button" value="选择图片" />
							<label>（尺寸：700 X 700  ） 小于4M</label>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>奖品icon：</label>
						</td>
						<td>
							<img id="awardIconUrl1" src="" style="height: 30px; width: 60px;">
							<input id="uploadAwardIcon1" type="button" value="选择图片" />
							<label>（尺寸：134 x 134  ） 小于4M</label>
						</td>
					</tr>
					<tr>
						<td>
							<label>奖品说明：</label>
						</td>
						<td>
							<textarea id="awardDesc1"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>奖品有效时间：</label>
						</td>
						<td>
						
						<input class="laydate-icon" id="availableBeginTime1" value="" style="width: 150px" onclick="laydate()">至
						<input class="laydate-icon" id="availableEndTime1"  value="" style="width: 150px" onclick="laydate()">
						
							<input id="availableBeginTime1" class="easyui-datebox" data-options="required:false" value="" style="width: 150px">
							至
							<input id="availableEndTime1" class="easyui-datebox" data-options="required:false" value="" style="width: 150px">
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>奖品数量：</label>
						</td>
						<td>
							<input id="awardAmount1" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>每天中奖上限：</label>
						</td>
						<td>
							<input id="winLimitDay1" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td>
							<label><span class="required">*</span>中奖码生成：</label>
						</td>
						<td>
							<select id="awardCodeType1">
								<option value="1">自动生成</option>
								<option value="2">批量导入</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label>奖品属性：</label>
						</td>
						<td>
							<select id="awardProperty1" onchange="selectProperty(1)">
								<option value="1">实物</option>
								<option value="2">电子兑换券</option>
								<option value="3">VIP时长</option>
								<option value="4">电影票</option>
								<option value="5">麦币</option>
								<option value="6">折扣券</option>
								<option value="7">代金券</option>
							</select>
						</td>
					</tr>
				</table>
				<div id="realAward1" style="display: none">
					<label>兑奖形式：</label>
					<span>是否需要扫码填写信息</span>
					<select	id="awardQrcodeFlag1" onchange="selectUserInfo(1)">
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
					<div id="userInfo1" style="display: none">
						<label>填写用户信息：</label>
						<label>
							<input name="userInfoType1" type="checkbox" value="1" />姓名
						</label>
						<label>
							<input name="userInfoType1" type="checkbox" value="2" />邮寄地址
						</label>
						<label>
							<input name="userInfoType1" type="checkbox" value="3" />手机号
						</label>
						<label>
							<input name="userInfoType1" type="checkbox" value="4" />邮编
						</label>
					</div>
				</div>
				<div id="vipHours1" style="display: none;">
					<label>
						<span class="required">*</span>VIP时长:
					</label>
					<input id="awardVipDuration1" type="text" value="" />
					<span>天</span>
				</div>
				<div id="ticket1" style="disPlay: none">
					<label>影片信息：</label>
					<select id="ticketProducts1">
					</select>
				</div>
				<div id="ml1" style="display: none;">
					<label>麦币:</label>
					<input id="awardMlAmount1" type="text" value="" />
					<span>个</span>
				</div>
				<div id="discount1" style="display: none;">
					<label>选择内容：</label>
					<select id="awardVideoType1" onchange="setValue(1)">
						<option value="0">请选择</option>
						<option value="2009">VIP</option>
						<option value="2006">极清首映</option>
					</select>
					<select id="vipProducts1" style="display:none">
					</select>
					<select id="blueProducts1" style="display:none">
					</select>
					<br>
					<label>折扣</label>
					<input id="awardDiscount1" value="" />
					折
				</div>
				<input id="deleteAward1" type="button" value="删除奖品" onclick="delLastAward(1)" /> -->
				<div id="awardList">
				</div>
				<input type="button" value="添加更多奖品" onclick="addAwardList()" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- <input type="button" value="删除奖品" onclick="delLastAward()" /> -->
				<br>
				<table width="100%">
					<tr>
						<td>
							<input type="button" value="后退" onclick="secondTab()" />
						</td>
						<td>
							<div id="submitDiv">
								<input type="button" value="提交" onclick="submitAward()" />
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>