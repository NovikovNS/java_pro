package ru.otus.novikov.java.hw16.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class ChatSession {
    @JsonProperty("chat_id")
    private Long chatId;
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("is_deleted")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean isDeleted;
    @JsonProperty("members")
    private List<Member> members;
    @JsonProperty("messages")
    private List<Message> messages;
}
