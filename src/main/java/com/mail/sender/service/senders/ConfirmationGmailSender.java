package com.mail.sender.service.senders;

import com.mail.sender.dto.kafka.consumer.account.AccountConfirmation;
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
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConfirmationGmailSender implements EmailSender<AccountConfirmation> {
    private final ApplicationModelValidator applicationModelValidator;
    private final JavaMailSender mailSender;
    private final String accountConfirmationTemplate;

    @Value("${mail.template.confirmation.link}")
    private String confirmationLink;

    @KafkaListener(topics = "${kafka.topic.names.mail.account-confirmation-message}", groupId = "account_confirmation_group_id",
            containerFactory = "mailConfirmationListenerContainerFactory")
    public void confirmationMessageListener(AccountConfirmation accountConfirmation) {
        String validationViolations = applicationModelValidator.validate(accountConfirmation);
        if (!validationViolations.isBlank()) {
            log.error(validationViolations);
            throw new ModelValidationException(validationViolations);
        }

        String userConfirmationLink = String.format(
                accountConfirmationTemplate,
                accountConfirmation.getUsername(),
                confirmationLink + accountConfirmation.getConfirmationTokenDetails().getToken());
        this.send(accountConfirmation, userConfirmationLink);
    }

    @Override
    public void send(AccountConfirmation account, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(message, true);
            helper.setTo(account.getEmail());
            helper.setSubject("Account confirmation");
            helper.setFrom("asdasd.sender@gmail.com");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("sending mail confirmation was failed. Exception stack trace: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
