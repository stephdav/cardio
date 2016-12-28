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
	    columns: [{
	        field: 'startDate', title: 'from'
	    }, {
	        field: 'endDate', title: 'to'
	    }, {
	        field: 'name', title: '#'
	    }, {
	        field: 'goal', title: 'sprint goal'
	    }, {
	        field: 'commitment', title: 'commitment'
	    }, {
	        field: 'velocity', title: 'velocity'
	    }]
	});

	$('#sprints-table').on('click-row.bs.table', function (e, row, $element, field) {
		window.location = "../../sprint/" + row.id;
	});
}

function getSprints() {
	ajaxGet("/api/sprints?limit=10&sort=enddate", function(data, hv, errorThrown) {
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
		$('#sprints-table').bootstrapTable('load', data);
		$('#sprints-count').text(hv.contentRange.split(/\//)[1]);
	}
}

function createSprint(name, startdate, enddate, goal, commitment) {
	log("name:"+name + " goal:"+goal);
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
			// reload list
			getSprints();	
		} else {
			log("Error creating sprint : " + errorThrown);
		}
	});
}

