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
import java.nio.file.Files;
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
 *
 */
public class XMLParserTest {

	private static String workspace = "test"+File.separator+"TestWorkspace";
	private static String xmlFile = workspace+File.separator+"jasg.xml";
	private static String xmlFileWithError = workspace+File.separator+"jasgWithError.xml";
	private static XMLParser xmlParser;
	private static FileFactory fileFactory = new FileFactory(workspace);
	private static String featureName = "Feature1";
	private String filePath = workspace+File.separator+featureName;
	
	private String parserControlFile = "test"+File.separator+"Feature1Sample"+File.separator+"Feature1.parser";
	private String flexControlFile = "test"+File.separator+"Feature1Sample"+File.separator+"Feature1.flex";
	private String astControlFile = "test"+File.separator+"Feature1Sample"+File.separator+"Feature1.ast";
	private String jragControlFile = "test"+File.separator+"Feature1Sample"+File.separator+"Feature1.jrag";
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		xmlParser = new XMLParser(workspace);
		xmlParser.parse(xmlFile);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		fileFactory.deleteDirectory(featureName);
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
		assertTrue(Files.exists(Paths.get(filePath+File.separator+featureName+".parser")));
		assertTrue(Files.exists(Paths.get(filePath+File.separator+featureName+".flex")));
		assertTrue(Files.exists(Paths.get(filePath+File.separator+featureName+".ast")));
		assertTrue(Files.exists(Paths.get(filePath+File.separator+featureName+".jrag")));
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
	public void testFileCorrectness() {
		assertTrue(FileComparator.areEqual(parserControlFile,filePath+File.separator+featureName+".parser"));
		assertTrue(FileComparator.areEqual(flexControlFile,filePath+File.separator+featureName+".flex"));
		assertTrue(FileComparator.areEqual(astControlFile,filePath+File.separator+featureName+".ast"));
		assertTrue(FileComparator.areEqual(jragControlFile,filePath+File.separator+featureName+".jrag"));
	}
	

}
