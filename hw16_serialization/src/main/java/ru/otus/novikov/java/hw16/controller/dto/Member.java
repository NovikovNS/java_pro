package ru.otus.novikov.java.hw16.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Member {
    private String first;
    @JsonProperty("handle_id")
    private Long handleId;
    @JsonProperty("image_path")
    private String imagePath;
    private String last;
    private String middle;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String service;
    @JsonProperty("thumb_path")
    private String thumbPath;
}
