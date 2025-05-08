package ru.thuggeelya.subscriptions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.thuggeelya.subscriptions.dto.system.SubscriptionInfoDto;
import ru.thuggeelya.subscriptions.service.SubscriptionService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<SubscriptionInfoDto>> findTopNSubscriptions(
            @RequestParam(name = "limit", required = false, defaultValue = "3") int n
    ) {

        return ok(subscriptionService.findTopNSubscriptions(n));
    }
}
