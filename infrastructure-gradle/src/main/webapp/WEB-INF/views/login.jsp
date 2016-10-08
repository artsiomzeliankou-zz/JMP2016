<%@ page import="by.gsu.epamlab.constants.Constants"%>
<%@ taglib uri="/jstl/core" prefix="c"%>
<%@ include file="header.jsp"%>
<%@ include file="header-main.jsp"%>

<div class="mainCont w960">

	<div class="createTaskCont">
		<c:if test="${not empty errorMessage}">
			<span class="errorMsg show"><c:out value="${errorMessage}" /></span>
		</c:if>
		
		<form name="loginForm" method="POST" action="<c:url value='/login'/>">
			
			<p>Login:</p> 
			<input class="w30" id="login" type="text" name=<%=Constants.KEY_LOGIN%> >
			<span class="errorMsg">Please fulfill this field</span>
				
			<p>Password:</p> 
			<input class="w30" id="password" type="password" name=<%=Constants.KEY_PASSWORD%> value="" size="6">
			<span class="errorMsg">Please fulfill this field</span>
			
			<input id="submitLogin" class="stdBtn" type="submit" value="Submit">
			
		</form>
		<a class="backLink" onclick="history.back();return false;" href="#"> <i class="fa fa-angle-left"></i> Back </a>
	</div>
</div>
<%@ include file="footer.jsp"%>
