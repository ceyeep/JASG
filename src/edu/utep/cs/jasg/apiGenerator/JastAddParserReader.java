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


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import parser.GrammarParser;
import parser.GrammarScanner;

import AST.ASTNode;
import AST.Grammar;
import beaver.Parser.Exception;

public class JastAddParserReader {
	
	private ArrayList<String> terminals = new ArrayList<String>();
	private HashMap<String,String> ruleTypes = new HashMap<String,String>();
	private HashMap<String,ArrayList<String>> rules = new HashMap<String,ArrayList<String>>();
	private String fileName = "";
	private Grammar grammar;
	
	/** Main constructor */
	public JastAddParserReader(String fileName){
		this.fileName = fileName;
		parseFile();
	}
	
	/** Parser beaver specification file. Creates a Grammar object  */
	private void parseFile(){
		try {

			String source = "parser"+File.separator+fileName+".all";

			ASTNode.sourceName = source;
			GrammarScanner scanner = new GrammarScanner(new FileReader(source));
			GrammarParser parser = new GrammarParser();
			Object o = parser.parse(scanner);
			grammar = (Grammar)o;
			
			terminals = grammar.getTerminals();
			ruleTypes = grammar.getRulesNames();
			rules = grammar.getRules();
	        
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	/** Get name of the beaver specification file */
	public String getFileName(){
		return fileName;
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
	
	/** Get grammar */
	public Grammar getGrammar(){
		return grammar;
	}
	
}

