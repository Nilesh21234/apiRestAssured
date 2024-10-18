package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.FileReader;
import java.util.Properties;
import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
// Created  for perform Create, Read,Update,Delete request the user API
public class UserEndPointsUsingProperties
{

	static ResourceBundle getURL()
	{
		ResourceBundle routes=ResourceBundle.getBundle("routes");
		return routes;
	}
	public static Response createUser(User payload)
	{
		// Reading data from properties file
		String Post_url=getURL().getString("Post_url");
		
         Response response=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(payload)
		
		.when()
		  .post(Post_url);
		
		return response;
	}
	
	public static Response readUser(String  username)
	{
		// Reading data from properties file
		String get_url=getURL().getString("get_url");
		
         Response response=given()
        	              .pathParam("username", username)
		
		.when()
		  .get(get_url);
		
		return response;
	}
	
	public static Response updateUser(User payload, String username)
	{
		// Reading data from properties file
				String update_url=getURL().getString("update_url");
				
         Response response=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.pathParam("username", username)
		.body(payload)
		
		.when()
		  .put(update_url);
		
		return response;
	}
	
	public static Response deleteUser(String  username)
	{
		// Reading data from properties file
		String delete_url=getURL().getString("delete_url");
		
         Response response=given()
        	              .pathParam("username", username)
		
		.when()
		  .delete(delete_url);
		
		return response;
	}
}
