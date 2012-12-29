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
package edu.utep.cs.jasg.apiGenerator;

import java.io.File;

import edu.utep.cs.jasg.FileFactory;

public class MainAPIGenerator {
	private String workspace;
	private FileFactory fileFactory;
	
	public MainAPIGenerator(String workspace){
		this.workspace = workspace;
		fileFactory = new FileFactory(workspace);
	}
	
	
	public void generateDoc(String fileName, String parserPath, String scannerPath){
		
		JastAddParserReader parserModel = new JastAddParserReader(parserPath);
		JFlexSpecificationParser scannerModel = new JFlexSpecificationParser(scannerPath);
		fileFactory.createDirectory("doc");
		fileFactory.copyFile("xml"+File.separator+"stylesheet.xsl", "doc"+File.separator+"stylesheet.xsl");
		fileFactory.copyFile("xml"+File.separator+"style.css", "doc"+File.separator+"style.css");
		APIGenerator generator = new APIGenerator(workspace+File.separator+"doc",fileName,parserModel, scannerModel);
		generator.generateXMLDocument();
		//generator.executeXSL();
	}	

}
