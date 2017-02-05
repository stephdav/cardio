$(document).ready(function() {
	initVision();
});

function initVision() {

	getVision();
	$('#editVision').on('click', function(e) {
		e.stopPropagation();
	});
}

function getVision() {
	ajaxGet("/api/project/vision", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			updateVision(data, hv);
		} else {
			log("Error getting vision: " + errorThrown);
		}
	});
}

function updateVision(data, hv) {
	$('.vision-body').empty();
	$.each(data.lines, function(index, line) {
		content = '<p>';
		content += line;
		content += '</p>';
		$('.vision-body').append(content);
	});
}