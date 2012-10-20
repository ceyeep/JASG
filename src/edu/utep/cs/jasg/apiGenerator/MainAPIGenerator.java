/*******************************************************************************
 * Copyright (c) 2012 Cesar Yeep.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD 3-Clause License ("New BSD" or "BSD Simplified") which accompanies this distribution, and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Contributors:
 *     Cesar Yeep - initial API and implementation
 ******************************************************************************/
package edu.utep.cs.jasg.apiGenerator;

public class MainAPIGenerator {

	/**
	 * Initiate scanner and parser models and convert models to a XML file
	 */
	public static void main(String[] args) {
		JastAddParserReader parserModel = new JastAddParserReader("temp");
		JFlexSpecificationParser scannerModel = new JFlexSpecificationParser("StateMachineScanner");
		//JastAddParserReader parserModel = new JastAddParserReader("JavaParser");
		//JFlexSpecificationParser scannerModel = new JFlexSpecificationParser("JavaScanner");
		APIGenerator generator = new APIGenerator(parserModel, scannerModel);
		generator.generateXMLDocument();
		//generator.executeXSL();

		
	}

}
