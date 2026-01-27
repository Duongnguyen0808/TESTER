# Hướng Dẫn Chạy Ứng Dụng

## 1. Tạo Database PostgreSQL

Mở PostgreSQL và chạy lệnh SQL sau:

```sql
CREATE DATABASE testtuan5;
```

## 2. Cấu hình Database (đã thiết lập trong application.properties)

- Database: testtuan5
- Username: postgres
- Password: postgres
- Port: 5432

**Lưu ý:** Nếu username/password của bạn khác, hãy sửa trong file:
`src/main/resources/application.properties`

## 3. Chạy Ứng Dụng

### Cách 1: Sử dụng Maven

```bash
./mvnw spring-boot:run
```

### Cách 2: Sử dụng IDE

- Mở file Bai5Application.java
- Click chuột phải và chọn "Run"

## 4. Truy Cập Ứng Dụng

Mở trình duyệt và truy cập:

```
http://localhost:8080
```

## Các Validation Đã Implement

### Mã Khách Hàng:

✓ Bắt buộc nhập
✓ 6-10 ký tự
✓ Chỉ chữ cái (a-z, A-Z) và số (0-9)
✓ Không được trùng lặp

### Họ và Tên:

✓ Bắt buộc nhập
✓ 5-50 ký tự
✓ Cho phép tiếng Việt có dấu

### Email:

✓ Bắt buộc nhập
✓ Định dạng email hợp lệ
✓ Không được trùng lặp

### Số điện thoại:

✓ Bắt buộc nhập
✓ 10-12 số
✓ Bắt đầu bằng số 0

### Địa chỉ:

✓ Bắt buộc nhập
✓ Tối đa 255 ký tự

### Mật khẩu:

✓ Bắt buộc nhập
✓ Tối thiểu 8 ký tự

### Xác nhận Mật khẩu:

✓ Bắt buộc nhập
✓ Phải khớp với mật khẩu

### Ngày sinh:

✓ Không bắt buộc
✓ Nếu nhập, phải đủ 18 tuổi

### Giới tính:

✓ Không bắt buộc

### Điều khoản dịch vụ:

✓ Bắt buộc phải tích chọn

## Chức Năng Buttons

- **Đăng ký**: Validate toàn bộ dữ liệu, lưu vào database, hiển thị thông báo thành công
- **Nhập lại**: Xóa toàn bộ dữ liệu trên form

## Cấu Trúc Database

Bảng `customers` sẽ tự động được tạo với các cột:

- ma_khach_hang (Primary Key)
- ho_ten
- email (Unique)
- so_dien_thoai
- dia_chi
- mat_khau
- ngay_sinh
- gioi_tinh
- dong_y_dieu_khoan
