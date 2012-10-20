/*******************************************************************************
 * Copyright (c) 2012 Cesar Yeep.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD 3-Clause License ("New BSD" or "BSD Simplified") which accompanies this distribution, and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Contributors:
 *     Cesar Yeep - initial API and implementation
 ******************************************************************************/
package edu.utep.cs.jasg.ruleGenerator;

import java.util.Scanner;

import edu.utep.cs.jasg.fileGenerator.MainFileFactory;

/** Main class for building new language constructs */
public class MainRuleGenerator {

	
	/*Case 2, adding a new AST rule (may include case 1)
	 *  - invoke AST file generator
	 *  - new node should not be in AST files (check language API javaDoc manually)
	 *  - may need to add required aspect functions for the new AST class
	 *  NOT SURE - need to use new AST node in a parser rule (unless is abstract)
	 *  - need to check if AST is valid, pre-compile (run jastAdd tool, or after runnin ANT)
	 */
	
	/* Case 3, adding a new basic scanner token (may include case 1, between the same context of the extension)
	 * 	- invoke parser file generator
	 * 	- need to check for existing scanner tokens using API (check automatically)
	 * 	- pre-compile using flex parser (check validity of scanner token)
	 */
	
	private static Scanner input = new Scanner(System.in);
	
	/**
	 * New language construct generator
	 */
	public static void main(String[] args) {
		

		String option = "";
		while(!option.equals("4"))
		{
	        System.out.println("Select an option");
			System.out.println("0. Add a new aspect function");
			System.out.println("1. Add a new parser rule");
			System.out.println("2. Add a new AST node");
			System.out.println("3. Add a new scanner token");
			System.out.println("4. Exit");
			
			option = input.nextLine();      // Read one line from the console.
			
			switch (option) {
			
			/*Case 0, adding a new aspect function
			 *	- invoke ASP file generator
			 *	- check if rule is valid (need to run JastAdd tool)
			 */
            case "0":
            	MainFileFactory mainFileFactory = new MainFileFactory();
            	mainFileFactory.createFile("ast");
            	break;

			/*Case 1, adding a new parser rule using existing parser, scanner, and AST rules
			 *  - invoke parser file generator
			 *  - input should be a rule using beaver syntax or xml syntax
			 *  - new rule should not exist in current parser specification (check API automatically)
			 *  - new parser rule should include existing parser rules, scanner tokens, 
			 *  	and return type should be a valid ASP file
			 *  - run beaver parser to check rule is valid
			 */
            case "1":  

            	break;
            	
            case "2":  

            	break;
            	
            case "3":  

            	break;
            	
            case "4":  System.out.println("Program finished");
    			break;
            	
            default: System.out.println("invalid input");
            	break;
			}
		
		}
		input.close();
		
	}

}
