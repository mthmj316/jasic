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
	void Testfall_001_DifferentiateWithRespectTo() {
		
		String expression = null;
		String variable = "x";
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("expression"));
	}
	
	@Test
	void Testfall_002_DifferentiateWithRespectTo() {
		
		String expression = "2x^2";
		String variable = null;
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("variable"));
	}
	
	@Test
	void Testfall_003_DifferentiateWithRespectTo() {
		
		String expression = "";
		String variable = "x";
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("expression"));
	}
	
	@Test
	void Testfall_004_DifferentiateWithRespectTo() {
		
		String expression = "2x^2";
		String variable = "";
		
		Object[] params = new Object[] {expression, variable};
		JavaScriptException exception = assertThrows(JavaScriptException.class, () -> fct.call(ctx, globalScope, globalScope, params));
		
		assertTrue(exception.getMessage().contains("variable"));
	}
	
	@Test
	void Testfall_006_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "2";
		String variable = "x";
		
		String expected = "0";
		
		Object[] params = new Object[] {expression, variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_007_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "x";
		String variable = "x";
		
		String expected = "1";
		
		Object[] params = new Object[] {expression, variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_008_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "x^1";
		String variable = "x";
		
		String expected = "1";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_009_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "x^2";
		String variable = "x";
		
		String expected = "2x";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_010_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_011_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x^1";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_012_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x^2";
		String variable = "x";
		
		String expected = "4x";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_012_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "-2x^2";
		String variable = "x";
		
		String expected = "-4x";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_014_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x+2";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_015_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x-2";
		String variable = "x";
		
		String expected = "2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_019_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "2x^3+4x+3";
		String variable = "x";
		
		String expected = "6x^2+4";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_016_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "3x^2+2/4x+3";
		String variable = "x";
		
		String expected = "6x+1/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_017_DifferentiateWithRespectTo() throws NoSuchMethodException, ScriptException {
		
		String expression = "x^3-5/2x^2+4x-4";
		String variable = "x";
		
		String expected = "3x^2-5x+4";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_020_DifferentiateWithRespectTo()throws NoSuchMethodException, ScriptException {
		
		String expression = "-3/4qwertz^10-7/9qwertz^9-2/4qwertz-100000000";
		String variable = "qwertz";
		
		String expected = "-15/2qwertz^9-7qwertz^8-1/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_045_DifferentiateWithRespectTo()throws NoSuchMethodException, ScriptException {
		
		String expression = "2.04x^2+1.5x";
		String variable = "x";
		
		String expected = "102/25x+3/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void Testfall_046_DifferentiateWithRespectTo()throws NoSuchMethodException, ScriptException {
		
		String expression = "-2.04x^2-1.5x";
		String variable = "x";
		
		String expected = "-102/25x-3/2";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	} 
	
	@Test
	void Testfall_047_DifferentiateWithRespectTo()throws NoSuchMethodException, ScriptException {
		
		String expression = "0.003x^3+0.04x^2-0.1x+0.231";
		String variable = "x";
		
		String expected = "9/1000x^2+2/25x-1/10";
		
		Object[] params = new Object[] {Context.javaToJS(expression, globalScope), variable};
		String actual = String.valueOf(fct.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	} 
}