package luvizzz.restAssuredTraining;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import restAssuredTraining.User;

public class TestReqResGET  extends Utils {
	private List<User> users;
	
	public TestReqResGET() {
		users = new ArrayList<User>();
		users.clear();
	}

	@BeforeClass
	public void setup() {
	}

	private void printListOfUsers(List<User> users) {
		for(User item : users) {
			System.out.println("---");
			item.printUser();
			System.out.println("---");
		}
	}

	@Test(groups= {"GET"})
	public void getUsersFromPage1() {
		setQueryParam("page", 1);
		expectResponseWithCode(200);
		getAllUsers();
	}

	@Test(groups= {"GET"})
	public void getUsersFromPage2() {
		setQueryParam("page", 2);
		expectResponseWithCode(200);
		getAllUsers();
	}

	@Test(groups= {"GET"})
	public void getUsersFromPage3() {
		setQueryParam("page", 3);
		expectResponseWithCode(200);
		getAllUsers();
	}

	/***
	 * This test populates the Global Variable users with all information fetched from the system.
	 * It uses a serialization (provided by jackson plugin) to input data retrieved from a JSONPath object into a List<User> variable
	 */
	@Test(groups = {"GET", "populatesGlobalVariables"})
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
	@Test(dependsOnGroups= {"populatesGlobalVariables"}, groups= {"GET"})
	public void validateUserID1() throws Exception{
		int id = 1;
		User expectedUser = new User();
		if(users.isEmpty()) {
			throw new EmptyStackException();
		}
		for(User item : users) {
			if(item.getId().equals(Integer.toString(id))) {
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
	@Test(dependsOnGroups= {"populatesGlobalVariables"}, groups= {"GET"})
	public void validateUserID5() throws Exception{
		int id = 5;
		User expectedUser = new User();
		if(users.size() == 0) {
			throw new EmptyStackException();
		}
		for(User item : users) {
			if(item.getId().equals(Integer.toString(id))) {
				expectedUser = item;
				break;
			}
		}
		compareUsers(expectedUser, getUserByIdWithExpectedResponseCode(id,200));
	}

	@Test(groups= {"GET", "negative"})
	public void getBadUser() {
		getUserByIdWithExpectedResponseCode(200,404);
	}
}
