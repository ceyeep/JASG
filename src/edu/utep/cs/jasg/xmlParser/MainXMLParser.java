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
package edu.utep.cs.jasg.xmlParser;

import java.io.File;

/** Main class that process a JASG XML spec file */
public class MainXMLParser {

	public static void main(String[] args){
		System.out.println("Parsing JASG file");
		
		MainXMLParser xmlParser = new MainXMLParser();
		xmlParser.createDirectory("test");

	}
	
	/** Create a new directory with specified name. */
	private void createDirectory(String name){
		try{
			String path ="custom/"+ name;

			// Create one directory
			boolean success = (new File(path)).mkdirs();
			if (success) {
				System.out.println("Directory: " + path + " created");
			}  

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
