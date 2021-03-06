/**
 * Contains all JavaScript functions which are needed by the
 * HTML page inclined_plane.html.
 */

//Space between the border of the canvas and the drawing within. 
const SPACE = {left:100, right:100, bottom:10, top:10};

//Canvas dimension
var canvasDim = {x:0,  y:0}; 

//Position of triangle bottom line (b)
var b = {xs:0,  ys:0, xe:0,  ye:0}; 

//Position of triangle vertical  line (h)
var h = {xs:0,  ys:0, xe:0,  ye:0};

//Position of triangle inclined  line (l)
var l = {xs:0,  ys:0, xe:0,  ye:0};

//Position of the circle and its radius
var circle = {mx:0, my:0, r:0};

//Position of the fg arrow
var fg = {xs:0,  ys:0, xe:0,  ye:0};

//Position of the fh arrow
var fh = {xs:0,  ys:0, xe:0,  ye:0};

//Position of the fn arrow
var fn = {xs:0,  ys:0, xe:0,  ye:0};

//Angle of the bottom line b to the inclined line l
var alpha = 0;

var fgValue = 100;
var fhValue = 0;
var fnValue = 0;

var initialH = 150;

export function recalc(gForceDelta,hDelta){

	 fgValue = fgValue + gForceDelta;
	 h.ye = h.ye - hDelta;
	 
	 l.ye = h.ye;
	 calcAlpha();
	 circle.my =  calcCircleMy();
	 
	 fg.ys = circle.my;
	 fg.ye = fg.ys + fgValue;

	 calcFhValue();
	 
	 fh.ys = circle.my;
	 calcFhEndPoint();
		
	 calcFnValue();

	 fn.ys = circle.my; 
	 calcFnEndPoint();
}

export function initPlane(canvasWidth, canvasHeight){
	
	canvasDim.x = canvasWidth;
	canvasDim.y = canvasHeight;

	b.xs = SPACE.left;
	b.ys = canvasDim.y - SPACE.bottom;
	b.xe = canvasDim.x -  SPACE.right;
	b.ye = b.ys;
	
	h.xs = b.xe;
	h.ys =  b.ys;
	h.xe = h.xs;
	h.ye = h.ys - initialH; //must be recalculated after ui change
	
	l.xs = b.xs;
	l.ys = b.ys;
	l.xe = h.xe;
	l.ye = h.ye;  //must be recalculated after ui change
	
	calcAlpha();
	
	circle.r = (h.ys - h.ye) / 3;  //Don not recalulate
	circle.mx = canvasDim.x / 2;
	circle.my =  calcCircleMy();   //must be recalculated after ui change
	
	fg.xs = circle.mx;
	fg.ys = circle.my; 	
	fg.xe = fg.xs;
	fg.ye = fg.ys + fgValue;
	
	calcFhValue();

	fh.xs = circle.mx;
	fh.ys = circle.my;	
	calcFhEndPoint();
	
	calcFnValue();
	
	fn.xs = circle.mx;
	fn.ys = circle.my; 
	calcFnEndPoint();
}

function calcFnEndPoint(){
	fn.xe = fg.xe + Math.cos(alpha) * fhValue;
	fn.ye = fg.ye - Math.sin(alpha) * fhValue;
}


function calcFnValue(){
	fnValue = fgValue * Math.cos(alpha);	
}

function calcFhEndPoint(){

	fh.xe = circle.mx - Math.cos(alpha) * fhValue; 
	fh.ye = circle.my + Math.sin(alpha) * fhValue;
}

function calcFhValue(){
	fhValue = fgValue * (getHLength() / getLLength());
}

function calcAlpha(){
	
	var h = getHLength();
	var b =  getBLength();
	var x = h / b;
	
	alpha = Math.atan( x);
}

function calcCircleMy(){
	return canvasDim.y - SPACE.bottom  - Math.tan(alpha) * getBLength() / 2  -  circle.r / Math.cos(alpha);
}

export function getAlpha(){
	
	return alpha;
}

export function getFnPosition(){
	return fn;
}

export function getFhPosition(){
	return fh;
}

export function getFgPosition(){
	return fg;
}

export function getCirclePosition(){
	return circle;
}

export function getBPosition(){
	
	return b;
}

export function getHLength(){
	return h.ys - h.ye;
}

export function getHPosition(){
	
	return h;
}

export function getLPosition(){
	
	return l;
}

export function getBLength(){
	return b.xe - b.xs;
}

export function getLLength(){
	
	var bl = getBLength();
	var hl = getHLength();
	
	var bl2 = Math.pow(bl, 2);
	var hl2 =  Math.pow(hl, 2);
	
	var ll = Math.sqrt( bl2 + hl2)
	
	return  ll;
}

export function getFg(){
	return fgValue;
}

export function getFn(){
	return fnValue;
}

export function getFh(){
	return fhValue;
}


