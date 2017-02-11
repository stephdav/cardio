$(document).ready(function() {
	initActivities();
});

function initActivities() {
	$('#addActivity').on('click', function(e) {
		e.stopPropagation();
		createActivity($('#activityType').val(), $('#activityName').val(), $('#activityDesc').val(), $('#activityStatus').val());
	});

	initActivitiesTable();
}

function initActivitiesTable() {
	$('#activities-table').bootstrapTable({
		pagination: true,
		url: '/api/activities',
		sidePagination: 'server',
		queryParamsType: 'page',
		queryParams: 'queryParams',
		pageNumber: 1, pageSize: 10, pageList: [10, 25, 50],
	    columns: [
	      { field: 'name', title: '#', align: 'center' },
	      { field: 'type', title: 'type', align: 'center', sortable: true, searchable: true },
	      { field: 'description', title: 'description' },
	      { field: 'status', title: 'status', align: 'center', sortable: true, searchable: true }
	    ]
	});
	
	$('#activities-table').on('load-success.bs.table', function (e, data) {
		$('#activities-count').text(data.total);
	});
	
	$('#typeFilter').on('change', function(e) {
		e.stopPropagation();
		refresh();
	});
	$('#statusFilter').on('change', function(e) {
		e.stopPropagation();
		refresh();
	});

}


function refresh() {
	$('#activities-table').bootstrapTable('refresh');
}

function createActivity(type, name, description, status) {
	var payload = {type: type, name: name, description: description, status: status};
	ajaxPost("/api/activities", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Activity " + hv.location + " created");
			// reset form
			$('#activityType').val('US');
			$('#activityName').val('');
			$('#activityDesc').val('');
			$('#activityStatus').val('READY');
			$('#errors').text('');
			$('.form-error').hide();
			// reload list
			refresh();	
		} else {
			log("Error creating activity : " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}

function queryParams() {
	var options = $('#activities-table').bootstrapTable('getOptions');
	
	var params = {};
	params['page'] = options.pageNumber;
	params['limit'] = options.pageSize;
	params['sortName'] = options.sortName;
	params['sortOrder'] = options.sortOrder;
	
	var type = $('#typeFilter').val();
	if ( type != undefined && type != '') {
		params['type'] = type;
	}
	var status = $('#statusFilter').val();
	if ( status != undefined && status != '') {
		params['status'] = status;
	}
	return params;
}

