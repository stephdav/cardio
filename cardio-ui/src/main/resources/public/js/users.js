$(document).ready(function() {
	initUsers();
});


function initUsers() {
	$('#addUser').on('click', function(e) {
		e.stopPropagation();
		createUser($('#userLogin').val(),$('#userFirstname').val(),$('#userLastname').val());
	});
	$('#userlist').on('click', '.deleteUser', function(e) {
		e.stopPropagation();
		deleteUser($(this).closest('li').attr('id'));
	});

	$('#exportUsers').on('click', function(e) {
		e.stopPropagation();
		downloadExport('/api/users/export');
	});

	initUsersTable();
}

function initUsersTable() {
	$('#users-table').bootstrapTable({
		pagination: true,
		url: '/api/users?bootstrap',
		sidePagination: 'server',
		queryParamsType: 'page',
		queryParams: 'queryParams',
		pageNumber: 1, pageSize: 10, pageList: [10, 25, 50],
	    columns: [
	      { field: 'login', title: 'login', align: 'center', sortable: true },
	      { field: 'firstname', title: 'first name', sortable: true },
	      { field: 'lastname', title: 'last name', sortable: true }
	    ]
	});
	
	$('#users-table').on('load-success.bs.table', function (e, data) {
		$('#users-count').text(data.total);
	});
}

function refreshUsers() {
	$('#users-table').bootstrapTable('refresh');
}


function queryParams() {
	var options = $('#users-table').bootstrapTable('getOptions');
	var params = {};
	params['page'] = options.pageNumber;
	params['limit'] = options.pageSize;
	params['sortName'] = options.sortName;
	params['sortOrder'] = options.sortOrder;
	return params;
}

function createUser(login, firstname, lasstname) {
	var payload = {login: login, firstname: firstname, lastname: lasstname};
	ajaxPost("/api/users", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Users " + hv.location + " created");
			// reset form
			$('#userLogin').val('');
			$('#userFirstname').val('');
			$('#userLastname').val('');
			$('#errors').text('');
			$('.form-error').hide();
			// reload list
			refreshUsers();	
		} else {
			log("Error creating user : " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
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