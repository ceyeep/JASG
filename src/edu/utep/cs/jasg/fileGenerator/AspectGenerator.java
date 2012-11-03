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
package edu.utep.cs.jasg.fileGenerator;
/** Generates a new aspect from given user rules */
public class AspectGenerator {
	
	//TODO: need to create header and food
	private String name = "Aspect";
	private StringBuffer AspectTemplate = new StringBuffer();
	
	public AspectGenerator(String name){
		this.name = name;
	}
	
	/** Generates aspect template */
	public String generateAspect()
	{
		AspectTemplate.append(getTemplateString(name)); 
		return AspectTemplate.toString();
	}
	
	/** Return Asepct template string. */
	private String getTemplateString(String name){
		return  "aspect " + name + "{\n" +
				"\t//public void StateMachine.printStateType() {\n" +
				"\t\t//for (Declaration d : getDeclarations()){\n" +
				"\t\t\t//d.printStateType();\n " +
				"\t\t//}\n" +
				"\t//}\n" +
				"}";
	}
}
