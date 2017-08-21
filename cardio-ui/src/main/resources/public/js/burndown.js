$(document).ready(function() {
	initBurndown();
});

function initBurndown() {
	fnSprintFindByDay('now', updateHomeView);
}

function updateHomeView(sprints, hv) {
	if (hv.status == 204) {
		initMonitoringTable(0);
	} else if (sprints != undefined && sprints[0] != undefined) {
		var sprint = sprints[0];
		initMonitoringTable(sprint.id);
	}
}

function initMonitoringTable(id) {
	$('#monitor-table').bootstrapTable({
		url: '/api/project/burndown',
	    columns: [
	      { field: 'id', title: '#', align: 'center', sortable: true },
	      { field: 'description', title: 'description' },
	      { field: 'contribution', title: 'value', align: 'center', formatter: 'valueFormatter', sortable: true },
	      { field: 'status', title: 'status', align: 'center', sortable: true, formatter: 'statusFormatter' },
	      { field: 'estimate', title: 'complexity', align: 'center', formatter: 'complexityFormatter', sortable: true },
	      { field: 'assignedUser', title: 'captain', align: 'center' }
	    ]
	});
}