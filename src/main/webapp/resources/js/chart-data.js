var randomScalingFactor = function(){ return Math.round(Math.random()*1000000)};
	
	var lineChartData = {
		labels : ["HR","KK","CN","HR","DL","TN","JK","AP","UP","RJ","BR","UE","UW","WB","KO","GJ","AS","NE","PB","MU","MH","MP"],
		datasets : [
			{
				label: "My First dataset",
				fillColor : "rgba(220,220,220,0.2)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(220,220,220,1)",
				data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),
					randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
			},
			{
				label: "My Second dataset",
				fillColor : "rgba(48, 164, 255, 0.2)",
				strokeColor : "rgba(48, 164, 255, 1)",
				pointColor : "rgba(48, 164, 255, 1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(48, 164, 255, 1)",
				data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),
					randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
			}
		]

	}
		
	var barChartData = {
		labels : ["HR","KK","CN","HR","DL","TN","JK","AP","UP","RJ","BR","UE","UW","WB","KO","GJ","AS","NE","PB","MU","MH","MP"],
		datasets : [
			{
				fillColor : "rgba(102, 140, 255,1)",
				highlightFill: "rgba(51, 51, 255,1)",
				highlightStroke: "rrgba(51, 51, 255,1)",
				data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),
				randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
			},
			{
				fillColor : "rgba(148, 184, 184, 1)",
				highlightFill : "rgba(92, 138, 138, 1)",
				highlightStroke : "rgba(92, 138, 138, 1)",
				data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),
				randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
			},
			
		]

	}
	
	

	var pieData = [
			{
				value: 300,
				color:"#30a5ff",
				highlight: "#62b9fb",
				label: "2G"
			},
			{
				value: 502,
				color: "#ffb53e",
				highlight: "#fac878",
				label: "4G"
			},
//			{
//				value: 1000,
//				color: "#1ebfae",
//				highlight: "#3cdfce",
//				label: "Site"
//			},
//			{
//				value: 500,
//				color: "#f9243f",
//				highlight: "#f6495f",
//				label: "Node"
//			}

		];
			
	