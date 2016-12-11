$(document).ready(function() {
	initTasks();
});

function initTasks() {
	$('#addUser').on('click', function(e) {
		e.stopPropagation();
		createUser($('#taskDescription').val());
	});
	$('#userlist').on('click', '.deleteUser', function(e) {
		e.stopPropagation();
		deleteUser($(this).closest('li').attr('id'));
	});
	$('#sortByLogin').on('click', function(e) {
		e.stopPropagation();
		getUsersSorted("login");
	});
	$('#sortByFirstname').on('click', function(e) {
		e.stopPropagation();
		getUsersSorted("firstname");
	});
	$('#sortByLastname').on('click', function(e) {
		e.stopPropagation();
		getUsersSorted("lastname");
	});
	getUsers();
}

function getUsers() {
	ajaxGet("/api/users?limit=10", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			updateUsers(data, hv);
		} else {
			log("Error getting users : " + errorThrown);
		}
	});
}

function getUsersSorted(key) {
	ajaxGet("/api/users?limit=10&sort=" + key, function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			updateUsers(data, hv);
		} else {
			log("Error getting users : " + errorThrown);
		}
	});
}

function createUser(taskDesc) {
	var payload = {description: taskDesc};
	ajaxPost("/api/users", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("User " + hv.location + " created");
			getUsers();	
		} else {
			log("Error creating user : " + errorThrown);
		}
	});
}

function deleteUser(taskId) {
	ajaxDelete("/api/users/" + taskId, function(data, hv, errorThrown) {
		if (hv.status == 200) {
			log("User " + taskId + " deleted");
			getUsers();	
		} else {
			log("Error deleting user : " + errorThrown);
		}
	});
}

function updateUsers(data, hv) {
	$('#userlist').empty();
	if (hv.status == 200 || hv.status == 206) {
		var content = "";
		$.each(data, function(index, user) {
			content += '<li class="list-group-item" id="' + user.id + '">';
			content += '<div class="clearfix">';
			content += '<label>';
			content += '[' + user.login + '] '+ user.firstname + ' ' + user.lastname;
			content += '</label>';
			content += '<button class="deleteUser btn btn-xs btn-default pull-right"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
			content += '</div></li>';
		});
		$('#userlist').append(content);
	}
}
