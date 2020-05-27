package io.springtest;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import io.restassured.response.Response;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import io.springtest.course.Course;
import io.springtest.course.CourseController;
import io.springtest.course.CourseRepository;
import io.springtest.course.CourseService;
import io.springtest.topic.Topic;
import io.springtest.topic.TopicRepository;
import io.springtest.topic.TopicService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.json;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

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
