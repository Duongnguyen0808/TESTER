public class Cau4 {
    private double chieuDai;
    private double chieuRong;

    public Cau4(double chieuDai, double chieuRong) {
        this.chieuDai = chieuDai;
        this.chieuRong = chieuRong;
    }

    // Tính diện tích hình chữ nhật
    public double tinhDienTich() {
        return chieuDai * chieuRong;
    }

    // Tính chu vi hình chữ nhật
    public double tinhChuVi() {
        return 2 * (chieuDai + chieuRong);
    }

    // Kiểm tra hai hình chữ nhật có giao nhau hay không
    // Giả sử hình chữ nhật này có tọa độ góc trên trái tại (0, 0)
    // và hình kia có tọa độ góc trên trái tại (x, y)
    public boolean kiemTraGiaoNhau(Cau4 other, double x, double y) {
        // Tọa độ hình chữ nhật hiện tại: (0, 0) đến (chieuDai, chieuRong)
        // Tọa độ hình chữ nhật other: (x, y) đến (x + other.chieuDai, y + other.chieuRong)
        
        // Hai hình chữ nhật KHÔNG giao nhau khi:
        // - Hình này hoàn toàn ở bên trái hình kia: this.chieuDai <= x
        // - Hình này hoàn toàn ở bên phải hình kia: x + other.chieuDai <= 0
        // - Hình này hoàn toàn ở phía trên hình kia: this.chieuRong <= y
        // - Hình này hoàn toàn ở phía dưới hình kia: y + other.chieuRong <= 0
        
        if (this.chieuDai <= x || x + other.chieuDai <= 0 ||
            this.chieuRong <= y || y + other.chieuRong <= 0) {
            return false;
        }
        
        return true;
    }

    // Getters
    public double getChieuDai() {
        return chieuDai;
    }

    public double getChieuRong() {
        return chieuRong;
    }
}
