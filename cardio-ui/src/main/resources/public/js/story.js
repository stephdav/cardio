$(document).ready(function() {
	initStory();
});

var _storyAssignedUser = 0;
var _storySprint = 0;

function initStory() {
	var storyId = getLastPathLocation();
	fnStoryGet(storyId, callbackStory);

	$('#updateStory').on('click', function(e) {
		e.stopPropagation();
		updateStory($('#storyId').text(), $('#storyDescription').val(), $('#storyStatus').val(), $('#storyValue').val(), $('#storyEstimate').val(), $('#storySprint').val(), $('#storyUser').val());
	});

	$('#deleteStory').on('click', function(e) {
		e.stopPropagation();
		deleteStory($('#storyId').text());
	});	
}

function storyLoadSprints(data, hv) {
	$('#storySprint').empty();
	var content = '<option value="0"></option>';
	$.each(data, function(index, sp) {
		content += '<option value="' + sp.id + '">' + sp.name + '</option>';
	});
	$('#storySprint').append(content);
	$('#storySprint').val(_storySprint);
}

function storyLoadUsers(data, hv) {
	$('#storyUser').empty();
	var content = '<option value="0"></option>';
	$.each(data, function(index, usr) {
		content += '<option value="' + usr.id + '">' + usr.login + '</option>';
	});
	$('#storyUser').append(content);
	$('#storyUser').val(_storyAssignedUser);
}

function callbackStory(story, hv) {
	$('#storyId').text(story.id);
	$('#storyDescription').val(story.description);
	$('#storyStatus option').prop('selected', false);
	$('#storyStatus option[value="' + story.status+ '"]').prop('selected', true);
	$('#storyEstimate option').prop('selected', false);
	$('#storyEstimate option[value="' + story.estimate+ '"]').prop('selected', true);
	if (story.contribution!=-1) {
		$('#storyValue').val(story.contribution);
	} else {
		$('#storyValue').val("");		
	}
	
	_storyAssignedUser = story.assignedUser;
	_storySprint = story.sprint;

	fnGetSprintsUnlimited(storyLoadSprints);
	fnGetUsersUnlimited(storyLoadUsers);
}

function updateStory(id, description, status, contributed, estimate, sprint, assigned) {
	var contribution = -1;
	if (contributed != "") {
		contribution = contributed;
	}
	var payload = {id: id, description: description, status: status, contribution: contribution, estimate: estimate, sprint: sprint, assignedUser: assigned};
	ajaxPost("/api/stories/"+id, payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Story " + hv.location + " updated");
		} else {
			log("Error updating story : " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}

function deleteStory(id) {
	ajaxDelete("/api/stories/"+id, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Story deleted");
			window.location.replace("../../stories");
		} else {
			log("Error deleting story : " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}