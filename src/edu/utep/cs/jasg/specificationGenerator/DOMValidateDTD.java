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
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/** Valdiate XML file. */
public class DOMValidateDTD {
	public static void main(String args[]) {	
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new org.xml.sax.ErrorHandler() {
				//Ignore the fatal errors
				public void fatalError(SAXParseException exception)throws SAXException { }
				//Validation errors 
				public void error(SAXParseException e)throws SAXParseException {
					System.out.println("Error at " +e.getLineNumber() + " line.");
					System.out.println(e.getMessage());
					System.exit(0);
				}
				//Show warnings
				public void warning(SAXParseException err)throws SAXParseException{
					System.out.println(err.getMessage());
					System.exit(0);
				}
			});
			Document xmlDocument = builder.parse(new FileInputStream("jasg.xml"));
			DOMSource source = new DOMSource(xmlDocument);
			StreamResult result = new StreamResult(System.out);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "jasg.dtd");
			transformer.transform(source, result);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}