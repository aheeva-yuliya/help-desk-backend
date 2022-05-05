package com.javamaster.controller;

import com.javamaster.entity.Attachment;
import com.javamaster.service.adapters.AttachmentServiceAdapter;
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

    @GetMapping()
    public ResponseEntity<Resource> downloadFile(@RequestParam Long attachmentId) {
        final Attachment attachment = attachmentService.getById(attachmentId);

        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(attachment.getName());


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(new ByteArrayResource(attachment.getBlob()));
    }

    @DeleteMapping()
    public ResponseEntity<?> removeAttachment(@RequestParam Long attachmentId) {
        attachmentService.removeAttachment(attachmentId);
        return ResponseEntity.ok("removed");
    }
}
