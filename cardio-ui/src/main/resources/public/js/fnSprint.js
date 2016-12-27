function fnSprintGet(id, callback) {
	ajaxGet("/api/sprints/" + id, function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error getting sprint '" + id + "': " + errorThrown);
		}
	});
}

function fnSprintGetData(id, callback, selector) {
	ajaxGet("/api/sprints/" + id + "/data", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error getting sprint '" + id + "' data: " + errorThrown);
		}
	});
}

function fnSprintUpdate(id, name, startdate, enddate, goal, commitment, callback) {
	var payload = {id: id, name: name, startDate: startdate, endDate: enddate, goal: goal, commitment: commitment};
	ajaxPost("/api/sprints/"+id, payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Sprint " + hv.location + " updated");
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error updating sprint '" + id + "': " + errorThrown);
		}
	});
}