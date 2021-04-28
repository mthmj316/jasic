import {calulateFunctionValue,differentiateWithRespectTo,sum, divide}  from  "/js/math/math.js";
import {convert2MathJax}  from  "/js/mathjax/mathjaxConverter.js";

/*
* Error return object for calculatePathTimeFunction
*/
const PATH_TIME_FUNCTION_ERROR = {	
		mathjax_s_t:"error", 
		mathjax_s_t_result:"error",
		mathjax_v_t:"error",
		mathjax_v_t_result:"error",
		mathjax_a_t:"error",
		mathjax_a_t_result:"error"
};

/*
* Mapping function value variable to its unit.
*/
const FUNCTION_VALUE_VAR_UNIT_DICT = {
	s:"m",
	v:"{{m}\\over{s}}",
	a:"{{m}\\over{s^2}}"
};

/*
* Contains the pathTimeFunctionRaw value of the last 
* calculatePathTimeFunction function call. 
*/
var S_FROM_T = "";

/*
* Contains the first derivative of the last S_FROM_T. 
*/
var V_FROM_T = "";

/*
* Contains the second derivative of the last S_FROM_T. 
*/
var A_FROM_T = "";

/*
* Contains s for t1 of the last S_FROM_T. 
*/
var S_FOR_T1 = "";

/*
* Contains v for t1 of the last V_FROM_T. 
*/
var V_FOR_T1 = "";

/*
* Contains a for t1 of the last A_FROM_T. 
*/
var A_FOR_T1 = "";

