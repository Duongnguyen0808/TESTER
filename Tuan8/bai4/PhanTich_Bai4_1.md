# BÀI TẬP 4.1 – TÍNH CC VÀ BASIS PATH

## Hàm tinhPhiShip

```java
public static double tinhPhiShip(double trongLuong, String vung, boolean laMember) {
    if (trongLuong <= 0) { // D1
        throw new IllegalArgumentException("Trong luong phai > 0");
    }
    double phi = 0;
    if (vung.equals("noi_thanh")) { // D2
        phi = 15000;
        if (trongLuong > 5) { // D3
            phi += (trongLuong - 5) * 2000;
        }
    } else if (vung.equals("ngoai_thanh")) { // D4
        phi = 25000;
        if (trongLuong > 3) { // D5
            phi += (trongLuong - 3) * 3000;
        }
    } else { // tinh khac
        phi = 50000;
        if (trongLuong > 2) { // D6
            phi += (trongLuong - 2) * 5000;
        }
    }
    if (laMember) { // D7
        phi = phi * 0.9; // giam 10%
    }
    return phi;
}
```

---

## 1. Đếm số lượng Decision (D)

| Decision | Vị trí trong code | Điều kiện                    |
| -------- | ----------------- | ---------------------------- |
| D1       | Dòng 2            | `trongLuong <= 0`            |
| D2       | Dòng 5            | `vung.equals("noi_thanh")`   |
| D3       | Dòng 7            | `trongLuong > 5`             |
| D4       | Dòng 10           | `vung.equals("ngoai_thanh")` |
| D5       | Dòng 12           | `trongLuong > 3`             |
| D6       | Dòng 16           | `trongLuong > 2`             |
| D7       | Dòng 19           | `laMember`                   |

**Tổng số Decision: D = 7**

### Tính CC theo công thức nhanh

> **CC = D + 1 = 7 + 1 = 8**

---

## 2. Đồ thị CFG đầy đủ

### Danh sách Node (14 node)

| Node | Mô tả                                                         |
| ---- | ------------------------------------------------------------- |
| N1   | `if (trongLuong <= 0)` — Decision D1                          |
| N2   | `throw new IllegalArgumentException(...)`                     |
| N3   | `double phi = 0; if (vung.equals("noi_thanh"))` — Decision D2 |
| N4   | `phi = 15000; if (trongLuong > 5)` — Decision D3              |
| N5   | `phi += (trongLuong - 5) * 2000`                              |
| N6   | `if (vung.equals("ngoai_thanh"))` — Decision D4               |
| N7   | `phi = 25000; if (trongLuong > 3)` — Decision D5              |
| N8   | `phi += (trongLuong - 3) * 3000`                              |
| N9   | `phi = 50000; if (trongLuong > 2)` — Decision D6              |
| N10  | `phi += (trongLuong - 2) * 5000`                              |
| N11  | `if (laMember)` — Decision D7                                 |
| N12  | `phi = phi * 0.9`                                             |
| N13  | `return phi`                                                  |
| Exit | Kết thúc hàm                                                  |

### Danh sách Edge (20 edge)

| Edge | Từ → Đến   | Điều kiện                         |
| ---- | ---------- | --------------------------------- |
| E1   | N1 → N2    | **D1-True**: trongLuong ≤ 0       |
| E2   | N2 → Exit  | throw exception                   |
| E3   | N1 → N3    | **D1-False**: trongLuong > 0      |
| E4   | N3 → N4    | **D2-True**: vung = "noi_thanh"   |
| E5   | N3 → N6    | **D2-False**: vung ≠ "noi_thanh"  |
| E6   | N4 → N5    | **D3-True**: trongLuong > 5       |
| E7   | N4 → N11   | **D3-False**: trongLuong ≤ 5      |
| E8   | N5 → N11   | — (merge)                         |
| E9   | N6 → N7    | **D4-True**: vung = "ngoai_thanh" |
| E10  | N6 → N9    | **D4-False**: tỉnh khác           |
| E11  | N7 → N8    | **D5-True**: trongLuong > 3       |
| E12  | N7 → N11   | **D5-False**: trongLuong ≤ 3      |
| E13  | N8 → N11   | — (merge)                         |
| E14  | N9 → N10   | **D6-True**: trongLuong > 2       |
| E15  | N9 → N11   | **D6-False**: trongLuong ≤ 2      |
| E16  | N10 → N11  | — (merge)                         |
| E17  | N11 → N12  | **D7-True**: laMember = true      |
| E18  | N11 → N13  | **D7-False**: laMember = false    |
| E19  | N12 → N13  | — (merge)                         |
| E20  | N13 → Exit | return phi                        |

### Sơ đồ CFG (mô tả văn bản)

