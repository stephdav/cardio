function fnProjectGetDetails(callback) {
	ajaxGet("/api/project/data", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error getting project details: " + errorThrown);
		}
	});
}

function fnProjectKanban(callback) {
	ajaxGet("/api/project/kanban", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error getting project kanban: " + errorThrown);
		}
	});
}