package com.morlimoore.restassured.controllers;

import com.morlimoore.restassured.entities.User;
import com.morlimoore.restassured.services.UserService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class UserControllerTest {

    private static UserService userService;
    private RequestSpecification spec;

    @Autowired
    public UserControllerTest(UserService userService) {
        this.userService = userService;
    }

    RequestSpecification request = given();
    private static final String CONTEXT_PATH = "/api/v1/users";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        Map<String, String> user = new HashMap<>(){{
            put("firstName", "adekunle");
            put("lastName", "ciroma");
            put("email", "adekunleciroma@gmail.com");
        }};
        given()
                .contentType("application/json")
                .body(user)
                .when().post(CONTEXT_PATH + "/add");
    }

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
    }

    @AfterAll
    public static void teardown() {
        userService.deleteUserByEmail("adekunleciroma@gmail.com");
    }

    @BeforeEach
    public void init() {
        request.header("Content-Type", "application/json");
    }

    @Test
    @DisplayName("Application is running")
    public void basicPingTest() {
        given()
                .accept("application/json")
                .when().get(CONTEXT_PATH + "/all")
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Can fetch all users")
    public void getAllUsersTest() {
        given(this.spec)
                .accept("application/json")
                .filter(document("fetching-all-users", responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("The response status"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("The response message"),
                        fieldWithPath("error").type(JsonFieldType.VARIES).description("The response error"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("The response timestamp"),
                        fieldWithPath("debugMessage").type(JsonFieldType.VARIES).description("The response debugMessage"),
                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("The user's id"),
                        fieldWithPath("data[].firstName").type(JsonFieldType.STRING).description("The user's firstName"),
                        fieldWithPath("data[].lastName").type(JsonFieldType.STRING).description("The user's lastName"),
                        fieldWithPath("data[].email").type(JsonFieldType.STRING).description("The user's email")
                )))
                .when().get(CONTEXT_PATH + "/all")
                .then().statusCode(200)
                .and()
                .body(containsString("id"));
    }

    @Test
    @DisplayName("Can fetch a user")
    public void getUserTest() {
        User user = userService.getUserByEmail("adekunleciroma@gmail.com");
        given(this.spec)
                .accept("application/json")
                .filter(document("fetching-a-user", responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("The response status"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("The response message"),
                        fieldWithPath("error").type(JsonFieldType.VARIES).description("The response error"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("The response timestamp"),
                        fieldWithPath("debugMessage").type(JsonFieldType.VARIES).description("The response debugMessage"),
                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("The user's id"),
                        fieldWithPath("data[].firstName").type(JsonFieldType.STRING).description("The user's firstName"),
                        fieldWithPath("data[].lastName").type(JsonFieldType.STRING).description("The user's lastName"),
                        fieldWithPath("data[].email").type(JsonFieldType.STRING).description("The user's email")
                )))
                .when().get(CONTEXT_PATH + "/" + user.getId())
                .then().statusCode(200)
                .and()
                .body(containsString("id"));
    }

    @Test
    @DisplayName("Can add user")
    public void addUserTest() {
        Map<String, String> user = new HashMap<>(){{
            put("firstName", "john");
            put("lastName", "nwaefi");
            put("email", "johnNwaefi@gmail.com");
        }};
        given(this.spec)
                .contentType("application/json")
                .body(user)
                .filter(document("adding-a-user", responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("The response status"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("The response message"),
                        fieldWithPath("error").type(JsonFieldType.NULL).description("The response error"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("The response timestamp"),
                        fieldWithPath("debugMessage").type(JsonFieldType.NULL).description("The response debugMessage"),
                        fieldWithPath("data").type(JsonFieldType.NULL).description("The response data")
                )))
                .when().post(CONTEXT_PATH + "/add")
                .then().statusCode(201)
                .and()
                .body("status", equalTo("CREATED"));
        userService.deleteUserByEmail("johnNwaefi@gmail.com");
    }

    @Test
    @DisplayName("Can delete a user")
    public void deleteUserTest() {
        Map<String, String> user = new HashMap<>(){{
            put("firstName", "john");
            put("lastName", "nwaefi");
            put("email", "johnNwaefi@gmail.com");
        }};
        given(this.spec)
                .contentType("application/json")
                .body(user)
                .when().post(CONTEXT_PATH + "/add");
        User temp = userService.getUserByEmail("johnNwaefi@gmail.com");
        given(this.spec)
                .contentType("application/json")
                .filter(document("deleting-a-user", responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("The response status"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("The response message"),
                        fieldWithPath("error").type(JsonFieldType.NULL).description("The response error"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("The response timestamp"),
                        fieldWithPath("debugMessage").type(JsonFieldType.NULL).description("The response debugMessage"),
                        fieldWithPath("data").type(JsonFieldType.NULL).description("The response data")
                )))
                .when().delete(CONTEXT_PATH + "/delete/" + temp.getId())
                .then().statusCode(200)
                .and()
                .body("message", containsString("deleted"));
    }

    @Test
    @DisplayName("Can update a user")
    public void updateUserTest() {
        User temp = userService.getUserByEmail("adekunleciroma@gmail.com");
        Map<String, String> user = new HashMap<>(){{
            put("firstName", "adekunle");
            put("lastName", "chukwuma");
            put("email", "adekunleciroma@gmail.com");
        }};

        given(this.spec)
                .contentType("application/json")
                .body(user)
                .filter(document("updating-a-user", responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("The response status"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("The response message"),
                        fieldWithPath("error").type(JsonFieldType.NULL).description("The response error"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("The response timestamp"),
                        fieldWithPath("debugMessage").type(JsonFieldType.NULL).description("The response debugMessage"),
                        fieldWithPath("data").type(JsonFieldType.NULL).description("The response data")
                )))
                .when().put(CONTEXT_PATH + "/update/" + temp.getId())
                .then().statusCode(200)
                .and()
                .body("message", containsString("updated"));
    }
}
