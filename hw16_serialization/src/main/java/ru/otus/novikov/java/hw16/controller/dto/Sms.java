package ru.otus.novikov.java.hw16.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Sms {
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    private String last;
    @JsonProperty("send_date")
    private LocalDateTime sendDate;
    private String text;
}
