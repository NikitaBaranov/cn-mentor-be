package tech.baranov.cnmentor.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.baranov.cnmentor.properties.GitHubProperties;
import tech.baranov.cnmentor.properties.MongoProperties;
import tech.baranov.cnmentor.properties.MoodleProperties;

@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "cnmentor.moodle")
    public MoodleProperties moodleProperties() {
        return new MoodleProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "cnmentor.mongo")
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "cnmentor.gh")
    public GitHubProperties gitHubProperties() {
        return new GitHubProperties();
    }

}
