/**
* Converts the given rawExpression into a mathjax expression. 
*/
export function convert2MathJax(rawExpression){
	
	return "";
}

// #####################################################
// private functions ###################################
// #####################################################
/*
* Converts the <numerator>/<denominator> notation
* to the mathjax notation {{numerator}\over{denominator}}
*/
function convertFraction(fraction) {
	
	//print("convertFraction fraction=" + fraction);
	
	//Check if fraction is valid
	if(!fraction.toString().includes("/")){
		//Is not a fraction, hence nothing to do.
		//print("convertFraction result=" + fraction);
		return fraction;
	}
	
	//Separate numerator from denominator
	const splitFraction = fraction.split("/");
	//print("convertFraction splitFraction=" + splitFraction);
	
	//Build the mathjax expression.
	const mathjaxExpression = "{{" + splitFraction[0].toString().trim() + "}\over{" + splitFraction[1].toString().trim() + "}}"
	//print("convertFraction result=" + mathjaxExpression);
	
	return mathjaxExpression;
}

/*
* Checks if the given expression contains "->".
* if so it is expected, that the given expression is a function
* link f->x = ...
* It replaces f->x by f(x).
*
* If the expression doesn't contain -> it'll return the input expression.
*/
function replaceXfromF(expression){
	
	//print("replaceXfromF expression=" + expression);
	
	//Check if X from f is in the expression.
	if(!expression.toString.includes("->")){
		//Doesn't contain: f->x, hence no replacement needed.
		//print("replaceXfromF return=" + expression);
		return expression;
	}
	
	//Separate the x from f part from the expression
	const splitExpression = expression.split("=");
	//print("replaceXfromF splitExpression=" + splitExpression);
	
	const xFromF = splitExpression[0];
	//print("replaceXfromF xFromF=" + xFromF);
	
	//Separate x from f
	const splitXFromF = xFromF.split("->");
	//print("replaceXfromF splitXFromF=" + splitXFromF);
	
	//Build mathjax expression
	const mathjaxExpression = splitXFromF[0].toString().trim() + "_{(" + splitXFromF[0].toString().trim() + ")}";	
	//print("replaceXfromF result=" + mathjaxExpression);
	
	return mathjaxExpression;
}