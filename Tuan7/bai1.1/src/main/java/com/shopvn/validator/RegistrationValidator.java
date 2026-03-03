package com.shopvn.validator;

import com.shopvn.model.RegistrationForm;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Validator cho Form Đăng Ký Tài Khoản – ShopVN.vn
 * 
 * Quy tắc validation:
 * - Họ và tên (*): Chỉ chữ cái (có dấu hoặc không dấu) và dấu cách. Độ dài 2–50 ký tự.
 * - Tên đăng nhập (*): Chữ thường, số, dấu gạch dưới. Bắt đầu bằng chữ cái. Dài 5–20 ký tự. Duy nhất.
 * - Email (*): Đúng định dạng email chuẩn RFC 5322. Chưa được đăng ký trong hệ thống.
 * - Số điện thoại (*): Số Việt Nam: bắt đầu bằng 0, gồm 10 chữ số liên tiếp.
 * - Mật khẩu (*): 8–32 ký tự. Phải có ít nhất: 1 chữ hoa, 1 chữ thường, 1 chữ số, 1 ký tự đặc biệt.
 * - Xác nhận mật khẩu (*): Phải trùng khớp hoàn toàn với trường Mật khẩu.
 * - Ngày sinh: Không bắt buộc. Định dạng dd/mm/yyyy. Phải từ 16 tuổi đến dưới 100 tuổi.
 * - Giới tính: Không bắt buộc. Nam / Nữ / Không muốn tiết lộ.
 * - Mã giới thiệu: Không bắt buộc. 8 ký tự chữ hoa và số. Hệ thống kiểm tra mã có tồn tại trong CSDL.
 * - Đồng ý Điều khoản (*): Checkbox phải được tích.
 */
public class RegistrationValidator {

    // Giả lập danh sách email đã tồn tại trong hệ thống
    private Set<String> existingEmails = new HashSet<>(Arrays.asList(
            "user@shopvn.vn", "admin@shopvn.vn", "test@gmail.com", "existing@email.com"
    ));

    // Giả lập danh sách tên đăng nhập đã tồn tại
    private Set<String> existingUsernames = new HashSet<>(Arrays.asList(
            "admin", "nguyen_van_a", "testuser", "existing_user"
    ));

    // Giả lập danh sách mã giới thiệu hợp lệ trong CSDL
    private Set<String> validReferralCodes = new HashSet<>(Arrays.asList(
            "ABCD1234", "SHOP2024", "REF12345", "VN123456"
    ));

    /**
     * Validate toàn bộ form đăng ký.
     * @return Map chứa tên trường và thông báo lỗi. Rỗng nếu hợp lệ.
     */
    public Map<String, String> validate(RegistrationForm form) {
        Map<String, String> errors = new LinkedHashMap<>();

        String hoTenError = validateHoTen(form.getHoTen());
        if (hoTenError != null) errors.put("hoTen", hoTenError);

        String tenDangNhapError = validateTenDangNhap(form.getTenDangNhap());
        if (tenDangNhapError != null) errors.put("tenDangNhap", tenDangNhapError);

        String emailError = validateEmail(form.getEmail());
        if (emailError != null) errors.put("email", emailError);

        String sdtError = validateSoDienThoai(form.getSoDienThoai());
        if (sdtError != null) errors.put("soDienThoai", sdtError);

        String matKhauError = validateMatKhau(form.getMatKhau());
        if (matKhauError != null) errors.put("matKhau", matKhauError);

        String xacNhanError = validateXacNhanMatKhau(form.getMatKhau(), form.getXacNhanMatKhau());
        if (xacNhanError != null) errors.put("xacNhanMatKhau", xacNhanError);

        String ngaySinhError = validateNgaySinh(form.getNgaySinh());
        if (ngaySinhError != null) errors.put("ngaySinh", ngaySinhError);

        String gioiTinhError = validateGioiTinh(form.getGioiTinh());
        if (gioiTinhError != null) errors.put("gioiTinh", gioiTinhError);

        String maGioiThieuError = validateMaGioiThieu(form.getMaGioiThieu());
        if (maGioiThieuError != null) errors.put("maGioiThieu", maGioiThieuError);

        String dieuKhoanError = validateDongYDieuKhoan(form.isDongYDieuKhoan());
        if (dieuKhoanError != null) errors.put("dongYDieuKhoan", dieuKhoanError);

        return errors;
    }