export function calculateWVAWithT1T2(t1, t2){
	
	//print("calculateWVAWithT1T2 t1=" + t1);
	//print("calculateWVAWithT1T2 t2=" + t2);
	
	const wvaForT2 = {s2:"", v2:"", a2:""};
	var deltaAndAverage = {s_t1_t2:"", v_t1_t2:"", a_t1_t2:""};
	
	//Check if t2 is set, convert the algebraic sign of t1, and calculate delta t.  
	
	if(t2.length == 0){
		return [wvaForT2, deltaAndAverage];
	}
	
	if(t1.toString().startsWith("-")){		
		t1 = t1.substring(1);		
	} else {
		t1 = "-" + t1;
	}
	
	//print("calculateWVAWithT1T2 t1=" + t1);
	
	const deltaT = sum(t2,t1);
	//print("calculateWVAWithT1T2 deltaT=" + deltaT);
	
	//Calculate s, v, and a for t2
	
	const sT2Result = convertFunctionValue2MathJax(S_FROM_T, t2, "s");
	//print("calculateWVAWithT1T2 sT2Result.mjax=" + sT2Result.mjax);
	//print("calculateWVAWithT1T2 sT2Result.value=" + sT2Result.value);
	
	const vT2Result = convertFunctionValue2MathJax(V_FROM_T, t2, "v");
	//print("calculateWVAWithT1T2 vT2Result.mjax=" + vT2Result.mjax);
	//print("calculateWVAWithT1T2 vT2Result.value=" + vT2Result.value);
	
	const aT2Result = convertFunctionValue2MathJax(A_FROM_T, t2, "a");
	//print("calculateWVAWithT1T2 aT2Result.mjax=" + aT2Result.mjax);
	//print("calculateWVAWithT1T2 aT2Result.value=" + aT2Result.value);
	
	//Calculate s, v, and a between t1 and t2
	
	const deltaS = sum(sT2Result.value, parseInt(S_FOR_T1) * -1);
	//print("calculateWVAWithT1T2 deltaS=" + deltaS);
	const averageV = divide(deltaS, deltaT);
	//print("calculateWVAWithT1T2 averageV=" + averageV);
	
	const vSummand1 = vT2Result.value + "/" + deltaT;
	//print("calculateWVAWithT1T2 vSummand1=" + vSummand1);
	const vSummand2 = (parseInt(V_FOR_T1) * -1) + "/" + deltaT;
	//print("calculateWVAWithT1T2 vSummand2=" + vSummand2);
	
	const averageA = sum(vSummand1, vSummand2);
	//print("calculateWVAWithT1T2 averageA=" + averageA);
	
	//Create result array with its dictionaries for wva for T2 and delta/avarage wva
	
	wvaForT2.s2 = sT2Result.mjax;
	wvaForT2.v2 = vT2Result.mjax;
	wvaForT2.a2 = aT2Result.mjax;
	
	deltaAndAverage.s_t1_t2 = deltaS;
	deltaAndAverage.v_t1_t2 = averageV;
	deltaAndAverage.a_t1_t2 = averageA;
	
	deltaAndAverage = createMjax4WVADeltaAndAverage(deltaAndAverage);
	
	const result = [wvaForT2, deltaAndAverage];
	
	//print("calculateWVAWithT1T2 wvaForT2.s2=" + wvaForT2.s2);
	//print("calculateWVAWithT1T2 wvaForT2.v2=" + wvaForT2.v2);
	//print("calculateWVAWithT1T2 wvaForT2.a2=" + wvaForT2.a2);
	
	//print("calculateWVAWithT1T2 deltaAndAverage.s_t1_t2=" + deltaAndAverage.s_t1_t2);
	//print("calculateWVAWithT1T2 deltaAndAverage.v_s1_s2=" + deltaAndAverage.v_t1_t2);
	//print("calculateWVAWithT1T2 deltaAndAverage.a_v1_v2=" + deltaAndAverage.a_t1_t2);
	
	return result;	
}

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
export function calculatePathTimeFunction(pathTimeFunctionRaw, t){
	
	//print("calculatePathTimeFunction pathTimeFunctionRaw=" + pathTimeFunctionRaw);
	//print("calculatePathTimeFunction t=" + t);
	
	//Check user input
	if(pathTimeFunctionRaw == null || pathTimeFunctionRaw.length == 0){
		//print("calculatePathTimeFunction result=" + PATH_TIME_FUNCTION_ERROR);
		return PATH_TIME_FUNCTION_ERROR;
	}
	
	S_FROM_T = pathTimeFunctionRaw;
	//print("calculatePathTimeFunction S_FROM_T=" + S_FROM_T);
	
	const sResult = calculateTimeFunction(pathTimeFunctionRaw, t, "s");
//	print("calculatePathTimeFunction sResult.mjax=" + sResult.mjax);
//	print("calculatePathTimeFunction sResult.mjaxValue=" + sResult.mjaxValue);
	
	//differentiate input function -> v(t), create mathjax for v(t)
	//and calculate s for user input t and create mathjax
	const vResult = diffCalculateTimeFunction(pathTimeFunctionRaw, t, "v");
	//print("calculatePathTimeFunction vResult=" + vResult);
	
	V_FROM_T = vResult.derivation;
	//print("calculatePathTimeFunction V_FROM_T=" + V_FROM_T);
	
	//differentiate v(t) to a(t), create mathjax for a(t) 
	//calculate a for user input t  and create mathjax
	const aResult = diffCalculateTimeFunction(vResult.derivation, t, "a"); 
	//print("calculatePathTimeFunction aResult=" + aResult);
	
	A_FROM_T = aResult.derivation;
	
	S_FOR_T1 = sResult.rawValue;
	V_FOR_T1 = vResult.rawValue;
	A_FOR_T1 = aResult.rawValue;
	
//	print("calculatePathTimeFunction S_FOR_T1=" + S_FOR_T1);
//	print("calculatePathTimeFunction V_FOR_T1=" + V_FOR_T1);
//	print("calculatePathTimeFunction A_FOR_T1=" + A_FOR_T1);
	
	//Build result dictionary
	const result = {	
		mathjax_s_t:sResult.mjax, 
		mathjax_s_t_result:sResult.mjaxValue,
		mathjax_v_t:vResult.mjax,
		mathjax_v_t_result:vResult.mjaxValue,
		mathjax_a_t:aResult.mjax,
		mathjax_a_t_result:aResult.mjaxValue
	}
	//print("calculatePathTimeFunction result=" + result);
	
	return result;
}

