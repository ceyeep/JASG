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


/** Interface to define parser document operatoins. */ 
public abstract class ParserDocumentFactory {
	
	private StringBuffer document = new StringBuffer();
	private Element element;
	
	public ParserDocumentFactory(Element element){
		this.element = element;
	}
	
	public StringBuffer generateDocument(){
		document.append(getHeader());
		buildRules();
		document.append(getFooter());
		
		return document;
		
	}
	
	public void buildRules(){
		Element parserRules = element.getChild("parserRules");
		if(parserRules != null){
			List<Element> rules = parserRules.getChildren();
			System.out.println("Size: "+rules.size());
			Iterator<Element> iterator = rules.iterator();
			while(iterator.hasNext()){
				document.append(generateRule(iterator.next()).toString());
				document.append("\n");
			}
			
		}
	}
	
	public StringBuffer generateRule(Element rule){
		StringBuffer ruleBuffer = new StringBuffer();
		Element pe_idUse = rule.getChild("pe_idUse");
		Element pe_idDecl = rule.getChild("pe_idDecl");
		Element pe_definition = rule.getChild("pe_definition");
		
		//ID use
		if(pe_idUse != null)
			ruleBuffer.append(pe_idUse.getText()+" ");
		
		//ID declaration
		ruleBuffer.append(pe_idDecl.getText()+" ");
		
		//Definition
		List<Element> pe_elements = pe_definition.getChildren("pe_element");
		
		if(pe_elements != null)
			if(pe_elements.size() > 0)
			{
				Iterator<Element> iterator = pe_elements.iterator();
				while(iterator.hasNext()){
					Element pe_element = iterator.next();
					ruleBuffer.append(pe_element.getChild("pe_idUse").getText()+" ");
					Element pe_name = pe_element.getChild("pe_name");
					if(pe_name != null)
						ruleBuffer.append(pe_name.getText()+" ");
				}
			}
		
		Element pe_code = pe_definition.getChild("pe_code");
		if(pe_code != null)
			ruleBuffer.append(pe_code.getText()+" ");
		
		return ruleBuffer;
	}

	abstract StringBuffer getFooter();

	abstract StringBuffer getHeader();

}

