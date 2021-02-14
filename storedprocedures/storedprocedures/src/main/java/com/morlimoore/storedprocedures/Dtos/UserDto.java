package com.morlimoore.storedprocedures.Dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDto {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Age cannot be blank")
    private int age;
}
