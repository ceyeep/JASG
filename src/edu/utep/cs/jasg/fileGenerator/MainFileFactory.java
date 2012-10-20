/*******************************************************************************
 * Copyright (c) 2012 Cesar Yeep.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD 3-Clause License ("New BSD" or "BSD Simplified") which accompanies this distribution, and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Contributors:
 *     Cesar Yeep - initial API and implementation
 ******************************************************************************/
package edu.utep.cs.jasg.fileGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainFileFactory {
	private static Scanner input = new Scanner(System.in);
	/** Create main JastAdd framework files */
	public static void main(String[] args) {

		MainFileFactory mainFactory = new MainFileFactory();
		
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
	public void createFile(String extension)
	{
		System.out.println("Select file name: (-1 to cancel)");
    	String fileName = input.nextLine();
    	if(fileName.equals("-1"))
    		return;
    		
    	//Check if current file exists
    	
    	File file = new File("custom"+File.separator+fileName+"."+extension);  
    	while(file.exists()){
        	System.out.println("file already exists, please select a new name (-1 to cancel): ");
        	fileName = input.nextLine();
        	if(fileName.equals("-1"))
        		return;
        	file = new File("custom"+File.separator+fileName+"."+extension);
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

}
