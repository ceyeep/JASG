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
import edu.utep.cs.jasg.specificationGenerator.fileGenerator.FileFactory;

public class XMLParser {

	private String nameSpace = "";
	private FileFactory fileFactory = new FileFactory();
	private DocumentFactory documentFactory = new DocumentFactory();

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
			fileFactory.createDirectory(nameSpace);


			//get root element declarations
			Element parserElement = root.getChild("parser");

			Element scannerElement = root.getChild("scanner");

			Element ASTNodeElement = root.getChild("ASTNodes");

			Element ASTBehaviorElement = root.getChild("ASTBehavior");

			//parse element
			if(parserElement != null)
				parseElement(parserElement,"parser");

			if(scannerElement != null)
				parseElement(scannerElement,"scanner");

			if(ASTNodeElement != null)
				parseElement(ASTNodeElement,"ASTNode");

			if(ASTBehaviorElement != null)
				parseElement(ASTBehaviorElement,"ASTBehavior");


		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	/** Parse element. */
	private void parseElement(Element element, String type){
		System.out.println("Parsing "+type+" elements");
		String document;

		try {
			//Create a document from a document factory
			document = documentFactory.createDocument(type, element).toString();

			System.out.println(document.toString());

			//TODO: get filename, replace "default" value

			//Create a new specification file from the document
			fileFactory.createFile(document,nameSpace, "default", type);
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
