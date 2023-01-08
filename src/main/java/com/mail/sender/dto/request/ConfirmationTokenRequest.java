package com.mail.sender.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationTokenRequest {
    @NotBlank
    private String token;
    @Valid
    private AccountDetailsRequest accountDetails;
}
