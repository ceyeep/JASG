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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import edu.utep.cs.jasg.Frontend;

public class FrontendTest {
	
	private String workspace = "test"+File.separator+"TestWorkspace";
	private String targetModule = "test"+File.separator+"StateMachineTestModule";
	private String workspaceProperty = "jasg.Workspace";
    private String targetModuleProperty = "jasg.TargetModule";
    private String workspaceProperties = "properties"+File.separator+"Workspace.properties";
    private String targetModuleProperties = "properties"+File.separator+"TargetModule.properties";
	private String xmlFile = workspace+File.separator+"jasg.xml";
	private Frontend frontend;
	
	@Before
	public void setUp() throws Exception {
		frontend = new Frontend(workspace, targetModule);
		System.out.println(frontend.getWorkspace());
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

	@Test
	public void testWorkspaceProperty() {
		Path path = Paths.get(workspace);
		assertEquals(path.toString(),checkProperty(workspaceProperties,workspaceProperty));
	}
	
	@Test
	public void testTargetModuleProperty() {
		Path path = Paths.get(targetModule);
		assertEquals(path.toString(),checkProperty(targetModuleProperties,targetModuleProperty));
	}

	@Test
	public void testSetWorkspace() {
		Path path = Paths.get(workspace);
		frontend.setWorkspace(workspace);
		assertEquals(path.toString(),frontend.getWorkspace());
	}

	@Test
	public void testSetTargetModule() {
		Path path = Paths.get(workspace);
		frontend.setTargetModule(targetModule);
		assertEquals(path.toString(),frontend.getWorkspace());
	}

	@Test
	public void testCheckFileExists() {
		assertTrue(frontend.checkFileExists(xmlFile));
	}

	@Test
	public void testGetWorkspace() {
		Path path = Paths.get(workspace);
		assertEquals(path.toString(),frontend.getWorkspace());
	}

	@Test
	public void testGetTargetModule() {
		Path path = Paths.get(targetModule);
		assertEquals(path.toString(),frontend.getTargetModule());
	}

}
