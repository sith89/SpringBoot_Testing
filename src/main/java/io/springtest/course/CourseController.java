package io.springtest.course;

import io.springtest.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CourseController {

    // need to tell spring to inject the dependency of Courseservice
    @Autowired
    private CourseService courseService;

    @RequestMapping("/topics/{id}/courses")
    public List<Course> getAllCourses(@PathVariable("id") String topicId){
        return courseService.getAllCourses(topicId);
    }

    @RequestMapping("/topics/{topicId}/courses/{courseId}")
    public Course getCourse(@PathVariable("courseId") String courseId){

        return courseService.getCourse(courseId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics/{topicId}/courses")
    public void addCourse(@Valid @RequestBody Course course, @PathVariable("topicId") String topicId){
        course.setTopic(new Topic(topicId, "", ""));
        courseService.addCourse(course);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{topicId}/courses/{courseId}")
    public void updateCourse(@RequestBody Course course, @PathVariable String topicId, @PathVariable String courseId){
        course.setTopic(new Topic(topicId, "", ""));
        courseService.updateCourse(course);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{topicId}/courses/{courseId}")
    public void deleteCourse( @PathVariable String courseId){
        courseService.deleteCourse(courseId);
    }

}
