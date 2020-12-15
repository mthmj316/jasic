/**
 * 
 */
                                                   
import { initPlane, getBPosition , getHPosition,getCirclePosition, getFgPosition, getFhPosition, getFnPosition, recalc, getHLength, getFg} from "/js/mechanics/dynamics/statics/inclined_plane.js";
import {drawTriangle, drawCircle}  from  "/js/draw_shapes.js";
import {drawForceArrow}  from  "/js/draw_basic_physic_shaps.js";

window.initInclinedPlane = function initnclinedPlane(context, canvasWidth, canvasHeigth){
	
	initPlane( canvasWidth, canvasHeigth);
	drawInclinedPlane(context, canvasWidth, canvasHeigth)
}

function drawInclinedPlane(context){
	
	//Draw inclined plane
	var b = getBPosition();
	var h = getHPosition();	
	drawTriangle(context, b.xs, b.ys, b.xe, b.ye, h.xe, h.ye);
	
	//Draw circle
	var circle = getCirclePosition();
	drawCircle(context, circle.mx, circle.my, circle.r);
	
	//Draw Fg arrow
	var fg = getFgPosition();
	drawForceArrow(context, fg.xs, fg.ys, fg.xe, fg.ye);
	
	//Draw Fh arrow
	var fh = getFhPosition();
	drawForceArrow(context, fh.xs, fh.ys, fh.xe, fh.ye);
	
	//Draw Fn arrow
	var fn = getFnPosition();
	drawForceArrow(context, fn.xs, fn.ys, fn.xe, fn.ye);
}

window.redrawInclinedPlane = function redrawInclinedPlane(context, fgDelta, hDelta){
	recalc(fgDelta,hDelta);
	drawInclinedPlane(context);
} 

window.getHeightSilderDefault = function getHeightSilderDefault(){
	return getHLength();
} 

window.getFgSilderDefault = function getFgSilderDefault(){
	return getFg();
} 

