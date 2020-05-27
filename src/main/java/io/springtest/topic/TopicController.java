package io.springtest.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    // need to tell spring to inject the dependency of TopicService
    @Autowired
    private TopicService topicService;

    @RequestMapping("/topics")
    public List<Topic> getAllTopics(){
        return topicService.getAllTopics();
    }

    @RequestMapping("/topics/{id}")
    public Topic getTopic(@PathVariable("id") String topicId){

        return topicService.getTopic(topicId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics")
    public void addTopic(@RequestBody Topic topic){
        topicService.addTopic(topic);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{id}")
    public void updateTopic(@RequestBody Topic topic, @PathVariable("id") String topicId){
        topicService.updateTopic(topic, topicId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{id}")
    public void deleteTopic(@PathVariable("id") String topicId){
        topicService.deleteTopic(topicId);
    }

}
