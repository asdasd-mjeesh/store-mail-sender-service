package com.mail.sender.config;

import com.mail.sender.util.FileReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class EmailMessageTemplatesConfig {
    private final FileReaderUtil fileReaderUtil;

    @Bean
    public String accountConfirmationTemplate() {
        try {
            var resource = new ClassPathResource("email_message_templates/confirmation");
            var path = resource.getFile().toPath();
            return fileReaderUtil.read(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
