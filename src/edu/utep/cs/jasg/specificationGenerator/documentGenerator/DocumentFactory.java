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

import java.util.List;

import org.jdom2.Element;

/** Document factory creates a JastAdd document based on the input type. */
public class DocumentFactory {

	ParserDocumentFactory parserDocumentFactory;

	public StringBuffer createDocument(String type, String template,Element elementList) throws DocumentGeneratorException{

		switch (type) {
		case "parser":  
			switch(template){
			case "beaver":
				parserDocumentFactory = new BeaverDocumentFactory(elementList);
				return parserDocumentFactory.generateDocument();
			default: throw new DocumentGeneratorException("Not a valid template name");
			}
		default: throw new DocumentGeneratorException("Not a valid document type");
		}
	}
}