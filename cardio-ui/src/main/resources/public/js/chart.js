function displayChart(selector, burndown) {
	Highcharts.chart(selector, getBurndownChart(burndown));
}

function getBurndownChart(burndown) {
	chart =  {
        title: { text: 'Burndown' },
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