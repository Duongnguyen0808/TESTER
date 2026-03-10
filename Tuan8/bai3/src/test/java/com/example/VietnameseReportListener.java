package com.example;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class VietnameseReportListener implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        int totalPass = 0, totalFail = 0, totalSkip = 0;
        // Collect all results grouped by class
        Map<String, List<ITestResult>> passMap = new LinkedHashMap<>();
        Map<String, List<ITestResult>> failMap = new LinkedHashMap<>();
        Map<String, List<ITestResult>> skipMap = new LinkedHashMap<>();

        for (ISuite suite : suites) {
            for (ISuiteResult sr : suite.getResults().values()) {
                ITestContext ctx = sr.getTestContext();
                collectByClass(ctx.getPassedTests().getAllResults(), passMap);
                collectByClass(ctx.getFailedTests().getAllResults(), failMap);
                collectByClass(ctx.getSkippedTests().getAllResults(), skipMap);
                totalPass += ctx.getPassedTests().size();
                totalFail += ctx.getFailedTests().size();
                totalSkip += ctx.getSkippedTests().size();
            }
        }

        int total = totalPass + totalFail + totalSkip;
        double passRate = total > 0 ? (totalPass * 100.0 / total) : 0;
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        // Merge all classes
        Set<String> allClasses = new LinkedHashSet<>();
        allClasses.addAll(passMap.keySet());
        allClasses.addAll(failMap.keySet());
        allClasses.addAll(skipMap.keySet());

        String filePath = outputDirectory + File.separator + "BaoCaoKiemThu.html";
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            writer.write("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n<meta charset='UTF-8'>\n");
            writer.write("<title>Báo Cáo Kiểm Thử - Bài 3 Coverage</title>\n");
            writer.write("<style>\n");
            writer.write("* { margin: 0; padding: 0; box-sizing: border-box; }\n");
            writer.write("body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f2f5; color: #333; }\n");
            writer.write(".header { background: linear-gradient(135deg, #1a237e, #0d47a1); color: white; padding: 30px 40px; }\n");
            writer.write(".header h1 { font-size: 26px; margin-bottom: 5px; }\n");
            writer.write(".header p { opacity: 0.85; font-size: 14px; }\n");
            writer.write(".container { max-width: 1100px; margin: 25px auto; padding: 0 20px; }\n");
            writer.write(".cards { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 28px; }\n");
            writer.write(".card { background: white; border-radius: 12px; padding: 22px; text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,0.08); border-top: 4px solid #ccc; }\n");
            writer.write(".card.total { border-top-color: #1a237e; }\n");
            writer.write(".card.pass { border-top-color: #2e7d32; }\n");
            writer.write(".card.fail { border-top-color: #c62828; }\n");
            writer.write(".card.rate { border-top-color: #f57f17; }\n");
            writer.write(".card .number { font-size: 36px; font-weight: 700; margin: 8px 0; }\n");
            writer.write(".card .label { font-size: 13px; color: #777; text-transform: uppercase; letter-spacing: 1px; }\n");
            writer.write(".card.total .number { color: #1a237e; }\n");
            writer.write(".card.pass .number { color: #2e7d32; }\n");
            writer.write(".card.fail .number { color: #c62828; }\n");
            writer.write(".card.rate .number { color: #f57f17; }\n");
            // Section styles
            writer.write(".section { background: white; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); margin-bottom: 24px; overflow: hidden; }\n");
            writer.write(".section-title { padding: 16px 24px; font-size: 16px; font-weight: 600; border-bottom: 1px solid #e0e0e0; display: flex; align-items: center; gap: 10px; }\n");
            writer.write(".section-title .icon { width: 28px; height: 28px; border-radius: 6px; display: flex; align-items: center; justify-content: center; color: white; font-size: 14px; }\n");
            writer.write(".section-title .icon.cls { background: #1565c0; }\n");
            writer.write("table { width: 100%; border-collapse: collapse; }\n");
            writer.write("th { background: #f5f5f5; padding: 12px 16px; text-align: left; font-size: 12px; text-transform: uppercase; letter-spacing: 0.5px; color: #666; border-bottom: 2px solid #e0e0e0; }\n");
            writer.write("td { padding: 12px 16px; border-bottom: 1px solid #f0f0f0; font-size: 14px; }\n");
            writer.write("tr:hover { background: #f8f9fa; }\n");
            writer.write(".badge { display: inline-block; padding: 4px 14px; border-radius: 20px; font-size: 12px; font-weight: 600; }\n");
            writer.write(".badge.pass { background: #e8f5e9; color: #2e7d32; }\n");
            writer.write(".badge.fail { background: #ffebee; color: #c62828; }\n");
            writer.write(".badge.skip { background: #fff3e0; color: #e65100; }\n");
            writer.write(".method-name { font-family: 'Consolas', monospace; font-size: 13px; }\n");
            writer.write(".params { color: #888; font-size: 12px; margin-top: 2px; }\n");
            writer.write(".time { color: #999; font-size: 13px; }\n");
            writer.write(".footer { text-align: center; padding: 20px; color: #999; font-size: 13px; }\n");
            // Coverage info box
            writer.write(".info-box { background: #e3f2fd; border-left: 4px solid #1565c0; padding: 16px 20px; border-radius: 0 8px 8px 0; margin: 20px 0; }\n");
            writer.write(".info-box h3 { color: #1565c0; margin-bottom: 8px; font-size: 15px; }\n");
            writer.write(".info-box p { font-size: 13px; line-height: 1.6; }\n");
            writer.write("</style>\n</head>\n<body>\n");

            // Header
            writer.write("<div class='header'>\n");
            writer.write("<h1>&#128202; BÁO CÁO KIỂM THỬ HỘP TRẮNG</h1>\n");
            writer.write("<p>Bài 3 — Statement &amp; Branch Coverage | " + timestamp + "</p>\n");
            writer.write("</div>\n");

            writer.write("<div class='container'>\n");

            // Info box
            writer.write("<div class='info-box'>\n");
            writer.write("<h3>&#9989; Mục tiêu kiểm thử</h3>\n");
            writer.write("<p><strong>Bài 3.1:</strong> Hàm <code>xepLoai()</code> — 5 nút quyết định, 10 nhánh, 6 TC đạt 100% Branch Coverage<br>\n");
            writer.write("<strong>Bài 3.2:</strong> Hàm <code>tinhTienNuoc()</code> — 5 nút quyết định, 10 nhánh, 6 TC đạt 100% Branch Coverage</p>\n");
            writer.write("</div>\n");

            // Summary cards
            writer.write("<div class='cards'>\n");
            writer.write("<div class='card total'><div class='label'>Tổng test</div><div class='number'>" + total + "</div></div>\n");
            writer.write("<div class='card pass'><div class='label'>Thành công</div><div class='number'>" + totalPass + "</div></div>\n");
            writer.write("<div class='card fail'><div class='label'>Thất bại</div><div class='number'>" + totalFail + "</div></div>\n");
            writer.write("<div class='card rate'><div class='label'>Tỷ lệ đạt</div><div class='number'>" + String.format("%.0f%%", passRate) + "</div></div>\n");
            writer.write("</div>\n");

            // Detail table per class
            for (String className : allClasses) {
                String shortName = className.contains(".") ? className.substring(className.lastIndexOf('.') + 1) : className;
                String baiLabel = shortName.contains("XepLoai") ? "Bài 3.1" : "Bài 3.2";
                writer.write("<div class='section'>\n");
                writer.write("<div class='section-title'><span class='icon cls'>&#128196;</span> " + baiLabel + " — " + shortName + "</div>\n");
                writer.write("<table>\n<tr><th style='width:5%'>#</th><th style='width:35%'>Phương thức</th><th style='width:25%'>Mô tả nhánh</th><th style='width:15%'>Trạng thái</th><th style='width:20%'>Thời gian</th></tr>\n");

                List<ITestResult> all = new ArrayList<>();
                if (passMap.containsKey(className)) all.addAll(passMap.get(className));
                if (failMap.containsKey(className)) all.addAll(failMap.get(className));
                if (skipMap.containsKey(className)) all.addAll(skipMap.get(className));
                all.sort(Comparator.comparing(r -> r.getMethod().getMethodName()));

                int idx = 0;
                for (ITestResult r : all) {
                    idx++;
                    String status;
                    String badgeClass;
                    if (r.getStatus() == ITestResult.SUCCESS) { status = "Đạt"; badgeClass = "pass"; }
                    else if (r.getStatus() == ITestResult.FAILURE) { status = "Lỗi"; badgeClass = "fail"; }
                    else { status = "Bỏ qua"; badgeClass = "skip"; }

                    long duration = r.getEndMillis() - r.getStartMillis();
                    String methodName = r.getMethod().getMethodName();
                    String branchDesc = getBranchDescription(methodName);

                    writer.write("<tr>");
                    writer.write("<td>" + idx + "</td>");
                    writer.write("<td><div class='method-name'>" + methodName + "()</div></td>");
                    writer.write("<td><div class='params'>" + branchDesc + "</div></td>");
                    writer.write("<td><span class='badge " + badgeClass + "'>" + status + "</span></td>");
                    writer.write("<td class='time'>" + duration + " ms</td>");
                    writer.write("</tr>\n");
                }
                writer.write("</table>\n</div>\n");
            }

            // Footer
            writer.write("<div class='footer'>Báo cáo tạo tự động bởi VietnameseReportListener — TestNG 7.8.0</div>\n");
            writer.write("</div>\n</body>\n</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectByClass(Set<ITestResult> results, Map<String, List<ITestResult>> map) {
        for (ITestResult r : results) {
            String cls = r.getTestClass().getName();
            map.computeIfAbsent(cls, k -> new ArrayList<>()).add(r);
        }
    }

    private String getBranchDescription(String methodName) {
        // Bài 3.1 - XepLoai
        if (methodName.contains("DiemAm")) return "N1-True: điểm không hợp lệ";
        if (methodName.contains("Diem9")) return "N1-F, N2-True: xếp loại Giỏi";
        if (methodName.contains("Diem7")) return "N2-F, N3-True: xếp loại Khá";
        if (methodName.contains("Diem6")) return "N3-F, N4-True: Trung Bình";
        if (methodName.contains("CoThiLai")) return "N4-F, N5-True: Thi lại";
        if (methodName.contains("KhongThiLai")) return "N4-F, N5-False: Học lại";
        // Bài 3.2 - TinhTienNuoc
        if (methodName.contains("BangKhong")) return "N1-True: soM3 ≤ 0 → 0";
        if (methodName.contains("HoNgheo")) return "N1-F, N2-True: hộ nghèo";
        if (methodName.contains("Duoi10")) return "N2-F, N3-T, N4-True: ≤10m³";
        if (methodName.contains("10Den20")) return "N4-F, N5-True: 10-20m³";
        if (methodName.contains("Tren20")) return "N4-F, N5-False: >20m³";
        if (methodName.contains("KinhDoanh")) return "N2-F, N3-False: kinh doanh";
        return "";
    }
}
