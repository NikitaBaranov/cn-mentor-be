package tech.baranov.cnmentor.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "courses")
public class Course {

    @Id
    private Integer id;

    private String name;

}
