package ru.thuggeelya.subscriptions.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.thuggeelya.subscriptions.service.SubscriptionService;

@Component
@RequiredArgsConstructor
public class SubscriptionValidityCheckingJob {

    private final SubscriptionService subscriptionService;
    private final SubscriptionValidityProcessor subscriptionValidityProcessor;

    @Scheduled(cron = "0 * * * * ?")
    public void checkSubscriptions() {
        subscriptionService.findOverdueSubscriptions().forEach(subscriptionValidityProcessor::processOverdue);
    }
}
