package api.utitlities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// Data provider 1
	@DataProvider(name="UserDataRestAssured")
   public String[][] getData() throws IOException
   {
	  String path= ".\\testData\\UserDataRestAssured.xlsx";
	  XLUtility xutil= new XLUtility(path);
	  int totalrows=xutil.getRowCount("Sheet1");
	  int totalcols=xutil.getCellCount("Sheet1",1);
	  
	  String apidata[][]= new String[totalrows][totalcols];
	  for (int i = 1; i <= totalrows; i++)
	  {
		  for (int j = 0; j < totalcols; j++)
		  {
			  apidata[i-1][j]=xutil.getCellData("Sheet1", i, j);
		  }
	  }
	  return apidata;
   }
	
	// Data provider 2
		@DataProvider(name="UserNames")
		 public String[] getUserNames() throws IOException
		   {
			  String path= ".\\testData\\UserDataRestAssured.xlsx";
			  XLUtility xutil= new XLUtility(path);
			  int totalrows=xutil.getRowCount("Sheet1");
			  
			  String apidata[] = new String[totalrows];
			  for (int i = 1; i <= totalrows; i++)
			  {
				apidata[i-1]=xutil.getCellData("Sheet1", i, 1);
				  
			  }
			  return apidata;
		   }
}
