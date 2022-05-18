package com.javamaster.controllers;

import com.javamaster.entities.Attachment;
import com.javamaster.services.adapters.AttachmentServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;

@AllArgsConstructor
@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentServiceAdapter attachmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        final Attachment attachment = attachmentService.getById(id);

        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(attachment.getName());


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(new ByteArrayResource(attachment.getBlob()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeAttachment(@PathVariable Long id) {
        attachmentService.removeAttachment(id);
        return ResponseEntity.ok("Attachment has been successfully removed.");
    }
}
