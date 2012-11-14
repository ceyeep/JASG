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
package edu.utep.cs.jasg.specificationGenerator.documentGenerator;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

/** Interface to define ASTBehavior document operations. */ 
public class ASTBehaviorDocumentFactory {
	
	//TODO: consider support of .jadd and .jarg in XML spec
	private StringBuffer document = new StringBuffer();
	private Element ASTBehaviorRoot;

	public ASTBehaviorDocumentFactory(Element ASTBehaviorRoot){
		this.ASTBehaviorRoot = ASTBehaviorRoot;
	}

	public String generateDocument(){
	
		document.append(documentHeader());
		parseAspects();

		return document.toString();

	}

	protected void parseAspects(){
		Element aspects = ASTBehaviorRoot.getChild("aspects");
		
		if(aspects != null){
			
			//Get set of aspects
			List<Element> aspectList = aspects.getChildren();
			Iterator<Element> aspectListIterator = aspectList.iterator();
			
			//Add existing aspects
			while(aspectListIterator.hasNext()){
				parseAspectElement(aspectListIterator.next());
				document.append("\n");
			}

		}
	}


	
	private void parseAspectElement(Element aspectElement) {
		Element aspectNameElement = aspectElement.getChild("aspectName");
		//Append aspect start including aspect name
		try {
			document.append(aspectStart(aspectNameElement.getText()));
		} catch (DocumentGeneratorException e) {
			e.printStackTrace();
		}
		
		Element ASTBehaviorsElement = aspectElement.getChild("ASTBehaviorElements");
		
		if(ASTBehaviorsElement != null){
		
			//Get set of existing AST behavior elements 
			List<Element> ASTBehaviorList = ASTBehaviorsElement.getChildren();
			Iterator<Element> ASTBehaviorIterator = ASTBehaviorList.iterator();
	
			//Add existing behavior elements (i.e. attributes, rewrites)
			while(ASTBehaviorIterator.hasNext()){
				document.append(formatASTBehavior(ASTBehaviorIterator.next().getText()));
			}
		}
		
		document.append(aspectEnd());
	}
	
	/** Format behavior to eliminate trailing spaces. */
	public String formatASTBehavior(String ASTBehavior){
		StringBuffer formattedBehavior = new StringBuffer();
		String[] behaviorLines = ASTBehavior.split("\n");
		for(String behaviorLine: behaviorLines){
			formattedBehavior.append("\t" + behaviorLine.trim());
			formattedBehavior.append("\n");
		}
		return formattedBehavior.toString();
	}

	/** Return document in a StringBuffer. */
	public StringBuffer getDocument(){
		return document;
	}

	//Template methods
	protected String documentHeader(){
		return "//JastAdd aspect file generated by JASG\n";
	}
	
	protected String aspectStart(String aspectName) throws DocumentGeneratorException{
		if(aspectName.contains(" "))
			throw new DocumentGeneratorException("Invalid aspect name (i.e. blank spaces): "+aspectName);
		return "aspect "+ aspectName + " {\n";
	}
	
	protected String aspectEnd(){
		return "\n}\n";
	}
	


}

