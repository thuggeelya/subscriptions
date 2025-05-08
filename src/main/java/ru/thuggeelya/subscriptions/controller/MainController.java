package ru.thuggeelya.subscriptions.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionDto;
import ru.thuggeelya.subscriptions.dto.system.UserDto;
import ru.thuggeelya.subscriptions.dto.request.UserCreateDto;
import ru.thuggeelya.subscriptions.dto.request.UserUpdateDto;
import ru.thuggeelya.subscriptions.service.SubscriptionService;
import ru.thuggeelya.subscriptions.service.UserService;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MainController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") final Long id) {
        return ok(userService.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody final UserCreateDto dto) {
        return ok(userService.createUser(dto));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody final UserUpdateDto dto) {
        return ok(userService.updateUser(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") final Long id) {

        userService.deleteUserById(id);
        return noContent().build();
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable("id") final Long id) {
        return ok(subscriptionService.findSubscriptionsByUserId(id));
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<SubscriptionDto> createUser(@PathVariable("id") final Long id,
                                                      @Valid @RequestBody final SubscriptionDto dto) {

        dto.setUserId(id);
        return ok(subscriptionService.createSubscription(dto));
    }

    @DeleteMapping("/{id}/subscriptions/{subId}")
    public ResponseEntity<?> deleteUserSubscription(@PathVariable("id") final Long userId,
                                                    @PathVariable("subId") final Long subId) {

        subscriptionService.deleteSubscriptionById(userId, subId);
        return noContent().build();
    }
}
