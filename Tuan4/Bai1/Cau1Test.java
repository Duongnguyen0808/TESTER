import org.junit.Test;
import static org.junit.Assert.*;

public class Cau1Test {
    
    @Test
    public void testPowerWithZeroExponent() {
        // Test case: n = 0, should return 1
        assertEquals(1.0, Cau1.Power(5.0, 0), 0.0001);
        assertEquals(1.0, Cau1.Power(-3.0, 0), 0.0001);
        assertEquals(1.0, Cau1.Power(0.0, 0), 0.0001);
    }
    
    @Test
    public void testPowerWithPositiveExponent() {
        // Test case: n > 0
        assertEquals(8.0, Cau1.Power(2.0, 3), 0.0001);
        assertEquals(25.0, Cau1.Power(5.0, 2), 0.0001);
        assertEquals(27.0, Cau1.Power(3.0, 3), 0.0001);
        assertEquals(10.0, Cau1.Power(10.0, 1), 0.0001);
    }
    
    @Test
    public void testPowerWithNegativeExponent() {
        // Test case: n < 0
        assertEquals(0.125, Cau1.Power(2.0, -3), 0.0001);
        assertEquals(0.25, Cau1.Power(2.0, -2), 0.0001);
        assertEquals(0.1, Cau1.Power(10.0, -1), 0.0001);
        assertEquals(0.04, Cau1.Power(5.0, -2), 0.0001);
    }
    
    @Test
    public void testPowerWithDecimalBase() {
        // Test case: x is decimal
        assertEquals(2.25, Cau1.Power(1.5, 2), 0.0001);
        assertEquals(6.25, Cau1.Power(2.5, 2), 0.0001);
    }
    
    @Test
    public void testPowerWithNegativeBase() {
        // Test case: x is negative
        assertEquals(-8.0, Cau1.Power(-2.0, 3), 0.0001);
        assertEquals(4.0, Cau1.Power(-2.0, 2), 0.0001);
    }
    
    @Test
    public void testPowerEdgeCases() {
        // Test edge cases
        assertEquals(0.0, Cau1.Power(0.0, 5), 0.0001);
        assertEquals(1.0, Cau1.Power(1.0, 100), 0.0001);
        assertEquals(1.0, Cau1.Power(-1.0, 2), 0.0001);
        assertEquals(-1.0, Cau1.Power(-1.0, 3), 0.0001);
    }
}
