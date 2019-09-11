package utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import resources.SecurityEndpointResources;

public class Utils {
	Properties properties = new Properties();
	SecurityEndpointResources securityEndpointResources = new SecurityEndpointResources();
	MessageConstants Messages = new MessageConstants();

	public String getBaseURI() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//env.properties");
		properties.load(fis);
		String baseURIString = properties.getProperty("HOST");
		return baseURIString;
	}

	public String getServerStatusURI() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//env.properties");
		properties.load(fis);
		String serverStatusURIString = properties.getProperty("SERVER_STATUS");
		return serverStatusURIString;
	}

	public boolean serverIsrunning() throws IOException {
		RestAssured.baseURI = getServerStatusURI();
		String relatedResourceString = securityEndpointResources.ServerStatus();
		given().when().get(relatedResourceString).

				then().assertThat().statusCode(200).and().statusLine(Messages.HTTPSUCCESS_STRING).and()
				.contentType(ContentType.JSON).and().body("description", equalTo(Messages.STATUSDESCRIPTION_STRING))
				.and().body("data.gitHash", equalTo("59e9215f05c0a671d02b45c80b6ae4f9bf94f7a7")).and()
				.body("data.appName", equalTo("security")).and().body("data.gitBranch", equalTo("origin/Dev")).and()
				.time(lessThan(10000L)).and().

				header("Content-Type", "application/json;charset=UTF-8").extract().response();
		return true;
	}

	public Response commonLoginmethods(String userName, String Password) throws IOException {
		RestAssured.baseURI = getBaseURI();
		String usertimezoneString = properties.getProperty("USERTIMEZONE");
		String loginPayloadString = "{" + "\"password\" : \"" + Password + "\"," + "\"timeZone\" : \""
				+ usertimezoneString + "\"," + "\"userName\" : \"" + userName + "\"}";
		System.out.println(loginPayloadString);
		Response loginResponse = given().body(loginPayloadString).header("Content-Type", "application/json")

				.when().post(securityEndpointResources.AdminLogin()).then().extract().response();

		String actualReposnString = loginResponse.asString();
		System.out.println(actualReposnString);
		return loginResponse;
	}

}
