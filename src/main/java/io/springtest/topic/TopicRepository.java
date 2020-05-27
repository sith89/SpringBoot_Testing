package io.springtest.topic;

import org.springframework.data.repository.CrudRepository;

//1st generic:-  Entity class we are working with 2nd generic:- id of entity class
public interface TopicRepository extends CrudRepository<Topic, String> {

    //getAllTopics
    //getTopic(String id)
    //updateTopic
}
