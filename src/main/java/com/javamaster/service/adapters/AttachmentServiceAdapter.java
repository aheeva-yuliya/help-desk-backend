package com.javamaster.service.adapters;

import com.javamaster.entity.Attachment;

import java.util.List;

public interface AttachmentServiceAdapter {
    void addAttachment(Attachment attachment);

    Attachment getById(Long attachmentId);

    void removeAttachment(Long attachmentId);

    List<Attachment> getAttachmentsByTicketId(Long ticketId);
}
