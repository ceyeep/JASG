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
package edu.utep.cs.jasg.specificationGenerator.documentGenerator;

import org.jdom2.Element;

import edu.utep.cs.jasg.specificationGenerator.FileFactory;

/** Document factory creates a JastAdd document based on the input type. */
public class DocumentFactory {

	private static final String DEFAULT_PARSER = "beaver";
	private static final String DEFAULT_SCANNER = "jflex";
	private String nameSpace;

	public DocumentFactory(String nameSpace){
		this.nameSpace = nameSpace;
	}

	public void createDocument(String type,Element documentContent) throws DocumentGeneratorException{
		String fileName = "";
		String document = "";

		Element fileNameElement = documentContent.getChild("fileName");
		if(fileNameElement != null)
			fileName = fileNameElement.getText();
		else fileName = nameSpace;


		//Select document type
		switch (type) {
		case "parser": 
		{
			ParserDocumentFactory parserDocumentFactory;
			String template = "";

			Element templateElement = documentContent.getChild("template");
			if(templateElement != null)
				template = templateElement.getText();
			else template = DEFAULT_PARSER;

			//Select parser document type
			switch(template){
			//This is a JastAdd beaver specification template
			case "beaver":
			{
				parserDocumentFactory = new BeaverDocumentFactory(documentContent,template);
				document = parserDocumentFactory.generateDocument();
				if(fileName != null)		
					FileFactory.createFile(document,nameSpace, fileName, type);
				break;
			}
			default: throw new DocumentGeneratorException("Invalid template name: "+template);
			}
			break;
		}
		
		case "scanner":
		{
			ScannerDocumentFactory scannerDocumentFactory;
			String template = "";

			Element templateElement = documentContent.getChild("template");
			if(templateElement != null)
				template = templateElement.getText();
			else template = DEFAULT_SCANNER;

			//Select scanner document type
			switch(template){
			//this is really a slim jflex template
			case "jflex":
			{
				scannerDocumentFactory = new JFlexDocumentFactory(documentContent,template);
				document = scannerDocumentFactory.generateDocument();
				if(fileName != null)		
					FileFactory.createFile(document,nameSpace, fileName, "flex");
				break;

			}
			default: throw new DocumentGeneratorException("Invalid template name: "+template);
			}
			break;
		}
		
		case "AST":
		{
			ASTDocumentFactory astDocumentFactory = new ASTDocumentFactory(documentContent);
			document = astDocumentFactory.generateDocument();
			if(fileName != null)		
				FileFactory.createFile(document,nameSpace, fileName, "ast");
			break;

		}
		
		case "ASTBehavior":
		{
			ASTBehaviorDocumentFactory astBehaviorDocumentFactory = new ASTBehaviorDocumentFactory(documentContent);
			document = astBehaviorDocumentFactory.generateDocument();
			if(fileName != null)		
				FileFactory.createFile(document,nameSpace, fileName, "jrag");
			break;

		}
		default: throw new DocumentGeneratorException("Invalid document type: "+type);
		}
	}
}