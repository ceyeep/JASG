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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileFactory {

	//TODO: Consider creating a customizable custom path
	private String workspace;
	
	public FileFactory(String workspace){
		this.workspace = workspace;
	}
	
	//TODO: consider changing document to a Document object
	/** Create a file from a JASG document. */
	public void createFile(String document, String path, String fileName, String extension) {
    	//Check if current file exists
    	
    	File file = new File(workspace+File.separator+path+File.separator+fileName+"."+extension);  

    	//Create new file
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try{
			  // Create file writer
			  FileWriter fstream = new FileWriter(file);
			  BufferedWriter out = new BufferedWriter(fstream);

			  //Write document in file
	          out.write(document);

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
			String pathString = workspace + File.separator+ name;
			Path path = Paths.get(pathString);
			Files.createDirectory(path);
			System.out.println("Directory: " +  path.toString() + " created");
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/** Return current workspace. */
	public String getWorkspace(){
		return workspace;
	}

}
