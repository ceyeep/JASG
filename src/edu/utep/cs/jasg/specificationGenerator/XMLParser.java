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
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import edu.utep.cs.jasg.FileFactory;
import edu.utep.cs.jasg.specificationGenerator.documentGenerator.SpecificationGenerator;
import edu.utep.cs.jasg.specificationGenerator.documentGenerator.DocumentGeneratorException;

public class XMLParser {

	private String nameSpace = "";
	private String workspace;
	private SpecificationGenerator specificationGenerator;
	private FileFactory fileFactory;

	public XMLParser(String workspace){
		this.workspace = workspace;
		fileFactory = new FileFactory(workspace);
	}

	//TODO: create sub-parsing of elements (e.g. parsing rules) pass List to methods.
	/** Parser XML file. */
	public void parse(String fileName){
		File file = new File(fileName);
		if(file.exists() && DOMValidateDTD.validateXML(fileName)){
			
			try {
				SAXBuilder builder = new SAXBuilder();
				Document doc = (Document) builder.build(file);
				Element root = doc.getRootElement();
	
				nameSpace = root.getChild("nameSpace").getText();
	
				//TODO: should I replace all files? Like compiling new files
				//check existing name spaces
				if(!Files.exists(Paths.get(workspace+File.separator+nameSpace)))
				{
					fileFactory.createDirectory(nameSpace);
				}
				
				specificationGenerator = new SpecificationGenerator(fileFactory,nameSpace);
	
				//get root element declarations
				Element parserElement = root.getChild("parser");
	
				Element scannerElement = root.getChild("scanner");
	
				Element ASTNodeElement = root.getChild("AST");
	
				Element ASTBehaviorElement = root.getChild("ASTBehavior");
	
				//parse root elements (document specifications)
				if(parserElement != null)
					parseRootElement("parser",parserElement);
	
				if(scannerElement != null)
					parseRootElement("scanner",scannerElement);
	
				if(ASTNodeElement != null)
					parseRootElement("AST",ASTNodeElement);
	
				if(ASTBehaviorElement != null)
					parseRootElement("ASTBehavior",ASTBehaviorElement);
	
	
			} catch (IOException io) {
				System.out.println(io.getMessage());
			} catch (JDOMException jdomex) {
				System.out.println(jdomex.getMessage());
			}
		}
	}

	/** Parse element. */
	private void parseRootElement(String type, Element element){
		System.out.println("Parsing "+type+" elements");
		
		try {
			//Create a new specification file
			specificationGenerator.generateSpecification(type, element);
			
		} catch (DocumentGeneratorException e) {
			e.printStackTrace();
		}		

	}


	/** Print XML file using XMLOutputter. */
	public void printXMLFile(File file){

		try {
			// Build the document with SAX and Xerces, no validation
			SAXBuilder builder = new SAXBuilder();
			// Create the document
			Document doc = builder.build(file);

			// Output the document, use standard formatter
			XMLOutputter fmt = new XMLOutputter();
			fmt.output(doc, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
