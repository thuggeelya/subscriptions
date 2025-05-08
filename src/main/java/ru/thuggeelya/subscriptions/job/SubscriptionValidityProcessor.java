package ru.thuggeelya.subscriptions.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.thuggeelya.subscriptions.entity.SubscriptionStatus;
import ru.thuggeelya.subscriptions.repository.projection.SubscriptionCheckingProjection;
import ru.thuggeelya.subscriptions.service.SubscriptionService;

import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.INACTIVE;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.PAYMENT_REQUIRED;

@Slf4j
@Service
@RequiredArgsConstructor
class SubscriptionValidityProcessor {

    private final SubscriptionService subscriptionService;

    @Async
    void processOverdue(final SubscriptionCheckingProjection subscription) {

        log.info("Subscription with ID = {} is overdue", subscription.getId());

        final SubscriptionStatus newStatus = subscription.getIsFree() ? INACTIVE : PAYMENT_REQUIRED;

        subscriptionService.changeStatus(subscription.getId(), newStatus);
    }
}
