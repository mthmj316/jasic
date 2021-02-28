/**
 * http://usejsdoc.org/
 */

/**
 * Differentiates the given expression with respect to the given variable.<br>
 * If one of the parameters is not set (which means is undefined or empty) an error will
 * be raised.<br>
 *  <ul>
 *  Derivative Rules:
 *  <li>expression contains variable    -> return 0 </li>
 *  <li> expression is only the variable -> return 1</li>
 *  <li> expression is only variable^k    -> return kvariable^(k-1)</li>
 *  <li> expression is avariable^k          -> return nvaribale^(k-1)           (n == product of k and a)</li>
 *  <li>expression is a/bvariable^k      -> return n/bvariable^(k-1)       (n == product of k and a) </li>
 *  Rules for the expressions:
 *  <li>exponent is a fraction is not allowed</li>
 *  <li>factors are only allowed before the variable</li>
 *  <li>expression is a fraction is not allowed</li>
 *  <li>other variables beside the function variable are not allowed, e.g avariable^2; a  must  be number or a fraction</li>
 *  <li>products before the variable, e.g. 3*2variable^3 or 3*1/4variable^1, are not allowed</li>
 *  <li>the only allowed operators in the expression are: "+", "-" and "^"</li>
 *  <li>only numbers are allowed in the fraction before the variable; no variables no operations.</li>
 *  </ul>
 */
export function differentiateWithRespectTo(expression, variable){
	
	if(expression == null || expression.length == 0){
		throw "expression not set: >" + expression + "<!";
	}
	
	if(variable == null || variable.length == 0){
		throw "variable not set: >" + variable + "<!";
	}
	
	//print("differentiateWithRespectTo	expression=" + expression );
	//print("differentiateWithRespectTo	variable=" + variable );
	
	var partialExpressions = splitMathSumExpression(expression);
	
	var diffPartialExpressions =  [];
	var diff;
	partialExpressions.forEach(function(partialExpression){
		
		diff = differentiatePartial(partialExpression, variable);
		
		if(diff.length > 0){
			diffPartialExpressions.push(diff);
		}
	});
	
	var diffExpression = concatPartialExpressions(diffPartialExpressions);
	
	//print("differentiateWithRespectTo	diffExpression=" + diffExpression );
	
	if(diffExpression.length == 0){
		diffExpression = "0";
	}
	
	//print("differentiateWithRespectTo	diffExpression=" + diffExpression );
	
	return diffExpression;
}

//#####################################################
// private functions #########################################
//#####################################################
/*
 * Concatenates the given partialExpressions to one expression.
 */
function concatPartialExpressions(partialExpressions){
	
	//print("concatPartialExpressions 	partialExpressions=" + partialExpressions );
	
	var concatExpression = "";
	var sep = "";
	
	partialExpressions.forEach(function(partialExpression){
		
		//print("concatPartialExpressions 	partialExpression=" + partialExpression );
		
		if(concatExpression.length == 0 ){
			sep = "";
		} else {
			if(partialExpression.toString().startsWith("-")){
				sep = "";
			} else {
				sep = "+"
			}
		}
		concatExpression = concatExpression + sep + partialExpression;
		
		//print("concatPartialExpressions 	concatExpression=" + concatExpression );
	});
	
	return concatExpression;
}

/*
 * Splits the given expression in its summands.
 * 
 * e.g.: 2x² - 2x + 3 -> ["2x²", "-2x", "3"]
 * 
 * It is not checked if the expression is valid.
 */
function splitMathSumExpression(expression){
	
	//print("splitMathSumExpression	expression=" + expression );
	
	expression = expression.replace(/-/g, "$-");
	
	//print("splitMathSumExpression	expression=" + expression );
	
	var splitExpression = expression.split("$");
	
	//print("splitMathSumExpression	splitExpression=" + splitExpression );
	
	var result = new Array();
	
	splitExpression.forEach(function(entry){
		
		//print("splitMathSumExpression	entry=" + entry );
		
		var splitEntry = entry.split("+");
		
		//print("splitMathSumExpression	splitEntry=" + splitEntry );
		
		Array.prototype.push.apply(result, splitEntry); //result.push(... splitEntry);
	});
	
	//print("splitMathSumExpression	result=" + result );
	
	return result;
}

/*
 * Reduces the given fraction.
 * Only simple fractiosn like "2/4" are supported.
 * "Double fraction" like "(2/4)/(3/6) are not supported.
 * Variables in the fraction are not supported.
 * It is not checked if the fraction is valid.
 */
function reduceFraction(fraction){
	
	//print("reduceFraction	fraction=" + fraction );
	
	//Separate numerator and denominator 
	var splitFraction = fraction.split("/");	
	var numerator = parseInt(splitFraction[0]);
	var denominator = parseInt(splitFraction[1]);
	
	//print("reduceFraction	numerator=" + numerator );
	//print("reduceFraction	denominator=" + denominator );
	
	//Contains the max. possible common factor
	var factor;
	
	if (Math.abs(numerator) > Math.abs(denominator)){
		//Numerator is greater than the denominator -> factor must be <= denominator
		factor = Math.abs(denominator);		
	} else {		
		//Denominator is greater than the numerator -> factor must be <= numerator#
		factor = Math.abs(numerator);
	}
	
	//Search for max. possible factor.
	for(i = factor; i > 0; i--){
		
		if((numerator % i) == 0 && (denominator % i) == 0 ){
			//When this is entered  for the  The first time, i must be the max. possible common factor. when 
			//This clause will be latest entered, when i == 1, that means the fraction cannot be reduced.
			factor = i;
			break;
		}
	}
	
	//print("reduceFraction	factor=" + factor );
	
	//Reduce the numerator and denominator
	numerator = numerator / factor;
	denominator = denominator / factor;
	
	//Built reduced fraction.
	var result = numerator + "/" + denominator;
	
	//print("reduceFraction	result=" + result );
	
	return result;
}

