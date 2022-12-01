package com.adventofcode.flashk.suites;

import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import com.adventofcode.flashk.common.test.constants.TestTag;

@Suite
@IncludeTags(TestTag.SAMPLE)
@SelectPackages({"com.adventofcode.flashk"})
@SuiteDisplayName("Test puzzle sample files")
public class SuiteTestSampleFiles {

    @BeforeAll
    public static void start() throws Exception {
        System.out.println("Before All from Suite1");
    }
}
