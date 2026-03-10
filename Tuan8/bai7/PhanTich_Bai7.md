# Bài 7.1 — Phân Tích và Thiết Kế Test Suite Toàn Diện

## Hàm phân tích: `OrderProcessor.calculateTotal()`

---

## 1. CFG — Control Flow Graph (đánh số Node & Edge)

```
          ┌──────────────┐
          │  N1: START    │
          └──────┬───────┘
                 │ E1
          ┌──────▼───────┐
          │ N2: D1       │──── E2 (T) ────► ┌─────────────────────────┐
          │ items==null  │                  │ N3: throw               │
          │ || isEmpty() │                  │ "Gio hang trong"        │──E27──►┐
          └──────┬───────┘                  └─────────────────────────┘        │
                 │ E3 (F)                                                     │
          ┌──────▼────────────────────┐                                       │
          │ N4: subtotal = sum()      │                                       │
          │     discount = 0          │                                       │
          └──────┬────────────────────┘                                       │
                 │ E4                                                         │
          ┌──────▼───────┐                                                    │
          │ N5: D2       │──── E6 (F) ──────────────────────────────┐        │
          │ coupon!=null │                                          │        │
          │ && !isEmpty()│                                          │        │
          └──────┬───────┘                                          │        │
                 │ E5 (T)                                           │        │
          ┌──────▼───────┐                                          │        │
          │ N6: D3       │──── E8 (F) ────►┌──────────┐            │        │
          │ =="SALE10"   │                 │ N8: D4   │            │        │
          └──────┬───────┘                 │=="SALE20"│            │        │
                 │ E7 (T)                  └────┬─────┘            │        │
          ┌──────▼───────┐                E10(T)│  │E11(F)         │        │
          │ N7: discount │          ┌───────────┘  │               │        │
          │ = sub*0.10   │          │         ┌────▼──────────┐    │        │
          └──────┬───────┘   ┌──────▼──────┐  │N10: throw     │    │        │
                 │ E9        │N9: discount │  │"Ma khong hop" │─E28┤        │
                 │           │ = sub*0.20  │  └───────────────┘    │        │
                 │           └──────┬──────┘                       │        │
                 │                  │ E12                           │        │
                 ▼                  ▼                               │        │
          ┌──────────────────────────────────┐◄────── E6 ──────────┘        │
          │ N11: memberDiscount = 0          │                              │
          └──────┬───────────────────────────┘                              │
                 │ E13                                                      │
          ┌──────▼───────┐                                                  │
          │ N12: D5      │──── E15 (F) ───►┌──────────┐                    │
          │ =="GOLD"     │                 │ N14: D6  │                    │
          └──────┬───────┘                 │=="PLAT"  │                    │
                 │ E14 (T)                 └────┬─────┘                    │
          ┌──────▼───────┐             E17(T)│    │E18(F)                  │
          │ N13: memDisc │          ┌────────┘    │                        │
          │ =(sub-d)*0.05│   ┌──────▼──────┐      │                        │
          └──────┬───────┘   │N15: memDisc │      │                        │
                 │ E16       │=(sub-d)*0.10│      │                        │
                 │           └──────┬──────┘      │                        │
                 │                  │ E19          │                        │
                 ▼                  ▼              │                        │
          ┌──────────────────────────────────┐◄───┘ E18                    │
          │ N16: total = sub - disc - memDisc│                              │
          └──────┬───────────────────────────┘                              │
                 │ E20                                                      │
          ┌──────▼───────┐                                                  │
          │ N17: D7      │──── E22 (F: ≥500K) ──►┌───────────┐            │
          │ total<500K   │                        │           │            │
          └──────┬───────┘                        │           │            │
                 │ E21 (T)                        │           │            │
          ┌──────▼───────┐                        │           │            │
          │ N18: D8      │──── E24 (F) ──►┌──────▼──────┐    │            │
          │ !="COD"      │               │N20: total  │    │            │
          └──────┬───────┘               │ += 20_000  │    │            │
                 │ E23 (T)               └──────┬─────┘    │            │
          ┌──────▼───────┐                      │ E26      │            │
          │ N19: total   │                      │          │            │
          │ += 30_000    │                      │          │            │
          └──────┬───────┘                      │          │            │
                 │ E25                          │          │            │
                 ▼                              ▼          │            │
          ┌──────────────────────────────────────┐◄────────┘ E22        │
          │ N21: return total                    │                      │
          └──────┬───────────────────────────────┘                      │
                 │ E29                                                  │
                 ▼                                                      │
          ┌──────────────┐◄────────── E27, E28 ────────────────────────┘
          │  N22: EXIT   │
          └──────────────┘
```

