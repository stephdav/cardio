$(document).ready(function() {
	initTasks();
});

function initTasks() {
	$('#addTask').on('click', function(e) {
		e.stopPropagation();
		createTask($('#taskDescription').val());
	});
	$('#tasklist').on('click', '.deleteTask', function(e) {
		e.stopPropagation();
		var cb = $(this).closest('li').find('input[type="checkbox"]');
		deleteTask(cb.val());
	});
	$('#tasklist').on('click', 'input[type="checkbox"]', function(e) {
		e.stopPropagation();
		var cb = $(this).closest('li').find('input[type="checkbox"]');
		updateTaskStatus(cb.val(), $(cb).is(':checked'))
	});
	getTasks();
}

function getTasks() {
	ajaxGet("/api/tasks", function(data, hv, errorThrown) {
		if (hv.status == 200 || hv.status == 206) {
			updateTasks(data, hv);
		} else {
			log("Error getting tasks : " + errorThrown);
		}
	});
}

function createTask(taskDesc) {
	var payload = {description: taskDesc};
	ajaxPost("/api/tasks", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Task " + hv.location + " created");
			getTasks();	
		} else {
			log("Error creating task : " + errorThrown);
		}
	});
}

function deleteTask(taskId) {
	ajaxDelete("/api/tasks/" + taskId, function(data, hv, errorThrown) {
		if (hv.status == 200) {
			log("Task " + taskId + " deleted");
			getTasks();	
		} else {
			log("Error deleting task : " + errorThrown);
		}
	});
}

function updateTaskStatus(taskId, status) {
	var payload = {status: 'TODO'};
	if (status) { payload.status = 'DONE'; }
	ajaxPatch("/api/tasks/" + taskId, payload, function(data, hv, errorThrown) {
		if (hv.status == 200) {
			log("Task " + taskId + " updated");
			getTasks();	
		} else {
			log("Error updating task : " + errorThrown);
		}
	});
}

function updateTasks(data, hv) {
	$('#tasklist').empty();
	if (hv.status == 200 || hv.status == 206) {
		var content = "";
		$.each(data, function(index, task) {
			content += '<li class="list-group-item">';
			content += '<div class="clearfix">';
			content += '<label>';
			if (task.status == 'DONE'){
				content += '<input type="checkbox" value="' + task.id + '" checked>';
			} else {
				content += '<input type="checkbox" value="' + task.id + '">';
			}
			content += ' #' + task.id + ' - ' + task.description + '</label>';
			content += '<button class="deleteTask btn btn-xs btn-default pull-right"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
			content += '</div></li>';
		});
		$('#tasklist').append(content);
	}
}
