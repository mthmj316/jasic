/**
 * 
 */
import {calculatePathTimeFunction}  from  "/js/model/kinematics.js";

window.onCalculate = function onCalculate(document){
	
	//Get s from t from user input and check if it is set.
	const sFromT = document.getElementById("way_function_input").value;	
	if(sFromT.length == 0){
		//it is not set --> abrot and return an error message.
		window.alert("s(t) ist nicht definiert!");
		return;
	}
	
	//Get t from user input and check if it is a number.
	const t = document.getElementById("time_value_input").value;	
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
		document.getElementById("way_for_time_value_input").innerText = resultDict.mathjax_s_t_result;
		
		document.getElementById("speed_function_mathjax").innerText = resultDict.mathjax_v_t;
		document.getElementById("speed_for_time_value_input").innerText = resultDict.mathjax_v_t_result;
		
		document.getElementById("acceleration_function_mathjax").innerText = resultDict.mathjax_a_t;
		document.getElementById("acceleration_for_time_value_input").innerText = resultDict.mathjax_a_t_result;
		
		MathJax.Hub.Queue(['Typeset',MathJax.Hub]);
		
	} catch (error){
		window.alert("Ein unerwarteter Fehler ist aufgetreten.\nBitte notieren Sie Ihre Eingabe und melden den Fehler: " + error);
		return;
	}
}