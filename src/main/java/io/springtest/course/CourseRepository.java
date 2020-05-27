package io.springtest.course;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

//1st generic:-  Entity class we are working with 2nd generic:- id of entity class
public interface CourseRepository extends CrudRepository<Course, String> {

    // get topic id and return list of courses
    // for this method name there is a structure. method name starts with "find" , "By", filter by "Name"
    //public List<Course> findByName(String name);

    //here referring to a property not a String but an Object at that field Id in method name
    //let spring data JPA know that we are looking for the "Id" property of "Topic" property
    public List<Course> findByTopicId(String topicId);
}
