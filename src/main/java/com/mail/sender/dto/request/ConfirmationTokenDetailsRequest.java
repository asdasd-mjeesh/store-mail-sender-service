package com.mail.sender.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationTokenDetailsRequest {
    @NotBlank
    private String token;

    @PastOrPresent
    private LocalDateTime createdAt;

    @Future
    private LocalDateTime expiredAt;
}
