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
package edu.utep.cs.jasg.specificationGenerator.fileGenerator;
/** Generates a new scanner from given user rules */
public class ScannerGenerator {
	
	private StringBuffer scannerTemplate = new StringBuffer();
	
	
	/** Generates scanner template */
	public String generateScanner()
	{
		scannerTemplate.append(""); 
		
		return scannerTemplate.toString();
	}
}
