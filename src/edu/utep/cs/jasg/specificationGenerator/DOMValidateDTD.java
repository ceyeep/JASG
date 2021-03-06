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
//import java.io.*;
//import org.w3c.dom.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.w3c.dom.Document;
import org.xml.sax.*;
import javax.xml.parsers.*;
//import javax.xml.transform.*;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.transform.stream.StreamResult;

/** Validate XML file. */
public class DOMValidateDTD {
	public static void main(String args[]) {	
		if(args.length > 0){
			validateXML(args[0]);
		}
	}
	
	/** Validate XML against its DTD. */
	public static boolean validateXML(String filePath){
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
					//System.exit(0);
				}
				//Show warnings
				public void warning(SAXParseException err)throws SAXParseException{
					System.out.println(err.getMessage());
					//System.exit(0);
				}
			});

			Path path = Paths.get(filePath);
			File inputFile = new File(path.toString());
			builder.parse(inputFile);
			//Document xmlDocument = builder.parse(inputFile);
			//DOMSource source = new DOMSource(xmlDocument);
			//StreamResult result = new StreamResult(System.out);
			//TransformerFactory tf = TransformerFactory.newInstance();
			//Transformer transformer = tf.newTransformer();
			//transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "jasg.dtd");
			//transformer.transform(source, result);
			return true;
		}
		catch (Exception e) {
			System.out.println("Exception in DOMValidteDTD: "+e.getMessage());
			return false;
		}
	}
}