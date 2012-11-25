package edu.utep.cs.jasg.testFramework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.utep.cs.jasg.tests.specificationGenerator.FileFactoryTest;
import edu.utep.cs.jasg.tests.specificationGenerator.MainSpecificationGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses({ FileFactoryTest.class, MainSpecificationGeneratorTest.class })
public class AllTests {

}
