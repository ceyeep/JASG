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

import org.jdom2.Element;

//TODO: consider implementing most of rule creation in templates (i.e. have only rule iterators in factory)
public class JFlexDocumentFactory extends ScannerDocumentFactory{

	public JFlexDocumentFactory(Element scannerRoot, String template) {
		super(scannerRoot, template);
	}


	protected String documentHeader() {
		return "//scanner file generated by JASG using "+getTemplateName()+" template\n";
	}


	protected String startOfRuleSet(StringBuffer states) {
		return "<"+states.toString()+"> {\n";
	}


	protected String endOfRuleSet() {
		return "\n}\n";
	}


	protected String ruleIDString(String idString) {
		return "\"" + idString + "\"\t";
	}


	protected String actionString(Element actionElement) {
		StringBuffer action = new StringBuffer();
		action.append("{ ");
		Element codeElement = actionElement.getChild("se_code");
		if(codeElement != null)
			action.append(codeElement.getText()+" ");
		
		Element terminalElement = actionElement.getChild("se_terminalName");
		action.append("return sym(Terminals."+terminalElement.getText()+"); }");
		return action.toString();
	}

}
