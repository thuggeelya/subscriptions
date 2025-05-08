-- liquibase formatted sql

--changeset ilya:1

CREATE TABLE users
(
    id         bigserial primary key,
    age        bigint,
    created    timestamp(6) without time zone NOT NULL,
    lastname   character varying(255)         NOT NULL,
    name       character varying(255)         NOT NULL,
    password   character varying(255)         NOT NULL,
    patronymic character varying(255),
    username   character varying(255)         NOT NULL UNIQUE
);

CREATE TABLE subscriptions
(
    id                  bigserial primary key,
    created             timestamp(6) without time zone NOT NULL,
    is_free             boolean                        NOT NULL,
    offer_validity_time timestamp(6) without time zone NOT NULL,
    status              character varying(255)         NOT NULL,
    user_id             bigint                         NOT NULL,
    vendor              character varying(255)         NOT NULL,
    CONSTRAINT subscriptions_status_check CHECK (
        ((status)::text = ANY
         ((ARRAY ['OUT_OF_VALIDITY_DATE'::character varying, 'CURRENT_CANCELLED'::character varying, 'CURRENT'::character varying, 'INACTIVE'::character varying, 'PAYMENT_REQUIRED'::character varying, 'PAYMENT_IN_PROCESS'::character varying])::text[]))
        ),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
