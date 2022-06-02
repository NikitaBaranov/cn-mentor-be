package tech.baranov.cnmentor.models;


import lombok.Data;

import java.util.List;

@Data
public class Progress {

    private Integer courseId;
    private List<Section> sections;

}
