$(document).ready(function() {
	initStory();
});

function initStory() {
	var storyId = getLastPathLocation();
	getStory(storyId);
	
	$('#updateStory').on('click', function(e) {
		e.stopPropagation();
		updateStory($('#storyId').text(), $('#storyDescription').val(), $('#storyStatus').val(), $('#storyEstimate').val());
	});

	$('#deleteStory').on('click', function(e) {
		e.stopPropagation();
		deleteStory($('#storyId').text());
	});
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
}

function updateStory(id, description, status, estimate) {
	var payload = {id: id, description: description, status: status, estimate: estimate};
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