package com.mail.sender.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsRequest {
    @Email
    private String email;

    @Length(min = 8, max = 40)
    private String username;

    @PastOrPresent
    private LocalDateTime createdAt;

    @Future
    private LocalDateTime expiredAt;
}
