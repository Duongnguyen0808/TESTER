# BÀI TẬP 5.1 – PHÂN TÍCH CONDITION VÀ MC/DC

## Hàm duDieuKienVay

```java
public static boolean duDieuKienVay(int tuoi, double thuNhap,
        boolean coTaiSanBaoLanh, int dienTinDung) {
    boolean dieuKienCoBan = (tuoi >= 22) && (thuNhap >= 10_000_000);
    boolean dieuKienBaoDam = coTaiSanBaoLanh || (dienTinDung >= 700);
    return dieuKienCoBan && dieuKienBaoDam;
}
```

---

## 1. Xác định tất cả điều kiện đơn (Atomic Conditions)

| Ký hiệu | Điều kiện đơn           | Mô tả               |
| ------- | ----------------------- | ------------------- |
| **A**   | `tuoi >= 22`            | Tuổi từ 22 trở lên  |
| **B**   | `thuNhap >= 10_000_000` | Thu nhập ≥ 10 triệu |
| **C**   | `coTaiSanBaoLanh`       | Có tài sản bảo lãnh |
| **D**   | `dienTinDung >= 700`    | Điểm tín dụng ≥ 700 |

### Biểu thức tổng hợp

```
dieuKienCoBan  = A && B
dieuKienBaoDam = C || D
Kết quả        = (A && B) && (C || D)
```

**Tổng số điều kiện đơn: 4** → Bảng chân trị có **2⁴ = 16 dòng**

---

## 2. Bảng chân trị đầy đủ (Truth Table)

| Row | A (tuoi≥22) | B (thuNhap≥10M) | C (coTaiSan) | D (dienTD≥700) | A&&B | C\|\|D | Kết quả |
| --- | :---------: | :-------------: | :----------: | :------------: | :--: | :----: | :-----: |
| 1   |      T      |        T        |      T       |       T        |  T   |   T    |  **T**  |
| 2   |      T      |        T        |      T       |       F        |  T   |   T    |  **T**  |
| 3   |      T      |        T        |      F       |       T        |  T   |   T    |  **T**  |
| 4   |      T      |        T        |      F       |       F        |  T   |   F    |  **F**  |
| 5   |      T      |        F        |      T       |       T        |  F   |   T    |  **F**  |
| 6   |      T      |        F        |      T       |       F        |  F   |   T    |  **F**  |
| 7   |      T      |        F        |      F       |       T        |  F   |   T    |  **F**  |
| 8   |      T      |        F        |      F       |       F        |  F   |   F    |  **F**  |
| 9   |      F      |        T        |      T       |       T        |  F   |   T    |  **F**  |
| 10  |      F      |        T        |      T       |       F        |  F   |   T    |  **F**  |
| 11  |      F      |        T        |      F       |       T        |  F   |   T    |  **F**  |
| 12  |      F      |        T        |      F       |       F        |  F   |   F    |  **F**  |
| 13  |      F      |        F        |      T       |       T        |  F   |   T    |  **F**  |
| 14  |      F      |        F        |      T       |       F        |  F   |   T    |  **F**  |
| 15  |      F      |        F        |      F       |       T        |  F   |   T    |  **F**  |
| 16  |      F      |        F        |      F       |       F        |  F   |   F    |  **F**  |

---

## 3. Condition Coverage

Mỗi điều kiện đơn phải có giá trị True và False ít nhất 1 lần.

**Tập TC tối thiểu cho Condition Coverage: 2 test case**

| TC     | A   | B   | C   | D   | Kết quả |
| ------ | --- | --- | --- | --- | ------- |
| Row 1  | T   | T   | T   | T   | T       |
| Row 16 | F   | F   | F   | F   | F       |

Kiểm tra:

- A: T (Row 1), F (Row 16) ✓
- B: T (Row 1), F (Row 16) ✓
- C: T (Row 1), F (Row 16) ✓
- D: T (Row 1), F (Row 16) ✓

> ⚠️ **LƯU Ý**: Condition Coverage CHƯA CHẮC đạt Branch Coverage! (Row 1 → True, Row 16 → False, nhưng không kiểm tra từng nhánh quyết định riêng)

---

## 4. Phân tích MC/DC (Modified Condition/Decision Coverage)

### Nguyên tắc

Với mỗi điều kiện đơn Ci, tìm 2 dòng trong bảng chân trị thỏa mãn:

1. Giá trị của Ci **KHÁC NHAU** (một là T, một là F)
2. Giá trị của **TẤT CẢ** điều kiện khác phải **GIỐNG NHAU**
3. **Kết quả** (output) của 2 dòng đó **KHÁC NHAU**

### Phân tích từng điều kiện

#### Điều kiện A (tuoi >= 22) — độc lập

Tìm 2 dòng: A khác nhau, B & C & D giống nhau, Output khác nhau:

