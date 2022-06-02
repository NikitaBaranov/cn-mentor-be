package tech.baranov.cnmentor.configs;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import tech.baranov.cnmentor.properties.MongoProperties;

@Configuration
public class MongoConfig {

    public @Bean
    MongoClient mongoClient(MongoProperties mongoProperties) {
        return MongoClients.create(mongoProperties.getUrl());
    }

    public @Bean
    MongoTemplate mongoTemplate(MongoProperties mongoProperties) {
        return new MongoTemplate(mongoClient(mongoProperties), "cn");
    }

}
