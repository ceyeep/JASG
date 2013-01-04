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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

import edu.utep.cs.jasg.FileFactory;

/**
 * Test FileFactory
 * @author Yeep
 *
 */
public class FileFactoryTest {
	private FileFactory fileFactory;
	private static Path tempWorkspace;
	private Path testDirectoryPath;

	@BeforeClass 
	public static void setUpClass() {      
		try {
			tempWorkspace = Files.createTempDirectory("tempTest");
			System.out.println(tempWorkspace.toString()+" created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testDirectoryPath  = Paths.get("test");
		fileFactory = new FileFactory(tempWorkspace);
		fileFactory.createDirectory(testDirectoryPath);
	}
	
	@After
    public void tearDown() {
		fileFactory.deleteDirectory(testDirectoryPath);
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

	/**
	 * Test creation of a file in specified directory in workspace
	 * {@link edu.utep.cs.jasg.FileFactory#createFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCreateFile() {
		Path filePath = Paths.get(tempWorkspace.toString()+File.separator+testDirectoryPath.toString()+File.separator+"test.test");
		fileFactory.createFile("test", testDirectoryPath, "test", "test");

		assertTrue(Files.exists(filePath));
	}

	/**
	 * Test creation of a new directory in workspace
	 * {@link edu.utep.cs.jasg.FileFactory#createDirectory(java.lang.String)}.
	 */
	@Test
	public void testCreateDirectory() {
		assertTrue(Files.exists(Paths.get(tempWorkspace+File.separator+testDirectoryPath)));
	}

}
