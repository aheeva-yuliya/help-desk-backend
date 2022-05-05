package com.javamaster.mother;

import com.javamaster.dto.TicketRaw;

public class TicketRawMother {
    public static TicketRaw.TicketRawBuilder<?, ?> create() {
        return TicketRaw.builder();
    }
}