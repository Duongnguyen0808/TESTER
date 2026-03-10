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
        List<ITestResult> basisResults = new ArrayList<>();
        List<ITestResult> mcdcResults = new ArrayList<>();
        List<ITestResult> dpResults = new ArrayList<>();

        for (ISuite suite : suites) {
            for (ISuiteResult sr : suite.getResults().values()) {
                ITestContext ctx = sr.getTestContext();
                for (ITestResult r : merge(ctx)) {
                    String method = r.getMethod().getMethodName();
                    String desc = r.getMethod().getDescription();
                    if (desc == null) desc = "";
                    if (method.contains("DataProvider")) {
                        dpResults.add(r);
                    } else if (method.startsWith("testMC") || desc.contains("MC")) {
                        mcdcResults.add(r);
                    } else {
                        basisResults.add(r);
                    }
                }
                totalPass += ctx.getPassedTests().size();
                totalFail += ctx.getFailedTests().size();
                totalSkip += ctx.getSkippedTests().size();
            }
        }

        basisResults.sort(Comparator.comparing(r -> r.getMethod().getMethodName()));
        mcdcResults.sort(Comparator.comparing(r -> r.getMethod().getMethodName()));
        dpResults.sort(Comparator.comparing(r -> {
            Object[] p = r.getParameters();
            return p != null && p.length > 5 ? p[5].toString() : "";
        }));

        int total = totalPass + totalFail + totalSkip;
        double passRate = total > 0 ? (totalPass * 100.0 / total) : 0;
        String ts = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        String filePath = outputDirectory + File.separator + "BaoCaoKiemThu.html";
        try (Writer w = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            w.write("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n<meta charset='UTF-8'>\n");
            w.write("<title>Báo Cáo Kiểm Thử — Bài 7 Comprehensive</title>\n");
            w.write("<style>\n");
            w.write("*{margin:0;padding:0;box-sizing:border-box}\n");
            w.write("body{font-family:'Segoe UI',Tahoma,sans-serif;background:#f0f2f5;color:#333}\n");
            w.write(".hdr{background:linear-gradient(135deg,#1b5e20,#43a047);color:#fff;padding:32px 40px}\n");
            w.write(".hdr h1{font-size:26px;margin-bottom:6px}.hdr p{opacity:.85;font-size:14px}\n");
            w.write(".ctn{max-width:1280px;margin:24px auto;padding:0 20px}\n");
            // cards
            w.write(".cards{display:grid;grid-template-columns:repeat(5,1fr);gap:14px;margin-bottom:24px}\n");
            w.write(".cd{background:#fff;border-radius:12px;padding:18px;text-align:center;box-shadow:0 2px 8px rgba(0,0,0,.07);border-top:4px solid #ccc}\n");
            w.write(".cd.tot{border-top-color:#1b5e20}.cd.ps{border-top-color:#2e7d32}.cd.fl{border-top-color:#c62828}\n");
            w.write(".cd.rt{border-top-color:#f57f17}.cd.cc{border-top-color:#6a1b9a}\n");
            w.write(".cd .num{font-size:34px;font-weight:700;margin:6px 0}\n");
            w.write(".cd .lbl{font-size:11px;color:#888;text-transform:uppercase;letter-spacing:1px}\n");
            w.write(".cd.tot .num{color:#1b5e20}.cd.ps .num{color:#2e7d32}.cd.fl .num{color:#c62828}\n");
            w.write(".cd.rt .num{color:#f57f17}.cd.cc .num{color:#6a1b9a}\n");
            // info
            w.write(".info-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px;margin-bottom:24px}\n");
            w.write(".info{background:#fff;border-radius:12px;padding:20px 24px;box-shadow:0 2px 8px rgba(0,0,0,.07);border-left:4px solid #43a047}\n");
            w.write(".info.mcdc{border-left-color:#6a1b9a}\n");
            w.write(".info h3{font-size:15px;margin-bottom:8px;color:#1b5e20}\n");
            w.write(".info.mcdc h3{color:#6a1b9a}\n");
            w.write(".info p,.info li{font-size:13px;line-height:1.7}.info ul{margin-left:18px}\n");
            // section
            w.write(".sec{background:#fff;border-radius:12px;box-shadow:0 2px 8px rgba(0,0,0,.07);margin-bottom:24px;overflow:hidden}\n");
            w.write(".sec-t{padding:14px 24px;font-size:15px;font-weight:600;border-bottom:1px solid #e0e0e0;display:flex;align-items:center;gap:10px}\n");
            w.write(".sec-t .cnt{background:#e8f5e9;color:#1b5e20;padding:3px 12px;border-radius:12px;font-size:12px;font-weight:600}\n");
            w.write(".sec-t .cnt.mcdc{background:#f3e5f5;color:#6a1b9a}\n");
            w.write(".sec-t .cnt.dp{background:#e3f2fd;color:#1565c0}\n");
            // table
            w.write("table{width:100%;border-collapse:collapse}\n");
            w.write("th{background:#fafafa;padding:10px 12px;text-align:left;font-size:11px;text-transform:uppercase;letter-spacing:.5px;color:#666;border-bottom:2px solid #e0e0e0}\n");
            w.write("td{padding:9px 12px;border-bottom:1px solid #f0f0f0;font-size:13px}tr:hover{background:#f9fafb}\n");
            w.write(".badge{display:inline-block;padding:3px 13px;border-radius:20px;font-size:12px;font-weight:600}\n");
            w.write(".badge.pass{background:#e8f5e9;color:#2e7d32}.badge.fail{background:#ffebee;color:#c62828}.badge.skip{background:#fff3e0;color:#e65100}\n");
            w.write(".meth{font-family:'Consolas',monospace;font-size:12px}\n");
            w.write(".desc{color:#555;font-size:12px;margin-top:2px}\n");
            w.write(".tag{display:inline-block;padding:2px 9px;border-radius:12px;font-size:10px;font-weight:600;margin-right:3px}\n");
            w.write(".tag.bp{background:#e8f5e9;color:#1b5e20}.tag.mc{background:#f3e5f5;color:#6a1b9a}.tag.dp{background:#e3f2fd;color:#1565c0}\n");
            w.write(".tag.ex{background:#ffebee;color:#c62828}.tag.allure{background:#fff8e1;color:#f57f17}\n");
            w.write(".time{color:#aaa;font-size:13px}\n");
            w.write(".jc{background:#e8f5e9;border-left:4px solid #2e7d32;padding:14px 20px;border-radius:0 8px 8px 0;margin-bottom:24px}\n");
            w.write(".jc p{font-size:13px;line-height:1.7}\n");
            w.write(".err{color:#c62828;font-size:11px;margin-top:3px;font-family:monospace;max-width:500px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}\n");
            w.write(".footer{text-align:center;padding:20px;color:#999;font-size:13px}\n");
            w.write("</style>\n</head>\n<body>\n");

            // Header
            w.write("<div class='hdr'>\n");
            w.write("<h1>&#128202; BÁO CÁO KIỂM THỬ TOÀN DIỆN — BÀI 7</h1>\n");
            w.write("<p>OrderProcessor | Basis Path (CC=9) + MC/DC (D2&amp;&amp;D3) | Allure + JaCoCo | " + ts + "</p>\n");
            w.write("</div>\n<div class='ctn'>\n");

            // Info grid
            w.write("<div class='info-grid'>\n");
            w.write("<div class='info'><h3>&#128736; Basis Path Analysis</h3><ul>\n");
            w.write("<li><strong>CFG:</strong> 22 nodes, 29 edges</li>\n");
            w.write("<li><strong>CC = E−N+2P = 29−22+2 = 9</strong></li>\n");
            w.write("<li><strong>CC = D+1 = 8+1 = 9</strong></li>\n");
            w.write("<li>8 decisions: D1→D8</li>\n");
            w.write("<li>9 basis paths + 1 boundary = <strong>10 TC</strong></li>\n");
            w.write("</ul></div>\n");
            w.write("<div class='info mcdc'><h3>&#128300; MC/DC — D2 &amp;&amp; D3</h3><ul>\n");
            w.write("<li><strong>A:</strong> couponCode != null</li>\n");
            w.write("<li><strong>B:</strong> !couponCode.isEmpty()</li>\n");
            w.write("<li><strong>C:</strong> couponCode.equals(\"SALE10\")</li>\n");
            w.write("<li>Pairs: A{1↔4}, B{1↔3}, C{1↔2}</li>\n");
            w.write("<li>Tối thiểu: <strong>n+1 = 4 TC</strong></li>\n");
            w.write("</ul></div>\n</div>\n");

            // JaCoCo + Allure note
            w.write("<div class='jc'>\n");
            w.write("<p>&#128200; <strong>JaCoCo:</strong> <code>target/site/jacoco/index.html</code>");
            w.write(" &nbsp;|&nbsp; &#128202; <strong>Allure:</strong> <code>target/allure-results/</code>");
            w.write(" (chạy <code>allure serve target/allure-results</code> nếu có Allure CLI)</p>\n");
            w.write("</div>\n");

            // Cards
            w.write("<div class='cards'>\n");
            w.write("<div class='cd tot'><div class='lbl'>Tổng test</div><div class='num'>" + total + "</div></div>\n");
            w.write("<div class='cd ps'><div class='lbl'>Thành công</div><div class='num'>" + totalPass + "</div></div>\n");
            w.write("<div class='cd fl'><div class='lbl'>Thất bại</div><div class='num'>" + totalFail + "</div></div>\n");
            w.write("<div class='cd rt'><div class='lbl'>Tỷ lệ đạt</div><div class='num'>" + String.format("%.0f%%", passRate) + "</div></div>\n");
            w.write("<div class='cd cc'><div class='lbl'>CC</div><div class='num'>9</div></div>\n");
            w.write("</div>\n");

            // Basis Path section
            writeSection(w, "&#128736; Basis Path Tests (CC=9)", "cnt", basisResults, "bp", "BasisPath");
            // MC/DC section
            writeSection(w, "&#128300; MC/DC Tests — D2 &amp;&amp; D3", "cnt mcdc", mcdcResults, "mc", "MC/DC");
            // DataProvider section
            writeSection(w, "&#128202; DataProvider — Tổng hợp", "cnt dp", dpResults, "dp", "DataProvider");

            w.write("<div class='footer'>Báo cáo tạo tự động — TestNG 7.8.0 | Allure 2.24.0 | JaCoCo 0.8.11</div>\n");
            w.write("</div>\n</body>\n</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSection(Writer w, String title, String cntClass, List<ITestResult> results,
                              String tagClass, String tagLabel) throws IOException {
        if (results.isEmpty()) return;
        long pass = results.stream().filter(r -> r.getStatus() == ITestResult.SUCCESS).count();
        w.write("<div class='sec'>\n<div class='sec-t'>" + title);
        w.write(" <span class='" + cntClass + "'>" + pass + "/" + results.size() + " passed</span></div>\n");
        w.write("<table>\n<tr><th>#</th><th>Phương thức</th><th>Mô tả</th><th>Loại</th><th>Trạng thái</th><th>Thời gian</th></tr>\n");
        int idx = 0;
        for (ITestResult r : results) {
            idx++;
            String status, bClass;
            if (r.getStatus() == ITestResult.SUCCESS) { status = "Đạt"; bClass = "pass"; }
            else if (r.getStatus() == ITestResult.FAILURE) { status = "Lỗi"; bClass = "fail"; }
            else { status = "Bỏ qua"; bClass = "skip"; }
            long dur = r.getEndMillis() - r.getStartMillis();
            String mn = r.getMethod().getMethodName();
            String desc = r.getMethod().getDescription();
            if (desc == null) desc = "";
            // Params
            String ps = "";
            if (r.getParameters() != null && r.getParameters().length > 0) {
                Object[] p = r.getParameters();
                StringBuilder sb = new StringBuilder("(");
                for (int i = 0; i < Math.min(p.length, 4); i++) {
                    if (i > 0) sb.append(", ");
                    String v = p[i] == null ? "null" : p[i].toString();
                    if (v.length() > 30) v = v.substring(0, 27) + "...";
                    sb.append(esc(v));
                }
                if (p.length > 4) sb.append(", ...");
                sb.append(")");
                ps = sb.toString();
            }
            String tags = "<span class='tag " + tagClass + "'>" + tagLabel + "</span>";
            if (desc.contains("exception") || desc.contains("Exception") || mn.contains("Null") || mn.contains("Empty") || mn.contains("Invalid")) {
                tags += "<span class='tag ex'>Exception</span>";
            }
            tags += "<span class='tag allure'>Allure</span>";
            String errHtml = "";
            if (r.getStatus() == ITestResult.FAILURE && r.getThrowable() != null) {
                String msg = r.getThrowable().getMessage();
                if (msg != null && msg.length() > 100) msg = msg.substring(0, 97) + "...";
                if (msg != null) errHtml = "<div class='err'>" + esc(msg) + "</div>";
            }
            w.write("<tr><td>" + idx + "</td>");
            w.write("<td><div class='meth'>" + esc(mn) + "()" + (ps.isEmpty() ? "" : " " + ps) + "</div></td>");
            w.write("<td><div class='desc'>" + esc(desc) + "</div>" + errHtml + "</td>");
            w.write("<td>" + tags + "</td>");
            w.write("<td><span class='badge " + bClass + "'>" + status + "</span></td>");
            w.write("<td class='time'>" + dur + " ms</td></tr>\n");
        }
        w.write("</table>\n</div>\n");
    }

    private List<ITestResult> merge(ITestContext ctx) {
        List<ITestResult> all = new ArrayList<>();
        all.addAll(ctx.getPassedTests().getAllResults());
        all.addAll(ctx.getFailedTests().getAllResults());
        all.addAll(ctx.getSkippedTests().getAllResults());
        return all;
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
