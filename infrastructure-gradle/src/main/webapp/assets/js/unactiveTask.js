function submitUnActiveRemove() {
	$('#submitType').val('REMOVE');
	$('#unactiveTaskForm').submit();
}

function submitReAdd() {
	$('#submitType').val('READD');
	$('#unactiveTaskForm').submit();
}

function submitDelete() {
	$('#submitType').val('DELETE');
	$('#unactiveTaskForm').submit();
}