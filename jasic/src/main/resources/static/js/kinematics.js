import {convert2MathJax}  from  "/js/mathjax/mathjaxConverter.js";
import {calulateFunctionValue,differentiateWithRespectTo}  from  "/js/math/math.js";

// #####################################################
// private functions ###################################
// #####################################################
/*
* Differntiates the given rawFunction and afterwards
* it calls the function calculateTimeFunction(rawFunction, t)
* to convert the derivation to mathjax notation and 
* calculates the function value if t is set poperly.
* return the the following dictionary:
* {mjax:functionMathJax,value:functionValue,derivation:derivation_}
*/
function diffCalculateTimeFunction(rawFunction, t, functionValueVar){
	
	//print("diffCalculateTimeFunction rawFunction=" + rawFunction);
	//print("diffCalculateTimeFunction t=" + t);
	//print("diffCalculateTimeFunction functionValueVar=" + functionValueVar);
	
	const derivation_ = differentiateWithRespectTo(rawFunction, "t");
	//print("diffCalculateTimeFunction derivation_=" + derivation_);
	
	const result = calculateTimeFunction(functionValueVar + "->t=" + derivation_, t);
	//print("diffCalculateTimeFunction result=" + result);
	
	result[derivation] = derivation_;
	//print("diffCalculateTimeFunction result=" + result);
	
	return result;
}

/*
* Converts the given rawFunction into MathJax notation,
* and calculates the function value for the given t-value.
* If t is not set or not a number it'll be ignored.
* return the the following dictionary:
* {mjax:functionMathJax,value:functionValue}
* if t i not a number:
* {mjax:functionMathJax,value:""}
*/
function calculateTimeFunction(rawFunction, t){
	
	//print("calculateTimeFunction rawFunction=" + rawFunction);
	//print("calculateTimeFunction t=" + t);

	const functionMathJax = convert2MathJax(rawFunction);
	//print("calculateTimeFunction t=" + t);
	
	var functionValue = "";
	
	if(!isNaN(t)){
		
		functionValue = calulateFunctionValue(rawFunction, "t", t);
	}
	//print("calculateTimeFunction functionValue=" + functionValue);
	
	const result = {
		mjax:functionMathJax,
		value:functionValue
	};
	//print("calculateTimeFunction result=" + result);
	
	return result;
}