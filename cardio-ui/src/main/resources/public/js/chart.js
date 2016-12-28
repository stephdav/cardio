function displayBurndown(selector, data) {
	Highcharts.chart(selector, getBurndownChart(data));
}

function displayBurnup(selector, data) {
	Highcharts.chart(selector, getBurnupChart(data));
}

function getBurndownChart(burndown) {
	chart =  {
		exporting: { enabled: false},
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