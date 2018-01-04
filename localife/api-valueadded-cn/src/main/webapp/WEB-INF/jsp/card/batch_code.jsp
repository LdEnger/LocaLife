<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批量生成激活码</title>
</head>
<body>
<form id="batch_code_form">
<div class="biP">
	<ul class="biC">
		<li>
			<label>活动名称</label>
			<span class="w_auto">
			<select id="activityName">
					<c:forEach items="${activityList}" var="aL" varStatus="st">
						<option value="${aL.id}">${aL.activityName}</option>
					</c:forEach>
			</select>
			</span>
		</li>
		<li>
			<label>生成卡用户所属分公司</label>
			<span class="w_auto"><input class="inputC NumDecText" type="text" name="" value=""></span>
		</li>
		<li>
			<label>生成卡用户名</label>
			<span class="w_auto"><input class="inputC NumDecText" type="text" name="" value="${currentUser.userName}"></span>
		</li>
		<li>
			<label>生成数量</label>
			<span class="w_auto"><input class="inputC NumDecText" type="text" name="" value=""></span>
			<label>张</label>
		</li>
		</ul>
	</div>
</form>
</body>
</html>