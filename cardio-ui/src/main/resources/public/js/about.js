$(document).ready(function() {
	initAbout();
});

function initAbout() {
	getAppVersion();
}

function getAppVersion() {
	ajaxGet("/api/config/parameters/cardio.version", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			$('#app-version').text(data.value);
		} else {
			log("Error getting parameter 'project.name': " + errorThrown);
		}
	});
}
