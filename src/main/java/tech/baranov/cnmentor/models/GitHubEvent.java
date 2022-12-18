package tech.baranov.cnmentor.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "github_events")
public class GitHubEvent {

    @Id
    private String id;

    private String type;

    private GitHubEventActor actor;
    private GitHubEventRepo repo;
    private Map<String, Object> payload;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}
