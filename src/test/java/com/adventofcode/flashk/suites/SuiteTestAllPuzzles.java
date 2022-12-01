package com.adventofcode.flashk.suites;

import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import com.adventofcode.flashk.common.test.constants.TestTag;

@Suite
@IncludeTags({ TestTag.PART_ONE, TestTag.PART_TWO })
@ExcludeTags(TestTag.DEBUG)
@SelectPackages({"com.adventofcode.flashk"})
@SuiteDisplayName("Test all puzzles")
public class SuiteTestAllPuzzles {

}
