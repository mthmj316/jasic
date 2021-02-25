package de.mthoma.web.jasic.unitjs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MathJSTest {
	
	private static final ScriptEngine JS_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
	private static Invocable invocable;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		String javaScript = FileUtils.readFileToString(new File("src/main/resources/static/js/math/math.js"), StandardCharsets.UTF_8);
		
		javaScript = javaScript.replaceAll(Pattern.quote("export function"), "function");
		
		JS_ENGINE.eval(javaScript);
		invocable = (Invocable) JS_ENGINE;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testfall001ExpressionUndefined() {
		
		String expression = null;
		String variable = "x";
		
		ScriptException exception = assertThrows(ScriptException.class, () -> invocable.invokeFunction(
				"differentiateWithRespectTo", expression, variable));
		
		assertTrue(exception.getMessage().contains("expression"));
	}
	
	@Test
	void testfall002VariableUndefined() {
		
		String expression = "2x^2";
		String variable = null;
		
		ScriptException exception = assertThrows(ScriptException.class, () -> invocable.invokeFunction(
				"differentiateWithRespectTo", expression, variable));
		
		assertTrue(exception.getMessage().contains("variable"));
	}
	
	@Test
	void testfall003ExpressionEmpty() {
		
		String expression = "";
		String variable = "x";
		
		ScriptException exception = assertThrows(ScriptException.class, () -> invocable.invokeFunction(
				"differentiateWithRespectTo", expression, variable));
		
		assertTrue(exception.getMessage().contains("expression"));
	}
	
	@Test
	void testfall004VariableEmpty() {
		
		String expression = "2x^2";
		String variable = "";
		
		ScriptException exception = assertThrows(ScriptException.class, () -> invocable.invokeFunction(
				"differentiateWithRespectTo", expression, variable));
		
		assertTrue(exception.getMessage().contains("variable"));
	}
	
	@Test
	void testfall006DifferentiateNumber() throws NoSuchMethodException, ScriptException {
		
		String expression = "2";
		String variable = "x";
		
		String expected = "0";
		String actual = String.valueOf(invocable.invokeFunction("differentiateWithRespectTo", expression, variable));
		
		assertEquals(expected, actual);
	}
}
