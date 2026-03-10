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
        List<ITestResult> allResults = new ArrayList<>();

        for (ISuite suite : suites) {
            for (ISuiteResult sr : suite.getResults().values()) {
                ITestContext ctx = sr.getTestContext();
                allResults.addAll(ctx.getPassedTests().getAllResults());
                allResults.addAll(ctx.getFailedTests().getAllResults());
                allResults.addAll(ctx.getSkippedTests().getAllResults());
                totalPass += ctx.getPassedTests().size();
                totalFail += ctx.getFailedTests().size();
                totalSkip += ctx.getSkippedTests().size();
            }
        }
        allResults.sort(Comparator.comparing(r -> r.getMethod().getMethodName()));

        int total = totalPass + totalFail + totalSkip;
        double passRate = total > 0 ? (totalPass * 100.0 / total) : 0;
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        String filePath = outputDirectory + File.separator + "BaoCaoKiemThu.html";
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            writer.write("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n<meta charset='UTF-8'>\n");
            writer.write("<title>Báo Cáo Kiểm Thử - Bài 4 Basis Path</title>\n");
            writer.write("<style>\n");
            writer.write("* { margin: 0; padding: 0; box-sizing: border-box; }\n");
            writer.write("body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f2f5; color: #333; }\n");
            writer.write(".header { background: linear-gradient(135deg, #4a148c, #7b1fa2); color: white; padding: 30px 40px; }\n");
            writer.write(".header h1 { font-size: 26px; margin-bottom: 5px; }\n");
            writer.write(".header p { opacity: 0.85; font-size: 14px; }\n");
            writer.write(".container { max-width: 1100px; margin: 25px auto; padding: 0 20px; }\n");
            writer.write(".cards { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 28px; }\n");
            writer.write(".card { background: white; border-radius: 12px; padding: 22px; text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,0.08); border-top: 4px solid #ccc; }\n");
            writer.write(".card.total { border-top-color: #4a148c; } .card.pass { border-top-color: #2e7d32; }\n");
            writer.write(".card.fail { border-top-color: #c62828; } .card.rate { border-top-color: #f57f17; }\n");
            writer.write(".card .number { font-size: 36px; font-weight: 700; margin: 8px 0; }\n");
            writer.write(".card .label { font-size: 13px; color: #777; text-transform: uppercase; letter-spacing: 1px; }\n");
            writer.write(".card.total .number { color: #4a148c; } .card.pass .number { color: #2e7d32; }\n");
            writer.write(".card.fail .number { color: #c62828; } .card.rate .number { color: #f57f17; }\n");
            writer.write(".info-box { background: #f3e5f5; border-left: 4px solid #7b1fa2; padding: 16px 20px; border-radius: 0 8px 8px 0; margin-bottom: 24px; }\n");
            writer.write(".info-box h3 { color: #4a148c; margin-bottom: 8px; font-size: 15px; }\n");
            writer.write(".info-box p { font-size: 13px; line-height: 1.8; }\n");
            writer.write(".section { background: white; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); margin-bottom: 24px; overflow: hidden; }\n");
            writer.write(".section-title { padding: 16px 24px; font-size: 16px; font-weight: 600; border-bottom: 1px solid #e0e0e0; }\n");
            writer.write("table { width: 100%; border-collapse: collapse; }\n");
            writer.write("th { background: #f5f5f5; padding: 12px 16px; text-align: left; font-size: 12px; text-transform: uppercase; letter-spacing: 0.5px; color: #666; border-bottom: 2px solid #e0e0e0; }\n");
            writer.write("td { padding: 12px 16px; border-bottom: 1px solid #f0f0f0; font-size: 14px; }\n");
            writer.write("tr:hover { background: #f8f9fa; }\n");
            writer.write(".badge { display: inline-block; padding: 4px 14px; border-radius: 20px; font-size: 12px; font-weight: 600; }\n");
            writer.write(".badge.pass { background: #e8f5e9; color: #2e7d32; }\n");
            writer.write(".badge.fail { background: #ffebee; color: #c62828; }\n");
            writer.write(".badge.skip { background: #fff3e0; color: #e65100; }\n");
            writer.write(".method { font-family: 'Consolas', monospace; font-size: 13px; }\n");
            writer.write(".desc { color: #555; font-size: 13px; margin-top: 3px; }\n");
            writer.write(".path-tag { display: inline-block; background: #ede7f6; color: #4a148c; padding: 2px 10px; border-radius: 12px; font-size: 11px; font-weight: 600; margin-right: 6px; }\n");
            writer.write(".time { color: #999; font-size: 13px; }\n");
            writer.write(".footer { text-align: center; padding: 20px; color: #999; font-size: 13px; }\n");
            writer.write("</style>\n</head>\n<body>\n");

            // Header
            writer.write("<div class='header'>\n");
            writer.write("<h1>&#128202; BÁO CÁO KIỂM THỬ ĐƯỜNG CƠ SỞ (BASIS PATH)</h1>\n");
            writer.write("<p>Bài 4 — Path Coverage &amp; Cyclomatic Complexity | " + timestamp + "</p>\n");
            writer.write("</div>\n");

            writer.write("<div class='container'>\n");

            // Info box
            writer.write("<div class='info-box'>\n");
            writer.write("<h3>&#128200; Thông tin Cyclomatic Complexity</h3>\n");
            writer.write("<p><strong>Hàm:</strong> <code>tinhPhiShip(double trongLuong, String vung, boolean laMember)</code><br>\n");
            writer.write("<strong>Số decision (D):</strong> 7 (D1–D7)<br>\n");
            writer.write("<strong>CC = D + 1 = 8</strong> → Cần 8 đường cơ sở (basis path) → 8 test case<br>\n");
            writer.write("<strong>Kiểm tra:</strong> CC = E − N + 2P = 20 − 14 + 2 = <strong>8</strong> ✓</p>\n");
            writer.write("</div>\n");

            // Cards
            writer.write("<div class='cards'>\n");
            writer.write("<div class='card total'><div class='label'>Tổng test (CC)</div><div class='number'>" + total + "</div></div>\n");
            writer.write("<div class='card pass'><div class='label'>Thành công</div><div class='number'>" + totalPass + "</div></div>\n");
            writer.write("<div class='card fail'><div class='label'>Thất bại</div><div class='number'>" + totalFail + "</div></div>\n");
            writer.write("<div class='card rate'><div class='label'>Tỷ lệ đạt</div><div class='number'>" + String.format("%.0f%%", passRate) + "</div></div>\n");
            writer.write("</div>\n");

            // Detail table
            writer.write("<div class='section'>\n");
            writer.write("<div class='section-title'>&#128196; Chi tiết 8 đường cơ sở — PhiShipBasisPathTest</div>\n");
            writer.write("<table>\n<tr><th style='width:5%'>#</th><th style='width:28%'>Phương thức</th><th style='width:32%'>Mô tả (Basis Path)</th><th style='width:15%'>Trạng thái</th><th style='width:20%'>Thời gian</th></tr>\n");

            int idx = 0;
            for (ITestResult r : allResults) {
                idx++;
                String status;
                String badgeClass;
                if (r.getStatus() == ITestResult.SUCCESS) { status = "Đạt"; badgeClass = "pass"; }
                else if (r.getStatus() == ITestResult.FAILURE) { status = "Lỗi"; badgeClass = "fail"; }
                else { status = "Bỏ qua"; badgeClass = "skip"; }

                long duration = r.getEndMillis() - r.getStartMillis();
                String methodName = r.getMethod().getMethodName();
                String description = r.getMethod().getDescription();
                if (description == null || description.isEmpty()) description = "";

                // Extract path number for tag
                String pathTag = "";
                if (methodName.contains("Path")) {
                    String num = methodName.replaceAll("\\D+", "");
                    if (!num.isEmpty()) pathTag = "<span class='path-tag'>Path " + num + "</span>";
                }

                writer.write("<tr>");
                writer.write("<td>" + idx + "</td>");
                writer.write("<td><div class='method'>" + methodName + "()</div></td>");
                writer.write("<td>" + pathTag + "<div class='desc'>" + description + "</div></td>");
                writer.write("<td><span class='badge " + badgeClass + "'>" + status + "</span></td>");
                writer.write("<td class='time'>" + duration + " ms</td>");
                writer.write("</tr>\n");
            }
            writer.write("</table>\n</div>\n");

            writer.write("<div class='footer'>Báo cáo tạo tự động bởi VietnameseReportListener — TestNG 7.8.0</div>\n");
            writer.write("</div>\n</body>\n</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
