import java.util.List;
import java.util.ArrayList;

public class Cau3 {
    private int number;

    public Cau3(int number) throws ArgumentException {
        if (number < 0)
            throw new ArgumentException("Incorrect Value");
        
        this.number = number;
    }

    public String ConvertDecimalToAnother(int radix) throws ArgumentException {
        int n = this.number;
        
        if (radix < 2 || radix > 16)
            throw new ArgumentException("Invalid Radix");
        
        if (n == 0) {
            return "0";
        }
        
        List<String> result = new ArrayList<String>();
        while (n > 0) {
            int value = n % radix;
            if (value < 10) {
                result.add(value + "");
            } else {
                switch (value) {
                    case 10: result.add("A"); break;
                    case 11: result.add("B"); break;
                    case 12: result.add("C"); break;
                    case 13: result.add("D"); break;
                    case 14: result.add("E"); break;
                    case 15: result.add("F"); break;
                }
            }
            n /= radix;
        }
        
        // Reverse the result
        StringBuilder sb = new StringBuilder();
        for (int i = result.size() - 1; i >= 0; i--) {
            sb.append(result.get(i));
        }
        
        return sb.toString();
    }
}
