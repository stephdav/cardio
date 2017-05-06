$(document).ready(function() {
	initUser();
});

function initUser() {
	var userId = getLastPathLocation();
	getUser(userId);
	
	$('#updateUser').on('click', function(e) {
		e.stopPropagation();
		updateUser($('#userId').text(), $('#userLogin').val(), $('#userFirstname').val(), $('#userLastname').val());
	});

	$('#deleteUser').on('click', function(e) {
		e.stopPropagation();
		deleteUser($('#userId').text());
	});
}

function getUser(id) {
	fnGetUser(id, callbackUser);
}

function callbackUser(usr, hv) {
	log("story: "+JSON.stringify(usr));
	$('#userId').text(usr.id);
	$('#userLogin').val(usr.login);
	$('#userFirstname').val(usr.firstname);
	$('#userLastname').val(usr.lastname);
}

function updateUser(id, login, firstname, lastname) {
	var payload = {id: id, login: login, firstname: firstname, lastname: lastname};
	ajaxPost("/api/users/"+id, payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("User " + hv.location + " updated");
		} else {
			log("Error updating user: " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}

function deleteUser(id) {
	ajaxDelete("/api/users/" + id, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("User deleted");
			window.location.replace("../../users");
		} else {
			log("Error deleting user: " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}