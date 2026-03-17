package framework.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import framework.config.ConfigReader;

public class RetryAnalyzer implements IRetryAnalyzer {

    private final int maxRetry = ConfigReader.getInstance().getRetryCount();
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetry) {
            retryCount++;
            System.out.println("[RetryAnalyzer] Retrying " + result.getMethod().getMethodName()
                + " (attempt " + retryCount + "/" + maxRetry + ")");
            return true;
        }
        return false;
    }
}
