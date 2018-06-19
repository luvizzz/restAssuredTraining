package luvizzz.restAssuredTraining;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.*;
import restAssuredTraining.User;

public abstract class Utils {

	RequestSpecification request;

	public Utils() {
		RestAssured.baseURI = "https://reqres.in/";
		request = RestAssured.given().contentType("application/json\r\n");
	}
	
	public void setHeaders(Headers headers) {
		request = RestAssured.given().headers(headers).log().all();
	}

	public void setPathParam(String param, Object value) {
		request = RestAssured.given().pathParam(param, value).log().all();
	}

	public void setQueryParam(String param, Object value) {
		request = RestAssured.given().params(param, value).log().all();
	}

	public void setBody(Object payload) {
		request = RestAssured.given().body(payload).log().all();
	}

	public JsonPath postUser() {
		return request.when().post("api/users/").then().log().all().extract().jsonPath();
	}

	public Object createUserPayload(String id, String firstName, String lastName, String avatar) {
		User body = new User();
		body.setId(id);
		body.setFirst_name(firstName);
		body.setLast_name(lastName);
		body.setAvatar(avatar);
		return body;
	}

	public void expectResponseWithCode(int responseCode) {
		request.expect().statusCode(responseCode);
	}


	public void expectResponseWithContentType(ContentType contentType) {
		request.expect().contentType(contentType);
	}

	public JsonPath getAllUsers() {
		return request.when().get("api/users/").then().log().all().extract().jsonPath();
	}

	public JsonPath getUser() {
		return request.when().get("api/users/{id}").then().log().all().extract().jsonPath();
	}

	public void compareUsers(User expectedUser, User userById) {
		Assert.assertEquals(expectedUser.getId(), userById.getId());
		Assert.assertEquals(expectedUser.getFirst_name(), userById.getFirst_name());
		Assert.assertEquals(expectedUser.getLast_name(), userById.getLast_name());
		Assert.assertEquals(expectedUser.getAvatar(), userById.getAvatar());
	}

	public User getUserByIdWithExpectedResponseCode(int Id, int expectedResponseCode) {
		setPathParam("id", Id);
		expectResponseWithCode(expectedResponseCode);
		return getUser().getObject("data", User.class);
	}
}
