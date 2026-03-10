# Bài 6 — Phân Tích CFG & White-Box Testing

## Bài 6.1: PhoneValidator — Kiểm tra số điện thoại Việt Nam

### 1. Quy tắc số điện thoại Việt Nam hợp lệ

- Bắt đầu bằng `0` hoặc `+84`
- Sau chuẩn hóa (bỏ +84, thêm 0): bắt đầu bằng `03x, 05x, 07x, 08x, 09x`
- Tổng 10 chữ số (sau chuẩn hóa về dạng `0xxxxxxxxx`)
- Chỉ chứa các chữ số 0-9, `+`, space (trước khi chuẩn hóa)

### 2. CFG — Control Flow Graph

```
     ┌──────────────────┐
     │  1. START / Entry │
     └────────┬─────────┘
              │
     ┌────────▼─────────┐
     │ 2. D1: phone==null│  ───── True ────► [3. return false]
     │    || isEmpty()   │
     └────────┬─────────┘
              │ False
     ┌────────▼─────────┐
     │ 4. Xóa khoảng    │
     │    trắng (clean)  │
     └────────┬─────────┘
              │
     ┌────────▼─────────┐
     │ 5. D2: chứa ký tự│  ───── True ────► [6. return false]
     │    không hợp lệ?  │       (invalid)
     └────────┬─────────┘
              │ False (valid chars)
     ┌────────▼──────────┐
     │ 7. D3: bắt đầu   │  ── True ──► [8. Chuẩn hóa +84→0] ─┐
     │    bằng "+84"?    │                                      │
     └────────┬──────────┘                                      │
              │ False                                           │
              ◄─────────────────────────────────────────────────┘
     ┌────────▼─────────┐
     │ 9. D4: bắt đầu   │  ───── False ───► [10. return false]
     │    bằng "0"?      │
     └────────┬─────────┘
              │ True
     ┌────────▼─────────┐
     │ 11. D5: length   │  ───── False ───► [12. return false]
     │     == 10?        │
     └────────┬─────────┘
              │ True
     ┌────────▼──────────┐
     │ 13. D6: prefix    │  ───── False ───► [14. return false]
     │  ∈ {03,05,07,08,09}│
     └────────┬──────────┘
              │ True
     ┌────────▼─────────┐
     │ 15. return true   │
     └──────────────────┘
```

### 3. Tính Cyclomatic Complexity (CC)

| Phương pháp | Tính toán | Kết quả |
| ----------- | --------- | ------- |
| CC = P + 1  | 6 + 1     | **7**   |

> P = 6 nút quyết định (D1, D2, D3, D4, D5, D6)

### 4. Basis Paths (7 đường)

| Path | Mô tả                                            | Input ví dụ      | Expected |
| ---- | ------------------------------------------------ | ---------------- | -------- |
| P1   | D1=T: phone null/empty                           | `null`           | false    |
| P2   | D1=F, D2=T: ký tự không hợp lệ                   | `"09abc12345"`   | false    |
| P3   | D1=F, D2=F, D3=F, D4=F: không bắt đầu 0/+84      | `"1234567890"`   | false    |
| P4   | D1=F, D2=F, D3=F, D4=T, D5=F: sai độ dài         | `"091234567"`    | false    |
| P5   | D1=F, D2=F, D3=F, D4=T, D5=T, D6=F: sai prefix   | `"0112345678"`   | false    |
| P6   | D1=F, D2=F, D3=F, D4=T, D5=T, D6=T: hợp lệ (0x)  | `"0912345678"`   | true     |
| P7   | D1=F, D2=F, D3=T, D4=T, D5=T, D6=T: hợp lệ (+84) | `"+84912345678"` | true     |

### 5. Test cases bổ sung (Boundary)

| TC  | Mô tả                  | Input            | Expected |
| --- | ---------------------- | ---------------- | -------- |
| B1  | Chuỗi rỗng ""          | `""`             | false    |
| B2  | Chỉ có khoảng trắng    | `"   "`          | false    |
| B3  | Có khoảng trắng hợp lệ | `"091 234 5678"` | true     |
| B4  | +84 nhưng sai đầu số   | `"+84112345678"` | false    |
| B5  | Quá dài 11 số          | `"09123456789"`  | false    |
| B6  | Chỉ có +84             | `"+84"`          | false    |
| B7  | Prefix 03x             | `"0312345678"`   | true     |
| B8  | Prefix 05x             | `"0512345678"`   | true     |
| B9  | Prefix 07x             | `"0712345678"`   | true     |
| B10 | Prefix 08x             | `"0812345678"`   | true     |

---

## Bài 6.2: TextBox Form — CFG luồng xử lý form

### 1. CFG Form (https://demoqa.com/text-box)

```
     ┌───────────────────┐
     │  1. START: Mở form │
     └────────┬──────────┘
              │
     ┌────────▼──────────┐
     │ 2. Nhập liệu      │
     │  (name, email,     │
     │   curAddr, permAddr)│
     └────────┬──────────┘
              │
     ┌────────▼──────────┐
     │ 3. Click Submit    │
     └────────┬──────────┘
              │
     ┌────────▼───────────┐
     │ 4. D1: Email có    │  ── Invalid ──► [5. Thêm class      ]
     │    hợp lệ không?   │                 │   'field-error'    │
     │  (empty = valid)   │                 │   Không hiển thị   │
     └────────┬───────────┘                 │   output           │
              │ Valid                        └────────┬───────────┘
     ┌────────▼──────────┐                           │
     │ 6. Hiển thị       │                           │
     │  #output section  │                           │
     │  với dữ liệu đã  │                           │
     │  nhập             │                           │
     └────────┬──────────┘                           │
              │                                      │
     ┌────────▼──────────────────────────────────────▼┐
     │                     7. END                      │
     └─────────────────────────────────────────────────┘
```

### 2. Boundary Value & Edge Cases

| Loại                | Test case                                |
| ------------------- | ---------------------------------------- |
| Empty               | Tất cả trống → submit vẫn OK             |
| Khoảng trắng        | Name chỉ có spaces → vẫn hiển thị        |
| Ký tự đặc biệt      | Name có `<>&"'` → kiểm tra hiển thị      |
| Email sai định dạng | `"abc"`, `"abc@"`, `"@domain"` → error   |
| Email hợp lệ        | `"test@example.com"` → valid             |
| Email trống         | Không nhập email → valid (no validation) |
| Dữ liệu dài         | Chuỗi rất dài → vẫn hiển thị             |
