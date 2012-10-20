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

//Deprecated class! Use JastAddParserReader instead
/** Create a model of the beaver parser specification file */
public class BeaverSpecificationParser {

	private ArrayList<String> terminals = new ArrayList<String>();
	private HashMap<String,String> ruleTypes = new HashMap<String,String>();
	private HashMap<String,ArrayList<String>> rules = new HashMap<String,ArrayList<String>>();
	private Scanner scanner;
	private String fileName = "file";
	
	/** Main constructor */
	public BeaverSpecificationParser(String fileName)
	{
		this.fileName = fileName;
		File file = new File("parser"+File.separator+ fileName+".beaver");
		parseFile(file);
		//printHashMap(ruleTypes);
		//printHashTableWithList(rules);
	}
	
	/** Get list of terminals specified by the scanner specification file */
	public ArrayList<String> getTerminals(){
		return terminals;
	}
	
	/** Get list of rule names and rule types */
	public HashMap<String,String> getRuleTypes(){
		return ruleTypes;
	}
	
	/** Get list of parser rules */
	public HashMap<String,ArrayList<String>> getRules(){
		return rules;
	}
	
	/** Parse beaver specification file */
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
		
	}
	
	/** Parser line of the beaver specification file */
	private void lineParser(String line){
		if(line.startsWith("%terminals"))
		{
			parseTerminals(line);
		}
		else if(line.startsWith("%typeof"))
		{
			parseRuleTypes(line);
		}
		else if(line.endsWith("="))
		{
			while(scanner.hasNext())
			{
				String nextLine = scanner.nextLine().trim();
				//System.out.println("LINE: "+line);
				//System.out.println("NEXTLINE: "+nextLine);
				//If rule contains a {: :} block get block
				if(nextLine.contains("{:"))
				{
					if(!nextLine.contains(":}"))
					{
						line += "\n" + nextLine;
						while(scanner.hasNext())
						{
							nextLine = scanner.nextLine().trim();
							if(nextLine.length()>0)
							{
								if(nextLine.contains(":}"))
								{
									//line += "\n" + nextLine;
									break;
								}
								else
									line += "\n" + nextLine;
							}
						}
					}
					
				}

				if(nextLine.endsWith("="))
				{
					parseRules(line);
					//System.out.println("parser rule: "+line);
					line = nextLine;
					
				}
				else
				{
					if(nextLine.length()>1)
						line += "\n" + nextLine;
					//System.out.println("parser rule: \n"+line);
				}
				//System.out.println("LINE: "+line);
			}
			if(line.length()>1)
				parseRules(line);
		}
	}
	
	/** Parser terminals in beaver specification file */
	private void parseTerminals(String rawTerminal)
	{
		String[] splitter = rawTerminal.split(" ");
		terminals.add(splitter[1].substring(0, splitter[1].length()-1));
	}
	
	/** Parser rules names and type contained in the beaver specification file */
	private void parseRuleTypes(String rawRuleType)
	{
		String[] splitter = rawRuleType.split(" ");
		ruleTypes.put(splitter[1], splitter[3].substring(1, splitter[3].length()-2));
	}
	
	/** Parse parser rules contained in the beaver specification file */
	private void parseRules(String rawRules)
	{
		String[] splitter = rawRules.split("\n");
		ArrayList<String> ruleList = new ArrayList<String>();
		String key = splitter[0].substring(0, splitter[0].length()-2);
		//System.out.println("KEY: "+key);
		//System.out.println("RAW rule: "+"\n"+rawRules);
		if(!rules.containsKey(key))
		{
			String rule = "";
			for(int i = 1; i < splitter.length; i++)
			{
				if(splitter[i].startsWith("|"))
					rule += splitter[i].substring(2);
		
				else if(splitter[i].length()>0)
					rule += splitter[i];
				
				if(i + 1 < splitter.length )
				{
					if(splitter[i+1].startsWith("|"))
					{
						ruleList.add(rule);
						rule = "";
					}
					else
						rule += "\n";
					
				}
				else
					ruleList.add(rule);
							
			}
			rules.put(splitter[0].substring(0, splitter[0].length()-2), ruleList);
		}
		else
		{
			String rule = "";
			for(int i = 1; i < splitter.length; i++)
			{
				if(splitter[i].startsWith("|"))
					rule += splitter[i].substring(2);
				
				else if(splitter[i].length()>0)	
					rule += splitter[i];
				
				if(i + 1 < splitter.length )
				{
					if(splitter[i+1].startsWith("|"))
					{
						rules.get(key).add(rule);
						rule = "";
					}
					else
						rule += "\n";
					
				}
				else
				{
					rules.get(key).add(rule);
				}
			}										
		}
	}
	
	/** Get name of the beaver specification file */
	public String getFileName()
	{
		return fileName;
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

	/** Print hash map <String,String> */
	public void printHashMap(HashMap<String,String> map)
	{
		Iterator<String> keys = map.keySet().iterator();
		System.out.println("KEYS: ");
		while(keys.hasNext())
		{
			String key = keys.next();
			System.out.println(key+", "+map.get(key));
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
