package com.morlimoore.apidemo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "First name cannot be blank")
    private String lastName;

    @Email(message = "Email must be a valid email address")
    private String email;
}