package com.mail.sender.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic accountConfirmationTopic(@Value("${kafka.topic.names.account.confirmation}") String accountMailConfirmationTopicName) {
        return TopicBuilder
                .name(accountMailConfirmationTopicName)
                .build();
    }

    @Bean
    public NewTopic madeOrderNotificationTopic(@Value("${kafka.topic.names.order.made.notification}") String madeOrderMailNotificationTopicName) {
        return TopicBuilder
                .name(madeOrderMailNotificationTopicName)
                .build();
    }
}
