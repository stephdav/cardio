$(document).ready(function() {
	initSprint();
	initSprintEvents();
});

function initSprint() {
	var sprintId = getLastPathLocation();
	fnSprintGet(sprintId, updateSprintView);
	fnSprintGetData(sprintId, displayCalendar);
}

function initSprintEvents() {
	$('#updateSprintProperties').on('click', function(e) {
		e.stopPropagation();
		fnSprintUpdate($('#sprintId').val(), $('#sprintName').val(), $('#sprintStartDate').val(), $('#sprintEndDate').val(), $('#sprintGoal').val(), $('#sprintCommitment').val());
	});
	$('#measures').on('click', 'button', function(e) {
		e.stopPropagation();
		updateData($('#sprintId').val());
	});
}

function updateSprintView(sprint, hv) {
	$('#sprintId').val(sprint.id);
	$('#sprintName').val(sprint.name);
	$('#sprintStartDate').val(sprint.startDate);
	$('#sprintEndDate').val(sprint.endDate);
	$('#sprintGoal').val(sprint.goal);
	$('#sprintCommitment').val(sprint.commitment);
}

function displayCalendar(data, hv) {
	var selector = '#measures';
	var serie;
	$.each(data.series, function(index, s) {
		if (s.name == 'done') {
			serie = s;
			return true;
		}
	});

	var content = "";
	content += '<div class="row">';
	content += '<div class="col-xs-1"></div>';
	content += '<div class="col-xs-2 day-of-week">monday</div>';
	content += '<div class="col-xs-2 day-of-week">tuesday</div>';
	content += '<div class="col-xs-2 day-of-week">wednesday</div>';
	content += '<div class="col-xs-2 day-of-week">thursday</div>';
	content += '<div class="col-xs-2 day-of-week">friday</div>';
	content += '</div>'

	var day0 = getDay(data.days[0]);
	if (day0 > 1) {
		content += '<div class="row">';
		content += '<div class="col-xs-1 week-of-year">S' + getWeek(data.days[0]) + '</div>';
		var idx = 1;
		for (; idx < day0; idx++) {
			content += '<div class="col-xs-2"></div>';
		}
	}

	var count = day0 - 1;
	$.each(data.days, function(index, day) {
		
		if (count==0) {
			content += '<div class="row">';
			content += '<div class="col-xs-1 week-of-year">S' + getWeek(day) + '</div>';
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

function updateData(id) {
	var payload = { data: {} };
	$('#measures').find('.sprint-day').each(function( index ) {
		var key = $(this).find('label').text();
		var val = $(this).find('input').val();
		payload.data[key] = val;
	});
	
	ajaxPost("/api/sprints/"+id+"/data", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Sprint " + hv.location + " data updated");
			// reset form
		} else {
			log("Error updating sprint data : " + errorThrown);
		}
	});
}

