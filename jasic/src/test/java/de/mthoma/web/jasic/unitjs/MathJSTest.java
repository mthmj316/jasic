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
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

class MathJSTest {
	
//	private static final ScriptEngine JS_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
//	private static Invocable invocable;
	private static Context ctx;
	private static Scriptable globalScope;
	private static Function fct;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		String javaScript = FileUtils.readFileToString(new File("src/main/resources/static/js/math/math.js"), StandardCharsets.UTF_8);
		
		javaScript = javaScript.replaceAll(Pattern.quote("export function"), "function");
//		
//		JS_ENGINE.eval(javaScript);
//		invocable = (Invocable) JS_ENGINE;
		
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
	
	@Test
	void Testfall_001_ExpressionUndefined() {
		
		String expression = null;
		String variable = "x";
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("expression"));
	}
	
	@Test
	void Testfall_002_VariableUndefined() {
		
		String expression = "2x^2";
		String variable = null;
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("variable"));
	}
	
	@Test
	void Testfall_003_ExpressionEmpty() {
		
		String expression = "";
		String variable = "x";
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("expression"));
	}
	
	@Test
	void Testfall_004_VariableEmpty() {
		
		String expression = "2x^2";
		String variable = "";
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("variable"));
	}
	
	@Test
	void Testfall_006_DifferentiateNumber() throws NoSuchMethodException, ScriptException {
		
		String expression = "2";
		String variable = "x";
		
		String expected = "0";
		
		Object[] params = new Object[] {expression, variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_007_Differentiate_x() throws NoSuchMethodException, ScriptException {
		
		String expression = "x";
		String variable = "x";
		
		String expected = "1";
		
		Object[] params = new Object[] {expression, variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_008_Differentiate_xpow1() throws NoSuchMethodException, ScriptException {
		
		String expression = "x^1";
		String variable = "x";
		
		String expected = "1";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_008_Differentiate_xpow2() throws NoSuchMethodException, ScriptException {
		
		String expression = "x^2";
		String variable = "x";
		
		String expected = "2x";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_010_Differentiate_2x() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_011_Differentiate_2xpow1() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x^1";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_012_Differentiate_2xpow2() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x^2";
		String variable = "x";
		
		String expected = "4x";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_012_Differentiate_minus2xpow2() throws NoSuchMethodException, ScriptException {
		
		String expression = "-2x^2";
		String variable = "x";
		
		String expected = "-4x";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_014_Differentiate_2xplus2() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x+2";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_015_Differentiate_2xminus2() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x-2";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_019_Differentiate_2xpow3plus4xplus3() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x^3+4x+3";
		String variable = "x";
		
		String expected = "6x^2+4";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_016_Differentiate_3xpow2plus2div4xplus3() throws NoSuchMethodException, ScriptException {
		
		String expression = "3x^2+2/4x+3";
		String variable = "x";
		
		String expected = "6x+1/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_017_Differentiate_xpow3minus5div2xpow2plus4xminus4() throws NoSuchMethodException, ScriptException {
		
		String expression = "x^3-5/2x^2+4x-4";
		String variable = "x";
		
		String expected = "3x^2-5x+4";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_020_Differentiate_mi3div4qwertzpow10mi7div9qwertzpow9mi2div4qwertzmi100000000()throws NoSuchMethodException, ScriptException {
		
		String expression = "-3/4qwertz^10-7/9qwertz^9-2/4qwertz-100000000";
		String variable = "qwertz";
		
		String expected = "-15/2qwertz^9-7qwertz^8-1/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_045_Differentiate_2Dot04xpow2pl1Dot5x()throws NoSuchMethodException, ScriptException {
		
		String expression = "2.04x^2+1.5x";
		String variable = "x";
		
		String expected = "102/25x+3/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_045_Differentiate_mi2Dot04xpow2mi1Dot5x()throws NoSuchMethodException, ScriptException {
		
		String expression = "-2.04x^2-1.5x";
		String variable = "x";
		
		String expected = "-102/25x-3/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	} 
}