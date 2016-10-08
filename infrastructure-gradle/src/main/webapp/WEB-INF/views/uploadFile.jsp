<%@ taglib uri="/jstl/core" prefix="c"%>
<%@ include file="header.jsp"%>
<%@ include file="header-main.jsp"%>

<div class="mainCont w960">
	
	<form name="uploadForm" method="post" enctype="multipart/form-data" action="<c:url value='/uploadController'/>">
	
		<h3 class="selectFileTitle"> Select a file to upload:</h3>
		
		<div class="selectFile" title="Select file">
			<input class="file" id="file" type="file" name="data" size="1"/> 
		</div>
		
		<input id="submitUpload" class="stdBtn" type="submit" value="Upload">

	</form>
	
	<a class="backLink" href="<c:url value='/showActiveTask'><c:param name="showTaskId" value="TODAY"/></c:url>"> 
		<i class="fa fa-angle-left"></i> Back 
	</a>
	
</div>

<%@ include file="footer.jsp"%>