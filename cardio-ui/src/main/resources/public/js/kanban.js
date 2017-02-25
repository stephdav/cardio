$(document).ready(function() {
	initKanban();
});

function initKanban() {
	fnProjectKanban(updateBurnup);
}

function updateBurnup(data, hv) {
	updateCategory(data.todo, '#stories-ready', '#story-to-do');
	updateCategory(data.pending, '#stories-pending', '#story-pending');
	updateCategory(data.done, '#stories-done', '#story-done');
}

function updateCategory(stories, listSelector, countSelector) {
	$(countSelector).text(stories.length);

	$(listSelector).empty();
	content = '';
	$.each(stories, function(index, story) {
		content += '<a href="#" class="list-group-item">';
		content += '<h4 class="list-group-item-heading">#' + story.id + '</h4>';
		content += '<p class="list-group-item-text">' + story.description + '</p>';
		content += '</a>';
	});
	$(listSelector).append(content);
}

