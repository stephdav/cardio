$(document).ready(function() {
	initHome();
});

function initHome() {
	getCurrentSprint();
}

function getCurrentSprint() {
	ajaxGet("/api/sprints/current", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			updateHomeView(data, hv);
		} else {
			log("Error getting sprints : " + errorThrown);
		}
	});
}

function updateHomeView(sprint, hv) {
	$('#current-sprint').empty();
	var content = '';
	if (hv.status == 200 || hv.status == 206) {
		content += '<dt>name</dt><dd>' + sprint.name + '</dd>';
		content += '<dt>from</dt><dd>' + sprint.startDate + '</dd>';
		content += '<dt>to</dt><dd>' + sprint.endDate + '</dd>';
	} else {
		content += '<dt>no pending sprint</dt>';
	}
	$('#current-sprint').append(content);
}