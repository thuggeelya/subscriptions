package ru.thuggeelya.subscriptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.thuggeelya.subscriptions.config.PostgresTestContainerConfig;
import ru.thuggeelya.subscriptions.dto.request.UserCreateDto;
import ru.thuggeelya.subscriptions.dto.request.UserUpdateDto;
import ru.thuggeelya.subscriptions.dto.system.UserDto;
import ru.thuggeelya.subscriptions.entity.User;
import ru.thuggeelya.subscriptions.exception.ClientException;
import ru.thuggeelya.subscriptions.repository.UserRepository;
import ru.thuggeelya.subscriptions.service.impl.UserServiceImpl;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(PostgresTestContainerConfig.class)
public class UserServiceIntegrationTests {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_ShouldCreateUserSuccessfully() {

        final UserCreateDto request = new UserCreateDto("testuser", "Test1234", "Doe", "John", "Patronymic", 25L);
        final UserDto userDto = userService.createUser(request);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getUsername()).isEqualTo("testuser");
    }

    @Test
    void createUser_ShouldThrowException_WhenUsernameExists() {

        userRepository.save(new User(null, "testuser", "Test1234", "Doe", "John", "Patronymic", 25L, now()));

        final UserCreateDto request = new UserCreateDto("testuser", "Test1234", "Doe", "John", "Patronymic", 25L);

        assertThrows(ClientException.class, () -> userService.createUser(request));
    }

    @Test
    void updateUser_ShouldUpdateUserSuccessfully() {

        final User user = userRepository.save(
                new User(null, "testuser", "Test1234", "Doe", "John", "Patronymic", 25L, now())
        );

        final UserUpdateDto request = new UserUpdateDto(
                user.getId(), "updateduser", "Updated123", "Smith", "Jane", "Middle", 30L, now()
        );

        final UserDto userDto = userService.updateUser(request);

        assertThat(userDto.getUsername()).isEqualTo("updateduser");
    }

    @Test
    void findUserById_ShouldReturnUser_WhenUserExists() {

        final User user = userRepository.save(
                new User(null, "testuser", "Test1234", "Doe", "John", "Patronymic", 25L, now())
        );

        final UserDto userDto = userService.findUserById(user.getId());

        assertThat(userDto).isNotNull();
        assertThat(userDto.getUsername()).isEqualTo("testuser");
    }

    @Test
    void deleteUserById_ShouldDeleteUserSuccessfully() {

        final User user = userRepository.save(
                new User(null, "testuser", "Test1234", "Doe", "John", "Patronymic", 25L, now())
        );

        userService.deleteUserById(user.getId());

        assertThat(userRepository.existsById(user.getId())).isFalse();
    }
}