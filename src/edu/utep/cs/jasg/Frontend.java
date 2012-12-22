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
package edu.utep.cs.jasg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.Scanner;

import edu.utep.cs.jasg.specificationGenerator.XMLParser;

/** Main program of JASG. */
public class Frontend {
	public static Scanner scanner = new Scanner( System.in );
	private String frameworkNameProperty, toolNameProperty, versionProperty, urlProperty;
	private String workspace, workspaceProperty;

	public static void main(String[] args){
		Frontend frontend = new Frontend();
	}
	
	public Frontend(){
		String option = "";

		//Load configuration properties
		getProperties();
		if(!workspaceProperty.equals("")){
			System.out.println("A previous workspace \"" + workspaceProperty + "\" was identifed");
			System.out.println("Do you want to use the same workspace? (yes, or no to specify a new one)");
			String workspaceOption = scanner.nextLine();
			if(workspaceOption.equals("yes"))
				workspace = workspaceProperty;			
		}
 
		printVersion();

		//Set workspace
		if(workspace == null){
			do{
				System.out.print( "Please specify a worskapce: " );
				workspace = scanner.nextLine();
			}while(!setWorkspace(workspace));
		}
		
		System.out.println("Using \"" + getWorkspace() + "\"" + " workspace\n");

		printOptions();

		//Enable command prompt
		while(true){
			System.out.print("JASG> ");
			option = scanner.nextLine();
			if(option.equals("exit"))
				break;
			processOptions(option);
		}
	}

	/** Get properties from properties file. */
	private void getProperties(){

		
		try {
			Properties defaultProps = new Properties();
			//load a properties file
			defaultProps.load(new FileInputStream("JASG.properties"));
			
			Properties applicationProps = new Properties(defaultProps);
			applicationProps.load(new FileInputStream("Workspace.properties"));

			//get the property value and print it out
			frameworkNameProperty = applicationProps.getProperty("jasg.JASG");
			urlProperty = applicationProps.getProperty("jasg.URL");
			versionProperty  = applicationProps.getProperty("jasg.Version");
			
			workspaceProperty = applicationProps.getProperty("jasg.Workspace");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	/** Create a workspace. 
	 * @return true if file is successfully created, false otherwise. */
	public boolean setWorkspace(String workspace){
		Path path = Paths.get(workspace);
		if(!Files.exists(path))
		{
			System.out.println("Workspace " + path.toString() + " doesn't exist");
			return false;
		}
		else
		{
			this.workspace = workspace;
			setWorkspaceProperty(workspace);
			System.out.println("Workspace " + "\"" + path.toString() + "\"" + " set");
			return true;
		}
	}
	
	/** Store workspace in properties. */
	private void setWorkspaceProperty(String workspace){
    	Properties prop = new Properties();
    	try {
    		prop.setProperty("jasg.Workspace", workspace);
 
    		prop.store(new FileOutputStream("Workspace.properties"), null);
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}

	//TODO: copy all files of a module?
	/** Import a JastAdd module. */
	private void importModule(String modulePath){
		if(workspace.equals(""))
			System.out.println("Set a workspace");
		else
		{
			if(!Files.exists(Paths.get(modulePath)))
				System.out.println("Path " + modulePath + " doesn't exist");
			else
			{
				//Copy main module files
				copyFiles(modulePath,workspace,"*.{jrag,jadd,flex,xml,ast,parser}");
				
				//Copy parser files
				copyFiles(modulePath+File.separator+"parser",workspace+File.separator+"parser","*.{all,parser}");
				
				//Copy scanner files
				copyFiles(modulePath+File.separator+"scanner",workspace+File.separator+"scanner","*.{flex}");
				
				//Copy AST files
				copyFiles(modulePath+File.separator+"AST",workspace+File.separator+"AST","*.{java}");
				
				
			}
		}
	}
	
	/** Copy files. */
	private void copyFiles(String source, String target, String glob){

		try (DirectoryStream<Path> stream = 
				Files.newDirectoryStream(Paths.get(source), glob)) {
			Path targetPath = Paths.get(target);
			if(!Files.exists(targetPath))
				Files.createDirectories(targetPath);

			for (Path entry: stream) {
				Files.copy(entry, targetPath.resolve(entry.getFileName()), StandardCopyOption.REPLACE_EXISTING);
				System.out.println(entry.getFileName());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Process options. */
	private void processOptions(String arg){
		String[] args = arg.split(" ");
		switch(args[0]){
		case "parse":
			parse(processArgument(args));
			break;
		case "import":
			importModule(processArgument(args));
			break;
		case "set-workspace":
			setWorkspace(processArgument(args));			
			break;
		case "get-workspace":
			System.out.println(getWorkspace());
			break;
		case "clean-workspace-property":
			setWorkspaceProperty("");
			System.out.println("Workspace property cleared");
			break;
		case "help":
			printOptions();
			break;
		case "version":
			printVersion();
			break;

		default: 
			System.out.println("Invalid option");
			printOptions();
		break;
		}
	}
	
	/** Process option argument. */
	private String processArgument(String[] args){
		String optionArg = "";
		if(args.length > 1){
			if(args[1] != null){
				optionArg = args[1];
			}
		}

		if(optionArg.equals("")){
			System.out.print("Specify " + args[0] + " option argument: " );
			optionArg = scanner.nextLine();
		}
		return optionArg;
	}
	/** Parse XML spec. */
	private void parse(String fileName){
		XMLParser xmlParser = new XMLParser(workspace);
		xmlParser.parse(fileName);
	}

	/** Return workspace location. */
	public String getWorkspace(){
		return workspace;
	}
	


	/** Tool information. */
	public void printVersion() {
		System.out.println(frameworkNameProperty  + " " + urlProperty + " Version " + versionProperty +"\n");
	}

	/** Print basic tool usage. */
	public void printOptions() {
		System.out.println(
				"Options:\n" +
						"  -parse <JASGXMLFile.xml>\t\tParse a JASG specification file\n" +
						"  -import <JastAdd module path>\t\tImport a JastAdd module into current workspace\n" +
						"  -set-workspace <workspace path> \tSet project workspace\n" +
						"  -get-workspace\t\t\tView current workspace\n" +
						"  -clean-workspace-property\t\tResets workspace property file\n" +
						"  -help\t\t\t\t\tPrint a synopsis of standard options\n" +
						"  -version\t\t\t\tPrint version information\n\n" +
						"  -exit\t\t\t\t\tExit application\n"
				);
	}

}
