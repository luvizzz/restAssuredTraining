package luvizzz.restAssuredTraining;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import restAssuredTraining.User;

public class TestReqResPOST extends Utils {

	private List<User> users;
	
	public TestReqResPOST() {
		users = new ArrayList<User>();
		users.clear();
	}

	@Test(groups= {"POST"})
	public void testCreatingUserSerialization() {
		//setRequest(request);
		
		setBody(createUserPayload("5", "Test", "Luvizzz", "github.com"));

		expectResponseWithCode(201);
		postUser();
	}
	
	@Test(groups= {"POST"})
	public void testCreatingUserAsString() {
		setBody("{"
				+ "\"id\":\"5\","
				+ "\"first_name\":\"Test2\","
				+ "\"last_name\":\"Luvizzz\","
				+ "\"avatar\":\"www.github.com\""
				+ "}");

		expectResponseWithCode(201);
		postUser();
	}
	
	@Test(groups= {"POST", "negative"})
	public void testCreatingUserWithBadPayload() {		
		setBody("");

		expectResponseWithCode(400);
		postUser();
	}

}
