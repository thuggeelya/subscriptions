package ru.thuggeelya.subscriptions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.thuggeelya.subscriptions.entity.Subscription;
import ru.thuggeelya.subscriptions.entity.SubscriptionStatus;
import ru.thuggeelya.subscriptions.repository.projection.SubscriptionCheckingProjection;
import ru.thuggeelya.subscriptions.repository.projection.SubscriptionInfoProjection;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserIdOrderByOfferValidityTimeAsc(final Long userId);

    @Modifying
    @Query(
            value = "update subscriptions set status = :status where id = :id",
            nativeQuery = true
    )
    void changeStatusById(@Param("id") final Long id, @Param("status") final String status);

    @Query("select s.vendor as vendor, count(s) as count from Subscription s group by s.vendor order by count desc limit :n")
    List<SubscriptionInfoProjection> findTopNVendors(@Param("n") final int n);

    @Query("select (count(s) > 0) from Subscription s where s.id = :id and s.userId = :userId")
    boolean checkOwnership(@Param("id") final Long id, @Param("userId") final Long userId);

    @Query("select s.id as id, s.isFree as isFree from Subscription s where s.offerValidityTime <= current_timestamp " +
           "and s.status not in :statusesToExclude")
    List<SubscriptionCheckingProjection> findOverdueSubscriptions(final List<SubscriptionStatus> statusesToExclude);
}