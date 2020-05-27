package io.springtest.course;


import io.springtest.topic.Topic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// with this annotation JPA know to create table called Topic with 3 columns
@Entity
public class Course {
    //primary key of the table
    @Id
    private String id;
    @NotEmpty(message = "User name is mandatory field")
    private String name;
    @Size(min = 3, message = "Description should have at lease 3 letters")
    private String description;


    //tell spring that this is foreign key on primary key for the topic table
    // 1 topic can have many courses.so this will be a many to 1 mapping
    @ManyToOne
    private Topic topic;

    public Course() {
    }

    public Course(String id, String name, String description, String topicId) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.topic = new Topic(topicId, "", "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