```
N1 [D1: trongLuong <= 0?]
 ├──[True]──→ N2 (throw Exception) → Exit
 └──[False]──→ N3 [D2: noi_thanh?]
                ├──[True]──→ N4 [D3: trongLuong > 5?]
                │             ├──[True]──→ N5 (phi += phụ thu) ──→ N11
                │             └──[False]─────────────────────────→ N11
                └──[False]──→ N6 [D4: ngoai_thanh?]
                              ├──[True]──→ N7 [D5: trongLuong > 3?]
                              │             ├──[True]──→ N8 (phi += phụ thu) ──→ N11
                              │             └──[False]─────────────────────────→ N11
                              └──[False]──→ N9 [D6: trongLuong > 2?]
                                            ├──[True]──→ N10 (phi += phụ thu) ──→ N11
                                            └──[False]─────────────────────────→ N11

N11 [D7: laMember?]
 ├──[True]──→ N12 (phi *= 0.9) ──→ N13 (return phi) → Exit
 └──[False]─────────────────────→ N13 (return phi) → Exit
```

---

## 3. Kiểm tra lại CC = E - N + 2P

| Thông số                     | Giá trị |
| ---------------------------- | ------- |
| E (số edge)                  | 20      |
| N (số node)                  | 14      |
| P (số thành phần liên thông) | 1       |

> **CC = E - N + 2P = 20 - 14 + 2(1) = 8** ✓

Khớp với công thức nhanh: **CC = D + 1 = 7 + 1 = 8** ✓

---

## 4. Thiết kế tập Basis Path (8 đường cơ sở)

### Bảng 8 đường cơ sở và dữ liệu test

| Path       | Đường đi trên CFG                           | Decisions                    | Dữ liệu test                                     | Kết quả mong đợi |
| ---------- | ------------------------------------------- | ---------------------------- | ------------------------------------------------ | ---------------- |
| **Path 1** | N1(T)→N2→Exit                               | D1=T                         | trongLuong=-1, vung="noi_thanh", laMember=false  | **Exception**    |
| **Path 2** | N1(F)→N3(T)→N4(F)→N11(F)→N13→Exit           | D1=F, D2=T, D3=F, D7=F       | trongLuong=3, vung="noi_thanh", laMember=false   | **15,000**       |
| **Path 3** | N1(F)→N3(T)→N4(T)→N5→N11(F)→N13→Exit        | D1=F, D2=T, D3=T, D7=F       | trongLuong=8, vung="noi_thanh", laMember=false   | **21,000**       |
| **Path 4** | N1(F)→N3(F)→N6(T)→N7(F)→N11(F)→N13→Exit     | D1=F, D2=F, D4=T, D5=F, D7=F | trongLuong=2, vung="ngoai_thanh", laMember=false | **25,000**       |
| **Path 5** | N1(F)→N3(F)→N6(T)→N7(T)→N8→N11(F)→N13→Exit  | D1=F, D2=F, D4=T, D5=T, D7=F | trongLuong=5, vung="ngoai_thanh", laMember=false | **31,000**       |
| **Path 6** | N1(F)→N3(F)→N6(F)→N9(F)→N11(F)→N13→Exit     | D1=F, D2=F, D4=F, D6=F, D7=F | trongLuong=1, vung="tinh_khac", laMember=false   | **50,000**       |
| **Path 7** | N1(F)→N3(F)→N6(F)→N9(T)→N10→N11(F)→N13→Exit | D1=F, D2=F, D4=F, D6=T, D7=F | trongLuong=4, vung="tinh_khac", laMember=false   | **60,000**       |
| **Path 8** | N1(F)→N3(F)→N6(F)→N9(F)→N11(T)→N12→N13→Exit | D1=F, D2=F, D4=F, D6=F, D7=T | trongLuong=1, vung="tinh_khac", laMember=true    | **45,000**       |

### Chi tiết tính toán kết quả mong đợi

| Path   | Phép tính                                     | Kết quả                  |
| ------ | --------------------------------------------- | ------------------------ |
| Path 1 | trongLuong = -1 → throw Exception             | IllegalArgumentException |
| Path 2 | phi = 15000 (nội thành, ≤5kg, không member)   | 15,000                   |
| Path 3 | phi = 15000 + (8-5)×2000 = 15000 + 6000       | 21,000                   |
| Path 4 | phi = 25000 (ngoại thành, ≤3kg, không member) | 25,000                   |
| Path 5 | phi = 25000 + (5-3)×3000 = 25000 + 6000       | 31,000                   |
| Path 6 | phi = 50000 (tỉnh khác, ≤2kg, không member)   | 50,000                   |
| Path 7 | phi = 50000 + (4-2)×5000 = 50000 + 10000      | 60,000                   |
| Path 8 | phi = 50000 × 0.9 (tỉnh khác, ≤2kg, member)   | 45,000                   |

---

## 5. Tổng kết

| Chỉ số                     | Giá trị               |
| -------------------------- | --------------------- |
| Số Decision (D)            | 7                     |
| Cyclomatic Complexity (CC) | **8**                 |
| Tổng Node (N)              | 14                    |
| Tổng Edge (E)              | 20                    |
| CC = E - N + 2P            | 20 - 14 + 2 = **8** ✓ |
| Số Basis Path              | 8                     |
| Số Test Case cần viết      | 8                     |

**Code TestNG**: Xem file `PhiShipBasisPathTest.java`
