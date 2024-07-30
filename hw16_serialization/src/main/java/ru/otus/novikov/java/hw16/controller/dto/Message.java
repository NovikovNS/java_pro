package ru.otus.novikov.java.hw16.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class Message {
    private String ROWID;
    private String attributedBody;
    @JsonProperty("belong_number")
    private String belongNumber;
    private String date;
    @JsonProperty("date_read")
    private String dateRead;
    private String guid;
    @JsonProperty("handle_id")
    private Long handleId;
    @JsonProperty("has_dd_results")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean hasDdResults;
    @JsonProperty("is_deleted")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean isDeleted;
    @JsonProperty("is_from_me")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean isFromMe;
    @JsonProperty("send_date")
    @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
    private LocalDateTime sendDate;
    @JsonProperty("send_status")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean sendStatus;
    private String service;
    private String text;
}
