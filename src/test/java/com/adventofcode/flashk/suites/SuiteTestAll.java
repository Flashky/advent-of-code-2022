package com.adventofcode.flashk.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages({"com.adventofcode.flashk"})
@SuiteDisplayName("Test all puzzles")
public class SuiteTestAll {

}
