import org.junit.Test;
import static org.junit.Assert.*;

public class Cau3Test {
    
    @Test
    public void testRadixConstructorValid() throws ArgumentException {
        // Test case: Valid positive number
        Cau3 radix = new Cau3(10);
        assertNotNull(radix);
        
        Cau3 radix2 = new Cau3(0);
        assertNotNull(radix2);
    }
    
    @Test(expected = ArgumentException.class)
    public void testRadixConstructorNegativeNumber() throws ArgumentException {
        // Test case: Negative number should throw ArgumentException
        new Cau3(-5);
    }
    
    @Test(expected = ArgumentException.class)
    public void testRadixConstructorNegativeZero() throws ArgumentException {
        // Test case: Another negative test
        new Cau3(-1);
    }
    
    @Test
    public void testConvertDecimalToBinary() throws ArgumentException {
        // Test case: Convert 10 to binary (base 2)
        // 10 in decimal = 1010 in binary
        Cau3 radix = new Cau3(10);
        assertEquals("1010", radix.ConvertDecimalToAnother(2));
        
        // Test case: Convert 5 to binary
        Cau3 radix2 = new Cau3(5);
        assertEquals("101", radix2.ConvertDecimalToAnother(2));
    }
    
    @Test
    public void testConvertDecimalToOctal() throws ArgumentException {
        // Test case: Convert to octal (base 8)
        // 64 in decimal = 100 in octal
        Cau3 radix = new Cau3(64);
        assertEquals("100", radix.ConvertDecimalToAnother(8));
        
        // 15 in decimal = 17 in octal
        Cau3 radix2 = new Cau3(15);
        assertEquals("17", radix2.ConvertDecimalToAnother(8));
    }
    
    @Test
    public void testConvertDecimalToHexadecimal() throws ArgumentException {
        // Test case: Convert to hexadecimal (base 16)
        // 255 in decimal = FF in hexadecimal
        Cau3 radix = new Cau3(255);
        assertEquals("FF", radix.ConvertDecimalToAnother(16));
        
        // 26 in decimal = 1A in hexadecimal
        Cau3 radix2 = new Cau3(26);
        assertEquals("1A", radix2.ConvertDecimalToAnother(16));
        
        // 171 in decimal = AB in hexadecimal
        Cau3 radix3 = new Cau3(171);
        assertEquals("AB", radix3.ConvertDecimalToAnother(16));
    }
    
    @Test
    public void testConvertDecimalToBase5() throws ArgumentException {
        // Test case: Convert to base 5
        // 12 in decimal = 22 in base 5
        Cau3 radix = new Cau3(12);
        assertEquals("22", radix.ConvertDecimalToAnother(5));
    }
    
    @Test(expected = ArgumentException.class)
    public void testConvertWithRadixTooSmall() throws ArgumentException {
        // Test case: Radix < 2 should throw ArgumentException
        Cau3 radix = new Cau3(10);
        radix.ConvertDecimalToAnother(1);
    }
    
    @Test(expected = ArgumentException.class)
    public void testConvertWithRadixTooLarge() throws ArgumentException {
        // Test case: Radix > 16 should throw ArgumentException
        Cau3 radix = new Cau3(10);
        radix.ConvertDecimalToAnother(17);
    }
    
    @Test(expected = ArgumentException.class)
    public void testConvertWithRadixZero() throws ArgumentException {
        // Test case: Radix = 0 should throw ArgumentException
        Cau3 radix = new Cau3(10);
        radix.ConvertDecimalToAnother(0);
    }
    
    @Test(expected = ArgumentException.class)
    public void testConvertWithRadixNegative() throws ArgumentException {
        // Test case: negative Radix should throw ArgumentException
        Cau3 radix = new Cau3(10);
        radix.ConvertDecimalToAnother(-5);
    }
    
    @Test
    public void testConvertAllValidRadixes() throws ArgumentException {
        // Test case: Test all valid Radix values from 2 to 16
        Cau3 radix = new Cau3(100);
        
        // Just ensure no exception is thrown for all valid radix
        for (int i = 2; i <= 16; i++) {
            String result = radix.ConvertDecimalToAnother(i);
            assertNotNull(result);
            assertTrue(result.length() > 0);
        }
    }
    
    @Test
    public void testConvertBoundaryRadixValues() throws ArgumentException {
        // Test case: Test boundary values Radix = 2 and Radix = 16
        Cau3 radix = new Cau3(100);
        
        // Radix = 2 (minimum valid)
        assertEquals("1100100", radix.ConvertDecimalToAnother(2));
        
        // Radix = 16 (maximum valid)
        assertEquals("64", radix.ConvertDecimalToAnother(16));
    }
    
    @Test
    public void testConvertWithLettersInHex() throws ArgumentException {
        // Test case: Ensure letters A-F are used correctly
        Cau3 radix = new Cau3(250);
        String hex = radix.ConvertDecimalToAnother(16);
        assertEquals("FA", hex);
        
        // Test with number that produces all hex letters
        Cau3 radix2 = new Cau3(3822); // = EEE in hex
        assertEquals("EEE", radix2.ConvertDecimalToAnother(16));
    }
}
