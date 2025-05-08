package ru.thuggeelya.subscriptions.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserCreateDto {

    @NotBlank(message = "Username is blank")
    private String username;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,15}$",
            message = "Password should contain latin letters and digits and have length between 4 and 15 (inclusive)"
    )
    private String password;

    @NotBlank(message = "Lastname is blank")
    private String lastname;

    @NotBlank(message = "Name is blank")
    private String name;

    private String patronymic;

    @Min(value = 16, message = "Age is lower than 16")
    private Long age;
}
