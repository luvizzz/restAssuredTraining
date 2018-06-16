package luvizzz.restAssuredTraining;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import restAssuredTraining.User;

public class TestReqRes {

	private RequestSpecification request;
	
	public TestReqRes() {
		RestAssured.baseURI = "https://reqres.in/";
	}

	@BeforeClass
	public void setup() {
	}

	public void setQueryParam(String param, Object value) {
		System.out.println("Test Setting QueryParam");
		request = RestAssured
				.given()
					.params(param, value)
					.log().all();
	}
	
	public void expectResponseWithCode(int responseCode) {
			request
			 	.expect()
			 		.statusCode(responseCode);
	}

	public JsonPath getUsers() {
		System.out.println("Test Getting Users"); 
		return
			request
				.when()
				 	.get("api/users")
				 .then()
				 	.log().all()
				 	.extract().jsonPath();
	}

	private void printListOfUsers(List<User> users) {
		for(User item : users) {
			System.out.println("---");
			item.printUser();
			System.out.println("---");
		}
	}

	@Test
	public void getUsersFromPage1() {
		setQueryParam("page", 1);
		expectResponseWithCode(200);
		getUsers();
	}

	@Test
	public void getUsersFromPage2() {
		setQueryParam("page", 2);
		expectResponseWithCode(200);
		getUsers();
	}

	@Test
	public void getUsersFromPage3() {
		setQueryParam("page", 3);
		expectResponseWithCode(200);
		getUsers();
	}

	@Test
	public void populateAllUsers() {
		//Runs first a GET operation to fetch "total" information: the total amount of users present in the system
		setQueryParam("page", 1);
		expectResponseWithCode(200);
		JsonPath response = getUsers();
		int totalUsers;
		totalUsers = (Integer) response.getJsonObject("total");
		
		System.out.println("total amount of users: " + totalUsers);
		
		//Runs a GET operation with all users present
		setQueryParam("per_page", totalUsers);
		expectResponseWithCode(200);
		JsonPath responseWithAllUsers = getUsers();
		List<User> users = responseWithAllUsers.getList("data", User.class);
		
		printListOfUsers(users);
	}

}
