package com.javamaster.utils.mothers;

import com.javamaster.entities.History;

public class HistoryMother {
    public static History.HistoryBuilder<?, ?> create() {
        return History.builder()
                .action("File is attached")
                .owner(UserMother.create().id(1000).build());
    }
}
