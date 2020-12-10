/**
 * 
 */
                                                   
import { initPlane, getBPosition , getHPosition,getCirclePosition} from "/js/mechanics/dynamics/statics/inclined_plane.js";
import {drawTriangle, drawCircle}  from  "/js/draw_shapes.js";

window.drawInclinedPlane = function drawInclinedPlane(context, canvasWidth, canvasHeigth){
	
	initPlane( canvasWidth, canvasHeigth);
	
	var b = getBPosition();
	var h = getHPosition();	
	drawTriangle(context, b.xs, b.ys, b.xe, b.ye, h.xe, h.ye);
	
	var circle = getCirclePosition();
	drawCircle(context, circle.mx, circle.my, circle.r);
}

window.redrawInclinedPlane = function redrawInclinedPlane(context, fg, h){
	
} 