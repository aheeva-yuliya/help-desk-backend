package com.javamaster.service.adapters;

import com.javamaster.entity.History;

import java.util.List;

public interface HistoryServiceAdapter {
    List<History> getHistoryByTicketId(Long ticketId);

    void saveHistory(History history);

}
