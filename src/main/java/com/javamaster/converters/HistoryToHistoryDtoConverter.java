package com.javamaster.converters;

import com.javamaster.dto.HistoryResponseDto;
import com.javamaster.entities.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryToHistoryDtoConverter {

    public HistoryResponseDto convert(History history) {
        return HistoryResponseDto.builder()
                .date(history.getDate())
                .user(history.getOwner().getName())
                .action(history.getAction())
                .description(history.getDescription())
                .build();
    }
}
