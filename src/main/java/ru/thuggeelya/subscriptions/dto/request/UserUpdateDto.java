package ru.thuggeelya.subscriptions.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserUpdateDto extends UserCreateDto {

    private Long id;
    private LocalDateTime created;

    public UserUpdateDto(final Long id, final String username, final String password, final String lastname,
                         final String name, final String patronymic, final long age, final LocalDateTime created) {

        super(username, password, lastname, name, patronymic, age);

        this.id = id;
        this.created = created;
    }
}
