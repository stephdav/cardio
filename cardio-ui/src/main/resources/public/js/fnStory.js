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

function fnPatchStory(id, field, value) {
	var payload = {};
	ajaxPatch("/api/stories/" + id + "?" + field + "=" + value, payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Story " + hv.location + " updated");
			refresh();
		} else {
			log("Error updating story : " + errorThrown);
		}
	});
}