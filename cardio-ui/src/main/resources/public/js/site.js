$(document).ready(function() {
	initBackUrl();
	initSite();
});

var BACK_URL = "http://localhost:4567";

function initBackUrl() {
	var hrefArray = window.location.href.split( '/' );
	var url = "";
	var idx = 0;
	var pathLocation = hrefArray[idx]; 
	while (pathLocation != 'ui' && idx < hrefArray.length) {
		if (url != "") {
			url += "/";
		}
		url += pathLocation;
		idx++;
		pathLocation = hrefArray[idx];
	}
	BACK_URL = url;
}

function initSite() {
	getProjectName();
}

function getProjectName() {
	ajaxGet("/api/config/parameters/project.name", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			updateProjectName(data, hv);
		} else {
			log("Error getting parameter 'project.name': " + errorThrown);
		}
	});
}

function updateProjectName(data, hv) {
	$('#project-name').text(data.value);
}

// ===========================================================================

function getLastPathLocation() {
	var pathArray = window.location.pathname.split( '/' );
	var idx = pathArray.length-1;
	var pathLocation = pathArray[idx]; 
	while (pathLocation == '' && idx > 0) {
		idx--;
		pathLocation = pathArray[idx];
	}
	return pathLocation;
}

//===========================================================================

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
		fnHandle(jqXHR.responseText, hv, errorThrown);
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
