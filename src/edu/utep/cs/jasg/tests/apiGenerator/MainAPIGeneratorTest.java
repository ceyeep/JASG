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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

	private static Path tempWorkspace;

	private static String targetModule = "test"+File.separator+"StateMachineTestModule";
	private static String testDocName = "StateMachineDoc";
	private static String scannerName = "StateMachineScanner";
	private static String parserName = "StateMachineParser";
	private static String scannerPath = targetModule+File.separator+"scanner"+File.separator+scannerName+".flex";
	private static String parserPath = targetModule+File.separator+"parser"+File.separator+parserName+".all";
	private String filePath = tempWorkspace.toString()+File.separator+"doc";
	
	private String controlFile = "test"+File.separator+"TestFiles"+File.separator+"StateMachineDoc.xml";
	private String controlFileFalse = "test"+File.separator+"TestFiles"+File.separator+"StateMachineDocIncorrect.xml";

	private static FileFactory fileFactory;
	private static MainAPIGenerator apiGenerator;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		//create a temp workspace
		try {
			tempWorkspace = Files.createTempDirectory("tempTest");
			System.out.println(tempWorkspace.toString()+" created");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fileFactory = new FileFactory(tempWorkspace);
		apiGenerator = new MainAPIGenerator(tempWorkspace.toString());
		
		apiGenerator.generateDoc(testDocName,scannerPath,parserPath);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		fileFactory.deleteDirectory("doc");
		
		//delete temp workspace
		try{
			Files.deleteIfExists(tempWorkspace);
			System.out.println(tempWorkspace.toString()+ " deleted");
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
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
