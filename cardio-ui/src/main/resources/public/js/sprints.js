$(document).ready(function() {
	initSprints();
});

function initSprints() {
	$('#addSprint').on('click', function(e) {
		e.stopPropagation();
		createSprint($('#sprintName').val(),$('#sprintStartDate').val(),$('#sprintEndDate').val());
	});
	getSprints();
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
		var content = "";
		$.each(data, function(index, sprint) {
			content += '<li class="list-group-item" id="' + sprint.id + '">';
			content += '<div class="clearfix">';
			content += '<label>';
			content += '[' + sprint.name + '] from '+ sprint.startDate + ' to ' + sprint.endDate;
			content += '</label>';
			content += '</div></li>';
		});
		$('#sprintslist').append(content);
	}
}

function createSprint(name, startdate, enddate) {
	var payload = {name: name, startDate: startdate, endDate: enddate};
	ajaxPost("/api/sprints", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Sprint " + hv.location + " created");
			getSprints();	
		} else {
			log("Error creating sprint : " + errorThrown);
		}
	});
}
