package ru.thuggeelya.subscriptions.entity;

import java.util.Arrays;

import static java.util.Optional.ofNullable;

public enum SubscriptionStatus {

    // просроченная
    OUT_OF_VALIDITY_DATE,
    // действующая, но не продленная после истечения текущего периода
    CURRENT_CANCELLED,
    // действующая/продленная
    CURRENT,
    // недействительная
    INACTIVE,
    // оформленная, но в ожидании оплаты
    PAYMENT_REQUIRED,
    // оформленная, оплаченная, в обработке
    PAYMENT_IN_PROCESS;

    public static SubscriptionStatus getValue(final String status) {

        return ofNullable(status).flatMap(s -> Arrays.stream(values())
                                                     .filter(v -> v.name().equals(s.toUpperCase()))
                                                     .findFirst())
                                 .orElse(null);
    }
}
