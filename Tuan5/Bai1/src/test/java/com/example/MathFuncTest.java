package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class MathFuncTest {
    private MathFunc mathFunc;

    @Before
    public void setUp() {
        mathFunc = new MathFunc();
    }

    @After
    public void tearDown() {
        mathFunc = null;
    }

    @Test
    public void calls() {
        // Kiểm tra calls trước khi gọi factorial
        assertEquals(0, mathFunc.getCalls());
        
        // Gọi factorial một lần
        mathFunc.factorial(3);
        assertEquals(1, mathFunc.getCalls());
        
        // Gọi factorial thêm lần nữa
        mathFunc.factorial(5);
        assertEquals(2, mathFunc.getCalls());
    }

    @Test
    public void factorial() {
        // Kiểm tra factorial(0) = 1
        assertEquals(1, mathFunc.factorial(0));
        
        // Kiểm tra factorial(1) = 1
        assertEquals(1, mathFunc.factorial(1));
        
        // Kiểm tra factorial(5) = 120
        assertEquals(120, mathFunc.factorial(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void factorialNegative() {
        mathFunc.factorial(-1);
    }

    @Test
    @Ignore
    public void todo() {
        assertEquals(5, mathFunc.plus(2, 3));
    }
}
