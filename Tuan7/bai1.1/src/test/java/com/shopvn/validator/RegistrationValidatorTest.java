package com.shopvn.validator;

import com.shopvn.model.RegistrationForm;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Bộ test case cho Form Đăng Ký Tài Khoản – ShopVN.vn
 * 
 * Áp dụng kỹ thuật Equivalence Partitioning (EP) và Boundary Value Analysis (BVA).
 * 
 * TC ID có hệ thống:
 *   - TC_REG_EP_xxx  : Test case phân hoạch tương đương
 *   - TC_REG_BVA_xxx : Test case giá trị biên
 *   - TC_REG_SPEC_xxx: Test case đặc biệt (logic nghiệp vụ)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationValidatorTest {

    private RegistrationValidator validator;

    @BeforeEach
    void setUp() {
        validator = new RegistrationValidator();
    }

    // ===== Helper: Tạo form hợp lệ mặc định =====
    private RegistrationForm createValidForm() {
        RegistrationForm form = new RegistrationForm();
        form.setHoTen("Nguyễn Văn An");
        form.setTenDangNhap("nguyenvanan");
        form.setEmail("nguyenvanan@gmail.com");
        form.setSoDienThoai("0987654321");
        form.setMatKhau("Abc@1234");
        form.setXacNhanMatKhau("Abc@1234");
        form.setNgaySinh("15/06/2000");
        form.setGioiTinh("Nam");
        form.setMaGioiThieu(null);
        form.setDongYDieuKhoan(true);
        return form;
    }

    // ============================================================
    // TC_REG_EP_001: Form hợp lệ hoàn toàn - tất cả trường đúng
    // Kỹ thuật: EP - Lớp hợp lệ cho TẤT CẢ trường
    // ============================================================
    @Test
    @Order(1)
    @DisplayName("TC_REG_EP_001 - Form hợp lệ hoàn toàn")
    void TC_REG_EP_001_formHopLeHoanToan() {
        RegistrationForm form = createValidForm();
        Map<String, String> errors = validator.validate(form);
        assertTrue(errors.isEmpty(), "Form hợp lệ không được có lỗi. Lỗi: " + errors);
    }

    // ============================================================
    // TC_REG_EP_002: Họ tên để trống
    // EP - Lớp KHÔNG hợp lệ: Trường bắt buộc bị bỏ trống
    // ============================================================
    @Test
    @Order(2)
    @DisplayName("TC_REG_EP_002 - Họ tên để trống")
    void TC_REG_EP_002_hoTenTrong() {
        assertNotNull(validator.validateHoTen(""),
                "Họ tên trống phải trả về lỗi");
        assertNotNull(validator.validateHoTen(null),
                "Họ tên null phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_003: Họ tên chứa số → không hợp lệ
    // EP - Lớp KHÔNG hợp lệ: Chứa ký tự không cho phép
    // ============================================================
    @Test
    @Order(3)
    @DisplayName("TC_REG_EP_003 - Họ tên chứa số")
    void TC_REG_EP_003_hoTenChuaSo() {
        assertNotNull(validator.validateHoTen("Nguyen Van 123"),
                "Họ tên chứa số phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_004: Họ tên chứa ký tự đặc biệt → không hợp lệ
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(4)
    @DisplayName("TC_REG_EP_004 - Họ tên chứa ký tự đặc biệt")
    void TC_REG_EP_004_hoTenChuaKyTuDacBiet() {
        assertNotNull(validator.validateHoTen("Nguyễn @Văn"),
                "Họ tên chứa ký tự đặc biệt phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_005: Họ tên hợp lệ có dấu tiếng Việt
    // EP - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(5)
    @DisplayName("TC_REG_EP_005 - Họ tên hợp lệ có dấu tiếng Việt")
    void TC_REG_EP_005_hoTenHopLeTiengViet() {
        assertNull(validator.validateHoTen("Trần Thị Bích Ngọc"),
                "Họ tên tiếng Việt hợp lệ phải trả về null");
    }

    // ============================================================
    // TC_REG_BVA_001: Họ tên đúng 1 ký tự (min-1) → không hợp lệ
    // BVA - Biên dưới
    // ============================================================
    @Test
    @Order(6)
    @DisplayName("TC_REG_BVA_001 - Họ tên 1 ký tự (dưới biên min)")
    void TC_REG_BVA_001_hoTen1KyTu() {
        assertNotNull(validator.validateHoTen("A"),
                "Họ tên 1 ký tự phải trả về lỗi (min=2)");
    }

    // ============================================================
    // TC_REG_BVA_002: Họ tên đúng 2 ký tự (min) → hợp lệ
    // BVA - Biên min
    // ============================================================
    @Test
    @Order(7)
    @DisplayName("TC_REG_BVA_002 - Họ tên 2 ký tự (biên min)")
    void TC_REG_BVA_002_hoTen2KyTu() {
        assertNull(validator.validateHoTen("An"),
                "Họ tên 2 ký tự phải hợp lệ (min=2)");
    }

    // ============================================================
    // TC_REG_BVA_003: Họ tên đúng 3 ký tự (min+1) → hợp lệ
    // BVA - Biên min+1
    // ============================================================
    @Test
    @Order(8)
    @DisplayName("TC_REG_BVA_003 - Họ tên 3 ký tự (min+1)")
    void TC_REG_BVA_003_hoTen3KyTu() {
        assertNull(validator.validateHoTen("Anh"),
                "Họ tên 3 ký tự phải hợp lệ (min+1)");
    }

    // ============================================================
    // TC_REG_BVA_004: Họ tên đúng 49 ký tự (max-1) → hợp lệ
    // BVA - Biên max-1
    // ============================================================
    @Test
    @Order(9)
    @DisplayName("TC_REG_BVA_004 - Họ tên 49 ký tự (max-1)")
    void TC_REG_BVA_004_hoTen49KyTu() {
        String name49 = "A".repeat(49);
        assertNull(validator.validateHoTen(name49),
                "Họ tên 49 ký tự phải hợp lệ (max-1=49)");
    }

    // ============================================================
    // TC_REG_BVA_005: Họ tên đúng 50 ký tự (max) → hợp lệ
    // BVA - Biên max
    // ============================================================
    @Test
    @Order(10)
    @DisplayName("TC_REG_BVA_005 - Họ tên 50 ký tự (biên max)")
    void TC_REG_BVA_005_hoTen50KyTu() {
        String name50 = "A".repeat(50);
        assertNull(validator.validateHoTen(name50),
                "Họ tên 50 ký tự phải hợp lệ (max=50)");
    }

    // ============================================================
    // TC_REG_BVA_006: Họ tên 51 ký tự (max+1) → không hợp lệ
    // BVA - Biên max+1
    // ============================================================
    @Test
    @Order(11)
    @DisplayName("TC_REG_BVA_006 - Họ tên 51 ký tự (trên biên max)")
    void TC_REG_BVA_006_hoTen51KyTu() {
        String name51 = "A".repeat(51);
        assertNotNull(validator.validateHoTen(name51),
                "Họ tên 51 ký tự phải trả về lỗi (max+1)");
    }

    // ============================================================
    // TC_REG_EP_006: Tên đăng nhập hợp lệ
    // EP - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(12)
    @DisplayName("TC_REG_EP_006 - Tên đăng nhập hợp lệ")
    void TC_REG_EP_006_tenDangNhapHopLe() {
        assertNull(validator.validateTenDangNhap("user_name01"),
                "Tên đăng nhập hợp lệ phải trả về null");
    }

    // ============================================================
    // TC_REG_EP_007: Tên đăng nhập bắt đầu bằng số → không hợp lệ
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(13)
    @DisplayName("TC_REG_EP_007 - Tên đăng nhập bắt đầu bằng số")
    void TC_REG_EP_007_tenDangNhapBatDauBangSo() {
        assertNotNull(validator.validateTenDangNhap("1user_name"),
                "Tên đăng nhập bắt đầu bằng số phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_008: Tên đăng nhập chứa chữ hoa → không hợp lệ
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(14)
    @DisplayName("TC_REG_EP_008 - Tên đăng nhập chứa chữ hoa")
    void TC_REG_EP_008_tenDangNhapChuaHoa() {
        assertNotNull(validator.validateTenDangNhap("UserName"),
                "Tên đăng nhập chứa chữ hoa phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_BVA_007: Tên đăng nhập 4 ký tự (min-1) → không hợp lệ
    // BVA - Biên dưới min
    // ============================================================
    @Test
    @Order(15)
    @DisplayName("TC_REG_BVA_007 - Tên đăng nhập 4 ký tự (dưới min)")
    void TC_REG_BVA_007_tenDangNhap4KyTu() {
        assertNotNull(validator.validateTenDangNhap("user"),
                "Tên đăng nhập 4 ký tự phải trả về lỗi (min=5)");
    }

    // ============================================================
    // TC_REG_BVA_008: Tên đăng nhập 5 ký tự (min) → hợp lệ
    // BVA - Biên min
    // ============================================================
    @Test
    @Order(16)
    @DisplayName("TC_REG_BVA_008 - Tên đăng nhập 5 ký tự (biên min)")
    void TC_REG_BVA_008_tenDangNhap5KyTu() {
        assertNull(validator.validateTenDangNhap("userx"),
                "Tên đăng nhập 5 ký tự phải hợp lệ (min=5)");
    }

    // ============================================================
    // TC_REG_BVA_009: Tên đăng nhập 6 ký tự (min+1) → hợp lệ
    // BVA - Biên min+1
    // ============================================================
    @Test
    @Order(17)
    @DisplayName("TC_REG_BVA_009 - Tên đăng nhập 6 ký tự (min+1)")
    void TC_REG_BVA_009_tenDangNhap6KyTu() {
        assertNull(validator.validateTenDangNhap("userxy"),
                "Tên đăng nhập 6 ký tự phải hợp lệ (min+1=6)");
    }

    // ============================================================
    // TC_REG_BVA_010: Tên đăng nhập 20 ký tự (max) → hợp lệ
    // BVA - Biên max
    // ============================================================
    @Test
    @Order(18)
    @DisplayName("TC_REG_BVA_010 - Tên đăng nhập 20 ký tự (biên max)")
    void TC_REG_BVA_010_tenDangNhap20KyTu() {
        // "a" + 19 chars = 20 total
        String username20 = "a" + "b".repeat(19);
        assertNull(validator.validateTenDangNhap(username20),
                "Tên đăng nhập 20 ký tự phải hợp lệ (max=20)");
    }

    // ============================================================
    // TC_REG_BVA_011: Tên đăng nhập 21 ký tự (max+1) → không hợp lệ
    // BVA - Biên max+1
    // ============================================================
    @Test
    @Order(19)
    @DisplayName("TC_REG_BVA_011 - Tên đăng nhập 21 ký tự (trên max)")
    void TC_REG_BVA_011_tenDangNhap21KyTu() {
        String username21 = "a" + "b".repeat(20);
        assertNotNull(validator.validateTenDangNhap(username21),
                "Tên đăng nhập 21 ký tự phải trả về lỗi (max+1)");
    }

    // ============================================================
    // TC_REG_EP_009: Email hợp lệ
    // EP - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(20)
    @DisplayName("TC_REG_EP_009 - Email hợp lệ")
    void TC_REG_EP_009_emailHopLe() {
        assertNull(validator.validateEmail("newuser@domain.com"),
                "Email hợp lệ phải trả về null");
    }

    // ============================================================
    // TC_REG_EP_010: Email thiếu @
    // EP - Lớp KHÔNG hợp lệ: Sai định dạng
    // ============================================================
    @Test
    @Order(21)
    @DisplayName("TC_REG_EP_010 - Email thiếu ký tự @")
    void TC_REG_EP_010_emailThieuAtSign() {
        assertNotNull(validator.validateEmail("userdomain.com"),
                "Email thiếu @ phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_011: Email thiếu domain
    // EP - Lớp KHÔNG hợp lệ: Sai định dạng
    // ============================================================
    @Test
    @Order(22)
    @DisplayName("TC_REG_EP_011 - Email thiếu domain")
    void TC_REG_EP_011_emailThieuDomain() {
        assertNotNull(validator.validateEmail("user@"),
                "Email thiếu domain phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_SPEC_001: Email đã tồn tại (duplicate)
    // SPEC - Kiểm tra logic nghiệp vụ: email đã đăng ký
    // ============================================================
    @Test
    @Order(23)
    @DisplayName("TC_REG_SPEC_001 - Email đã tồn tại trong hệ thống")
    void TC_REG_SPEC_001_emailDaTonTai() {
        String error = validator.validateEmail("test@gmail.com");
        assertNotNull(error, "Email đã tồn tại phải trả về lỗi");
        assertTrue(error.contains("đã được đăng ký"),
                "Lỗi phải thông báo email đã được đăng ký");
    }

    // ============================================================
    // TC_REG_EP_012: Số điện thoại hợp lệ
    // EP - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(24)
    @DisplayName("TC_REG_EP_012 - Số điện thoại hợp lệ")
    void TC_REG_EP_012_soDienThoaiHopLe() {
        assertNull(validator.validateSoDienThoai("0987654321"),
                "Số điện thoại hợp lệ phải trả về null");
    }

    // ============================================================
    // TC_REG_EP_013: Số điện thoại không bắt đầu bằng 0
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(25)
    @DisplayName("TC_REG_EP_013 - SĐT không bắt đầu bằng 0")
    void TC_REG_EP_013_sdtKhongBatDauBang0() {
        assertNotNull(validator.validateSoDienThoai("1234567890"),
                "SĐT không bắt đầu bằng 0 phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_014: Số điện thoại 9 chữ số (thiếu)
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(26)
    @DisplayName("TC_REG_EP_014 - SĐT 9 chữ số (thiếu)")
    void TC_REG_EP_014_sdt9ChuSo() {
        assertNotNull(validator.validateSoDienThoai("098765432"),
                "SĐT 9 chữ số phải trả về lỗi (cần đúng 10)");
    }

    // ============================================================
    // TC_REG_EP_015: Số điện thoại 11 chữ số (thừa)
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(27)
    @DisplayName("TC_REG_EP_015 - SĐT 11 chữ số (thừa)")
    void TC_REG_EP_015_sdt11ChuSo() {
        assertNotNull(validator.validateSoDienThoai("09876543210"),
                "SĐT 11 chữ số phải trả về lỗi (cần đúng 10)");
    }

    // ============================================================
    // TC_REG_EP_016: Mật khẩu hợp lệ
    // EP - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(28)
    @DisplayName("TC_REG_EP_016 - Mật khẩu hợp lệ")
    void TC_REG_EP_016_matKhauHopLe() {
        assertNull(validator.validateMatKhau("Abc@1234"),
                "Mật khẩu hợp lệ phải trả về null");
    }

    // ============================================================
    // TC_REG_EP_017: Mật khẩu thiếu chữ hoa
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(29)
    @DisplayName("TC_REG_EP_017 - Mật khẩu thiếu chữ hoa")
    void TC_REG_EP_017_matKhauThieuChuHoa() {
        assertNotNull(validator.validateMatKhau("abc@1234"),
                "Mật khẩu thiếu chữ hoa phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_018: Mật khẩu thiếu chữ thường
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(30)
    @DisplayName("TC_REG_EP_018 - Mật khẩu thiếu chữ thường")
    void TC_REG_EP_018_matKhauThieuChuThuong() {
        assertNotNull(validator.validateMatKhau("ABC@1234"),
                "Mật khẩu thiếu chữ thường phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_019: Mật khẩu thiếu số
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(31)
    @DisplayName("TC_REG_EP_019 - Mật khẩu thiếu số")
    void TC_REG_EP_019_matKhauThieuSo() {
        assertNotNull(validator.validateMatKhau("Abcdefg@"),
                "Mật khẩu thiếu số phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_020: Mật khẩu thiếu ký tự đặc biệt
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(32)
    @DisplayName("TC_REG_EP_020 - Mật khẩu thiếu ký tự đặc biệt")
    void TC_REG_EP_020_matKhauThieuKyTuDacBiet() {
        assertNotNull(validator.validateMatKhau("Abcdefg1"),
                "Mật khẩu thiếu ký tự đặc biệt phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_BVA_012: Mật khẩu 7 ký tự (min-1) → không hợp lệ
    // BVA - Biên dưới min
    // ============================================================
    @Test
    @Order(33)
    @DisplayName("TC_REG_BVA_012 - Mật khẩu 7 ký tự (dưới min)")
    void TC_REG_BVA_012_matKhau7KyTu() {
        assertNotNull(validator.validateMatKhau("Ab@1234"),
                "Mật khẩu 7 ký tự phải trả về lỗi (min=8)");
    }

    // ============================================================
    // TC_REG_BVA_013: Mật khẩu 8 ký tự (min) → hợp lệ
    // BVA - Biên min
    // ============================================================
    @Test
    @Order(34)
    @DisplayName("TC_REG_BVA_013 - Mật khẩu 8 ký tự (biên min)")
    void TC_REG_BVA_013_matKhau8KyTu() {
        assertNull(validator.validateMatKhau("Abc@1234"),
                "Mật khẩu 8 ký tự phải hợp lệ (min=8)");
    }

    // ============================================================
    // TC_REG_BVA_014: Mật khẩu 9 ký tự (min+1) → hợp lệ
    // BVA - Biên min+1
    // ============================================================
    @Test
    @Order(35)
    @DisplayName("TC_REG_BVA_014 - Mật khẩu 9 ký tự (min+1)")
    void TC_REG_BVA_014_matKhau9KyTu() {
        assertNull(validator.validateMatKhau("Abcd@1234"),
                "Mật khẩu 9 ký tự phải hợp lệ (min+1=9)");
    }

    // ============================================================
    // TC_REG_BVA_015: Mật khẩu 32 ký tự (max) → hợp lệ
    // BVA - Biên max
    // ============================================================
    @Test
    @Order(36)
    @DisplayName("TC_REG_BVA_015 - Mật khẩu 32 ký tự (biên max)")
    void TC_REG_BVA_015_matKhau32KyTu() {
        // 32 ký tự: có chữ hoa, thường, số, đặc biệt
        String pw32 = "Aa@1" + "x".repeat(28); // 4 + 28 = 32
        assertNull(validator.validateMatKhau(pw32),
                "Mật khẩu 32 ký tự phải hợp lệ (max=32)");
    }

    // ============================================================
    // TC_REG_BVA_016: Mật khẩu 33 ký tự (max+1) → không hợp lệ
    // BVA - Biên max+1
    // ============================================================
    @Test
    @Order(37)
    @DisplayName("TC_REG_BVA_016 - Mật khẩu 33 ký tự (trên max)")
    void TC_REG_BVA_016_matKhau33KyTu() {
        String pw33 = "Aa@1" + "x".repeat(29); // 4 + 29 = 33
        assertNotNull(validator.validateMatKhau(pw33),
                "Mật khẩu 33 ký tự phải trả về lỗi (max+1)");
    }

    // ============================================================
    // TC_REG_SPEC_002: Xác nhận mật khẩu không khớp
    // SPEC - Kiểm tra logic: password mismatch
    // ============================================================
    @Test
    @Order(38)
    @DisplayName("TC_REG_SPEC_002 - Xác nhận mật khẩu không khớp")
    void TC_REG_SPEC_002_xacNhanMatKhauKhongKhop() {
        String error = validator.validateXacNhanMatKhau("Abc@1234", "Abc@12345");
        assertNotNull(error, "Xác nhận mật khẩu không khớp phải trả về lỗi");
        assertTrue(error.contains("không khớp"), "Lỗi phải thông báo không khớp");
    }

    // ============================================================
    // TC_REG_SPEC_003: Xác nhận mật khẩu khớp đúng
    // SPEC - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(39)
    @DisplayName("TC_REG_SPEC_003 - Xác nhận mật khẩu khớp đúng")
    void TC_REG_SPEC_003_xacNhanMatKhauKhop() {
        assertNull(validator.validateXacNhanMatKhau("Abc@1234", "Abc@1234"),
                "Xác nhận mật khẩu khớp phải trả về null");
    }

    // ============================================================
    // TC_REG_BVA_017: Ngày sinh - đúng 16 tuổi (biên min tuổi) → hợp lệ
    // BVA - Biên tuổi min
    // ============================================================
    @Test
    @Order(40)
    @DisplayName("TC_REG_BVA_017 - Ngày sinh đúng 16 tuổi (biên min)")
    void TC_REG_BVA_017_ngaySinh16Tuoi() {
        LocalDate sixteenYearsAgo = LocalDate.now().minusYears(16);
        String dob = sixteenYearsAgo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertNull(validator.validateNgaySinh(dob),
                "Ngày sinh đúng 16 tuổi phải hợp lệ");
    }

    // ============================================================
    // TC_REG_BVA_018: Ngày sinh - 15 tuổi (dưới biên min tuổi) → không hợp lệ
    // BVA - Dưới biên min tuổi
    // ============================================================
    @Test
    @Order(41)
    @DisplayName("TC_REG_BVA_018 - Ngày sinh 15 tuổi (dưới min)")
    void TC_REG_BVA_018_ngaySinh15Tuoi() {
        LocalDate fifteenYearsAgo = LocalDate.now().minusYears(15);
        String dob = fifteenYearsAgo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertNotNull(validator.validateNgaySinh(dob),
                "Ngày sinh 15 tuổi phải trả về lỗi (min=16)");
    }

    // ============================================================
    // TC_REG_BVA_019: Ngày sinh - 99 tuổi (max-1) → hợp lệ
    // BVA - Biên trên max-1
    // ============================================================
    @Test
    @Order(42)
    @DisplayName("TC_REG_BVA_019 - Ngày sinh 99 tuổi (max-1)")
    void TC_REG_BVA_019_ngaySinh99Tuoi() {
        LocalDate ninetyNineYearsAgo = LocalDate.now().minusYears(99);
        String dob = ninetyNineYearsAgo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertNull(validator.validateNgaySinh(dob),
                "Ngày sinh 99 tuổi phải hợp lệ (max-1)");
    }

    // ============================================================
    // TC_REG_BVA_020: Ngày sinh - 100 tuổi (max) → không hợp lệ
    // BVA - Biên max tuổi
    // ============================================================
    @Test
    @Order(43)
    @DisplayName("TC_REG_BVA_020 - Ngày sinh 100 tuổi (biên max)")
    void TC_REG_BVA_020_ngaySinh100Tuoi() {
        LocalDate hundredYearsAgo = LocalDate.now().minusYears(100);
        String dob = hundredYearsAgo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertNotNull(validator.validateNgaySinh(dob),
                "Ngày sinh 100 tuổi phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_021: Ngày sinh sai định dạng
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(44)
    @DisplayName("TC_REG_EP_021 - Ngày sinh sai định dạng")
    void TC_REG_EP_021_ngaySinhSaiDinhDang() {
        assertNotNull(validator.validateNgaySinh("2000-06-15"),
                "Ngày sinh sai định dạng (yyyy-mm-dd) phải trả về lỗi");
        assertNotNull(validator.validateNgaySinh("15-06-2000"),
                "Ngày sinh sai định dạng (dd-mm-yyyy) phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_022: Ngày sinh để trống (không bắt buộc) → hợp lệ
    // EP - Lớp HỢP LỆ (optional)
    // ============================================================
    @Test
    @Order(45)
    @DisplayName("TC_REG_EP_022 - Ngày sinh để trống (không bắt buộc)")
    void TC_REG_EP_022_ngaySinhTrong() {
        assertNull(validator.validateNgaySinh(null),
                "Ngày sinh trống phải hợp lệ (không bắt buộc)");
        assertNull(validator.validateNgaySinh(""),
                "Ngày sinh rỗng phải hợp lệ (không bắt buộc)");
    }

    // ============================================================
    // TC_REG_SPEC_004: Mã giới thiệu đúng format, tồn tại trong CSDL
    // SPEC - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(46)
    @DisplayName("TC_REG_SPEC_004 - Mã giới thiệu hợp lệ & tồn tại CSDL")
    void TC_REG_SPEC_004_maGioiThieuHopLe() {
        assertNull(validator.validateMaGioiThieu("ABCD1234"),
                "Mã giới thiệu hợp lệ phải trả về null");
    }

    // ============================================================
    // TC_REG_SPEC_005: Mã giới thiệu sai format (chữ thường)
    // SPEC - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(47)
    @DisplayName("TC_REG_SPEC_005 - Mã giới thiệu sai format")
    void TC_REG_SPEC_005_maGioiThieuSaiFormat() {
        assertNotNull(validator.validateMaGioiThieu("abcd1234"),
                "Mã giới thiệu chứa chữ thường phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_SPEC_006: Mã giới thiệu đúng format nhưng không tồn tại trong CSDL
    // SPEC - Kiểm tra logic nghiệp vụ
    // ============================================================
    @Test
    @Order(48)
    @DisplayName("TC_REG_SPEC_006 - Mã giới thiệu không tồn tại trong CSDL")
    void TC_REG_SPEC_006_maGioiThieuKhongTonTai() {
        String error = validator.validateMaGioiThieu("ZZZZ9999");
        assertNotNull(error, "Mã giới thiệu không tồn tại phải trả về lỗi");
        assertTrue(error.contains("không tồn tại"),
                "Lỗi phải thông báo mã không tồn tại");
    }

    // ============================================================
    // TC_REG_SPEC_007: Không tích đồng ý điều khoản
    // SPEC - Kiểm tra checkbox bắt buộc
    // ============================================================
    @Test
    @Order(49)
    @DisplayName("TC_REG_SPEC_007 - Chưa tích đồng ý điều khoản")
    void TC_REG_SPEC_007_chuaDongYDieuKhoan() {
        RegistrationForm form = createValidForm();
        form.setDongYDieuKhoan(false);
        Map<String, String> errors = validator.validate(form);
        assertTrue(errors.containsKey("dongYDieuKhoan"),
                "Chưa tích điều khoản phải có lỗi dongYDieuKhoan");
    }

    // ============================================================
    // TC_REG_SPEC_008: Tên đăng nhập đã tồn tại
    // SPEC - Kiểm tra duplicate username
    // ============================================================
    @Test
    @Order(50)
    @DisplayName("TC_REG_SPEC_008 - Tên đăng nhập đã tồn tại")
    void TC_REG_SPEC_008_tenDangNhapDaTonTai() {
        String error = validator.validateTenDangNhap("nguyen_van_a");
        assertNotNull(error, "Tên đăng nhập đã tồn tại phải trả về lỗi");
        assertTrue(error.contains("đã tồn tại"),
                "Lỗi phải thông báo tên đăng nhập đã tồn tại");
    }

    // ============================================================
    // TC_REG_EP_023: Email để trống → không hợp lệ
    // EP - Lớp KHÔNG hợp lệ: Trường bắt buộc
    // ============================================================
    @Test
    @Order(51)
    @DisplayName("TC_REG_EP_023 - Email để trống")
    void TC_REG_EP_023_emailTrong() {
        assertNotNull(validator.validateEmail(""),
                "Email trống phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_024: Số điện thoại chứa chữ cái
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(52)
    @DisplayName("TC_REG_EP_024 - SĐT chứa chữ cái")
    void TC_REG_EP_024_sdtChuaChuCai() {
        assertNotNull(validator.validateSoDienThoai("098765abcd"),
                "SĐT chứa chữ cái phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_SPEC_009: Toàn bộ form - nhiều trường lỗi cùng lúc
    // SPEC - Kiểm tra validation đồng thời
    // ============================================================
    @Test
    @Order(53)
    @DisplayName("TC_REG_SPEC_009 - Nhiều trường lỗi cùng lúc")
    void TC_REG_SPEC_009_nhieuTruongLoiCungLuc() {
        RegistrationForm form = new RegistrationForm();
        // Tất cả trường bắt buộc để trống, không tích điều khoản
        form.setDongYDieuKhoan(false);
        Map<String, String> errors = validator.validate(form);
        assertTrue(errors.size() >= 6,
                "Form rỗng phải có ít nhất 6 lỗi (các trường bắt buộc)");
    }

    // ============================================================
    // TC_REG_EP_025: Mật khẩu để trống
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(54)
    @DisplayName("TC_REG_EP_025 - Mật khẩu để trống")
    void TC_REG_EP_025_matKhauTrong() {
        assertNotNull(validator.validateMatKhau(""),
                "Mật khẩu trống phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_026: Giới tính hợp lệ - Nữ
    // EP - Lớp HỢP LỆ
    // ============================================================
    @Test
    @Order(55)
    @DisplayName("TC_REG_EP_026 - Giới tính hợp lệ")
    void TC_REG_EP_026_gioiTinhHopLe() {
        assertNull(validator.validateGioiTinh("Nam"), "Giới tính 'Nam' phải hợp lệ");
        assertNull(validator.validateGioiTinh("Nữ"), "Giới tính 'Nữ' phải hợp lệ");
        assertNull(validator.validateGioiTinh("Không muốn tiết lộ"),
                "Giới tính 'Không muốn tiết lộ' phải hợp lệ");
    }

    // ============================================================
    // TC_REG_EP_027: Giới tính giá trị không hợp lệ
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(56)
    @DisplayName("TC_REG_EP_027 - Giới tính giá trị không hợp lệ")
    void TC_REG_EP_027_gioiTinhKhongHopLe() {
        assertNotNull(validator.validateGioiTinh("Khác"),
                "Giới tính 'Khác' phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_SPEC_010: Mã giới thiệu trống (không bắt buộc) → hợp lệ
    // SPEC - Optional field
    // ============================================================
    @Test
    @Order(57)
    @DisplayName("TC_REG_SPEC_010 - Mã giới thiệu trống (không bắt buộc)")
    void TC_REG_SPEC_010_maGioiThieuTrong() {
        assertNull(validator.validateMaGioiThieu(null),
                "Mã giới thiệu trống phải hợp lệ (không bắt buộc)");
        assertNull(validator.validateMaGioiThieu(""),
                "Mã giới thiệu rỗng phải hợp lệ (không bắt buộc)");
    }

    // ============================================================
    // TC_REG_EP_028: Tên đăng nhập để trống
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(58)
    @DisplayName("TC_REG_EP_028 - Tên đăng nhập để trống")
    void TC_REG_EP_028_tenDangNhapTrong() {
        assertNotNull(validator.validateTenDangNhap(""),
                "Tên đăng nhập trống phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_EP_029: Số điện thoại để trống
    // EP - Lớp KHÔNG hợp lệ
    // ============================================================
    @Test
    @Order(59)
    @DisplayName("TC_REG_EP_029 - Số điện thoại để trống")
    void TC_REG_EP_029_soDienThoaiTrong() {
        assertNotNull(validator.validateSoDienThoai(""),
                "Số điện thoại trống phải trả về lỗi");
    }

    // ============================================================
    // TC_REG_SPEC_011: Xác nhận mật khẩu để trống
    // SPEC - Trường bắt buộc
    // ============================================================
    @Test
    @Order(60)
    @DisplayName("TC_REG_SPEC_011 - Xác nhận mật khẩu để trống")
    void TC_REG_SPEC_011_xacNhanMatKhauTrong() {
        assertNotNull(validator.validateXacNhanMatKhau("Abc@1234", ""),
                "Xác nhận mật khẩu trống phải trả về lỗi");
    }
}
