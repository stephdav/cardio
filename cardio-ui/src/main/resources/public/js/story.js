$(document).ready(function() {
	initStory();
});

function initStory() {
	var storyId = getLastPathLocation();
	getStory(storyId);
}

function getStory(id) {
	fnStoryGet(id, callbackStory);
}

function callbackStory(story, hv) {
	log("story: "+JSON.stringify(story));
	$('#storyId').text('#' + story.id);
	$('#storyDescription').val(story.description);
	$('#storyStatus option').prop('selected', false);
	$('#storyStatus option[value="' + story.status+ '"]').prop('selected', true);
	$('#storyEstimate option').prop('selected', false);
	$('#storyEstimate option[value="' + story.estimate+ '"]').prop('selected', true);
}