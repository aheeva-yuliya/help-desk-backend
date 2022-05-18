package com.javamaster.utils.mothers;

import com.javamaster.dto.TicketRaw;

public class TicketRawMother {
    public static TicketRaw.TicketRawBuilder<?, ?> create() {
        return TicketRaw.builder();
    }
}