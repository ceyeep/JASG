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

//:TODO create a super class for DocumentFactories
/** Interface to define scanner document operations. */ 
public abstract class ScannerDocumentFactory {

	private StringBuffer document = new StringBuffer();
	private Element scannerRoot;
	private String template = "";

	public ScannerDocumentFactory(Element scannerRoot, String template){
		this.scannerRoot = scannerRoot;
		this.template = template;
	}

	public String generateDocument(){
	
		document.append(documentHeader());
		parseRules();

		return document.toString();

	}

	protected void parseRules(){
		Element ruleSetsElement = scannerRoot.getChild("scannerRuleSets");
		if(ruleSetsElement != null){

			//Get set of existing rule sets
			List<Element> ruleSets = ruleSetsElement.getChildren();
			Iterator<Element> ruleSetsIterator = ruleSets.iterator();

			//Add existing rule sets
			while(ruleSetsIterator.hasNext()){
				document.append(parseRuleSet(ruleSetsIterator.next()).toString());
			}

		}
	}


	protected StringBuffer parseRuleSet(Element ruleSet){

		StringBuffer ruleBuffer = new StringBuffer();
		Element se_states = ruleSet.getChild("se_states");
		Element se_rules = ruleSet.getChild("se_rules");
		
		List<Element> statesList = se_states.getChildren();
		Iterator<Element> statesIterator = statesList.iterator();
		
		//Buffer containing state list to be appended in the document
		StringBuffer states = new StringBuffer();
		
		//Iterate through state elements
		while(statesIterator.hasNext()){
			states.append(statesIterator.next().getText());
			if(statesIterator.hasNext())
				states.append(", ");
		}
		document.append(startOfRuleSet(states));
		
		if(se_rules != null){
			//Append existing scanner rules
			List<Element> rules = se_rules.getChildren();
			Iterator<Element> rulesIterator = rules.iterator();
			
			//Iterate through rule elements
			while(rulesIterator.hasNext()){
				document.append(parseScannerRule(rulesIterator.next()));
			}		
		}
		document.append(endOfRuleSet());
		return ruleBuffer;
	}
	
	protected String parseScannerRule(Element ruleElement){
		StringBuffer rule = new StringBuffer();
		Element se_idDecl = ruleElement.getChild("se_idDecl");
		Element se_action = ruleElement.getChild("se_action");
		
		rule.append("\t" + ruleIDString(se_idDecl.getText()));
		rule.append(actionString(se_action));
		rule.append("\n");
		return rule.toString();
	}

	/** Get document template name. */
	public String getTemplateName(){
		return template;
	}
	
	/** Return document in a StringBuffer. */
	public StringBuffer getDocument(){
		return document;
	}

	//Template methods

	abstract protected String documentHeader();

	abstract protected String startOfRuleSet(StringBuffer states);
	abstract protected String endOfRuleSet();
	
	abstract protected String ruleIDString(String idString);
	abstract protected String actionString(Element action);


}