    // ===== VALIDATE HỌ VÀ TÊN =====
    // Bắt buộc. Chỉ chữ cái (có dấu/không dấu) và dấu cách. Độ dài 2–50 ký tự.
    public String validateHoTen(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            return "Họ và tên không được để trống";
        }
        if (hoTen.length() < 2) {
            return "Họ và tên phải có ít nhất 2 ký tự";
        }
        if (hoTen.length() > 50) {
            return "Họ và tên không được vượt quá 50 ký tự";
        }
        // Chỉ cho phép chữ cái (Unicode bao gồm tiếng Việt) và dấu cách
        if (!hoTen.matches("^[\\p{L} ]+$")) {
            return "Họ và tên chỉ được chứa chữ cái và dấu cách";
        }
        return null;
    }

    // ===== VALIDATE TÊN ĐĂNG NHẬP =====
    // Bắt buộc. Chữ thường, số, dấu gạch dưới. Bắt đầu bằng chữ cái. Dài 5–20 ký tự. Duy nhất.
    public String validateTenDangNhap(String tenDangNhap) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            return "Tên đăng nhập không được để trống";
        }
        if (tenDangNhap.length() < 5) {
            return "Tên đăng nhập phải có ít nhất 5 ký tự";
        }
        if (tenDangNhap.length() > 20) {
            return "Tên đăng nhập không được vượt quá 20 ký tự";
        }
        if (!tenDangNhap.matches("^[a-z][a-z0-9_]*$")) {
            return "Tên đăng nhập chỉ chứa chữ thường, số, gạch dưới và bắt đầu bằng chữ cái";
        }
        if (existingUsernames.contains(tenDangNhap)) {
            return "Tên đăng nhập đã tồn tại trong hệ thống";
        }
        return null;
    }

    // ===== VALIDATE EMAIL =====
    // Bắt buộc. Đúng định dạng email chuẩn RFC 5322. Chưa được đăng ký trong hệ thống.
    public String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống";
        }
        // Kiểm tra định dạng email cơ bản theo RFC 5322
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            return "Email không đúng định dạng";
        }
        if (existingEmails.contains(email.toLowerCase())) {
            return "Email đã được đăng ký trong hệ thống";
        }
        return null;
    }

    // ===== VALIDATE SỐ ĐIỆN THOẠI =====
    // Bắt buộc. Số Việt Nam: bắt đầu bằng 0, gồm 10 chữ số liên tiếp. VD: 0987654321.
    public String validateSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }
        if (!soDienThoai.matches("^0\\d{9}$")) {
            return "Số điện thoại phải bắt đầu bằng 0 và gồm đúng 10 chữ số";
        }
        return null;
    }

    // ===== VALIDATE MẬT KHẨU =====
    // Bắt buộc. 8–32 ký tự. Phải có ít nhất: 1 chữ hoa, 1 chữ thường, 1 chữ số, 1 ký tự đặc biệt.
    public String validateMatKhau(String matKhau) {
        if (matKhau == null || matKhau.trim().isEmpty()) {
            return "Mật khẩu không được để trống";
        }
        if (matKhau.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }
        if (matKhau.length() > 32) {
            return "Mật khẩu không được vượt quá 32 ký tự";
        }
        if (!matKhau.matches(".*[A-Z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ hoa";
        }
        if (!matKhau.matches(".*[a-z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ thường";
        }
        if (!matKhau.matches(".*\\d.*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ số";
        }
        if (!matKhau.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt";
        }
        return null;
    }

    // ===== VALIDATE XÁC NHẬN MẬT KHẨU =====
    // Bắt buộc. Phải trùng khớp hoàn toàn với trường Mật khẩu.
    public String validateXacNhanMatKhau(String matKhau, String xacNhanMatKhau) {
        if (xacNhanMatKhau == null || xacNhanMatKhau.trim().isEmpty()) {
            return "Xác nhận mật khẩu không được để trống";
        }
        if (matKhau != null && !matKhau.equals(xacNhanMatKhau)) {
            return "Xác nhận mật khẩu không khớp với mật khẩu";
        }
        return null;
    }

    // ===== VALIDATE NGÀY SINH =====
    // Không bắt buộc. Định dạng dd/mm/yyyy. Phải từ 16 tuổi đến dưới 100 tuổi tính đến ngày đăng ký.
    public String validateNgaySinh(String ngaySinh) {
        if (ngaySinh == null || ngaySinh.trim().isEmpty()) {
            return null; // Không bắt buộc
        }
        // Kiểm tra định dạng dd/mm/yyyy
        if (!ngaySinh.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            return "Ngày sinh phải có định dạng dd/mm/yyyy";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDate = LocalDate.parse(ngaySinh, formatter);
            LocalDate today = LocalDate.now();
            long age = ChronoUnit.YEARS.between(birthDate, today);
            if (age < 16) {
                return "Bạn phải đủ 16 tuổi để đăng ký";
            }
            if (age >= 100) {
                return "Ngày sinh không hợp lệ (trên 100 tuổi)";
            }
        } catch (DateTimeParseException e) {
            return "Ngày sinh không hợp lệ";
        }
        return null;
    }

    // ===== VALIDATE GIỚI TÍNH =====
    // Không bắt buộc. Radiobutton: Nam / Nữ / Không muốn tiết lộ.
    public String validateGioiTinh(String gioiTinh) {
        if (gioiTinh == null || gioiTinh.trim().isEmpty()) {
            return null; // Không bắt buộc
        }
        Set<String> validValues = new HashSet<>(Arrays.asList("Nam", "Nữ", "Không muốn tiết lộ"));
        if (!validValues.contains(gioiTinh)) {
            return "Giới tính phải là: Nam, Nữ hoặc Không muốn tiết lộ";
        }
        return null;
    }

    // ===== VALIDATE MÃ GIỚI THIỆU =====
    // Không bắt buộc. 8 ký tự chữ hoa và số. Hệ thống kiểm tra mã có tồn tại trong CSDL.
    public String validateMaGioiThieu(String maGioiThieu) {
        if (maGioiThieu == null || maGioiThieu.trim().isEmpty()) {
            return null; // Không bắt buộc
        }
        if (!maGioiThieu.matches("^[A-Z0-9]{8}$")) {
            return "Mã giới thiệu phải gồm 8 ký tự chữ hoa và số";
        }
        if (!validReferralCodes.contains(maGioiThieu)) {
            return "Mã giới thiệu không tồn tại trong hệ thống";
        }
        return null;
    }

    // ===== VALIDATE ĐỒNG Ý ĐIỀU KHOẢN =====
    // Bắt buộc. Checkbox phải được tích.
    public String validateDongYDieuKhoan(boolean dongYDieuKhoan) {
        if (!dongYDieuKhoan) {
            return "Bạn phải đồng ý với Điều khoản sử dụng";
        }
        return null;
    }

    // ===== SETTERS cho danh sách giả lập (dùng trong test) =====
    public void setExistingEmails(Set<String> existingEmails) {
        this.existingEmails = existingEmails;
    }

    public void setExistingUsernames(Set<String> existingUsernames) {
        this.existingUsernames = existingUsernames;
    }

    public void setValidReferralCodes(Set<String> validReferralCodes) {
        this.validReferralCodes = validReferralCodes;
    }
}
