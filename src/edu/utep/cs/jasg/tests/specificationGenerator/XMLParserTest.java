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
package edu.utep.cs.jasg.tests.specificationGenerator;

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
import edu.utep.cs.jasg.specificationGenerator.DOMValidateDTD;
import edu.utep.cs.jasg.specificationGenerator.XMLParser;
import edu.utep.cs.jasg.testFramework.FileComparator;

/**
 * Test XMLParser
 * @author Yeep
 */
public class XMLParserTest {

	private static Path tempWorkspace;

	private static String featureName = "Feature1";
	private String fileDestinationPath = tempWorkspace.toString()+File.separator+featureName;
	
	private static String testFilesPath = "test"+File.separator+"TestFiles";
	
	private static String xmlFile = testFilesPath+File.separator+"jasg.xml";
	private static String xmlFileWithError = testFilesPath+File.separator+"jasgWithError.xml";
	private String parserControlFile = testFilesPath+File.separator+"Feature1.parser";
	private String flexControlFile = testFilesPath+File.separator+"Feature1.flex";
	private String astControlFile = testFilesPath+File.separator+"Feature1.ast";
	private String jragControlFile = testFilesPath+File.separator+"Feature1.jrag";
	
	private static FileFactory fileFactory;
	private static XMLParser xmlParser;
	
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
		xmlParser = new XMLParser(tempWorkspace.toString());
		xmlParser.parse(xmlFile);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		fileFactory.deleteDirectory(featureName);
		
		//delete temp workspace
		try{
			Files.deleteIfExists(tempWorkspace);
			System.out.println(tempWorkspace.toString()+ " deleted");
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	//Tests

	/** Test correct feature name. */
	@Test
	public void testGetFeature() {
		assertEquals(featureName,xmlParser.getNameSpace());
	}
	
	/** Test specification files are created. */
	@Test
	public void testCreatedFiles() {
		assertTrue(Files.exists(Paths.get(fileDestinationPath+File.separator+featureName+".parser")));
		assertTrue(Files.exists(Paths.get(fileDestinationPath+File.separator+featureName+".flex")));
		assertTrue(Files.exists(Paths.get(fileDestinationPath+File.separator+featureName+".ast")));
		assertTrue(Files.exists(Paths.get(fileDestinationPath+File.separator+featureName+".jrag")));
	}
	
	/** Test DTD validator. */
	@Test
	public void testDOMValidateDTD1() {
		assertTrue(DOMValidateDTD.validateXML(xmlFile));
	}
	
	/** Test DTD validator with an error. */
	@Test
	public void testDOMValidateDTD2() {
		assertFalse(DOMValidateDTD.validateXML(xmlFileWithError));
	}
	
	/** Test if specification files are created correctly compared against a test file. */
	@Test
	public void testFileCorrectness()  {
		assertTrue(FileComparator.areEqual(parserControlFile,fileDestinationPath+File.separator+featureName+".parser"));
		assertTrue(FileComparator.areEqual(flexControlFile,fileDestinationPath+File.separator+featureName+".flex"));
		assertTrue(FileComparator.areEqual(astControlFile,fileDestinationPath+File.separator+featureName+".ast"));
		assertTrue(FileComparator.areEqual(jragControlFile,fileDestinationPath+File.separator+featureName+".jrag"));
	}
	

}
