package ru.otus.novikov.java.hw16.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.novikov.java.hw16.controller.dto.SessionInfo;
import ru.otus.novikov.java.hw16.controller.dto.SmsInfo;
import ru.otus.novikov.java.hw16.service.SmsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sms")
@AllArgsConstructor
public class SmsController {
    private final SmsService smsService;

    @PostMapping()
    public ResponseEntity<List<SmsInfo>> getSmsInfo(@RequestBody SessionInfo sessionInfo,
        @RequestHeader(HttpHeaders.ACCEPT) String accept) {
        return ResponseEntity.ok(smsService.getSmsInfo(sessionInfo));
    }
}
