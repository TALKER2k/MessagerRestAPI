package ru.relex.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private String username;
    private String message;
}
