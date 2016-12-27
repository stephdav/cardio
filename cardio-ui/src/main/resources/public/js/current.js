function getCurrentSprint(callback) {
	ajaxGet("/api/sprints/current", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			callback(data, hv);
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

function getBurndown(callback, selector) {
	ajaxGet("/api/sprints/current/burndown", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			callback(selector, data);
		} else {
			log("Error getting burndown: " + errorThrown);
		}
	});
}

function getBurnup(callback, selector) {
	ajaxGet("/api/sprints/current/burnup", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			callback(selector, data);
		} else {
			log("Error getting burndown: " + errorThrown);
		}
	});
}