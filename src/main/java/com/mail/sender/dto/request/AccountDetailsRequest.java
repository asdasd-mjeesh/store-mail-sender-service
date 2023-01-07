package com.mail.sender.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsRequest {
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
}
