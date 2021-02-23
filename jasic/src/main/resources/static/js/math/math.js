/**
 * http://usejsdoc.org/
 */

export function differentiateWithRespectTo(expression, variable){
	
}

//#####################################################
// private functions #########################################
//#####################################################

/*
 * Splits the given expression in its summands.
 * 
 * e.g.: 2x² - 2x + 3 -> ["2x²", "-2x", "3"]
 * 
 * It is not checked if the expression is valid.
 */
function splitMathSumExpression(expression){
	
	expression = expression.replaceAll("-", "$-");
	
	var splitExpression = expression.split("$");
	
	var result = {};
	
	splitExpression.forEach(function(entry){
		result.concat(entry.split("+"));
	});
	
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
	
	//Separate numerator and denominator 
	var splitFraction = fraction.split("/");	
	var numerator = parseInt(splitFraction[0]);
	var denominator = parseInt(splitFraction[1]);
	
	//Contains the max. possible common factor
	var factor;
	
	if (numerator > denominator){
		//Numerator is greater than the denominator -> factor must be <= denominator
		factor = denominator;		
	} else {		
		//Denominator is greater than the numerator -> factor must be <= numerator#
		factor = numerator;
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
	
	//Reduce the numerator and denominator
	numerator = numerator / factor;
	denominator = denominator / factor;
	
	//Built reduced fraction.
	var result = numerator + "/" + denominator;
	
	return result;
}

/*
 * Multiplies the given fractions. If it is possible the result will be reduced.
 * If the result is whole-number the number will returned and not a fraction.
 * It is not checked if the given fractions are valid.
 */
function multiplyFractions(fraction1, fraction2){
	
	var splitFraction1 = fraction1.split("/");
	var splitFraction2 = fraction2.split("/");
	
	//Calculate the the numerator and denominator of the result fraction.
	var resultNumerator = parseInt(splitFraction1[0]) * parseInt(splitFraction2[0]);
	var resultDenominator = parseInt(splitFraction1[1]) * parseInt(splitFraction2[1]);
	
	if (resultNumerator % resultDenominator == 0){
		//In this case the result is whole-number.
		return (resultNumerator / resultDenominator).toString();
		
	} else {
		//The result is again a fraction. 
		return reduceFraction(resultNumerator + "/" + resultDenominator);
	}
}

/*
 * Multiplies the given multipliers.
 * The multiplier may be whole-numbers or fraction.
 * It is checked if the multipliers are valied. If one of them is not an error while be raised.
 */
function multiply(multiplier1, multiplier2){
	
	var m1Ok = isNumberOrFraction(multiplier1);
	var m2Ok = isNumberOrFraction(multiplier2);
	
	if(!m1Ok || !m2Ok){
		
		throw "Check your input: " + multiplier1 + " and/or " + multiplier2  + "  is not a valid number or fraction.";
	}
	
	if(!multiplier1.includes("/")){
		//Make a fraction of multiplier1.
		multiplier1 = multiplier1 + "/1";
	}
	
	if(!multiplier2.includes("/")){
		//Make a fraction of multiplier2.
		multiplier2 = multiplier2 + "/1";
	}
	
	return multiplyFractions(multiplier1, multiplier2);
}

/*
 * Checks if the given term is either a  valid whole-number or a valid fraction.
 */
function isNumberOrFraction(term){
	
	if(term.includes("/")){
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
	
	if(!partialExpression.includes(variable)){
		//partialExpression doesn't contains variable (e.g. 4) -> return ""
		return "";
	} else if (!partialExpression.includes("^")){
	
		//partialExpression doesn't conatins "^" (e.g. 4x or x)		-> return  4 or 1
		if(partialExpression.length == variable.length){
			//e.g . partialExpression == x
			return "1";
		} else {
			//e.g . partialExpression == 4x
			return partialExpression.replace(variable, "");
		}
		
	} else {
		
		var splitPartialEx = partialExpression.split("^");
		
		var exponent = parseInt(splitPartialEx[1]);
		
		if(exponent == 1){
			//e.g . partialExpression == 4x¹
			return splitPartialEx[0].replace(variable, "");
			
		} else {
			//any other expression (e.g. 3x³) -> return 9x² 
			var factor = parseInt(splitPartialEx[0].replace(variable, ""));
			var newFactor = multiply(factor, exponent);
			var newExponent = exponent - 1;
			
			var result = newFactor.toString() + variable;
			
			if(newExponent > 1){
				result = "^" + newExponent;
			}
			
			return result;
		}
	}
}