package com.morlimoore.storedprocedures.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private long id;
    private String address;
    private String type;

    private User owner;
}
