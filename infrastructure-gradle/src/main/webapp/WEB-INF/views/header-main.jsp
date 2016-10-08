<%@ taglib uri="/jstl/core" prefix="c"%>

<div class="mainHeader">
	<c:choose>
		<c:when test="${empty sessionScope.user}">
			<div class="logo">Todo List
			</div>
			<div class="login"><span>User:<i class="fa fa-user"></i></span>
				<b>guest </b>
				<a href="<c:url value='/main'><c:param name="action" value="login"/></c:url>">Login</a>
				<a href="<c:url value='/main'><c:param name="action" value="registrate"/></c:url>">Registrate</a>
			</div>	
		</c:when>
		<c:otherwise>
			<a class="logo" href="<c:url value='/showActiveTask'><c:param name="showTaskId" value="TODAY"/></c:url>"> Todo List</a>
			<div class="login">
				<span>User:<i class="fa fa-user"></i></span>
				<b>${sessionScope.user.login}</b>
				<a href="<c:url value='/logout'/>">Logout</a>
			</div>    
		</c:otherwise>
	</c:choose>
</div>

