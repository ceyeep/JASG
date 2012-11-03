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
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class XMLParser {
	
	private String nameSpace = "";
	
	public static void main(String[] args) {

		XMLParser parser = new XMLParser();
		parser.parse();

	}
	
	/** Parser XML file. */
	public void parse(){
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(new File("jasg.xml"));
			Element root = doc.getRootElement();
			//get namespace
			nameSpace = root.getChild("nameSpace").getText();
			
			//parse different specs used
			System.out.println(nameSpace);
			


		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	/** Print XML file using XMLOutputter. */
	public void printXMLFile(String fileName){

		try {
			// Build the document with SAX and Xerces, no validation
			SAXBuilder builder = new SAXBuilder();
			// Create the document
			Document doc = builder.build(new File(fileName));
						
			// Output the document, use standard formatter
			XMLOutputter fmt = new XMLOutputter();
			fmt.output(doc, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Create a new directory with the specified name. */
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