### Tóm tắt CFG

| Thành phần         | Danh sách                                                          | Số lượng |
| ------------------ | ------------------------------------------------------------------ | -------- |
| **Nodes (N)**      | N1 → N22                                                           | **22**   |
| **Edges (E)**      | E1 → E29                                                           | **29**   |
| **Decision Nodes** | N2(D1), N5(D2), N6(D3), N8(D4), N12(D5), N14(D6), N17(D7), N18(D8) | **8**    |
| **Exit Nodes**     | N3 (exception), N10 (exception), N21 (return) → N22 (EXIT)         | 3→1      |

### Bảng Edge đầy đủ

| Edge | Từ  | Đến | Điều kiện            |
| ---- | --- | --- | -------------------- |
| E1   | N1  | N2  | —                    |
| E2   | N2  | N3  | D1 = True            |
| E3   | N2  | N4  | D1 = False           |
| E4   | N4  | N5  | —                    |
| E5   | N5  | N6  | D2 = True            |
| E6   | N5  | N11 | D2 = False           |
| E7   | N6  | N7  | D3 = True (SALE10)   |
| E8   | N6  | N8  | D3 = False           |
| E9   | N7  | N11 | —                    |
| E10  | N8  | N9  | D4 = True (SALE20)   |
| E11  | N8  | N10 | D4 = False (invalid) |
| E12  | N9  | N11 | —                    |
| E13  | N11 | N12 | —                    |
| E14  | N12 | N13 | D5 = True (GOLD)     |
| E15  | N12 | N14 | D5 = False           |
| E16  | N13 | N16 | —                    |
| E17  | N14 | N15 | D6 = True (PLATINUM) |
| E18  | N14 | N16 | D6 = False           |
| E19  | N15 | N16 | —                    |
| E20  | N16 | N17 | —                    |
| E21  | N17 | N18 | D7 = True (< 500K)   |
| E22  | N17 | N21 | D7 = False (≥ 500K)  |
| E23  | N18 | N19 | D8 = True (online)   |
| E24  | N18 | N20 | D8 = False (COD)     |
| E25  | N19 | N21 | —                    |
| E26  | N20 | N21 | —                    |
| E27  | N3  | N22 | exception exit       |
| E28  | N10 | N22 | exception exit       |
| E29  | N21 | N22 | normal exit          |

---

## 2. Cyclomatic Complexity (CC) — 2 cách tính

### Cách 1: CC = E − N + 2P

| Ký hiệu | Giá trị | Ý nghĩa                  |
| ------- | ------- | ------------------------ |
| E       | 29      | Số cạnh (edges)          |
| N       | 22      | Số nút (nodes)           |
| P       | 1       | Số thành phần liên thông |

> **CC = 29 − 22 + 2(1) = 9**

### Cách 2: CC = D + 1 (đếm Decision)

| Decision | Node | Điều kiện                          |
| -------- | ---- | ---------------------------------- |
| D1       | N2   | items == null \|\| items.isEmpty() |
| D2       | N5   | couponCode != null && !isEmpty()   |
| D3       | N6   | couponCode.equals("SALE10")        |
| D4       | N8   | couponCode.equals("SALE20")        |
| D5       | N12  | memberLevel.equals("GOLD")         |
| D6       | N14  | memberLevel.equals("PLATINUM")     |
| D7       | N17  | total < 500_000                    |
| D8       | N18  | !paymentMethod.equals("COD")       |

> **D = 8 → CC = 8 + 1 = 9**

### ✅ Cả 2 cách đều cho kết quả **CC = 9**

---

