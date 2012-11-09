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

/** Document factory creates a JastAdd document based on the input type. */
public class DocumentFactory {

	private ParserDocumentFactory parserDocumentFactory;
	private static final String DEFAULT_PARSER = "beaver";

	public StringBuffer createDocument(String type,Element documentContent) throws DocumentGeneratorException{
		
		//Select document type
		switch (type) {
		case "parser": 
			String template = "";
			
			Element templateElement = documentContent.getChild("template");
			if(templateElement != null)
				template = templateElement.getText();
			else template = DEFAULT_PARSER;
			
			//Select parser document type
			switch(template){
			case "beaver":
				parserDocumentFactory = new BeaverDocumentFactory(documentContent,template);
				return parserDocumentFactory.generateDocument();
			default: throw new DocumentGeneratorException("Invalid template name");
			}
			

		default: throw new DocumentGeneratorException("Invalid document type");
		}
	}
}