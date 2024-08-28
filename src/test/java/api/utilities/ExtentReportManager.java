package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestListener;

import java.util.HashMap;
import java.util.Map;

public class ExtentReportManager implements ITestListener {
    private ExtentHtmlReporter extentHtmlReporter;
    private ExtentReports report;
    private Map<String, ExtentTest> tests = new HashMap<String, ExtentTest>();

    public ExtentReportManager(ReportType reportType, ExtentReportDetails reportDetails){
        report = new ExtentReports();
        attachReport(reportType, reportDetails);
    }

    private void attachReport(ReportType reportType, ExtentReportDetails reportDetails) {
        switch (reportType){
            case HTML:
                report.attachReporter(getHtmlReporter(reportDetails));
        }
    }

    public ExtentTest getTest(String testName){
        return tests.get(testName);
    }

    public ExtentTest setUpTest(String testName){
        ExtentTest test = report.createTest(testName);
        tests.put(testName,test);
        return tests.get(testName);
    }

    private ExtentHtmlReporter getHtmlReporter(ExtentReportDetails reportDetails) {
        String filePath = reportDetails.getReportFilePath() + ".html";
        extentHtmlReporter = new ExtentHtmlReporter(filePath);
        // make the charts visible on report open
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setDocumentTitle(reportDetails.getDocumentTitle());
        extentHtmlReporter.config().setReportName(reportDetails.getReportName());
        extentHtmlReporter.config().setTheme(reportDetails.getTheme());
        return extentHtmlReporter;
    }

    public void clearTests(){
        tests.clear();
        report.flush();
    }

    public enum ReportType{
        HTML,
    }
}
