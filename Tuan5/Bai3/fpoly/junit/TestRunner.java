package fpoly.junit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JunitAnnotationsExample.class);
        
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        
        System.out.println("\n========================================");
        System.out.println("Test Result Summary:");
        System.out.println("========================================");
        System.out.println("Total tests run: " + result.getRunCount());
        System.out.println("Tests passed: " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("Tests failed: " + result.getFailureCount());
        System.out.println("Tests ignored: " + result.getIgnoreCount());
        System.out.println("Success: " + result.wasSuccessful());
        System.out.println("========================================");
    }
}
