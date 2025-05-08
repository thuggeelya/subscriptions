package ru.thuggeelya.subscriptions.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.thuggeelya.subscriptions.entity.User}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String lastname;
    private String name;
    private String patronymic;
    private Long age;
    private LocalDateTime created;
}