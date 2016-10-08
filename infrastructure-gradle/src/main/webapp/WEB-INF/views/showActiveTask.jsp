<%@ taglib uri="/jstl/core" prefix="c"%>
<%@ include file="header.jsp"%>
<%@ include file="header-main.jsp"%>

<div class="mainCont w960">

	<%@ include file="mainCont.jsp"%>
	
	<div class="tableCont">
		<h2>${tableHeader}</h2>
		<c:choose>
			<c:when test="${(showTasks > 0)}">
				<form name="showData" method="POST" id="activeTaskForm" action="<c:url value='/updateTask'/>">
					<input type="hidden" name="submitType" value="fix" id="submitType">
					<table cellpadding="5" border="1" cellspacing="0">
						<tr>
							<th  width="30px"></th>
							<th  width="400px">Task</th>
							<c:if test="${(showDate > 0)}">
								<th  width="120px">Date</th>
							</c:if>
							<th>File</th>
						</tr>
						<c:forEach items="${tasks}" var="task" varStatus="status">
							<tr>
								<td align="center"><input type="checkbox" name="checkBoxes"	value="${task.id}"> </td>
								<td>
									<span>${task.title}</span>
								</td>
								<c:if test="${(showDate > 0)}">
								<td> 
									<p>${task.date}</p>
								</td>
								</c:if>
								<td align="center">
									<c:choose>
										<c:when test="${empty task.fileName}">
											<a href="<c:url value='/uploadController'><c:param name="idTask" value="${task.id}"/></c:url>">
												<button type="button">Upload file...</button>
											</a>
										</c:when>
										<c:otherwise>
											<a	href="<c:url value='/downloadController'><c:param name="fileName" value="${task.fileName}"/></c:url>">
												${task.fileName}
											</a>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
					
					<div class="actionsCont">
					 	<a class="addTask br5" href="<c:url value='/createTask'><c:param name="checkData" value="no"/></c:url>"> 
					 		<i class="fa fa-plus-square"></i> Add task</a> 
						<a class="fixTask br5" href="#" onclick="submitFix()"> <i class="fa fa-check-square-o"></i> Fix</a>
						<a class="removeTask br5" href="#" onclick="submitActiveRemove()"> <i class="fa fa-trash-o"></i> Remove</a> 
					</div>	
				</form>
			</c:when>
			<c:otherwise>
				<span class="errorMsg show">There are no tasks </span>
				<div class="actionsCont">
					<a class="addTask br5" href="<c:url value='/createTask'><c:param name="checkData" value="no"/></c:url>">
						<i class="fa fa-plus-square"></i> Add task</a> 	
				</div>
			</c:otherwise>
		</c:choose>		
	</div>
</div>
<%@ include file="footer.jsp"%>
