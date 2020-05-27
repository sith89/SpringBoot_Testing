package io.springtest.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    //Topic service should connect to database and run commands.
    //create object of TopicService and inject in to TopicRepository class
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {

        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll()
        .forEach(topics::add);

        return topics;
    }

    public Topic getTopic(String id) {
        return topicRepository.findById(id).get();

    }

    public void addTopic(Topic topic) {

        //topics.add(topic);
        topicRepository.save(topic);
    }

    public void updateTopic(Topic topic, String topicId) {
        topicRepository.save(topic);

    }

    public void deleteTopic(String topicId)
    {
        topicRepository.deleteById(topicId);
    }
}
