package com.BasePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class ApiTest {

    public static void main(String[] args) {
        // JSON Path matching example
        RestAssured.given()
            .when()
            .get("https://jsonplaceholder.typicode.com/posts/1")
            .then()
            .body("title", RestAssuredMatchers.matchesJsonPath("$[0].title"))  // Using RestAssuredMatchers for JSON Path
            .body("id", Matchers.equalTo(1));  // Hamcrest matcher for equality
    }
}
