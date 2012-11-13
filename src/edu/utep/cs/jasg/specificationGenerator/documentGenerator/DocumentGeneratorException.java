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

public class DocumentGeneratorException extends Exception{

	private static final long serialVersionUID = 1L;
	private String error;

	public DocumentGeneratorException()
	{
		super();            
		error = "unknown";
	}


	public DocumentGeneratorException(String error)
	{
		super(error);    
		this.error = error;  
	}

	/** Return error message. */
	public String getError()
	{
		return error;
	}

}
