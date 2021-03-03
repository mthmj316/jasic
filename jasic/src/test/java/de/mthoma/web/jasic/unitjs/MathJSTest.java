package de.mthoma.web.jasic.unitjs;

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
import org.junit.jupiter.api.Test;
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
	private static Function fct;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		String javaScript = FileUtils.readFileToString(new File("src/main/resources/static/js/math/math.js"), StandardCharsets.UTF_8);
		
		javaScript = javaScript.replaceAll(Pattern.quote("export function"), "function");
		
		ctx = Context.enter();
		ctx.getWrapFactory().setJavaPrimitiveWrap(true);
		globalScope = new Global(ctx); //ctx.initStandardObjects();
		ctx.evaluateString(globalScope, javaScript, "math.js", 1, null);
		
		fct = (Function)globalScope.get("differentiateWithRespectTo", globalScope);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		Context.exit();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "differentiateWithRespectTo_ErrorTestData.csv", numLinesToSkip = 1)
	void testErrorDifferentiateWithRespectTo(String testCase, String expression, String variable, String contains)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains(contains));
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "differentiateWithRespectTo_TestData.csv", numLinesToSkip = 1)
	void testDifferentiateWithRespectTo(String testCase, String expression, String variable, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
}