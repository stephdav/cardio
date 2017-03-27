$(document).ready(function() {
	initKanban();
});

function initKanban() {
	fnProjectKanban(updateBurnup);
	fnGetUsers(loadKanbanUsers);
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
		content += '<h4 class="list-group-item-heading">#' + story.id;
		if (story.assignedUser !== undefined) {
			content += '<span class="assigned pull-right">' + kanbanGetUserLogin(story.assignedUser) + '</span>';
		}
		content += '</h4>';
		content += '<p class="list-group-item-text">' + story.description + '</p>';
		content += '</a>';
	});
	$(listSelector).append(content);
}

var kanban_users = [];
function loadKanbanUsers(data, hv) {
	kanban_users = data;
}

function kanbanGetUserLogin(id) {
	var login = "";
	$.each(kanban_users, function(index, usr) {
		content += '<option value="' + usr.id + '"';	  
		if (usr.id == id) {
			login += usr.login;
			return true;
		}
	});
	return login;
}
