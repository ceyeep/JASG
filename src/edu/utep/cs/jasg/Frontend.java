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
package edu.utep.cs.jasg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.Scanner;


/** Frontend provides console functions and options. */
public abstract class Frontend {
	public static Scanner scanner = new Scanner( System.in );
	private String frameworkNameProperty, toolNameProperty, versionProperty, urlProperty;
	private String workspace;

	public Frontend(){
		getProperties();
	}


	/** Get properties from properties file. */
	private void getProperties(){
		try {
			Properties prop = new Properties();
			//load a properties file
			prop.load(new FileInputStream("JASG.properties"));

			//get the property value and print it out
			frameworkNameProperty = prop.getProperty("jasg.JASG");
			toolNameProperty = prop.getProperty(toolNameProperty());
			urlProperty = prop.getProperty("jasg.URL");
			versionProperty  = prop.getProperty("jasg.Version");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/** Print version information. */
	public void printVersion() {
		System.out.println(frameworkNameProperty + ": " + toolNameProperty + " " + urlProperty + " JASG Version " + versionProperty +"\n");
	}

	public String getFrameworkNameProperty() {
		return frameworkNameProperty;
	}

	public String getVersionProperty() {
		return versionProperty;
	}

	public String getUrlProperty() {
		return urlProperty;
	}

	public String getToolNameProperty(){
		return toolNameProperty;
	}

	public String getWorkspace(){
		return workspace;
	}

	/** Create a workspace. 
	 * @return true if file is succesfully created, false otherwise. */
	public boolean setWorkspace(String workspace){
		Path path = Paths.get(workspace);
		if(!Files.exists(path))
		{
			System.out.println("Workspace " + path.toString() + " doesn't exist");
			return false;
		}
		else
		{
			this.workspace = workspace;
			System.out.println("Workspace " + path.toString() + " set");
			return true;
		}
	}

	/** Import a JastAdd module. */
	public void importModule(String modulePath){
		if(workspace.equals(""))
			System.out.println("Set a workspace");
		else
		{
			if(!Files.exists(Paths.get(modulePath)))
				System.out.println("Path " + modulePath + " doesn't exist");
			else
			{
				
				//Copy parser .all files
				try (DirectoryStream<Path> stream = 
						Files.newDirectoryStream(Paths.get(modulePath+File.separator+"parser"), "*.{all}")) {
					Path parserPath = Paths.get(workspace+File.separator+"parser");
					if(!Files.exists(parserPath))
						Files.createDirectories(parserPath);

					for (Path entry: stream) {
						Files.copy(entry, parserPath.resolve(entry.getFileName()), StandardCopyOption.REPLACE_EXISTING);
						System.out.println(entry.getFileName());
					}


				} catch (IOException e) {
					e.printStackTrace();
				}	

				//Copy AST files
				try (DirectoryStream<Path> stream = 
						Files.newDirectoryStream(Paths.get(modulePath+File.separator+"AST"), "*.java")) {
					Path astPath = Paths.get(workspace+File.separator+"AST");
					if(!Files.exists(astPath))
						Files.createDirectories(astPath);

					for (Path entry: stream) {
						Files.copy(entry, astPath.resolve(entry.getFileName()), StandardCopyOption.REPLACE_EXISTING);
						System.out.println(entry.getFileName());
					}


				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** Print tool basic usage information. */
	public abstract void printUsage(); 

	/** Define tool name property. */
	public abstract String toolNameProperty();

	/** Process options. */
	public abstract void processOptions(String arg);

}
