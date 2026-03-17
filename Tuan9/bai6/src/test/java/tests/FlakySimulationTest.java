package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.base.BaseTest;

public class FlakySimulationTest extends BaseTest {

    private static int callCount = 0;

    @Test(description = "Mo phong test flakey: fail 2 lan dau, pass lan thu 3")
    public void flakyScenario() {
        callCount++;
        System.out.println("[FlakyTest] Dang chay lan thu: " + callCount);

        if (callCount <= 2) {
            Assert.fail("Mo phong loi mang tam thoi - lan " + callCount);
        }

        Assert.assertTrue(true, "Test pass o lan thu " + callCount);
    }
}
