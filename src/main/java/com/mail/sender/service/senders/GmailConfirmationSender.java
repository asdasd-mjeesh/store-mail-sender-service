package com.mail.sender.service.senders;

import com.mail.sender.dto.request.account.AccountRequest;
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
public class GmailConfirmationSender implements EmailSender<AccountRequest> {
    private final ApplicationModelValidator applicationModelValidator;
    private final JavaMailSender mailSender;
    private final String accountConfirmationTemplate;

    @Value("${mail.template.confirmation.link}")
    private String confirmationLink;

    @KafkaListener(topics = "${kafka.topic.names.account.confirmation}", groupId = "account_confirmation_group_id",
            containerFactory = "mailConfirmationListenerContainerFactory")
    public void confirmationMessageListener(AccountRequest accountRequest) {
        String validationViolations = applicationModelValidator.validate(accountRequest);
        if (!validationViolations.isBlank()) {
            log.error(validationViolations);
            throw new ModelValidationException(validationViolations);
        }

        String userConfirmationLink = String.format(
                accountConfirmationTemplate,
                accountRequest.getUsername(),
                confirmationLink + accountRequest.getConfirmationTokenDetails().getToken());
        this.send(accountRequest, userConfirmationLink);
    }

    @Override
    public void send(AccountRequest account, String message) {
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
