package de.mthoma.web.jasic.js.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

class MathJSTest {
	
	private static Context ctx;
	private static Scriptable globalScope;
	private static Function differentiateWithRespectTo;
	private static Function calulateFunctionValue;
	private static Function sum;
	private static Function divide;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		String javaScript = FileUtils.readFileToString(new File("src/main/resources/static/js/math/math.js"), StandardCharsets.UTF_8);
		
		javaScript = javaScript.replaceAll(Pattern.quote("export function"), "function");
		
		ctx = Context.enter();
		ctx.getWrapFactory().setJavaPrimitiveWrap(true);
		globalScope = new Global(ctx);
		ctx.evaluateString(globalScope, javaScript, "math.js", 1, null);
		
		differentiateWithRespectTo = (Function)globalScope.get("differentiateWithRespectTo", globalScope);
		calulateFunctionValue = (Function)globalScope.get("calulateFunctionValue", globalScope);
		sum = (Function)globalScope.get("sum", globalScope);
		divide = (Function)globalScope.get("divide", globalScope);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		Context.exit();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "math_divide_TestData.csv", numLinesToSkip = 1)
	void testDivide(String testCase, String numerator, String denominator, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {numerator, denominator};
		String actual = String.valueOf(divide.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "math_divide_ErrorTestData.csv", numLinesToSkip = 1)
	void testErrorDivide(String testCase, String numerator, String denominator, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {numerator, denominator};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> divide.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().startsWith(expected));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "math_sum_TestData.csv", numLinesToSkip = 1)
	void testSum(String testCase, String summand1, String summand2, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {summand1, summand2};
		String actual = String.valueOf(sum.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "math_sum_ErrorTestData.csv", numLinesToSkip = 1)
	void testErrorSum(String testCase, String summand1, String summand2, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {summand1, summand2};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> sum.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().startsWith(expected));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "differentiateWithRespectTo_ErrorTestData.csv", numLinesToSkip = 1)
	void testErrorDifferentiateWithRespectTo(String testCase, String expression, String variable, String contains)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> differentiateWithRespectTo.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains(contains));
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "differentiateWithRespectTo_TestData.csv", numLinesToSkip = 1)
	void testDifferentiateWithRespectTo(String testCase, String expression, String variable, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(differentiateWithRespectTo.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "calulateFunctionValue_TestData.csv", numLinesToSkip = 1)
	void testCalulateFunctionValue(String testCase, String function, String variable, 
			String variableValue, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {function, variable, variableValue};
		String actual = String.valueOf(calulateFunctionValue.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "calulateFunctionValue_ErrorTestData.csv", numLinesToSkip = 1)
	void testErrorCalulateFunctionValue(String testCase, String function, String variable, String variableValue, String contains)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {function, variable, variableValue};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> calulateFunctionValue.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains(contains));
	}
}