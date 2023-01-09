package com.mail.sender;

import com.mail.sender.dto.request.AccountRequest;
import com.mail.sender.service.senders.GmailConfirmationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class TestCntrllr {
    private final GmailConfirmationSenderService emailSender;

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody AccountRequest request) {
        emailSender.confirmationMessageListener(request);
        return ResponseEntity.ok("ok");
    }
}
