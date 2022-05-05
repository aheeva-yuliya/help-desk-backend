package com.javamaster.service.adapters;

import com.javamaster.entity.Mail;

public interface EmailSenderServiceAdapter {
    void sendEmail(Mail mail);
}
