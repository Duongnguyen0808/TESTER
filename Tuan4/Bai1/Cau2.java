import java.util.List;

public class Cau2 {
    private int n;
    private List<Integer> a;

    public Cau2(int n, List<Integer> a) throws ArgumentException {
        if (a.size() != n + 1) {
            throw new ArgumentException("Invalid Data");
        }
        this.n = n;
        this.a = a;
    }

    public int Cal(double x) {
        int result = 0;
        for (int i = 0; i <= this.n; i++) {
            result += (int)(a.get(i) * Math.pow(x, i));
        }
        return result;
    }
}

class ArgumentException extends Exception {
    public ArgumentException(String message) {
        super(message);
    }
}
