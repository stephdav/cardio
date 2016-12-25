$(document).ready(function() {
	initSprint();
	$('#updateSprint').on('click', function(e) {
		e.stopPropagation();
		updateSprint($('#sprintId').val(), $('#sprintName').val(), $('#sprintStartDate').val(), $('#sprintEndDate').val(), $('#sprintGoal').val(), $('#sprintCommitment').val());
	});
});

function initSprint() {
	getCurrentSprint(updateSprintView);
	getBurndown(displayCalendar, '#measures');
}

function updateSprintView(sprint, hv) {
	$('#sprintId').val(sprint.id);
	$('#sprintName').val(sprint.name);
	$('#sprintStartDate').val(sprint.startDate);
	$('#sprintEndDate').val(sprint.endDate);
	$('#sprintGoal').val(sprint.goal);
	$('#sprintCommitment').val(sprint.commitment);
}

function updateSprint(id, name, startdate, enddate, goal, commitment) {
	var payload = {id: id, name: name, startDate: startdate, endDate: enddate, goal: goal, commitment: commitment};
	ajaxPost("/api/sprints/"+id, payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Sprint " + hv.location + " updated");
			// reset form
		} else {
			log("Error updating sprint : " + errorThrown);
		}
	});
}

function displayCalendar(selector, data) {
	var serie;
	$.each(data.series, function(index, s) {
		if (s.name == 'real') {
			serie = s;
			return true;
		}
	});

	var content = "";
	var count = 0;
	$.each(data.days, function(index, day) {
		if (count==0) {
			content += '<div class="row">'
			content += '<div class="col-xs-1"></div>';
		}
		content += '<div class="col-xs-2">';
		content += '<div class="form-group sprint-day">';
		content += '<input type="number" class="form-control input-lg"';
		if (serie.data[index] != null) {
			content += ' value="' + serie.data[index] + '"';
		}
		content += '>';
		content += '<label>' + day + '</label>';
		content += '</div>';
		content += '</div>';
		count++;
		if (count==5) {
			content += '</div>'
			count=0;
		}
	});
	if (count != 0) {
		content += '</div>'
	}
	content += '<div class="row">'
	content += '<div class="col-xs-1"></div>';
	content += '<div class="col-xs-11"><button id="updateSprintData" type="button" class="btn btn-default">update data</button></div>';
	content += '</div>'
	$(selector).append(content);
}
