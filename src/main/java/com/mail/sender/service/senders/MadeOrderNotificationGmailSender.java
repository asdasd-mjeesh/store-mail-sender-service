package com.mail.sender.service.senders;

import com.mail.sender.dto.kafka.consumer.order.MadeOrder;
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
public class MadeOrderNotificationGmailSender implements EmailSender<MadeOrder> {
    private final ApplicationModelValidator applicationModelValidator;
    private final JavaMailSender mailSender;
    private final String orderNotificationTemplate;

    @Value("${mail.template.made.order.uri}")
    private String orderUri;

    @KafkaListener(topics = "${kafka.topic.names.order.made.notification}", groupId = "account_confirmation_group_id",
            containerFactory = "orderNotificationsListenerContainerFactory")
    public void madeOrdersListener(MadeOrder madeOrder) {
        var validationViolations = applicationModelValidator.validate(madeOrder);
        if (validationViolations.isBlank()) {
            log.error(validationViolations);
            throw new ModelValidationException(validationViolations);
        }

        String userOrderNotification = String.format(
                orderNotificationTemplate,
                madeOrder.getOrderOwner().getUsername(),
                madeOrder.getTotalPrice(),
                madeOrder.getOrderedAt(),
                orderUri + madeOrder.getOrderId());
        this.send(madeOrder, userOrderNotification);
    }

    @Override
    public void send(MadeOrder messageDetails, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(message, true);
            helper.setTo(messageDetails.getOrderOwner().getEmail());
            helper.setSubject("Order notification");
            helper.setFrom("asdasd.sender@gmail.com");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("sending order notification was failed. Exception stack trace: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
