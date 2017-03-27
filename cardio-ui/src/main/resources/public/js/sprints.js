$(document).ready(function() {
	initSprints();
});

function initSprints() {
	$('#addSprint').on('click', function(e) {
		e.stopPropagation();
		createSprint($('#sprintName').val(), $('#sprintStartDate').val(), $('#sprintEndDate').val(), $('#sprintGoal').val(), $('#sprintCommitment').val());
	});
	
	$('#exportSprints').on('click', function(e) {
		e.stopPropagation();
		downloadExport('/api/sprints/export');
	});

	initSprintsTable();
}

function initSprintsTable() {
	$('#sprints-table').bootstrapTable({
		pagination: true,
		url: '/api/sprints?bootstrap',
		sidePagination: 'server',
		queryParamsType: 'page',
		queryParams: 'queryParams',
		pageNumber: 1, pageSize: 10, pageList: [10, 25, 50],
	    columns: [
	      { field: 'startDate', title: 'from', align: 'center', formatter: 'dateFormatter' },
	      { field: 'endDate', title: 'to', align: 'center', formatter: 'dateFormatter' },
	      { field: 'name', title: '#', align: 'center' },
	      { field: 'goal', title: 'sprint goal' },
	      { field: 'commitment', title: 'commitment', align: 'center' },
	      { field: 'velocity', title: 'velocity', align: 'center' }
	    ]
	});

	$('#sprints-table').on('load-success.bs.table', function (e, data) {
		$('#sprints-count').text(data.total);
	});

	$('#sprints-table').on('click-row.bs.table', function (e, row, $element, field) {
		window.location = "../sprint/" + row.id;
	});
}

function refreshSprints() {
	$('#sprints-table').bootstrapTable('refresh');
}

function queryParams() {
	var options = $('#sprints-table').bootstrapTable('getOptions');
	var params = {};
	params['page'] = options.pageNumber;
	params['limit'] = options.pageSize;
//	params['sortName'] = options.sortName;enddate
//	params['sortOrder'] = options.sortOrder;desc
	params['sortName'] = "enddate";
	params['sortOrder'] = "desc";
	return params;
}

function dateFormatter(value, row) {
	return convertToDDMMYYYY(value);
}

function createSprint(name, startdate, enddate, goal, commitment) {
	var payload = {name: name, startDate: startdate, endDate: enddate, goal: goal, commitment: commitment};
	ajaxPost("/api/sprints", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Sprint " + hv.location + " created");
			// reset form
			$('#sprintName').val('');
			$('#sprintStartDate').val('');
			$('#sprintEndDate').val('');
			$('#sprintGoal').val('');
			$('#sprintCommitment').val('');
			$('#errors').text('');
			$('.form-error').hide();
			// reload list
			refreshSprints();	
		} else {
			log("Error creating sprint : " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}

