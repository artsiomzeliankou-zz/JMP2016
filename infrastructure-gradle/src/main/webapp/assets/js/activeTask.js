function submitFix() {
	$('#submitType').val('FIX');
	$('#activeTaskForm').submit();
}

function submitActiveRemove() {
	$('#submitType').val('REMOVE');
	$('#activeTaskForm').submit();
	//console.log($('#submitType').name);
}