// #####################################################
// private functions ###################################
// #####################################################
/*
* Helper methode for calculateWVAWithT1T2 to create the mjax
* notations for the delta and average values.
*/
function createMjax4WVADeltaAndAverage(deltaAndAverage){
	
	//print("createMjax4WVADeltaAndAverage deltaAndAverage.s_t1_t2=" + deltaAndAverage.s_t1_t2);
	//print("createMjax4WVADeltaAndAverage deltaAndAverage.v_t1_t2=" + deltaAndAverage.v_t1_t2);
	//print("createMjax4WVADeltaAndAverage deltaAndAverage.a_t1_t2=" + deltaAndAverage.a_t1_t2);
	
	
	//Initialies result dictionary
	const mjaxDeltaAndAverage = {s_t1_t2:"", v_t1_t2:"", a_t1_t2:""}
	
	//Check if s_t1_t2 is set -> if not return the initilized result dictionray
	if(!("s_t1_t2" in deltaAndAverage)){
		
		return mjaxDeltaAndAverage;
	}
	
	//Create mjax for s_t1_t2
	const mjaxST1T2 = "$$\\Delta{s}=" + deltaAndAverage.s_t1_t2 + "\\," + FUNCTION_VALUE_VAR_UNIT_DICT.s + "$$"
	
	//Create mjax for v_t1_t2
	const mjaxVT1T2 = "$$\\bar{v}=" + deltaAndAverage.v_t1_t2 + "\\," + FUNCTION_VALUE_VAR_UNIT_DICT.v + "$$"
	
	//Create mjax for a_t1_t2
	const mjaxAT1T2 = "$$\\bar{a}=" + deltaAndAverage.a_t1_t2 + "\\," + FUNCTION_VALUE_VAR_UNIT_DICT.a + "$$"
	
	//Set mjax values in the result dictionary
	mjaxDeltaAndAverage.s_t1_t2 = mjaxST1T2;
	mjaxDeltaAndAverage.v_t1_t2 = mjaxVT1T2; 
	mjaxDeltaAndAverage.a_t1_t2 = mjaxAT1T2;
	
	return mjaxDeltaAndAverage;
}
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
	
	const result = calculateTimeFunction(derivation_, t, functionValueVar);
	//print("diffCalculateTimeFunction result=" + result);
	
	result.derivation = derivation_;
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
function calculateTimeFunction(rawFunction, t, functionValueVar){
	
	//print("calculateTimeFunction rawFunction=" + rawFunction);
	//print("calculateTimeFunction t=" + t);
	//print("calculateTimeFunction functionValueVar=" + functionValueVar);

	const rawFunctionExtended = functionValueVar + "->t=" + rawFunction;
	//print("calculateTimeFunction rawFunctionExtended=" + rawFunctionExtended);

	const functionConversionResult = convert2MathJax(rawFunctionExtended);
	//print("calculateTimeFunction functionConversionResult=" + functionConversionResult);

	const functionValueConversionResult = convertFunctionValue2MathJax(rawFunction, t, functionValueVar);
	//print("calculateTimeFunction functionValueConversionResult.mathjax=" + functionValueConversionResult.mjax);
	//print("calculateTimeFunction functionValueConversionResult.value=" + functionValueConversionResult.value);
	
	const result = {
		mjax:functionConversionResult,
		mjaxValue:functionValueConversionResult.mjax,
		rawValue:functionValueConversionResult.value
	};
	//print("calculateTimeFunction result=" + result);
	
	return result;
}

/*
* Calculates for t the corrsponding function value and creates
* the term as follows: e.g. s(t=<t>)= 4 m as mathjax notation.
*
* If t is not set, an empty mathjax notation only will be returned
*/
function convertFunctionValue2MathJax(rawFunction, t, functionValueVar){
	
	//print("convertFunctionValue2MathJax rawFunction=" + rawFunction);
	//print("convertFunctionValue2MathJax t=" + t);
	//print("convertFunctionValue2MathJax functionValueVar=" + functionValueVar);
	
	var rawFunctionValue = "";
	
	var value = "";
		
	if(t != null && t.length > 0){
		value = calulateFunctionValue(rawFunction, "t", t);
		rawFunctionValue = functionValueVar + "->X=" + value + " U";
	}
	
	//print("convertFunctionValue2MathJax rawFunctionValue=" + rawFunctionValue);
	
	var mathjaxFunctionValue = convert2MathJax(rawFunctionValue);
	
	//print("convertFunctionValue2MathJax mathjaxFunctionValue=" + mathjaxFunctionValue);
	
	if(rawFunctionValue != ""){
		mathjaxFunctionValue = mathjaxFunctionValue.replace("U", FUNCTION_VALUE_VAR_UNIT_DICT[functionValueVar]);
		mathjaxFunctionValue = mathjaxFunctionValue.replace("X", "t=" + t);
	}
	
	//print("convertFunctionValue2MathJax mathjaxFunctionValue=" + mathjaxFunctionValue);
	
	const result = {
		mjax:mathjaxFunctionValue,
		value:value
	};
	
	//print("convertFunctionValue2MathJax result=" + result);
	
	return result;
}