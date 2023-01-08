package com.mail.sender.service.senders;

import com.mail.sender.dto.request.ConfirmationTokenRequest;
import com.mail.sender.exception.ModelValidationException;
import com.mail.sender.service.validator.ApplicationModelValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@Service
@Slf4j
@RequiredArgsConstructor
public class GmailConfirmationSenderService implements EmailSender<ConfirmationTokenRequest> {
    private final String accountConfirmationTemplate;
    private final ApplicationModelValidator applicationModelValidator;
    private final JavaMailSender mailSender;

    @Value("${confirmation.link.template}")
    private String confirmationLink;

    @KafkaListener(topics = "account_confirmation", groupId = "account_confirmation_group_id")
    public void confirmationMessageListener(@Valid ConfirmationTokenRequest confirmationTokenRequest) {
        String validationViolations = applicationModelValidator.validate(confirmationTokenRequest);
        if (!validationViolations.isBlank()) {
            log.error(validationViolations);
            throw new ModelValidationException(validationViolations);
        }

        String userConfirmationLink = String.format(
                accountConfirmationTemplate,
                confirmationTokenRequest.getAccountDetails().getUsername(),
                confirmationLink + confirmationTokenRequest.getToken());
        this.send(confirmationTokenRequest, userConfirmationLink);
    }

    @Override
    public void send(ConfirmationTokenRequest messageDetails, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(message, true);
            helper.setTo(messageDetails.getAccountDetails().getEmail());
            helper.setSubject("Account confirmation");
            helper.setFrom("asdasd.sender@gmail.com");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
