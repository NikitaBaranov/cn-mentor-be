package tech.baranov.cnmentor.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;
import tech.baranov.cnmentor.properties.GitHubProperties;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final GitHubProperties gitHubProperties;

    @Bean
    public RestTemplate gitHubRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(gitHubProperties.getUsername(), gitHubProperties.getPat()));
        return restTemplate;
    }

}
