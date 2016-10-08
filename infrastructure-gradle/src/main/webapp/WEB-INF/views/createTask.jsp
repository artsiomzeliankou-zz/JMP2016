<%@ taglib uri="/jstl/core" prefix="c"%>
<%@ page import="by.gsu.epamlab.constants.Constants"%>
<%@ include file="header.jsp"%>
<%@ include file="header-main.jsp"%>

<div class="mainCont w960">
	<div class="createTaskCont">
		<h2>Create new Todo</h2>
		<form name="createForm" method="POST" action="<c:url value='/createTask'/>">
			<p>Todo</p>
			
			<input id="text" type="text" name=<%=Constants.KEY_TASK_TITLE%>>
			
			<span class="errorMsg">Please fulfill this field</span>
			
			<p>Date</p>
			
			<input id="date" type="date" name=<%=Constants.KEY_TASK_DATE%> >
			
			<span class="errorMsg">Can't be empty. Please fulfill this field</span>
			
			<input id="createTask" class="stdBtn" type="submit" value="Create"> 
			
			<input class="stdBtn" type="reset" value="Clear">
		</form>
	</div>
	
	<a class="backLink" onclick="history.back();return false;" href="#"> <i class="fa fa-angle-left"></i> Back </a>

</div>	

<%@ include file="footer.jsp"%>
