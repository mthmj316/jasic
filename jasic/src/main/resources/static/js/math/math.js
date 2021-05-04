/**
 * http://usejsdoc.org/
 */
/**
 * Subtracts the subtrahend from the minuend.
 */
export function subtract(minuend, subtrahend){
	
	//Check input
	validateNumber(minuend);
	validateNumber(subtrahend);
	
	//Inverte the algebraic sign of the subtrahend
	if(subtrahend.toString().includes("-")){
		subtrahend = subtrahend.replace("-", "");
	} else {
		subtrahend = "-" + subtrahend;
	}
	
	//Call sum and return call result
	const difference = sum(minuend, subtrahend);
	
	return difference;
}

/**
 * Divides the given numerator by the given denominator.
 * If the result is whole number, this whole number will be rturned.
 * Otherwise the reduced fraction.
 */
export function divide(numerator,denominator){
	
	//print("divide numerator=" + numerator);
	//print("divide denominator=" + denominator);
	
	validateNumber(numerator);
	validateNumber(denominator);
	
	if(denominator == 0){
		throw "Division by zero is not permitted";
	}
	
	//Create reverse break of the denominator.
	
	
	var denominatorReverseBreak;
	
	if(/\//.test(denominator)){
		
		denominatorReverseBreak = denominator.split(/\//)[1] + "/" + denominator.split(/\//)[0];
		
	} else {
		
		denominatorReverseBreak = "1/" + denominator;
	}
	
	const result = multiply(numerator, denominatorReverseBreak);
	//print("divide result=" + result);
	
	return result;
}

/** 
* Sums the given sumands.
*/
export function sum(summand1, summand2){
	
	//print("sum summand1=" + summand1);
	//print("sum summand2=" + summand2);
	
	validateNumber(summand1);
	validateNumber(summand2);
	
	//Check if both summands are fraction and if not transform them
	if(!summand1.toString().includes("/")){
		summand1 = transform2Fraction(summand1);	
		//print("sum summand1=" + summand1);
	}
	
	if(!summand2.toString().includes("/")){
		summand2 = transform2Fraction(summand2);
	//print("sum summand2=" + summand2);	
	}
	
	//Bring the both summands to equal denominator
	const equalledFractions = equalDenominator(summand1, summand2);
	
	const f1ExpandedSplit = equalledFractions[0].split("/");
	const f2ExpandedSplit = equalledFractions[1].split("/");
	
	const f1Numerator = parseInt(f1ExpandedSplit[0]);
	const f2Numerator = parseInt(f2ExpandedSplit[0]);
	const resultDenominator = parseInt(f2ExpandedSplit[1]);
	
	//print("sum f1Numerator=" + f1Numerator);
	//print("sum f2Numerator=" + f2Numerator);
	//print("sum resultDenominator=" + resultDenominator);
	
	//Sum up and reduce the result fraction
	const resultNumerator = f1Numerator + f2Numerator;
	//print("sum resultNumerator=" + resultNumerator);
	
	if(resultNumerator == 0){
		//print("sum result=" + result);
		return "0";
	}
	
	var result = resultNumerator + "/" + resultDenominator;
	//print("sum result=" + result);
	
	result = reduceFraction(result);
	//print("sum result=" + result);
	
	result = convert2WholeNumber(result);
	//print("sum result=" + result);
	
	return result;
}
/**
* Calculates for the given function_, variable, and variableValue the function value.
* function_ - the function for which the value must be calculated: x^2 + x + 3
* variable - the function variable: x
* variableValue - the value which must set for the variable.
*/
export function calulateFunctionValue(function_,variable,variableValue){
	
	//print("calulateFunctionValue function_=" + function_);
	//print("calulateFunctionValue variable=" + variable);
	//print("calulateFunctionValue variableValue=" + variableValue);
	
	//Validate the input date
	validateFunction(function_);
	validateFunctionVariable(variable);
	validateNumber(variableValue);
	
	//Disaggregate the function in its summands
	const splitFunction = splitMathSumExpression(function_);
	//print("calulateFunctionValue splitFunction=" + splitFunction);
	
	//Initialize return value
	var sum_ = 0;
	
	//Build sum over all function summands.
	splitFunction.forEach(function(summand){
		
		if(summand.toString().includes(variable)){
			
			summand = normalize4Diff(summand, variable);
			
			//operands[0] = factor of variable
			//operands[1] = exponent
			const operands = summand.split(variable + "^");
			//print("calulateFunctionValue operands=" + operands);
			
			const power_ = power(variableValue, operands[1]);
			//print("calulateFunctionValue power=" + power_);
			const product = multiply(power_, operands[0]);
			//print("calulateFunctionValue product=" + product);
			
			sum_ = sum(sum_, product);
		
		} else {
			sum_ = sum(sum_, summand);
		}
		
		//print("calulateFunctionValue sum=" + sum_);
	});
	
	return sum_;
}

/**
 * Differentiates the given expression with respect to the given variable.<br>
 * If one of the parameters is not set (which means is undefined or empty) an
 * error will be raised.<br>
 * <ul>
 * Derivative Rules:
 * <li>expression contains variable -> return 0 </li>
 * <li> expression is only the variable -> return 1</li>
 * <li> expression is only variable^k -> return kvariable^(k-1)</li>
 * <li> expression is avariable^k -> return nvaribale^(k-1) (n == product of k
 * and a)</li>
 * <li>expression is a/bvariable^k -> return n/bvariable^(k-1) (n == product of
 * k and a) </li>
 * Rules for the expressions:
 * <li>exponent is a fraction is not allowed</li>
 * <li>factors are only allowed before the variable</li>
 * <li>expression is a fraction is not allowed</li>
 * <li>other variables beside the function variable are not allowed, e.g
 * avariable^2; a must be number or a fraction</li>
 * <li>products before the variable, e.g. 3*2variable^3 or 3*1/4variable^1, are
 * not allowed</li>
 * <li>the only allowed operators in the expression are: "+", "-" and "^"</li>
 * <li>only numbers are allowed in the fraction before the variable; no
 * variables no operations.</li>
 * </ul>
 */
export function differentiateWithRespectTo(expression, variable){
	
	//print("differentiateWithRespectTo expression=" + expression );
	//print("differentiateWithRespectTo variable=" + variable );
	
	if(expression == null || expression.length == 0){
		throw "expression not set: >" + expression + "<!";
	}
	
	if(variable == null || variable.length == 0){
		throw "variable not set: >" + variable + "<!";
	}
	
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
	
	//print("differentiateWithRespectTo diffExpression=" + diffExpression );
	
	if(diffExpression.length == 0){
		diffExpression = "0";
	}
	
	//print("differentiateWithRespectTo diffExpression=" + diffExpression );
	
	return diffExpression;
}

// #####################################################
// private functions ###################################
// #####################################################
/*
* Check if the given number is either a whole number or a fraction.
* If not an error will be thrown.
*/
function validateNumber(number){
	
	//print("validateNumber number=" + number);
	
	//Validate string if undefined or empty
	if(!validateString(number)){
		throw "number is not set properly";
	}	
	
	//Validate if number or fraction
	if(!isNumberOrFraction(number)){
		throw "number is not a number or fraction";
	}
}

/*
* Return false if the given string is null/undefined or empty.
*/
function validateString(string){

	//Check if string is null/undefind or empty
	return !(string == null || string.length == 0);
}

/*
* Validates the given variable.
* An error is thrown if the variable is not valid.
*/
function validateFunctionVariable(variable){

	//Check if null or empty
	if(!validateString(variable)){
		throw "variable is not set properly";
	}
}
/*
* Validates if the given function is a valid expression.
* An error is thrown if the function_ is not valid.
* Check:
*	If function_ in null or empty.
*/
function validateFunction(function_){
	
	//Validate sting if undefined or empty
	if(!validateString(function_)){
	
		throw "function is not set properly";
	}
}
	
/*
* Raises the given base to the given exponent.
*/
function power(base, exponent){
	
	//print("power base=" + base);
	//print("power exponent=" + exponent);
	
	//Check if fraction and if not transform it
	if(!base.toString().includes("/")){
		base = transform2Fraction(base);
		//print("power base=" + base);
	}
	
	//Split fraction and parse it to integer
	const baseSplit = base.split("/");
	const numerator = parseInt(baseSplit[0]);
	const denominator = parseInt(baseSplit[1]);
	
	//print("power baseSplit=" + baseSplit);
	//print("power numerator=" + numerator);
	//print("power denominator=" + denominator);

	//Check if base and exponent is 0
	if(exponent == "0" && numerator == 0){
		throw "Zero to the power of zero is indeterminate.";
	}
	
	//Power the fraction parts and create the result fraction
	const iexponent = parseInt(exponent);
	const resultNumerator = Math.pow(numerator, iexponent);
	const resultDenominator = Math.pow(denominator, iexponent);
	
	//print("power resultNumerator=" + resultNumerator);
	//print("power resultDenominator=" + resultDenominator);
	
	if(resultNumerator == 0){
		//print("power result=0");
		return "0";
	}
	
	var result = resultNumerator + "/" + resultDenominator;
	
	//print("power result=" + result);
	
	result = reduceFraction(result);
	
	//print("power result=" + result);
	
	result = convert2WholeNumber(result);
	
	//print("power result=" + result);
	
	return result;
}
/*
* Brings both fractions to the same denominator.
*/
function equalDenominator(fraction1, fraction2){
	
	//print("equalDenominator fraction1=" + fraction1);
	//print("equalDenominator fraction2=" + fraction2);
	
	//Split Fractions
	const fraction1Split = fraction1.split("/");
	const fraction2Split = fraction2.split("/");
	
	//Expand Fraction Elements
	var f1Numerator = parseInt(fraction1Split[0]);
	var f1Denominator = parseInt(fraction1Split[1]);
	const f1DenominatorBeforeExtension = f1Denominator;
	
	var f2Numerator = parseInt(fraction2Split[0]);
	var f2Denominator = parseInt(fraction2Split[1]);
	
	//print("equalDenominator f1Numerator=" + f1Numerator);
	//print("equalDenominator f1Denominator=" + f1Denominator);
	//print("equalDenominator f2Numerator=" + f2Numerator);
	//print("equalDenominator f2Denominator=" + f2Denominator);
	
	var f1Numerator = f1Numerator * f2Denominator;
	var f1Denominator = f1Denominator * f2Denominator;
		
	var f2Numerator = f2Numerator * f1DenominatorBeforeExtension;
	var f2Denominator = f2Denominator * f1DenominatorBeforeExtension;
	
	//print("equalDenominator f1Numerator=" + f1Numerator);
	//print("equalDenominator f1Denominator=" + f1Denominator);
	//print("equalDenominator f2Numerator=" + f2Numerator);
	//print("equalDenominator f2Denominator=" + f2Denominator);
	
	//Created Expanded Fractions And Result Array
	const f1Expanded = f1Numerator + "/" + f1Denominator;
	const f2Expanded = f2Numerator + "/" + f2Denominator;
	
	const result = [f1Expanded, f2Expanded];
	
	//print("equalDenominator result=" + result);
	
	return result;
}

/*
* Converts the given fraction to a whole number if possible.
*/
function convert2WholeNumber(fraction){
	
	//print("convert2WholeNumber fraction=" + fraction);
	
	//Check if fraction
	if(!fraction.toString().includes("/")){
		
		//It is already a whole number -> return the fraction variable
		return fraction;
	}
	
	const fractionSplit = fraction.split("/");	
	//print("convert2WholeNumber fractionSplit=" + fractionSplit);
	
	//Check if the fraction can be converted to a whole number
	
	const numerator = parseInt(fractionSplit[0]);
	const denominator = parseInt(fractionSplit[1]);
	//print("convert2WholeNumber numerator=" + numerator);
	//print("convert2WholeNumber denominator=" + denominator);
	
	if(numerator % denominator != 0) {

		//fraction cannot be converted to a whole number.
		return fraction;
	}		
	
	const result = numerator / denominator;
	//print("convert2WholeNumber result=" + result);
	
	return result.toString();
}

/*
 * Concatenates the given partialExpressions to one expression.
 * If a partialExpression starts with "-" the operator will be "-"
 * otherwise "+"
 */
function concatPartialExpressions(partialExpressions){
	
	//print("concatPartialExpressions partialExpressions=" + partialExpressions);
	
	var concatExpression = "";
	var sep = "";
	
	partialExpressions.forEach(function(partialExpression){
		
		//print("concatPartialExpressions partialExpression="  + partialExpression);
		
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
		
		//print("concatPartialExpressions concatExpression=" + concatExpression);
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
	
	//print("splitMathSumExpression expression=" + expression );
	
	expression = expression.replace(/-/g, "$-");
	
	//print("splitMathSumExpression expression=" + expression );
	
	var splitExpression = expression.split("$");
	
	splitExpression = splitExpression.filter(function (el) {
		return el != null && el.length > 0;
	});
	
	//print("splitMathSumExpression splitExpression=" + splitExpression );
	
	var result = new Array();
	
	splitExpression.forEach(function(entry){
		
		//print("splitMathSumExpression entry=" + entry );
		
		var splitEntry = entry.split("+");
		
		//print("splitMathSumExpression splitEntry=" + splitEntry );
		
		Array.prototype.push.apply(result, splitEntry); // result.push(splitEntry);
	});
	
	//print("splitMathSumExpression result=" + result );
	
	return result;
}

/*
 * Reduces the given fraction. Only simple fractiosn like "2/4" are supported.
 * "Double fraction" like "(2/4)/(3/6) are not supported. Variables in the
 * fraction are not supported. It is not checked if the fraction is valid.
 */
function reduceFraction(fraction){
	
	//print("reduceFraction fraction=" + fraction );
	
	// Separate numerator and denominator
	var splitFraction = fraction.split("/");	
	var numerator = parseInt(splitFraction[0]);
	var denominator = parseInt(splitFraction[1]);
	
	//print("reduceFraction numerator=" + numerator );
	//print("reduceFraction denominator=" + denominator );
	
	if(numerator == 0){
		//print("reduceFraction result=" + fraction );
		return fraction;
	}
	
	if (denominator < 0){
		numerator = numerator * -1;
		denominator = denominator * -1;
	}
	
	// Contains the max. possible common factor
	var factor;
	
	if (Math.abs(numerator) > Math.abs(denominator)){
		// Numerator is greater than the denominator -> factor must be <=
		// denominator
		factor = Math.abs(denominator);		
	} else {		
		// Denominator is greater than the numerator -> factor must be <=
		// numerator#
		factor = Math.abs(numerator);
	}
	
	// Search for max. possible factor.
	for(var i = factor; i > 0; i--){
		
		if((numerator % i) == 0 && (denominator % i) == 0 ){
			// When this is entered for the The first time, i must be the max.
			// possible common factor. when
			// This clause will be latest entered, when i == 1, that means the
			// fraction cannot be reduced.
			factor = i;
			break;
		}
	}
	
	//print("reduceFraction factor=" + factor );
	
	// Reduce the numerator and denominator
	numerator = numerator / factor;
	denominator = denominator / factor;
	
	// Built reduced fraction.
	var result = numerator + "/" + denominator;
	
	//print("reduceFraction result=" + result );
	
	return result;
}

/*
 * Multiplies the given fractions. If it is possible the result will be reduced.
 * If the result is whole-number the number will returned and not a fraction. It
 * is not checked if the given fractions are valid.
 */
function multiplyFractions(fraction1, fraction2){
	
	//print("multiplyFractions fraction1=" + fraction1);
	//print("multiplyFractions fraction2=" + fraction2);
	
	var splitF1 = fraction1.split("/");
	var splitF2 = fraction2.split("/");
	
	//Check if at least one numerator is zero
	if(splitF1[0] == "0" || splitF2[0] == "0"){
		//print("multiplyFractions result=0");
		return "0";
	}
	
	// Calculate the the numerator and denominator of the result fraction.
	var resultNumerator = parseInt(splitF1[0]) * parseInt(splitF2[0]);
	var resultDenominator = parseInt(splitF1[1]) * parseInt(splitF2[1]);
	
	//print("multiplyFractions resultNumerator=" + resultNumerator);
	//print("multiplyFractions resultDenominator=" + resultDenominator);
	
	var result;
	
	if (resultNumerator % resultDenominator == 0){
		// In this case the result is whole-number.
		result = (resultNumerator / resultDenominator).toString();
	} else {
		// The result is again a fraction.
		result = reduceFraction(resultNumerator + "/" + resultDenominator);
	}
	
	//print("multiplyFractions result=" + result);
	
	result = convert2WholeNumber(result);
	
	//print("multiplyFractions result=" + result);
	
	return result;
}

/*
 * Multiplies the given multipliers. The multiplier may be whole-numbers or
 * fraction. It is checked if the multipliers are valied. If one of them is not
 * an error while be raised.
 */
function multiply(multiplier1, multiplier2){
	
	 //print("multiply multiplier1=" + multiplier1);
	 //print("multiply multiplier2=" + multiplier2);
	
	var m1Ok = isNumberOrFraction(multiplier1);
	var m2Ok = isNumberOrFraction(multiplier2);
	
	if(!m1Ok || !m2Ok){
		
		throw "Check your input: " + multiplier1 + " and/or " + multiplier2  + "  is not a valid number or fraction.";
	}
	
	if(!multiplier1.toString().includes("/")){
		// Make a fraction of multiplier1.
		multiplier1 = multiplier1 + "/1";
	}
	
	if(!multiplier2.toString().includes("/")){
		// Make a fraction of multiplier2.
		multiplier2 = multiplier2 + "/1";
	}
	
	var result = multiplyFractions(multiplier1, multiplier2);
	
	//print("multiply result=" + result);
	
	return result;
}

/*
 * Checks if the given term is either a valid whole-number or a valid fraction.
 */
function isNumberOrFraction(term){
	
	if(term.toString().includes("/")){
		// Must be a fraction.
		
		var splitTerm = term.split("/");
		var numerator = parseInt(splitTerm[0]);
		var denominator = parseInt(splitTerm[1]);
		
		return !isNaN(numerator) && !isNaN(denominator);
		
	} else {
		// Must be whole-number.
		
		return !isNaN(term);
	}
}

/*
 * Differentiates the given partialExpression with given variable. This methdo
 * supports expressions with "+" and "-" operators. Cases: partialExpression
 * doesn't contains variable (e.g. 4) -> return "" partialExpression doesn't
 * conatins "^" (e.g. 4x or x) -> return 4 or 1 any other expression (e.g. 3x³) ->
 * return 9x²
 */
function differentiatePartial(partialExpression, variable){
	
	//print("differentiatePartial partialExpression=" + partialExpression);
	//print("differentiatePartial variable=" + variable);
	
	if(!partialExpression.toString().includes(variable)){
		
		//print("differentiatePartial result=<empty>");
		// partialExpression doesn't contains variable (e.g. 4) -> return ""
		return "";
	} else {
		
		partialExpression = normalize4Diff(partialExpression, variable);
		
		//print("differentiatePartial partialExpression=" + partialExpression + " (normalized)");
		
		const splitPartialEx = partialExpression.toString().split("^");
		const oldFactor = splitPartialEx[0].replace(variable, "");
		const oldExponent = splitPartialEx[1];
		
		//print("differentiatePartial splitPartialEx=" + splitPartialEx);
		//print("differentiatePartial oldFactor=" + oldFactor);
		//print("differentiatePartial oldExponent=" + oldExponent);
		
		const newExponent = parseInt(oldExponent) - 1;
		const newFactor = multiply(oldFactor, oldExponent);
		
		//print("differentiatePartial newExponent=" + newExponent);
		//print("differentiatePartial newFactor=" + newFactor);
		
		var exponentPart;
		
		if(newExponent > 1){
			 exponentPart =  "^" + newExponent;
		} else {
			exponentPart = "";
			
			if (newExponent == 0){
				variable = "";
			}
		}
		
		const sFactor = (newFactor == "1" && newExponent >0) ? "" : newFactor;
		
		//print("differentiatePartial exponentPart=" + exponentPart);
		//print("differentiatePartial sFactor=" + sFactor);
		
		const result = sFactor + variable + exponentPart;
		
		//print("differentiatePartial result=" + result);
		
		return result;
	}
}
	
/*
 * Transforms the expression to standardized term: e.g: 2.5x -> 5/2x^1
 */
function normalize4Diff(expression, variable){
	
	//print("normalize4Diff expression=" + expression);
	//print("normalize4Diff variable=" + variable);
	
	if(!expression.toString().includes("^")){
		expression = expression + "^1";
		//print("normalize4Diff expression=" + expression);
	}
	
	const splitExpression = expression.toString().split(variable);
	
	const factor = splitExpression[0];
	//print("normalize4Diff factor=" + factor);
	
	if(factor.toString().includes("/")){
		// Factor is already a fraction -> return the expression as it is.
		//print("normalize4Diff result=" + expression);
		return expression;
	} else {
	
		var result;
		
		if(factor.length == 0 || factor == "-"){
			result = factor + "1/1";
		} else {
			// Transform the factor to fraction
			const factorAsAFraction = transform2Fraction(factor);
			//print("normalize4Diff factorAsAFraction=" + factorAsAFraction);
			
			result = factorAsAFraction;
		}
		
		result = result + variable + splitExpression[1];
		//print("normalize4Diff result=" + result);
		return result;
	}
}

/*
 * Transforms the given term to fraction. e.g 4 -> 4/1 e.g. 4.1 -> 41/10 It
 * is not checked if the term is already a term. It is expected that is done
 * by the consumer.
 */
function transform2Fraction(term){
	
	//print("transform2Fraction term=" + term);
	
	if(term.toString().includes(".")){
		
		const termSplit = term.toString().split(".");
		
		const numerator = term.replace(".", "").replace(/^0+/, "");
		const denominator = "1" + "0".repeat(termSplit[1].length);
		
		const fraction = numerator + "/" +  denominator;
		
		//print("transform2Fraction fraction=" + fraction);
		
		const reducedFraction = reduceFraction(fraction);
		
		//print("transform2Fraction reducedFraction=" + reducedFraction);
		
		return reducedFraction;
		
	} else {
	
		const result = term + "/1";
		
		//print("transform2Fraction result=" + result);
		return result;
	}
}