package edu.utep.cs.jasg.testFramework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.utep.cs.jasg.tests.FileFactoryTest;
import edu.utep.cs.jasg.tests.FrontendTest;
import edu.utep.cs.jasg.tests.specificationGenerator.SpecificationGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	FileFactoryTest.class,
	FrontendTest.class,
	SpecificationGeneratorTest.class })
public class AllTests {

}
