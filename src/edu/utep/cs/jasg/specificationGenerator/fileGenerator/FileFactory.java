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
package edu.utep.cs.jasg.specificationGenerator.fileGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileFactory {
	private static Scanner input = new Scanner(System.in);
	//TODO: Consider creating a cutomizable custom path
	private static final String CUSTOM_PATH = "custom";
	
	/** Create main JastAdd framework files */
	public static void main(String[] args) {

		FileFactory mainFactory = new FileFactory();
		
		String option = "";
		while(!option.equals("6"))
		{
	        System.out.println("Select an option");
			System.out.println("1. Create parser file");
			System.out.println("2. Create scanner file");
			System.out.println("3. Create ast file");
			System.out.println("4. Create aspect file");
			System.out.println("5. Create basic files");
			System.out.println("6. Exit");
			
			option = input.nextLine();      // Read one line from the console.
			
			switch (option) {
            case "1":
            	mainFactory.createFile("parser");
            	break;
                 
            case "2":  
            	mainFactory.createFile("flex");
            	break;
            	
            case "3":  
            	mainFactory.createFile("ast");
            	break;
            	
            case "4":  
            	mainFactory.createFile("jrag");
            	break;
            	
            case "5":
            	mainFactory.createFile("parser");
            	mainFactory.createFile("flex");
            	mainFactory.createFile("ast");
            	mainFactory.createFile("jrag");
            	break;
            	
            case "6":  System.out.println("Program finished");
    			break;
            default: System.out.println("invalid input");
            	break;
			}
		
		}
		input.close();
		
	}
	
	/** Create a file from a file template depending on extension */
	public void createFile(String path, String fileName, String extension)
	{
   		
    	//Check if current file exists
    	
    	File file = new File(CUSTOM_PATH+File.separator+path+File.separator+fileName+"."+extension);  

    	//Create new file
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Write file depending on selected type of document
		try{
			  // Create file writer
			  FileWriter fstream = new FileWriter(file);
			  BufferedWriter out = new BufferedWriter(fstream);
			  
				switch (extension) {
	            case "parser":
	            	out.write(new ParserGenerator().generateParser());
	            	break;
	                 
	            case "flex":  
	            	out.write(new ScannerGenerator().generateScanner());
	            	break;
	            	
	            case "ast":  
	            	out.write(new ASTGenerator().generateAST());
	            	break;
	            	
	            case "jrag":  
	            	out.write(new AspectGenerator(fileName).generateAspect());
	            	break;
	           
	            default: System.out.println("invalid extension");
	            	break;
				}
			  
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("File has been created: " + file.getAbsolutePath());

	}

	//TODO: add path to craete file
	/** Create a file from a file template depending on extension */
	public void createFile(String extension)
	{
		System.out.println("Select file name: (-1 to cancel)");
    	String fileName = input.nextLine();
    	if(fileName.equals("-1"))
    		return;
    		
    	//Check if current file exists
    	
    	File file = new File(CUSTOM_PATH+File.separator+fileName+"."+extension);  
    	while(file.exists()){
        	System.out.println("file already exists, please select a new name (-1 to cancel): ");
        	fileName = input.nextLine();
        	if(fileName.equals("-1"))
        		return;
        	file = new File(CUSTOM_PATH+File.separator+fileName+"."+extension);
		}
    	
    	//Create new file
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Write file depending on selected type of document
		try{
			  // Create file writer
			  FileWriter fstream = new FileWriter(file);
			  BufferedWriter out = new BufferedWriter(fstream);
			  
				switch (extension) {
	            case "parser":
	            	out.write(new ParserGenerator().generateParser());
	            	break;
	                 
	            case "flex":  
	            	out.write(new ScannerGenerator().generateScanner());
	            	break;
	            	
	            case "ast":  
	            	out.write(new ASTGenerator().generateAST());
	            	break;
	            	
	            case "jrag":  
	            	out.write(new AspectGenerator(fileName).generateAspect());
	            	break;
	           
	            default: System.out.println("invalid extension");
	            	break;
				}
			  
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("File has been created: " + file.getAbsolutePath());

	}
	
	/** Create a new directory with the specified name. */
	public void createDirectory(String name){
		try{
			String path = CUSTOM_PATH + File.separator+ name;

			// Create one directory
			boolean success = (new File(path)).mkdirs();
			if (success) {
				System.out.println("Directory: " + path + " created");
			}  

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void createFile(String document, String path, String fileName, String extension) {
    	//Check if current file exists
    	
    	File file = new File(CUSTOM_PATH+File.separator+path+File.separator+fileName+"."+extension);  

    	//Create new file
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Write file depending on selected type of document
		try{
			  // Create file writer
			  FileWriter fstream = new FileWriter(file);
			  BufferedWriter out = new BufferedWriter(fstream);
			  
				switch (extension) {
	            case "parser":
	            	out.write(document);
	            	break;
	                 
	            case "flex":  
	            	out.write(document);
	            	break;
	            	
	            case "ast":  
	            	out.write(document);
	            	break;
	            	
	            case "jrag":  
	            	out.write(document);
	            	break;
	           
	            default: System.out.println("invalid extension");
	            	break;
				}
			  
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("File has been created: " + file.getAbsolutePath());
		
	}

}
