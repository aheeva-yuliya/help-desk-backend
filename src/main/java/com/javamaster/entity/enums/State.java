package com.javamaster.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum State {
    DRAFT("DRAFT"), NEW("NEW"), APPROVED("APPROVED"), DECLINED("DECLINED"),
    PROGRESS("IN PROGRESS"), DONE("DONE"), CANCELED("CANCELED");
    private String value;
}
