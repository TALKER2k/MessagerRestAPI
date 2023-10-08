package ru.relex.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserInfoResponse {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private List<String> roles;

    public UserInfoResponse(Long id, String username, String firstname, String lastname, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
    }
}
