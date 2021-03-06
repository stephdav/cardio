function displayBurndown(selector, data) {
	Highcharts.chart(selector, getBurndownChart(data));
}

function displayBurnup(selector, data) {
	Highcharts.chart(selector, getBurnupChart(data));
}

function displayVelocities(selector, data) {
	Highcharts.chart(selector, getVelocitiesChart(data));
}

function getBurndownChart(data) {
	var details = data.details;
	chart =  {
        title: { text: '' },
        xAxis: {
            categories: details.days
        },
        yAxis: { title: { text: '' } },
        series: []
    };

	var serieIdeal = { name: 'ideal', data: details.ideal, lineWidth:2, dashStyle: 'DashDot', marker: { enabled: false } };
    chart.series.push(serieIdeal);

    var serieReal = { name: 'real', data: details.left};
    chart.series.push(serieReal);

    return chart;
}

function getBurnupChart(data) {
	chart =  {
        title: { text: '' },
        xAxis: {
        	title: { text: 'sprint' },
        	labels: { formatter: function () { return '#' + this.value; } },
            categories: data.sprints
        },
        yAxis: { title: { text: '' } },
        series: []
    };

    var serieBurnup = { name: 'done', data: data.burnup};
    chart.series.push(serieBurnup);

	return chart;
}

function getVelocitiesChart(data) {
	chart =  {
		chart: { type: 'column' },
        title: { text: '' },
        xAxis: {
        	title: { text: 'sprint' },
        	labels: { formatter: function () { return '#' + this.value; } },
            categories: data.sprintsSample
        },
        yAxis: { title: { text: '' } },
        plotOptions: {
            spline: { lineWidth: 4, states: { hover: { lineWidth: 5 } }, marker: { enabled: false } }
        },
        series: [ { type: 'column', name: "velocity", data: data.done } ]
    };
	
	var s1 = { type: 'spline', color: '#FF0000', name: "low", data: [] }
	var s2 = { type: 'spline', color: '#0000FF', name: "average", data: [] }
	var s3 = { type: 'spline', color: '#00FF00', name: "high", data: [] }

	if (data.done != undefined) {
		var nb = data.done.length;
		var idx = 0;
		for ( ; idx<nb; idx++) {
			s1.data.push(data.worst);
			s2.data.push(data.average);
			s3.data.push(data.best);
		}
	}

	chart.series.push(s1);
	chart.series.push(s2);
	chart.series.push(s3);

	return chart;
}