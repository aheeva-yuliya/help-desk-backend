package com.javamaster.services;

import com.javamaster.entities.Attachment;
import com.javamaster.entities.History;
import com.javamaster.repositories.AttachmentRepository;
import com.javamaster.services.adapters.AttachmentServiceAdapter;
import com.javamaster.services.adapters.HistoryServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class AttachmentService implements AttachmentServiceAdapter {
    private final AttachmentRepository attachmentRepository;
    private final HistoryServiceAdapter historyService;

    @Override
    public void addAttachment(Attachment attachment) {
        attachmentRepository.save(attachment);
        historyService.saveHistory(
                History.builder()
                        .action("File is attached")
                        .description("File is attached: [" + attachment.getName() + "].")
                        .owner(attachment.getTicket().getOwner())
                        .ticket(attachment.getTicket())
                        .build()
        );
    }

    @Override
    public Attachment getById(Long attachmentId) {
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new NotFoundException(String.format("Attachment %d not found.", attachmentId)));
    }

    @Override
    @Transactional
    public void removeAttachment(Long attachmentId) {
        Attachment attachment = getById(attachmentId);
        attachmentRepository.delete(attachment);
        historyService.saveHistory(
                History.builder()
                        .action("File is removed")
                        .description("File is removed: [" + attachment.getName() + "].")
                        .owner(attachment.getTicket().getOwner())
                        .ticket(attachment.getTicket())
                        .build()
        );
    }

    @Override
    @Transactional
    public List<Attachment> getAttachmentsByTicketId(Long ticketId) {
        return attachmentRepository.findAttachmentsByTicketId(ticketId);
    }
}
