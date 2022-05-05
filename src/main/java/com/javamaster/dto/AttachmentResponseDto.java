package com.javamaster.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponseDto {
    private Long id;
    private String name;
}
