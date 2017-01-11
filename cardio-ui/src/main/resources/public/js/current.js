function getBurndown(callback, selector) {
	ajaxGet("/api/sprints/current/data", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			callback(selector, data);
		} else {
			log("Error getting burndown: " + errorThrown);
		}
	});
}