- **Row 2** (T, T, T, F → **T**) vs **Row 10** (F, T, T, F → **F**)
- B=T, C=T, D=F giống nhau ✓ | A: T→F, Output: T→F ✓

> **A có MC/DC qua cặp (Row 2, Row 10)**

#### Điều kiện B (thuNhap >= 10_000_000) — độc lập

Tìm 2 dòng: B khác nhau, A & C & D giống nhau, Output khác nhau:

- **Row 2** (T, T, T, F → **T**) vs **Row 6** (T, F, T, F → **F**)
- A=T, C=T, D=F giống nhau ✓ | B: T→F, Output: T→F ✓

> **B có MC/DC qua cặp (Row 2, Row 6)**

#### Điều kiện C (coTaiSanBaoLanh) — độc lập

Tìm 2 dòng: C khác nhau, A & B & D giống nhau, Output khác nhau:

- **Row 2** (T, T, T, F → **T**) vs **Row 4** (T, T, F, F → **F**)
- A=T, B=T, D=F giống nhau ✓ | C: T→F, Output: T→F ✓

> **C có MC/DC qua cặp (Row 2, Row 4)**

#### Điều kiện D (dienTinDung >= 700) — độc lập

Tìm 2 dòng: D khác nhau, A & B & C giống nhau, Output khác nhau:

- **Row 3** (T, T, F, T → **T**) vs **Row 4** (T, T, F, F → **F**)
- A=T, B=T, C=F giống nhau ✓ | D: T→F, Output: T→F ✓

> **D có MC/DC qua cặp (Row 3, Row 4)**

---

## 5. Tập test case tối thiểu cho MC/DC

Tổng hợp các Row cần dùng: {2, 3, 4, 6, 10}

**Số TC tối thiểu: 5** (= n + 1 = 4 + 1, tối ưu nhất!)

| TC  | Row | tuoi     | thuNhap           | coTaiSan | dienTD     | Kết quả   | Phục vụ MC/DC       |
| --- | --- | -------- | ----------------- | -------- | ---------- | --------- | ------------------- |
| TC1 | 2   | 25 (≥22) | 15,000,000 (≥10M) | true     | 600 (<700) | **true**  | A, B, C (base True) |
| TC2 | 3   | 25 (≥22) | 15,000,000 (≥10M) | false    | 750 (≥700) | **true**  | D (base True)       |
| TC3 | 4   | 25 (≥22) | 15,000,000 (≥10M) | false    | 600 (<700) | **false** | C, D (pair False)   |
| TC4 | 6   | 25 (≥22) | 5,000,000 (<10M)  | true     | 600 (<700) | **false** | B (pair False)      |
| TC5 | 10  | 20 (<22) | 15,000,000 (≥10M) | true     | 600 (<700) | **false** | A (pair False)      |

### Bảng theo dõi: Mỗi TC phủ MC/DC cho điều kiện nào

| Cặp MC/DC       | Điều kiện                 | TC base (True) | TC pair (False) | ✓   |
| --------------- | ------------------------- | -------------- | --------------- | --- |
| (Row 2, Row 10) | **A** – tuoi ≥ 22         | TC1 (tuoi=25)  | TC5 (tuoi=20)   | ✓   |
| (Row 2, Row 6)  | **B** – thuNhap ≥ 10M     | TC1 (thu=15M)  | TC4 (thu=5M)    | ✓   |
| (Row 2, Row 4)  | **C** – coTaiSanBaoLanh   | TC1 (true)     | TC3 (false)     | ✓   |
| (Row 3, Row 4)  | **D** – dienTinDung ≥ 700 | TC2 (dien=750) | TC3 (dien=600)  | ✓   |

### Xác nhận cũng đạt Condition Coverage

| Điều kiện | True tại           | False tại          | ✓   |
| --------- | ------------------ | ------------------ | --- |
| A         | TC1, TC2, TC3, TC4 | TC5                | ✓   |
| B         | TC1, TC2, TC3, TC5 | TC4                | ✓   |
| C         | TC1, TC4, TC5      | TC2, TC3           | ✓   |
| D         | TC2                | TC1, TC3, TC4, TC5 | ✓   |

---

## 6. Tổng kết

| Chỉ số                    | Giá trị                 |
| ------------------------- | ----------------------- |
| Số điều kiện đơn (n)      | 4                       |
| Tổng dòng bảng chân trị   | 16                      |
| TC cho Condition Coverage | 2 (tối thiểu)           |
| TC cho MC/DC              | **5** (= n + 1, tối ưu) |
| Cặp MC/DC cho A           | Row 2 vs Row 10         |
| Cặp MC/DC cho B           | Row 2 vs Row 6          |
| Cặp MC/DC cho C           | Row 2 vs Row 4          |
| Cặp MC/DC cho D           | Row 3 vs Row 4          |
