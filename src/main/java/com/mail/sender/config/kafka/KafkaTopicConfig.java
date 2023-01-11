package com.mail.sender.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.topic.names.account.confirmation}")
    private String accountMailConfirmationTopicName;

    @Bean
    public NewTopic accountConfirmationTopic() {
        return TopicBuilder
                .name(accountMailConfirmationTopicName)
                .build();
    }
}
