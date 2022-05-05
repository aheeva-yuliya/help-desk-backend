package com.javamaster.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDto {
    private String message;
    private List<ErrorFieldDto> fields;

    public ApiErrorDto(String message) {
        this.message = message;
    }
}