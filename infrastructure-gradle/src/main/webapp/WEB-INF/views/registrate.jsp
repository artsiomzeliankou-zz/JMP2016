<%@ page import="by.gsu.epamlab.constants.Constants"%>
<%@ taglib uri="/jstl/core" prefix="c"%>
<%@ include file="header.jsp"%>
<%@ include file="header-main.jsp"%>

<div class="mainCont w960">
	<div class="createTaskCont">
		<h2>New user registration</h2>
		<c:if test="${not empty errorMessage}">
			<span class="errorMsg show"><c:out value="${errorMessage}" /></span>
		</c:if>
		
		<form name="registrate" method="POST" action="<c:url value='/register'/>">
			<input type="hidden" name="pagename" value="register" />
			<table cellpadding="5" cellspacing="5">
				<tr>
					<td>Enter Your Login </td>
					<td><input id="text" type="text" name=<%=Constants.KEY_LOGIN%> value="${login}"> </td>
					<td><span class="errorMsg">Please fulfill this field</span></td>
				</tr>
				<tr>
					<td>Enter Your Password</td>
					<td><input id="password" type="password" name=<%=Constants.KEY_PASSWORD%>> </td>
					<td><span class="errorMsg">Please fulfill this field</span>	</td>
				</tr>
				<tr>
					<td>Password Confirm</td>
					<td><input id="passwordConf" type="password" name=<%=Constants.KEY_PASSWORD%>> </td>
					<td><span class="errorMsg">Please fulfill this field such as previous</span> </td>
				</tr>
				<tr>
					<td><button id="registerBtn" class="stdBtn" type="submit">Register</button> </td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<a class="backLink" onclick="history.back();return false;" href="#"> <i class="fa fa-angle-left"></i> Back </a>
		</form>
	</div>	
</div>
<%@ include file="footer.jsp"%>

