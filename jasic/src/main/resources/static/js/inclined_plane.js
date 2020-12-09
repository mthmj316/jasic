/**
 * Contains all JavaScript functions which are needed by the
 * HTML page inclined_plane.html.
 */

//Space between the border of the canvas and the drawing within. 
const SPACE = {left:10, right:10, bottom:10, top:10};

//Canvas dimension
var canvasDim = {x:0,  y:0}; 

//Position of triangle bottom line (b)
var b = {xs:0,  ys:0, xe:0,  ye:0}; 

//Position of triangle vertical  line (h)
var h = {xs:0,  ys:0, xe:0,  ye:0};

//Position of triangle inclined  line (l)
var l = {xs:0,  ys:0, xe:0,  ye:0};

//Position of the circle and its radius
var circle = {mx:0, my:0, r=0};

//Position of the fg arrow
var fg = {xs:0,  ys:0, xe:0,  ye:0};

//Position of the fh arrow
var fh = {xs:0,  ys:0, xe:0,  ye:0};

//Position of the fn arrow
var fn = {xs:0,  ys:0, xe:0,  ye:0};

//Angle of the bottom line b to the inclined line l
var alpha = 0;

function recalc(gForce, triangleHeigth){
	

}

function initPlane(canvasWidth, canvasHeight){
	
	canvasDim.x = canvasWidth;
	canvasDim.y = canvasHeight;

	b.xs = SPACE.left;
	b.ys = canvasDim.y - SPACE.bottom;
	b.xe = canvasDim.x -  SPACE.right;
	b.ye = b.ys;
	
	h.xs = b.xe;
	h.ys =  b.ys;
	h.xe = h.xs;
	h.ye = canvasDim.y / 2; //must be recalculated after ui change
	
	l.xs = b.xs;
	l.ys = b.ys;
	l.xe = h.xe;
	l.ye = h.ye;  //must be recalculated after ui change
	
	circle.r = (h.ys - h.ye) / 2; 
	circle.mx = canvasDim.x / 2;
	circle.my = l.ye + (l.ys - l.ye) / 2 - circle.r;   //must be recalculated after ui change
}