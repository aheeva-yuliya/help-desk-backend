package com.javamaster.services.adapters;

import com.javamaster.entities.Mail;

public interface EmailSenderServiceAdapter {
    void sendEmail(Mail mail);
}
