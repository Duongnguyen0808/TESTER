import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class Cau2Test {
    
    @Test
    public void testPolynomialConstructorValid() throws ArgumentException {
        // Test case: Valid input with n+1 coefficients
        List<Integer> coefficients = Arrays.asList(1, 2, 3);
        Cau2 poly = new Cau2(2, coefficients);
        assertNotNull(poly);
    }
    
    @Test(expected = ArgumentException.class)
    public void testPolynomialConstructorInvalidData() throws ArgumentException {
        // Test case: Invalid input - not enough coefficients
        List<Integer> coefficients = Arrays.asList(1, 2);
        new Cau2(3, coefficients); // Should throw ArgumentException
    }
    
    @Test(expected = ArgumentException.class)
    public void testPolynomialConstructorTooManyCoefficients() throws ArgumentException {
        // Test case: Invalid input - too many coefficients
        List<Integer> coefficients = Arrays.asList(1, 2, 3, 4, 5);
        new Cau2(2, coefficients); // Should throw ArgumentException
    }
    
    @Test
    public void testCalWithSimplePolynomial() throws ArgumentException {
        // Test case: P(x) = 1 + 2x + 3x^2, evaluate at x = 2
        // P(2) = 1 + 2*2 + 3*4 = 1 + 4 + 12 = 17
        List<Integer> coefficients = Arrays.asList(1, 2, 3);
        Cau2 poly = new Cau2(2, coefficients);
        assertEquals(17, poly.Cal(2.0));
    }
    
    @Test
    public void testCalWithZero() throws ArgumentException {
        // Test case: Evaluate at x = 0
        // P(0) = a0
        List<Integer> coefficients = Arrays.asList(5, 2, 3);
        Cau2 poly = new Cau2(2, coefficients);
        assertEquals(5, poly.Cal(0.0));
    }
    
    @Test
    public void testCalWithOne() throws ArgumentException {
        // Test case: P(x) = 1 + 2x + 3x^2, evaluate at x = 1
        // P(1) = 1 + 2 + 3 = 6
        List<Integer> coefficients = Arrays.asList(1, 2, 3);
        Cau2 poly = new Cau2(2, coefficients);
        assertEquals(6, poly.Cal(1.0));
    }
    
    @Test
    public void testCalWithNegativeX() throws ArgumentException {
        // Test case: P(x) = 1 + 2x + 3x^2, evaluate at x = -1
        // P(-1) = 1 + 2*(-1) + 3*1 = 1 - 2 + 3 = 2
        List<Integer> coefficients = Arrays.asList(1, 2, 3);
        Cau2 poly = new Cau2(2, coefficients);
        assertEquals(2, poly.Cal(-1.0));
    }
    
    @Test
    public void testCalWithLinearPolynomial() throws ArgumentException {
        // Test case: P(x) = 3 + 4x, evaluate at x = 5
        // P(5) = 3 + 4*5 = 23
        List<Integer> coefficients = Arrays.asList(3, 4);
        Cau2 poly = new Cau2(1, coefficients);
        assertEquals(23, poly.Cal(5.0));
    }
    
    @Test
    public void testCalWithConstantPolynomial() throws ArgumentException {
        // Test case: P(x) = 7 (constant), evaluate at any x
        List<Integer> coefficients = Arrays.asList(7);
        Cau2 poly = new Cau2(0, coefficients);
        assertEquals(7, poly.Cal(100.0));
        assertEquals(7, poly.Cal(-50.0));
    }
    
    @Test
    public void testCalWithHighDegreePolynomial() throws ArgumentException {
        // Test case: P(x) = 1 + x + x^2 + x^3, evaluate at x = 2
        // P(2) = 1 + 2 + 4 + 8 = 15
        List<Integer> coefficients = Arrays.asList(1, 1, 1, 1);
        Cau2 poly = new Cau2(3, coefficients);
        assertEquals(15, poly.Cal(2.0));
    }
    
    @Test
    public void testCalWithDecimalX() throws ArgumentException {
        // Test case: P(x) = 2 + 3x, evaluate at x = 1.5
        // P(1.5) = 2 + 3*1.5 = 2 + 4.5 = 6.5 (truncated to 6)
        List<Integer> coefficients = Arrays.asList(2, 3);
        Cau2 poly = new Cau2(1, coefficients);
        assertEquals(6, poly.Cal(1.5));
    }
}
