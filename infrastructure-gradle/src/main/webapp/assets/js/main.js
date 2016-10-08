$(function() {

	$('#createTask').click(function(ev) {
		ev.preventDefault();//stop browser default action
		ev.stopPropagation();//stop event bubbling
		if ($('#text').val() == '') {
			$('#text').next().show(200);
		} else if ($('#date').val() == '') {
			$('#date').next().show(200);
		} else {
			$('form[name="createForm"]').submit();
		}
	});

	$('#submitLogin').click(function(ev) {
		ev.preventDefault();
		ev.stopPropagation();
		if ($('#login').val() == '') {
			$('#login').next().show(200);
		} else if ($('#password').val() == '') {
			$('#password').next().show(200);
		} else {
			$('form[name="loginForm"]').submit();
		}
	});

	$('#registerBtn').click(function(ev) {
		ev.preventDefault();
		ev.stopPropagation();
		if ($('#text').val() == '') {
			$($('#text').parent().next().children()[0]).show(200);
		} else if ($('#password').val() == '') {
			$($('#password').parent().next().children()[0]).show(200);
		} else if ($('#passwordConf').val() == '') {
			$($('#passwordConf').parent().next().children()[0]).show(200);
		} else {
			$('form[name="registrate"]').submit();
		}
	});

});