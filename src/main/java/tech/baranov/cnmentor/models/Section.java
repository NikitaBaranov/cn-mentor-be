package tech.baranov.cnmentor.models;

import lombok.Data;

import java.util.List;

@Data
public class Section {

    private Integer order;
    private String name;
    private List<Topic> topics;

}
