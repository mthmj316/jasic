/**
 * Contains all JavaScript functions which are needed by the
 * HTML page inclined_plane.html.
 */
//Space between the left side of the canvas and the inclined plane triangle.
const LEFT_SPACE_X = 10;
//Space between the right side of the canvas and the inclined plane triangle.
const RIGHT_SPACE_X = 10;
//Space between the bottom of the canvas and the inclined plane triangle.
const BOTTOM_SPACE_Y = 10;
//Space between the top of the canvas and the inclined plane triangle.
const TOP_SPACE_Y = 10;
//Width of the the canvas
var canvasWidth = 0;
//Height of the canvas
var canvasHeight = 0;
//X coordinate of the left bottom corner of the triangle.
var blx = LEFT_SPACE_X
//Y coordinate of the left bottom corner of the triangle.
var bly = 0;
//X coordinate of the right bottom corner of the triangle.
var brx = 0;
//Y coordinate of the right bottom corner of the triangle.
var bry = 0;
//X coordinate of the upper corner of the triangle.
var bhx = 0;
//Y coordinate of the upper corner of the triangle.
var bhy = 0;
//Radius of the circle
var r = 0;
//Gewichtskraft
var fg = 50;

function recalc(gForce, triangleHeigth){
	
	this.bhy = this.bry - triangleHeigth;
}

function initPlane(width, height){
	
	this.canvasWidth = width;
	this.canvasHeight = height;
	
	this.bly = this.canvasHeight - this.BOTTOM_SPACE_Y;
	this.brx = this.canvasWidth - this.RIGHT_SPACE_X;
	this.bry = this.bly;
	this.bhx = this.brx;
	
	//Initial height of the triangle
	var triangleHeight =  this.bry - this.canvasHeight / 2;
	this.recalc(this.fg, triangleHeight);
	
	this.r = (this.bry - this.bhy) / 2; //Not before recalc has been called!
}