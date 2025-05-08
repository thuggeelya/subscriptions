package ru.thuggeelya.subscriptions.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionDto;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionInfoDto;
import ru.thuggeelya.subscriptions.entity.Subscription;
import ru.thuggeelya.subscriptions.entity.SubscriptionStatus;
import ru.thuggeelya.subscriptions.exception.ClientException;
import ru.thuggeelya.subscriptions.mapper.SubscriptionMapper;
import ru.thuggeelya.subscriptions.repository.SubscriptionRepository;
import ru.thuggeelya.subscriptions.repository.UserRepository;
import ru.thuggeelya.subscriptions.repository.projection.SubscriptionCheckingProjection;
import ru.thuggeelya.subscriptions.repository.projection.SubscriptionInfoProjection;
import ru.thuggeelya.subscriptions.service.SubscriptionService;

import java.util.List;

import static java.time.LocalDateTime.now;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.INACTIVE;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.PAYMENT_REQUIRED;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public SubscriptionDto createSubscription(final SubscriptionDto dto) {

        log.info("createSubscription method was called");

        if (!userRepository.existsById(dto.getUserId())) {
            throw new ClientException("User not found with id " + dto.getUserId());
        }

        final Subscription subscription = subscriptionMapper.toSubscription(dto);
        subscription.setCreated(now());

        return subscriptionMapper.toSubscriptionDto(subscriptionRepository.save(subscription));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDto> findSubscriptionsByUserId(final Long userId) {

        log.info("findSubscriptionsByUserId method was called with userId = {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new ClientException("User not found with id " + userId);
        }

        return subscriptionRepository.findByUserIdOrderByOfferValidityTimeAsc(userId)
                                     .stream()
                                     .map(subscriptionMapper::toSubscriptionDto)
                                     .toList();
    }

    @Override
    @Transactional
    public void deleteSubscriptionById(final Long userId, final Long id) {

        log.info("deleteSubscriptionById method was called with id = {}, userId = {}", id, userId);

        if (!subscriptionRepository.existsById(id)) {
            throw new ClientException("Subscription with id " + id + " does not exist");
        }

        if (!subscriptionRepository.checkOwnership(id, userId)) {
            throw new ClientException("Subscription with id " + id + " does not belong to user with id " + userId);
        }

        subscriptionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionCheckingProjection> findOverdueSubscriptions() {
        return subscriptionRepository.findOverdueSubscriptions(List.of(INACTIVE, PAYMENT_REQUIRED));
    }

    @Override
    @Transactional
    public void changeStatus(final Long id, final SubscriptionStatus status) {

        log.info("changeStatus method was called with id = {}, status = {}", id, status);

        subscriptionRepository.changeStatusById(id, status.name());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionInfoDto> findTopNSubscriptions(final int n) {

        log.info("findTop{}Subscriptions method was called", n);

        return subscriptionRepository.findTopNVendors(n)
                .stream()
                .map(SubscriptionServiceImpl::mapToSubscriptionInfoDto)
                .toList();
    }

    private static SubscriptionInfoDto mapToSubscriptionInfoDto(final SubscriptionInfoProjection projection) {
        return SubscriptionInfoDto.builder().vendor(projection.getVendor()).count(projection.getCount()).build();
    }
}
