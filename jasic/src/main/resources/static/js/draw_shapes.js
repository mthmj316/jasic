//function drawArrow(context, fromX, fromY, toX, toY){
//	drawArrow(context, fromX, fromY, toX, toY, "black", 1)
//}
//
//function drawArrow(context, fromx, fromy, tox, toy, color, width, r){
//	
//	context.fillStyle = color;
//	context.strokeStyle = color;
//	context.lineWidth = width;
//	
//	var x_center = tox;
//	var y_center = toy;
//	
//	var angle;
//	var x;
//	var y;
//	
//	context.beginPath();
//	
//	angle = Math.atan2(toy-fromy,tox-fromx)
//	
//	x = r*Math.cos(angle) + x_center;
//	y = r*Math.sin(angle) + y_center;
//
//	context.moveTo(x, y);
//	
//	angle += (1/3)*(2*Math.PI)
//	x = r*Math.cos(angle) + x_center;
//	y = r*Math.sin(angle) + y_center;
//	
//	context.lineTo(x, y);
//	
//	angle += (1/3)*(2*Math.PI)
//	x = r*Math.cos(angle) + x_center;
//	y = r*Math.sin(angle) + y_center;
//	
//	context.lineTo(x, y);
//	
//	context.closePath();
//	
//	context.fill();
//	
//	context.beginPath();
//	context.moveTo(fromx, fromy);
//	context.lineTo(tox, toy);
//	context.stroke();
//
//	context.fillStyle = "black";
//	context.strokeStyle = "black";
//	context.lineWidth = 1;
//}
//

export function drawCircle(context, centerX, centerY, radius){
	
	context.beginPath();
	context.arc(centerX, centerY, radius, 0, 2 * Math.PI);
	context.closePath();
	context.stroke();
}
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
function drawLine(context, p1_x, p1_y, p2_x, p2_y){
	context.beginPath();
	context.moveTo(p1_x, p1_y);
	context.lineTo(p2_x, p2_y);
	context.stroke();
}
