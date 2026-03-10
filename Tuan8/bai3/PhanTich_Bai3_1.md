# BÀI TẬP 3.1 – PHÂN TÍCH CFG VÀ TÍNH TOÁN COVERAGE

## Hàm xepLoai

```java
public static String xepLoai(int diemTB, boolean coThiLai) {
    if (diemTB < 0 || diemTB > 10) { // Điều kiện 1
        return "Diem khong hop le";
    }
    if (diemTB >= 8.5) { // Điều kiện 2
        return "Gioi";
    } else if (diemTB >= 7.0) { // Điều kiện 3
        return "Kha";
    } else if (diemTB >= 5.5) { // Điều kiện 4
        return "Trung Binh";
    } else {
        if (coThiLai) { // Điều kiện 5
            return "Thi lai";
        }
        return "Yeu - Hoc lai";
    }
}
```

---

## 1. Đồ thị luồng điều khiển (CFG)

### Danh sách Node

| Node  | Mô tả                                               |
| ----- | --------------------------------------------------- |
| Entry | Điểm bắt đầu hàm                                    |
| N1    | `if (diemTB < 0 \|\| diemTB > 10)` — Nút quyết định |
| N1a   | `return "Diem khong hop le"`                        |
| N2    | `if (diemTB >= 8.5)` — Nút quyết định               |
| N2a   | `return "Gioi"`                                     |
| N3    | `else if (diemTB >= 7.0)` — Nút quyết định          |
| N3a   | `return "Kha"`                                      |
| N4    | `else if (diemTB >= 5.5)` — Nút quyết định          |
| N4a   | `return "Trung Binh"`                               |
| N5    | `if (coThiLai)` — Nút quyết định                    |
| N5a   | `return "Thi lai"`                                  |
| N5b   | `return "Yeu - Hoc lai"`                            |
| Exit  | Điểm kết thúc hàm                                   |

### Danh sách Edge (Cạnh)

| Edge | Từ → Đến   | Điều kiện                                      |
| ---- | ---------- | ---------------------------------------------- |
| E1   | Entry → N1 | —                                              |
| E2   | N1 → N1a   | **True**: diemTB < 0 hoặc diemTB > 10          |
| E3   | N1a → Exit | —                                              |
| E4   | N1 → N2    | **False**: 0 ≤ diemTB ≤ 10                     |
| E5   | N2 → N2a   | **True**: diemTB ≥ 8.5 (tức diemTB ≥ 9 vì int) |
| E6   | N2a → Exit | —                                              |
| E7   | N2 → N3    | **False**: diemTB < 8.5 (tức diemTB ≤ 8)       |
| E8   | N3 → N3a   | **True**: diemTB ≥ 7.0 (tức diemTB ≥ 7)        |
| E9   | N3a → Exit | —                                              |
| E10  | N3 → N4    | **False**: diemTB < 7.0 (tức diemTB ≤ 6)       |
| E11  | N4 → N4a   | **True**: diemTB ≥ 5.5 (tức diemTB ≥ 6)        |
| E12  | N4a → Exit | —                                              |
| E13  | N4 → N5    | **False**: diemTB < 5.5 (tức diemTB ≤ 5)       |
| E14  | N5 → N5a   | **True**: coThiLai == true                     |
| E15  | N5a → Exit | —                                              |
| E16  | N5 → N5b   | **False**: coThiLai == false                   |
| E17  | N5b → Exit | —                                              |

### Sơ đồ CFG (mô tả văn bản)

```
Entry → N1
  N1 --[True]--> N1a (return "Diem khong hop le") → Exit
  N1 --[False]--> N2
    N2 --[True]--> N2a (return "Gioi") → Exit
    N2 --[False]--> N3
      N3 --[True]--> N3a (return "Kha") → Exit
      N3 --[False]--> N4
        N4 --[True]--> N4a (return "Trung Binh") → Exit
        N4 --[False]--> N5
          N5 --[True]--> N5a (return "Thi lai") → Exit
          N5 --[False]--> N5b (return "Yeu - Hoc lai") → Exit
```

---

## 2. Tổng số lệnh và nhánh

### Tổng số lệnh (Statements): 11

