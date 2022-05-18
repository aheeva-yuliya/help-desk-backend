package com.javamaster.utils.mothers;

import com.javamaster.dto.TicketOverview;

public class TicketOverviewMother {
    public static TicketOverview.TicketOverviewBuilder<?, ?> create() {
        return TicketOverview.builder()
                .name("overview")
                .category("Application & Services");
    }
}
