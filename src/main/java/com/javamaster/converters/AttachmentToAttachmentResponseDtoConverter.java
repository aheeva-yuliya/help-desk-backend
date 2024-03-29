package com.javamaster.converters;

import com.javamaster.dto.AttachmentResponseDto;
import com.javamaster.entities.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentToAttachmentResponseDtoConverter {

    public AttachmentResponseDto convert(Attachment attachment) {
        return AttachmentResponseDto.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .build();
    }
}
