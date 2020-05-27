package io.springtest;

import io.springtest.course.Course;
import io.springtest.course.CourseRepository;
import io.springtest.course.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CourseApiServiceLayerTests {

	@Autowired
	private CourseService courseService;

	//add Mockito mocks in a Spring ApplicationContext
	@MockBean
	private CourseRepository courseRepository;

	@Test
	public void getAllCourseForTopicTest(){
		when(courseRepository.findByTopicId("Java")).thenReturn(Stream.of(new Course
				("Java Course", "Java is the best", "Java new course", "Java")).collect(Collectors.toList()));
		assertEquals(courseRepository.findByTopicId("Java Course"), courseService.getAllCourses("Java Course"));
	}

}
