package tech.baranov.cnmentor.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GitHubRepo {
    private Integer id;

    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("private")
    private boolean isPrivate;
    @JsonProperty("default_branch")
    private String defaultBranch;

    private String url;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("events_url")
    private String eventsUrl;
    @JsonProperty("ssh_url")
    private String sshUrl;


    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    @JsonProperty("pushed_at")
    private LocalDateTime pushedAt;

}
