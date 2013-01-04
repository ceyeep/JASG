package edu.utep.cs.jasg.testFramework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.utep.cs.jasg.tests.FileFactoryTest;
import edu.utep.cs.jasg.tests.FrontendTest;
import edu.utep.cs.jasg.tests.apiGenerator.MainAPIGeneratorTest;
import edu.utep.cs.jasg.tests.specificationGenerator.XMLParserTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	FileFactoryTest.class,
	FrontendTest.class,
	XMLParserTest.class,
	MainAPIGeneratorTest.class})
public class AllTests {

}
