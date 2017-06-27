function fnUserCount(callback) {
	ajaxGet("/api/users/count", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error: " + errorThrown);
		}
	});
}

function fnGetUsers(callback) {
	ajaxGet("/api/users", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204 || hv.status == 206) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error: " + errorThrown);
		}
	});
}

function fnGetUsersUnlimited(callback) {
	ajaxGet("/api/users?limit=-1", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error: " + errorThrown);
		}
	});
}

function fnGetUser(id, callback) {
	ajaxGet("/api/users/" + id, function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 204) {
			if (callback != undefined) {
				callback(data, hv);
			}
		} else {
			log("Error: " + errorThrown);
		}
	});
}