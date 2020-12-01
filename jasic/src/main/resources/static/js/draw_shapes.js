/**
 * 
 */
export function drawTriangle(context,p1_x, p1_y, p2_x, p2_y, p3_x, p3_y){
	
	drawLine(context, p1_x, p1_y, p2_x, p2_y);
	drawLine(context, p2_x, p2_y, p3_x, p3_y);
	drawLine(context, p3_x, p3_y, p1_x, p1_y);
}

/**
 * Draws between a line the given coordinates.
 * @param context the context where the line shall be drawed
 * @param p1_x x-coordinate of the staring point of line
 * @param p1_y y-coordinate of the staring point of line
 * @param p2_x x-coordinate of the end point of line
 * @param p2_y y-coordinate of the end point of line
 */
export function drawLine(context, p1_x, p1_y, p2_x, p2_y){
	context.beginPath();
	context.moveTo(p1_x, p1_y);
	context.lineTo(p2_x, p2_y);
	context.stroke();
}
