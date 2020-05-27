package io.springtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.ResponseCreators;
import io.specto.hoverfly.junit5.HoverflyExtension;
import io.springtest.topic.Topic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@ExtendWith(HoverflyExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestTopicController {
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String OUTPUT = "{\n"
            + "   \"$id\":\"1\",\n"
            + "   \"currentDateTime\":\"2019-03-12T10:54+01:00\",\n"
            + "   \"utcOffset\":\"01:00:00\",\n"
            + "   \"isDayLightSavingsTime\":false,\n"
            + "   \"dayOfTheWeek\":\"Tuesday\",\n"
            + "   \"timeZoneName\":\"Central Europe Standard Time\",\n"
            + "   \"currentFileTime\":131968616698822965,\n"
            + "   \"ordinalDate\":\"2019-71\",\n"
            + "   \"serviceResponse\":null\n"
            + "}";

    private static final String TOPIC = "{\n"
            + "\"id\": \"Java\",\n"
            + "\"name\": \"Java 8\",\n"
            + "\"description\": \"Good Topic\"\n"
            + " }";

    private static Topic topic = new Topic("Java2", "Java 28", "Good Topic2");

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testWorldClock(Hoverfly hoverfly) {
        // simulator
        hoverfly.simulate(SimulationSource.dsl(
                HoverflyDsl
                        .service("http://testservice.com")
                        .get("/api/test")
                        .willReturn(ResponseCreators
                                .success(OUTPUT, "application/json"))
        ));

        // integration
        ResponseEntity<String> res = restTemplate.getForEntity("http://testservice.com/api/test", String.class);

        // custom business logic
        System.out.println("Response : " + res.getBody());
    }


    @Test
    public void testTopicController(Hoverfly hoverfly) throws JsonProcessingException {
        hoverfly.simulate(SimulationSource.dsl(
                HoverflyDsl
                        .service("http://topicservice.com")
                        .get("/topic")
                        .willReturn(ResponseCreators
                                .success(TOPIC, "application/json"))
        ));

        ResponseEntity<String> res = restTemplate.getForEntity("http://topicservice.com/topic", String.class);

        Topic topic = mapper.readValue(res.getBody(), Topic.class);

        System.out.println(topic.getId());

        hoverfly.simulate(SimulationSource.dsl(
                HoverflyDsl
                        .service("http://course.com")
                        .post("/course")
                        .body(res.getBody())
                        .willReturn(ResponseCreators
                                .success(TOPIC, "application/json"))
        ));


        ResponseEntity<String> res2 = restTemplate.getForEntity("http://course.com/course", String.class);
        System.out.println(res2.getBody());
    }

}
