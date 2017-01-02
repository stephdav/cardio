$(document).ready(function() {
	initPlanning();
});

function initPlanning() {
	getVelocities(displayVelocities, 'burnup');
}

function getVelocities(callback, selector) {
	ajaxGet("/api/sprints/velocity", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			callback(selector, data);
			$('#worst-sprints').text(data.worst);
			$('#average-sprints').text(data.average);
			$('#best-sprints').text(data.best);
			$('#over-commit').text(data.overCommit+"%");
		} else {
			log("Error getting burndown: " + errorThrown);
		}
	});
}