package com.mail.sender.service;

import com.mail.sender.dto.request.ConfirmationTokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class GmailConfirmationSenderService implements EmailSender<ConfirmationTokenRequest> {
    private final JavaMailSender mailSender;
    private final String confirmationLink = "http://localhost:8083/api/v1/accounts/confirm?token=";

    private final String accountConfirmationTemplate;

    @KafkaListener(topics = "account_confirmation", groupId = "account_confirmation_group_id")
    public void confirmationMessageListener(ConfirmationTokenRequest confirmationTokenRequest) {
        String userConfirmationLink = String.format(accountConfirmationTemplate, confirmationTokenRequest.getEmail(),
                confirmationLink + confirmationTokenRequest.getToken().getToken());
        this.send(confirmationTokenRequest, userConfirmationLink);
    }

    @Override
    public void send(ConfirmationTokenRequest messageDetails, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(message, true);
            helper.setTo(messageDetails.getEmail());
            helper.setSubject("Account confirmation");
            helper.setFrom("asdasd.sender@gmail.com");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("asd");
            throw new RuntimeException(e);
        }
    }
}
