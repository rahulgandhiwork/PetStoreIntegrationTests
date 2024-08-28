package api;


import api.utilities.ExtentReportDetails;
import api.utilities.ExtentReportManager;
import org.testng.ITestListener;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;;

public class TestBase implements ITestListener {
    public static ExtentReportManager reportManager;

    @BeforeSuite
    public static void init() {
        String reportsRootDir = System.getProperty("user.dir") + "\\reports";
        ExtentReportDetails reportDetails = new ExtentReportDetails(reportsRootDir + "\\PetStoreApiAutomationReport",
                "Basic Extent Report", "Basic Report");
        reportManager = new ExtentReportManager(ExtentReportManager.ReportType.HTML, reportDetails);
    }
    @AfterSuite
    public static void cleanUp() {
        reportManager.clearTests();
    }
}
