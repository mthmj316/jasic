/**
* Converts the given rawExpression into a mathjax expression. 
*/
export function convert2MathJax(rawExpression){
	
	//print("convert2MathJax rawExpression=" + rawExpression);
	
	var mathjaxExpression = "$$";
	
	//Process invalid input data
	if(rawExpression == null || rawExpression.length == 0){
		
		mathjaxExpression = mathjaxExpression + "$$";
		
		//print("convert2MathJax result=" + mathjaxExpression);
		
		return mathjaxExpression;
	}
	
	//Process spaces
	rawExpression = replaceSpaces(rawExpression);
	
	//replace x from f
	rawExpression = replaceXfromF(rawExpression);
	
	//print("convert2MathJax rawExpression=" + rawExpression);
	
	//replace fractions
	rawExpression = replaceFractions(rawExpression);
	
	//print("convert2MathJax rawExpression=" + rawExpression);
	
	//replace powers
	rawExpression = replacePowers(rawExpression);
	
	//print("convert2MathJax rawExpression=" + rawExpression);
	
	//Close mathjaxExpression
	mathjaxExpression = mathjaxExpression + rawExpression + "$$";
	
	//print("convert2MathJax result=" + rawExpression);
	
	return mathjaxExpression;
}

// #####################################################
// private functions ###################################
// #####################################################
/*
* Replaces the spaces in the rawExpression as follows:
* 
* Any double space by "\;"
* Any single spce by "\,"
*
*/
function replaceSpaces(rawExpression){
	
	//print("replaceSpaces rawExpression=" + rawExpression);
	
	var result = rawExpression.toString();
	//print("replaceSpaces result=" + result);
	
	//Check for double spaces and replace them
	if(result.includes("  ")){
		result = result.replace(/  /g, "\\;");
		//print("replaceSpaces result=" + result);
	}
	
	//Check for single spaces and replace them
	if(result.includes(" ")){
		result = result.replace(/ /g, "\\,");
		//print("replaceSpaces result=" + result);
	}
	
	//print("replaceSpaces result=" + result);
	
	return result;
}

/*
*
* Searches for all powers in the given expression,
* and converts it to a mathjax notation power.
*/
function replacePowers(expression){
	
	//print("replacePowers expression=" + expression);
	
	var mathjaxExpression = expression;
	
	//Exract all powers from the expression and if none is contained return just the expression.
	const powers = mathjaxExpression.match(/[a-zA-Z]+\^\d+/g);
//	//print("replacePowers powers=" + powers);
	
	if(powers == null){
//		//print("replacePowers expression=" + mathjaxExpression);
		
		return mathjaxExpression; 
	}
	
	//Convert each power to a mathjax power notation.
	
	powers.forEach(function(power){
		
//		//print("replacePowers power=" + power);
	
		const mathjaxPower = convertPower(power);
	
//		//print("replacePowers mathjaxPower=" + mathjaxPower);
	
		mathjaxExpression = mathjaxExpression.replace(power, mathjaxPower);
		
//		//print("replacePowers mathjaxExpression=" + mathjaxExpression);
	});
	
//	//print("replacePowers result=" + mathjaxExpression);
	
	return mathjaxExpression;
}
/*
*
* Searches for all fractions in the given expression,
* and converts it to a mathjax notation fraction.
*/
function replaceFractions(expression){
	
	//print("replaceFractions expression=" + expression);
	
	//Extract all fractions from the expression and check if there is one
	const fractions = expression.match(/\d+[/]\d+/g);
	
	//print("replaceFractions expression=" + expression);
	
	if(fractions == null){
		
		//print("replaceFractions return=" + expression);
		
		return expression;
	}
	
	var mathjaxExpression = expression;
	
	//print("replaceFractions mathjaxExpression=" + mathjaxExpression);
	
	//fractions.forEach(call: convertFraction -> replace the fraction with the mathjax notation)
	fractions.forEach(function(fraction){
		
		const mathjaxFraction = convertFraction(fraction);
		
		//print("replaceFractions mathjaxFraction=" + mathjaxFraction);
		
		mathjaxExpression = mathjaxExpression.replace(fraction, mathjaxFraction);
		
		//print("replaceFractions mathjaxExpression=" + mathjaxExpression);
	});
	
	//print("replaceFractions return=" + mathjaxExpression);
	
	return mathjaxExpression;
}

/*
* Converts the <base>^<exponent> notation
* to the mathjax notation {base}^{exponent}
*/
function convertPower(power){
	
	//print("convertPower power=" + power);
	
	//Check if the expression is a valid power notation
	if(!power.toString().includes("^")){
		//It is not power expression, hence nothing to do.
		//print("convertPower result=" + power);
		return power;
	}
	
	//Separate base from exponent
	const splitPower = power.split("^");
	//print("convertPower splitPower=" + splitPower);
	
	//Build mathjax expression
	const mathjaxExpression = splitPower[0] + "^{" + splitPower[1] +  "}";
	//print("convertPower result=" + mathjaxExpression);
	
	return mathjaxExpression;
}

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
	const mathjaxExpression = "{{" + splitFraction[0].toString().trim() + "}\\over{" + splitFraction[1].toString().trim() + "}}"
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
	if(!expression.toString().includes("->")){
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
	const mathjaxExpression = splitXFromF[0].toString().trim() + "_{(" + splitXFromF[1].toString().trim() + ")}=" + splitExpression[1];	
	//print("replaceXfromF result=" + mathjaxExpression);
	
	return mathjaxExpression;
}