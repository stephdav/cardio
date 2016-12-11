$(document).ready(function() {
	initSite();
});

function initSite() {
	getProjectName();
}

var BACK_URL = "http://localhost:4567";


function getProjectName() {
	ajaxGet("/api/config/parameters/project.name", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			updateUsers(data, hv);
		} else {
			log("Error getting parameter 'project.name': " + errorThrown);
		}
	});
}

function updateUsers(data, hv) {
	if (hv.status == 200 || hv.status == 206) {
		$('#project-name').text(data.value);
	}
}

// ===========================================================================

function handleAjaxError(jqXHR, status, errorThrown) {
	log("Ajax error (jqXHR.status=" + jqXHR.status + " - status=" + status + " - errorThrown=" + errorThrown+")");
}

function log(message) {
	if (console && console.log) {
		console.log(message);
	}
}

function parseStatusAndHeader(jqXHR) {
	var hv = {};
	hv.status = jqXHR.status;
	hv.acceptRange = jqXHR.getResponseHeader('Accept-Range');
	hv.contentRange = jqXHR.getResponseHeader('Content-Range');
	hv.location = jqXHR.getResponseHeader('Location');
	return hv;
}

function ajaxPost(url, payload, fnHandle) {
	log('POST ' + url + ' with payload ' + JSON.stringify(payload));
	$.ajax({
	    url: BACK_URL + url,
	    type: "POST",
	    dataType: "json",
	    data: JSON.stringify(payload),
	    contentType: "application/json"
	}).done(function(data, textStatus, jqXHR) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle(data, hv, "no error");
	}).fail(function(jqXHR, status, errorThrown) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle("", hv, errorThrown);
	});
}

function ajaxGet(url, fnHandle) {
	log('GET ' + url);
	$.ajax({
		url : BACK_URL + url
	}).done(function(data, textStatus, jqXHR) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle(data, hv);
	}).fail(function(jqXHR, status, errorThrown) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle("", hv, errorThrown);
	});
}

function ajaxDelete(url, fnHandle) {
	log('DELETE ' + url);
	$.ajax({
	    url: BACK_URL + url,
	    type: "DELETE",
	}).done(function(data, textStatus, jqXHR) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle(data, hv, "no error");
	}).fail(function(jqXHR, status, errorThrown) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle("", hv, errorThrown);
	});
}

function ajaxPatch(url, payload, fnHandle) {
	log('PATCH ' + url + ' with payload ' + JSON.stringify(payload));
	$.ajax({
	    url: BACK_URL + url,
	    type: "PATCH",
	    dataType: "json",
	    data: JSON.stringify(payload),
	    contentType: "application/json"
	}).done(function(data, textStatus, jqXHR) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle(data, hv, "no error");
	}).fail(function(jqXHR, status, errorThrown) {
		var hv = parseStatusAndHeader(jqXHR);
		fnHandle("", hv, errorThrown);
	});
}
