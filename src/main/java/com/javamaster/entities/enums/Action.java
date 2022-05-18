package com.javamaster.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Action {
    SUBMIT("SUBMIT"), APPROVE("APPROVE"), DECLINE("DECLINE"),
    CANCEL("CANCEL"), ASSIGN("ASSIGN TO ME"), DONE("DONE");
    private String value;
}
