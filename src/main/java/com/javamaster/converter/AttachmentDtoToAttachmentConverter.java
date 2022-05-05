package com.javamaster.converter;

import com.javamaster.entity.Attachment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.BadRequestException;
import java.io.IOException;

@Component
public class AttachmentDtoToAttachmentConverter {
    public Attachment convert(MultipartFile source) {
        if (source == null) {
            return null;
        } else {
            try {
                return Attachment.builder()
                        .name(source.getOriginalFilename())
                        .blob(source.getBytes())
                        .build();
            } catch (IOException exception) {
                throw new BadRequestException(exception);
            }
        }
    }
}
