package luvizzz.restAssuredTraining;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import restAssuredTraining.User;

public class TestReqResPOST {

	private RequestSpecification request;
	private List<User> users;
	
	public TestReqResPOST() {
		RestAssured.baseURI = "https://reqres.in/";
		users = new ArrayList<User>();
		users.clear();
	}

}
