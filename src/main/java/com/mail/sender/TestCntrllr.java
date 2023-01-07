package com.mail.sender;

import com.mail.sender.dto.request.ConfirmationTokenRequest;
import com.mail.sender.service.ConfirmationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class TestCntrllr {
    private final ConfirmationSenderService emailSender;

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody ConfirmationTokenRequest request) {
        emailSender.send(request, "test");
        return ResponseEntity.ok("ok");
    }
}
