package ru.thuggeelya.subscriptions.dto.system;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubscriptionInfoDto {

    private String vendor;
    private Integer count;
}
