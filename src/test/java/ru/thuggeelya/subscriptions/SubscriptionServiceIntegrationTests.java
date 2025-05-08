package ru.thuggeelya.subscriptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.thuggeelya.subscriptions.config.PostgresTestContainerConfig;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionDto;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionInfoDto;
import ru.thuggeelya.subscriptions.entity.Subscription;
import ru.thuggeelya.subscriptions.entity.User;
import ru.thuggeelya.subscriptions.repository.SubscriptionRepository;
import ru.thuggeelya.subscriptions.repository.UserRepository;
import ru.thuggeelya.subscriptions.service.impl.SubscriptionServiceImpl;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.CURRENT;
import static ru.thuggeelya.subscriptions.entity.SubscriptionStatus.INACTIVE;

@SpringBootTest
@Import(PostgresTestContainerConfig.class)
class SubscriptionServiceIntegrationTests extends PostgresTestContainerConfig {

    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createSubscription_ShouldCreateSubscriptionSuccessfully() {

        final User user = userRepository.save(getUser());

        final SubscriptionDto request = new SubscriptionDto(
                null, "Netflix", now(), now().plusDays(30), "CURRENT", user.getId(), false
        );

        final SubscriptionDto result = subscriptionService.createSubscription(request);

        assertThat(result).isNotNull();
        assertThat(result.getVendor()).isEqualTo("Netflix");
    }

    @Test
    void findSubscriptionsByUserId_ShouldReturnSubscriptions() {

        final User user = userRepository.save(getUser());

        subscriptionRepository.save(
                new Subscription(null, "Netflix", now(), now().plusDays(30), CURRENT, false, user.getId())
        );

        final List<SubscriptionDto> subscriptions = subscriptionService.findSubscriptionsByUserId(user.getId());

        assertThat(subscriptions).hasSize(1);
    }

    @Test
    void deleteSubscriptionById_ShouldDeleteSubscriptionSuccessfully() {

        final User user = userRepository.save(getUser());

        final Subscription subscription = subscriptionRepository.save(
                new Subscription(null, "Netflix", now(), now().plusDays(30), CURRENT, false, user.getId())
        );

        subscriptionService.deleteSubscriptionById(user.getId(), subscription.getId());

        assertThat(subscriptionRepository.existsById(subscription.getId())).isFalse();
    }

    @Test
    void changeStatus_ShouldChangeSubscriptionStatusSuccessfully() {

        final User user = userRepository.save(getUser());

        final Subscription subscription = subscriptionRepository.save(
                new Subscription(null, "Netflix", now(), now().plusDays(30), CURRENT, false, user.getId())
        );

        subscriptionService.changeStatus(subscription.getId(), INACTIVE);

        final Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(subscription.getId());

        assertThat(subscriptionOptional.isPresent()).isTrue();
        assertThat(subscriptionOptional.get().getStatus()).isEqualTo(INACTIVE);
    }

    @Test
    void findTopNSubscriptions_ShouldReturnTopN() {

        final User user = userRepository.save(getUser());

        subscriptionRepository.save(
                new Subscription(null, "Netflix", now(), now().plusDays(30), CURRENT, false, user.getId())
        );

        subscriptionRepository.save(
                new Subscription(null, "Amazon", now(), now().plusDays(30), CURRENT, false, user.getId())
        );

        final List<SubscriptionInfoDto> topSubscriptions = subscriptionService.findTopNSubscriptions(1);

        assertThat(topSubscriptions).hasSize(1);
    }

    private static User getUser() {
        return new User(null, "testuser", "password", "Doe", "John", "Patronymic", 25L, now());
    }
}
