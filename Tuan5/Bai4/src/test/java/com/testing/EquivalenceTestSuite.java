package com.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * EquivalenceTestSuite - Test Suite cho các test cases kiểm tra lớp tương đương
 * 
 * Chỉ chạy các test cases liên quan đến Equivalence Class Partitioning
 */
@RunWith(Suite.class)
@SuiteClasses({
    EquivalenceClassTest.class
})
public class EquivalenceTestSuite {
    // Test suite chỉ chạy equivalence class tests
}
