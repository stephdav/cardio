$(document).ready(function() {
	initPlanning();
});

function initPlanning() {
	fnProjectGetDetails(updateVelocities);
}

function updateVelocities(data, hv) {
	displayVelocities('burnup', data);
	$('#worst-sprints').text(data.worst);
	$('#average-sprints').text(data.average);
	$('#best-sprints').text(data.best);
	$('#over-commit').text(data.overCommit+"%");
}