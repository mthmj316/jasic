/**
 * 
 */
import {calculatePathTimeFunction, calculateWVAWithT1T2}  from  "/js/model/kinematics.js";
                              
window.onCalculateExtended = function onCalculateExtended(document){
	
	const t1 = document.getElementById("t1_value_input").value;	
	if(t1.length == 0){
		return;
	}
	
	const t2 = document.getElementById("t2_value_input").value;
	
	if(t2.length > 0 && isNaN(t2)){
		window.alert("'" + t2 + "' ist kein erlaubter Wert!")
		return;
	}
	
	try {
		
		const resultArray = calculateWVAWithT1T2(t1, t2);
		
		const wvaForT2 = resultArray[0];
		const deltaAndAverage = resultArray[1];
		
		document.getElementById("way_for_t2").innerText = wvaForT2.s2;
		document.getElementById("way_between_t1_and_t2").innerText = deltaAndAverage.s_t1_t2;
		
		document.getElementById("speed_for_t2").innerText = wvaForT2.v2;
		document.getElementById("average_speed_between_t1_and_t2").innerText = deltaAndAverage.v_t1_t2;
		
		document.getElementById("acceleration_for_t2").innerText = wvaForT2.a2;
		document.getElementById("average_acceleration_between_t1_and_t2").innerText = deltaAndAverage.a_t1_t2;
				
		MathJax.Hub.Queue(['Typeset',MathJax.Hub]);
		
	} catch (error){
		window.alert("Ein unerwarteter Fehler ist aufgetreten.\nBitte notieren Sie Ihre Eingabe und melden den Fehler: " + error);
		return;
	}
}

window.onCalculate = function onCalculate(document){
	
	//Get s from t from user input and check if it is set.
	const sFromT = document.getElementById("way_function_input").value;	
	if(sFromT.length == 0){
		//it is not set --> abrot and return an error message.
		window.alert("s(t) ist nicht definiert!");
		return;
	}
	
	//Get t from user input and check if it is a number.
	const t = document.getElementById("t1_value_input").value;	
	if(t.length > 0 && isNaN(t)){
		window.alert("'" + t + "' ist kein erlaubter Wert!")
		return;
	}
	
	//Perform the calculation, check the result for error, and write the results to the front end
	try {
		
		var resultDict = calculatePathTimeFunction(sFromT, t);
		
		if(resultDict.mathjax_s_t == "error"){
			window.alert("Ein unerwarteter Fehler ist aufgetreten.\nBitte notieren Sie Ihre Eingabe und melden den Fehler.");
			return ;
		}
		
		document.getElementById("way_function_mathjax").innerText = resultDict.mathjax_s_t;
		document.getElementById("way_for_t1").innerText = resultDict.mathjax_s_t_result;
		
		document.getElementById("speed_function_mathjax").innerText = resultDict.mathjax_v_t;
		document.getElementById("speed_for_t1").innerText = resultDict.mathjax_v_t_result;
		
		document.getElementById("acceleration_function_mathjax").innerText = resultDict.mathjax_a_t;
		document.getElementById("acceleration_for_t1").innerText = resultDict.mathjax_a_t_result;
		
		MathJax.Hub.Queue(['Typeset',MathJax.Hub]);
		
	} catch (error){
		window.alert("Ein unerwarteter Fehler ist aufgetreten.\nBitte notieren Sie Ihre Eingabe und melden den Fehler: " + error);
		return;
	}
}