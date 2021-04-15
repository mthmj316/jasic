/**
 * 
 */
import {drawArrow}  from  "/js/draw_shapes.js";

export function drawForceArrow(context, fromX, fromY, toX, toY){
	drawArrow(context, fromX, fromY, toX, toY, "red", 3, 10);
}