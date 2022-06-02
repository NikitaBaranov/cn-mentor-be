package tech.baranov.cnmentor.models;

import lombok.Data;
import tech.baranov.cnmentor.enums.TopicType;

import java.time.LocalDateTime;

@Data
public class Topic {

    private Integer order;
    private String topic;
    private TopicType type;
    private Integer imgId;
    private String comment;
    private LocalDateTime date;

}
