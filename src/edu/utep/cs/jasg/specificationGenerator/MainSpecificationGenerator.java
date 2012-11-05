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
package edu.utep.cs.jasg.specificationGenerator;

/** Main class that process a JASG XML spec file */
public class MainSpecificationGenerator {

	public static void main(String[] args){
		System.out.println("Parsing JASG file");
		
		//MainXMLParser mainXmlParser = new MainXMLParser();
		//mainXmlParser.createDirectory("test");
		XMLParser xmlParser = new XMLParser();
		xmlParser.parse(args[0]);
	}
	
}
