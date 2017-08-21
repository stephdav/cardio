$(document).ready(function() {
	initSprint();
});

function initSprint() {
	fnGetCapacity(checkCapacity);
	
	var sprintId = getLastPathLocation();
	getSprint(sprintId);
	getSprintData();

	$('#updateSprintProperties').on('click', function(e) {
		e.stopPropagation();
		updateSprint($('#sprintId').val(), $('#sprintName').val(), $('#sprintStartDate').val(), $('#sprintEndDate').val(), $('#sprintGoal').val(), $('#sprintCommitment').val(), $('#sprintCapacity').val());
	});
	$('#measures').on('click', 'button', function(e) {
		e.stopPropagation();
		updateSprintData($('#sprintId').val());
	});
}

function checkCapacity(data, hv) {
	if (data.value != 'true') {
		log("disable...");
		$('#sprintCapacity').prop('disabled', true);
	}
}

function updateSprint(id, name, startdate, enddate, goal, commitment, capacity) {
	var payload = {id: id, name: name, startDate: startdate, endDate: enddate, goal: goal, commitment: commitment, capacity: capacity};
	ajaxPatch("/api/sprints/"+id, payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Sprint " + hv.location + " updated");
			$('#errors').text('');
			$('.form-error').hide();
			getSprintData(data, hv);
		} else {
			log("Error updating sprint '" + id + "': " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}

function updateSprintData(id) {
	var data = [];
	$('#measures').find('.sprint-day').each(function( index ) {
		var day = {};
		day.key = $(this).find('label').text();
		day.val = $(this).find('input').val();
		data.push(day);
	});

	fnSprintUpdateData(id, data, getSprintData);
}

function getSprint(id) {
	fnSprintGet(id, callbackSprint);
}

function callbackSprint(sprint, hv) {
	$('#sprintId').val(sprint.id);
	$('#sprintName').val(sprint.name);
	$('#sprintStartDate').val(sprint.startDate);
	$('#sprintEndDate').val(sprint.endDate);
	$('#sprintGoal').val(sprint.goal);
	$('#sprintCommitment').val(sprint.commitment);
	$('#sprintCapacity').val(sprint.capacity);
}

function getSprintData() {
	var sprintId = getLastPathLocation();
	fnSprintGetData(sprintId, callbackSprintData);
}

function callbackSprintData(data, hv) {
	displayBurndown('burndown', data);
	displayCalendar(data);
}

function displayCalendar(data) {
	var details = data.details;

	$('#measures').empty();
	var content = "";
	content += '<div class="row">';
	content += '<div class="col-xs-1"></div>';
	content += '<div class="col-xs-2 day-of-week">monday</div>';
	content += '<div class="col-xs-2 day-of-week">tuesday</div>';
	content += '<div class="col-xs-2 day-of-week">wednesday</div>';
	content += '<div class="col-xs-2 day-of-week">thursday</div>';
	content += '<div class="col-xs-2 day-of-week">friday</div>';
	content += '</div>'

	var day0 = getDay(details.days[0]);
	if (day0 > 1) {
		content += '<div class="row">';
		content += '<div class="col-xs-1 week-of-year">S' + getWeek(details.days[0]) + '</div>';
		var idx = 1;
		for (; idx < day0; idx++) {
			content += '<div class="col-xs-2"></div>';
		}
	}

	var count = day0 - 1;
	$.each(details.days, function(index, day) {
		
		if (count==0) {
			content += '<div class="row">';
			content += '<div class="col-xs-1 week-of-year">S' + getWeek(day) + '</div>';
		}
		content += '<div class="col-xs-2">';
		content += '<div class="form-group sprint-day">';
		content += '<input type="number" class="form-control input-lg"';
		if (details.done[index] != null) {
			content += ' value="' + details.done[index] + '"';
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
	$('#measures').append(content);
}
