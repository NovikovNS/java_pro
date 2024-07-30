package ru.otus.novikov.java.hw16.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.novikov.java.hw16.controller.dto.Message;
import ru.otus.novikov.java.hw16.controller.dto.SessionInfo;
import ru.otus.novikov.java.hw16.controller.dto.Sms;
import ru.otus.novikov.java.hw16.controller.dto.SmsInfo;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@Service
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Override
    public List<SmsInfo> getSmsInfo(SessionInfo sessionInfo) {
        return sessionInfo.getChatSessions().stream()
            .flatMap(chatSession -> chatSession.getMessages().stream()
                .collect(groupingBy(Message::getBelongNumber))
                .entrySet().stream()
                .map(entry -> SmsInfo.builder()
                    .belongNumber(entry.getKey())
                    .smsList(entry.getValue().stream()
                        .map(message -> Sms.builder()
                            .chatIdentifier(chatSession.getChatIdentifier())
                            .last(chatSession.getMembers().stream().findFirst().orElseThrow().getLast())
                            .sendDate(message.getSendDate())
                            .text(message.getText())
                            .build()).sorted(Comparator.comparing(Sms::getSendDate))
                        .toList()).build()))
            .toList();
    }
}
