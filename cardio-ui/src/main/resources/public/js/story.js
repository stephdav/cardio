$(document).ready(function() {
	initStory();
});

function initStory() {
	var storyId = getLastPathLocation();
	getStory(storyId);

	fnGetUsersUnlimited(storyLoadUsers);

	$('#updateStory').on('click', function(e) {
		e.stopPropagation();
		updateStory($('#storyId').text(), $('#storyDescription').val(), $('#storyStatus').val(), $('#storyValue').val(), $('#storyEstimate').val());
	});

	$('#deleteStory').on('click', function(e) {
		e.stopPropagation();
		deleteStory($('#storyId').text());
	});	
}

function storyLoadUsers(data, hv) {
	$('#storyUser').empty();
	var content = '<option value="0"></option>';
	$.each(data, function(index, usr) {
		content += '<option value="' + usr.id + '">' + usr.login + '</option>';
	});
	$('#storyUser').append(content);
}

function getStory(id) {
	fnStoryGet(id, callbackStory);
}

function callbackStory(story, hv) {
	$('#storyId').text(story.id);
	$('#storyDescription').val(story.description);
	$('#storyStatus option').prop('selected', false);
	$('#storyStatus option[value="' + story.status+ '"]').prop('selected', true);
	$('#storyEstimate option').prop('selected', false);
	$('#storyEstimate option[value="' + story.estimate+ '"]').prop('selected', true);
	$('#storyValue').val(story.contribution);

	$('#storySprint').val(story.sprint);
}

function updateStory(id, description, status, contribution, estimate) {
	var payload = {id: id, description: description, status: status, contribution: contribution, estimate: estimate};
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