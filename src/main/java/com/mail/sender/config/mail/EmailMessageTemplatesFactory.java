package com.mail.sender.config.mail;

import com.mail.sender.util.FileReaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class EmailMessageTemplatesFactory {
    private final FileReaderUtil fileReaderUtil;

    @Bean
    @SneakyThrows
    public String accountConfirmationTemplate() {
        var resource = new ClassPathResource("email_message_templates/confirmation");
        var path = resource.getFile().toPath();
        return fileReaderUtil.read(path);
    }

    @Bean
    @SneakyThrows
    public String orderNotificationTemplate() {
        var resource = new ClassPathResource("email_message_templates/madeOrderNotification");
        var path = resource.getFile().toPath();
        return fileReaderUtil.read(path);
    }
}
