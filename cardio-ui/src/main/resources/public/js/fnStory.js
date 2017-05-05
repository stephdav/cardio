function fnStoryCount(callback) {
	ajaxGet("/api/stories/count", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error: " + errorThrown);
		}
	});
}

function fnStoryGet(id, callback) {
	ajaxGet("/api/stories/" + id, function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error getting story '" + id + "': " + errorThrown);
		}
	});
}