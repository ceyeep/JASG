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
package edu.utep.cs.jasg.specificationGenerator.ruleGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import beaver.comp.run.*;

/** Creates a new parser rule */ 
public class ParserRuleCreator {
	
	//After validating correct rule, need to create appropriate files containing the new rule (i.e. .parser .jflex)
	
	// production rule syntax: symbol = symbol_a symbol_b ... {: action routine code :} ;
	private String parserFileName = "";
	private String tempFileName = "temp.all";
	
	private String parserRule = 
			"Declaration declaration = \n" +
			"START STATE IDENTIFIER SEMI {: return new StartState(IDENTIFIER); :}\n"+
			"|	YEEP STATE IDENTIFIER SEMI {: return new FinalState(IDENTIFIER); :}\n"+
			";";
	
	/** Test constructor */
	public ParserRuleCreator(String parserFileName){
		this.parserFileName = parserFileName;
	}
	
	public ParserRuleCreator(String parserRule, String parserFileName){
		this.parserRule = parserRule;
		this.parserFileName = parserFileName;
	}
	
	//TODO: method under construction
	/** Validate that the rule is well constructed */
	private void verifyRule(){
		createStub("StateMachineParser");
		programs.Main main = new programs.Main();
		String[] parameters = {tempFileName,"parser"+File.separator+parserFileName+".beaver"};
		/**TODO: Need to create own main method to report errors in rule creation */
		main.main(parameters);
		String[] makerParameters = {"parser"+File.separator+parserFileName+".beaver"};
		Make maker = new Make();
		try {
			maker.main(makerParameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Need to check if rules or tokens exist
	}
		
	private String readFile(String fileName) throws IOException  {
		FileInputStream stream = new FileInputStream(new File(fileName));
		try {	
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

			return Charset.defaultCharset().decode(bb).toString();
		}
		finally {
			stream.close();
		}
	}
		
	/** Creates a stub JastAddParser file with the new rule */
	private void createStub(String fileName) 
	{
		FileOutputStream fop = null;
		File file = new File(tempFileName);
		String code = "";
		try {
			code = readFile("parser"+File.separator+fileName+".all");
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		code += "\n"+parserRule;
		
		try {
			fop = new FileOutputStream(file);
			fop.write(code.getBytes());
			fop.flush();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		    if (fop != null) {
		        try {
		            fop.close();
		        } catch (IOException e2) {
		        	e2.printStackTrace();
		        }
		    }
		}
		
		System.out.println("File " + "parser"+File.separator+tempFileName+".all" + " created");
	}
	
	/** Check if tokens exist in the current scanner specification file */
	
	public static void main(String[] args){
		ParserRuleCreator parserRuleCreator = new ParserRuleCreator("StateMachineParser");
		parserRuleCreator.verifyRule();
		
	}
}
