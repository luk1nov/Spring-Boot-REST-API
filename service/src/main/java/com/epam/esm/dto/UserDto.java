package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
