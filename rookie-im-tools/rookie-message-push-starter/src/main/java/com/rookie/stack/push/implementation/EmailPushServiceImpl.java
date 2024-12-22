package com.rookie.stack.push.implementation;

import com.rookie.stack.push.MessagePushProperties;
import com.rookie.stack.push.MessagePushService;
import com.rookie.stack.push.template.TemplateRenderer;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

/**
 * Name：EmailPushServiceImpl
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
public class EmailPushServiceImpl implements MessagePushService {

    private final JavaMailSender mailSender;
    private final MessagePushProperties.EmailProperties properties;
    private final TemplateRenderer templateRenderer;

    public EmailPushServiceImpl(MessagePushProperties.EmailProperties properties, TemplateRenderer templateRenderer) {
        this.properties = properties;
        this.templateRenderer = templateRenderer;
        this.mailSender = createJavaMailSender(properties);
    }

    /**
     * 创建 JavaMailSender
     */
    private JavaMailSender createJavaMailSender(MessagePushProperties.EmailProperties properties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(properties.getHost());
        sender.setPort(properties.getPort());
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setDefaultEncoding("UTF-8");
        Properties mailProperties = sender.getJavaMailProperties();
        mailProperties.put("mail.transport.protocol", "smtp");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", properties.isSsl());
        mailProperties.put("mail.debug", "false");

        return sender;
    }

    @Override
    public void sendText(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(to);
            helper.setFrom(properties.getUsername());
            helper.setSubject(subject);
            helper.setText(content, false);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("发送简单邮件失败", e);
        }
    }

    @Override
    public void sendTemplate(String to, String subject, String templateName, Object model) {
        try {
            String renderedContent = templateRenderer.render(templateName, model);
            sendHtmlEmail(to, subject, renderedContent); // 调用 sendText 发送模板内容
        } catch (Exception e) {
            throw new RuntimeException("Failed to send template email", e);
        }
    }
    @Override
    public void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setFrom(properties.getFrom());
            helper.setSubject(subject);
            helper.setText(html, true); // 第二个参数设置为 true 表示 HTML 格式
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }
}
