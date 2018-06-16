package luvizzz.restAssuredTraining;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import restAssuredTraining.User;

public class TestReqRes {

	private RequestSpecification request;
	private List<User> users;
	
	public TestReqRes() {
		RestAssured.baseURI = "https://reqres.in/";
		users = new ArrayList<User>();
		users.clear();
	}

	@BeforeClass
	public void setup() {
	}

	public void setPathParam(String param, Object value) {
		request = RestAssured
				.given()
					.pathParam(param, value)
					//.log().all()
					;
	}
	
	public void setQueryParam(String param, Object value) {
		request = RestAssured
				.given()
					.params(param, value)
					//.log().all()
					;
	}
	
	public void expectResponseWithCode(int responseCode) {
			request
			 	.expect()
			 		.statusCode(responseCode);
	}

	public JsonPath getAllUsers() {
		return
			request
				.when()
				 	.get("api/users/")
				 .then()
				 	//.log().all()
				 	.extract().jsonPath()
				 	;
	}

	public JsonPath getUser() {
		return
			request
				.when()
				 	.get("api/users/{id}")
				 .then()
				 	//.log().all()
				 	.extract().jsonPath()
				 	;
	}

	private void printListOfUsers(List<User> users) {
		for(User item : users) {
			System.out.println("---");
			item.printUser();
			System.out.println("---");
		}
	}

	private void compareUsers(User expectedUser, User userById) {
		Assert.assertEquals(expectedUser.getId(), userById.getId());
		Assert.assertEquals(expectedUser.getFirst_name(), userById.getFirst_name());
		Assert.assertEquals(expectedUser.getLast_name(), userById.getLast_name());
		Assert.assertEquals(expectedUser.getAvatar(), userById.getAvatar());
	}

	private User getUserByIdWithExpectedResponseCode(int Id, int expectedResponseCode) {
		setPathParam("id", Id);
		expectResponseWithCode(expectedResponseCode);
		return getUser().getObject("data", User.class);
	}

	@Test
	public void getUsersFromPage1() {
		setQueryParam("page", 1);
		expectResponseWithCode(200);
		getAllUsers();
	}

	@Test
	public void getUsersFromPage2() {
		setQueryParam("page", 2);
		expectResponseWithCode(200);
		getAllUsers();
	}

	@Test
	public void getUsersFromPage3() {
		setQueryParam("page", 3);
		expectResponseWithCode(200);
		getAllUsers();
	}

	/***
	 * This test populates the Global Variable users with all information fetched from the system.
	 * It uses a serialization (provided by jackson plugin) to input data retrieved from a JSONPath object into a List<User> variable
	 */
	@Test(groups = {"populatesGlobalVariables"})
	public void populateAllUsers() {
		//Runs first a GET operation to fetch "total" information: the total amount of users present in the system
		setQueryParam("page", 1);
		expectResponseWithCode(200);
		JsonPath response = getAllUsers();
		int totalUsers;
		totalUsers = (Integer) response.getJsonObject("total");
		
		//Runs a GET operation with all users present
		setQueryParam("per_page", totalUsers);
		expectResponseWithCode(200);
		JsonPath responseWithAllUsers = getAllUsers();
		users = responseWithAllUsers.getList("data", User.class); //commenting this line will break 2 test cases (validateUserID1 and validateUserID5)
		
		//printListOfUsers(users);
	}
	
	/***
	 * This test will use the Global Variable users to fetch specific information for the user with ID=1
	 * Depends on this list (users) to be already populated.
	 * The test method populateAllUsers updates this variable.
	 */
	@Test(dependsOnGroups= {"populatesGlobalVariables"})
	public void validateUserID1() throws Exception{
		int id = 1;
		User expectedUser = new User();
		if(users.isEmpty()) {
			throw new EmptyStackException();
		}
		for(User item : users) {
			if(item.getId() == id) {
				expectedUser = item;
				break;
			}
		}
		compareUsers(expectedUser, getUserByIdWithExpectedResponseCode(id,200));
	}
	
	/***
	 * This test will use the Global Variable users to fetch specific information for the user with ID=5
	 * Depends on this list (users) to be already populated.
	 * The test method populateAllUsers updates this variable.
	 */
	@Test(dependsOnGroups= {"populatesGlobalVariables"})
	public void validateUserID5() throws Exception{
		int id = 5;
		User expectedUser = new User();
		if(users.size() == 0) {
			throw new EmptyStackException();
		}
		for(User item : users) {
			if(item.getId() == id) {
				expectedUser = item;
				break;
			}
		}
		compareUsers(expectedUser, getUserByIdWithExpectedResponseCode(id,200));
	}

	@Test()
	public void getBadUser() {
		getUserByIdWithExpectedResponseCode(200,404);
	}
}