| #   | Lệnh                               |
| --- | ---------------------------------- |
| S1  | `if (diemTB < 0 \|\| diemTB > 10)` |
| S2  | `return "Diem khong hop le"`       |
| S3  | `if (diemTB >= 8.5)`               |
| S4  | `return "Gioi"`                    |
| S5  | `if (diemTB >= 7.0)`               |
| S6  | `return "Kha"`                     |
| S7  | `if (diemTB >= 5.5)`               |
| S8  | `return "Trung Binh"`              |
| S9  | `if (coThiLai)`                    |
| S10 | `return "Thi lai"`                 |
| S11 | `return "Yeu - Hoc lai"`           |

### Tổng số nút quyết định: 5 (N1, N2, N3, N4, N5)

### Tổng số nhánh (Branches): 10 (= 5 nút × 2 nhánh True/False)

| Nhánh    | Điều kiện            | Mô tả               |
| -------- | -------------------- | ------------------- |
| N1-True  | diemTB < 0 hoặc > 10 | Điểm không hợp lệ   |
| N1-False | 0 ≤ diemTB ≤ 10      | Điểm hợp lệ         |
| N2-True  | diemTB ≥ 9           | Xếp loại Giỏi       |
| N2-False | diemTB ≤ 8           | Không phải Giỏi     |
| N3-True  | diemTB ≥ 7           | Xếp loại Khá        |
| N3-False | diemTB ≤ 6           | Không phải Khá      |
| N4-True  | diemTB = 6           | Xếp loại Trung Bình |
| N4-False | diemTB ≤ 5           | Dưới Trung Bình     |
| N5-True  | coThiLai = true      | Được thi lại        |
| N5-False | coThiLai = false     | Học lại             |

---

## 3. Statement Coverage (Phủ lệnh) — 100%

### Tập test case tối thiểu: 6 TC

| TC  | diemTB | coThiLai | Đường đi trên CFG                        | Kết quả             | Lệnh phủ                |
| --- | ------ | -------- | ---------------------------------------- | ------------------- | ----------------------- |
| TC1 | -1     | false    | Entry→N1(T)→Exit                         | "Diem khong hop le" | S1, S2                  |
| TC2 | 9      | false    | Entry→N1(F)→N2(T)→Exit                   | "Gioi"              | S1, S3, S4              |
| TC3 | 7      | false    | Entry→N1(F)→N2(F)→N3(T)→Exit             | "Kha"               | S1, S3, S5, S6          |
| TC4 | 6      | false    | Entry→N1(F)→N2(F)→N3(F)→N4(T)→Exit       | "Trung Binh"        | S1, S3, S5, S7, S8      |
| TC5 | 3      | true     | Entry→N1(F)→N2(F)→N3(F)→N4(F)→N5(T)→Exit | "Thi lai"           | S1, S3, S5, S7, S9, S10 |
| TC6 | 3      | false    | Entry→N1(F)→N2(F)→N3(F)→N4(F)→N5(F)→Exit | "Yeu - Hoc lai"     | S1, S3, S5, S7, S9, S11 |

**Statement Coverage = 11/11 = 100%**

---

## 4. Branch Coverage (Phủ nhánh) — 100%

### Tập test case tối thiểu: 6 TC (cùng tập TC như Statement Coverage)

### Bảng theo dõi: Mỗi TC phủ nhánh nào

| TC  | N1-T | N1-F | N2-T | N2-F | N3-T | N3-F | N4-T | N4-F | N5-T | N5-F |
| --- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| TC1 | ✓    |      |      |      |      |      |      |      |      |      |
| TC2 |      | ✓    | ✓    |      |      |      |      |      |      |      |
| TC3 |      | ✓    |      | ✓    | ✓    |      |      |      |      |      |
| TC4 |      | ✓    |      | ✓    |      | ✓    | ✓    |      |      |      |
| TC5 |      | ✓    |      | ✓    |      | ✓    |      | ✓    | ✓    |      |
| TC6 |      | ✓    |      | ✓    |      | ✓    |      | ✓    |      | ✓    |

**Branch Coverage = 10/10 = 100%**

---

## 5. Tổng kết

- **Tổng node**: 13 (Entry + 5 decision + 6 action/return + Exit)
- **Tổng edge**: 17
- **Tổng lệnh**: 11
- **Tổng nhánh**: 10
- **Số TC tối thiểu cho 100% Statement Coverage**: 6
- **Số TC tối thiểu cho 100% Branch Coverage**: 6
- **Code TestNG**: Xem file `XepLoaiTest.java`
