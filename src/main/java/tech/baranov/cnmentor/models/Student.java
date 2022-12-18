package tech.baranov.cnmentor.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "students")
public class Student {

    @Id
    private final Integer id;

    private final String name;

    private final String gutHubUserName;
    private GitHubUser gitHubUser;
    private List<GitHubRepo> gitHubRepos;

    private final LocalDateTime start;
    private final LocalDateTime end;

    private Map<Integer, Progress> coursesProgress;

}
