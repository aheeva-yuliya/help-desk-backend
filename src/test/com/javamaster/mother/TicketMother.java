package com.javamaster.mother;

import com.javamaster.entity.Category;
import com.javamaster.entity.Ticket;

public class TicketMother {
    public static Ticket.TicketBuilder<?, ?> create() {
        return Ticket.builder()
                .name("name")
                .category(Category.builder().id(1).category("Application & Services").build())
                .owner(UserMother.create().build());
    }
}
