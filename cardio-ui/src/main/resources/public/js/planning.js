$(document).ready(function() {
	initPlanning();
});

function initPlanning() {
	fnProjectGetDetails(updateVelocities);
	initReadyStories();
}

function updateVelocities(data, hv) {
	displayVelocities('burnup', data);
	$('#worst-sprints').text(data.worst);
	$('#average-sprints').text(data.average);
	$('#best-sprints').text(data.best);
	$('#over-commit').text(data.overCommit+"%");
}

function initReadyStories() {
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
	      { field: 'status', title: 'status', align: 'center', sortable: true },
	      { field: 'estimate', title: 'complexity', align: 'center', sortable: true, formatter: 'valueFormatter' },
	    ]
	});
}

function queryParams() {
	var options = $('#stories-table').bootstrapTable('getOptions');	
	var params = {};
	params['page'] = options.pageNumber;
	params['limit'] = options.pageSize;
	params['sortName'] = options.sortName;
	params['sortOrder'] = options.sortOrder;
	params['status'] = 'READY,PENDING';
	return params;
}