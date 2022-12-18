package tech.baranov.cnmentor.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubEventActor {
    private Integer id;

    private String login;
    @JsonProperty("display_login")
    private String displayLogin;

}
