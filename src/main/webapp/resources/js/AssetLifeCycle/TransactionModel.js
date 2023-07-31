
function openTransactionModel(element) {	


$('input[name="Site"]'). click(function(){
		if($(this). is(":checked")){
			 console.log("Checkbox is checked." );
			 $('input[name="siteId"]').removeAttr("disabled");
			 $('input[name="siteNameTrans"]').removeAttr("disabled");
			
		     document.getElementById("siteId").checked = true;
			 document.getElementById("siteNameTrans").checked = true;
			
		}
		else if($(this). is(":not(:checked)")){
			 $('input[name="siteId"]').attr("disabled", true);
			 $('input[name="siteNameTrans"]').attr("disabled", true);
			
		     document.getElementById("siteId").checked = false;
			 document.getElementById("siteNameTrans").checked = false;
			 document.getElementById("transaction").checked = false;
		  console.log("Checkbox site is unchecked." );
		} 
		
	});
	
	$('input[name="Node"]'). click(function(){
		if($(this). is(":checked")){
			 console.log("Checkbox is checked." );
			 $('input[name="nodeIdTr"]').removeAttr("disabled");
			 $('input[name="nodeNameTr"]').removeAttr("disabled");
			 $('input[name="nodeSNo"]').removeAttr("disabled");
			 $('input[name="nodeTypeTr"]').removeAttr("disabled");
			 $('input[name="nodeTransType"]').removeAttr("disabled");
			
			 document.getElementById("nodeIdTr").checked = true;
			 document.getElementById("nodeNameTr").checked = true;
			 document.getElementById("nodeSNo").checked = true;
			 document.getElementById("nodeTypeTr").checked = true;
			 document.getElementById("nodeTransType").checked = true;
				
		}
		else if($(this). is(":not(:checked)")){
			$('input[name="nodeIdTr"]').attr("disabled", true);
			 $('input[name="nodeNameTr"]').attr("disabled", true);
			 $('input[name="nodeSNo"]').attr("disabled", true);
			 $('input[name="nodeTypeTr"]').attr("disabled", true);
			 $('input[name="nodeTransType"]').attr("disabled", true);
			
			 document.getElementById("nodeIdTr").checked = false;
			 document.getElementById("nodeNameTr").checked = false;
			 document.getElementById("nodeSNo").checked = false;
			 document.getElementById("nodeTypeTr").checked = false;
			 document.getElementById("nodeTransType").checked = false;
			 
			
		
		  console.log("Checkbox Node is unchecked." );
		} 
		
	});
	
	$('input[name="Cell"]'). click(function(){
		if($(this). is(":checked")){
			 console.log("Checkbox is checked." );
			 $('input[name="cellIdTr"]').removeAttr("disabled");
			 $('input[name="cellNameTr"]').removeAttr("disabled");
			 $('input[name="cellTransType"]').removeAttr("disabled");
			 
			 document.getElementById("cellIdTr").checked = true;
		     document.getElementById("cellNameTr").checked = true;
			 document.getElementById("cellTransType").checked = true;
			 
			 document.getElementById("siteId").checked = true;
		     document.getElementById("siteNameTrans").checked = true;
				
		     	
			 document.getElementById("nodeIdTr").checked = true;
			 document.getElementById("nodeNameTr").checked = true;
			
					
		}
		else if($(this). is(":not(:checked)")){
			
			
			 $('input[name="cellIdTr"]').attr("disabled", true);
			 $('input[name="cellNameTr"]').attr("disabled", true);
			 $('input[name="cellTransType"]').attr("disabled", true);
			 
			 document.getElementById("cellIdTr").checked = false;
		     document.getElementById("cellNameTr").checked = false;
			 document.getElementById("cellTransType").checked = false;
		
		
		  console.log("Checkbox cell is unchecked." );
		} 
		
	});

	
	$('input[name="Cabinet"]'). click(function(){
		if($(this). is(":checked")){
			 console.log("Checkbox is checked." );
		    $('input[name="cabinetPosition"]').removeAttr("disabled");
		    $('input[name="cabinetModel"]').removeAttr("disabled");
		    $('input[name="cabinetSNo"]').removeAttr("disabled");
		    $('input[name="cabinetTransType"]').removeAttr("disabled");
		   
		    document.getElementById("cabinetPosition").checked = true;
			document.getElementById("cabinetModel").checked = true;
			document.getElementById("cabinetSNo").checked = true;
			document.getElementById("cabinetTransType").checked = true;
			
			 
		
		}
        else if($(this). is(":not(:checked)")){
			
	        $('input[name="cabinetPosition"]').attr("disabled", true);
            $('input[name="cabinetModel"]').attr("disabled", true);
            $('input[name="cabinetSNo"]').attr("disabled", true);
            $('input[name="cabinetTransType"]').attr("disabled", true);
   
            document.getElementById("cabinetPosition").checked = false;
	        document.getElementById("cabinetModel").checked = false;
	        document.getElementById("cabinetSNo").checked = false;
	        document.getElementById("cabinetTransType").checked = false;
			 
		  console.log("Checkbox cabinet is unchecked." );
		} 
		
	});
	
	$('input[name="Board"]'). click(function(){
		if($(this). is(":checked")){
			   console.log("Checkbox is checked." );
		    $('input[name="boardPosition"]').removeAttr("disabled");
		    $('input[name="boardModel"]').removeAttr("disabled");
		    $('input[name="boardSNo"]').removeAttr("disabled");
		    $('input[name="boardTransType"]').removeAttr("disabled");
		   
		    document.getElementById("boardPosition").checked = true;
			document.getElementById("boardModel").checked = true;
			document.getElementById("boardSNo").checked = true;
			document.getElementById("boardTransType").checked = true;
		}
        else if($(this). is(":not(:checked)")){
			
	       $('input[name="boardPosition"]').attr("disabled", true);
	       $('input[name="boardModel"]').attr("disabled", true);
	       $('input[name="boardSNo"]').attr("disabled", true);
	       $('input[name="boardTransType"]').attr("disabled", true);
	   
	       document.getElementById("boardPosition").checked = false;
		   document.getElementById("boardModel").checked = false;
		   document.getElementById("boardSNo").checked = false;
		   document.getElementById("boardTransType").checked = false;
			 
		  console.log("Checkbox board is unchecked." );
		} 
		
	});
	
	$('input[name="Antenna"]'). click(function(){
		if($(this). is(":checked")){
			    console.log("Checkbox is checked." );
		    $('input[name="antennaId"]').removeAttr("disabled");
		    $('input[name="antennaModel"]').removeAttr("disabled");
		    $('input[name="antennaSNo"]').removeAttr("disabled");
		    $('input[name="antennaTransType"]').removeAttr("disabled");
		   
		    
		    document.getElementById("antennaId").checked = true;
			document.getElementById("antennaModel").checked = true;
			document.getElementById("antennaSNo").checked = true;
			document.getElementById("antennaTransType").checked = true;
		}
		else if($(this). is(":not(:checked)")){
			 
		        $('input[name="antennaId"]').attr("disabled", true);
			    $('input[name="antennaModel"]').attr("disabled", true);
			    $('input[name="antennaSNo"]').attr("disabled", true);
			    $('input[name="antennaTransType"]').attr("disabled", true);
			   
			    
			    document.getElementById("antennaId").checked = false;
				document.getElementById("antennaModel").checked = false;
				document.getElementById("antennaSNo").checked = false;
				document.getElementById("antennaTransType").checked = false;

		      
				 
			  console.log("Checkbox antenna is unchecked." );
			} 
	});
	
	$('input[name="Host"]'). click(function(){
		if($(this). is(":checked")){
			  console.log("Checkbox is checked." );
		    $('input[name="hostVersion"]').removeAttr("disabled");
		    $('input[name="hostVersionTrans"]').removeAttr("disabled");
		    
		    document.getElementById("hostVersion").checked = true;
			document.getElementById("hostVersionTrans").checked = true;
		}
		else if($(this). is(":not(:checked)")){
		 
	        $('input[name="hostVersion"]').attr("disabled", true);
		    $('input[name="hostVersionTrans"]').attr("disabled", true);
		    
		    document.getElementById("hostVersion").checked = false;
			document.getElementById("hostVersionTrans").checked = false;
		    
		    console.log("Checkbox host is unchecked." );
		}
	});
	

	 $(document).ready(function() {
		 
		 
		// $("#transColumns").click(  function() {
			  
			  $('#myModal').modal('show');
			  $('#myModal').draggable(); 
			  
			    document.getElementById("Cell").checked = false;
				document.getElementById("Node").checked = false; 
				document.getElementById("Site").checked = false;
				document.getElementById("Cabinet").checked = false;
				document.getElementById("Board").checked = false;
				document.getElementById("Antenna").checked = false;
				document.getElementById("Host").checked = false;
			 
			    document.getElementById("siteId").checked = false;
				document.getElementById("siteNameTrans").checked = false;
				
				document.getElementById("cellIdTr").checked = false;
				document.getElementById("cellNameTr").checked = false;
				document.getElementById("cellTransType").checked = false;
				
				document.getElementById("nodeIdTr").checked = false;
				document.getElementById("nodeNameTr").checked = false;
				document.getElementById("nodeSNo").checked = false;
				document.getElementById("nodeTypeTr").checked = false;
				document.getElementById("nodeTransType").checked = false;
				
				document.getElementById("cabinetPosition").checked = false;
				document.getElementById("cabinetModel").checked = false;
				document.getElementById("cabinetSNo").checked = false;
				document.getElementById("cabinetTransType").checked = false;
				
				document.getElementById("boardPosition").checked = false;
				document.getElementById("boardModel").checked = false;
				document.getElementById("boardSNo").checked = false;
				document.getElementById("boardTransType").checked = false;
				
				document.getElementById("antennaId").checked = false;
			    document.getElementById("antennaModel").checked = false;
				document.getElementById("antennaSNo").checked = false;
				document.getElementById("antennaTransType").checked = false;
				
				document.getElementById("hostVersion").checked = false;
				document.getElementById("hostVersionTrans").checked = false;
				
				 $('input[name="siteId"]').attr("disabled", true);
				 $('input[name="siteNameTrans"]').attr("disabled", true);
				 
				 $('input[name="cellIdTr"]').attr("disabled", true);
				 $('input[name="cellNameTr"]').attr("disabled", true);
				 $('input[name="cellTransType"]').attr("disabled", true);
				 
				 $('input[name="nodeIdTr"]').attr("disabled", true);
				 $('input[name="nodeNameTr"]').attr("disabled", true);
				 $('input[name="nodeSNo"]').attr("disabled", true);
				 $('input[name="nodeTypeTr"]').attr("disabled", true);
				 $('input[name="nodeTransType"]').attr("disabled", true);
				
				 $('input[name="cabinetPosition"]').attr("disabled", true);
				 $('input[name="cabinetModel"]').attr("disabled", true);
				 $('input[name="cabinetSNo"]').attr("disabled", true);
				 $('input[name="cabinetTransType"]').attr("disabled", true);
				
				 $('input[name="boardPosition"]').attr("disabled", true);
				 $('input[name="boardModel"]').attr("disabled", true);
				 $('input[name="boardSNo"]').attr("disabled", true);
				 $('input[name="boardTransType"]').attr("disabled", true);
				
				 $('input[name="antennaId"]').attr("disabled", true);
				 $('input[name="antennaModel"]').attr("disabled", true);
				 $('input[name="antennaSNo"]').attr("disabled", true);
				 $('input[name="antennaTransType"]').attr("disabled", true);
				 
				 $('input[name="hostVersion"]').attr("disabled", true);
				 $('input[name="hostVersionTrans"]').attr("disabled", true);

			  
			  
		//  });		
			/// save model options
		 $("#saveTrans").click(  function() {
			  $('#myModal').modal('hide');
						  
						  
		  });
			
		 $("#clearTrans").click(  function() {
			 
			    document.getElementById("Cell").checked = false;
				document.getElementById("Node").checked = false; 
				document.getElementById("Site").checked = false;
				document.getElementById("Cabinet").checked = false;
				document.getElementById("Board").checked = false;
				document.getElementById("Antenna").checked = false;
				document.getElementById("Host").checked = false;
			 
			    document.getElementById("siteId").checked = false;
				document.getElementById("siteNameTrans").checked = false;
				
				document.getElementById("cellIdTr").checked = false;
				document.getElementById("cellNameTr").checked = false;
				document.getElementById("cellTransType").checked = false;
				
				document.getElementById("nodeIdTr").checked = false;
				document.getElementById("nodeNameTr").checked = false;
				document.getElementById("nodeSNo").checked = false;
				document.getElementById("nodeTypeTr").checked = false;
				document.getElementById("nodeTransType").checked = false;
				
				document.getElementById("cabinetPosition").checked = false;
				document.getElementById("cabinetModel").checked = false;
				document.getElementById("cabinetSNo").checked = false;
				document.getElementById("cabinetTransType").checked = false;
				
				document.getElementById("boardPosition").checked = false;
				document.getElementById("boardModel").checked = false;
				document.getElementById("boardSNo").checked = false;
				document.getElementById("boardTransType").checked = false;
				
				document.getElementById("antennaId").checked = false;
			    document.getElementById("antennaModel").checked = false;
				document.getElementById("antennaSNo").checked = false;
				document.getElementById("antennaTransType").checked = false;
				
				document.getElementById("hostVersion").checked = false;
				document.getElementById("hostVersionTrans").checked = false;
				
				 $('input[name="siteId"]').attr("disabled", true);
				 $('input[name="siteNameTrans"]').attr("disabled", true);
				 
				 $('input[name="cellIdTr"]').attr("disabled", true);
				 $('input[name="cellNameTr"]').attr("disabled", true);
				 $('input[name="cellTransType"]').attr("disabled", true);
				 
				 $('input[name="nodeIdTr"]').attr("disabled", true);
				 $('input[name="nodeNameTr"]').attr("disabled", true);
				 $('input[name="nodeSNo"]').attr("disabled", true);
				 $('input[name="nodeTypeTr"]').attr("disabled", true);
				 $('input[name="nodeTransType"]').attr("disabled", true);
				
				 $('input[name="cabinetPosition"]').attr("disabled", true);
				 $('input[name="cabinetModel"]').attr("disabled", true);
				 $('input[name="cabinetSNo"]').attr("disabled", true);
				 $('input[name="cabinetTransType"]').attr("disabled", true);
				
				 $('input[name="boardPosition"]').attr("disabled", true);
				 $('input[name="boardModel"]').attr("disabled", true);
				 $('input[name="boardSNo"]').attr("disabled", true);
				 $('input[name="boardTransType"]').attr("disabled", true);
				
				 $('input[name="antennaId"]').attr("disabled", true);
				 $('input[name="antennaModel"]').attr("disabled", true);
				 $('input[name="antennaSNo"]').attr("disabled", true);
				 $('input[name="antennaTransType"]').attr("disabled", true);
				 
				 $('input[name="hostVersion"]').attr("disabled", true);
				 $('input[name="hostVersionTrans"]').attr("disabled", true);

						  
						  
		 });
		 
		 
	 });
}
 