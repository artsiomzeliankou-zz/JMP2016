<div class="criteriaCont">
		<a href="<c:url value='/showActiveTask'><c:param name="menuId" value="TODAY"/></c:url>">
			Today
		</a>
		<a href="<c:url value='/showActiveTask'><c:param name="menuId" value="TOMORROW"/></c:url>">
			Tomorrow
		</a>
		<a href="<c:url value='/showActiveTask'><c:param name="menuId" value="SOMEDAY"/></c:url>">
			Someday
		</a>
		<a class="extraCrit" href="<c:url value='/showUnactiveTask'><c:param name="menuId" value="FIXED"/></c:url>">
			<i class="fa fa-check-circle"></i> 
			Fixed
		</a>
		<a class="extraCrit" href="<c:url value='/showUnactiveTask'><c:param name="menuId" value="REMOVED"/></c:url>">
			<i class="fa fa-recycle"></i> 
			Recycle Bin
		</a>
	</div>	