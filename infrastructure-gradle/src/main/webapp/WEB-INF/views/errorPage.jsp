<%@ taglib uri="/jstl/core" prefix="c"%>
<%@ include file="header.jsp"%>
<%@ include file="header-main.jsp"%>

<div class="mainCont w960">
	
	<h2 class="errorMsg">${errorMessage}</h2>
	<a class="backLink" onclick="history.back();return false;" href="#"> <i class="fa fa-angle-left"></i> Back </a>
		
</div>

<jsp:include page="footer.jsp" />
