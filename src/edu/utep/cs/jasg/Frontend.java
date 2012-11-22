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
import java.util.Properties;

/** Frontend provides console functions and options. */
public abstract class Frontend {
	private String frameworkNameProperty, toolNameProperty, versionProperty, urlProperty;

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
			toolNameProperty = prop.getProperty(defineToolNameProperty());
			urlProperty = prop.getProperty("jasg.URL");
			versionProperty  = prop.getProperty("jasg.Version");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/** Print version information. */
	public void printVersion() {
		System.out.println(frameworkNameProperty + ": " + toolNameProperty + " " + urlProperty + " JASG Version " + versionProperty);
	}
	
	/** Create a workspace. */
	public void createWorkspace(String path){

		File files = new File(path);
		if (files.exists()) {
			if (files.mkdirs()) {
				System.out.println("Multiple directories are created!");
			} else {
				System.out.println("Failed to create multiple directories!");
			}
		}		
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
	
	/** Print tool basic usage information. */
	public abstract void printUsage(); 

	/** Define tool name property. */
	public abstract String defineToolNameProperty();
	
	/** Process options. */
	public abstract void processOptions(String arg);
	
}
