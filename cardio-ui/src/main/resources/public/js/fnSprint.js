function fnSprintCount(callback) {
	ajaxGet("/api/sprints/count", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error: " + errorThrown);
		}
	});
}

function fnGetSprintsUnlimited(callback) {
	ajaxGet("/api/sprints?limit=-1", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error: " + errorThrown);
		}
	});
}

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

function fnSprintUpdate(id, name, startdate, enddate, goal, commitment, callback) {
	var payload = {id: id, name: name, startDate: startdate, endDate: enddate, goal: goal, commitment: commitment};
	ajaxPatch("/api/sprints/"+id, payload, function(data, hv, errorThrown) {
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

function fnSprintGetData(id, callback) {
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

function fnSprintUpdateData(id, data, callback) {
	var payload = { data: {} };
	$.each(data, function(index, day) {
		payload.data[day.key] = day.val;
	});
	
	ajaxPost("/api/sprints/" + id + "/data", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Sprint " + hv.location + " data updated");
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error updating sprint data : " + errorThrown);
		}
	});
}

function fnSprintFindByDay(day, callback) {
	var filter = "";
	if (day != undefined && day != '') {
		filter += "?day=" + day;
	}
	ajaxGet("/api/sprints" + filter, function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			callback(data, hv);
		} else {
			log("Error getting sprints: " + errorThrown);
		}
	});
}
