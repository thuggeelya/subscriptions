package ru.thuggeelya.subscriptions.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionDto;
import ru.thuggeelya.subscriptions.entity.Subscription;
import ru.thuggeelya.subscriptions.entity.SubscriptionStatus;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.CURRENT;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.INACTIVE;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.PAYMENT_REQUIRED;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        builder = @Builder(disableBuilder = true)
)
public interface SubscriptionMapper {

    @Mapping(target = "status", ignore = true)
    SubscriptionDto toSubscriptionDto(final Subscription subscription);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    Subscription toSubscription(final SubscriptionDto dto);

    @AfterMapping
    default void finishMapping(@MappingTarget final SubscriptionDto dto, final Subscription subscription) {
        dto.setStatus(subscription.getStatus().name());
    }

    @AfterMapping
    default void finishMapping(@MappingTarget final Subscription subscription, final SubscriptionDto dto) {

        subscription.setStatus(
                ofNullable(SubscriptionStatus.getValue(dto.getStatus()))
                        .orElse(getStatus(dto.getOfferValidityTime(), dto.getIsFree()))
        );

        subscription.setCreated(now());
    }

    default SubscriptionStatus getStatus(final LocalDateTime validityTime, final boolean isFree) {

        if (validityTime.isAfter(now())) {
            return CURRENT;
        }

        if (isFree) {
            return INACTIVE;
        }

        return PAYMENT_REQUIRED;
    }
}
