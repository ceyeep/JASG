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
package edu.utep.cs.jasg.apiGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.ProcessingInstruction;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/** Creates a XML file of the scanner and parser APIs using JDOM */
public class APIGenerator {
	
	private Document doc;
	private String fileName = "file";
	//private BeaverSpecificationParser parserModel;
	private JastAddParserReader parserModel;
	private JFlexSpecificationParser scannerModel;
	
	/** Main constructor, takes the output name of the XML file */
	public APIGenerator(String fileName, JastAddParserReader parserModel, JFlexSpecificationParser scannerModel){
		this.fileName = fileName;
		this.parserModel = parserModel;
		this.scannerModel = scannerModel;
	}
	
	/** Secondary constructor, takes the name of the parser model to name the XML file */
	public APIGenerator(JastAddParserReader parserModel, JFlexSpecificationParser scannerModel){
		this.fileName = parserModel.getFileName();
		this.parserModel = parserModel;
		this.scannerModel = scannerModel;
	}
	
	/** Generates a XML document using JDom */
	public void generateXMLDocument(){
		Element parserAPI = new Element("parser_api");
		parserAPI.addContent(new Element("parser_name").setText(fileName));
		doc = new Document(parserAPI);
		
		createTerminalElements();
		createRuleElements();
		
		createXMLFileWithXSLLink();
	}
	
	/** Creates JDom nodes of the terminals contained in the parser specification file.
	 * 	Maps the name of the terminals contained in the Beaver specification file to the value
	 *  of the terminals extracted from a JFlex specification file */
	private void createTerminalElements(){
		Element terminals = new Element("terminals");
		
		
		for(String terminal: parserModel.getTerminals())
		{
			Element terminalElement = new Element("terminal");
			terminalElement.setAttribute("name", terminal);
			
			if(scannerModel.getTokens().containsKey(terminal))
			{
				Element symbolSet = new Element("symbol_set");
				Iterator<String> symbolList = scannerModel.getTokens().get(terminal).iterator();
				
				while(symbolList.hasNext())
				{
					String symbol = symbolList.next();
					symbolSet.addContent(new Element("symbol").setAttribute(new Attribute("value",symbol)));
				}
			
				terminalElement.addContent(symbolSet);
			}
			terminals.addContent(terminalElement);
		}
		
		doc.getRootElement().addContent(terminals);
	}
	
	/** Creates JDom nodes of the parser rules */
	private void createRuleElements(){
		Element ruleSet = new Element("rule_set");
		Iterator<String> keys = parserModel.getRuleTypes().keySet().iterator();
		
		while(keys.hasNext())
		{
			Element rule = new Element("rule");
			String key = keys.next();
			
			rule.setAttribute(new Attribute("name", key));
			rule.setAttribute(new Attribute("type", parserModel.getRuleTypes().get(key)));
			rule.addContent(createRuleDefinitions(key));
			ruleSet.addContent(rule);
		}
		
		doc.getRootElement().addContent(ruleSet);
	}
	
	/** Creates JDom nodes of the parse rules definitions */
	private Element createRuleDefinitions(String key){
		Element ruleSet = new Element("rule_definitions");
		for(String rule: parserModel.getRules().get(key))
		{
			ruleSet.addContent(new Element("rule_definition").addContent(new Element("definition").setText(rule)));
		}
		
		return ruleSet;

	}
	
	/* Creates a XML file with the parser and scanner APIs using JDom
	private void createXMLFile(){
		  try {
				// new XMLOutputter().output(doc, System.out);
				XMLOutputter xmlOutput = new XMLOutputter();
		 
				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput.output(doc, new FileWriter("xml"+File.separator+fileName+".xml"));
		 
				System.out.println("File Saved!");
			  } catch (IOException io) {
				System.out.println(io.getMessage());
			  }
	}
	*/
	
	/** Creates a XML file with an XSL reference using JDom */
	private void createXMLFileWithXSLLink(){
		try {
		
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
		 
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
		
			HashMap<String,String> piMap = new HashMap<String,String>();
			piMap.put( "type", "text/xsl" );
			piMap.put( "href", "stylesheet.xsl" );
			ProcessingInstruction pi = new ProcessingInstruction( "xml-stylesheet",piMap );
		
			doc.getContent().add( 0, pi );
			xmlOutput.output(doc, new FileWriter("xml"+File.separator+fileName+".xml"));
		 
				
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
		  
		System.out.println("File " + "xml"+File.separator+fileName+".xml" + " created");
	}
	
	/** Creates a HTML file based on the XSL and XML files, using a DOM transformer */
    	public void executeXSL() {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            DOMOutputter outputter = new DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(doc);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("xml"+File.separator+"stylesheet.xsl"));
			//Make the output result for the finished document
            
            // Note that here we are just going to output the results to the
            // System.out, since we don't actually have a HTTPResponse object
            // in this example
             
            //StreamResult xmlResult = new StreamResult(response.getOutputStream());
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
	}
	
	
}
