package api.utilities;

import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.Data;

@Data
public class ExtentReportDetails {
    private final String reportFilePath;
    private final String documentTitle;

    public String getReportFilePath() {
        return reportFilePath;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public String getReportName() {
        return reportName;
    }

    public Theme getTheme() {
        return theme;
    }


    private final String reportName;

    private Theme theme;

    public ExtentReportDetails(String reportFilePath, String documentTitle, String reportName){
        this.reportFilePath = reportFilePath;
        this.documentTitle = documentTitle;
        this.reportName = reportName;

        System.out.println("Report created here: " + reportFilePath);
    }

}
