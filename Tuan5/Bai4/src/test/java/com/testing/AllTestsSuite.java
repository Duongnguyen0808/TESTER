package com.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AllTestsSuite - Test Suite tổng hợp tất cả các test cases
 * 
 * Sử dụng @RunWith và @SuiteClasses để tổ chức và chạy nhiều test classes
 * trong một lần thực thi
 */
@RunWith(Suite.class)
@SuiteClasses({
    PaymentCalculatorTest.class,
    BoundaryValueTest.class,
    EquivalenceClassTest.class
})
public class AllTestsSuite {
    // Class này để trống
    // JUnit sẽ tự động chạy tất cả các test classes được liệt kê trong @SuiteClasses
}
