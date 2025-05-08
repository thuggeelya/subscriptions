package ru.thuggeelya.subscriptions.service;

import ru.thuggeelya.subscriptions.dto.system.SubscriptionDto;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionInfoDto;
import ru.thuggeelya.subscriptions.entity.SubscriptionStatus;
import ru.thuggeelya.subscriptions.repository.projection.SubscriptionCheckingProjection;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDto createSubscription(final SubscriptionDto dto);

    List<SubscriptionDto> findSubscriptionsByUserId(final Long userId);

    void deleteSubscriptionById(final Long userId, final Long id);

    List<SubscriptionCheckingProjection> findOverdueSubscriptions();

    void changeStatus(final Long id, final SubscriptionStatus status);

    List<SubscriptionInfoDto> findTopNSubscriptions(final int n);
}
