# BÀI TẬP 3.2 – STATEMENT & BRANCH COVERAGE THỰC TẾ

## Hàm tinhTienNuoc

```java
public static double tinhTienNuoc(int soM3, String loaiKhachHang) {
    if (soM3 <= 0) return 0;                          // N1
    double don_gia;
    if (loaiKhachHang.equals("ho_ngheo")) {            // N2
        don_gia = 5000;
    } else if (loaiKhachHang.equals("dan_cu")) {       // N3
        if (soM3 <= 10) {                              // N4
            don_gia = 7500;
        } else if (soM3 <= 20) {                       // N5
            don_gia = 9900;
        } else {
            don_gia = 11400;
        }
    } else {                                           // kinh_doanh
        don_gia = 22000;
    }
    return soM3 * don_gia;
}
```

---

## 1. Đồ thị luồng điều khiển (CFG)

### Danh sách Node

| Node  | Mô tả                                                       |
| ----- | ----------------------------------------------------------- |
| Entry | Điểm bắt đầu hàm                                            |
| N1    | `if (soM3 <= 0)` — Nút quyết định                           |
| N1a   | `return 0`                                                  |
| N2    | `if (loaiKhachHang.equals("ho_ngheo"))` — Nút quyết định    |
| N2a   | `don_gia = 5000`                                            |
| N3    | `else if (loaiKhachHang.equals("dan_cu"))` — Nút quyết định |
| N3a   | `don_gia = 22000` (kinh doanh)                              |
| N4    | `if (soM3 <= 10)` — Nút quyết định                          |
| N4a   | `don_gia = 7500`                                            |
| N5    | `else if (soM3 <= 20)` — Nút quyết định                     |
| N5a   | `don_gia = 9900`                                            |
| N5b   | `don_gia = 11400`                                           |
| N6    | `return soM3 * don_gia`                                     |
| Exit  | Điểm kết thúc hàm                                           |

**Tổng node: 14** (Entry + 5 decision + 7 action + Exit)

### Danh sách Edge (Cạnh)

| Edge | Từ → Đến   | Điều kiện                            |
| ---- | ---------- | ------------------------------------ |
| E1   | Entry → N1 | —                                    |
| E2   | N1 → N1a   | **True**: soM3 ≤ 0                   |
| E3   | N1a → Exit | — (return 0)                         |
| E4   | N1 → N2    | **False**: soM3 > 0                  |
| E5   | N2 → N2a   | **True**: loaiKhachHang = "ho_ngheo" |
| E6   | N2a → N6   | —                                    |
| E7   | N2 → N3    | **False**: không phải "ho_ngheo"     |
| E8   | N3 → N4    | **True**: loaiKhachHang = "dan_cu"   |
| E9   | N3 → N3a   | **False**: loại khác (kinh_doanh)    |
| E10  | N3a → N6   | —                                    |
| E11  | N4 → N4a   | **True**: soM3 ≤ 10                  |
| E12  | N4a → N6   | —                                    |
| E13  | N4 → N5    | **False**: soM3 > 10                 |
| E14  | N5 → N5a   | **True**: soM3 ≤ 20                  |
| E15  | N5a → N6   | —                                    |
| E16  | N5 → N5b   | **False**: soM3 > 20                 |
| E17  | N5b → N6   | —                                    |
| E18  | N6 → Exit  | — (return soM3 \* don_gia)           |

**Tổng edge: 18**

### Sơ đồ CFG (mô tả văn bản)

```
Entry → N1
  N1 --[True: soM3≤0]--> N1a (return 0) → Exit
  N1 --[False: soM3>0]--> N2
    N2 --[True: "ho_ngheo"]--> N2a (don_gia=5000) → N6
    N2 --[False]--> N3
      N3 --[True: "dan_cu"]--> N4
        N4 --[True: soM3≤10]--> N4a (don_gia=7500) → N6
        N4 --[False: soM3>10]--> N5
          N5 --[True: soM3≤20]--> N5a (don_gia=9900) → N6
          N5 --[False: soM3>20]--> N5b (don_gia=11400) → N6
      N3 --[False: khác]--> N3a (don_gia=22000) → N6
    N6: return soM3 * don_gia → Exit
```

---

## 2. Tổng số lệnh và tổng số nhánh

### Tổng số lệnh (Statements): 12

| #   | Lệnh                                    |
| --- | --------------------------------------- |
| S1  | `if (soM3 <= 0)`                        |
| S2  | `return 0`                              |
| S3  | `if (loaiKhachHang.equals("ho_ngheo"))` |
| S4  | `don_gia = 5000`                        |
| S5  | `if (loaiKhachHang.equals("dan_cu"))`   |
| S6  | `if (soM3 <= 10)`                       |
| S7  | `don_gia = 7500`                        |
| S8  | `if (soM3 <= 20)`                       |
| S9  | `don_gia = 9900`                        |
| S10 | `don_gia = 11400`                       |
| S11 | `don_gia = 22000`                       |
| S12 | `return soM3 * don_gia`                 |

