package ru.thuggeelya.subscriptions.dto.system;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.thuggeelya.subscriptions.entity.Subscription}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {

    private Long id;

    @NotBlank(message = "Vendor is blank")
    private String vendor;

    private LocalDateTime created;

    @NotNull(message = "Offer validity time is null")
    private LocalDateTime offerValidityTime;

    private String status;

    private Long userId;

    private Boolean isFree;
}