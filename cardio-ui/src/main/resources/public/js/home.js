$(document).ready(function() {
	initHome();
});

function initHome() {
	fnSprintFindByDay('now', updateHomeView)
	fnProjectGetDetails(updateBurnup);
	fnUserCount(function(data,hv){
		$('#team-members').text(data.value);
	});
	fnStoryCount(updateStoriesCount);
	fnSprintCount(updateSprintsCount);
}

function updateSprintsCount(data, hv) {
	var done = 0;
	$.each(data, function(index, parameter) {
		if (parameter.key == 'SPRINTS_COMPLETED') {
			done = parseInt(parameter.value);
			return;
		}
	});
	$('#sprints-done').text(done);
}

function updateStoriesCount(data, hv) {
	$.each(data, function(index, parameter) {
		$('#stories-'+parameter.key.toLowerCase()).text(parameter.value);
	});
}

function updateHomeView(sprints, hv) {
	if (hv.status == 204) {
		$('#from-day').text('');
		$('#from-month').text('');
		$('#to-day').text('');
		$('#to-month').text('');
		$('#sprint-name-and-goal').text('no pending sprint');
		$('#sprint-commitment').text('-');
	} else if (sprints != undefined && sprints[0] != undefined) {
		var sprint = sprints[0];
		updateSprintDate(sprint.startDate, '#from-month', '#from-day');
		$('#sprint-name-and-goal').text('#' + sprint.name + ' ' + sprint.goal);
		updateSprintDate(sprint.endDate, '#to-month', '#to-day');
		$('#sprint-commitment').text(sprint.commitment);
		fnSprintGetData(sprint.id, updateBurndown);
	}
}

function updateBurndown(data, hv) {
	if (hv.status == 204) {
		$('#left-days').text('-');
	} else {
		displayBurndown('burndown', data);
		$('#left-days').text(data.details.leftDays);
	}
}

function updateBurnup(data, hv) {
	displayBurnup('burnup', data);
}

function updateSprintDate(date, monthSelector, daySelector) {
	tokens = date.split(/-/);
	$(monthSelector).text(getMonthAsShortString(tokens[1]));
	$(daySelector).text(tokens[2]);
}
