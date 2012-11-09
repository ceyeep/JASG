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
	private Element parserRoot;
	private String template = "";

	public ParserDocumentFactory(Element parserRoot, String template){
		this.parserRoot = parserRoot;
		this.template = template;
	}

	public StringBuffer generateDocument(){
	
		document.append(documentHeader());
		buildRules();
		document.append(documentFooter());

		return document;

	}

	public void buildRules(){
		Element parserRules = parserRoot.getChild("parserRules");
		if(parserRules != null){

			//Get set of existing rules
			List<Element> rules = parserRules.getChildren();
			Iterator<Element> ruleIterator = rules.iterator();

			//Add existing rules to document
			while(ruleIterator.hasNext()){
				document.append(generateRule(ruleIterator.next()).toString());
				document.append(endOfRule());
				document.append("\n");
			}

		}
	}



	public StringBuffer generateRule(Element rule){
		StringBuffer ruleBuffer = new StringBuffer();
		Element pe_idUse = rule.getChild("pe_idUse");
		Element pe_idDecl = rule.getChild("pe_idDecl");

		//Append ID use
		if(pe_idUse != null)
			ruleBuffer.append(pe_idUse.getText()+" ");

		//Append ID declaration
		ruleBuffer.append(pe_idDecl.getText()+" ");

		//Add definition symbol
		ruleBuffer.append(definitionSymbol());

		//Get rule definitions
		List<Element> pe_definitions = rule.getChildren("pe_definition");

		if(pe_definitions != null){
			Iterator<Element> definitionIterator = pe_definitions.iterator();

			//Iterate through existing definitions
			while(definitionIterator.hasNext()){
				//Definition
				Element pe_definition = definitionIterator.next();

				List<Element> pe_elements = pe_definition.getChildren("pe_element");

				if(pe_elements != null)
					if(pe_elements.size() > 0)
					{
						//Iterate through different 
						Iterator<Element> elementIterator = pe_elements.iterator();
						while(elementIterator.hasNext()){
							Element pe_element = elementIterator.next();
							ruleBuffer.append(pe_element.getChild("pe_idUse").getText());
							ruleBuffer.append(idUseSeparator());
							Element pe_name = pe_element.getChild("pe_name");
							if(pe_name != null)
								ruleBuffer.append(pe_name.getText()+" ");
						}
					}

				Element pe_code = pe_definition.getChild("pe_code");
				if(pe_code != null)
				{	
					ruleBuffer.append(codeInit());
					ruleBuffer.append(pe_code.getText());
					ruleBuffer.append(codeEnd());
				}

				if(definitionIterator.hasNext())
					ruleBuffer.append(elseSymbol());
			}
		}
		return ruleBuffer;
	}
	
	public String getTemplateName(){
		return template;
	}

	//Template methods

	abstract String documentFooter();

	abstract String documentHeader();

	abstract String endOfRule();

	abstract String definitionSymbol();
	
	abstract String elseSymbol();
	
	abstract String idUseSeparator();
	
	abstract String codeInit();
	
	abstract String codeEnd();

}

