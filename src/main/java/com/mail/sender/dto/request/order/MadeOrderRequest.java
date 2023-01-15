package com.mail.sender.dto.request.order;

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
public class MadeOrderRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private OrderOwnerDetailsRequest orderOwner;

    @NotNull
    @Min(10)
    private BigDecimal totalPrice;

    @PastOrPresent
    private LocalDateTime orderedAt;
}
