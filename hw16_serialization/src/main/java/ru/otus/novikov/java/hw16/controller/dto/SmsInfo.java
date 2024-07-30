package ru.otus.novikov.java.hw16.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SmsInfo {
    @JsonProperty("belong_number")
    private String belongNumber;
    private List<Sms> smsList;
}
