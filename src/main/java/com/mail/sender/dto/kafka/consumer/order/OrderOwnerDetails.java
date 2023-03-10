package com.mail.sender.dto.kafka.consumer.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOwnerDetails {

    @Email
    private String email;

    @Length(min = 8, max = 40)
    private String username;
}
