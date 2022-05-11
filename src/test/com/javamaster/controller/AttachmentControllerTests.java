package com.javamaster.controller;

import com.javamaster.controller.abstracts.AbstractJunitControllerTests;
import com.javamaster.service.adapters.AttachmentServiceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.mockito.Mockito.verify;

@WebMvcTest(AttachmentController.class)
public class AttachmentControllerTests extends AbstractJunitControllerTests {
    @MockBean
    private AttachmentServiceAdapter attachmentService;

    @Test
    @WithUserDetails("employee1_mogilev@yopmail.com")
    public void shouldReturn200AndRemoveAttachmentWhenRequestedByAuthorized() {
        deleteData(HttpMethod.DELETE, "/attachments/15");
        verify(attachmentService).removeAttachment(15L);
    }
}