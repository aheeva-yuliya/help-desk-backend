package com.javamaster.service;

import com.javamaster.entity.History;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repository.HistoryRepository;
import com.javamaster.service.adapters.HistoryServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryService implements HistoryServiceAdapter {
    private final HistoryRepository historyRepository;
    private final DateTimeProvider dateTimeProvider;

    @Override
    public List<History> getHistoryByTicketId(Long ticketId) {
        return historyRepository.findHistoriesByTicketId(ticketId);
    }

    @Override
    public void saveHistory(History history) {
        history.setDate(dateTimeProvider.now());
        historyRepository.save(history);
    }
}
