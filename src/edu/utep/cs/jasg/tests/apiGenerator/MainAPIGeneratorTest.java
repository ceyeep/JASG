/*******************************************************************************
 * Copyright (c) 2012 Cesar Yeep.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD 3-Clause License
 * ("New BSD" or "BSD Simplified") which accompanies this distribution,
 * and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Contributors:
 *     Cesar Yeep - initial API and implementation
 ******************************************************************************/
package edu.utep.cs.jasg.tests.apiGenerator;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.utep.cs.jasg.FileFactory;
import edu.utep.cs.jasg.apiGenerator.MainAPIGenerator;
import edu.utep.cs.jasg.testFramework.FileComparator;

/**
 * Test XMLParser
 * @author Yeep
 *
 */
public class MainAPIGeneratorTest {

	private static String workspace = "test"+File.separator+"TestWorkspace";
	private static String targetModule = "test"+File.separator+"StateMachineTestModule";
	private static String testDocName = "StateMachineDoc";
	private static String scannerName = "StateMachineScanner";
	private static String parserName = "StateMachineParser";
	private static String scannerPath = targetModule+File.separator+"scanner"+File.separator+scannerName+".flex";
	private static String parserPath = targetModule+File.separator+"parser"+File.separator+parserName+".all";
	private String filePath = workspace+File.separator+"doc";
	private String controlFile = "test"+File.separator+"DocSample"+File.separator+"StateMachineDoc.xml";
	private String controlFileFalse = "test"+File.separator+"DocSample"+File.separator+"StateMachineDocIncorrect.xml";

	private static FileFactory fileFactory = new FileFactory(workspace);
	private static MainAPIGenerator apiGenerator = new MainAPIGenerator(workspace);
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		apiGenerator.generateDoc(testDocName,scannerPath,parserPath);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		fileFactory.deleteDirectory("doc");
	}
	
	//Tests
	
	/** Test doc file exists. */
	@Test
	public void testDocExists() {
		assertTrue(Files.exists(Paths.get(filePath+File.separator+testDocName+".xml")));
		assertTrue(Files.exists(Paths.get(filePath+File.separator+"style.css")));
		assertTrue(Files.exists(Paths.get(filePath+File.separator+"stylesheet.xsl")));
	}
		
	/** Test if doc file is created correctly compared against a test file. */
	@Test
	public void testDocCorrectness() {
		assertTrue(FileComparator.areEqual(controlFile, filePath+File.separator+testDocName+".xml"));
	}
	
	/** Test that an incorrect sample file, gives a false result. */
	@Test
	public void testDocCorrectnessFalsePositive() {
		assertFalse(FileComparator.areEqual(controlFileFalse, filePath+File.separator+testDocName+".xml"));
	}
}
