package ru.thuggeelya.subscriptions.service;

import ru.thuggeelya.subscriptions.dto.system.UserDto;
import ru.thuggeelya.subscriptions.dto.request.UserCreateDto;
import ru.thuggeelya.subscriptions.dto.request.UserUpdateDto;

public interface UserService {

    UserDto createUser(final UserCreateDto request);

    UserDto updateUser(final UserUpdateDto request);

    UserDto findUserById(final Long id);

    void deleteUserById(final Long id);
}
