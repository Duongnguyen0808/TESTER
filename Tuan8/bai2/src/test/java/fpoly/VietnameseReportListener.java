package fpoly;

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
        String suiteName = "";
        Set<Long> threadIds = new HashSet<>();

        for (ISuite suite : suites) {
            suiteName = suite.getName();
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

        allResults.sort(Comparator.comparing(r -> r.getMethod().getTestClass().getName() + r.getMethod().getMethodName()));

        for (ITestResult r : allResults) {
            threadIds.add(r.getStartMillis()); // dùng để phân biệt
        }

        int total = totalPass + totalFail + totalSkip;
        double passRate = total > 0 ? (totalPass * 100.0 / total) : 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String now = sdf.format(new Date());

        // Detect parallel info
        Set<Long> threads = new HashSet<>();
        for (ITestResult r : allResults) {
            // Check thread from method name pattern in output
            threads.add(Thread.currentThread().getId());
        }

        html.append("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n");
        html.append("<meta charset='UTF-8'>\n");
        html.append("<title>Bao Cao Kiem Thu - Bai 2</title>\n");
        html.append("<style>\n");
        html.append("* { margin: 0; padding: 0; box-sizing: border-box; }\n");
        html.append("body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f2f5; color: #333; padding: 30px; }\n");
        html.append(".container { max-width: 1100px; margin: 0 auto; }\n");
        html.append(".header { background: linear-gradient(135deg, #0d47a1, #1976d2); color: white; padding: 30px 40px; border-radius: 12px 12px 0 0; text-align: center; }\n");
        html.append(".header h1 { font-size: 24px; margin-bottom: 5px; letter-spacing: 1px; }\n");
        html.append(".header p { font-size: 13px; opacity: 0.9; }\n");
        html.append(".summary { display: flex; justify-content: space-around; background: white; padding: 25px; border-bottom: 1px solid #e0e0e0; }\n");
        html.append(".summary-item { text-align: center; }\n");
        html.append(".summary-item .number { font-size: 36px; font-weight: bold; }\n");
        html.append(".summary-item .label { font-size: 12px; color: #666; margin-top: 4px; text-transform: uppercase; }\n");
        html.append(".total .number { color: #1565c0; }\n");
        html.append(".pass .number { color: #2e7d32; }\n");
        html.append(".fail .number { color: #c62828; }\n");
        html.append(".skip .number { color: #f9a825; }\n");
        html.append(".rate .number { color: #1565c0; }\n");
        html.append(".progress-bar { background: white; padding: 15px 40px 20px; }\n");
        html.append(".progress-track { height: 10px; background: #e0e0e0; border-radius: 5px; overflow: hidden; display: flex; }\n");
        html.append(".progress-pass { background: #2e7d32; height: 100%; transition: width 0.5s; }\n");
        html.append(".progress-fail { background: #c62828; height: 100%; transition: width 0.5s; }\n");
        html.append(".progress-skip { background: #f9a825; height: 100%; transition: width 0.5s; }\n");
        html.append(".section-title { background: #e3f2fd; padding: 12px 20px; font-size: 15px; font-weight: 600; color: #1565c0; border-left: 4px solid #1565c0; margin: 0; }\n");
        html.append("table { width: 100%; border-collapse: collapse; background: white; }\n");
        html.append("th { background: #1565c0; color: white; padding: 12px 14px; text-align: left; font-size: 13px; }\n");
        html.append("td { padding: 10px 14px; border-bottom: 1px solid #eee; font-size: 13px; }\n");
        html.append("tr:nth-child(even) { background: #fafafa; }\n");
        html.append("tr:hover { background: #e3f2fd; }\n");
        html.append(".badge { display: inline-block; padding: 4px 14px; border-radius: 20px; font-size: 12px; font-weight: 600; }\n");
        html.append(".badge-pass { background: #e8f5e9; color: #2e7d32; }\n");
        html.append(".badge-fail { background: #ffebee; color: #c62828; }\n");
        html.append(".badge-skip { background: #fff8e1; color: #f9a825; }\n");
        html.append(".group-tag { display: inline-block; padding: 2px 8px; border-radius: 10px; font-size: 11px; margin: 1px 2px; }\n");
        html.append(".group-smoke { background: #e8f5e9; color: #2e7d32; }\n");
        html.append(".group-regression { background: #e3f2fd; color: #1565c0; }\n");
        html.append(".group-sanity { background: #fff3e0; color: #e65100; }\n");
        html.append(".class-name { color: #666; font-size: 11px; }\n");
        html.append(".footer { background: white; padding: 15px; text-align: center; border-radius: 0 0 12px 12px; border-top: 1px solid #e0e0e0; font-size: 12px; color: #999; }\n");
        html.append("</style>\n</head>\n<body>\n<div class='container'>\n");

        // Header
        html.append("<div class='header'>\n");
        html.append("<h1>BAO CAO KIEM THU - BAI 2</h1>\n");
        html.append("<p>TestNG Suites, Groups & Parallel Execution | Website: saucedemo.com</p>\n");
        html.append("<p style='margin-top:5px;font-size:12px;opacity:0.8'>Suite: ").append(suiteName).append(" | Thoi gian: ").append(now).append("</p>\n");
        html.append("</div>\n");

        // Summary
        html.append("<div class='summary'>\n");
        html.append("<div class='summary-item total'><div class='number'>").append(total).append("</div><div class='label'>Tong So Test</div></div>\n");
        html.append("<div class='summary-item pass'><div class='number'>").append(totalPass).append("</div><div class='label'>Thanh Cong</div></div>\n");
        html.append("<div class='summary-item fail'><div class='number'>").append(totalFail).append("</div><div class='label'>That Bai</div></div>\n");
        html.append("<div class='summary-item skip'><div class='number'>").append(totalSkip).append("</div><div class='label'>Bo Qua</div></div>\n");
        html.append("<div class='summary-item rate'><div class='number'>").append(String.format("%.0f%%", passRate)).append("</div><div class='label'>Ty Le Pass</div></div>\n");
        html.append("</div>\n");

        // Progress bar
        html.append("<div class='progress-bar'><div class='progress-track'>\n");
        if (total > 0) {
            html.append("<div class='progress-pass' style='width:").append(String.format("%.1f", totalPass * 100.0 / total)).append("%'></div>\n");
            html.append("<div class='progress-fail' style='width:").append(String.format("%.1f", totalFail * 100.0 / total)).append("%'></div>\n");
            html.append("<div class='progress-skip' style='width:").append(String.format("%.1f", totalSkip * 100.0 / total)).append("%'></div>\n");
        }
        html.append("</div></div>\n");

        // Group by class
        Map<String, List<ITestResult>> byClass = new LinkedHashMap<>();
        for (ITestResult r : allResults) {
            String cls = r.getMethod().getTestClass().getName();
            byClass.computeIfAbsent(cls, k -> new ArrayList<>()).add(r);
        }

        // Descriptions
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("testLoginSuccess", "Dang nhap thanh cong voi user/pass hop le, chuyen sang /inventory.html");
        descriptions.put("testLoginWrongPassword", "Nhap sai mat khau, kiem tra thong bao loi xuat hien");
        descriptions.put("testLoginLockedUser", "Dung locked_out_user, kiem tra thong bao 'Sorry, this user has been locked out'");
        descriptions.put("testAddToCart", "Them san pham vao gio hang, kiem tra badge hien thi so 1");
        descriptions.put("testRemoveFromCart", "Xoa san pham khoi gio hang, kiem tra badge bien mat");
        descriptions.put("testGoToCheckout", "Vao trang checkout tu gio hang, kiem tra URL checkout-step-one");
        descriptions.put("testCheckoutMissingInfo", "Nhan Continue khong dien thong tin, kiem tra loi 'First Name is required'");

        // Class name mapping
        Map<String, String> classLabels = new LinkedHashMap<>();
        classLabels.put("fpoly.LoginTest", "LoginTest - Kiem Thu Dang Nhap");
        classLabels.put("fpoly.cart.CartTest", "CartTest - Kiem Thu Gio Hang");
        classLabels.put("fpoly.CheckoutTest", "CheckoutTest - Kiem Thu Thanh Toan");

        int stt = 0;
        for (Map.Entry<String, List<ITestResult>> entry : byClass.entrySet()) {
            String className = entry.getKey();
            List<ITestResult> classResults = entry.getValue();

            String label = classLabels.getOrDefault(className, className);
            html.append("<div class='section-title'>").append(label).append("</div>\n");

            html.append("<table>\n<thead><tr>\n");
            html.append("<th style='width:5%'>STT</th>\n");
            html.append("<th style='width:22%'>Ten Test Case</th>\n");
            html.append("<th style='width:30%'>Mo Ta</th>\n");
            html.append("<th style='width:15%'>Groups</th>\n");
            html.append("<th style='width:10%'>Thoi Gian</th>\n");
            html.append("<th style='width:18%'>Ket Qua</th>\n");
            html.append("</tr></thead>\n<tbody>\n");

            for (ITestResult result : classResults) {
                stt++;
                String methodName = result.getMethod().getMethodName();
                long duration = result.getEndMillis() - result.getStartMillis();
                String durationStr = String.format("%.2f giay", duration / 1000.0);

                String status;
                String badgeClass;
                switch (result.getStatus()) {
                    case ITestResult.SUCCESS:
                        status = "PASS";
                        badgeClass = "badge-pass";
                        break;
                    case ITestResult.FAILURE:
                        status = "FAIL";
                        badgeClass = "badge-fail";
                        break;
                    default:
                        status = "SKIP";
                        badgeClass = "badge-skip";
                        break;
                }

                String desc = descriptions.getOrDefault(methodName, "");

                // Groups
                String[] groups = result.getMethod().getGroups();
                StringBuilder groupHtml = new StringBuilder();
                for (String g : groups) {
                    String gc = "group-regression";
                    if (g.equals("smoke")) gc = "group-smoke";
                    else if (g.equals("sanity")) gc = "group-sanity";
                    groupHtml.append("<span class='group-tag ").append(gc).append("'>").append(g).append("</span> ");
                }

                html.append("<tr>\n");
                html.append("<td style='text-align:center;font-weight:bold'>").append(stt).append("</td>\n");
                html.append("<td><strong>").append(methodName).append("</strong></td>\n");
                html.append("<td>").append(desc).append("</td>\n");
                html.append("<td>").append(groupHtml.toString()).append("</td>\n");
                html.append("<td style='text-align:center'>").append(durationStr).append("</td>\n");
                html.append("<td style='text-align:center'><span class='badge ").append(badgeClass).append("'>").append(status).append("</span></td>\n");
                html.append("</tr>\n");
            }

            html.append("</tbody>\n</table>\n");
        }

        html.append("<div class='footer'>TestNG + Selenium WebDriver + ThreadLocal | Lab 8 - Tuan 8 - Bai 2</div>\n");
        html.append("</div>\n</body>\n</html>");

        // Write to file with UTF-8
        String reportPath = outputDirectory + File.separator + "BaoCaoKiemThu.html";
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(reportPath), StandardCharsets.UTF_8))) {
            pw.write(html.toString());
            System.out.println(">> Bao cao: " + reportPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
