package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker= new Faker();
	User userpayload= new User();
	public Logger logger;
	@BeforeClass
	public void setupData()
	{
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("-------------- Creating New User----------------");
		 Response response=UserEndPoints.createUser(userpayload);
		 response.then().log().all();
		 
		 Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("-------------- Getting User by Name ----------------");
		Response response=UserEndPoints.readUser(this.userpayload.getUsername());
		 response.then().log().all();
		 Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void testUpdateUser()
	{
		logger.info("-------------- Updating Existing User----------------");
		// update data using same payload
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		
		 Response response=UserEndPoints.updateUser(userpayload,this.userpayload.getUsername());
		 response.then().log().all();
		 response.then().log().body().statusCode(200);
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 // checking data after update
			Response responseafterIpdate=UserEndPoints.readUser(this.userpayload.getUsername());
			 Assert.assertEquals(responseafterIpdate.getStatusCode(), 200);
	}
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("-------------- Deleting User----------------");
		Response response=UserEndPoints.deleteUser(this.userpayload.getUsername());
		 Assert.assertEquals(response.getStatusCode(), 200);
	}
}
