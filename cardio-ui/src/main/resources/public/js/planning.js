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
		} else {
			log("Error getting burndown: " + errorThrown);
		}
	});
}