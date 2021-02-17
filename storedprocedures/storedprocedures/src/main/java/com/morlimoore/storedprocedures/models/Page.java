package com.morlimoore.storedprocedures.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Page<T> {
    private Long count;
    private List<T> content;
}
