package io.springtest;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import io.springtest.course.CourseService;
import io.springtest.topic.Topic;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.json;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CourseAPITControllerTest {
    static String body = "{\n" +
            "\t\"id\": \"Java\",\n" +
            "    \"name\": \"Java 8\",\n" +
            "    \"description\": \"Good Topic\"\n" +
            " }";

    private static Topic topic = new Topic("Java","Java 8","Good Topic");

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(SimulationSource.dsl(
            service("http://localhost:8080")
                    .post("/topics").body(body)
                    .willReturn(success(String.valueOf(json(topic)), "application/json"))
    ));

    @Test
    public void getAllCourseForTopicTest(){

        String requestBody = "{\n" +
                "    \"id\": \"Java Course1\",\n" +
                "    \"name\": \"fgjhfgjhfg\",\n" +
                "    \"description\": \"Jan\"\n" +
                "}";

                 RestAssuredMockMvc.given()
                .standaloneSetup(new CourseService()).contentType("application/json")
                .body(requestBody)
                .when()
                .post("/topics/{topicId}/courses", topic.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo("Java Course1"))
                .body("name", equalTo("fgjhfgjhfg"))
                .body("description", equalTo("Jan"));

    }



}
