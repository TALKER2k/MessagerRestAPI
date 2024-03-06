package ru.relex.payload.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangeProfileRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String hobby;
    private String profession;
    private String city;
    private String country;
}
