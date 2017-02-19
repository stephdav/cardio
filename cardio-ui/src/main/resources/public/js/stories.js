$(document).ready(function() {
	initActivities();
});

function initActivities() {
	$('#addStory').on('click', function(e) {
		e.stopPropagation();
		createStory($('#storyDesc').val(), $('#storyStatus').val());
	});

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
	      { field: 'id', title: '#', align: 'center' },
	      { field: 'description', title: 'description' },
	      { field: 'status', title: 'status', align: 'center', sortable: true, searchable: true, formatter: 'selectFormatter' }
	    ]
	});
	
	$('#stories-table').on('load-success.bs.table', function (e, data) {
		$('#stories-count').text(data.total);
	});
	
	$('#stories-table').on('change', 'select',function (e) {
		patchActivityStatus($(this).data("id"), this.value);
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
  content = '<div class="form-group-sm">'
  content += '<select data-id="' + row.id + '" class="form-control">';
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

function patchActivityStatus(id, status) {
	var payload = {id: id, status: status};
	ajaxPatch("/api/stories/" + id, payload, function(data, hv, errorThrown) {
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

