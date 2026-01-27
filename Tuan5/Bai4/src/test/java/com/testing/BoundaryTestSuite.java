package com.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * BoundaryTestSuite - Test Suite cho các test cases kiểm tra giá trị biên
 * 
 * Chỉ chạy các test cases liên quan đến Boundary Value Analysis
 */
@RunWith(Suite.class)
@SuiteClasses({
    BoundaryValueTest.class
})
public class BoundaryTestSuite {
    // Test suite chỉ chạy boundary value tests
}
