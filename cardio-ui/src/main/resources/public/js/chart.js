function displayBurndown(selector, data) {
	Highcharts.chart(selector, getBurndownChart(data));
}

function displayBurnup(selector, data) {
	Highcharts.chart(selector, getBurnupChart(data));
}

function displayVelocities(selector, data) {
	Highcharts.chart(selector, getVelocitiesChart(data));
}

function getBurndownChart(burndown) {
	chart =  {
        title: { text: '' },
        xAxis: {
            categories: burndown.days
        },
        yAxis: { title: { text: '' } },
        series: []
    };
	
	$.each(burndown.series, function(index, serie) {
		if (serie.name == 'ideal') {
			serie.lineWidth = 2;
			serie.dashStyle = 'DashDot';
			serie.marker =  { enabled: false };
		}
		if (serie.name != 'done') {
		  chart.series.push(serie);
		}
	});
	return chart;
}

function getBurnupChart(burndown) {
	chart =  {
        title: { text: '' },
        xAxis: {
        	title: { text: 'sprint' },
        	labels: { formatter: function () { return '#' + this.value; } },
            categories: burndown.days
        },
        yAxis: { title: { text: '' } },
        series: []
    };
	
	$.each(burndown.series, function(index, serie) {
	  chart.series.push(serie);
	});
	return chart;
}

function getVelocitiesChart(data) {
	chart =  {
		chart: { type: 'column' },
        title: { text: '' },
        xAxis: {
        	title: { text: 'sprint' },
        	labels: { formatter: function () { return '#' + this.value; } },
            categories: data.names
        },
        yAxis: { title: { text: '' } },
        plotOptions: {
            spline: { lineWidth: 4, states: { hover: { lineWidth: 5 } }, marker: { enabled: false } }
        },
        series: [ { type: 'column', name: "velocity", data: data.data } ]
    };
	
	var nb = data.data.length;
	var s1 = { type: 'spline', color: '#FF0000', name: "low", data: [] }
	var s2 = { type: 'spline', color: '#0000FF', name: "average", data: [] }
	var s3 = { type: 'spline', color: '#00FF00', name: "high", data: [] }
	
	var idx = 0;
	for ( ; idx<nb; idx++) {
		s1.data.push(data.worst);
		s2.data.push(data.average);
		s3.data.push(data.best);
	}

	chart.series.push(s1);
	chart.series.push(s2);
	chart.series.push(s3);

	return chart;
}