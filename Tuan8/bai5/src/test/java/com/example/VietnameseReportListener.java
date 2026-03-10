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
            writer.write("<title>Báo Cáo Kiểm Thử - Bài 5 MC/DC</title>\n");
            writer.write("<style>\n");
            writer.write("* { margin: 0; padding: 0; box-sizing: border-box; }\n");
            writer.write("body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f2f5; color: #333; }\n");
            writer.write(".header { background: linear-gradient(135deg, #004d40, #00897b); color: white; padding: 30px 40px; }\n");
            writer.write(".header h1 { font-size: 26px; margin-bottom: 5px; }\n");
            writer.write(".header p { opacity: 0.85; font-size: 14px; }\n");
            writer.write(".container { max-width: 1200px; margin: 25px auto; padding: 0 20px; }\n");
            writer.write(".cards { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 28px; }\n");
            writer.write(".card { background: white; border-radius: 12px; padding: 22px; text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,0.08); border-top: 4px solid #ccc; }\n");
            writer.write(".card.total { border-top-color: #004d40; } .card.pass { border-top-color: #2e7d32; }\n");
            writer.write(".card.fail { border-top-color: #c62828; } .card.rate { border-top-color: #f57f17; }\n");
            writer.write(".card .number { font-size: 36px; font-weight: 700; margin: 8px 0; }\n");
            writer.write(".card .label { font-size: 13px; color: #777; text-transform: uppercase; letter-spacing: 1px; }\n");
            writer.write(".card.total .number { color: #004d40; } .card.pass .number { color: #2e7d32; }\n");
            writer.write(".card.fail .number { color: #c62828; } .card.rate .number { color: #f57f17; }\n");
            writer.write(".info-box { background: #e0f2f1; border-left: 4px solid #00897b; padding: 16px 20px; border-radius: 0 8px 8px 0; margin-bottom: 24px; }\n");
            writer.write(".info-box h3 { color: #004d40; margin-bottom: 8px; font-size: 15px; }\n");
            writer.write(".info-box p, .info-box li { font-size: 13px; line-height: 1.8; }\n");
            writer.write(".info-box ul { margin-left: 20px; }\n");
            writer.write(".section { background: white; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); margin-bottom: 24px; overflow: hidden; }\n");
            writer.write(".section-title { padding: 16px 24px; font-size: 16px; font-weight: 600; border-bottom: 1px solid #e0e0e0; }\n");
            writer.write("table { width: 100%; border-collapse: collapse; }\n");
            writer.write("th { background: #f5f5f5; padding: 12px 14px; text-align: left; font-size: 12px; text-transform: uppercase; letter-spacing: 0.5px; color: #666; border-bottom: 2px solid #e0e0e0; }\n");
            writer.write("td { padding: 11px 14px; border-bottom: 1px solid #f0f0f0; font-size: 13px; }\n");
            writer.write("tr:hover { background: #f8f9fa; }\n");
            writer.write(".badge { display: inline-block; padding: 4px 14px; border-radius: 20px; font-size: 12px; font-weight: 600; }\n");
            writer.write(".badge.pass { background: #e8f5e9; color: #2e7d32; }\n");
            writer.write(".badge.fail { background: #ffebee; color: #c62828; }\n");
            writer.write(".badge.skip { background: #fff3e0; color: #e65100; }\n");
            writer.write(".method { font-family: 'Consolas', monospace; font-size: 12px; }\n");
            writer.write(".desc { color: #555; font-size: 12px; margin-top: 3px; }\n");
            writer.write(".mcdc-tag { display: inline-block; background: #e0f2f1; color: #004d40; padding: 2px 10px; border-radius: 12px; font-size: 11px; font-weight: 600; margin-right: 4px; }\n");
            writer.write(".time { color: #999; font-size: 13px; }\n");
            writer.write(".footer { text-align: center; padding: 20px; color: #999; font-size: 13px; }\n");
            writer.write("</style>\n</head>\n<body>\n");

            // Header
            writer.write("<div class='header'>\n");
            writer.write("<h1>&#128202; BÁO CÁO KIỂM THỬ MC/DC</h1>\n");
            writer.write("<p>Bài 5 — Condition Coverage &amp; Modified Condition/Decision Coverage | " + timestamp + "</p>\n");
            writer.write("</div>\n");

            writer.write("<div class='container'>\n");

            // Info box
            writer.write("<div class='info-box'>\n");
            writer.write("<h3>&#128209; Phân tích MC/DC cho hàm duDieuKienVay()</h3>\n");
            writer.write("<p><strong>Biểu thức:</strong> <code>(A && B) && (C || D)</code> — 4 điều kiện đơn</p>\n");
            writer.write("<ul>\n");
            writer.write("<li><strong>A</strong>: tuoi ≥ 22 | <strong>B</strong>: thuNhap ≥ 10M | <strong>C</strong>: coTaiSanBaoLanh | <strong>D</strong>: dienTinDung ≥ 700</li>\n");
            writer.write("<li>MC/DC cần <strong>5 test case</strong> (n+1 = 4+1)</li>\n");
            writer.write("<li>Cặp MC/DC: A(Row2,10) | B(Row2,6) | C(Row2,4) | D(Row3,4)</li>\n");
            writer.write("</ul>\n");
            writer.write("</div>\n");

            // Cards
            writer.write("<div class='cards'>\n");
            writer.write("<div class='card total'><div class='label'>Tổng test</div><div class='number'>" + total + "</div></div>\n");
            writer.write("<div class='card pass'><div class='label'>Thành công</div><div class='number'>" + totalPass + "</div></div>\n");
            writer.write("<div class='card fail'><div class='label'>Thất bại</div><div class='number'>" + totalFail + "</div></div>\n");
            writer.write("<div class='card rate'><div class='label'>Tỷ lệ đạt</div><div class='number'>" + String.format("%.0f%%", passRate) + "</div></div>\n");
            writer.write("</div>\n");

            // Detail table
            writer.write("<div class='section'>\n");
            writer.write("<div class='section-title'>&#128196; Chi tiết kết quả kiểm thử MC/DC</div>\n");
            writer.write("<table>\n<tr><th style='width:4%'>#</th><th style='width:28%'>Phương thức</th><th style='width:35%'>Mô tả MC/DC</th><th style='width:13%'>Trạng thái</th><th style='width:10%'>Thời gian</th></tr>\n");

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
                if (description == null) description = "";

                // Params for DataProvider
                String paramStr = "";
                if (r.getParameters() != null && r.getParameters().length > 0) {
                    Object[] params = r.getParameters();
                    paramStr = String.format("(%s, %.0f, %s, %s)",
                        params[0], (Double) params[1], params[2], params[3]);
                }

                String mcdcTag = getMCDCTag(methodName, r.getParameters());

                writer.write("<tr>");
                writer.write("<td>" + idx + "</td>");
                writer.write("<td><div class='method'>" + methodName + "()" + (paramStr.isEmpty() ? "" : " " + paramStr) + "</div></td>");
                writer.write("<td>" + mcdcTag + "<div class='desc'>" + description + "</div></td>");
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

    private String getMCDCTag(String methodName, Object[] params) {
        StringBuilder sb = new StringBuilder();
        if (methodName.contains("Base_TatCa")) {
            sb.append("<span class='mcdc-tag'>Base A,B,C</span>");
        } else if (methodName.contains("DienTinDung")) {
            sb.append("<span class='mcdc-tag'>D-True</span>");
        } else if (methodName.contains("KhongTaiSan")) {
            sb.append("<span class='mcdc-tag'>C-Pair</span><span class='mcdc-tag'>D-Pair</span>");
        } else if (methodName.contains("ThuNhap")) {
            sb.append("<span class='mcdc-tag'>B-Pair</span>");
        } else if (methodName.contains("Tuoi")) {
            sb.append("<span class='mcdc-tag'>A-Pair</span>");
        } else if (methodName.contains("DataProvider") && params != null && params.length > 5) {
            String moTa = params[5].toString();
            if (moTa.contains("Base")) sb.append("<span class='mcdc-tag'>Base</span>");
            if (moTa.contains("D-True")) sb.append("<span class='mcdc-tag'>D-True</span>");
            if (moTa.contains("C-F") || moTa.contains("D-F")) sb.append("<span class='mcdc-tag'>C/D-Pair</span>");
            if (moTa.contains("B-Doc")) sb.append("<span class='mcdc-tag'>B-Pair</span>");
            if (moTa.contains("A-Doc")) sb.append("<span class='mcdc-tag'>A-Pair</span>");
        }
        return sb.toString();
    }
}
