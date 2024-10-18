package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utitlities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTest {

	User userpayload= new User();
	
	@Test(priority=1, dataProvider="UserDataRestAssured", dataProviderClass=DataProviders.class)
	public void testPostUser(String userId,String username,String firstname,String lastname,String email,String password, String phone)
	{
		userpayload.setId(Integer.parseInt(userId));
		userpayload.setUsername(username);
		userpayload.setFirstName(firstname);
		userpayload.setLastName(lastname);
		userpayload.setEmail(email);
		userpayload.setPassword(password);
		userpayload.setPhone(phone);
		
		 Response response=UserEndPoints.createUser(userpayload);
		 response.then().log().all();
		 
		 Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=2,dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String username)
	{
		Response response=UserEndPoints.deleteUser(username);
		 Assert.assertEquals(response.getStatusCode(), 200);
	}
}
