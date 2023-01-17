package com.mail.sender.dto.kafka.consumer.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MadeOrder {

    @NotNull
    private Long orderId;

    @NotNull
    private OrderOwnerDetails orderOwner;

    @NotNull
    @Min(10)
    private BigDecimal totalPrice;

    @PastOrPresent
    private LocalDateTime orderedAt;
}
