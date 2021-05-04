package de.mthoma.web.jasic.js.unittests.mathjaxconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

class MathJaxConverterTest {
	
	private static Context ctx;
	private static Scriptable globalScope;
	private static Function convert2MathJax;
	private static Function convertResultLine;
	
	private static final String JS_FILE = "mathjaxConverter.js";
	private static final String PATH_2_JS = "src/main/resources/static/js/mathjax/" + JS_FILE;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		String javaScript = FileUtils.readFileToString(new File(PATH_2_JS), StandardCharsets.UTF_8);
		
		javaScript = javaScript.replaceAll(Pattern.quote("export function"), "function");
		
		ctx = Context.enter();
		ctx.getWrapFactory().setJavaPrimitiveWrap(true);
		globalScope = new Global(ctx);
		ctx.evaluateString(globalScope, javaScript, JS_FILE, 1, null);
		
		convert2MathJax = (Function)globalScope.get("convert2MathJax", globalScope);
		convertResultLine = (Function)globalScope.get("convertResultLine", globalScope);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		Context.exit();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "convertResultLine_TestData.csv", numLinesToSkip = 1)
	void testConvertResultLine(String testCase, String rawExpression, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {Context.javaToJS(rawExpression, globalScope)};
		String actual = String.valueOf(convertResultLine.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "convert2MathJax_TestData.csv", numLinesToSkip = 1)
	void testConvert2MathJax(String testCase, String rawExpression, String expected)throws NoSuchMethodException, ScriptException {
		
		Object[] params = new Object[] {Context.javaToJS(rawExpression, globalScope)};
		String actual = String.valueOf(convert2MathJax.call(ctx, globalScope, globalScope, params));
		
		assertEquals(expected, actual);
	}
}