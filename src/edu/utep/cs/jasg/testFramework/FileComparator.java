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
package edu.utep.cs.jasg.testFramework;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Compares if the content of two text files is equal. */
public class FileComparator {

	public static boolean areEqual(String file1, String file2){

		Path file1Path = Paths.get(file1);
		Path file2Path = Paths.get(file2);

		Charset charset = Charset.forName("US-ASCII");
		try (
				BufferedReader reader1 = Files.newBufferedReader(file1Path, charset);
				BufferedReader reader2 = Files.newBufferedReader(file2Path, charset);
				) {
			String file1Line = null;
			String file2Line = null;
			while ((file1Line = reader1.readLine()) != null 
					&& (file2Line = reader2.readLine()) != null) {
				//System.out.println(file1Line);
				//System.out.println(file2Line);
				if(!file1Line.equals(file2Line))
					return false;
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
			return false;
		}
		return true;
	}

}
