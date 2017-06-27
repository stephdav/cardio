$(document).ready(function() {
	initActivities();
});

function initActivities() {
	$('#addStory').on('click', function(e) {
		e.stopPropagation();
		createStory($('#storyDesc').val(), $('#storyStatus').val());
	});

	$('#exportStories').on('click', function(e) {
		e.stopPropagation();
		downloadExport('/api/stories/export');
	});

	fnGetUsersUnlimited(loadUsers);
}

var stories_users = [];
function loadUsers(data, hv) {
	stories_users = data;
	initActivitiesTable();	
}

function initActivitiesTable() {
	$('#stories-table').bootstrapTable({
		pagination: true,
		url: '/api/stories?bootstrap',
		sidePagination: 'server',
		queryParamsType: 'page',
		queryParams: 'queryParams',
		pageNumber: 1, pageSize: 10, pageList: [10, 25, 50],
	    columns: [
	      { field: 'id', title: '#', align: 'center', sortable: true },
	      { field: 'description', title: 'description' },
	      { field: 'status', title: 'status', align: 'center', sortable: true, searchable: true, formatter: 'selectFormatter' },
	      //{ field: 'contribution', title: 'value', align: 'center', formatter: 'valueFormatter' },
	      { field: 'estimate', title: 'complexity', align: 'center', formatter: 'complexityFormatter', sortable: true },
	      { field: 'assignedUser', title: 'assigned', align: 'center', formatter: 'userFormatter' }
	    ]
	});
	
	$('#stories-table').on('load-success.bs.table', function (e, data) {
		$('#stories-count').text(data.total);
	});

	$('#stories-table').on('click-cell.bs.table', function (e, field, value, row, $element) {
		if (field == 'id' || field == 'description') {
			window.location = "../story/" + row.id;
		}
	});
	
	$('#stories-table').on('change', 'select.change-status',function (e) {
		e.stopPropagation();
		patchStory($(this).data("id"), "status", this.value);
	});

	$('#stories-table').on('change', 'select.change-complexity',function (e) {
		e.stopPropagation();
		patchStory($(this).data("id"), "estimate", this.value);
	});
	
	$('#stories-table').on('change', 'select.change-assigned',function (e) {
		e.stopPropagation();
		patchStory($(this).data("id"), "assignedUser", this.value);
	});

	$('#typeFilter').on('change', function(e) {
		e.stopPropagation();
		refresh();
	});
	$('#statusFilter').on('change', function(e) {
		e.stopPropagation();
		refresh();
	});
}

function selectFormatter(value, row) {
  content = '<div class="form-group-sm">';
  content += '<select data-id="' + row.id + '" class="form-control change-status">';
  if (value=='DRAFT'){
	  content += '<option selected>DRAFT</option>';	  
  } else {
	  content += '<option>DRAFT</option>';
  }
  if (value=='READY'){
	  content += '<option selected>READY</option>';
  } else {
	  content += '<option>READY</option>';
  }
  if (value=='PENDING'){
	  content += '<option selected>PENDING</option>';
  } else {
	  content += '<option>PENDING</option>';
  }
  if (value=='DONE'){
	  content += '<option selected>DONE</option>';
  } else {
	  content += '<option>DONE</option>';
  }
  content += '</select></div>';
  return content;
}

function valueFormatter(value, row) {
	content = '';
	if (value!=-1){
		content = value;
	}
	return content;
}

function complexityFormatter(value, row) {
	content = '<div class="form-group-sm">';
	content += '<select data-id="' + row.id + '" class="form-control change-complexity">';
	var values = [0, 1, 2, 3, 5, 8, 13, 21];
	if (value==-1){
		content += '<option value="-1" selected></option>';
	} else {
		content += '<option value="-1"></option>';
	}
	$.each(values, function(key, val) {
		content += '<option value="' + val + '"';
		if (value==val) {
			content += ' selected';
		}
		content += '>' + val + '</option>';
	});
	content += '</select></div>';
	return content;
}

function userFormatter(value, row) {
	content = '<div class="form-group-sm">';
	content += '<select data-id="' + row.id + '" class="form-control change-assigned">';
	if (value==-1){
		content += '<option value="-1" selected></option>';	  
	} else {
		content += '<option value="-1"></option>';	  
	}
	
	$.each(stories_users, function(index, usr) {
		content += '<option value="' + usr.id + '"';	  
		if (usr.id==value) {
			content += '" selected';
		}
		content += '>' + usr.login + '</option>';	  
	});
	content += '</select></div>';
	return content;
}

function refresh() {
	$('#stories-table').bootstrapTable('refresh');
}

function createStory(description, status) {
	var payload = {description: description, status: status};
	ajaxPost("/api/stories", payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Story " + hv.location + " created");
			// reset form
			$('#storyDesc').val('');
			$('#storyStatus').val('READY');
			$('#errors').text('');
			$('.form-error').hide();
			// reload list
			refresh();	
		} else {
			log("Error creating story : " + errorThrown);
			$('#errors').text(data);
			$('.form-error').show();
		}
	});
}

function patchStory(id, field, value) {
	var payload = {};
	ajaxPatch("/api/stories/" + id + "?" + field + "=" + value, payload, function(data, hv, errorThrown) {
		if (hv.status == 201 || hv.status == 200) {
			log("Story " + hv.location + " updated");
			refresh();
		} else {
			log("Error updating story : " + errorThrown);
		}
	});
}

function queryParams() {
	var options = $('#stories-table').bootstrapTable('getOptions');
	
	var params = {};
	params['page'] = options.pageNumber;
	params['limit'] = options.pageSize;
	params['sortName'] = options.sortName;
	params['sortOrder'] = options.sortOrder;
	
	var status = $('#statusFilter').val();
	if ( status != undefined && status != '') {
		params['status'] = status;
	}
	return params;
}