/*
 * Multiplies the given fractions. If it is possible the result will be reduced.
 * If the result is whole-number the number will returned and not a fraction.
 * It is not checked if the given fractions are valid.
 */
function multiplyFractions(fraction1, fraction2){
	
	//print("multiplyFractions	fraction1=" + fraction1);
	//print("multiplyFractions	fraction2=" + fraction2);
	
	var splitFraction1 = fraction1.split("/");
	var splitFraction2 = fraction2.split("/");
	
	//Calculate the the numerator and denominator of the result fraction.
	var resultNumerator = parseInt(splitFraction1[0]) * parseInt(splitFraction2[0]);
	var resultDenominator = parseInt(splitFraction1[1]) * parseInt(splitFraction2[1]);
	
	//print("multiplyFractions	resultNumerator=" + resultNumerator);
	//print("multiplyFractions	resultDenominator=" + resultDenominator);
	
	if (resultNumerator % resultDenominator == 0){
		//In this case the result is whole-number.
		var result = (resultNumerator / resultDenominator).toString();
	} else {
		//The result is again a fraction. 
		var result = reduceFraction(resultNumerator + "/" + resultDenominator);
	}
	
	//print("multiplyFractions	result=" + result);
	
	return result;
}

/*
 * Multiplies the given multipliers.
 * The multiplier may be whole-numbers or fraction.
 * It is checked if the multipliers are valied. If one of them is not an error while be raised.
 */
function multiply(multiplier1, multiplier2){
	
	//print("multiply	multiplier1=" + multiplier1);
	//print("multiply	multiplier2=" + multiplier2);
	
	var m1Ok = isNumberOrFraction(multiplier1);
	var m2Ok = isNumberOrFraction(multiplier2);
	
	if(!m1Ok || !m2Ok){
		
		throw "Check your input: " + multiplier1 + " and/or " + multiplier2  + "  is not a valid number or fraction.";
	}
	
	if(!multiplier1.toString().includes("/")){
		//Make a fraction of multiplier1.
		multiplier1 = multiplier1 + "/1";
	}
	
	if(!multiplier2.toString().includes("/")){
		//Make a fraction of multiplier2.
		multiplier2 = multiplier2 + "/1";
	}
	
	var result = multiplyFractions(multiplier1, multiplier2);
	
	//print("multiply	result=" + result);
	
	return result;
}

/*
 * Checks if the given term is either a  valid whole-number or a valid fraction.
 */
function isNumberOrFraction(term){
	
	if(term.toString().includes("/")){
		//Must be a fraction.
		
		var splitTerm = term.split("/");
		var numerator = parseInt(splitTerm[0]);
		var denominator = parseInt(splitTerm[1]);
		
		return !isNaN(numerator) && !isNaN(denominator);
		
	} else {
		//Must be whole-number.
		
		return !isNaN(term);
	}
}

/*
 * Differentiates the given partialExpression with given variable.
 * This methdo supports expressions with "+" and "-" operators.
 * Cases:
 * 	partialExpression doesn't contains variable (e.g. 4) -> return ""
 * 	partialExpression doesn't conatins "^" (e.g. 4x or x)		-> return  4 or 1
 * 	any other expression (e.g. 3x³) -> return 9x² 
 */
function differentiatePartial(partialExpression, variable){
	
	//print("differentiatePartial	partialExpression=" + partialExpression);
	//print("differentiatePartial	variable=" + variable);
	
	if(!partialExpression.toString().includes(variable)){
		
		//print("differentiatePartial	result=<empty>");
		//partialExpression doesn't contains variable (e.g. 4) -> return ""
		return "";
	} else if (!partialExpression.toString().includes("^")){
	
		//partialExpression doesn't conatins "^" (e.g. 4x or x)		-> return  4 or 1
		if(partialExpression.toString().length == variable.toString().length){
			
			//print("differentiatePartial	result=1");
			
			//e.g . partialExpression == x
			return "1";
		} else {
			//e.g . partialExpression == 4x
			
			var result = partialExpression.toString().replace(variable, "");
			//print("differentiatePartial	result=" +  result);
			
			if(result.toString().includes("/")){
				result = reduceFraction(result);
				//print("differentiatePartial	result=" +  result);
			}
			
			return result;
		}
		
	} else {
		
		var splitPartialEx = partialExpression.toString().split("^");
		
		//print("differentiatePartial	splitPartialEx=" +  splitPartialEx);
		
		var exponent = parseInt(splitPartialEx[1]);
		
		if(exponent == 1){
			//e.g . partialExpression == 4x¹ or x¹
			
			var result = splitPartialEx[0].replace(variable, "");
			
			if(result.length == 0){
				result = "1";
			}
			
			//print("differentiatePartial	result=" +  result);
			
			return result;
			
		} else {
			//any other expression (e.g. 3x³) -> return 9x² 
			
			var sfactor = splitPartialEx[0].replace(variable, "");
			
			if(sfactor.length == 0){
				sfactor = "1";
			}
			
			//print("differentiatePartial	sfactor=" +  sfactor);
			
			var newFactor = multiply(sfactor, exponent.toString());
			var newExponent = exponent - 1;
			
			var result = newFactor.toString() + variable;
			
			if(newExponent > 1){
				result = result + "^" + newExponent;
			}
			
			//print("differentiatePartial	result=" +  result);
			return result;
		}
	}
}