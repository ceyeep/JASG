/*******************************************************************************
 * Copyright (c) 2012 Cesar Yeep.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD 3-Clause License ("New BSD" or "BSD Simplified") which accompanies this distribution, and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Contributors:
 *     Cesar Yeep - initial API and implementation
 ******************************************************************************/
package edu.utep.cs.jasg.fileGenerator;
/** Generates a new aspect from given user rules */
public class AspectGenerator {
	
	private String name = "Aspect";
	private StringBuffer AspectTemplate = new StringBuffer();
	private final String contentTemplate = 
	"aspect " + name + "{\n" +
	"//public void StateMachine.printStateType() {\n" +
	"//for (Declaration d : getDeclarations()){\n" +
	"//d.printStateType();\n " +
	"//}\n" +
	"//}\n" +
	"}";
	
	public AspectGenerator(String name){
		this.name = name;
	}
	
	/** Generates aspect template */
	public String generateAspect()
	{
		AspectTemplate.append(contentTemplate); 
		
		return AspectTemplate.toString();
	}
}
