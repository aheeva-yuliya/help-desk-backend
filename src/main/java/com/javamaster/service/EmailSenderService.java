package com.javamaster.service;

import com.javamaster.entity.Mail;
import com.javamaster.entity.User;
import com.javamaster.service.adapters.EmailSenderServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailSenderService implements EmailSenderServiceAdapter {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final TaskExecutor taskExecutor;

    @Override
    public void sendEmail(Mail mail) {
        taskExecutor.execute(() -> {
            try {
                sendTemplateMail(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String getHtmlContent(Mail mail) {
        Context context = new Context();
        context.setVariables(mail.getHtmlTemplate().getProps());
        return templateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
    }

    private void sendTemplateMail(Mail mail) {
        MimeMessage message = emailSender.createMimeMessage();

        List<User> recipients = mail.getTo();

        for (User recipient : recipients) {
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());


                String html = getHtmlContent(mail);

                helper.setTo(recipient.getEmail());
                helper.setFrom("testmailappwise@gmail.com", "Mail Robot");
                helper.setSubject(mail.getSubject());
                helper.setText(html, true);

                emailSender.send(message);

            } catch (MessagingException | UnsupportedEncodingException exception) {
                throw new MailParseException(exception);
            }
        }
    }
}
