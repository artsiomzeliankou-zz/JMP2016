<%@ taglib uri="/jstl/core" prefix="c"%>
<%@ include file="header.jsp"%>
<%@ include file="header-main.jsp"%>

<div class="mainCont w960">

	<%@ include file="mainCont.jsp"%>
	
	<div class="tableCont">	 
		<h2>${tableHeader}</h2>
		<c:choose>
			<c:when test="${(showTasks > 0)}">
				<form name="showData" method="POST" id="unactiveTaskForm" action="<c:url value='/updateTask'/>">
					<input type="hidden" name="submitType" value="removed" id="submitType">
					<table cellpadding="5" border="1" cellspacing="0">
						<tr>
							<th width="30px"></th>
							<th width="400px">Task</th>
							<th width="120px">File</th>
						</tr>
						<c:forEach items="${tasks}" var="task" varStatus="status">
							<tr>
								<td align="center"><input type="checkbox" name="checkBoxes" value="${task.id}"> </td>
								<td>
									<span>${task.title}</span>
								</td>
								<td align="center">
									<c:if test="${not empty task.fileName}">
										<a	href="<c:url value='/downloadController'><c:param name="fileName" value="${task.fileName}"/></c:url>">
												${task.fileName}
											</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div class="actionsCont">	
						<c:choose>
							<c:when test="${empty showLinks}">
								<a class="removeTask br5" href="#" onclick="submitUnActiveRemove()"> <i class="fa fa-trash-o"></i> Remove </a>
							</c:when>
							<c:otherwise>
								<a class="reAddTask br5" href="#" onclick="submitReAdd()"> <i class="fa fa-retweet"></i> Readd </a>
					   			<a class="deleteTask br5" href="#" onclick="submitDelete()"><i class="fa fa-times"></i>	Delete </a>
							</c:otherwise>
						</c:choose>
					</div>
					
				</form>
			</c:when>
			<c:otherwise>
				<span class="errorMsg show">There are no tasks </span>
			</c:otherwise>
		</c:choose>		
	</div>
</div>
<%@ include file="footer.jsp"%>
