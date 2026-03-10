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
        Map<String, List<ITestResult>> groupedResults = new LinkedHashMap<>();
        int totalPass = 0, totalFail = 0, totalSkip = 0;

        for (ISuite suite : suites) {
            for (ISuiteResult sr : suite.getResults().values()) {
                ITestContext ctx = sr.getTestContext();
                String testName = ctx.getName();
                List<ITestResult> results = groupedResults.computeIfAbsent(testName, k -> new ArrayList<>());
                results.addAll(ctx.getPassedTests().getAllResults());
                results.addAll(ctx.getFailedTests().getAllResults());
                results.addAll(ctx.getSkippedTests().getAllResults());
                totalPass += ctx.getPassedTests().size();
                totalFail += ctx.getFailedTests().size();
                totalSkip += ctx.getSkippedTests().size();
            }
        }

        int total = totalPass + totalFail + totalSkip;
        double passRate = total > 0 ? (totalPass * 100.0 / total) : 0;
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        String filePath = outputDirectory + File.separator + "BaoCaoKiemThu.html";
        try (Writer w = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            w.write("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n<meta charset='UTF-8'>\n");
            w.write("<title>Báo Cáo Kiểm Thử - Bài 6 White-Box + Selenium</title>\n");
            w.write("<style>\n");
            w.write("*{margin:0;padding:0;box-sizing:border-box}\n");
            w.write("body{font-family:'Segoe UI',Tahoma,sans-serif;background:#f4f6f8;color:#333}\n");
            w.write(".header{background:linear-gradient(135deg,#1a237e,#3949ab);color:#fff;padding:32px 40px}\n");
            w.write(".header h1{font-size:26px;margin-bottom:6px}\n");
            w.write(".header p{opacity:.85;font-size:14px}\n");
            w.write(".container{max-width:1250px;margin:24px auto;padding:0 20px}\n");
            w.write(".cards{display:grid;grid-template-columns:repeat(4,1fr);gap:16px;margin-bottom:26px}\n");
            w.write(".card{background:#fff;border-radius:12px;padding:20px;text-align:center;box-shadow:0 2px 10px rgba(0,0,0,.07);border-top:4px solid #ccc}\n");
            w.write(".card.total{border-top-color:#1a237e}.card.pass{border-top-color:#2e7d32}\n");
            w.write(".card.fail{border-top-color:#c62828}.card.rate{border-top-color:#f57f17}\n");
            w.write(".card .number{font-size:36px;font-weight:700;margin:8px 0}\n");
            w.write(".card .label{font-size:12px;color:#888;text-transform:uppercase;letter-spacing:1px}\n");
            w.write(".card.total .number{color:#1a237e}.card.pass .number{color:#2e7d32}\n");
            w.write(".card.fail .number{color:#c62828}.card.rate .number{color:#f57f17}\n");
            // Section
            w.write(".section{background:#fff;border-radius:12px;box-shadow:0 2px 10px rgba(0,0,0,.07);margin-bottom:24px;overflow:hidden}\n");
            w.write(".section-title{padding:16px 24px;font-size:16px;font-weight:600;border-bottom:1px solid #e0e0e0;display:flex;align-items:center;gap:10px}\n");
            w.write(".section-title .badge-count{background:#e8eaf6;color:#1a237e;padding:3px 12px;border-radius:12px;font-size:13px;font-weight:600}\n");
            // Table
            w.write("table{width:100%;border-collapse:collapse}\n");
            w.write("th{background:#f5f5f5;padding:11px 14px;text-align:left;font-size:11px;text-transform:uppercase;letter-spacing:.5px;color:#666;border-bottom:2px solid #e0e0e0}\n");
            w.write("td{padding:10px 14px;border-bottom:1px solid #f0f0f0;font-size:13px}\n");
            w.write("tr:hover{background:#f9fafb}\n");
            w.write(".badge{display:inline-block;padding:3px 14px;border-radius:20px;font-size:12px;font-weight:600}\n");
            w.write(".badge.pass{background:#e8f5e9;color:#2e7d32}\n");
            w.write(".badge.fail{background:#ffebee;color:#c62828}\n");
            w.write(".badge.skip{background:#fff3e0;color:#e65100}\n");
            w.write(".method{font-family:'Consolas',monospace;font-size:12px}\n");
            w.write(".desc{color:#555;font-size:12px;margin-top:2px}\n");
            w.write(".tag{display:inline-block;padding:2px 10px;border-radius:12px;font-size:11px;font-weight:600;margin-right:4px}\n");
            w.write(".tag.tdd{background:#e8eaf6;color:#283593}\n");
            w.write(".tag.selenium{background:#e0f7fa;color:#00695c}\n");
            w.write(".tag.basis{background:#fce4ec;color:#c62828}\n");
            w.write(".tag.boundary{background:#fff8e1;color:#f57f17}\n");
            // Info
            w.write(".info-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px;margin-bottom:24px}\n");
            w.write(".info-box{background:#fff;border-radius:12px;padding:20px 24px;box-shadow:0 2px 10px rgba(0,0,0,.07);border-left:4px solid #3949ab}\n");
            w.write(".info-box.selenium{border-left-color:#00897b}\n");
            w.write(".info-box h3{font-size:15px;margin-bottom:8px;color:#1a237e}\n");
            w.write(".info-box.selenium h3{color:#00695c}\n");
            w.write(".info-box p,.info-box li{font-size:13px;line-height:1.7}\n");
            w.write(".info-box ul{margin-left:18px}\n");
            w.write(".jacoco-box{background:#e8eaf6;border-left:4px solid #283593;padding:16px 20px;border-radius:0 8px 8px 0;margin-bottom:24px}\n");
            w.write(".jacoco-box p{font-size:13px;line-height:1.7}\n");
            w.write(".footer{text-align:center;padding:20px;color:#999;font-size:13px}\n");
            w.write(".time{color:#aaa;font-size:13px}\n");
            w.write(".error-detail{color:#c62828;font-size:11px;margin-top:4px;font-family:monospace;max-width:500px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}\n");
            w.write("</style>\n</head>\n<body>\n");

            // Header
            w.write("<div class='header'>\n");
            w.write("<h1>&#128202; BÁO CÁO KIỂM THỬ — BÀI 6</h1>\n");
            w.write("<p>Tích hợp White-Box Testing với Selenium WebDriver | TDD + JaCoCo Coverage | " + timestamp + "</p>\n");
            w.write("</div>\n");

            w.write("<div class='container'>\n");

            // Info grid
            w.write("<div class='info-grid'>\n");
            w.write("<div class='info-box'>\n");
            w.write("<h3>&#128736; Bài 6.1 — TDD: PhoneValidator</h3>\n");
            w.write("<ul>\n");
            w.write("<li><strong>CFG:</strong> 6 nút quyết định → <strong>CC = 7</strong></li>\n");
            w.write("<li><strong>7 Basis Path</strong> + 10 Boundary tests</li>\n");
            w.write("<li>Quy trình: RED → GREEN → REFACTOR</li>\n");
            w.write("<li>Coverage đo bằng <strong>JaCoCo</strong></li>\n");
            w.write("</ul>\n</div>\n");
            w.write("<div class='info-box selenium'>\n");
            w.write("<h3>&#127760; Bài 6.2 — Selenium: TextBox Form</h3>\n");
            w.write("<ul>\n");
            w.write("<li><strong>URL:</strong> demoqa.com/text-box</li>\n");
            w.write("<li><strong>Page Object Model:</strong> TextBoxPage.java</li>\n");
            w.write("<li>CFG: Nhập liệu → Validate email → Output</li>\n");
            w.write("<li>4 CFG paths + 6 Boundary tests</li>\n");
            w.write("</ul>\n</div>\n</div>\n");

            // JaCoCo note
            w.write("<div class='jacoco-box'>\n");
            w.write("<p>&#128200; <strong>JaCoCo Coverage Report:</strong> Xem chi tiết tại <code>target/site/jacoco/index.html</code> sau khi chạy <code>mvn clean test</code></p>\n");
            w.write("</div>\n");

            // Cards
            w.write("<div class='cards'>\n");
            w.write("<div class='card total'><div class='label'>Tổng test</div><div class='number'>" + total + "</div></div>\n");
            w.write("<div class='card pass'><div class='label'>Thành công</div><div class='number'>" + totalPass + "</div></div>\n");
            w.write("<div class='card fail'><div class='label'>Thất bại</div><div class='number'>" + totalFail + "</div></div>\n");
            w.write("<div class='card rate'><div class='label'>Tỷ lệ đạt</div><div class='number'>" + String.format("%.0f%%", passRate) + "</div></div>\n");
            w.write("</div>\n");

            // Grouped sections
            for (Map.Entry<String, List<ITestResult>> entry : groupedResults.entrySet()) {
                String testName = entry.getKey();
                List<ITestResult> results = entry.getValue();
                results.sort(Comparator.comparing(r -> r.getMethod().getMethodName()));

                boolean isSelenium = testName.contains("Selenium") || testName.contains("TextBox");
                String icon = isSelenium ? "&#127760;" : "&#128736;";
                String tagClass = isSelenium ? "selenium" : "tdd";
                String tagLabel = isSelenium ? "Selenium" : "TDD";

                long passCount = results.stream().filter(r -> r.getStatus() == ITestResult.SUCCESS).count();

                w.write("<div class='section'>\n");
                w.write("<div class='section-title'>" + icon + " " + testName);
                w.write(" <span class='badge-count'>" + passCount + "/" + results.size() + " passed</span></div>\n");
                w.write("<table>\n<tr><th>#</th><th>Phương thức</th><th>Mô tả</th><th>Loại</th><th>Trạng thái</th><th>Thời gian</th></tr>\n");

                int idx = 0;
                for (ITestResult r : results) {
                    idx++;
                    String status, badgeClass;
                    if (r.getStatus() == ITestResult.SUCCESS) { status = "Đạt"; badgeClass = "pass"; }
                    else if (r.getStatus() == ITestResult.FAILURE) { status = "Lỗi"; badgeClass = "fail"; }
                    else { status = "Bỏ qua"; badgeClass = "skip"; }

                    long duration = r.getEndMillis() - r.getStartMillis();
                    String methodName = r.getMethod().getMethodName();
                    String desc = r.getMethod().getDescription();
                    if (desc == null) desc = "";

                    // Params
                    String paramStr = "";
                    if (r.getParameters() != null && r.getParameters().length > 0) {
                        Object[] p = r.getParameters();
                        StringBuilder sb = new StringBuilder("(");
                        for (int i = 0; i < Math.min(p.length, 3); i++) {
                            if (i > 0) sb.append(", ");
                            String v = p[i] == null ? "null" : p[i].toString();
                            if (v.length() > 25) v = v.substring(0, 22) + "...";
                            sb.append(v);
                        }
                        if (p.length > 3) sb.append(", ...");
                        sb.append(")");
                        paramStr = sb.toString();
                    }

                    // Type tag
                    String typeTag = "<span class='tag " + tagClass + "'>" + tagLabel + "</span>";
                    if (methodName.contains("P1") || methodName.contains("P2") || methodName.contains("P3")
                        || methodName.contains("P4") || methodName.contains("P5") || methodName.contains("P6")
                        || methodName.contains("P7") || methodName.contains("Valid") || methodName.contains("Invalid")
                        || methodName.contains("Empty") || methodName.contains("Path")) {
                        typeTag += methodName.matches(".*[PB]\\d.*") && methodName.contains("B")
                            ? "<span class='tag boundary'>Boundary</span>"
                            : "<span class='tag basis'>BasisPath</span>";
                    }
                    if (methodName.contains("Boundary") || methodName.contains("Long") || methodName.contains("Special")
                        || methodName.contains("Space") || methodName.contains("Only") || methodName.contains("All")) {
                        typeTag = "<span class='tag " + tagClass + "'>" + tagLabel + "</span><span class='tag boundary'>Boundary</span>";
                    }

                    String errorHtml = "";
                    if (r.getStatus() == ITestResult.FAILURE && r.getThrowable() != null) {
                        String msg = r.getThrowable().getMessage();
                        if (msg != null && msg.length() > 120) msg = msg.substring(0, 117) + "...";
                        if (msg != null) errorHtml = "<div class='error-detail'>" + escapeHtml(msg) + "</div>";
                    }

                    w.write("<tr>");
                    w.write("<td>" + idx + "</td>");
                    w.write("<td><div class='method'>" + escapeHtml(methodName) + "()" + (paramStr.isEmpty() ? "" : " " + escapeHtml(paramStr)) + "</div></td>");
                    w.write("<td><div class='desc'>" + escapeHtml(desc) + "</div>" + errorHtml + "</td>");
                    w.write("<td>" + typeTag + "</td>");
                    w.write("<td><span class='badge " + badgeClass + "'>" + status + "</span></td>");
                    w.write("<td class='time'>" + duration + " ms</td>");
                    w.write("</tr>\n");
                }
                w.write("</table>\n</div>\n");
            }

            w.write("<div class='footer'>Báo cáo tạo tự động bởi VietnameseReportListener — TestNG 7.8.0 | JaCoCo 0.8.11</div>\n");
            w.write("</div>\n</body>\n</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
