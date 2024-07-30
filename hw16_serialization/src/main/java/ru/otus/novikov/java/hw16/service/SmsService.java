package ru.otus.novikov.java.hw16.service;

import ru.otus.novikov.java.hw16.controller.dto.SessionInfo;
import ru.otus.novikov.java.hw16.controller.dto.SmsInfo;

import java.util.List;

public interface SmsService {
    List<SmsInfo> getSmsInfo(SessionInfo chatSession);
}
