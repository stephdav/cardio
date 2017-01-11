$(document).ready(function() {
	initHome();
});

function initHome() {
	fnSprintFindByDay('now', updateHomeView)
	fnProjectGetDetails(updateBurnup);
}

function updateHomeView(sprints, hv) {
	if (hv.status == 204) {
		$('#from-day').text('');
		$('#from-month').text('');
		$('#to-day').text('');
		$('#to-month').text('');
		$('#sprint-name').text('no pending sprint');
		$('#sprint-goal').text('');
		$('#sprint-commitment').text('-');
	} else if (sprints != undefined && sprints[0] != undefined) {
		var sprint = sprints[0];
		updateSprintDate(sprint.startDate, '#from-month', '#from-day');
		$('#sprint-name').text('[#' + sprint.name + ']');
		$('#sprint-goal').text(sprint.goal);
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
