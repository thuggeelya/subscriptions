package ru.thuggeelya.subscriptions.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vendor;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime offerValidityTime;

    @Enumerated(STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private Boolean isFree;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = User.class)
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private Long userId;

    public Subscription(final Long id, final String vendor, final LocalDateTime created,
                        final LocalDateTime offerValidityTime, final SubscriptionStatus status, final Boolean isFree,
                        final Long userId) {

        this.id = id;
        this.vendor = vendor;
        this.created = created;
        this.offerValidityTime = offerValidityTime;
        this.status = status;
        this.isFree = isFree;
        this.userId = userId;
    }

    @Override
    public final boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null) return false;
        final Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy)o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        final Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy)this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        final Subscription s = (Subscription)o;
        return getId() != null && Objects.equals(getId(), s.getId());
    }

    @Override
    public final int hashCode() {

        return this instanceof HibernateProxy
                ? ((HibernateProxy)this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
