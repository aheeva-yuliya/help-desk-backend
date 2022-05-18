package com.javamaster.utils.mothers;

import com.javamaster.entities.Category;
import com.javamaster.entities.Ticket;

public class TicketMother {
    public static Ticket.TicketBuilder<?, ?> create() {
        return Ticket.builder()
                .name("name")
                .category(Category.builder().id(1).category("Application & Services").build())
                .owner(UserMother.create().build());
    }
}
