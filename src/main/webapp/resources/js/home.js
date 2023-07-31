
function draw_arrow( sel1, locs1, sel2, locs2, arr ) 
{
  var elt1  = document.querySelector( sel1 );
  var elt2  = document.querySelector( sel2 );
  var arrow = document.querySelector( arr );

  var posn1 = draw_arrow_pos( elt1, locs1 );

  var posn2 = draw_arrow_pos( elt2, locs2 ); 

  var d_str =
      "M" +
      posn1.x + "," + posn1.y + " " +
      "L" +
      posn2.x + "," + posn2.y;
  arrow.setAttribute( "d", d_str );
}


/* loc is a two-character string whose 
   first character is one of "l", "m" or "r",
   and whose second is one of "l", "m" or "r".
   elt is a DOM element.
   Returns a point with selectors 'x'
   and 'y', being the x and y coordinates
   of the (left, middle, or right) 
   and (top, middle, or bottom) of elt.
*/
function draw_arrow_pos( elt, locs )
{
  return(    
    { x: draw_arrow_x_pos( elt, locs.substring(0,1) ),
      y: draw_arrow_y_pos( elt, locs.substring(1,2) )
    }
  );
}


/* loc is one of "l", "m" or "r". elt is
   a DOM element. Returns the x coordinate
   of the left, middle, or right of elt.
*/
function draw_arrow_x_pos( elt, loc )
{
  return(     
    elt.offsetLeft +
      ( loc == "l" ? 0:
      ( loc == "m" ? elt.offsetWidth / 2 :
             /*"r"*/ elt.offsetWidth 
      ))
	
  ); 
}


/* loc is one of "t", "m" or "b". elt is
   a DOM element. Returns the y coordinate
   of the top, middle, or bottom of elt.
*/
function draw_arrow_y_pos( elt, loc )
{
  return(
    elt.offsetTop +
      ( loc == "t" ? 0:
      ( loc == "m" ? elt.offsetHeight / 2 :
           /*  "b" */elt.offsetHeight 
      ))
	
	
	  ); 
}

