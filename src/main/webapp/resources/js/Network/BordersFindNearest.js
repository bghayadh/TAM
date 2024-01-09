function bordersFindNearest(startLongPoint,startLatPoint,endLongPoint,endLatPoint) {
	
	if(startLongPoint != ''){	
			
		if(startLatPoint !='') {
			p1 = new google.maps.LatLng(startLatPoint,startLongPoint);
		}
		else {
			p1 = new google.maps.LatLng(-5,startLongPoint);
		}
		
		if(endLatPoint !='') {
			p2 = new google.maps.LatLng(endLatPoint,startLongPoint);
		}
		else {
			p2 = new google.maps.LatLng(5,startLongPoint);
		}
		
		startLongPath =[p1, p2];
		drawLine("#FF0000",startLongPath);
	}
	
	if(startLatPoint != ''){		
		
		if(startLongPoint !='') {
			p1 = new google.maps.LatLng(startLatPoint,startLongPoint);
		}
		else {
			p1 = new google.maps.LatLng(startLatPoint,33.5);
		}
		
		if(endLongPoint !='') {
			p2 = new google.maps.LatLng(startLatPoint,endLongPoint);
		}
		else {
			p2 = new google.maps.LatLng(startLatPoint,42);
		}
		
		startLatPath =[p1, p2];
		drawLine("#FF0000",startLatPath);
			
	}
	if(endLongPoint != ''){		
						
		if(endLatPoint !='') {
			p1 = new google.maps.LatLng(endLatPoint,endLongPoint);
		}
		else {
			p1 = new google.maps.LatLng(5,endLongPoint);
		}
		
		if(startLatPoint !='') {
			p2 = new google.maps.LatLng(startLatPoint,endLongPoint);
		}
		else {
			p2 = new google.maps.LatLng(-5,endLongPoint);
		}
		
		endLongPath =[p1, p2];
		drawLine("#006400",endLongPath);
		
	}
	 if(endLatPoint != ''){		
		
		if(endLongPoint !='') {
			p1 = new google.maps.LatLng(endLatPoint,endLongPoint);
		}
		else {
			p1 = new google.maps.LatLng(endLatPoint,42);
		}
		
		if(startLongPoint !='') {
			p2 = new google.maps.LatLng(endLatPoint,startLongPoint);
		}
		else {
			p2 = new google.maps.LatLng(endLatPoint,33.5);
		}
		
		endLatPath =[p1, p2];
		drawLine("#006400",endLatPath);			
	}
	
}