package com.example;

public class MathFunc {
    private int calls;

    public MathFunc() {
        this.calls = 0;
    }

    public int factorial(int n) {
        calls++;
        
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        
        if (n == 0) {
            return 1;
        }
        
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public int plus(int a, int b) {
        return a + b;
    }

    public int getCalls() {
        return calls;
    }
}
