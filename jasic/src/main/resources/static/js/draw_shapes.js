function drawArrow(context, fromX, fromY, toX, toY){
	drawArrow(context, fromX, fromY, toX, toY, "black", 1)
}

function drawArrow(context, fromX, fromY, toX, toY, color, width){
	
	context.fillStyle = color;
	context.strokeStyle = color;
	context.lineWidth = width;
	

	var headlen = 20; // length of head in pixels
	var dx = toX - fromX;
	var dy = toY - fromY;
	var angle = Math.atan2(dy, dx);

	context.beginPath();
	context.moveTo(fromX, fromY);
	context.lineTo(toX, toY);
	context.stroke();

	context.beginPath();
	context.moveTo(toX, toY);
	context.lineTo(toX - headlen * Math.cos(angle - Math.PI / 6), toY - headlen * Math.sin(angle - Math.PI / 6));
	context.stroke();
	context.moveTo(toX, toY);
	context.lineTo(toX - headlen * Math.cos(angle + Math.PI / 6), toY - headlen * Math.sin(angle + Math.PI / 6));
	context.stroke();
	
	context.moveTo(toX - headlen * Math.cos(angle - Math.PI / 6), toY - headlen * Math.sin(angle - Math.PI / 6));
	context.lineTo(toX - headlen * Math.cos(angle + Math.PI / 6), toY - headlen * Math.sin(angle + Math.PI / 6));
	context.stroke();
	context.fill();
	
	context.fillStyle = "black";
	context.strokeStyle = "black";
	context.lineWidth = 1;
}

function drawCircle(context, centerX, centerY, radius){
	
	context.beginPath();
	context.arc(centerX, centerY, radius, 0, 2 * Math.PI);
	context.closePath();
	context.stroke();
}
/**
 * 
 */
function drawTriangle(context,p1_x, p1_y, p2_x, p2_y, p3_x, p3_y){
	
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
