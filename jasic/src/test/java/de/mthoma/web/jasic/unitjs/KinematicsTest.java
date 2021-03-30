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

class KinematicsTest {
	
	private static Context ctx;
	private static Scriptable globalScope;
	private static Function calculatePathTimeFunction;
	
	private static final String JS_ROOT = "src/main/resources/static/js/";
	private static final String JS_FILE = "kinematics.js";
	private static final String PATH_2_JS = JS_ROOT + JS_FILE;
	
	private static final String JS_FILE_MATHJAX_CONVERTER = "mathjaxConverter.js";
	private static final String PATH_MATHJAX_CONVERTER = JS_ROOT + "mathjax/" + JS_FILE_MATHJAX_CONVERTER;
	
	private static final String JS_FILE_MATH = "math.js";
	private static final String PATH_MATH = JS_ROOT +  "math/" + JS_FILE_MATH;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		String javaScript = FileUtils.readFileToString(new File(PATH_2_JS), StandardCharsets.UTF_8);
		javaScript = javaScript.replaceAll(Pattern.quote("export function"), "function");
		javaScript = javaScript.replaceAll(Pattern.quote("import"), "//import");
		
		String mathjaxConverter = FileUtils.readFileToString(new File(PATH_MATHJAX_CONVERTER), StandardCharsets.UTF_8);
		mathjaxConverter = mathjaxConverter.replaceAll(Pattern.quote("export function"), "function");
		
		String math = FileUtils.readFileToString(new File(PATH_MATH), StandardCharsets.UTF_8);
		math = math.replaceAll(Pattern.quote("export function"), "function");
		
		ctx = Context.enter();
		ctx.getWrapFactory().setJavaPrimitiveWrap(true);
		globalScope = new Global(ctx);
		ctx.evaluateString(globalScope, javaScript, JS_FILE, 1, null);
		ctx.evaluateString(globalScope, mathjaxConverter, JS_FILE_MATHJAX_CONVERTER, 1, null);
		ctx.evaluateString(globalScope, math, JS_FILE_MATH, 1, null);
		
		calculatePathTimeFunction = (Function)globalScope.get("calculatePathTimeFunction", globalScope);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Context.exit();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "calculatePathTimeFunction_TestData.csv", numLinesToSkip = 1)
	void testCalculatePathTimeFunction(
		String testcase, String s_t_input, String t_input, String mathjax_s_t, String mathjax_s_t_result,
		String mathjax_v_t, String mathjax_v_t_result, String mathjax_a_t, String mathjax_a_t_result)
		throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {s_t_input,t_input};
		Object actual = String.valueOf(calculatePathTimeFunction.call(ctx, globalScope, globalScope, params));
		
		assertEquals(null, actual);
	}
}