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
	var content = "";
	var serie;
	$.each(data.series, function(index, s) {
		if (s.name == 'real') {
			serie = s;
			return true;
		}
	});
	
	$.each(data.days, function(index, day) {
		content += '<tr>';
		content += '<td>' + day + '</td>';
		content += '<td><input type="number"';
		if (serie.data[index] != null) {
			content += ' value="' + serie.data[index] + '"';
		}
		content += '></td>';
		content += '</tr>';
	});
	$(selector).append(content);
}
