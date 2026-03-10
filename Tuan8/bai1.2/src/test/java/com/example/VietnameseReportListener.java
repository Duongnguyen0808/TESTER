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
        StringBuilder html = new StringBuilder();
        int totalPass = 0, totalFail = 0, totalSkip = 0;
        List<ITestResult> allResults = new ArrayList<>();

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> results = suite.getResults();
            for (ISuiteResult sr : results.values()) {
                ITestContext tc = sr.getTestContext();
                totalPass += tc.getPassedTests().size();
                totalFail += tc.getFailedTests().size();
                totalSkip += tc.getSkippedTests().size();
                allResults.addAll(tc.getPassedTests().getAllResults());
                allResults.addAll(tc.getFailedTests().getAllResults());
                allResults.addAll(tc.getSkippedTests().getAllResults());
            }
        }

        allResults.sort(Comparator.comparing(ITestResult::getStartMillis));

        int total = totalPass + totalFail + totalSkip;
        double passRate = total > 0 ? (totalPass * 100.0 / total) : 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String now = sdf.format(new Date());

        html.append("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n");
        html.append("<meta charset='UTF-8'>\n");
        html.append("<title>Báo Cáo Kiểm Thử - Bài 1.2</title>\n");
        html.append("<style>\n");
        html.append("* { margin: 0; padding: 0; box-sizing: border-box; }\n");
        html.append("body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f2f5; color: #333; padding: 30px; }\n");
        html.append(".container { max-width: 1000px; margin: 0 auto; }\n");
        html.append(".header { background: linear-gradient(135deg, #1a73e8, #4285f4); color: white; padding: 30px 40px; border-radius: 12px 12px 0 0; text-align: center; }\n");
        html.append(".header h1 { font-size: 26px; margin-bottom: 5px; }\n");
        html.append(".header p { font-size: 14px; opacity: 0.9; }\n");
        html.append(".summary { display: flex; justify-content: space-around; background: white; padding: 25px; border-bottom: 1px solid #e0e0e0; }\n");
        html.append(".summary-item { text-align: center; }\n");
        html.append(".summary-item .number { font-size: 36px; font-weight: bold; }\n");
        html.append(".summary-item .label { font-size: 13px; color: #666; margin-top: 4px; }\n");
        html.append(".total .number { color: #1a73e8; }\n");
        html.append(".pass .number { color: #0f9d58; }\n");
        html.append(".fail .number { color: #db4437; }\n");
        html.append(".skip .number { color: #f4b400; }\n");
        html.append(".rate .number { color: #1a73e8; }\n");
        html.append(".progress-bar { background: white; padding: 15px 40px 20px; }\n");
        html.append(".progress-track { height: 12px; background: #e0e0e0; border-radius: 6px; overflow: hidden; display: flex; }\n");
        html.append(".progress-pass { background: #0f9d58; height: 100%; }\n");
        html.append(".progress-fail { background: #db4437; height: 100%; }\n");
        html.append(".progress-skip { background: #f4b400; height: 100%; }\n");
        html.append("table { width: 100%; border-collapse: collapse; background: white; }\n");
        html.append("th { background: #1a73e8; color: white; padding: 14px 16px; text-align: left; font-size: 14px; }\n");
        html.append("td { padding: 12px 16px; border-bottom: 1px solid #eee; font-size: 14px; }\n");
        html.append("tr:hover { background: #f8f9fa; }\n");
        html.append(".badge { display: inline-block; padding: 4px 14px; border-radius: 20px; font-size: 13px; font-weight: 600; }\n");
        html.append(".badge-pass { background: #e6f4ea; color: #0f9d58; }\n");
        html.append(".badge-fail { background: #fce8e6; color: #db4437; }\n");
        html.append(".badge-skip { background: #fef7e0; color: #f4b400; }\n");
        html.append(".footer { background: white; padding: 15px; text-align: center; border-radius: 0 0 12px 12px; border-top: 1px solid #e0e0e0; font-size: 12px; color: #999; }\n");
        html.append("</style>\n</head>\n<body>\n<div class='container'>\n");

        // Header
        html.append("<div class='header'>\n");
        html.append("<h1>BÁO CÁO KIỂM THỬ FORM ĐĂNG NHẬP</h1>\n");
        html.append("<p>Bài 1.2 — Website: saucedemo.com | Thời gian: ").append(now).append("</p>\n");
        html.append("</div>\n");

        // Summary
        html.append("<div class='summary'>\n");
        html.append("<div class='summary-item total'><div class='number'>").append(total).append("</div><div class='label'>TỔNG SỐ TEST</div></div>\n");
        html.append("<div class='summary-item pass'><div class='number'>").append(totalPass).append("</div><div class='label'>THÀNH CÔNG</div></div>\n");
        html.append("<div class='summary-item fail'><div class='number'>").append(totalFail).append("</div><div class='label'>THẤT BẠI</div></div>\n");
        html.append("<div class='summary-item skip'><div class='number'>").append(totalSkip).append("</div><div class='label'>BỎ QUA</div></div>\n");
        html.append("<div class='summary-item rate'><div class='number'>").append(String.format("%.0f%%", passRate)).append("</div><div class='label'>TỶ LỆ PASS</div></div>\n");
        html.append("</div>\n");

        // Progress bar
        html.append("<div class='progress-bar'><div class='progress-track'>\n");
        if (total > 0) {
            html.append("<div class='progress-pass' style='width:").append(String.format("%.1f", totalPass * 100.0 / total)).append("%'></div>\n");
            html.append("<div class='progress-fail' style='width:").append(String.format("%.1f", totalFail * 100.0 / total)).append("%'></div>\n");
            html.append("<div class='progress-skip' style='width:").append(String.format("%.1f", totalSkip * 100.0 / total)).append("%'></div>\n");
        }
        html.append("</div></div>\n");

        // Test case table
        html.append("<table>\n<thead><tr>\n");
        html.append("<th style='width:5%'>STT</th>\n");
        html.append("<th style='width:30%'>Tên Test Case</th>\n");
        html.append("<th style='width:35%'>Mô Tả</th>\n");
        html.append("<th style='width:12%'>Thời Gian</th>\n");
        html.append("<th style='width:18%'>Kết Quả</th>\n");
        html.append("</tr></thead>\n<tbody>\n");

        Map<String, String> descriptions = new LinkedHashMap<>();
        descriptions.put("testLoginSuccess", "Đăng nhập thành công với user/pass hợp lệ → chuyển sang /inventory.html");
        descriptions.put("testLoginWrongPassword", "Nhập sai mật khẩu → hiện thông báo lỗi");
        descriptions.put("testLoginEmptyUsername", "Bỏ trống username → hiện 'Username is required'");
        descriptions.put("testLoginEmptyPassword", "Bỏ trống password → hiện 'Password is required'");
        descriptions.put("testLoginLockedUser", "Dùng locked_out_user → hiện 'Sorry, this user has been locked out'");

        int stt = 0;
        for (ITestResult result : allResults) {
            stt++;
            String methodName = result.getMethod().getMethodName();
            long duration = result.getEndMillis() - result.getStartMillis();
            String durationStr = String.format("%.2f giây", duration / 1000.0);

            String status;
            String badgeClass;
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    status = "PASS ✓";
                    badgeClass = "badge-pass";
                    break;
                case ITestResult.FAILURE:
                    status = "FAIL ✗";
                    badgeClass = "badge-fail";
                    break;
                default:
                    status = "BỎ QUA";
                    badgeClass = "badge-skip";
                    break;
            }

            String desc = descriptions.getOrDefault(methodName, "");

            html.append("<tr>\n");
            html.append("<td style='text-align:center;font-weight:bold'>").append(stt).append("</td>\n");
            html.append("<td><strong>").append(methodName).append("</strong></td>\n");
            html.append("<td>").append(desc).append("</td>\n");
            html.append("<td style='text-align:center'>").append(durationStr).append("</td>\n");
            html.append("<td style='text-align:center'><span class='badge ").append(badgeClass).append("'>").append(status).append("</span></td>\n");
            html.append("</tr>\n");
        }

        html.append("</tbody>\n</table>\n");
        html.append("<div class='footer'>Sinh viên thực hiện — TestNG + Selenium WebDriver | Lab 8 - Tuần 8</div>\n");
        html.append("</div>\n</body>\n</html>");

        // Write to file
        String reportPath = outputDirectory + File.separator + "BaoCaoKiemThu.html";
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(reportPath), StandardCharsets.UTF_8))) {
            pw.write(html.toString());
            System.out.println(">> Báo cáo tiếng Việt: " + reportPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
