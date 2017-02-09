$(document).ready(function() {
	initActivities();
});

function initActivities() {
	$('#addActivity').on('click', function(e) {
		e.stopPropagation();
		createActivity($('#activityName').val(), $('#activityDesc').val(), $('#activityStatus').val());
	});
	initActivitiesTable();
}

function initActivitiesTable() {
	$('#activities-table').bootstrapTable({
		pagination: true,
		url: '/api/activities/bootstrap-table',
		sidePagination: 'server',
		queryParamsType: 'page',
		queryParams: 'queryParams',
		pageNumber: 1, pageSize: 10, pageList: [10, 25, 50],
	    columns: [
	      { field: 'name', title: '#', align: 'center' },
	      { field: 'status', title: 'status', sortable: true, searchable: true },
	      { field: 'description', title: 'description', sortable: true, searchable: true }
	    ]
	});
	
	$('#activities-table').on('load-success.bs.table', function (e, data) {
		$('#activities-count').text(data.total);
	});

}


function refresh() {
	$('#activities-table').bootstrapTable('refresh');
}

function createActivity(name, description, status) {
	var payload = {name: name, description: description, status: status};
	ajaxPost("/api/activities", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Activity " + hv.location + " created");
			// reset form
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
	log('OPTIONS: ' + JSON.stringify(options));
	
	var params = {};
	params['page'] = options.pageNumber;
	params['limit'] = options.pageSize;
	params['sortName'] = options.sortName;
	params['sortOrder'] = options.sortOrder;
	return params;
}

