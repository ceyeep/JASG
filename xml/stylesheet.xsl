<?xml version="1.0" encoding="UTF-8"?>
<!--
  Copyright (c) 2012 Cesar Yeep.
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the BSD 3-Clause License
  ("New BSD" or "BSD Simplified") which accompanies this distribution,
  and is available at
  http://opensource.org/licenses/BSD-3-Clause
  
  Contributors:
      Cesar Yeep - initial API and implementation
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/parser_api">
		<html>
			<head>
				Beaver Parser API
				<link rel="stylesheet" type="text/css" href="style.css"/>
			</head>
			<body class="body">
				<h1><xsl:value-of select="parser_name"/> API</h1>
				<h2 class = "p_name">Terminals</h2>
				<table class = "table">
					<tr>
						<th class = "tableheader">Terminal</th>
						<th class = "tableheader">Value</th>
					</tr>
					<xsl:for-each select="terminals/terminal">
						<tr>
						  <td class="t_cell"><xsl:value-of select="@name"/></td>
						  <td class="t_cell">
								<table class = "inner_table">
								<xsl:for-each select="symbol_set/symbol">
									<tr>
										<td class="t_cell"><xsl:value-of select="@value"/></td>
									</tr>
								</xsl:for-each>
								</table>
						  </td>
						</tr>
					</xsl:for-each>
					
				</table>
				
				<h2 class = "p_name">Rules</h2>
				<table class = "table">
					<tr>
						<th class = "tableheader">Name</th>
						<th class = "tableheader">Type</th>
						<th class = "tableheader">Set of rules</th>
					</tr>
					<xsl:for-each select="rule_set/rule">
						<tr>
						  <td class="t_cell"><xsl:value-of select="@name"/></td>
						  <td class="t_cell"><xsl:value-of select="@type"/></td>
						  <td class="t_cell">
								<table class = "inner_table">
								<xsl:for-each select="rule_definitions/rule_definition">
									<tr>
										<td class="t_cell"><xsl:value-of select="definition"/></td>
									</tr>
								</xsl:for-each>
								</table>
						  </td>
						</tr>
					</xsl:for-each>
					
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
