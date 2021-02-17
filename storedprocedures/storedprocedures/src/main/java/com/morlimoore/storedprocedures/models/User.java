package com.morlimoore.storedprocedures.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends AbstractModel {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
