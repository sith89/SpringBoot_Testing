package io.springtest.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    //Topic service should connect to database and run commands.
    //create object of Courseservice and inject in to TopicRepository class
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(String topicId) {
        List<Course> courses = new ArrayList<>();
        courseRepository.findByTopicId(topicId)
        .forEach(courses::add);

        return courses;
    }

    public Course getCourse(String id) {
        return courseRepository.findById(id).get();

    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course);

    }

    public void deleteCourse(String topicId)
    {
        courseRepository.deleteById(topicId);
    }
}
