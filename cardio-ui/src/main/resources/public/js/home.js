$(document).ready(function() {
	initHome();
});

function initHome() {
	getCurrentSprint(updateHomeView);
	getBurnup(displayBurnup, 'burnup');
}

function updateHomeView(sprint, hv) {
	updateSprintDate(sprint.startDate, '#from-month', '#from-day');
	$('#sprint-name').text('[#' + sprint.name + ']');
	$('#sprint-goal').text(sprint.goal);
	updateSprintDate(sprint.endDate, '#to-month', '#to-day');
	$('#sprint-commitment').text(sprint.commitment);
	
	fnSprintGetData(sprint.id, updateBurndown);
}

function updateBurndown(data, hv) {
	displayBurndown('burndown', data);
	$('#left-days').text(data.details.leftDays);
}

function updateSprintDate(date, monthSelector, daySelector) {
	tokens = date.split(/-/);
	$(monthSelector).text(getMonthAsShortString(tokens[1]));
	$(daySelector).text(tokens[2]);
}
