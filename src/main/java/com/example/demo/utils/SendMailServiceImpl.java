package com.example.demo.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwenjie
 */
@Slf4j
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Override
    public void sendTxtMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.mailProperties.getUsername());
        message.setSubject("【" + subject + "-" + LocalDate.now() + " " + LocalTime.now().withNano(0) + "】");
        message.setCc(to);
        message.setText(content);
        this.mailSender.send(message);
        log.info("send txt mail success");
    }

    @Override
    public void sendHtmlMail(String to, String subject, MailContentModel content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("【" + subject + "-" + LocalDate.now() + " " + LocalTime.now().withNano(0) + "】");

            Map<String, Object> model = new HashMap<>();
            model.put("params", content);
            Template template = freeMarkerConfig.getConfiguration().getTemplate("message.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(text, true);
            helper.setFrom(this.mailProperties.getUsername());

            this.mailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            log.error("send template mail failed.", e);
        }
    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content, String attachmentPath) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(this.mailProperties.getUsername());
            helper.setSubject("【" + subject + "-" + LocalDate.now() + " " + LocalTime.now().withNano(0) + "】");
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            String fileName = file.getFilename();
            assert fileName != null;
            helper.addAttachment(fileName, file);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("send attachment mail failed.", e);
        }

    }

    @Override
    public void sendImageResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(this.mailProperties.getUsername());
            helper.setSubject("【" + subject + "-" + LocalDate.now() + " " + LocalTime.now().withNano(0) + "】");
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("send image mail failed.", e);
        }
    }
}
