package com.javamaster.services.adapters;

import com.javamaster.entities.History;

import java.util.List;

public interface HistoryServiceAdapter {
    List<History> getHistoryByTicketId(Long ticketId);

    void saveHistory(History history);

}
