package ru.relex.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MessageShowRequest {
    @NotBlank
    private String username;

    public MessageShowRequest() {}

    public MessageShowRequest(String username) {
        this.username = username;
    }
}
