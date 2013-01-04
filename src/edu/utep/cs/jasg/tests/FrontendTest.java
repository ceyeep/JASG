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
package edu.utep.cs.jasg.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.utep.cs.jasg.Frontend;

/** Test JASG Frontend. */
public class FrontendTest {
	
	private static Path tempWorkspace;

	private String targetModule = "test"+File.separator+"StateMachineTestModule";
	
	private String workspaceProperty = "jasg.Workspace";
    private String targetModuleProperty = "jasg.TargetModule";
    
    private String workspaceProperties = "properties"+File.separator+"Workspace.properties";
    private String targetModuleProperties = "properties"+File.separator+"TargetModule.properties";
    
    private String testFilesPath = "test"+File.separator+"TestFiles";
    
	private String xmlFile = testFilesPath+File.separator+"jasg.xml";
	private String completeBuildFile = testFilesPath+File.separator+"build.xml";
	private String incompleteBuildFile = testFilesPath+File.separator+"buildIncomplete.xml";
	
	private Frontend frontend;
	
	@BeforeClass 
	public static void setUpClass() {      
		try {
			tempWorkspace = Files.createTempDirectory("tempTest");
			System.out.println(tempWorkspace.toString()+" created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		frontend = new Frontend(tempWorkspace.toString(), targetModule);
	}
	
	@AfterClass 
	public static void tearDownClass() {
		try{
			Files.deleteIfExists(tempWorkspace);
			System.out.println(tempWorkspace.toString()+ " deleted");
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/** Check if property exists */
	private String checkProperty(String propertiesFile, String propertyName){
		String property = "";
		try {
			Properties properties = new Properties();
			//load a properties file
			properties.load(new FileInputStream(propertiesFile));
			
			//get the property value and print it out
			property = properties.getProperty(propertyName);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return property;
	}
	
	//Tests

	@Test
	public void testWorkspaceProperty() {
		assertEquals(tempWorkspace.toString(),checkProperty(workspaceProperties,workspaceProperty));
	}
	
	@Test
	public void testTargetModuleProperty() {
		Path path = Paths.get(targetModule);
		assertEquals(path.toString(),checkProperty(targetModuleProperties,targetModuleProperty));
	}

	@Test
	public void testSetWorkspace() {
		frontend.setWorkspace(tempWorkspace.toString());
		assertEquals(tempWorkspace.toString(),frontend.getWorkspace());
	}

	@Test
	public void testSetTargetModule() {
		frontend.setTargetModule(targetModule);
		assertEquals(tempWorkspace.toString(),frontend.getWorkspace());
	}

	@Test
	public void testCheckFileExists() {
		assertTrue(frontend.checkFileExists(xmlFile));
	}

	@Test
	public void testGetWorkspace() {
		assertEquals(tempWorkspace.toString(),frontend.getWorkspace());
	}

	@Test
	public void testGetTargetModule() {
		Path path = Paths.get(targetModule);
		assertEquals(path.toString(),frontend.getTargetModule());
	}
	
	/** Test parseBuildFile with a complete ant build file. */
	@Test
	public void testParseBuildFile01() {
		String[] expectedAttributes = {"StateMachine","StateMachineScanner","StateMachineParser"};
		assertArrayEquals(expectedAttributes,frontend.parseBuildFile(completeBuildFile));
	}
	
	/** Test parseBuildFile with an incomplete ant build file. */
	@Test
	public void testParseBuildFile02() {
		String[] expectedAttributes = {"","","StateMachineParser"};
		assertArrayEquals(expectedAttributes,frontend.parseBuildFile(incompleteBuildFile));
	}

}
