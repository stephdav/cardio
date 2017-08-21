$(document).ready(function() {
	initPlanning();
});

function initPlanning() {
	fnProjectGetDetails(updateVelocities);
	fnGetSprintsUnlimited(planningLoadSprints);
}

var planning_sprints = [];
function planningLoadSprints(data, hv) {
	planning_sprints = data;
	initReadyStories();
}

function sprintFormatter(value, row) {
	content = '<div class="form-group-sm">';
	content += '<select data-id="' + row.id + '" class="form-control change-sprint">';
	if (value==0){
		content += '<option value="0" selected></option>';	  
	} else {
		content += '<option value="0"></option>';	  
	}
	
	$.each(planning_sprints, function(index, sp) {
		content += '<option value="' + sp.id + '"';	  
		if (sp.id==value) {
			content += '" selected';
		}
		content += '>' + sp.name + '</option>';	  
	});
	content += '</select></div>';
	return content;
}

function updateVelocities(data, hv) {
	displayVelocities('burnup', data);
	$('#worst-sprints').text(data.worst);
	$('#average-sprints').text(data.average);
	$('#best-sprints').text(data.best);
	$('#over-commit').text(data.overCommit+"%");
}

function initReadyStories() {
	$('#planning-table').bootstrapTable({
		pagination: true,
		url: '/api/stories?bootstrap',
		sidePagination: 'server',
		queryParamsType: 'page',
		queryParams: 'queryParams',
		pageNumber: 1, pageSize: 10, pageList: [10, 25, 50],
	    columns: [
	      { field: 'id', title: '#', align: 'center', sortable: true },
	      { field: 'description', title: 'description' },
	      { field: 'status', title: 'status', align: 'center', sortable: true },
	      { field: 'contribution', title: 'value', align: 'center', formatter: 'valueFormatter', sortable: true },
	      { field: 'estimate', title: 'complexity', align: 'center', sortable: true, formatter: 'valueFormatter' },
	      { field: 'sprint', title: 'sprint', align: 'center', formatter: 'sprintFormatter' }
	    ]
	});
	
	$('#planning-table').on('change', 'select.change-sprint',function (e) {
		e.stopPropagation();
		fnPatchStory($(this).data("id"), "sprint", this.value);
	});

}

function queryParams() {
	var options = $('#planning-table').bootstrapTable('getOptions');	
	var params = {};
	params['page'] = options.pageNumber;
	params['limit'] = options.pageSize;
	params['sortName'] = options.sortName;
	params['sortOrder'] = options.sortOrder;
	params['status'] = 'READY,PENDING';
	return params;
}