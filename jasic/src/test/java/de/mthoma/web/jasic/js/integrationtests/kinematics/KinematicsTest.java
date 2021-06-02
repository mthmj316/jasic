package de.mthoma.web.jasic.js.integrationtests.kinematics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

class KinematicsTest {
	
	private static Context ctx;
	private static Scriptable globalScope;
	private static Function calculatePathTimeFunction;
	private static Function calculateWVAWithT1T2;
	
	private static final String JS_ROOT = "src/main/resources/static/js/";
	private static final String JS_FILE = "kinematics.js";
	private static final String PATH_2_JS = JS_ROOT + "model/" + JS_FILE;
	
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
		calculateWVAWithT1T2 = (Function)globalScope.get("calculateWVAWithT1T2", globalScope);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Context.exit();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "kinematics_calculateWVAWithT1T2_TestData.csv", numLinesToSkip = 1)
	void testCalculateWVAWithT1T2(String testcase, 
		String sFromT, String t1, String t2, 
		String s2, String v2, String a2, 
		String s_t1_t2, String v_t1_t2, String a_t1_t2)
		throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {sFromT,t1};
		calculatePathTimeFunction.call(ctx, globalScope, globalScope, params);
		
		Map<Object, Object> expectedWVA4T2 = new HashMap<Object,Object>();
		expectedWVA4T2.put("s2", s2);
		expectedWVA4T2.put("v2", v2);
		expectedWVA4T2.put("a2", a2);
		
		Map<Object, Object> expectedDeltasAverages = new HashMap<Object,Object>();
		expectedDeltasAverages.put("s_t1_t2", s_t1_t2);
		expectedDeltasAverages.put("v_t1_t2", v_t1_t2);
		expectedDeltasAverages.put("a_t1_t2", a_t1_t2);
		
		params = new Object[] {t1,t2};
		NativeArray actual = (NativeArray) calculateWVAWithT1T2.call(ctx, globalScope, globalScope, params);
		
		assertEquals(2, actual.getLength());
		
		Set<Entry<Object, Object>> actualWVA4T2Set = ((NativeObject) actual.get(0)).entrySet();
		for(Entry<Object, Object> entry : actualWVA4T2Set) {
			assertEquals(String.valueOf(expectedWVA4T2.get(entry.getKey())), String.valueOf(entry.getValue()));
		}
		
		Set<Entry<Object, Object>> actualDeltaAverageSet = ((NativeObject) actual.get(1)).entrySet();
		for(Entry<Object, Object> entry : actualDeltaAverageSet) {
			assertEquals(String.valueOf(expectedDeltasAverages.get(entry.getKey())), String.valueOf(entry.getValue()));
		}
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "calculatePathTimeFunction_ErrorTestData.csv", numLinesToSkip = 1)
	void testCalculatePathTimeFunctionError(
		String testcase, String s_t_input, String t_input, String mathjax_s_t, String mathjax_s_t_result,
		String mathjax_v_t, String mathjax_v_t_result, String mathjax_a_t, String mathjax_a_t_result)
		throws NoSuchMethodException, ScriptException {
		
		Map<Object, Object> expected = new HashMap<Object,Object>();
		expected.put("mathjax_s_t", mathjax_s_t);
		expected.put("mathjax_s_t_result", mathjax_s_t_result);
		expected.put("mathjax_v_t", mathjax_v_t);
		expected.put("mathjax_v_t_result", mathjax_v_t_result);
		expected.put("mathjax_a_t", mathjax_a_t);
		expected.put("mathjax_a_t_result", mathjax_a_t_result);
		
		Object[] params = new Object[] {s_t_input,t_input};
		NativeObject actual = (NativeObject) calculatePathTimeFunction.call(ctx, globalScope, globalScope, params);
		
		Set<Entry<Object, Object>> actualSet = actual.entrySet();
		
		for(Entry<Object, Object> entry : actualSet) {
			assertEquals(String.valueOf(expected.get(entry.getKey())), String.valueOf(entry.getValue()));
		}
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "calculatePathTimeFunction_TestData.csv", numLinesToSkip = 1)
	void testCalculatePathTimeFunction(
		String testcase, String s_t_input, String t_input, String mathjax_s_t, String mathjax_s_t_result,
		String mathjax_v_t, String mathjax_v_t_result, String mathjax_a_t, String mathjax_a_t_result)
		throws NoSuchMethodException, ScriptException {
		
		Map<Object, Object> expected = new HashMap<Object,Object>();
		expected.put("mathjax_s_t", mathjax_s_t);
		expected.put("mathjax_s_t_result", mathjax_s_t_result);
		expected.put("mathjax_v_t", mathjax_v_t);
		expected.put("mathjax_v_t_result", mathjax_v_t_result);
		expected.put("mathjax_a_t", mathjax_a_t);
		expected.put("mathjax_a_t_result", mathjax_a_t_result);
		
		Object[] params = new Object[] {s_t_input,t_input};
		NativeObject actual = (NativeObject) calculatePathTimeFunction.call(ctx, globalScope, globalScope, params);
		
		Set<Entry<Object, Object>> actualSet = actual.entrySet();
		
		for(Entry<Object, Object> entry : actualSet) {
			assertEquals(String.valueOf(expected.get(entry.getKey())), String.valueOf(entry.getValue()));
		}
	}
}