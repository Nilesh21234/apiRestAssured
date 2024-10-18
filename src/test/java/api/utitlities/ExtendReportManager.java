package api.utitlities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtendReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;  // UI of the report
	   public ExtentReports extent; // populate common info on the report
	   public ExtentTest test; // creating test case entries in the report and update status of the test method

	   String repName;
	   
	   public void onStart(ITestContext testContext) 
	   {
		   String timestamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(0));
		   repName= "Test-Report-"+ timestamp + ".html";
		   sparkReporter = new ExtentSparkReporter(".\\Reports\\"+ repName);
		   
		   sparkReporter.config().setDocumentTitle("RestAssured API Automation Report");
		   sparkReporter.config().setReportName("User API Testing");
		   sparkReporter.config().setTheme(Theme.DARK);
		   
		   extent= new ExtentReports();
		   extent.attachReporter(sparkReporter);
		   extent.setSystemInfo("Application", "Pet Store Users API");
		   extent.setSystemInfo("Module", "pet Stores");
		   extent.setSystemInfo("Sub module", "Users");
		   extent.setSystemInfo("User Name", System.getProperty("user.name"));
		   extent.setSystemInfo("Environemnt", "QA");
		   
		   String OS= testContext.getCurrentXmlTest().getParameter("OS");
		   extent.setSystemInfo("Operating System", OS);
		   
		   String Browser=testContext.getCurrentXmlTest().getParameter("Browser");
		   extent.setSystemInfo("Browser", Browser);
		   
		   List<String> includeGroups= testContext.getCurrentXmlTest().getIncludedGroups();
		   if (!includeGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includeGroups.toString());
		}
		   
	   }
	   
	    public void onTestSuccess(ITestResult result)
	    {
	    	test=extent.createTest(result.getTestClass().getName());
	    	test.assignCategory(result.getMethod().getGroups());
	    	test.log(Status.PASS, result.getName()+"Got Successfully executed");
	    }
	    
	    public void onTestFailure(ITestResult result)
	    {
	    	test=extent.createTest(result.getTestClass().getName());
	    	test.assignCategory(result.getMethod().getGroups());
	    	test.createNode(result.getName());
	    	test.log(Status.FAIL, result.getName()+"Got failed");
	    	test.log(Status.INFO, result.getThrowable().getMessage());
	    	
	    }
	    
	    public void onTestSkipped(ITestResult result)
	    {
	    	test=extent.createTest(result.getTestClass().getName());
	    	test.assignCategory(result.getMethod().getGroups());
	    	
	    	test.log(Status.SKIP, result.getName()+"Got skipped");
	    	test.log(Status.INFO, result.getThrowable().getMessage());
	    }
	    
	    public void onFinish(ITestContext testContext) 
	    {
	    	extent.flush();
	    	 String pathOfExtentReport = System.getProperty("user.dir")+"\\Reports\\"+repName;
	    	 File extentreport= new File(pathOfExtentReport);
	    	 
	    	 try
	    	 {
	    		 Desktop.getDesktop().browse(extentreport.toURI());
	    	 }catch(IOException e)
	    	 {
	    		 e.printStackTrace();
	    	 }
	    }
}
