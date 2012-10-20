/*******************************************************************************
 * Copyright (c) 2012 Cesar Yeep.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD 3-Clause License ("New BSD" or "BSD Simplified") which accompanies this distribution, and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Contributors:
 *     Cesar Yeep - initial API and implementation
 ******************************************************************************/
package edu.utep.cs.jasg.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import edu.utep.cs.jasg.apiGenerator.JastAddParserReader;


public class MainGUI {

	private JFrame mainFrame;
	private DefaultListModel<String> typeListModel, ruleListModel, tokenListModel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		typeListModel = new DefaultListModel<String>();
		ruleListModel = new DefaultListModel<String>();
		tokenListModel = new DefaultListModel<String>();
		initializeRuleListModel();
		initializeTokenListModel();
		
		mainFrame = new JFrame();
		mainFrame.setTitle("JastAdd Rule Creator");
		mainFrame.setBounds(100, 100, 500, 470);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPane = new JPanel();
		mainFrame.getContentPane().add(mainPane, BorderLayout.CENTER);
		
		JScrollPane typeScrollPane = new JScrollPane();
		mainPane.add(typeScrollPane);
		
		JList<String> types = new JList<String>(typeListModel);
		typeScrollPane.setViewportView(types);
		
		JScrollPane rulesScrollPane = new JScrollPane();
		mainPane.add(rulesScrollPane);
		
		JList<String> rules = new JList<String>(ruleListModel);
		rulesScrollPane.setViewportView(rules);
		
		JScrollPane tokensScrollPane = new JScrollPane();
		mainPane.add(tokensScrollPane);
		
		JList<String> tokens = new JList<String>(tokenListModel);
		tokensScrollPane.setViewportView(tokens);
	}
	
	/** Populate rule list model */
	private void initializeRuleListModel(){
		JastAddParserReader parserModel = new JastAddParserReader("StateMachineParser");
		
		Iterator<String> types = parserModel.getRuleTypes().values().iterator();
		Iterator<String> rules = parserModel.getRuleTypes().keySet().iterator();

		while(types.hasNext())
		{
			typeListModel.addElement(types.next());
		}
		
		while(rules.hasNext())
		{
			ruleListModel.addElement(rules.next());
		}
	}
	
	/** Populate token list model */
	private void initializeTokenListModel(){
		JastAddParserReader parserModel = new JastAddParserReader("StateMachineParser");
		for(String element: parserModel.getGrammar().getTerminals())
		{
			tokenListModel.addElement(element);
		}
	}

}
