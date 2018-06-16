package luvizzz.restAssuredTraining;

import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;

public class TestReqRes {
	
	public TestReqRes() {
		RestAssured.baseURI = "https://reqres.in/";
	}
	
	@Test
	public void getUsers(){
		RestAssured
			.given()
				.queryParam("page",2)
			.when()
			 	.get("api/users")
			.then()
			 	.log().all();
	}

}
