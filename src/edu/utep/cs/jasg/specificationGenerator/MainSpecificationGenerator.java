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

import edu.utep.cs.jasg.Frontend;

/** Main class that process a JASG XML spec file */
public class MainSpecificationGenerator  extends Frontend{

	public static void main(String[] args){

		MainSpecificationGenerator main = new MainSpecificationGenerator(args);
		main.createWorkspace("C:\\asgasgasdg\\LO\\SHO\\sHo");
	}

	/** Main checker constructor. */
	public MainSpecificationGenerator(String[] args)
	{
		super();
		if(args.length > 0)
		{
			String arg = args[0];
			if(arg.startsWith("-"))
				processOptions(arg);
			else{
				//Process file
				File file = new File(arg);
				//Parse file
				XMLParser xmlParser = new XMLParser();
				xmlParser.parse(file);
			}
		}
		else
			printUsage();
	}

	/** Process options. */
	public void processOptions(String arg){
		switch(arg){
		case "-version":
			printVersion();
			break;
		case "-help":
			printUsage();
			break;
		default: System.out.println("Invalid option");
		break;
		}
	}

	/** Define tool name property. */
	public String defineToolNameProperty() {
		return "jasg.SpecGenTool";
	}

	/** Print basic tool usage. */
	public void printUsage() {
		System.out.println(
				getFrameworkNameProperty() + ": " + getToolNameProperty() + "\n\n" +
						"Usage example: jar "+ getToolNameProperty() + "<options>\n" +
						"or: jar MainSpecificationGenetaor <JASGXMLFile.xml>\n" +
						"  -help \t\t Print a synopsis of standard options\n" +
						"  -version \t\t Print version information\n"
				);
	}
}
