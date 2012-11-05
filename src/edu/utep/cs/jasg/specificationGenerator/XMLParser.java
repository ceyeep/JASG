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
import java.util.List;

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
	private DocumentFactory documentFactory;

	//Main method for testing purposes only
	public static void main(String[] args) {

		XMLParser parser = new XMLParser();
		parser.parse("jasg.xml");

	}

	//TODO: create subparsing of elements (e.g. parsing rules) pass List to methods.
	/** Parser XML file. */
	public void parse(String fileName){
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(new File(fileName));
			Element root = doc.getRootElement();

			//create a new namespace
			nameSpace = root.getChild("nameSpace").getText();
			fileFactory.createDirectory(nameSpace);


			//get parser elements
			List<Element> parserElements = root.getChildren("parser");

			//get scanner elements
			List<Element> scannerElements = root.getChildren("scanner");

			//get AST node declarations
			List<Element> ASTNodeElements = root.getChildren("ASTNodes");

			//get AST behavior declarations
			List<Element> ASTBehaviorElements = root.getChildren("ASTBehavior");

			//parse parser elements
			if(parserElements != null)
				if(parserElements.size()>0)
					parseParserElements(parserElements);

			//parse scanner elements
			if(scannerElements != null)
				if(scannerElements.size()>0)
					parseScannerElements(scannerElements);

			//parse AST node elements
			if(ASTNodeElements != null)
				if(ASTNodeElements.size()>0)
					parseASTNodeElements(ASTNodeElements);

			//parse parser elements
			if(ASTBehaviorElements != null)
				if(ASTBehaviorElements.size()>0)
					parseASTBehaviorElements(ASTBehaviorElements);


		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	//TODO: add file name element to assign a file name to a set of elements

	/** Parse XML parser elements. */
	private void parseParserElements(List<Element> parserElements){
		System.out.println("Parsing parser elements");
		
		//Call Document factory
		
		//Create a factory based on document type
		documentFactory = new DocumentFactory();
		
		//TODO: modify implementation
		//Create document
		Element template = parserElements.get(0);
		Element elementList = parserElements.get(1);
		
		try {
			StringBuffer documentBuffer = documentFactory.createDocument("parser", template.getText(), elementList);
		} catch (DocumentGeneratorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Create a new parser file based on created rules
		
		fileFactory.createFile(nameSpace, nameSpace, "parser");

	}

	/** Parse XML scanner elements. */
	private void parseScannerElements(List<Element> scannerElements){
		System.out.println("Parsing scanner elements");

		//Create a new scanner file
		fileFactory.createFile(nameSpace, nameSpace, "flex");

	}

	/** Parse XML AST node elements. */
	private void parseASTNodeElements(List<Element> ASTNodeElements){
		System.out.println("Parsing AST node elements");

		//Create a new AST file
		fileFactory.createFile(nameSpace, nameSpace, "ast");

	}

	/** Parse XML AST behavior elements. */
	private void parseASTBehaviorElements(List<Element> ASTBehaviorElements){
		System.out.println("Parsing AST behavior elements");

		//Create a new jrag or jadd file
		fileFactory.createFile(nameSpace, nameSpace, "jrag");

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
