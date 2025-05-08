package ru.thuggeelya.subscriptions.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thuggeelya.subscriptions.dto.system.UserDto;
import ru.thuggeelya.subscriptions.dto.request.UserCreateDto;
import ru.thuggeelya.subscriptions.dto.request.UserUpdateDto;
import ru.thuggeelya.subscriptions.entity.User;
import ru.thuggeelya.subscriptions.exception.ClientException;
import ru.thuggeelya.subscriptions.mapper.UserMapper;
import ru.thuggeelya.subscriptions.repository.UserRepository;
import ru.thuggeelya.subscriptions.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(final UserCreateDto request) {

        final String username = request.getUsername();

        log.info("createUser method was called: {}", username);

        if (userRepository.existsByUsername(username)) {
            throw new ClientException("User with username " + username + " exist");
        }

        final User user = userRepository.save(userMapper.toUser(request));

        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(final UserUpdateDto request) {

        log.info("updateUser method was called");

        if (!userRepository.existsById(request.getId())) {
            throw new ClientException("User with id " + request.getId() + " does not exist");
        }

        final User user = userRepository.save(userMapper.toUser(request));

        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserById(final Long id) {

        log.info("findUserById method was called with id = {}", id);

        final User user = userRepository.findById(id).orElseThrow(
                () -> new ClientException("User with id " + id + " does not exist")
        );

        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUserById(final Long id) {

        log.info("deleteUserById method was called with id = {}", id);

        if (!userRepository.existsById(id)) {
            throw new ClientException("User with id " + id + " does not exist");
        }

        userRepository.deleteById(id);
    }
}