## 3. Đường cơ sở (Basis Paths) — 9 đường

| Path | Đường đi (Nodes)                                         | Decisions                          | Mô tả                                |
| ---- | -------------------------------------------------------- | ---------------------------------- | ------------------------------------ |
| P1   | N1→N2→N3→N22                                             | D1=T                               | items null → exception               |
| P2   | N1→N2→N4→N5→N11→N12→N13→N16→N17→N18→N19→N21→N22          | D1=F, D2=F, D5=T, D7=T, D8=T       | No coupon, GOLD, online              |
| P3   | N1→N2→N4→N5→N11→N12→N14→N16→N17→N18→N19→N21→N22          | D1=F, D2=F, D5=F, D6=F, D7=T, D8=T | No coupon, NORMAL, online            |
| P4   | N1→N2→N4→N5→N11→N12→N14→N15→N16→N17→N18→N19→N21→N22      | D1=F, D2=F, D5=F, D6=T, D7=T, D8=T | No coupon, PLATINUM, online          |
| P5   | N1→N2→N4→N5→N6→N7→N11→N12→N14→N16→N17→N18→N19→N21→N22    | D2=T, D3=T, D6=F, D7=T, D8=T       | SALE10, NORMAL, online               |
| P6   | N1→N2→N4→N5→N6→N8→N9→N11→N12→N14→N16→N17→N18→N19→N21→N22 | D2=T, D3=F, D4=T, D6=F, D7=T, D8=T | SALE20, NORMAL, online               |
| P7   | N1→N2→N4→N5→N6→N8→N10→N22                                | D2=T, D3=F, D4=F                   | Invalid coupon → exception           |
| P8   | N1→N2→N4→N5→N11→N12→N14→N16→N17→N21→N22                  | D2=F, D5=F, D6=F, D7=F             | No coupon, NORMAL, ≥500K → free ship |
| P9   | N1→N2→N4→N5→N11→N12→N14→N16→N17→N18→N20→N21→N22          | D2=F, D5=F, D6=F, D7=T, D8=F       | No coupon, NORMAL, COD               |

### Dữ liệu test cho từng Basis Path

| Path | items (giá) | couponCode | memberLevel | paymentMethod | Expected Result          |
| ---- | ----------- | ---------- | ----------- | ------------- | ------------------------ |
| P1   | null        | —          | —           | —             | IllegalArgumentException |
| P1b  | [] (rỗng)   | —          | —           | —             | IllegalArgumentException |
| P2   | [100,000]   | null       | "GOLD"      | "MOMO"        | **125,000**              |
| P3   | [100,000]   | null       | "NORMAL"    | "MOMO"        | **130,000**              |
| P4   | [100,000]   | null       | "PLATINUM"  | "MOMO"        | **120,000**              |
| P5   | [200,000]   | "SALE10"   | "NORMAL"    | "MOMO"        | **210,000**              |
| P6   | [200,000]   | "SALE20"   | "NORMAL"    | "MOMO"        | **190,000**              |
| P7   | [100,000]   | "INVALID"  | "NORMAL"    | "MOMO"        | IllegalArgumentException |
| P8   | [600,000]   | null       | "NORMAL"    | "MOMO"        | **600,000**              |
| P9   | [100,000]   | null       | "NORMAL"    | "COD"         | **120,000**              |

### Chi tiết tính toán

| Path | subtotal | discount | memberDisc | total trước ship | ship | **Kết quả** |
| ---- | -------- | -------- | ---------- | ---------------- | ---- | ----------- |
| P2   | 100,000  | 0        | 5,000      | 95,000           | +30K | **125,000** |
| P3   | 100,000  | 0        | 0          | 100,000          | +30K | **130,000** |
| P4   | 100,000  | 0        | 10,000     | 90,000           | +30K | **120,000** |
| P5   | 200,000  | 20,000   | 0          | 180,000          | +30K | **210,000** |
| P6   | 200,000  | 40,000   | 0          | 160,000          | +30K | **190,000** |
| P8   | 600,000  | 0        | 0          | 600,000          | free | **600,000** |
| P9   | 100,000  | 0        | 0          | 100,000          | +20K | **120,000** |

