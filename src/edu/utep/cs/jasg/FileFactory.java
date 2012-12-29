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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.StandardCopyOption.*;
import static java.nio.file.FileVisitResult.*;

public class FileFactory {

	private String workspace;

	public FileFactory(String workspace){
		this.workspace = workspace;
	}

	public FileFactory(Path workspace){
		this.workspace = workspace.toString();
	}

	public void createFile(String document, Path path, String fileName, String extension){
		String pathString = path.toString();
		createFile(document,pathString,fileName,extension);
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
			System.out.println("File has been created: " + file.getAbsolutePath());
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error creating file: " + e.getMessage());
		}	
	}

	/** Create a new directory in the current workspace with the specified name. */
	public void createDirectory(String name){
		try{
			String pathString = workspace + File.separator+ name;
			Path path = Paths.get(pathString);
			if(!Files.exists(path)){
				Files.createDirectory(path);
				System.out.println("Directory: " +  path + " created");
			}
		}catch (Exception e){//Catch exception if any
			System.err.println("Error creating directory: " + e.getMessage());
		}
	}

	/** Create a new directory in the current workspace with the specified name. */
	public void createDirectory(Path path){
		createDirectory(path.toString());
	}

	/** Delete directory and files from workspace. */
	public void deleteDirectory(String name){
		String pathString = workspace + File.separator+ name;
		Path path = Paths.get(pathString);
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				//TODO: delete attrs
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {

					System.out.println("Deleting file: " + file);
					Files.delete(file);
					return CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir,
						IOException exc) throws IOException {

					System.out.println("Deleting dir: " + dir);
					if (exc == null) {
						Files.delete(dir);
						return CONTINUE;
					} else {
						throw exc;
					}
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Delete directory from workspace. */
	public void deleteDirectory(Path path){
		deleteDirectory(path.toString());
	}

	/** Copy a file in specified workspace directory. */
	public void copyFile(String source, String target){

		Path sourcePath = Paths.get(source).toAbsolutePath();
		Path targetPath = Paths.get(workspace+File.separator+target);

		try {
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
			System.out.println("File \""+sourcePath+"\" copied to \""+targetPath+"\"");
		} catch (IOException e) {
			System.err.println("Error copying file \"" + sourcePath + "\": " +e.getMessage());
		}
	}


	/** Return current workspace. */
	public String getWorkspace(){
		return workspace;
	}

}