### Tổng số nút quyết định: 5 (N1, N2, N3, N4, N5)

### Tổng số nhánh (Branches): 10 (= 5 nút × 2 nhánh True/False)

| Nhánh    | Điều kiện                  | Mô tả                        |
| -------- | -------------------------- | ---------------------------- |
| N1-True  | soM3 ≤ 0                   | Số m³ không hợp lệ, trả về 0 |
| N1-False | soM3 > 0                   | Số m³ hợp lệ, tiếp tục tính  |
| N2-True  | loaiKhachHang = "ho_ngheo" | Hộ nghèo, đơn giá 5,000      |
| N2-False | loaiKhachHang ≠ "ho_ngheo" | Không phải hộ nghèo          |
| N3-True  | loaiKhachHang = "dan_cu"   | Dân cư, kiểm tra mức m³      |
| N3-False | loaiKhachHang ≠ "dan_cu"   | Kinh doanh, đơn giá 22,000   |
| N4-True  | soM3 ≤ 10                  | Dân cư mức 1, đơn giá 7,500  |
| N4-False | soM3 > 10                  | Dân cư mức cao hơn           |
| N5-True  | soM3 ≤ 20                  | Dân cư mức 2, đơn giá 9,900  |
| N5-False | soM3 > 20                  | Dân cư mức 3, đơn giá 11,400 |

---

## 3. Thiết kế Test Suite đạt 100% Branch Coverage (ít TC nhất)

### Tập test case tối thiểu: 6 TC

| TC  | soM3 | loaiKhachHang | Đường đi trên CFG                                         | Kết quả mong đợi |
| --- | ---- | ------------- | --------------------------------------------------------- | ---------------- |
| TC1 | 0    | "ho_ngheo"    | Entry→N1(T)→return 0→Exit                                 | 0                |
| TC2 | 5    | "ho_ngheo"    | Entry→N1(F)→N2(T)→don_gia=5000→N6→Exit                    | 25,000           |
| TC3 | 5    | "dan_cu"      | Entry→N1(F)→N2(F)→N3(T)→N4(T)→don_gia=7500→N6→Exit        | 37,500           |
| TC4 | 15   | "dan_cu"      | Entry→N1(F)→N2(F)→N3(T)→N4(F)→N5(T)→don_gia=9900→N6→Exit  | 148,500          |
| TC5 | 25   | "dan_cu"      | Entry→N1(F)→N2(F)→N3(T)→N4(F)→N5(F)→don_gia=11400→N6→Exit | 285,000          |
| TC6 | 5    | "kinh_doanh"  | Entry→N1(F)→N2(F)→N3(F)→don_gia=22000→N6→Exit             | 110,000          |

---

## 4. Bảng theo dõi: Mỗi TC phủ được những nhánh nào

| TC  | N1-T | N1-F | N2-T | N2-F | N3-T | N3-F | N4-T | N4-F | N5-T | N5-F |
| --- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| TC1 | ✓    |      |      |      |      |      |      |      |      |      |
| TC2 |      | ✓    | ✓    |      |      |      |      |      |      |      |
| TC3 |      | ✓    |      | ✓    | ✓    |      | ✓    |      |      |      |
| TC4 |      | ✓    |      | ✓    | ✓    |      |      | ✓    | ✓    |      |
| TC5 |      | ✓    |      | ✓    | ✓    |      |      | ✓    |      | ✓    |
| TC6 |      | ✓    |      | ✓    |      | ✓    |      |      |      |      |

### Kiểm tra:

- **N1-True**: TC1 ✓
- **N1-False**: TC2, TC3, TC4, TC5, TC6 ✓
- **N2-True**: TC2 ✓
- **N2-False**: TC3, TC4, TC5, TC6 ✓
- **N3-True**: TC3, TC4, TC5 ✓
- **N3-False**: TC6 ✓
- **N4-True**: TC3 ✓
- **N4-False**: TC4, TC5 ✓
- **N5-True**: TC4 ✓
- **N5-False**: TC5 ✓

**Branch Coverage = 10/10 = 100%** ✓

---

## 5. Tổng kết

| Chỉ số                                   | Giá trị |
| ---------------------------------------- | ------- |
| Tổng node                                | 14      |
| Tổng edge                                | 18      |
| Tổng lệnh (statements)                   | 12      |
| Tổng nhánh (branches)                    | 10      |
| Số TC tối thiểu cho 100% Branch Coverage | 6       |

**Code TestNG**: Xem file `TinhTienNuocTest.java`