---

## 4. Phân tích MC/DC cho D2 && D3 (Coupon)

### 4.1 Xác định điều kiện đơn (Atomic Conditions)

Biểu thức tổng hợp để vào nhánh SALE10:

```
(couponCode != null) && (!couponCode.isEmpty()) && (couponCode.equals("SALE10"))
       A                        B                              C
```

| Ký hiệu | Điều kiện đơn               | Ý nghĩa      |
| ------- | --------------------------- | ------------ |
| A       | couponCode != null          | Không null   |
| B       | !couponCode.isEmpty()       | Không rỗng   |
| C       | couponCode.equals("SALE10") | Là mã SALE10 |

### 4.2 Bảng chân trị (Truth Table)

| Row | A   | B   | C   | A && B && C | Ghi chú                            |
| --- | --- | --- | --- | ----------- | ---------------------------------- |
| 1   | T   | T   | T   | **T**       | coupon="SALE10" → discount 10%     |
| 2   | T   | T   | F   | **F**       | coupon="SALE20" → không vào SALE10 |
| 3   | T   | F   | \*  | **F**       | coupon="" → D2 false               |
| 4   | F   | \*  | \*  | **F**       | coupon=null → D2 false             |
| 5   | T   | T   | F   | **F**       | coupon="OTHER" → D3 false          |

> _Lưu ý: Do short-circuit evaluation, khi A=F thì B,C không đánh giá; khi B=F thì C không đánh giá._

### 4.3 Cặp độc lập MC/DC (Independence Pairs)

| Điều kiện | Cặp MC/DC | Giá trị thay đổi  | Outcome thay đổi |
| --------- | --------- | ----------------- | ---------------- |
| **A**     | Row 1 ↔ 4 | A: T→F (B=T, C=T) | T → F ✓          |
| **B**     | Row 1 ↔ 3 | B: T→F (A=T, C=T) | T → F ✓          |
| **C**     | Row 1 ↔ 2 | C: T→F (A=T, B=T) | T → F ✓          |

### 4.4 Tập test tối thiểu MC/DC

> **n + 1 = 3 + 1 = 4 test cases**

Tập chọn: **{Row 1, Row 2, Row 3, Row 4}**

| TC  | couponCode | A   | B   | C   | Kết quả D2&&D3 | Dữ liệu test                                    |
| --- | ---------- | --- | --- | --- | -------------- | ----------------------------------------------- |
| MC1 | "SALE10"   | T   | T   | T   | **TRUE**       | items=[200K], coupon="SALE10", "NORMAL", "MOMO" |
| MC2 | "SALE20"   | T   | T   | F   | **FALSE**      | items=[200K], coupon="SALE20", "NORMAL", "MOMO" |
| MC3 | ""         | T   | F   | —   | **FALSE**      | items=[200K], coupon="", "NORMAL", "MOMO"       |
| MC4 | null       | F   | —   | —   | **FALSE**      | items=[200K], coupon=null, "NORMAL", "MOMO"     |

### 4.5 Expected values cho MC/DC tests

| TC  | subtotal | discount     | total trước ship | ship | **Kết quả** |
| --- | -------- | ------------ | ---------------- | ---- | ----------- |
| MC1 | 200,000  | 20,000 (10%) | 180,000          | +30K | **210,000** |
| MC2 | 200,000  | 40,000 (20%) | 160,000          | +30K | **190,000** |
| MC3 | 200,000  | 0            | 200,000          | +30K | **230,000** |
| MC4 | 200,000  | 0            | 200,000          | +30K | **230,000** |

---

## 5. Tổng hợp Test Cases

| Nhóm       | Số TC  | Mô tả                          |
| ---------- | ------ | ------------------------------ |
| Basis Path | 10     | 9 đường + 1 P1b (empty list)   |
| MC/DC      | 4      | D2&&D3 independence pairs      |
| **Tổng**   | **14** | Bao phủ toàn diện CC=9 + MC/DC |

> _Lưu ý: MC1 trùng P5, MC2 trùng P6 về input nhưng được giữ riêng để rõ ràng mục đích MC/DC._
