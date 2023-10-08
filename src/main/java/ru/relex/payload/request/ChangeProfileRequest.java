package ru.relex.payload.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeProfileRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
}
