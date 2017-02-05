$(document).ready(function() {
	initSprints();
});

function initSprints() {
	$('#addSprint').on('click', function(e) {
		e.stopPropagation();
		createSprint($('#sprintName').val(), $('#sprintStartDate').val(), $('#sprintEndDate').val(), $('#sprintGoal').val(), $('#sprintCommitment').val());
	});
	initSprintsTable();
	getSprints();
}

function initSprintsTable() {
	$('#sprints-table').bootstrapTable({
		pagination: true,
		sidePagination: 'server',
	    columns: [
	      { field: 'startDate', title: 'from', align: 'center', formatter: 'dateFormatter' },
	      { field: 'endDate', title: 'to', align: 'center', formatter: 'dateFormatter' },
	      { field: 'name', title: '#', align: 'center' },
	      { field: 'goal', title: 'sprint goal' },
	      { field: 'commitment', title: 'commitment', align: 'center' },
	      { field: 'velocity', title: 'velocity', align: 'center' }
	    ]
	});

	$('#sprints-table').on('click-row.bs.table', function (e, row, $element, field) {
		window.location = "../sprint/" + row.id;
	});
	$('#sprints-table').on('page-change.bs.table', function (e, number, size) {
		getSprints(number, size);
	});
}

function dateFormatter(value, row) {
	return convertToDDMMYYYY(value);
}

function getSprints(page, limit) {
	if (page == undefined) {
		page = 1;
	}
	if (limit == undefined) {
		limit = 10;
	}
	ajaxGet("/api/sprints?page=" + page + "&limit=" + limit + "&sort=enddate&desc", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			updateSprints(data, hv);
		} else {
			log("Error getting sprints : " + errorThrown);
		}
	});
}

function updateSprints(data, hv) {
	$('#sprintslist').empty();
	if (hv.status == 200 || hv.status == 206) {
		var nb = hv.contentRange.split(/\//)[1];
		var bootstrapTableData = { rows: [] };
		bootstrapTableData.total = nb;
		bootstrapTableData.rows = data;
		$('#sprints-table').bootstrapTable('load', bootstrapTableData);
		$('#sprints-count').text(nb);
	}
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
			getSprints();	
		} else {
			log("Error creating sprint : " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}

