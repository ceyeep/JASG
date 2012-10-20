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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/** Create a model of the JFlex scanner specification file */
public class JFlexSpecificationParser {
	
	private HashMap<String,ArrayList<String>> tokens = new HashMap<String,ArrayList<String>>();
	private Scanner scanner;
	private String fileName = "file";

	/** Main constructor */
	public JFlexSpecificationParser(String fileName){
		this.fileName = fileName;
		File file = new File("scanner"+File.separator+ fileName+".flex");
		parseFile(file);
	}
	
	/** Return list of tokens contained in the JFlex specification file */
	public HashMap<String,ArrayList<String>> getTokens(){
		return tokens;
	}
	
	/** Get name of the JFlex specification file */
	public String getFileName(){
		return fileName;
	}
	
	/** Parse JFlex specification file. Get tokens and symbols */
	private void parseFile(File file) {
		try {
			scanner = new Scanner(file);
			String line = "";
			while(scanner.hasNext())
			{
				line = scanner.nextLine();
				lineParser(line);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//printHashTableWithList(tokens);
		
	}
	
	/** Parser line of the JFlex specification file containing a terminal */
	private void lineParser(String line){
		if(line.contains("sym(Terminals"))
		{
			processLine(line);
		}	
	}
	
	/** Process line of the JFlex specification file. Extract terminals and symbols and creates a list with the items */
	private void processLine(String line){
		line = line.trim();
		String[] splitter = line.split("[\t ]");
		for(String element : splitter)
		{
			if(element.length() > 0)
			{
				
				if(element.contains("Terminals"))
				{
					StringBuffer token = new StringBuffer();
					for(int i = 0; i<element.length(); i++)
					{
						String letter = "";
						Character character = element.charAt(i);
						letter = character.toString();
						if(letter.matches("[A-Z]"))
						{
							token.append(letter);
						}
						else if(letter.equals("-"))
						{
							token.append("-");
						}
						else if(letter.equals("_"))
						{
							token.append("_");
						}
					}
					insertTokenToList(token.toString().substring(1,token.length()),splitter[0]);
				}
			}
		}
		
	}
	
	
	private void insertTokenToList(String key, String token){
		if(!tokens.containsKey(key))
		{
			ArrayList<String> tokenList = new ArrayList<String>();
			tokenList.add(token);
			
			tokens.put(key, tokenList);
		}
		else
		{
			if(!tokens.get(key).contains(token))
				tokens.get(key).add(token);
		}
	}
	
	/** Print array list */
	public void printList(ArrayList<String> list)
	{
		System.out.println("LIST: ");
		for(String element: list)
		{
			
			System.out.println(element);
		}
		System.out.println();
	}
	
	/** Print hash map <String,ArrayList> */
	public void printHashTableWithList(HashMap<String,ArrayList<String>> map)
	{
		Iterator<String> keys = map.keySet().iterator();
		System.out.println("KEYS: ");
		while(keys.hasNext())
		{
			String key = keys.next();
			System.out.println("rule: " + key +": ");
			printList(map.get(key));
		}
		System.out.println();
	}
	
}
