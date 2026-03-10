package com.example;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Feature("Xử lý đơn hàng — OrderProcessor")
public class OrderProcessorTest {

    private OrderProcessor processor;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        processor = new OrderProcessor();
    }

    /** Helper: tạo danh sách Item từ danh sách giá */
    private List<Item> items(double... prices) {
        return Arrays.stream(prices)
                .mapToObj(p -> new Item("SP", p))
                .collect(Collectors.toList());
    }

    // ═══════════════════════════════════════════════════════════
    //  BASIS PATH TESTS (CC = 9, 10 test cases)
    // ═══════════════════════════════════════════════════════════

    @Test(description = "P1: D1=T — items=null → exception")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Path 1: items null, D1=True → IllegalArgumentException 'Gio hang trong'")
    public void testP1_ItemsNull() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                processor.calculateTotal(null, null, "NORMAL", "MOMO"));
    }

    @Test(description = "P1b: D1=T — items rỗng → exception")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Path 1b: items empty list, D1=True → IllegalArgumentException")
    public void testP1b_ItemsEmpty() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                processor.calculateTotal(Collections.emptyList(), null, "NORMAL", "MOMO"));
    }

    @Test(description = "P2: D2=F, D5=T(GOLD), D7=T, D8=T(online) → 125,000")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 2: No coupon, GOLD member, total<500K, online → ship +30K")
    public void testP2_NoCoupon_Gold_Online() {
        // subtotal=100K, disc=0, memDisc=5K, total=95K, +30K = 125K
        double result = processor.calculateTotal(items(100_000), null, "GOLD", "MOMO");
        Assert.assertEquals(result, 125_000.0);
    }

    @Test(description = "P3: D2=F, D5=F, D6=F(NORMAL), D7=T, D8=T → 130,000")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 3: No coupon, NORMAL member, total<500K, online → ship +30K")
    public void testP3_NoCoupon_Normal_Online() {
        // subtotal=100K, disc=0, memDisc=0, total=100K, +30K = 130K
        double result = processor.calculateTotal(items(100_000), null, "NORMAL", "MOMO");
        Assert.assertEquals(result, 130_000.0);
    }

    @Test(description = "P4: D2=F, D6=T(PLATINUM), D7=T, D8=T → 120,000")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 4: No coupon, PLATINUM member, total<500K, online → ship +30K")
    public void testP4_NoCoupon_Platinum_Online() {
        // subtotal=100K, disc=0, memDisc=10K, total=90K, +30K = 120K
        double result = processor.calculateTotal(items(100_000), null, "PLATINUM", "MOMO");
        Assert.assertEquals(result, 120_000.0);
    }

    @Test(description = "P5: D2=T, D3=T(SALE10), D6=F, D7=T, D8=T → 210,000")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 5: SALE10 coupon, NORMAL member, online → discount 10% + ship 30K")
    public void testP5_Sale10_Normal_Online() {
        // subtotal=200K, disc=20K, memDisc=0, total=180K, +30K = 210K
        double result = processor.calculateTotal(items(200_000), "SALE10", "NORMAL", "MOMO");
        Assert.assertEquals(result, 210_000.0);
    }

    @Test(description = "P6: D2=T, D3=F, D4=T(SALE20), D7=T, D8=T → 190,000")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 6: SALE20 coupon, NORMAL member, online → discount 20% + ship 30K")
    public void testP6_Sale20_Normal_Online() {
        // subtotal=200K, disc=40K, memDisc=0, total=160K, +30K = 190K
        double result = processor.calculateTotal(items(200_000), "SALE20", "NORMAL", "MOMO");
        Assert.assertEquals(result, 190_000.0);
    }

    @Test(description = "P7: D2=T, D3=F, D4=F — coupon invalid → exception")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Path 7: Invalid coupon code → IllegalArgumentException 'Ma giam gia khong hop le'")
    public void testP7_InvalidCoupon() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                processor.calculateTotal(items(100_000), "INVALID", "NORMAL", "MOMO"));
    }

    @Test(description = "P8: D2=F, D7=F(≥500K) → free ship = 600,000")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 8: No coupon, NORMAL, total≥500K → miễn phí ship")
    public void testP8_FreeShipping() {
        // subtotal=600K, disc=0, memDisc=0, total=600K >= 500K → no ship
        double result = processor.calculateTotal(items(600_000), null, "NORMAL", "MOMO");
        Assert.assertEquals(result, 600_000.0);
    }

    @Test(description = "P9: D2=F, D7=T, D8=F(COD) → ship +20K = 120,000")
    @Story("Basis Path Coverage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 9: No coupon, NORMAL, total<500K, COD → ship +20K")
    public void testP9_NoCoupon_Normal_COD() {
        // subtotal=100K, disc=0, memDisc=0, total=100K, +20K = 120K
        double result = processor.calculateTotal(items(100_000), null, "NORMAL", "COD");
        Assert.assertEquals(result, 120_000.0);
    }

    // ═══════════════════════════════════════════════════════════
    //  MC/DC TESTS — D2 && D3 (Coupon Condition)
    //  A: couponCode != null
    //  B: !couponCode.isEmpty()
    //  C: couponCode.equals("SALE10")
    //  Biểu thức: A && B && C  →  n+1 = 4 test cases
    // ═══════════════════════════════════════════════════════════

    @Test(description = "MC1: A=T, B=T, C=T — coupon='SALE10' → D2&&D3=TRUE → 210,000")
    @Story("MC/DC — D2 && D3 (Coupon)")
    @Severity(SeverityLevel.CRITICAL)
    @Description("MC/DC Row1: coupon='SALE10', A=T B=T C=T → discount 10%. Pair cơ sở cho A, B, C.")
    public void testMC1_AllTrue_SALE10() {
        // subtotal=200K, disc=20K, total=180K, +30K = 210K
        double result = processor.calculateTotal(items(200_000), "SALE10", "NORMAL", "MOMO");
        Assert.assertEquals(result, 210_000.0);
    }

    @Test(description = "MC2: A=T, B=T, C=F — coupon='SALE20' → D3=FALSE → 190,000")
    @Story("MC/DC — D2 && D3 (Coupon)")
    @Severity(SeverityLevel.CRITICAL)
    @Description("MC/DC Row2: Pair C — C thay đổi T→F, A=T B=T giữ nguyên, outcome T→F")
    public void testMC2_C_False_SALE20() {
        // subtotal=200K, disc=40K, total=160K, +30K = 190K
        double result = processor.calculateTotal(items(200_000), "SALE20", "NORMAL", "MOMO");
        Assert.assertEquals(result, 190_000.0);
    }

    @Test(description = "MC3: A=T, B=F — coupon='' → D2=FALSE → 230,000")
    @Story("MC/DC — D2 && D3 (Coupon)")
    @Severity(SeverityLevel.CRITICAL)
    @Description("MC/DC Row3: Pair B — B thay đổi T→F (empty string), A=T giữ nguyên, outcome T→F")
    public void testMC3_B_False_EmptyCoupon() {
        // subtotal=200K, disc=0, memDisc=0, total=200K, +30K = 230K
        double result = processor.calculateTotal(items(200_000), "", "NORMAL", "MOMO");
        Assert.assertEquals(result, 230_000.0);
    }

    @Test(description = "MC4: A=F — coupon=null → D2=FALSE → 230,000")
    @Story("MC/DC — D2 && D3 (Coupon)")
    @Severity(SeverityLevel.CRITICAL)
    @Description("MC/DC Row4: Pair A — A thay đổi T→F (null), outcome T→F")
    public void testMC4_A_False_NullCoupon() {
        // subtotal=200K, disc=0, memDisc=0, total=200K, +30K = 230K
        double result = processor.calculateTotal(items(200_000), null, "NORMAL", "MOMO");
        Assert.assertEquals(result, 230_000.0);
    }

    // ═══════════════════════════════════════════════════════════
    //  DATA PROVIDER — Tổng hợp tất cả test cases
    // ═══════════════════════════════════════════════════════════

    @DataProvider(name = "allTestCases")
    public Object[][] allTestCases() {
        return new Object[][] {
            // Basis Paths
            { items(100_000), null,      "GOLD",     "MOMO", 125_000.0, "P2: NoCoupon, GOLD, online" },
            { items(100_000), null,      "NORMAL",   "MOMO", 130_000.0, "P3: NoCoupon, NORMAL, online" },
            { items(100_000), null,      "PLATINUM", "MOMO", 120_000.0, "P4: NoCoupon, PLATINUM, online" },
            { items(200_000), "SALE10",  "NORMAL",   "MOMO", 210_000.0, "P5: SALE10, NORMAL, online" },
            { items(200_000), "SALE20",  "NORMAL",   "MOMO", 190_000.0, "P6: SALE20, NORMAL, online" },
            { items(600_000), null,      "NORMAL",   "MOMO", 600_000.0, "P8: NoCoupon, ≥500K, free ship" },
            { items(100_000), null,      "NORMAL",   "COD",  120_000.0, "P9: NoCoupon, NORMAL, COD" },
            // MC/DC
            { items(200_000), "SALE10",  "NORMAL",   "MOMO", 210_000.0, "MC1: A=T,B=T,C=T" },
            { items(200_000), "SALE20",  "NORMAL",   "MOMO", 190_000.0, "MC2: A=T,B=T,C=F" },
            { items(200_000), "",        "NORMAL",   "MOMO", 230_000.0, "MC3: A=T,B=F" },
            { items(200_000), null,      "NORMAL",   "MOMO", 230_000.0, "MC4: A=F" },
        };
    }

    @Test(dataProvider = "allTestCases", description = "DataProvider: Basis Path + MC/DC")
    @Story("Tổng hợp — DataProvider")
    @Severity(SeverityLevel.NORMAL)
    @Description("Kiểm tra tất cả non-exception test cases qua DataProvider")
    public void testCalculateTotal_DataProvider(List<Item> items, String coupon,
            String member, String payment, double expected, String moTa) {
        double result = processor.calculateTotal(items, coupon, member, payment);
        Assert.assertEquals(result, expected, "FAIL: " + moTa);
    }
}
