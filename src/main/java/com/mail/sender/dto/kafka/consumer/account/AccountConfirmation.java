package com.mail.sender.dto.kafka.consumer.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfirmation {

    @Email
    private String email;

    @Length(min = 8, max = 40)
    private String username;

    @NotNull
    @Valid
    private ConfirmationTokenDetails confirmationTokenDetails;
}
