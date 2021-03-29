import {calulateFunctionValue,differentiateWithRespectTo}  from  "/js/math/math.js";
import {convert2MathJax}  from  "/js/mathjax/mathjaxConverter.js";

const PATH_TIME_FUNCTION_ERROR = {	
		mathjax_s_t:"error", 
		mathjax_s_t_result:"error",
		mathjax_v_t:"error",
		mathjax_v_t_result:"error",
		mathjax_a_t:"error",
		mathjax_a_t_result:"error"
};

/**
* Calculates for the given pathTimeFunctionRaw the velocity time
* and the acceleration time function.
* If t is set will also caluclate s, v, and a for t.
*
* pathTimeFunctionRaw: path function input of the user without s(t)
* t: user input for the tim eseconds.
*
* Return value type is a dictionary which is built up as follows:
* result = {
*	mathjax_s_t:<mathjax notation of the given pathTimeFunctionRaw, the s(t) will be prefixed.>, 
*	mathjax_s_t_result:<s function value for t, or empty if t is not set.>,
*	mathjax_v_t:<mathjax notation of the first derivation of pathTimeFunctionRaw, v(t) will be prefixed>,
*	mathjax_v_t_result:<v function value for t, or empty if t is not set.>,
*	mathjax_a_t:<mathjax notation of the second derivation of pathTimeFunctionRaw, a(t) will be prefixed>,
*	mathjax_a_t_result:<a function value for t, or empty if t is not set.>
* }
*
* if pathTimeFunctionRaw is not set correctly, the dictionary PATH_TIME_FUNCTION_ERROR will be returned.
*/
function calculatePathTimeFunction(pathTimeFunctionRaw, t){
	
	//print("calculatePathTimeFunction pathTimeFunctionRaw=" + rawFunction);
	//print("calculatePathTimeFunction t=" + t);
	
	//Check user input
	if(pathTimeFunctionRaw == null || pathTimeFunctionRaw.length == 0){
		//print("calculatePathTimeFunction result=" + PATH_TIME_FUNCTION_ERROR);
		return PATH_TIME_FUNCTION_ERROR;
	}
	
	//Create mathjax for s(t), calculate s for user input t and create mathjax
	const sFromTRaw = "s->t=" + pathTimeFunctionRaw;
	//print("calculatePathTimeFunction sFromTRaw=" + sFromTRaw);
	
	const sResult = calculateTimeFunction(sFromTRaw, t);
	//print("calculatePathTimeFunction sResult=" + sResult);
	
	//differentiate input function -> v(t), create mathjax for v(t)
	//and calculate s for user input t and create mathjax
	const vResult = diffCalculateTimeFunction(pathTimeFunctionRaw, t, "v");
	//print("calculatePathTimeFunction vResult=" + vResult);
	
	//differentiate v(t) to a(t), create mathjax for a(t) 
	//calculate a for user input t  and create mathjax
	const aResult = diffCalculateTimeFunction(vResult[derivation], t, "a"); 
	//print("calculatePathTimeFunction aResult=" + aResult);
	
	//Build result dictionary
	const result = {	
		mathjax_s_t:sResult[mjax], 
		mathjax_s_t_result:sResult[value],
		mathjax_v_t:vResult[mjax],
		mathjax_v_t_result:vResult[value],
		mathjax_a_t:aResult[mjax],
		mathjax_a_t_result:aResult[value]
	}
	//print("calculatePathTimeFunction result=" + result);
	
	return result;
}

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
	
	const derivationExtended = functionValueVar + "->t=";
	//print("diffCalculateTimeFunction derivationExtended=" + derivationExtended);
	
	const result = calculateTimeFunction(derivationExtended + derivation_, t);
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