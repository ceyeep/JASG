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

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import edu.utep.cs.jasg.specificationGenerator.documentGenerator.DocumentFactory;
import edu.utep.cs.jasg.specificationGenerator.documentGenerator.DocumentGeneratorException;

public class XMLParser {

	private String nameSpace = "";
	private DocumentFactory documentFactory;

	//Main method for testing purposes only
	public static void main(String[] args) {

		XMLParser parser = new XMLParser();
		//TODO: change to accept input argument
		parser.parse("jasg.xml");

	}

	//TODO: create sub-parsing of elements (e.g. parsing rules) pass List to methods.
	/** Parser XML file. */
	public void parse(String fileName){
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(new File(fileName));
			Element root = doc.getRootElement();

			//create a new namespace
			nameSpace = root.getChild("nameSpace").getText();
			FileFactory.createDirectory(nameSpace);
			documentFactory = new DocumentFactory(nameSpace);


			//get root element declarations
			Element parserElement = root.getChild("parser");

			Element scannerElement = root.getChild("scanner");

			Element ASTNodeElement = root.getChild("AST");

			Element ASTBehaviorElement = root.getChild("ASTBehavior");

			//parse element
			if(parserElement != null)
				parseElement("parser",parserElement);

			if(scannerElement != null)
				parseElement("scanner",scannerElement);

			if(ASTNodeElement != null)
				parseElement("AST",ASTNodeElement);

			if(ASTBehaviorElement != null)
				parseElement("ASTBehavior",ASTBehaviorElement);


		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	/** Parse element. */
	private void parseElement(String type, Element element){
		System.out.println("Parsing "+type+" elements");
		
		try {
			//Create a new file
			documentFactory.createDocument(type, element);
			
		} catch (DocumentGeneratorException e) {
			e.printStackTrace();
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

}
