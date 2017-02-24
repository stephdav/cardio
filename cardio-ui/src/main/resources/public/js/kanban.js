$(document).ready(function() {
	initKanban();
});

function initKanban() {
	fnProjectKanban(updateBurnup);
}

function updateBurnup(data, hv) {
	$('#story-to-do').text(data.todo.length);
	$('#story-pending').text(data.pending.length);
	$('#story-done').text(data.done.length);
}
