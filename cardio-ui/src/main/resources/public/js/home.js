$(document).ready(function() {
	initHome();
});

function initHome() {
	getCurrentSprint();
	getLeftDays();
	getBurndown();
}

function getCurrentSprint() {
	ajaxGet("/api/sprints/current", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			updateHomeView(data, hv);
		} else {
			log("Error getting sprints: " + errorThrown);
		}
	});
}

function getLeftDays() {
	ajaxGet("/api/sprints/current/leftdays", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			$('#left-days').text(data.value);
		} else {
			log("Error getting left days: " + errorThrown);
		}
	});
}

function getBurndown() {
	ajaxGet("/api/sprints/current/burndown", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			displayChart('container', data);
		} else {
			log("Error getting burndown: " + errorThrown);
		}
	});
}

function updateHomeView(sprint, hv) {
	updateSprintDate(sprint.startDate, '#from-month', '#from-day');
	$('#sprint-name').text('#' + sprint.name);
	$('#sprint-goal').text(sprint.goal);
	updateSprintDate(sprint.endDate, '#to-month', '#to-day');
	$('#sprint-commitment').text(sprint.commitment);
}

function updateSprintDate(date, monthSelector, daySelector) {
	tokens = date.split(/-/);
	$(monthSelector).text(getMonth(tokens[1]));
	$(daySelector).text(tokens[2]);
}

function getMonth(value) {
	months = [ 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec'];
	return months[value-1];
}